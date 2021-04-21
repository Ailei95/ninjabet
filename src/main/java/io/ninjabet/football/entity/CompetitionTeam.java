package io.ninjabet.football.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class CompetitionTeam extends DeleteManagerEntity implements Serializable, AbstractEntity<CompetitionTeamKey> {

    @EmbeddedId
    CompetitionTeamKey id;

    @JsonIgnore
    @ManyToOne
    @MapsId("competitionId")
    Competition competition;

    @JsonIgnore
    @ManyToOne
    @MapsId("teamId")
    Team team;

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

    public Competition getCompetition() {
        return competition;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
