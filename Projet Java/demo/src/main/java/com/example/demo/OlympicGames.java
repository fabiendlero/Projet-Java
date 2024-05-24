package com.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.*;

class OlympicGames {
    String location;
    private ObservableList<Athlete> athletes = FXCollections.observableArrayList();
    private ObservableList<Event> events = FXCollections.observableArrayList();

    List<Result> results = new ArrayList<>();
    MedalTable medalTable = new MedalTable();
    private ObservableList<Sport> sportList = FXCollections.observableArrayList();

    public ObservableList<Sport> getSportList() {
        return sportList;
    }

    public ObservableList<Event> getEvents() {
        return events;
    }


    public ObservableList<Athlete> getAthletes() {
        return athletes;
    }


    public void addSport(Sport sport) {
        sportList.add(sport);
    }
    OlympicGames(String location) {
        this.location = location;
    }


    void addAthlete(Athlete athlete) {
        athletes.add(athlete);
    }

    public void removeAthlete(Athlete athlete) {
        athletes.remove(athlete);
    }

    void addEvent(Event event) {
        events.add(event);
    }

    void recordResult(Result result) {
        results.add(result);
        medalTable.updateMedals(result.athlete.getCountry(), result.medal);
    }


    void saveData() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("olympic_data.txt"))) {
            writer.println("Location: " + location);
            writer.println("Sports:");
            for (Sport sport : sportList) {
                writer.println(sport.name);
            }
            writer.println("Athletes:");
            for (Athlete athlete : athletes) {
                writer.println(athlete.getName() + "," + athlete.getCountry() + "," + athlete.getAge() + "," + athlete.getGender());
            }
            writer.println("Events:");
            for (Event event : events) {
                writer.println(event.name + "," + event.sport.name);
            }
            writer.println("Results:");
            for (Result result : results) {
                writer.println(result.athlete.getName() + "," + result.event.name + "," + result.position + "," + result.score + "," +
                        (result.medal != null ? result.medal.type.toString() : ""));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }




    public List<Result> getResults() {
        return results;
    }

    public static OlympicGames loadData() {
        OlympicGames olympicGames = new OlympicGames("New Olympic Games");
        try (Scanner scanner = new Scanner(new File("olympic_data.txt"))) {
            String currentSection = "";
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.startsWith("Location:")) {
                    olympicGames.location = line.substring(9).trim();
                } else if (line.equals("Sports:")) {
                    currentSection = "Sports";
                } else if (line.equals("Athletes:")) {
                    currentSection = "Athletes";
                } else if (line.equals("Events:")) {
                    currentSection = "Events";
                } else if (line.equals("Results:")) {
                    currentSection = "Results";
                } else {
                    switch (currentSection) {
                        case "Sports":
                            if (!line.isEmpty()) {
                                olympicGames.addSport(new Sport(line));
                            }
                            break;
                        case "Athletes":
                            if (!line.isEmpty()) {
                                String[] parts = line.split(",");
                                if (parts.length == 4) {
                                    String name = parts[0].trim();
                                    String country = parts[1].trim();
                                    int age = Integer.parseInt(parts[2].trim());
                                    String gender = parts[3].trim();
                                    olympicGames.addAthlete(new Athlete(name, country, age, gender));
                                }
                            }
                            break;
                        case "Events":
                            if (!line.isEmpty()) {
                                String[] parts = line.split(",");
                                if (parts.length == 2) {
                                    String eventName = parts[0].trim();
                                    String sportName = parts[1].trim();
                                    Sport sport = olympicGames.sportList.stream()
                                            .filter(s -> s.getName().equals(sportName))
                                            .findFirst()
                                            .orElse(null);
                                    if (sport != null) {
                                        olympicGames.addEvent(new Event(eventName, sport));
                                    }
                                }
                            }
                            break;
                        case "Results":
                            if (!line.isEmpty()) {
                                String[] parts = line.split(",");
                                if (parts.length >= 4) {
                                    String athleteName = parts[0].trim();
                                    String eventName = parts[1].trim();
                                    int position = Integer.parseInt(parts[2].trim());
                                    double score = Double.parseDouble(parts[3].trim());
                                    Medal medal = parts.length > 4 && !parts[4].isEmpty() ? new Medal(MedalType.valueOf(parts[4].trim()), 1) : null;
                                    Athlete athlete = olympicGames.athletes.stream()
                                            .filter(a -> a.getName().equals(athleteName))
                                            .findFirst()
                                            .orElse(null);
                                    Event event = olympicGames.events.stream()
                                            .filter(e -> e.getName().equals(eventName))
                                            .findFirst()
                                            .orElse(null);
                                    if (athlete != null && event != null) {
                                        olympicGames.recordResult(new Result(athlete, event, position, score, medal));
                                    }
                                }
                            }
                            break;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            // File not found, return the default instance of OlympicGames
            return olympicGames;
        }
        return olympicGames;
    }

}