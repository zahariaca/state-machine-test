package com.zahariaca.statemachinetest;

import com.zahariaca.statemachinetest.entities.PatientFileEntity;
import com.zahariaca.statemachinetest.entities.ScenarioEntity;
import com.zahariaca.statemachinetest.entities.StateEntity;
import com.zahariaca.statemachinetest.entities.TransitionEntity;
import com.zahariaca.statemachinetest.repositories.*;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class StateMachineTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(StateMachineTestApplication.class, args);
    }


    @Bean
    public ApplicationRunner applicationRunner(
            PatientFileRepository patientFileRepository,
            ScenarioRepository scenarioRepository,
            StateRepository stateRepository,
            TransitionRepository transitionRepository,
            UserStartedScenariosRepository userStartedScenariosRepository
    ) {
        return (args) -> {
            if (userStartedScenariosRepository.count() == 0) {
                userStartedScenariosRepository.deleteAll();
                scenarioRepository.deleteAll();
                patientFileRepository.deleteAll();
                transitionRepository.deleteAll();
                stateRepository.deleteAll();

                PatientFileEntity patientEntity1 = PatientFileEntity.builder()
                        .firstName("Luz")
                        .familyName("Diaz")
                        .gender("female")
                        .age(61)
                        .complaint("Altered mental state")
                        .build();

                var savedPatientFileEntity = patientFileRepository.save(patientEntity1);


                var initialState = StateEntity.builder()
                        .name("Initial")
                        .description("Initial patient case description")
                        .isStarter(true)
                        .build();
                var state1 = StateEntity.builder()
                        .name("State1")
                        .description("State1 Description")
                        .build();
                var state2a = StateEntity.builder()
                        .name("State2a")
                        .description("State2a Description")
                        .build();
                var state2b = StateEntity.builder()
                        .name("State2b")
                        .description("State2b Description")
                        .build();
                var finalBadState = StateEntity.builder()
                        .name("Final Bad")
                        .description("Final Bad Description")
                        .isFinal(true)
                        .build();
                var finalGoodState = StateEntity.builder()
                        .name("Final Good")
                        .description("Final Good Description")
                        .isFinal(true)
                        .build();

                var savedInitialStateEntity = stateRepository.save(initialState);
                var savedState1 = stateRepository.save(state1);
                var savedState2a = stateRepository.save(state2a);
                var savedState2b = stateRepository.save(state2b);
                var savedFinalBadState = stateRepository.save(finalBadState);
                var savedFinalGoodState = stateRepository.save(finalGoodState);

                var transitionToState1 = TransitionEntity.builder()
                        .fromState(savedInitialStateEntity)
                        .toState(savedState1)
                        .condition("#Condition1 && #Condition2")
                        .score(10)
                        .build();
                var transitionToState2a = TransitionEntity.builder()
                        .fromState(savedState1)
                        .toState(savedState2a)
                        .condition("#Condition4 && #Condition5 != true")
                        .score(20)
                        .build();
                var transitionToState2b = TransitionEntity.builder()
                        .fromState(savedState1)
                        .toState(savedState2b)
                        .condition("#Condition4 && #Condition5")
                        .score(25)
                        .build();
                var transitionFromStage2aToFinalGoodState = TransitionEntity.builder()
                        .fromState(savedState2a)
                        .toState(savedFinalGoodState)
                        .condition("#Condition6 || #Condition7")
                        .score(30)
                        .build();
                var transitionFromStage2aToFinalBadState = TransitionEntity.builder()
                        .fromState(savedState2a)
                        .toState(savedFinalBadState)
                        .condition("#Condition8")
                        .score(0)
                        .build();
                var transitionFromStage2bToFinalGoodState = TransitionEntity.builder()
                        .fromState(savedState2b)
                        .toState(savedFinalGoodState)
                        .condition("#Condition6 || #Condition7")
                        .score(30)
                        .build();
                var savedTransitionToState1 = transitionRepository.save(transitionToState1);
                var savedTransitionToState2a = transitionRepository.save(transitionToState2a);
                var savedTransitionToState2b = transitionRepository.save(transitionToState2b);
                var savedTransitionFromStage2aToFinalState = transitionRepository.save(transitionFromStage2aToFinalGoodState);
                var savedTransitionFromStage2aToFinalBadState = transitionRepository.save(transitionFromStage2aToFinalBadState);
                var savedTransitionFromStage2bToFinalState = transitionRepository.save(transitionFromStage2bToFinalGoodState);


                var scenario1 = ScenarioEntity.builder()
                        .name("First scenario")
                        .description("First scenario description")
                        .patientId(savedPatientFileEntity)
                        .initialStateEntity(savedInitialStateEntity)
                        .build();

                var savedScenario2 = scenarioRepository.save(scenario1);
            }
        };
    }
}
