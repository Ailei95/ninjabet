package io.ninjabet.football.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
public class JMatch {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Team home;

    @ManyToOne
    private Team guest;

    private Date date;

    @ManyToOne
    private MatchDay matchDay;

    public JMatch() {
    }

    public JMatch(Team home, Team guest, Date date, MatchDay matchDay) {
        this.home = home;
        this.guest = guest;
        this.date = date;
        this.matchDay = matchDay;
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

    public MatchDay getMatchDay() {
        return matchDay;
    }

    public void setMatchDay(MatchDay matchDay) {
        this.matchDay = matchDay;
    }
}
