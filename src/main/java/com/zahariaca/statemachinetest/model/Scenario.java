package com.zahariaca.statemachinetest.model;

import lombok.Builder;
import lombok.Data;

import java.util.Objects;

@Data
@Builder
public final class Scenario {
    private final PatientFile patientFile;
    private final State initialState;
    private State currentState;

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Scenario) obj;
        return Objects.equals(this.patientFile, that.patientFile) &&
                Objects.equals(this.initialState, that.initialState) &&
                Objects.equals(this.currentState, that.currentState);
    }

    @Override
    public int hashCode() {
        return Objects.hash(patientFile, initialState, currentState);
    }

    @Override
    public String toString() {
        return "Scenario[" +
                "patientFile=" + patientFile + ", " +
                "initialState=" + initialState + ", " +
                "currentState=" + currentState + ']';
    }

}
