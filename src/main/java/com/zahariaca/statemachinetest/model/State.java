package com.zahariaca.statemachinetest.model;


import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Data
@Getter
public class State {
    private final String name;
    private final String description;
    private final List<Transition> transitions;

    public State(String name, String description) {
        this.name = name;
        this.description = description;
        this.transitions = new ArrayList<>();
    }

    public void addTransition(Condition condition, State nextState, int score) {
        transitions.add(new Transition(condition, nextState, score));
    }

    public TransitionResult getNextState(UserInput userInput) {
        for (Transition transition : transitions) {
            if (transition.getCondition().evaluate(userInput)) {
                return new TransitionResult(transition.getNextState(), transition.getScore());
            }
        }
        return null;
    }
}
