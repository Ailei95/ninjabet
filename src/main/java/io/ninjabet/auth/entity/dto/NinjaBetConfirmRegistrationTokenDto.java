package io.ninjabet.auth.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class NinjaBetConfirmRegistrationTokenDto {

    private LocalDateTime createdAt;

    private LocalDateTime expiresAt;

    @JsonIgnore
    private NinjaBetUserDto user;

    public String getEmail() { return user.getEmail(); }
}
