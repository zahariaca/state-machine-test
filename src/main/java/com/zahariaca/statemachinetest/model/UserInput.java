package com.zahariaca.statemachinetest.model;

import java.util.Map;

public class UserInput {
    private Map<String, Boolean> conditions;

    public UserInput(Map<String, Boolean> conditions) {
        this.conditions = conditions;
    }

    public Map<String, Boolean> getConditions() {
        return conditions;
    }
}
