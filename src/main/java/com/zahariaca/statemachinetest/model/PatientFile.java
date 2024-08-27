package com.zahariaca.statemachinetest.model;

import lombok.Builder;

@Builder
public record PatientFile(
        String firstName,
        String lastName,
        String gender,
        Integer age,
        String complaint
) {
}