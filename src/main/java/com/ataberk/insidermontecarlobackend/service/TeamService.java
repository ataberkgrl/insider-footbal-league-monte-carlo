package com.ataberk.insidermontecarlobackend.service;

import com.ataberk.insidermontecarlobackend.entity.Team;
import com.ataberk.insidermontecarlobackend.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamService {
    private final TeamRepository teamRepository;

    @Autowired
    TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

}
