package com.wong.question.user.mapper;

import com.wong.question.user.entity.ScoreEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author wong
* @description 针对表【score】的数据库操作Mapper
* @createDate 2025-08-12 18:28:47
* @Entity com.wong.question.user.entity.ScoreEntity
*/
@Mapper
public interface ScoreMapper extends BaseMapper<ScoreEntity> {
    List<ScoreEntity> getScoreAll();
}




