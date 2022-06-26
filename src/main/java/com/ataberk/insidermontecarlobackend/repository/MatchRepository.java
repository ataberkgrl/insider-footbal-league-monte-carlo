package com.ataberk.insidermontecarlobackend.repository;

import com.ataberk.insidermontecarlobackend.entity.Match;
import com.ataberk.insidermontecarlobackend.entity.Season;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {
    List<Match> getMatchesBySeasonAndWeek(Season season, int week);
    List<Match> getMatchesBySeasonAndIsPlayedTrueOrderByWeekAsc(Season season);
}
