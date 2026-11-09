package com.example.model;

import java.sql.Timestamp;

public class GameScore {

    private int id;
    private final String playerName;
    private final int score;
    private final String difficulty;
    private Timestamp playedAt;

    public GameScore(String playerName, int score, String difficulty) {
        this.playerName = playerName;
        this.score = score;
        this.difficulty = difficulty;
    }

    public GameScore(int id, String playerName, int score, String difficulty, Timestamp playedAt) {
        this(playerName, score, difficulty);
        this.id = id;
        this.playedAt = playedAt;
    }

    public int getId() { return id; }
    public String getPlayerName() { return playerName; }
    public int getScore() { return score; }
    public String getDifficulty() { return difficulty; }
    public Timestamp getPlayedAt() { return playedAt; }
}
