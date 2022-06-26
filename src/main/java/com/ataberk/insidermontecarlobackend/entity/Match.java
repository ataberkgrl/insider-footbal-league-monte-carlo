package com.ataberk.insidermontecarlobackend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Match {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "season_id", referencedColumnName = "id")
    private Season season;

    private int week;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "home_team_id", referencedColumnName = "id")
    private Team homeTeam;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "away_team_id", referencedColumnName = "id")
    private Team awayTeam;

    private int homeScore;
    private int awayScore;

    @Column(columnDefinition = "boolean default false")
    private boolean isPlayed = false;

    public Match(Season season, int week, Team homeTeam, Team awayTeam) {
        this.season = season;
        this.week = week;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
    }
}
