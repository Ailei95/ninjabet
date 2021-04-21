package io.ninjabet.football.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.ninjabet.auth.entity.User;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@MappedSuperclass
public abstract class DeleteManagerEntity {
    @Column
    protected Date deleteDate;

    @ManyToOne
    protected User lastDeleteActionUser;

    public DeleteManagerEntity() {
    }

    @JsonIgnore
    public Date getDeleteDate() {
        return deleteDate;
    }

    public void setDeleteDate(Date deleteDate) {
        this.deleteDate = deleteDate;
    }

    @JsonIgnore
    public User getLastDeleteActionUser() { return lastDeleteActionUser; }

    public void setLastDeleteActionUser(User lastDeleteActionUser) { this.lastDeleteActionUser = lastDeleteActionUser; }
}
