package com.aiebt.map_coloring;

import com.aiebt.map_coloring.core.Color;
import com.aiebt.map_coloring.core.Country;
import com.aiebt.map_coloring.core.State;

import java.util.Map;
import java.util.Set;

public class Fixtures {

    public static final State STATE_A = new State("State A", Color.WHITE);
    public static final State STATE_B = new State("State B", Color.WHITE);
    public static final State STATE_C = new State("State C", Color.WHITE);

    public static final Map<String, Set<String>> COUNTRY_1_STATES = Map.of(
            "State A", Set.of("State B", "State C"),
            "State B", Set.of("State A"),
            "State C", Set.of("State A")
    );
    public static final Country UNCOLORED_COUNTRY_1 = new Country(
            COUNTRY_1_STATES,
            3
    );

    public static final Country COLORED_COUNTRY_1 = new Country(
            COUNTRY_1_STATES,
            3,
            Map.of("State A", Color.forIndex(1), "State B", Color.forIndex(2), "State C", Color.forIndex(3))
    );
}
