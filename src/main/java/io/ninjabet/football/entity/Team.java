package io.ninjabet.football.entity;

import io.ninjabet.core.entity.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity(name = "TEAMS")
public class Team implements Serializable, AbstractEntity<Long> {

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

    public List<Long> getCompetitionsId() {
        return competitionTeam != null ?
                competitionTeam.stream().map(ct -> ct.getId().getCompetitionId()).collect(Collectors.toList()) : new LinkedList<>();
    }
}
