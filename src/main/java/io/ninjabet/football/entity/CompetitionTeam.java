package io.ninjabet.football.entity;

import io.ninjabet.core.entity.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
public class CompetitionTeam implements Serializable, AbstractEntity<CompetitionTeamKey> {

    @EmbeddedId
    private CompetitionTeamKey id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("competitionId")
    private Competition competition;

    @ManyToOne(fetch = FetchType.LAZY)
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
}
