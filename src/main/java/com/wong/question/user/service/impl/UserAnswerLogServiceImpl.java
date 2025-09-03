package com.wong.question.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sun.javafx.collections.MappingChange;
import com.wong.question.user.entity.ScoreEntity;
import com.wong.question.user.entity.SubjectEntity;
import com.wong.question.user.entity.UserAnswerLogEntity;
import com.wong.question.user.entity.UserEntity;
import com.wong.question.user.service.*;
import com.wong.question.user.mapper.UserAnswerLogMapper;
import com.wong.question.utils.R;
import com.wong.question.utils.RedisUtil;
import io.swagger.models.auth.In;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.logging.Handler;
import java.util.stream.Collectors;

import static com.wong.question.user.service.impl.SubjectServiceImpl.SUB_KEY;

/**
* @author wong
* @description 针对表【user_answer_log】的数据库操作Service实现
* @createDate 2025-08-11 16:22:46
*/
@Service
public class UserAnswerLogServiceImpl extends ServiceImpl<UserAnswerLogMapper, UserAnswerLogEntity>
    implements UserAnswerLogService {

    @Resource
    private UserService userService;

    @Resource
    private SubjectService subjectService;

    @Resource
    private ScoreService scoreService;

    @Resource
    private RedisUtil redisUtil;

    @Override
    public R addAnswer(UserAnswerLogEntity userAnswerLogEntity) {
        if (!redisUtil.hasKey(SUB_KEY+userAnswerLogEntity.getSubjectId()+"start")) {
            return R.error("答题未开始");
        }

        if (!redisUtil.hasKey(SUB_KEY+userAnswerLogEntity.getSubjectId())) {
            return R.error("答题失败，答题时间结束");
        }


        UserEntity userEntity = userService.getById(userAnswerLogEntity.getUserId());

        Map<String, String> map = new HashMap<>();
        map.put("userName", userEntity.getName());

        // 检查重复答题
        if (this.checkExits(userAnswerLogEntity.getUserId(), userAnswerLogEntity.getSessionId(), userAnswerLogEntity.getSubjectId())) {
            Integer sum = this.getSumAllBySessionId(userAnswerLogEntity.getUserId(), userAnswerLogEntity.getSessionId());
            map.put("sum", sum == null ? "0" : sum.toString());
            return R.error("请勿重复答题", map);
        }

        SubjectEntity subjectEntity = subjectService.getById(userAnswerLogEntity.getSubjectId());
        UserAnswerLogEntity entity = UserAnswerLogEntity.builder().build();

        BeanUtils.copyProperties(userAnswerLogEntity, entity);
        entity.setUserName(userEntity.getName());
        entity.setSubjectCorrectAnswer(subjectEntity.getCorrectAnswer());
        entity.setPoints(0);
        entity.setCorrect(false);
        entity.setCreateTime(new Date());

        // 校验答案是否正确，正确赋值10分
        if (subjectService.verificationAnswer(userAnswerLogEntity.getSubjectId(), userAnswerLogEntity.getUserAnswer())) {
            entity.setPoints(10);
            entity.setCorrect(true);
            map.put("correct", "正确");
        } else {
            map.put("correct", "错误");
        }

        if (save(entity)) {
            // 统计自己队伍的分数
            ScoreEntity score = new ScoreEntity();
            score.setUserId(entity.getUserId());
            score.setUserName(entity.getUserName());
            score.setSessionId(entity.getSessionId());
            score.setScore(entity.getPoints());
            score.setCreateTime(new Date());
            score.setSubNum(entity.getSubjectId());
            scoreService.save(score);

            Integer sum = this.getSumAllBySessionId(userAnswerLogEntity.getUserId(), userAnswerLogEntity.getSessionId());
            map.put("sum", sum == null ? "0" : sum.toString());
        }

        return R.success("回答完毕", map);
    }

    @Override
    public Integer getSumAllBySessionId(Integer userId, String sessionId) {
        return baseMapper.getSumAllBySessionId(userId, sessionId);
    }

    @Override
    public List<UserAnswerLogEntity> getAllBySubjectId(Integer subjectId) {
        UserAnswerLogEntity byIdEntity = this.getById(subjectId);
        SubjectEntity subjectEntity;
        if (ObjectUtils.isEmpty(byIdEntity)) {
            subjectEntity = subjectService.getById(subjectId);
        } else {
            subjectEntity = null;
        }
        List<UserAnswerLogEntity> answerList = new ArrayList<>();
        List<ScoreEntity> scoreEntityList = new ArrayList<>();
        //
        List<UserEntity> list = userService.getList();
        list.forEach(userEntity -> {
            UserAnswerLogEntity entity = this.getEntity(userEntity.getId(), subjectId);
            if (entity == null) {
                UserAnswerLogEntity answerLog = new UserAnswerLogEntity();
                answerLog.setUserId(Math.toIntExact(userEntity.getId()));
                answerLog.setUserName(userEntity.getName());
                answerLog.setSubjectId(subjectId);
                answerLog.setUserAnswer("未作答");
                answerLog.setPoints(0);
                answerLog.setCorrect(false);
                if (!ObjectUtils.isEmpty(subjectEntity)) {
                    answerLog.setSubjectCorrectAnswer(subjectEntity.getCorrectAnswer());
                } else {
                    answerLog.setSubjectCorrectAnswer(byIdEntity.getSubjectCorrectAnswer());

                }
                answerLog.setCreateTime(new Date());
                answerLog.setSessionId("第一轮");
                answerList.add(answerLog);

                ScoreEntity score = new ScoreEntity();
                score.setUserId(Math.toIntExact(userEntity.getId()));
                score.setUserName(userEntity.getName());
                score.setScore(0);
                score.setSubNum(subjectId);
                score.setCreateTime(new Date());
                score.setSessionId("第一轮");
                scoreEntityList.add(score);
            }
        });
        if (!answerList.isEmpty()) {
            this.saveBatch(answerList);
        }
        if (!scoreEntityList.isEmpty()) {
            scoreService.saveBatch(scoreEntityList);
        }

        List<UserAnswerLogEntity> allBySubjectId = baseMapper.getAllBySubjectId(subjectId);
        List<UserAnswerLogEntity> allSumByUserId = baseMapper.getAllSumByUserId();
        Map<Integer, Integer> collect = allSumByUserId.stream().collect(Collectors.toMap(UserAnswerLogEntity::getUserId, UserAnswerLogEntity::getSum, (value1, value2) -> value1));
        allBySubjectId.forEach(userAnswerLogEntity -> {
            userAnswerLogEntity.setSum(collect.get(userAnswerLogEntity.getUserId()));
        });

        return allBySubjectId.stream().sorted(Comparator.comparing(UserAnswerLogEntity::getSum).reversed()).collect(Collectors.toList());
    }

    @Override
    public List<UserAnswerLogEntity> getAll() {
        return baseMapper.getAll();
    }

    private Boolean checkExits(Integer userId, String sessionId, Integer subjectId) {
        LambdaQueryWrapper<UserAnswerLogEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserAnswerLogEntity::getUserId, userId);
        queryWrapper.eq(UserAnswerLogEntity::getSessionId, sessionId);
        queryWrapper.eq(UserAnswerLogEntity::getSubjectId, subjectId);
        return exists(queryWrapper);
    }

    private UserAnswerLogEntity getEntity(Long userId, Integer subjectId) {
        LambdaQueryWrapper<UserAnswerLogEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserAnswerLogEntity::getUserId, userId);
        queryWrapper.eq(UserAnswerLogEntity::getSubjectId, subjectId).last("limit 1");
        return baseMapper.selectOne(queryWrapper);
    }
}




