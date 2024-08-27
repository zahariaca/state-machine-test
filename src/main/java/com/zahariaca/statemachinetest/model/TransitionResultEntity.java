package com.zahariaca.statemachinetest.model;

import com.zahariaca.statemachinetest.entities.StateEntity;
import lombok.Data;

@Data
public class TransitionResultEntity {
    private StateEntity nextState;
    private int score;

    public TransitionResultEntity(StateEntity nextState, int score) {
        this.nextState = nextState;
        this.score = score;
    }
}
