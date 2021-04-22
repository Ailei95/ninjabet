package io.ninjabet.football.entity;

import io.ninjabet.core.entity.AbstractEntity;
import io.ninjabet.core.entity.DeleteManagerEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Entity(name = "TEAMS")
public class Team extends DeleteManagerEntity implements Serializable, AbstractEntity<Long> {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    private String imageUrl;

    @OneToMany(mappedBy = "team")
    private List<CompetitionTeam> competitionTeam;

    public Team() {
    }

    public Team(String name, String imageUrl, List<CompetitionTeam> competitionTeam) {
        this.name = name;
        this.imageUrl = imageUrl;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<Long> getCompetitionsId() {
        return competitionTeam != null ?
                competitionTeam.stream().map(ct -> ct.getId().getCompetitionId()).collect(Collectors.toList()) : new LinkedList<>();
    }

    public void setCompetitionTeam(List<CompetitionTeam> competitionTeam) {
        this.competitionTeam = competitionTeam;
    }
}
