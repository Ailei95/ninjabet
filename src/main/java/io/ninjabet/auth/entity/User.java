package io.ninjabet.auth.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "USERS")
public class User {

    @Id
    private String email;

    @Column(nullable = false)
    private String password;

    private boolean admin;

    public User() {
    }

    public User(String email, String password, boolean admin) {
        this.email = email;
        this.password = password;
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

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
}
