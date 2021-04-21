package io.ninjabet.football.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity(name = "COMPETITIONS")
public class Competition extends DeleteManagerEntity<Long> implements Serializable, AbstractEntity<Long> {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    private Date fromDate;

    private Date toDate;

    private String imageUrl;

    @ManyToOne
    private Country country;

    @OneToMany(mappedBy = "competition")
    private List<CompetitionTeam> competitionTeam;

    public Competition() {
    }

    public Competition(String name, Date fromDate, Date toDate, String imageUrl, Country country, List<CompetitionTeam> competitionTeam) {
        this.name = name;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.imageUrl = imageUrl;
        this.country = country;
        this.competitionTeam = competitionTeam;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public List<CompetitionTeam> getCompetitionTeam() {
        return competitionTeam;
    }

    public void setCompetitionTeams(List<CompetitionTeam> competitionTeams) {
        this.competitionTeam = competitionTeam;
    }
}
