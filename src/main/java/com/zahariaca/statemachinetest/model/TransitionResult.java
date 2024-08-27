package com.zahariaca.statemachinetest.model;

public class TransitionResult {
    private State nextState;
    private int score;

    public TransitionResult(State nextState, int score) {
        this.nextState = nextState;
        this.score = score;
    }

    public State getNextState() {
        return nextState;
    }

    public int getScore() {
        return score;
    }
}
