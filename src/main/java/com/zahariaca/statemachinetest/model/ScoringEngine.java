package com.zahariaca.statemachinetest.model;

public class ScoringEngine {
    private int score;

    public ScoringEngine() {
        this.score = 0;
    }

    public void updateScore(int points) {
        this.score += points;
    }

    public int getScore() {
        return score;
    }
}

