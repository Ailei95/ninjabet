package io.ninjabet.football.entity;

import io.ninjabet.core.entity.DeleteManagerEntity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CompetitionTeamKey extends DeleteManagerEntity implements Serializable {
    @Column
    private Long competitionId;

    @Column
    private Long teamId;

    public CompetitionTeamKey() {
    }

    public CompetitionTeamKey(Long competitionId, Long teamId) {
        this.competitionId = competitionId;
        this.teamId = teamId;
    }

    public Long getCompetitionId() {
        return competitionId;
    }

    public void setCompetitionId(Long competitionId) {
        this.competitionId = competitionId;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompetitionTeamKey that = (CompetitionTeamKey) o;
        return Objects.equals(competitionId, that.competitionId) &&
                Objects.equals(teamId, that.teamId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(competitionId, teamId);
    }
}
