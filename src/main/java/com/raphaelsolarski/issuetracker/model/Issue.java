package com.raphaelsolarski.issuetracker.model;

import com.raphaelsolarski.issuetracker.model.base.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "issues")
public class Issue extends BaseEntity{

    @Column(nullable = false)
    private String title;
    private String description;
    @Column(name = "user_id",nullable = false)
    private Integer userId;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
