package com.aiebt.map_coloring.solver;

import com.aiebt.map_coloring.Fixtures;
import com.aiebt.map_coloring.core.Country;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MapColoringSolverTest {

    @Test
    public void canSolve() {
        Country coloredCountry = new MapColoringSolver(Fixtures.UNCOLORED_COUNTRY_1).execute();
        assertTrue(coloredCountry.isColored());
    }
}
