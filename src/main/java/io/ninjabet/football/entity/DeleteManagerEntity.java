package io.ninjabet.football.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.ninjabet.auth.entity.User;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
public abstract class DeleteManagerEntity<T extends Serializable> {
    @JsonIgnore
    @Column
    protected boolean deleted;

    @JsonIgnore
    @Column
    protected Date deleteDate;

    @JsonIgnore
    @ManyToOne
    protected User lastDeleteActionUser;

    public DeleteManagerEntity() {
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Date getDeleteDate() {
        return deleteDate;
    }

    public void setDeleteDate(Date deleteDate) {
        this.deleteDate = deleteDate;
    }

    public User getLastDeleteActionUser() { return lastDeleteActionUser; }

    public void setLastDeleteActionUser(User lastDeleteActionUser) { this.lastDeleteActionUser = lastDeleteActionUser; }
}
