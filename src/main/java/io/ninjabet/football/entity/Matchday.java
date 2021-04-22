package io.ninjabet.football.entity;

import io.ninjabet.core.entity.AbstractEntity;
import io.ninjabet.core.entity.DeleteManagerEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity(name = "MATCHDAYS")
public class Matchday extends DeleteManagerEntity implements Serializable, AbstractEntity<Long> {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    private Date fromDate;

    private Date toDate;

    @Transient
    public Long competitionId;

    @ManyToOne
    private Competition competition;

    public Matchday() {
    }

    public Matchday(String name, Date fromDate, Date toDate, Competition competition) {
        this.name = name;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.competition = competition;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public Long getCompetitionId() { return competition.getId(); }

    public void setCompetitionId(Long competitionId) {
        this.competitionId = competitionId;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }
}
