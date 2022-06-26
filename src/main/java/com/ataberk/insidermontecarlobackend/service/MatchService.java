package com.ataberk.insidermontecarlobackend.service;

import com.ataberk.insidermontecarlobackend.entity.Match;
import com.ataberk.insidermontecarlobackend.entity.Season;
import com.ataberk.insidermontecarlobackend.entity.Team;
import com.ataberk.insidermontecarlobackend.entity.TeamStats;
import com.ataberk.insidermontecarlobackend.repository.MatchRepository;
import com.ataberk.insidermontecarlobackend.repository.TeamStatsRepository;
import org.apache.commons.math3.distribution.PoissonDistribution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatchService {

    private final MatchRepository matchRepository;
    private final TeamStatsService teamStatsService;

    @Autowired
    MatchService(MatchRepository matchRepository, TeamStatsService teamStatsService) {
        this.matchRepository = matchRepository;
        this.teamStatsService = teamStatsService;
    }

    public List<Match> getMatchesBySeasonAndWeek(Season season, int week) {
        return matchRepository.getMatchesBySeasonAndWeek(season, week);
    }

    public List<Match> getMatchesBySeasonAndIsPlayedTrueOrderByWeekAsc(Season season) {
        return matchRepository.getMatchesBySeasonAndIsPlayedTrueOrderByWeekAsc(season);
    }

    public void saveMatch(Match match) {
        matchRepository.save(match);
    }

    public Match simulateMatch(Match match) {
        Team awayTeam = match.getAwayTeam();
        Team homeTeam = match.getHomeTeam();

        TeamStats awayTeamStats = teamStatsService.findTeamStatsByTeam(awayTeam);
        TeamStats homeTeamStats = teamStatsService.findTeamStatsByTeam(homeTeam);

        float homeTeamExpectedScore = (homeTeamStats.getAverageGoalsScored() + awayTeamStats.getAverageGoalsConceeded()) / 2;
        float awayTeamExpectedScore = (awayTeamStats.getAverageGoalsScored() + homeTeamStats.getAverageGoalsConceeded()) / 2;

        PoissonDistribution homeTeamPoissonDistribution = new PoissonDistribution(homeTeamExpectedScore, 10000);
        PoissonDistribution awayTeamPoissonDistribution = new PoissonDistribution(awayTeamExpectedScore, 10000);

        int homeScore = homeTeamPoissonDistribution.sample();
        int awayScore = awayTeamPoissonDistribution.sample();

        match.setHomeScore(homeScore);
        match.setAwayScore(awayScore);
        match.setPlayed(true);

        matchRepository.save(match);

        return match;
    }
}
