package io.ninjabet.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;

// Abstract class for DeleteManagerService

/*
    DeleteManagerEntity Constraint extended with Entity

    !Note: null = null in sql is evaluated false

    Unique constraint has to extended with deleteDate column -> deleteDate column is nullable

    If Entity Primary Key coincides with Foreign Table Primary Key, the key has to extended with deleteDate column
 */

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
