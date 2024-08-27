package com.zahariaca.statemachinetest.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

import static java.lang.String.format;

@Getter
@Setter
@Entity
@Table(name = "state")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private UUID id;

    @NotNull
    @Column(name = "name", nullable = false, length = Integer.MAX_VALUE)
    private String name;

    @NotNull
    @Column(name = "description", nullable = false, length = Integer.MAX_VALUE)
    private String description;

    @Column(name="is_starter", nullable = false)
    @Builder.Default
    private Boolean isStarter = false;

    @Column(name="is_final", nullable = false)
    @Builder.Default
    private Boolean isFinal = false;



    @OneToMany(mappedBy = "fromState")
    private Set<TransitionEntity> availableTransitions = new LinkedHashSet<>();

    @OneToMany(mappedBy = "toState")
    private Set<TransitionEntity> toStateTransitions = new LinkedHashSet<>();

//    public TransitionResultEntity getNextState(UserInput userInput) {
//        for (TransitionEntity transition : transitions) {
//            if (transition.evaluate(userInput)) {
//                return new TransitionResultEntity(transition.getToState(), transition.getScore());
//            }
//        }
//        return null;
//    }


    @Override
    public String toString() {

        StringBuilder fromStateSB = new StringBuilder();

        availableTransitions.forEach(t -> fromStateSB
                .append('\n')
                .append("\t\t\t")
                .append('{')
                .append(t.getId())
                .append(" | ")
                .append(t.getToState().getName())
                .append(" | ")
                .append(t.getCondition())
                .append('}')
                .append('\n'));

        StringBuilder toStateSB = new StringBuilder();

        toStateTransitions.forEach(t -> fromStateSB
                .append('\n')
                .append("\t\t\t")
                .append('{')
                .append(t.getId())
                .append(" | ")
                .append(t.getToState().getName())
                .append(" | ")
                .append(t.getCondition())
                .append('}')
                .append('\n'));

        return format("""
                 %nStateEntity [
                     id=%s,
                     name=%s,
                     description=%s,
                     fromStateTransitions=%s
                     toStateTransitions=%s
                 ]
                \s""", id, name, description, fromStateSB, toStateSB);
//
//        return format("%nStateEntity{" +
//                "%n%t id=" + id +
//                "%n%t , name='" + name + '\'' +
//                ", description='" + description + '\'' +
//                ", fromStateTransitions= %n%s" +
//                ", toStateTransitions=" + toStateTransitions +
//                '}', sb.toString());
    }
}