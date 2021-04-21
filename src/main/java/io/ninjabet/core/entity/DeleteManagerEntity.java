package io.ninjabet.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;

// Abstract class for DeleteManagerService

@MappedSuperclass
public abstract class DeleteManagerEntity {
    @Column
    protected Date deleteDate;

    public DeleteManagerEntity() {
    }

    @JsonIgnore
    public Date getDeleteDate() {
        return deleteDate;
    }

    public void setDeleteDate(Date deleteDate) {
        this.deleteDate = deleteDate;
    }
}
