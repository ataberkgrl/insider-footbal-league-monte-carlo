package com.ataberk.insidermontecarlobackend.repository;

import com.ataberk.insidermontecarlobackend.entity.TeamStats;
import com.ataberk.insidermontecarlobackend.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamStatsRepository extends JpaRepository<TeamStats, Long> {
    TeamStats findTeamStatsByTeam(Team team);
}
