package io.ninjabet.football.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Competition {

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

    @ManyToMany
    private List<Team> teams;

    public Competition() {
    }

    public Competition(String name, Date fromDate, Date toDate, String imageUrl, Country country, List<Team> teams) {
        this.name = name;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.imageUrl = imageUrl;
        this.country = country;
        this.teams = teams;
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getFromDate() { return fromDate; }

    public void setFromDate(Date fromDate) { this.fromDate = fromDate; }

    public Date getToDate() { return toDate; }

    public void setToDate(Date toDate) { this.toDate = toDate; }

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

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }
}
