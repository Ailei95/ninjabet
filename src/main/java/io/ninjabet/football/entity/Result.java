package io.ninjabet.football.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.ninjabet.core.entity.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity(name = "RESULTS")
public class Result implements Serializable, AbstractEntity<Long> {

    @Id
    private Long matchId;

    @OneToOne
    @MapsId
    private Match match;

    private Integer homeScore;

    private Integer guestScore;

    private String status;

    public Result() {
    }

    public Result(Match match, Integer homeScore, Integer guestScore, String status) {
        this.match = match;
        this.homeScore = homeScore;
        this.guestScore = guestScore;
        this.status = status;
    }

    @JsonIgnore
    @Override
    public Long getId() { return this.matchId; }
}
