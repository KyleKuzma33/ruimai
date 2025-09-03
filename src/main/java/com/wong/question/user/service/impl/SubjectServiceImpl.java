package com.wong.question.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wong.question.user.entity.SubjectAnswerEntity;
import com.wong.question.user.entity.SubjectEntity;
import com.wong.question.user.service.SubjectAnswerService;
import com.wong.question.user.service.SubjectService;
import com.wong.question.user.mapper.SubjectMapper;
import com.wong.question.utils.R;
import com.wong.question.utils.RedisUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
* @author wong
* @description
* @createDate 2025-08-09 15:54:38
*/
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, SubjectEntity>
    implements SubjectService {

    @Resource
    private SubjectAnswerService answerService;

    @Resource
    private RedisUtil redisUtil;

    public static final String SUB_KEY = "subject_now";

    @Override
    public IPage<SubjectEntity> getListPage(int current, int size) {
        Page<SubjectEntity> page = new Page<>(current, size);
        IPage<SubjectEntity> subjectListPage = baseMapper.getSubjectList(page);
        List<Integer> ids = subjectListPage.getRecords().stream()
                .map(SubjectEntity::getId)
                .collect(Collectors.toList());
        if (!ids.isEmpty()) {
            List<SubjectAnswerEntity> answers = answerService.getListById(ids);

            Map<Integer, List<SubjectAnswerEntity>> answerMap = answers.stream()
                    .collect(Collectors.groupingBy(SubjectAnswerEntity::getSubjectId));

            subjectListPage.getRecords().forEach(subjectEntity ->
                    subjectEntity.setSubjectAnswerEntityList(answerMap.getOrDefault(subjectEntity.getId(), Collections.emptyList())));
        }

        List<SubjectEntity> records = subjectListPage.getRecords();
        List<SubjectEntity> collect = records.stream().sorted(Comparator.comparing(SubjectEntity::getSort).reversed()).collect(Collectors.toList());
        subjectListPage.setRecords(collect);

        return subjectListPage;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R addSubject(SubjectEntity subjectEntity) {
        if (subjectEntity.getSubjectAnswerEntityList().isEmpty()) {
            return R.error("新增失败，答案列表为空");
        }



        StringBuilder builder = new StringBuilder();
        subjectEntity.getSubjectAnswerEntityList().forEach(subjectAnswerEntity -> {
            if (subjectAnswerEntity.getCorrectAnswer()) {
                builder.append(subjectAnswerEntity.getAnswerNumber());
            }
        });
        subjectEntity.setDelFlag(false);
        subjectEntity.setCorrectAnswer(builder.toString());
        Integer subId = subjectEntity.getId();
        if (subId != null) {
            SubjectEntity byId = this.getById(subId);
            if (byId.getSort() != subjectEntity.getSort()) {
                if (this.checkStoreExits(subjectEntity.getSort())) {
                    return R.error("题目序号重复");
                }
            }
        } else {
            if (this.checkStoreExits(subjectEntity.getSort())) {
                return R.error("题目序号重复");
            }
        }

//        if (this.checkSubjectExits(subjectEntity.getBody())) {
//            return R.error("题目重复");
//        }

        if (saveOrUpdate(subjectEntity)) {
            Integer id = subjectEntity.getId();
            subjectEntity.getSubjectAnswerEntityList().forEach(subjectAnswerEntity -> subjectAnswerEntity.setSubjectId(id));
            if (!answerService.saveOrUpdateBatch(subjectEntity.getSubjectAnswerEntityList())) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return R.error("新增答案失败，请检查");
            }
        }
        return R.success("新增成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R delById(Integer id) {
        if (baseMapper.deleteById(id) > 0 && answerService.delBySubId(id)) {
            return R.success("删除成功");
        }
        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        return R.error("删除失败");
    }

    @Override
    public R getSubNow() {
        Object entity = redisUtil.get(SUB_KEY);
        if (ObjectUtils.isEmpty(entity)) {
            return R.error(510, "未设置题目，请稍后");
        }
        SubjectEntity subjectEntity = (SubjectEntity) entity;
        if (this.getCountdown(subjectEntity.getId())) {
            subjectEntity.setSubjectStart(true);
        } else {
            subjectEntity.setSubjectStart(false);
        }
        return R.success("获取最新题目成功", entity);
    }

    @Override
    public R setSubNow(Integer subjectId) {
        SubjectEntity subjectEntity = null;
        if (subjectId == null) {
            // 获取第一题
            subjectEntity = this.getSubjectByStore(1);
        } else {
            SubjectEntity byId = getById(subjectId);
            for (int i = 1; i < 10; i++) {
                int i1 = byId.getSort() + i;
                SubjectEntity subjectByStore = this.getSubjectByStore(i1);
                if (subjectByStore != null) {
                    subjectEntity = subjectByStore;
                    break;
                }
            }
            if (ObjectUtils.isEmpty(subjectEntity)) {
                return R.error("最后一题，没有下一题了");
            }
        }

        subjectId = subjectEntity.getId();
        List<SubjectAnswerEntity> listBySubjectId = answerService.getListBySubjectId(subjectId);
        subjectEntity.setSubjectAnswerEntityList(listBySubjectId);
        redisUtil.set(SUB_KEY, subjectEntity);
        Map<String, Integer> map = new HashMap<>();
        map.put("subjectId", subjectId);
        return redisUtil.set(SUB_KEY+subjectEntity.getId(), subjectEntity) ? R.success("设置成功", map) : R.error("设置失败");
    }

    @Override
    public Boolean verificationAnswer(Integer subjectId, String answer) {
        SubjectEntity subjectEntity = getById(subjectId);

        // 判断答案长度一样
        if (subjectEntity.getCorrectAnswer().length() != answer.length()) {
            return false;
        }

        List<Character> charList = subjectEntity.getCorrectAnswer().chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.toList());

        char[] answerArray = answer.toCharArray();

        // 用户答案，遍历比较
        for (char c : answerArray) {
            if (!charList.contains(c)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public R startSub(Integer subjectId) {
        SubjectEntity byId = this.getById(subjectId);
        if (ObjectUtils.isEmpty(byId)) {
            return R.error("未查询到题目，请检查Id");
        }
        Integer second = byId.getSecond()+3;
        redisUtil.set(SUB_KEY+subjectId+"start", subjectId, second);
        return redisUtil.expire(SUB_KEY+subjectId, second) ? R.success("开始成功") : R.error("开始失败");
    }

    @Override
    public Boolean getCountdown(Integer subjectId) {
        if (!redisUtil.hasKey(SUB_KEY+subjectId+"start")) {
            return false;
        }
        return true;
    }

    // 检查序号是否重复
    private Boolean checkStoreExits(Integer store) {
        LambdaQueryWrapper<SubjectEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SubjectEntity::getSort, store);
        return exists(queryWrapper);
    }

    private Boolean checkSubjectExits(String body) {
        LambdaQueryWrapper<SubjectEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SubjectEntity::getBody, body);
        return exists(queryWrapper);
    }

    // 按序号获取题目
    private SubjectEntity getSubjectByStore(Integer store) {
        LambdaQueryWrapper<SubjectEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SubjectEntity::getSort, store);
        return this.getOne(queryWrapper);
    }
}

