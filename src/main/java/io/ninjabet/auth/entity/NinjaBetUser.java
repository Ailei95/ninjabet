package io.ninjabet.auth.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name = "USERS")
public class NinjaBetUser {

    @Id
    private String email;

    @Column(nullable = false)
    private String password;

    private LocalDateTime registrationDate;

    private LocalDateTime lastPasswordChangeDate;

    private LocalDateTime lastLoginDate;

    private boolean admin;

    private boolean verified;
}
