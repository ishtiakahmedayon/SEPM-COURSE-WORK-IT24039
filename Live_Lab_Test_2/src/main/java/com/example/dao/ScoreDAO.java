package com.example.dao;

import com.example.model.GameScore;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ScoreDAO {

    private static final String URL = "jdbc:mysql://localhost:3306/math_whiz_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "YOUR_DB_PASSWORD";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public void save(GameScore s) throws SQLException {
        String sql = "INSERT INTO game_scores (player_name, score, difficulty, played_at) VALUES (?, ?, ?, ?)";
        try (Connection c = connect(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, s.getPlayerName());
            ps.setInt(2, s.getScore());
            ps.setString(3, s.getDifficulty());
            ps.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            ps.executeUpdate();
        }
    }

    public List<GameScore> findTop10() throws SQLException {
        String sql = "SELECT id, player_name, score, difficulty, played_at "
                   + "FROM game_scores ORDER BY score DESC, played_at ASC LIMIT 10";
        List<GameScore> list = new ArrayList<>();
        try (Connection c = connect();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new GameScore(
                        rs.getInt("id"),
                        rs.getString("player_name"),
                        rs.getInt("score"),
                        rs.getString("difficulty"),
                        rs.getTimestamp("played_at")));
            }
        }
        return list;
    }
}
