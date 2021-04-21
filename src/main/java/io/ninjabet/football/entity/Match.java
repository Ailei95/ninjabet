package io.ninjabet.football.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.ninjabet.core.entity.AbstractEntity;
import io.ninjabet.core.entity.DeleteManagerEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity(name = "MATCHES")
public class Match extends DeleteManagerEntity implements Serializable, AbstractEntity<Long> {

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

    public Match() {
    }

    public Match(Team home, Team guest, Date date, Matchday matchday) {
        this.home = home;
        this.guest = guest;
        this.date = date;
        this.matchday = matchday;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonIgnore
    public Team getHome() {
        return home;
    }

    public Long getHomeId() {
        return home.getId();
    }

    public void setHome(Team home) {
        this.home = home;
    }

    @JsonIgnore
    public Team getGuest() {
        return guest;
    }

    public Long getGuestId() {
        return guest.getId();
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

    public Long getMatchdayId() { return this.matchday.getId(); }

    @JsonIgnore
    public Matchday getMatchday() { return matchday; }

    public void setMatchday(Matchday matcheay) {
        this.matchday = matcheay;
    }
}
