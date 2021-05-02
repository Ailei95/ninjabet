package io.ninjabet.football.entity.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResultDto {

    private Long matchId;

    private Integer homeScore;

    private Integer guestScore;

    private String status;
}
