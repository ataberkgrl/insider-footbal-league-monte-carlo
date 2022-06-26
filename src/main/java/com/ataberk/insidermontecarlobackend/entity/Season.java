package com.ataberk.insidermontecarlobackend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.bytebuddy.build.HashCodeAndEqualsPlugin;

import javax.persistence.*;
import java.time.Year;
import java.util.List;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Season {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "int default 0")
    private int currentWeek;

    @Column(columnDefinition = "boolean default true")
    private Boolean isActive = true;

    @OneToMany(cascade = CascadeType.ALL)
    private List<TeamStanding> teamStandings;
}
