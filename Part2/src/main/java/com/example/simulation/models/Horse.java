package com.example.simulation.models;

public class Horse {
    private String name;
    private String breed;
    private String coatColour;
    private double distanceTravelled;
    private boolean hasFallen;
    private double horseConfidence;
    private int totalRaceWon;
    private double winningOdds;

    public Horse(String name, String breed, String coatColour) {
        this.name = name;
        this.breed = breed;
        this.coatColour = coatColour;
        this.distanceTravelled = 0;
        this.hasFallen = false;
        this.horseConfidence = Math.round((0.5 + Math.random() * 0.5) * 10.0) / 10.0;
        this.totalRaceWon = 0;
        this.winningOdds = 20;
    }

    public double getWinningOdds() {
        return this.winningOdds;
    }

    public void setWinningOdds(double winningOdds) {
        this.winningOdds = winningOdds;
    }

    public int getTotalRaceWon() {
        return this.totalRaceWon;
    }

    public void setTotalRaceWon(int totalRaceWon) {
        this.totalRaceWon = totalRaceWon;
    }

    public void incrementTotalRaceWon() {
        this.totalRaceWon++;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBreed() {
        return this.breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getCoatColour() {
        return this.coatColour;
    }

    public void setCoatColour(String coatColour) {
        this.coatColour = coatColour;
    }

    public double getDistanceTravelled() {
        return this.distanceTravelled;
    }

    public void setDistanceTravelled(double distanceTravelled) {
        this.distanceTravelled = distanceTravelled;
    }

    public boolean isHasFallen() {
        return this.hasFallen;
    }

    public void setHasFallen(boolean hasFallen) {
        this.hasFallen = hasFallen;
    }

    public double getHorseConfidence() {
        return this.horseConfidence;
    }

    public void setHorseConfidence(double horseConfidence) {
        this.horseConfidence = horseConfidence;
    }

    public void moveForward(double factor) {
        if (!isHasFallen()) {
            distanceTravelled += horseConfidence * factor;
        }
    }

    public void updateWinningOdds() {
        if (totalRaceWon == 0) {
            winningOdds = 20;
        } else {
            double averageRank = (double) totalRaceWon / (totalRaceWon + 1);
            winningOdds = Math.round((1.0 / averageRank) * 100 * 100.0) / 100.0;
            if (winningOdds > 90) {
                winningOdds = 90;
            }
        }
    }

    @Override
    public String toString() {
        return this.name;
    }
}