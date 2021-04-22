package io.ninjabet.football.entity;

import io.ninjabet.core.entity.AbstractEntity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class CompetitionTeam implements Serializable, AbstractEntity<CompetitionTeamKey> {

    @EmbeddedId
    private CompetitionTeamKey id;

    @Transient
    public Long competitionId;

    @ManyToOne
    @MapsId("competitionId")
    private Competition competition;

    @Transient
    public Long teamId;

    @ManyToOne
    @MapsId("teamId")
    private Team team;

    public CompetitionTeam() {
    }

    public CompetitionTeam(Competition competition, Team team) {
        this.competition = competition;
        this.team = team;
    }

    @Override
    public CompetitionTeamKey getId() {
        return id;
    }

    public void setId(CompetitionTeamKey id) {
        this.id = id;
    }

    public Long getCompetitionId() {
        return competition.getId();
    }

    public void setCompetitionId(Long competitionId) {
        this.competitionId = competitionId;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }

    public Long getTeamId() {
        return team.getId();
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
