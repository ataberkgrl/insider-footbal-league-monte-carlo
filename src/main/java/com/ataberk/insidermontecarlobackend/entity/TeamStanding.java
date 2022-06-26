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
public class TeamStanding {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", referencedColumnName = "id")
    private Team team;

    @ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name = "season_id", referencedColumnName = "id")
    private Season season;

    @Column(columnDefinition = "int default 0")
    private int points;

    @Column(columnDefinition = "int default 0")
    private int playedGame;

    @Column(columnDefinition = "int default 0")
    private int winCount;

    @Column(columnDefinition = "int default 0")
    private int drawCount;

    @Column(columnDefinition = "int default 0")
    private int loseCount;

    @Column(columnDefinition = "int default 0")
    private int goalDifference;
}
