package io.ninjabet.football.entity.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CountryDto {
    private Long id;

    private String name;

    private String isoCode;

    private String imageUrl;
}
