package com.example.model;

import java.io.Serializable;

public class Choices implements Serializable {

    private final String[] options;
    private final int correctIndex;

    public Choices(String[] options, int correctIndex) {
        this.options = options;
        this.correctIndex = correctIndex;
    }

    public String[] getOptions() { return options; }
    public int getCorrectIndex() { return correctIndex; }
    public String getCorrectText() { return options[correctIndex]; }
}
