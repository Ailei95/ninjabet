package io.ninjabet.football.entity;

import io.ninjabet.core.entity.AbstractEntity;

import javax.persistence.*;
import java.io.Serializable;

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

    public void setId(Long id) {
        this.id = id;
    }

    public String getIsoCode() {
        return isoCode;
    }

    public void setIsoCode(String isoCode) {
        this.isoCode = isoCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
