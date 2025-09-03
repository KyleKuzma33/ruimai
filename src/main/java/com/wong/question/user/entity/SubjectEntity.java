package com.wong.question.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 科普题目
 * @TableName subject
 */
@TableName(value ="subject")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubjectEntity implements Serializable {
    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 0 选择题 1填空题 2问答题
     */
    @TableField(value = "type")
    private Integer type;

    /**
     * 题目标签
     */
    @TableField(value = "subject_label")
    private String subjectLabel;

    /**
     * 题目难度等级
     */
    @TableField(value = "level")
    private String level;

    /**
     * 题目
     */
    @TableField(value = "body")
    @NotBlank(message = "题目内容不能为空")
    private String body;

    /**
     * 正确答案
     */
    @TableField(value = "correct_answer")
    private String correctAnswer;

    /**
     * 排序
     */
    @TableField(value = "sort")
    private Integer sort;

    /**
     * 
     */
    @TableField(value = "create_time")
    private Date createTime;

    @TableField(value = "second")
    private Integer second;

    /**
     * 
     */
    @TableField(value = "del_flag")
    private Boolean delFlag;

    @TableField(exist = false)
    private List<SubjectAnswerEntity> subjectAnswerEntityList;

    @TableField(exist = false)
    private Boolean subjectStart;



    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        SubjectEntity other = (SubjectEntity) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getSubjectLabel() == null ? other.getSubjectLabel() == null : this.getSubjectLabel().equals(other.getSubjectLabel()))
            && (this.getLevel() == null ? other.getLevel() == null : this.getLevel().equals(other.getLevel()))
            && (this.getBody() == null ? other.getBody() == null : this.getBody().equals(other.getBody()))
            && (this.getCorrectAnswer() == null ? other.getCorrectAnswer() == null : this.getCorrectAnswer().equals(other.getCorrectAnswer()))
            && (this.getSort() == null ? other.getSort() == null : this.getSort().equals(other.getSort()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getDelFlag() == null ? other.getDelFlag() == null : this.getDelFlag().equals(other.getDelFlag()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getSubjectLabel() == null) ? 0 : getSubjectLabel().hashCode());
        result = prime * result + ((getLevel() == null) ? 0 : getLevel().hashCode());
        result = prime * result + ((getBody() == null) ? 0 : getBody().hashCode());
        result = prime * result + ((getCorrectAnswer() == null) ? 0 : getCorrectAnswer().hashCode());
        result = prime * result + ((getSort() == null) ? 0 : getSort().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getDelFlag() == null) ? 0 : getDelFlag().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", type=").append(type);
        sb.append(", subjectLabel=").append(subjectLabel);
        sb.append(", level=").append(level);
        sb.append(", body=").append(body);
        sb.append(", correctAnswer=").append(correctAnswer);
        sb.append(", sort=").append(sort);
        sb.append(", createTime=").append(createTime);
        sb.append(", delFlag=").append(delFlag);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}