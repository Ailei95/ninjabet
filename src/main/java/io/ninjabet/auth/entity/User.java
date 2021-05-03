package io.ninjabet.auth.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name = "USERS")
public class User {

    @Id
    private String email;

    @Column(nullable = false)
    private String password;

    private Date registrationDate;

    private Date lastPasswordChangeDate;

    private boolean admin;

    private boolean verify;
}
