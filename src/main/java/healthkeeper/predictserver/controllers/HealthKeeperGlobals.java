package main.java.healthkeeper.predictserver.controllers;

public class HealthKeeperGlobals {
    public static final int LOWER_BOUND_SECONDS = 60 * 60 * 2;
    public static final int UPPER_BOUND_SECONDS = 60 * 60 * 1;
    public static final int MEASURES_COUNT      = 4;
    public static final int TIME_PROBE_SCONDS   = 
            (UPPER_BOUND_SECONDS - LOWER_BOUND_SECONDS + 1) / MEASURES_COUNT;
}
