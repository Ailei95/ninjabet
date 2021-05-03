package io.ninjabet.core.entity;

import io.ninjabet.auth.entity.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;

@Getter
@Setter
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
}
