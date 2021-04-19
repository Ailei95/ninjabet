package io.ninjabet.football.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.ninjabet.auth.entity.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity(name = "COUNTRIES")
public class Country implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String iso2Code;

    private String imageUrl;

    @JsonIgnore
    private boolean deleted;

    @JsonIgnore
    private Date deleteDate;

    @JsonIgnore
    @ManyToOne
    private User lastDeleteActionUser;

    public Country() {
    }

    public Country(String name, String iso2Code, String imageUrl) {
        this.iso2Code = iso2Code;
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIso2Code() {
        return iso2Code;
    }

    public void setIso2Code(String iso2Code) {
        this.iso2Code = iso2Code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isDeleted() { return deleted; }

    public void setDeleted(boolean deleted) { this.deleted = deleted; }

    public Date getDeleteDate() {
        return deleteDate;
    }

    public void setDeleteDate(Date deleteDate) {
        this.deleteDate = deleteDate;
    }

    public User getLastDeleteActionUser() {
        return lastDeleteActionUser;
    }

    public void setLastDeleteActionUser(User lastDeleteActionUser) {
        this.lastDeleteActionUser = lastDeleteActionUser;
    }
}
