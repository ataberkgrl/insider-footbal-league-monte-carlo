package com.ataberk.insidermontecarlobackend.controller;

import com.ataberk.insidermontecarlobackend.entity.Match;
import com.ataberk.insidermontecarlobackend.entity.Season;
import com.ataberk.insidermontecarlobackend.service.MatchService;
import com.ataberk.insidermontecarlobackend.service.SeasonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {
    private final SeasonService seasonService;
    private final MatchService matchService;

    @Autowired
    public MainController(SeasonService seasonService, MatchService matchService) {
        this.seasonService = seasonService;
        this.matchService = matchService;
    }

    @GetMapping("/")
    public String index(Model model) {
        Season lastSeason = seasonService.getLastSeason();
        List<Match> currentWeekMatches = null;

        if (lastSeason != null) {
            currentWeekMatches = matchService.getMatchesBySeasonAndWeek(lastSeason,
                    lastSeason.getCurrentWeek());
        }

        model.addAttribute("season", lastSeason);
        model.addAttribute("currentWeekMatches",currentWeekMatches);

        return "index";
    }

    @PostMapping("/season")
    public String createSeason() {
        seasonService.createSeason();

        return "redirect:/";
    }

    @GetMapping("/season")
    public String getSeasons(Model model) {
        List<Season> seasons = seasonService.getAllSeasons();
        model.addAttribute("seasonList", seasons);

        return "seasons";
    }

    @GetMapping("/season/{id}")
    public String getSeasonDetails(Model model, @PathVariable Long id) {
        Season season = seasonService.getSeasonById(id);
        List<Match> matchesPlayedOrderedByWeek = matchService.getMatchesBySeasonAndIsPlayedTrueOrderByWeekAsc(season);

        model.addAttribute("matchesPlayed", matchesPlayedOrderedByWeek);
        model.addAttribute("season", season);

        return "season_details";
    }

    @PostMapping("/season/advance-week")
    public String advanceWeek() {
        seasonService.advanceCurrentSeasonOneWeek();

        return "redirect:/";
    }

    @PostMapping("/season/advance-season")
    public String advanceSeason() {
        seasonService.advanceCurrentSeason();

        return "redirect:/";
    }
}
