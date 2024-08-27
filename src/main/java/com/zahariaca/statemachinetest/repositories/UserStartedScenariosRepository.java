package com.zahariaca.statemachinetest.repositories;

import com.zahariaca.statemachinetest.entities.UserStartedScenarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserStartedScenariosRepository extends JpaRepository<UserStartedScenarioEntity, UUID> {
    public UserStartedScenarioEntity findByUsernameAndScenarioId(String name, UUID scenarioId);
    public UserStartedScenarioEntity findByUsernameAndId(String name, UUID scenarioId);
}
