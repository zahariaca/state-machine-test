CREATE TABLE patient_file
(
    id          UUID DEFAULT gen_random_uuid() NOT NULL,
    first_name  VARCHAR(100)                   NOT NULL,
    family_name VARCHAR(100)                   NOT NULL,
--     picture_url TEXT,
    gender      varchar(25)                    NOT NULL,
    age         int                            NOT NULL,
    complaint   TEXT                           NOT NULL,

    CONSTRAINT patient_file_pkey PRIMARY KEY (id)
);

CREATE TABLE state
(
    id          UUID    DEFAULT gen_random_uuid() NOT NULL,
    name        text                              not null,
    description text                              not null,
    is_starter  boolean default false             not null,
    is_final    boolean default false             not null

        CONSTRAINT state_pkey PRIMARY KEY (id)
);

CREATE TABLE transition
(
    id            SERIAL NOT NULL,
    condition     TEXT   NOT NULL,
    from_state_id UUID   NOT NULL,
    to_state_id   UUID   NOT NULL,
    score         int    not null,

    CONSTRAINT transition_pkey PRIMARY KEY (id),
    CONSTRAINT fk_from_state_id FOREIGN KEY (from_state_id) REFERENCES state (id),
    CONSTRAINT fk_to_state_id FOREIGN KEY (to_state_id) REFERENCES state (id)
);

-- state machine
CREATE TABLE scenario
(
    id               UUID DEFAULT gen_random_uuid() NOT NULL,
    name             VARCHAR(255)                   NOT NULL,
    description      VARCHAR(255)                   NOT NULL,
    patient_file_id  UUID                           NOT NULL,
    initial_state_id UUID                           NOT NULL,

    CONSTRAINT scenario_pkey PRIMARY KEY (id),
    CONSTRAINT fk_patient_file_id FOREIGN KEY (patient_file_id) references patient_file (id),
    CONSTRAINT fk_initial_state_id FOREIGN KEY (initial_state_id) references state (id)
);

-- user play session
CREATE TABLE user_started_scenarios
(
    id                UUID    DEFAULT gen_random_uuid() NOT NULL,
    username          TEXT                              NOT NULL,
    scenario_id       UUID                              NOT NULL,
    previous_state_id UUID    DEFAULT NULL,
    current_state_id  UUID                              NOT NULL,
    finished          boolean DEFAULT false             NOT NULL,
    score             int     DEFAULT 0                 NOT NULL,

    CONSTRAINT user_started_scenarios_pkey PRIMARY KEY (id),
    CONSTRAINT fk_scenario_id FOREIGN KEY (scenario_id) REFERENCES scenario (id),
    CONSTRAINT fk_previous_state_id FOREIGN KEY (previous_state_id) REFERENCES state (id),
    CONSTRAINT fk_current_state_id FOREIGN KEY (current_state_id) REFERENCES state (id)
)