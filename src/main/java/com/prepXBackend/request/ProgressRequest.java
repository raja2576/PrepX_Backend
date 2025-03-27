package com.prepXBackend.request;

public class ProgressRequest {
    private int score;
    private int totalQuestions;
    private String category;
    private String difficulty;

    public ProgressRequest() {}

    public ProgressRequest(int score, int totalQuestions, String category, String difficulty) {
        this.score = score;
        this.totalQuestions = totalQuestions;
        this.category = category;
        this.difficulty = difficulty;
    }

    // ✅ Getters & Setters
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }
}
