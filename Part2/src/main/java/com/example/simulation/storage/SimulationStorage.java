package com.example.simulation.storage;

import com.example.simulation.models.Horse;

import java.util.LinkedList;
import java.util.List;

public class SimulationStorage {
    private static final List<Horse> horses = new LinkedList<>();
    private static int maxRaceNumber = 0;

    private static final String[] coatColours = {"lavender", "black", "brown", "purple", "orange", "yellow", "green", "blue", "red"};
    private static final String[] raceDistance = {"500", "400", "300", "200", "100"};

    static {
        horses.add(new Horse("PIPPI", "Thoroughbred", "red"));
        horses.add(new Horse("KOKOMO", "Quarter Horse", "blue"));
        horses.add(new Horse("EL JEFE", "Arabian", "green"));
    }

    public static String[] getCoatColours() {
        return coatColours;
    }

    public static String[] getRaceDistance() {
        return raceDistance;
    }

    public static List<Horse> getHorses() {
        return new LinkedList<>(horses);
    }

    public static void addHorse(Horse horse) {
        horses.add(horse);
    }

    public static void deleteHorse(String name) {
        horses.removeIf(horse -> horse.getName().equalsIgnoreCase(name));
    }

}