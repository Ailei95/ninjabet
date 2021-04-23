package io.ninjabet.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;

/***********************************************************************************************************************

    Constraint: Entity extended with DeleteManagerEntity

    !Note: null = null in sql is evaluated false

    !Note: It doesn't handle reference to a deleted Entity

    Not deleted entity has the deletedDate value equals to new Date(0)

    Unique constraint has to extended with deleteDate column -> deleteDate column is not nullable

    If Entity Primary Key coincides with Foreign Table Primary Key, the key has to extended with deleteDate column

    Feeds: Unstable class

 **********************************************************************************************************************/

// Abstract class for DeleteManagerService

@MappedSuperclass
public abstract class DeleteManagerEntity {
    @Column(nullable = false)
    protected Date deleteDate;

    public DeleteManagerEntity() {
    }

    public DeleteManagerEntity(Date deleteDate) {
        this.deleteDate = deleteDate;
    }

    @JsonIgnore
    public Date getDeleteDate() {
        return deleteDate;
    }

    public void setDeleteDate(Date deleteDate) {
        this.deleteDate = deleteDate;
    }
}
