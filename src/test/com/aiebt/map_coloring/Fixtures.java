package com.aiebt.map_coloring;

import com.aiebt.map_coloring.core.Color;
import com.aiebt.map_coloring.core.Country;
import com.aiebt.map_coloring.core.State;

import java.util.HashMap;
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

    public static final Map<String, Set<String>> COUNTRY_2_STATES = Map.of(
            "Western Australia", Set.of("Northern Territory", "Southern Australia"),
            "Northern Territory", Set.of("Western Australia", "Southern Australia", "Queensland"),
            "Southern Australia", Set.of("Western Australia", "Northern Territory", "Queensland", "New South Wales", "Victoria"),
            "Queensland", Set.of("Northern Territory", "Southern Australia", "New South Wales"),
            "New South Wales", Set.of("Southern Australia", "Queensland", "Victoria"),
            "Victoria", Set.of("Southern Australia", "New South Wales"),
            "Tasmania", Set.of()
    );
    public static final Country UNCOLORED_COUNTRY_2 = new Country(
            COUNTRY_2_STATES,
            3
    );

    public static final Map<String, Set<String>> COUNTRY_3_STATES;
    static {
        COUNTRY_3_STATES = new HashMap<>(27);
        // 7 states in North region
        COUNTRY_3_STATES.put("AC", Set.of("AM", "RO"));
        COUNTRY_3_STATES.put("AM", Set.of("AC", "RO", "MT", "PA", "RR"));
        COUNTRY_3_STATES.put("RR", Set.of("AM", "PA"));
        COUNTRY_3_STATES.put("PA", Set.of("RR", "AM", "MT", "TO", "MA", "AP"));
        COUNTRY_3_STATES.put("AP", Set.of("PA"));
        COUNTRY_3_STATES.put("RO", Set.of("AC", "AM", "MT"));
        COUNTRY_3_STATES.put("TO", Set.of("PA", "MT", "GO", "BA", "PI", "MA"));
        // 9 states in North-East region
        COUNTRY_3_STATES.put("MA", Set.of("PA", "TO", "PI"));
        COUNTRY_3_STATES.put("PI", Set.of("MA", "TO", "BA", "PE", "CE"));
        COUNTRY_3_STATES.put("CE", Set.of("PI", "PE", "PB", "RN"));
        COUNTRY_3_STATES.put("RN", Set.of("CE", "PB"));
        COUNTRY_3_STATES.put("PB", Set.of("RN", "CE", "PE"));
        COUNTRY_3_STATES.put("PE", Set.of("CE", "PI", "BA", "AL"));
        COUNTRY_3_STATES.put("AL", Set.of("PE", "BA", "SE"));
        COUNTRY_3_STATES.put("SE", Set.of("AL", "BA"));
        COUNTRY_3_STATES.put("BA", Set.of("PI", "TO", "GO", "MG", "ES", "SE", "AL"));
        // 4 states in center West region
        COUNTRY_3_STATES.put("MT", Set.of("AM", "RO", "MS", "GO", "TO", "PA"));
        COUNTRY_3_STATES.put("MS", Set.of("MT", "PR", "SP", "MG", "GO"));
        COUNTRY_3_STATES.put("GO", Set.of("MT", "MS", "MG", "GO", "DF", "BA", "TO"));
        COUNTRY_3_STATES.put("DF", Set.of("GO"));
        // 4 states in South-East region
        COUNTRY_3_STATES.put("MG", Set.of("GO", "MS", "SP", "RJ", "ES", "BA"));
        COUNTRY_3_STATES.put("ES", Set.of("BA", "MG", "RJ"));
        COUNTRY_3_STATES.put("RJ", Set.of("MG", "SP", "ES"));
        COUNTRY_3_STATES.put("SP", Set.of("MG", "MS", "PR", "RJ"));
        // 3 states in South region
        COUNTRY_3_STATES.put("PR", Set.of("MS", "SP", "SC", "RJ"));
        COUNTRY_3_STATES.put("SC", Set.of("PR", "RS"));
        COUNTRY_3_STATES.put("RS", Set.of("SC"));
    };

    public static final Country UNCOLORED_COUNTRY_3 = new Country(
            COUNTRY_3_STATES,
            4
    );
}
