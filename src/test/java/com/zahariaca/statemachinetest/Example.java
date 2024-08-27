package com.zahariaca.statemachinetest;

import com.zahariaca.statemachinetest.model.*;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class Example {
    @Test
    void test() {
        var patientFile = PatientFile.builder()
                .firstName("Luz")
                .lastName("Diaz")
                .gender("female")
                .age(61)
                .complaint("Altered mental state")
                .build();
        // Create states
        State initialState = new State("Initial", "Initial Patient Description");
        State state1 = new State("Scenario 1", "Description 1");
        State state2 = new State("Scenario 2", "Description 2");
        State finale = new State("Finale", "Final Description");

        // Define conditions
        Condition condition1 = new Condition("#Condition1 && #Condition2");
        Condition condition2a = new Condition("#Condition4 && #Condition5 != true");
        Condition condition2b = new Condition("#Condition4 && #Condition5");
        Condition condition3 = new Condition("#Condition7 || #Condition8");

        // Add transitions
        initialState.addTransition(condition1, state1, 10);
        state1.addTransition(condition2a, state2, 20);
        state1.addTransition(condition2b, state2, 25);
        state2.addTransition(condition3, finale, 30);

        var scenario = Scenario.builder()
                .initialState(initialState)
                .patientFile(patientFile)
                .build();

        // Simulate user input
        Map<String, Boolean> userConditions = new HashMap<>();
        userConditions.put("Condition1", true);
        userConditions.put("Condition2", true);
        UserInput userInput = new UserInput(userConditions);

        // Scoring Engine
        ScoringEngine scoringEngine = new ScoringEngine();

        // Run state machine
        System.out.println("Patient details: ");
        System.out.println(scenario.getPatientFile());
        System.out.println("------------------------------------------------------------");

        TransitionResult result = scenario.getInitialState().getNextState(userInput);
        if (result != null) {
            scoringEngine.updateScore(result.getScore());
            State nextState = result.getNextState();
            scenario.setCurrentState(nextState);

            System.out.println("Transitioned to: " + scenario.getCurrentState().getName());
            System.out.println("Current Score: " + scoringEngine.getScore());

            // Simulate 2nd user input
            userConditions.clear();
            userConditions.put("Condition4", true);
            userConditions.put("Condition5", false);

            var secondNextState = scenario.getCurrentState().getNextState(new UserInput(userConditions));
            scoringEngine.updateScore(secondNextState.getScore());
            scenario.setCurrentState(secondNextState.getNextState());

            System.out.println("Transitioned to: " + scenario.getCurrentState().getName());
            System.out.println("Current Score: " + scoringEngine.getScore());

        } else {
            System.out.println("No valid transition found.");
        }


    }
}
