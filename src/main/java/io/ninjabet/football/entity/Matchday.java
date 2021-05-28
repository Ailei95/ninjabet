package io.ninjabet.football.entity;

import io.ninjabet.core.entity.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Entity(name = "MATCHDAYS")
public class Matchday implements Serializable, AbstractEntity<Long> {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    private LocalDateTime fromDate;

    private LocalDateTime toDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Competition competition;

    public Matchday() {
    }

    public Matchday(String name, LocalDateTime fromDate, LocalDateTime toDate, Competition competition) {
        this.name = name;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.competition = competition;
    }

    @Override
    public Long getId() {
        return id;
    }
}
