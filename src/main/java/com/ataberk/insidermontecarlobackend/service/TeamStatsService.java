package com.ataberk.insidermontecarlobackend.service;

import com.ataberk.insidermontecarlobackend.entity.Team;
import com.ataberk.insidermontecarlobackend.entity.TeamStats;
import com.ataberk.insidermontecarlobackend.repository.TeamStatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamStatsService {

    private final TeamStatsRepository teamStatsRepository;

    @Autowired
    TeamStatsService(TeamStatsRepository teamStatsRepository) {
        this.teamStatsRepository = teamStatsRepository;
    }

    public TeamStats findTeamStatsByTeam(Team team) {
        return teamStatsRepository.findTeamStatsByTeam(team);
    }
}
