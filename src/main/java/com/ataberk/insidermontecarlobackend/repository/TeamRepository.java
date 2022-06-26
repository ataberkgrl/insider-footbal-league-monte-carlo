package com.ataberk.insidermontecarlobackend.repository;

import com.ataberk.insidermontecarlobackend.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
