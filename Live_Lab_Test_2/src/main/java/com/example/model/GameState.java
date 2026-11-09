package com.example.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GameState implements Serializable {

    public static final int TOTAL = 4;
    private static final int[] MARKS = {2, 2, 3, 3};

    private final String playerName;
    private final List<QuestionResult> results = new ArrayList<>();
    private int index;
    private int score;
    private String prompt;
    private Choices choices;

    public GameState(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerName() { return playerName; }
    public int getScore() { return score; }
    public int getTotal() { return TOTAL; }
    public int getQuestionNumber() { return index + 1; }
    public int getMarks() { return MARKS[Math.min(index, TOTAL - 1)]; }
    public String getPrompt() { return prompt; }
    public Choices getChoices() { return choices; }
    public List<QuestionResult> getResults() { return results; }
    public boolean isChoosing() { return choices != null; }
    public boolean isFinished() { return index >= TOTAL; }

    public String getType() {
        if (index == 0) return "PRIME";
        if (index == 1) return "MOD";
        return "TRIG";
    }

    public String getTitle() {
        switch (getType()) {
            case "PRIME": return "PRIME NUMBER";
            case "MOD": return "MODULUS";
            default: return "TRIGONOMETRY";
        }
    }

    public void startChoosing(String prompt, Choices choices) {
        this.prompt = prompt;
        this.choices = choices;
    }

    public void answer(int choice) {
        int earned = choice == choices.getCorrectIndex() ? MARKS[index] : 0;
        results.add(new QuestionResult(prompt, choices.getOptions()[choice], choices.getCorrectText(), earned, MARKS[index]));
        score += earned;
        index++;
        prompt = null;
        choices = null;
    }
}
