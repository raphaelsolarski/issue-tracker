package com.raphaelsolarski.issuetracker.model;

import com.raphaelsolarski.issuetracker.model.base.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "comments")
public class Comment extends BaseEntity{

    private String content;

    private Integer userId;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
