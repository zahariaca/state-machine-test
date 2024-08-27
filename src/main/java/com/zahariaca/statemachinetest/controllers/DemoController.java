package com.zahariaca.statemachinetest.controllers;

import com.zahariaca.statemachinetest.entities.TransitionEntity;
import com.zahariaca.statemachinetest.model.TransitionResultEntity;
import com.zahariaca.statemachinetest.model.UserInput;
import com.zahariaca.statemachinetest.repositories.ScenarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class DemoController {
    private final ScenarioRepository scenarioRepository;

    @GetMapping("/demo")
    public ResponseEntity<String> demo() {
        var scenario = scenarioRepository.findAll().stream().findFirst().get();

        Map<String, Boolean> userConditions = new HashMap<>();
        userConditions.put("Condition1", true);
        userConditions.put("Condition2", true);
        UserInput userInput = new UserInput(userConditions);
        var initialState = scenario.getInitialStateEntity();

        TransitionResultEntity nextState = null;

        for (TransitionEntity transition : initialState.getAvailableTransitions()) {
            if (transition.evaluate(userInput)) {
                nextState = new TransitionResultEntity(transition.getToState(), transition.getScore());
            }
        }

        if (nextState != null) {
            return ResponseEntity.ok(nextState.getNextState().getName());
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


}
