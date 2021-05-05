package io.ninjabet.football.entity.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompetitionTeamDto {

    private CompetitionDto competition;

    private TeamDto team;
}
