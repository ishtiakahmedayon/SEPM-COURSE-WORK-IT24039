package com.example.servlet;

import com.example.dao.ScoreDAO;
import com.example.model.Choices;
import com.example.model.GameScore;
import com.example.model.GameState;
import com.example.service.MathService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/game")
public class GameServlet extends HttpServlet {

    private static final String DIFFICULTY = "NORMAL";
    private final ScoreDAO dao = new ScoreDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if ("leaderboard".equals(req.getParameter("action"))) {
            showLeaderboard(req, resp);
        } else {
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        if ("start".equals(action)) {
            startGame(req, resp);
        } else if ("generate".equals(action)) {
            generateOptions(req, resp);
        } else if ("answer".equals(action)) {
            submitChoice(req, resp);
        } else {
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
        }
    }

    private void startGame(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("playerName");
        name = name == null ? "" : name.trim();

        if (name.isEmpty() || name.length() > 100) {
            req.setAttribute("error", "Enter a name (1-100 characters).");
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
            return;
        }

        req.getSession(true).setAttribute("game", new GameState(name));
        showGame(req, resp);
    }

    private void generateOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        GameState game = currentGame(req);
        if (game == null) {
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
            return;
        }
        if (game.isChoosing()) {
            showGame(req, resp);
            return;
        }

        try {
            String prompt;
            Choices choices;
            switch (game.getType()) {
                case "PRIME": {
                    int n = parseInt(req.getParameter("n"), "Number");
                    prompt = "Is " + n + " a prime number? (1 = prime, 0 = not prime)";
                    choices = MathService.intChoices(MathService.isPrime(n));
                    break;
                }
                case "MOD": {
                    int a = parseInt(req.getParameter("a"), "Dividend");
                    int b = parseInt(req.getParameter("b"), "Divisor");
                    int remainder = MathService.calculateMod(a, b);
                    prompt = "What is " + a + " % " + b + " ?";
                    choices = MathService.intChoices(remainder);
                    break;
                }
                default: {
                    String fn = req.getParameter("fn") == null ? "" : req.getParameter("fn");
                    int angle = parseInt(req.getParameter("angle"), "Angle");
                    double value = MathService.calculateTrig(fn, angle);
                    if (Double.isNaN(value)) {
                        throw new IllegalArgumentException(fn + "(" + angle + "\u00B0) is undefined");
                    }
                    prompt = fn.toUpperCase() + "(" + angle + "\u00B0) = ? (2 decimals)";
                    choices = MathService.decimalChoices(value);
                    break;
                }
            }
            game.startChoosing(prompt, choices);
        } catch (IllegalArgumentException e) {
            req.setAttribute("error", e.getMessage());
        }
        showGame(req, resp);
    }

    private void submitChoice(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        GameState game = currentGame(req);
        if (game == null) {
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
            return;
        }
        if (!game.isChoosing()) {
            showGame(req, resp);
            return;
        }

        int choice;
        try {
            choice = Integer.parseInt(req.getParameter("choice"));
        } catch (NumberFormatException e) {
            choice = -1;
        }
        if (choice < 0 || choice >= game.getChoices().getOptions().length) {
            req.setAttribute("error", "Select one option.");
            showGame(req, resp);
            return;
        }

        game.answer(choice);
        if (game.isFinished()) {
            finishGame(req, resp, game);
        } else {
            showGame(req, resp);
        }
    }

    private void finishGame(HttpServletRequest req, HttpServletResponse resp, GameState game) throws ServletException, IOException {
        try {
            dao.save(new GameScore(game.getPlayerName(), game.getScore(), DIFFICULTY));
        } catch (SQLException e) {
            req.setAttribute("dbError", "Score not saved: " + e.getMessage());
        }
        req.setAttribute("playerName", game.getPlayerName());
        req.setAttribute("score", game.getScore());
        req.setAttribute("results", game.getResults());
        req.getSession().removeAttribute("game");
        req.getRequestDispatcher("/result.jsp").forward(req, resp);
    }

    private void showLeaderboard(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setAttribute("scores", dao.findTop10());
        } catch (SQLException e) {
            req.setAttribute("error", "Database error: " + e.getMessage());
        }
        req.getRequestDispatcher("/leaderboard.jsp").forward(req, resp);
    }

    private void showGame(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/game.jsp").forward(req, resp);
    }

    private GameState currentGame(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        return session == null ? null : (GameState) session.getAttribute("game");
    }

    private static int parseInt(String value, String label) {
        try {
            return Integer.parseInt(value.trim());
        } catch (RuntimeException e) {
            throw new IllegalArgumentException(label + " must be a whole number");
        }
    }
}
