package com.theh.moduleuser.Services;

import java.util.Map;

public interface StatistiqueService {

    Map<String, Long> getMosquesByLocation();
    Map<String, Double> getAvgMosqueSizeByQuartier();
    Map<String, Map<String, Long>> getMosquesByFridayStatus();
    Map<String, Double> getAvgMosquesByLocation();
    Map<String, Long> getSermonsByMosque();
    Map<String, Long> getPredicationsByType();
    Map<String, Long> getTopThemes();
    Map<String, Long> getUsersByAccountType();
    Map<String, Long> getUserRegistrationsByMonth();
    Map<String, Long> getUsersByLocation();
}
