package com.example.quiz;

public class QuestionModel {
    private int color;
    private boolean answer;

    public QuestionModel() {
    }

    public int getColor() {
        return color;
    }

    public boolean getAnswer() {
        return answer;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setAnswer(boolean answer) {
        this.answer = answer;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public QuestionModel(int color, boolean answer, String description) {
        this.color = color;
        this.answer = answer;
        this.description = description;
    }

    private String description;

}
