package io.ninjabet.football.entity;

import io.ninjabet.core.entity.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity(name = "COUNTRIES")
public class Country implements Serializable, AbstractEntity<Long> {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String isoCode;

    private String imageUrl;

    public Country() {
    }

    public Country(String name, String iso2Code, String imageUrl) {
        this.isoCode = iso2Code;
        this.name = name;
        this.imageUrl = imageUrl;
    }

    @Override
    public Long getId() {
        return id;
    }
}
