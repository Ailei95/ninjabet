package io.ninjabet.football.entity;

import io.ninjabet.core.entity.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity(name = "MATCHES")
public class Match implements Serializable, AbstractEntity<Long> {

    @Id
    @GeneratedValue
    private Long id;

    private Date date;

    @ManyToOne(fetch = FetchType.LAZY)
    private Team home;

    @ManyToOne(fetch = FetchType.LAZY)
    private Team guest;

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
}
