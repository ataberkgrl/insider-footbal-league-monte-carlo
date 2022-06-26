package com.ataberk.insidermontecarlobackend.repository;

import com.ataberk.insidermontecarlobackend.entity.Season;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Year;

public interface SeasonRepository extends JpaRepository<Season, Long> {
    Season findSeasonByIsActive(boolean isActive);
    Season findFirstByOrderByIdDesc();
    Season getSeasonById(Long id);
}
