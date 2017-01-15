package main.java.healthkeeper.predictserver.controllers;

public class HealthKeeperGlobals {
    public static final int LOWER_BOUND_SECONDS = 60 * 60 * 2;
    public static final int UPPER_BOUND_SECONDS = 60 * 60 * 1;
    public static final int MEASURES_COUNT      = 1;
    public static final int TIME_PROBE_SCONDS   = 
            (LOWER_BOUND_SECONDS - UPPER_BOUND_SECONDS) / MEASURES_COUNT;
}
