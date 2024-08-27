package com.zahariaca.statemachinetest.controllers;

import com.zahariaca.statemachinetest.entities.TransitionEntity;
import com.zahariaca.statemachinetest.entities.UserStartedScenarioEntity;
import com.zahariaca.statemachinetest.model.TransitionResultEntity;
import com.zahariaca.statemachinetest.model.UserInput;
import com.zahariaca.statemachinetest.repositories.ScenarioRepository;
import com.zahariaca.statemachinetest.repositories.UserStartedScenariosRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {
    private final ScenarioRepository scenarioRepository;
    private final UserStartedScenariosRepository userStartedScenariosRepository;

    @GetMapping
    public String home(Model model) {
        model.addAttribute("message", "Welcome to the home page!");
        model.addAttribute("scenarios", scenarioRepository.findAll());
        model.addAttribute("startedScenarios", userStartedScenariosRepository.findAll());
        return "home";
    }

    @PostMapping("/start-scenario")
    public String startScenario(
            @AuthenticationPrincipal User user,
            @RequestParam("scenarioId") UUID scenarioId,
            Model model) {
        var scenario = scenarioRepository.findById(scenarioId).orElseThrow();
        var savedStartedScenario = userStartedScenariosRepository.save(UserStartedScenarioEntity.builder()
                .username(user.getUsername())
                .scenario(scenario)
                .currentState(scenario.getInitialStateEntity())
                .finished(false)
                .score(0)
                .build());

        log.info("Saved started scenario with id: {}", savedStartedScenario.getId());

        model.addAttribute("savedScenario", savedStartedScenario);

        return "scenario-page";
    }

    @PostMapping("/continue-scenario")
    public String continueScenario(
            @AuthenticationPrincipal User user,
            @RequestParam("startedScenarioId") UUID startedScenarioId,
            @RequestParam("scenarioId") UUID scenarioId,
            Model model) {

        var existingStartedScenario = userStartedScenariosRepository.findByUsernameAndId(user.getUsername(), startedScenarioId);

        if (existingStartedScenario != null && !existingStartedScenario.getFinished()) {
            log.info("Scenario already exists: {}", existingStartedScenario);

            model.addAttribute("savedScenario", existingStartedScenario);
        } else {
            var scenario = scenarioRepository.findById(scenarioId).orElseThrow();
            var savedStartedScenario = userStartedScenariosRepository.save(UserStartedScenarioEntity.builder()
                    .username(user.getUsername())
                    .scenario(scenario)
                    .currentState(scenario.getInitialStateEntity())
                    .finished(false)
                    .score(0)
                    .build());

            log.info("Saved started scenario with id: {}", savedStartedScenario.getId());

            model.addAttribute("savedScenario", savedStartedScenario);
        }

        return "scenario-page";
    }

    @PostMapping("/view-result")
    public String viewResult(
            @AuthenticationPrincipal User user,
            @RequestParam("startedScenarioId") UUID startedScenarioId,
            Model model) {
        var existingStartedScenario = userStartedScenariosRepository.findByUsernameAndId(user.getUsername(), startedScenarioId);

        if (existingStartedScenario != null && existingStartedScenario.getFinished()) {
            log.info("Scenario already exists: {}", existingStartedScenario);

            model.addAttribute("savedScenario", existingStartedScenario);
        } else {
            log.error("Something bad happened...");
        }

        return "scenario-page";
    }

    @PostMapping("/submit-answer")
    public String submitSubItems(
            @AuthenticationPrincipal User user,
            @RequestParam("scenarioId") UUID savedScenarioId,
            @RequestParam("answer") String conditionsInput,
            Model model) {
        // condition1=true && condition2=false
        Map<String, Boolean> conditionsMap = new HashMap<>();
        String[] conditionsArray = conditionsInput.split(",");

        // Iterate over each condition, split by "=", and add to the map
        for (String condition : conditionsArray) {
            String[] keyValue = condition.trim().split("="); // Split by "="
            if (keyValue.length == 2) {
                String key = keyValue[0].trim();
                Boolean value = Boolean.parseBoolean(keyValue[1].trim());
                conditionsMap.put(key, value);
            }
        }

        conditionsMap.forEach((key, value) -> {
            System.out.println("Condition: " + key + ", Value: " + value);
        });

        var userInput = new UserInput(conditionsMap);

        var startedScenario = userStartedScenariosRepository.findById(savedScenarioId).orElseThrow();
        var currentState = startedScenario.getCurrentState();

        TransitionResultEntity transitionResultEntity = null;

        for (TransitionEntity transition : currentState.getAvailableTransitions()) {
            if (transition.evaluate(userInput)) {
                transitionResultEntity = new TransitionResultEntity(transition.getToState(), transition.getScore());
                break;
            }
        }

        if (transitionResultEntity != null) {
            startedScenario.setPreviousState(currentState);
            startedScenario.setCurrentState(transitionResultEntity.getNextState());
            startedScenario.setScore(startedScenario.getScore() + transitionResultEntity.getScore());
            startedScenario.setFinished(transitionResultEntity.getNextState().getIsFinal());

            userStartedScenariosRepository.save(startedScenario);
        }

        log.info("Scenario id: {}", savedScenarioId);

        return "redirect:/";
    }
}
