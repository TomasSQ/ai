package com.aiebt.map_coloring.core;

import com.aiebt.map_coloring.Fixtures;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CountryTest {

    @Test
    public void canInitializeWithStateConnectionMapping() {
        String expected = """
                (State A: WHITE) -> [(State B: WHITE), (State C: WHITE)]
                (State B: WHITE) -> [(State A: WHITE)]
                (State C: WHITE) -> [(State A: WHITE)]
                """;
        Country country = buildCountry(3);
        assertEquals(expected, country.toString());
    }

    @Test
    public void newCountryIsNotColored() {
        Country country = buildCountry(3);
        assertTrue(country.hasStateWithUnassignedColor());
        assertFalse(country.isColored());
    }

    @Test
    public void canAssignColorWhenValid() {
        Country countryWith2Colors = buildCountry(2);
        Country countryWith3Colors = buildCountry(3);

        Optional<Country> colored1With2Colors = countryWith2Colors.setColor(Color.forIndex(1), Fixtures.STATE_A);
        Optional<Country> colored1With3Colors = countryWith3Colors.setColor(Color.forIndex(1), Fixtures.STATE_A);
        assertTrue(colored1With2Colors.isPresent());
        assertTrue(colored1With3Colors.isPresent());

        Optional<Country> colored2With2Colors = colored1With2Colors.get().setColor(Color.forIndex(2), Fixtures.STATE_B);
        Optional<Country> colored2With3Colors = colored1With3Colors.get().setColor(Color.forIndex(2), Fixtures.STATE_B);
        assertTrue(colored2With2Colors.isPresent());
        assertTrue(colored2With3Colors.isPresent());

        Optional<Country> colored3With2Colors = colored2With2Colors.get().setColor(Color.forIndex(1), Fixtures.STATE_C);
        Optional<Country> colored3With3Colors = colored2With3Colors.get().setColor(Color.forIndex(3), Fixtures.STATE_C);
        assertTrue(colored3With2Colors.isEmpty());
        assertTrue(colored3With3Colors.isPresent());

        assertEquals("""
                (State A: RED) -> [(State B: GREEN), (State C: BLUE)]
                (State B: GREEN) -> [(State A: RED)]
                (State C: BLUE) -> [(State A: RED)]
                """, colored3With3Colors.get().toString());
    }

    @Test
    public void coloredCountryIsColored() {
        Country country = buildCountry(3);

        Optional<Country> colored = country.setColor(Color.forIndex(1), Fixtures.STATE_A);
        colored = colored.get().setColor(Color.forIndex(2), Fixtures.STATE_B);
        colored = colored.get().setColor(Color.forIndex(3), Fixtures.STATE_C);
        assertTrue(colored.get().isColored());
    }

    private Country buildCountry(int maxNumberOfColorsAllowed) {
        return new Country(Fixtures.COUNTRY_1_STATES, maxNumberOfColorsAllowed);
    }
}
