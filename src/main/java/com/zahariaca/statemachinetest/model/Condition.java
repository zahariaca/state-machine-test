package com.zahariaca.statemachinetest.model;

import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

public class Condition {
    private String expression;

    public Condition(String expression) {
        this.expression = expression;
    }

    public boolean evaluate(UserInput userInput) {
        ExpressionParser parser = new SpelExpressionParser();
        StandardEvaluationContext context = new StandardEvaluationContext();

        userInput.getConditions().forEach((key, value) -> {
            context.setVariable(key, value);
        });

//        // Simplified evaluation logic, assumes expression is a boolean
//        // You can implement a parser or use an existing library for complex expressions
//        Map<String, Boolean> inputConditions = userInput.getConditions();
//        // Example: "Condition1 AND Condition2"
//        String[] parts = expression.split(" AND ");
//        for (String part : parts) {
//            if (!inputConditions.getOrDefault(part.trim(), false)) {
//                return false;
//            }
//        }
//        return true;o
        return Boolean.TRUE.equals(parser.parseExpression(expression).getValue(context, Boolean.class));
    }
}

