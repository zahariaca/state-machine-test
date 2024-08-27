package com.zahariaca.statemachinetest.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "scenario")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScenarioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "patient_file_id", nullable = false)
    private PatientFileEntity patientId;

    @NotNull
    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "initial_state_id", nullable = false)
    private StateEntity initialStateEntity;

}