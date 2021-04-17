package io.ninjabet.football.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
public class MatchDay {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    private Date fromDate;

    private Date toDate;

    @ManyToOne
    private Competition competition;

    public MatchDay() {
    }

    public MatchDay(String name, Date fromDate, Date toDate, Competition competition) {
        this.name = name;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.competition = competition;
    }

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

    public Competition getCompetition() {
        return competition;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }
}
