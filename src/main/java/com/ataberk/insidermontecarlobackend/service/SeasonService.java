package com.ataberk.insidermontecarlobackend.service;

import com.ataberk.insidermontecarlobackend.entity.Match;
import com.ataberk.insidermontecarlobackend.entity.Season;
import com.ataberk.insidermontecarlobackend.entity.Team;
import com.ataberk.insidermontecarlobackend.entity.TeamStanding;
import com.ataberk.insidermontecarlobackend.repository.MatchRepository;
import com.ataberk.insidermontecarlobackend.repository.SeasonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SeasonService {
    private final SeasonRepository seasonRepository;
    private final TeamStandingService teamStandingService;
    private final MatchService matchService;

    @Autowired
    public SeasonService(SeasonRepository seasonRepository, TeamStandingService teamStandingService,
                         MatchService matchService) {
        this.seasonRepository = seasonRepository;
        this.teamStandingService = teamStandingService;
        this.matchService = matchService;
    }

    public void createSeason() {
        Season season = new Season();

        List<TeamStanding> teamStandings = teamStandingService.createTeamStandingsForSeason(season);
        season.setTeamStandings(teamStandings);

        calculateSeasonFixtureAndCreateMatches(season);

        seasonRepository.save(season);
    }

    private void calculateSeasonFixtureAndCreateMatches(Season season) {
        List<Team> teams = season.getTeamStandings().stream().map(TeamStanding::getTeam).collect(Collectors.toList());

        //SHOULD HAVE USED A ROUND ROBIN ALGORITHM BUT DIDN'T HAVE TIME

        matchService.saveMatch(new Match(season, 1, teams.get(0), teams.get(3)));
        matchService.saveMatch(new Match(season, 1, teams.get(1), teams.get(2)));

        matchService.saveMatch(new Match(season, 2, teams.get(3), teams.get(2)));
        matchService.saveMatch(new Match(season, 2, teams.get(0), teams.get(1)));

        matchService.saveMatch(new Match(season, 3, teams.get(1), teams.get(3)));
        matchService.saveMatch(new Match(season, 3, teams.get(2), teams.get(0)));

        matchService.saveMatch(new Match(season, 4, teams.get(3), teams.get(0)));
        matchService.saveMatch(new Match(season, 4, teams.get(2), teams.get(1)));

        matchService.saveMatch(new Match(season, 5, teams.get(2), teams.get(3)));
        matchService.saveMatch(new Match(season, 5, teams.get(1), teams.get(0)));

        matchService.saveMatch(new Match(season, 6, teams.get(3), teams.get(1)));
        matchService.saveMatch(new Match(season, 6, teams.get(0), teams.get(2)));
    }

    public Season getActiveSeason() {
        return seasonRepository.findSeasonByIsActive(true);
    }

    public Season getLastSeason() {
        return seasonRepository.findFirstByOrderByIdDesc();
    }

    public void advanceCurrentSeasonOneWeek() {
        Season activeSeason = getActiveSeason();
        int currentWeek = activeSeason.getCurrentWeek();
        int numOfTeamsInSeason = activeSeason.getTeamStandings().size();

        List<Match> nextWeekMatches = matchService.getMatchesBySeasonAndWeek(activeSeason,
                currentWeek + 1);

        nextWeekMatches.forEach(match -> {
            match = matchService.simulateMatch(match);

            int homeScore = match.getHomeScore();
            int awayScore = match.getAwayScore();

            TeamStanding homeTeamStanding = teamStandingService.getTeamStandingByTeamAndBySeason(match.getHomeTeam(), activeSeason);
            TeamStanding awayTeamStanding = teamStandingService.getTeamStandingByTeamAndBySeason(match.getAwayTeam(), activeSeason);

            homeTeamStanding.setGoalDifference(homeTeamStanding.getGoalDifference() + homeScore - awayScore);
            homeTeamStanding.setPlayedGame(homeTeamStanding.getPlayedGame() + 1);

            awayTeamStanding.setGoalDifference(homeTeamStanding.getGoalDifference() + awayScore - homeScore);
            awayTeamStanding.setPlayedGame(awayTeamStanding.getPlayedGame() + 1);

            if (homeScore > awayScore) {
                homeTeamStanding.setWinCount(homeTeamStanding.getWinCount() + 1);
                homeTeamStanding.setPoints(homeTeamStanding.getPoints() + 3);

                awayTeamStanding.setLoseCount(awayTeamStanding.getLoseCount() + 1);
            } else if (homeScore == awayScore) {
                homeTeamStanding.setDrawCount(homeTeamStanding.getDrawCount() + 1);
                homeTeamStanding.setPoints(homeTeamStanding.getPoints() + 1);

                awayTeamStanding.setDrawCount(awayTeamStanding.getDrawCount() + 1);
                awayTeamStanding.setPoints(awayTeamStanding.getPoints() + 1);
            } else {
                awayTeamStanding.setWinCount(awayTeamStanding.getWinCount() + 1);
                awayTeamStanding.setPoints(awayTeamStanding.getPoints() + 3);

                homeTeamStanding.setLoseCount(homeTeamStanding.getLoseCount() + 1);
            }

            teamStandingService.save(homeTeamStanding);
            teamStandingService.save(awayTeamStanding);
        });

        activeSeason.setCurrentWeek(currentWeek + 1);

        if ((numOfTeamsInSeason * (numOfTeamsInSeason - 1)) / 2 == currentWeek + 1) {
            activeSeason.setIsActive(false);
        }

        seasonRepository.save(activeSeason);
    }

    public List<Season> getAllSeasons() {
        return seasonRepository.findAll();
    }

    public Season getSeasonById(Long id) {
        return seasonRepository.getSeasonById(id);
    }

    public void advanceCurrentSeason() {
        Season activeSeason = getActiveSeason();

        while (activeSeason != null) {
            advanceCurrentSeasonOneWeek();
            activeSeason = getActiveSeason();
        }
    }
}
