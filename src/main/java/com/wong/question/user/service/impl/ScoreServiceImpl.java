package com.wong.question.user.service.impl;

import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wong.question.user.entity.ScoreEntity;
import com.wong.question.user.service.ScoreService;
import com.wong.question.user.mapper.ScoreMapper;
import com.wong.question.utils.R;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
* @author wong
* @description 针对表【score】的数据库操作Service实现
* @createDate 2025-08-12 18:28:47
*/
@Service
public class ScoreServiceImpl extends ServiceImpl<ScoreMapper, ScoreEntity>
    implements ScoreService{

    @Override
    public R getScoreList() {
        List<ScoreEntity> scoreAll = baseMapper.getScoreAll();
        // 按名字分组
        Map<String, List<ScoreEntity>> collect = scoreAll.stream().collect(Collectors.groupingBy(ScoreEntity::getUserName));
        collect.forEach((key, list) -> {
            int sum = list.stream().mapToInt(ScoreEntity::getScore).sum();
            list.forEach(object -> object.setSum(sum));
        });
        Map<String, List<ScoreEntity>> stringListMap = sortMapByListElementProperty(collect);
        return R.success(stringListMap);
    }

//    @Override
//    public List<ScoreEntity> getScoreList() {
//        List<ScoreEntity> scoreAll = baseMapper.getScoreAll();
//
//    }
    /**
     * 按List中ScoreEntity的score属性对Map进行排序
     */
    private static Map<String, List<ScoreEntity>> sortMapByListElementProperty(Map<String, List<ScoreEntity>> map) {
        // 对Map的entry进行排序
        return map.entrySet().stream()
                // 按List中第一个元素的score排序（也可以按平均值、最大值等排序）
                .sorted((entry1, entry2) -> {
                    // 获取两个列表中第一个元素的score值进行比较
                    int score1 = entry1.getValue().get(0).getSum();
                    int score2 = entry2.getValue().get(0).getSum();
//                    return Integer.compare(score1, score2); // 升序
                     return Integer.compare(score2, score1); // 降序
                })
                // 收集到LinkedHashMap中以保持排序
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        ((scoreEntities, scoreEntities2) -> scoreEntities),
                        LinkedHashMap::new
                ));
    }
}




