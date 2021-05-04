package io.ninjabet.securety.role;

import com.google.common.collect.Sets;
import io.ninjabet.securety.permission.NinjaBetFootballPermission;

import static io.ninjabet.securety.permission.NinjaBetFootballPermission.*;

import java.util.Set;

public enum NinjaBetRole {
    USER(Sets.newHashSet(COMPETITION_READ, COMPETITION_TEAM_READ, COUNTRY_READ, MATCH_READ, MATCHDAY_READ, RESULT_READ, TEAM_READ)),
    ADMIN(Sets.newHashSet(COMPETITION_READ, COMPETITION_TEAM_READ, COUNTRY_READ, MATCH_READ, MATCHDAY_READ, RESULT_READ, TEAM_READ,
            COMPETITION_WRITE, COMPETITION_TEAM_WRITE, COUNTY_WRITE, MATCH_WRITE, MATCHDAY_WRITE, RESULT_WRITE, TEAM_WRITE));

    private final Set<NinjaBetFootballPermission> footballPermissions;

    NinjaBetRole(Set<NinjaBetFootballPermission> footballPermissions) {
        this.footballPermissions = footballPermissions;
    }

    public Set<NinjaBetFootballPermission> getFootballPermissions() {
        return footballPermissions;
    }
}
