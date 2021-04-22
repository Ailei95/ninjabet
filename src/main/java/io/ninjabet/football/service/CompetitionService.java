package io.ninjabet.football.service;

import io.ninjabet.auth.service.UserService;
import io.ninjabet.core.service.DeleteLoggerDMCrudService;
import io.ninjabet.football.entity.*;
import io.ninjabet.football.repository.CompetitionRepository;
import io.ninjabet.core.repository.ActionLoggerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class CompetitionService extends DeleteLoggerDMCrudService<Competition, Long, CompetitionRepository> {

    private final CountryService countryService;
    private final CompetitionTeamService competitionTeamService;
    private final TeamService teamService;

    @Autowired
    public CompetitionService(
            ActionLoggerRepository actionLoggerRepository,
            CompetitionRepository competitionRepository,
            CountryService countryService,
            TeamService teamService,
            CompetitionTeamService competitionTeamService,
            UserService userService
    ) {
        super(Competition.class, actionLoggerRepository, competitionRepository, userService);

        this.countryService = countryService;
        this.competitionTeamService = competitionTeamService;
        this.teamService = teamService;
    }

    public Iterable<Competition> findByCountry(Long countryId) {
        Optional<Country> localCountry = this.countryService.findById(countryId);

        if (!localCountry.isPresent()) { return new LinkedList<>(); }

        return this.crudRepository.findByCountry(localCountry.get());
    }

    @Transactional
    @Override
    public Optional<Competition> update(Long id, Competition competition) {
        if (competition.countryId != null) {
            Optional<Country> localCountry = this.countryService.findById(competition.countryId);

            if (!localCountry.isPresent()) { return Optional.empty(); }

            competition.setCountry(localCountry.get());
        }

        if (competition.teamsId != null) {
            List<CompetitionTeam> localCompetitionTeams = new LinkedList<>();

            for (Long teamId : competition.teamsId) {
                Optional<Team> localTeam = this.teamService.findById(teamId);

                if (!localTeam.isPresent())
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Team not found: " + teamId);

                CompetitionTeam temp = new CompetitionTeam();

                temp.setId(new CompetitionTeamKey(competition.getId(), teamId));

                temp.setCompetition(competition);

                temp.setTeam(localTeam.get());

                CompetitionTeam localCompetitionTeam = this.competitionTeamService.add(temp);

                localCompetitionTeams.add(localCompetitionTeam);
            }

            competition.setCompetitionTeam(localCompetitionTeams);
        }

        return super.update(id, competition);
    }
}
