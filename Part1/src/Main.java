public class Main {
    public static void main(String[] args) {
        Horse horseInstance1 = new Horse('P', "PIPPI LONGSTOCKING", 0.7);
        Horse horseInstance2 = new Horse('K', "KOKOMO", 0.5);
        Horse horseInstance3 = new Horse('E', "EL JEFE", 0.6);
        Horse horseInstance4 = new Horse('S', "SECRETARIAT", 0.8);

        int raceDistance = 30;
        int maxLaneNumber = 4;

        Race race = new Race(raceDistance, maxLaneNumber);

        race.addHorse(horseInstance1, 1);
        race.addHorse(horseInstance2, 2);
        race.addHorse(horseInstance3, 3);
        race.addHorse(horseInstance4, 4);

        race.startRace();
    }
}
