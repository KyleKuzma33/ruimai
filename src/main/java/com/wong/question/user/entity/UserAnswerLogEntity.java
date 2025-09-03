package com.wong.question.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @TableName user_answer_log
 */
@TableName(value ="user_answer_log")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserAnswerLogEntity implements Serializable {
    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    @TableField(value = "user_id")
    private Integer userId;

    /**
     * 
     */
    @TableField(value = "user_name")
    private String userName;

    /**
     * 
     */
    @TableField(value = "subject_id")
    private Integer subjectId;

    /**
     * 
     */
    @TableField(value = "subject_correct_answer")
    private String subjectCorrectAnswer;

    /**
     * 
     */
    @TableField(value = "user_answer")
    private String userAnswer;

    /**
     * 
     */
    @TableField(value = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:ss:mm", timezone = "Asia/Shanghai")
    private Date createTime;

    /**
     * 
     */
    @TableField(value = "correct")
    private Boolean correct;

    /**
     * 
     */
    @TableField(value = "session_id")
    private String sessionId;

    /**
     * 
     */
    @TableField(value = "points")
    private Integer points;

    @TableField(exist = false)
    private Integer sum;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}