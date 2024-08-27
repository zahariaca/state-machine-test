package com.zahariaca.statemachinetest.repositories;

import com.zahariaca.statemachinetest.entities.PatientFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PatientFileRepository extends JpaRepository<PatientFileEntity, UUID> {
}
