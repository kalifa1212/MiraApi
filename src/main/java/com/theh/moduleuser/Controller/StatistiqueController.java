package com.theh.moduleuser.Controller;

import com.theh.moduleuser.Services.StatistiqueService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import static com.theh.moduleuser.Constant.Constants.STATISTIQUE_ENDPOINT;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(STATISTIQUE_ENDPOINT)
@SecurityRequirement(name = "Bearer Authentication")
public class StatistiqueController {

    @Autowired
    private StatistiqueService statsService;

    @GetMapping("/mosquees/localisation")
    public Map<String, Long> getMosquesByLocation() {
        return statsService.getMosquesByLocation();
    }

    @GetMapping("/mosquees/superficie")
    public Map<String, Double> getAvgMosqueSizeByQuartier() {
        return statsService.getAvgMosqueSizeByQuartier();
    }

    @GetMapping("/mosquees/vendredi")
    public Map<String, Map<String, Long>> getMosquesByFridayStatus() {
        return statsService.getMosquesByFridayStatus();
    }

    @GetMapping("/mosquees/ville")
    public Map<String, Double> getAvgMosquesByLocation() {
        return statsService.getAvgMosquesByLocation();
    }

    @GetMapping("/predications/mosquee")// Predication par mosque ou il ya plus de preche
    public Map<String, Long> getSermonsByMosque() {
        return statsService.getSermonsByMosque();
    }

    @GetMapping("/predications/type")
    public Map<String, Long> getPredicationsByType() {
        return statsService.getPredicationsByType();
    }

    @GetMapping("/predications/themes")
    public Map<String, Long> getTopThemes() {
        return statsService.getTopThemes();
    }

    @GetMapping("/utilisateurs/typecompte")
    public Map<String, Long> getUsersByAccountType() {
        return statsService.getUsersByAccountType();
    }

    @GetMapping("/utilisateurs/inscriptions")
    public Map<String, Long> getUserRegistrationsByMonth() {
        return statsService.getUserRegistrationsByMonth();
    }

    @GetMapping("/utilisateurs/localisation")
    public Map<String, Long> getUsersByLocation() {
        return statsService.getUsersByLocation();
    }

}
