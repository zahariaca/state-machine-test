package com.zahariaca.statemachinetest.repositories;

import com.zahariaca.statemachinetest.entities.ScenarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ScenarioRepository extends JpaRepository<ScenarioEntity, UUID> {
}
