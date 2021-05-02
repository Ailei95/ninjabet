package io.ninjabet.football.entity.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class MatchdayDto {

    private Long id;

    private String name;

    private Date fromDate;

    private Date toDate;

    private Long competitionId;
}
