package ru.practicum.explorewithme.client;

import java.time.format.DateTimeFormatter;

public class StatsUtils {
    public static final String DT_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final DateTimeFormatter DT_FORMATTER = DateTimeFormatter.ofPattern(DT_FORMAT);
    public static final String HIT_ENDPOINT = "/hit";
    public static final String STATS_ENDPOINT = "/stats";
    static final String STATS_URL = "http://stats-server:9090";

    //static final String STATS_URL = "http://localhost:9090";
}
