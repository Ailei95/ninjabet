package io.ninjabet.football.entity.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TeamDto {

    private Long id;

    private String name;

    private String imageUrl;

    private List<Long> competitionsId;
}
