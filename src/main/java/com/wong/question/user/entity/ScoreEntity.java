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
 * @TableName score
 */
@TableName(value ="score")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScoreEntity implements Serializable {
    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    @TableField(value = "session_id")
    private String sessionId;

    /**
     * 
     */
    @TableField(value = "score")
    private Integer score;

    @TableField(value = "sub_num")
    private Integer subNum;

    /**
     * 
     */
    @TableField(value = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 
     */
    @TableField(value = "user_id")
    private Integer userId;

    @TableField(exist = false)
    private Integer sum;

    /**
     * 
     */
    @TableField(value = "user_name")
    private String userName;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}