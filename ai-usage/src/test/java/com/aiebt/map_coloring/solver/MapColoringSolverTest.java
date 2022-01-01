package com.aiebt.map_coloring.solver;

import com.aiebt.map_coloring.Fixtures;
import com.aiebt.map_coloring.core.Country;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class MapColoringSolverTest {

    @Test
    public void canSolve() {
        Country coloredCountry = new MapColoringSolver(Fixtures.UNCOLORED_COUNTRY_1).execute();
        assertTrue(coloredCountry.isColored());

        coloredCountry = new MapColoringSolver(Fixtures.UNCOLORED_COUNTRY_2).execute();
        assertTrue(coloredCountry.isColored());

        coloredCountry = new MapColoringSolver(Fixtures.UNCOLORED_COUNTRY_3).execute();
        assertTrue(coloredCountry.isColored());
    }
}
