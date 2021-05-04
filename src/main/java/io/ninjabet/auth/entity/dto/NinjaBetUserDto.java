package io.ninjabet.auth.entity.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class NinjaBetUserDto {

    private String email;

    private Date registrationDate;
}
