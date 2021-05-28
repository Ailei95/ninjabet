package io.ninjabet.auth.entity.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class NinjaBetUserDto {

    private String email;

    private List<String> authorities;

    private String imageUrl;
}
