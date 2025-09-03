package com.wong.question.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 科普题目答案
 * @TableName subject_answer
 */
@TableName(value ="subject_answer")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubjectAnswerEntity implements Serializable {
    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    @TableField(value = "subject_id")
    private Integer subjectId;

    /**
     * 答案序号
     */
    @TableField(value = "answer_number")
    private String answerNumber;

    /**
     * 答案内容
     */
    @TableField(value = "answer_body")
    private String answerBody;

    /**
     * 是否是正确答案
     */
    @TableField(value = "correct_answer")
    private Boolean correctAnswer;

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
        SubjectAnswerEntity other = (SubjectAnswerEntity) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getSubjectId() == null ? other.getSubjectId() == null : this.getSubjectId().equals(other.getSubjectId()))
            && (this.getAnswerNumber() == null ? other.getAnswerNumber() == null : this.getAnswerNumber().equals(other.getAnswerNumber()))
            && (this.getAnswerBody() == null ? other.getAnswerBody() == null : this.getAnswerBody().equals(other.getAnswerBody()))
            && (this.getCorrectAnswer() == null ? other.getCorrectAnswer() == null : this.getCorrectAnswer().equals(other.getCorrectAnswer()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getSubjectId() == null) ? 0 : getSubjectId().hashCode());
        result = prime * result + ((getAnswerNumber() == null) ? 0 : getAnswerNumber().hashCode());
        result = prime * result + ((getAnswerBody() == null) ? 0 : getAnswerBody().hashCode());
        result = prime * result + ((getCorrectAnswer() == null) ? 0 : getCorrectAnswer().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", subjectId=").append(subjectId);
        sb.append(", answerNumber=").append(answerNumber);
        sb.append(", answerBody=").append(answerBody);
        sb.append(", correctAnswer=").append(correctAnswer);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}