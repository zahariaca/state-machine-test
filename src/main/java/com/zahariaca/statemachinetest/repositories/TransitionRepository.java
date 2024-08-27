package com.zahariaca.statemachinetest.repositories;

import com.zahariaca.statemachinetest.entities.TransitionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransitionRepository extends JpaRepository<TransitionEntity, Integer> {
}
