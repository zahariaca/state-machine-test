package com.zahariaca.statemachinetest.model;

public class Transition {
    private Condition condition;
    private State nextState;
    private int score;

    public Transition(Condition condition, State nextState, int score) {
        this.condition = condition;
        this.nextState = nextState;
        this.score = score;
    }

    public Condition getCondition() {
        return condition;
    }

    public State getNextState() {
        return nextState;
    }

    public int getScore() {
        return score;
    }
}

