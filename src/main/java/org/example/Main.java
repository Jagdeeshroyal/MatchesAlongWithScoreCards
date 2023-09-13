package org.example;

import org.example.Model.Match;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException {
        loadPlayer();//V Kohli-4 //3 India //1348644  out-14 score-7
//        Map<String, Integer> players = new HashMap<>();
//        players.put("j",67);
//        System.out.println(players.put("j",players.get("j")+67));
    }


    public static void loadPlayer() throws IOException {
        File files[] = new File("src/main/resources/all_male_csv").listFiles();
        System.out.println(files.length);
        updatePlayer(files);
    }

    private static void updatePlayer(File[] files) throws IOException {
        List<Match> matches = new ArrayList<>();
        boolean india = false;
        boolean infoCompleted = false;
        Map<String, Integer> players = new LinkedHashMap<>();

        for (File file : files) {
            infoCompleted = false;
            players.clear();
            String[] teams = new String[2];
            Match match = new Match();
            int score = 0;
            india = false;
            List<String> list = Files.lines(Path.of(file.getPath())).collect(Collectors.toList());
            //V Kohli-4 //3 India //1348644  out-14 score-7

            for (String x : list) {
                {
                    String[] a = x.split(",");

                    if (a[0].equals("info") && !infoCompleted) {
                        if (a[1].equals("team")) {
                            if (teams[0] == null) {
                                teams[0] = a[2];
                                continue;
                            } else {
                                teams[1] = a[2];
                                if (teams[0].equals("India")) {
                                    matches.add(match);
                                    match.setTeam(teams[0]);
                                    match.setAgainst(teams[1]);
                                    india = true;
                                } else if (teams[1].equals("India")) {
                                    matches.add(match);
                                    match.setTeam(teams[1]);
                                    match.setAgainst(teams[0]);
                                    india = true;
                                } else {
                                    break;
                                }

                            }
                        } else if (a[1].equals("date") && a.length > 2) {
                            match.setDate(a[2]);
                        } else if (a[1].equals("event") && a.length > 2) {
                            match.setEvent(a[2]);
                        } else if (a[1].equals("city") && a.length > 2) {
                            match.setCity(a[2]);
                        } else if (a[1].equals("venue") && a.length > 2) {
                            match.setVenue(a[2]);
                        } else if (a[1].equals("toss_winner") && a.length > 2) {
                            match.setToss(a[2]);
                        } else if (a[1].equals("player_of_match") && a.length > 2) {
                            match.setMvp(a[2]);
                        } else if (a[1].equals("winner") && a.length > 2) {
                            match.setWinner(a[2]);
                            infoCompleted = true;
                            break;
                        }
                    }

                }
            }
            if (india) {
                players = updatePlayer(file);
                match.setScorecard(players);
            }


        }
        System.out.println(matches.size());
    }

    private static Map<String, Integer> updatePlayer(File file) throws IOException {
        Map<String, Integer> players = new HashMap<>();
        List<String> list = Files.lines(Path.of(file.getPath())).collect(Collectors.toList());
        //V Kohli-4 //3 India //1348644  out-14 score-7
        list.forEach(x -> {
            String[] a = x.split(",");
            if (a[0].equals("ball")) {
                if (a[3].equals("India")) {
                    if (players.get(a[4]) != null)
                        players.put(a[4], players.get(a[4]) + Integer.valueOf(a[7]));
                    else
                        players.put(a[4], Integer.valueOf(a[7]));
                }
            }
        });
        return players;
    }
}