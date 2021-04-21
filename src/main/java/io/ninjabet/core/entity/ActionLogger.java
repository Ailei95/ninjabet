package io.ninjabet.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.ninjabet.auth.entity.User;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
public class ActionLogger implements AbstractEntity<Long> {

    @Id
    @GeneratedValue
    private Long id;

    private String tableName;

    private String tableKey;

    private String action;

    private Date date;

    @ManyToOne
    private User user;

    public ActionLogger() {
    }

    public ActionLogger(String tableName, String tableKey, String action, Date date, User user) {
        this.tableName = tableName;
        this.tableKey = tableKey;
        this.action = action;
        this.date = date;
        this.user = user;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableKey() {
        return tableKey;
    }

    public void setTableKey(String tableKey) {
        this.tableKey = tableKey;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUserId() {
        return user.getEmail();
    }

    @JsonIgnore
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
