package com.zahariaca.statemachinetest.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "patient_file")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PatientFileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Size(max = 100)
    @NotNull
    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Size(max = 100)
    @NotNull
    @Column(name = "family_name", nullable = false, length = 100)
    private String familyName;

    @Size(max = 25)
    @NotNull
    @Column(name = "gender", nullable = false, length = 25)
    private String gender;

    @NotNull
    @Column(name = "age", nullable = false)
    private Integer age;

    @NotNull
    @Column(name = "complaint", nullable = false, length = Integer.MAX_VALUE)
    private String complaint;

}