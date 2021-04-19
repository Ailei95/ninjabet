package io.ninjabet.football.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity(name = "RESULTS")
public class Result implements Serializable {

    @Id
    private Long id;

    @OneToOne
    @MapsId
    private Match match;

    private Integer homeScore;

    private Integer guestScore;

    private String status;

    @JsonIgnore
    private boolean deleted;

    @JsonIgnore
    private Date deleteDate;

    public Result() {
    }

    public Result(Match match, Integer homeScore, Integer guestScore, String status) {
        this.match = match;
        this.homeScore = homeScore;
        this.guestScore = guestScore;
        this.status = status;
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public Integer getHomeScore() {
        return homeScore;
    }

    public void setHomeScore(Integer homeScore) {
        this.homeScore = homeScore;
    }

    public Integer getGuestScore() {
        return guestScore;
    }

    public void setGuestScore(Integer guestScore) {
        this.guestScore = guestScore;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
}
