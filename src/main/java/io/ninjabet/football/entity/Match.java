package io.ninjabet.football.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.ninjabet.core.entity.AbstractEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity(name = "MATCHES")
public class Match implements Serializable, AbstractEntity<Long> {

    @Id
    @GeneratedValue
    private Long id;

    @Transient
    public Long homeId;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(fetch = FetchType.LAZY)
    private Team home;

    @Transient
    public Long guestId;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(fetch = FetchType.LAZY)
    private Team guest;

    private Date date;

    @Transient
    public Long matchdayId;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(fetch = FetchType.LAZY)
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

    public Long getHomeId() {
        return home.getId();
    }

    public void setHome(Team home) {
        this.home = home;
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

    public void setMatchdayId(Long matchdayId) {
        this.matchdayId = matchdayId;
    }

    public void setMatchday(Matchday matcheay) {
        this.matchday = matcheay;
    }
}
