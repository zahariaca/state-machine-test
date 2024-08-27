package com.zahariaca.statemachinetest.entities;

import com.zahariaca.statemachinetest.model.UserInput;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.SpelEvaluationException;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

@Getter
@Setter
@Entity
@Table(name = "transition")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class TransitionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transition_id_gen")
    @SequenceGenerator(name = "transition_id_gen", sequenceName = "transition_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    // TODO: Long
    private Integer id;

    @NotNull
    @Column(name = "condition", nullable = false, length = Integer.MAX_VALUE)
    private String condition;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "from_state_id", nullable = false)
    private StateEntity fromState;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "to_state_id", nullable = false)
    private StateEntity toState;

    @NotNull
    @Column(name = "score", nullable = false)
    private Integer score;

    public boolean evaluate(UserInput userInput) {
        ExpressionParser parser = new SpelExpressionParser();
        StandardEvaluationContext context = new StandardEvaluationContext();

        userInput.getConditions().forEach((key, value) -> {
            context.setVariable(key, value);
        });

        var result = Boolean.FALSE;
        try {
            result = parser.parseExpression(condition).getValue(context, Boolean.class);
        } catch (SpelEvaluationException e) {
            log.debug(e.getMessage());
        }


        return Boolean.TRUE.equals(result);
    }
}