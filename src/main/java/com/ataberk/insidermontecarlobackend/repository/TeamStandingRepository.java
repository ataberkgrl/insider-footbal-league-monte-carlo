package com.ataberk.insidermontecarlobackend.repository;

import com.ataberk.insidermontecarlobackend.entity.Season;
import com.ataberk.insidermontecarlobackend.entity.Team;
import com.ataberk.insidermontecarlobackend.entity.TeamStanding;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamStandingRepository extends JpaRepository<TeamStanding, Long> {
    TeamStanding getTeamStandingByTeamAndSeason(Team team, Season season);
}
