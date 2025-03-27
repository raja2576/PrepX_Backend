package com.prepXBackend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Progress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user; // ✅ Associate progress with user

    private String username; // ✅ Store the username
    private int score;
    private int totalQuestions;
    private String category;
    private String difficulty;
    private LocalDateTime date; // ✅ Store Timestamp

    // ✅ Constructors
    public Progress() {}

    public Progress(User user, String username, int score, int totalQuestions, String category, String difficulty, LocalDateTime date) {
        this.user = user;
        this.username = username;
        this.score = score;
        this.totalQuestions = totalQuestions;
        this.category = category;
        this.difficulty = difficulty;
        this.date = date;
    }

    // ✅ Getters & Setters
    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

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

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
