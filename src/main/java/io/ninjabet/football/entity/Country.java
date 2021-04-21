package io.ninjabet.football.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "COUNTRIES")
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = {"iso2Code", "deleteDate"}) })
public class Country extends DeleteManagerEntity implements Serializable, AbstractEntity<Long> {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String iso2Code;

    private String imageUrl;

    public Country() {
    }

    public Country(String name, String iso2Code, String imageUrl) {
        this.iso2Code = iso2Code;
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

    public String getIso2Code() {
        return iso2Code;
    }

    public void setIso2Code(String iso2Code) {
        this.iso2Code = iso2Code;
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
