package com.ataberk.insidermontecarlobackend.service;

import com.ataberk.insidermontecarlobackend.entity.Season;
import com.ataberk.insidermontecarlobackend.entity.Team;
import com.ataberk.insidermontecarlobackend.entity.TeamStanding;
import com.ataberk.insidermontecarlobackend.repository.TeamStandingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeamStandingService {
    private final TeamStandingRepository teamStandingRepository;
    private final TeamService teamService;
    
    @Autowired
    TeamStandingService(TeamStandingRepository teamStandingRepository, TeamService teamService) {
        this.teamStandingRepository = teamStandingRepository;
        this.teamService = teamService;
    }
    
    public List<TeamStanding> createTeamStandingsForSeason(Season season) {
        List<Team> teams = teamService.getAllTeams();
        List<TeamStanding> teamStandings = new ArrayList<>();
        
        teams.forEach(team -> {
            TeamStanding teamStanding = new TeamStanding();
            teamStanding.setTeam(team);
            teamStanding.setSeason(season);
            teamStandings.add(teamStanding);
            teamStandingRepository.save((teamStanding));
        });

        return teamStandings;
    }

    public TeamStanding getTeamStandingByTeamAndBySeason(Team team, Season season) {
        return teamStandingRepository.getTeamStandingByTeamAndSeason(team, season);
    }

    public void save(TeamStanding teamStanding) {
        teamStandingRepository.save(teamStanding);
    }
}
