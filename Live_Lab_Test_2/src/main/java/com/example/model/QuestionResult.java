package com.example.model;

import java.io.Serializable;

public class QuestionResult implements Serializable {

    private final String prompt;
    private final String chosen;
    private final String correct;
    private final int marks;
    private final int maxMarks;

    public QuestionResult(String prompt, String chosen, String correct, int marks, int maxMarks) {
        this.prompt = prompt;
        this.chosen = chosen;
        this.correct = correct;
        this.marks = marks;
        this.maxMarks = maxMarks;
    }

    public String getPrompt() { return prompt; }
    public String getChosen() { return chosen; }
    public String getCorrect() { return correct; }
    public int getMarks() { return marks; }
    public int getMaxMarks() { return maxMarks; }
    public boolean isRight() { return marks > 0; }
}
