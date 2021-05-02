package io.ninjabet.football.entity.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CompetitionDto {

    private Long id;

    private String name;

    private Date fromDate;

    private Date toDate;

    private String imageUrl;

    private Long countryId;
}
