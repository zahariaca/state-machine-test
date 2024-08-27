package com.zahariaca.statemachinetest.repositories;

import com.zahariaca.statemachinetest.entities.StateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StateRepository extends JpaRepository<StateEntity, UUID> {
}
