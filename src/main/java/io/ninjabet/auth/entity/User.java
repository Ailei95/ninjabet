package io.ninjabet.auth.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity(name = "USERS")
public class User {

    @Id
    private String email;

    @Column(nullable = false)
    private String password;

    private Date registrationDate;

    private boolean admin;

    public User() {
    }

    public User(String email, String password, Date registrationDate, boolean admin) {
        this.email = email;
        this.password = password;
        this.registrationDate = registrationDate;
        this.admin = admin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
}
