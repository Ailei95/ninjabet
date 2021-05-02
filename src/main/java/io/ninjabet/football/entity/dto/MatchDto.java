package io.ninjabet.football.entity.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class MatchDto {

    private  Long id;

    private Date date;

    private Long homeId;

    private Long guestId;

    private Long matchdayId;
}
