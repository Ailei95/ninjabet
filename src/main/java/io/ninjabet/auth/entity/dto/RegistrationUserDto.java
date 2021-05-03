package io.ninjabet.auth.entity.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationUserDto {

    private String email;

    private String password;
}
