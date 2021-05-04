package io.ninjabet.football.entity;

import io.ninjabet.core.entity.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity(name = "COMPETITIONS")
public class Competition implements Serializable, AbstractEntity<Long> {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    private Date fromDate;

    private Date toDate;

    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
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
}
