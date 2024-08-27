package com.zahariaca.statemachinetest.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "user_started_scenarios")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserStartedScenarioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @NotNull
    @Column(name = "username", nullable = false, length = Integer.MAX_VALUE)
    private String username;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "scenario_id", nullable = false)
    private ScenarioEntity scenario;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "previous_state_id")
    private StateEntity previousState;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "current_state_id", nullable = false)
    private StateEntity currentState;

    @ColumnDefault("false")
    @Column(name = "finished", nullable = false)
    private Boolean finished = false;

    @ColumnDefault("0")
    @Column(name = "score", nullable = false)
    private Integer score;

}