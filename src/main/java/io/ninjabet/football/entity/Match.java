package io.ninjabet.football.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity(name = "MATCHES")
public class Match implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Team home;

    @ManyToOne
    private Team guest;

    private Date date;

    @ManyToOne
    private Matchday matchday;

    @JsonIgnore
    private boolean deleted;

    @JsonIgnore
    private Date deleteDate;

    public Match() {
    }

    public Match(Team home, Team guest, Date date, Matchday matchday) {
        this.home = home;
        this.guest = guest;
        this.date = date;
        this.matchday = matchday;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Team getHome() {
        return home;
    }

    public void setHome(Team home) {
        this.home = home;
    }

    public Team getGuest() {
        return guest;
    }

    public void setGuest(Team guest) {
        this.guest = guest;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Matchday getMatchday() {
        return matchday;
    }

    public void setMatchday(Matchday matcheay) {
        this.matchday = matcheay;
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
