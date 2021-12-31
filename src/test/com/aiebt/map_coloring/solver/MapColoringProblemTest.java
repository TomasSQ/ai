package com.aiebt.map_coloring.solver;

import com.aiebt.map_coloring.Fixtures;
import com.aiebt.map_coloring.core.Color;
import com.aiebt.map_coloring.core.Country;
import com.aiebt.map_coloring.core.State;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MapColoringProblemTest {

    @Test
    public void delegateIsSolved() {
        assertFalse(new MapColoringProblem(Fixtures.UNCOLORED_COUNTRY_1).isSolved());
        assertTrue(new MapColoringProblem(Fixtures.COLORED_COUNTRY_1).isSolved());
    }

    @Test
    public void nextUnassignedVariableIsTheNextStateInline() {
        Country unsolved = new Country(Fixtures.COUNTRY_1_STATES, 3)
                .setColor(Color.RED, Fixtures.STATE_A).get()
                .setColor(Color.BLUE, Fixtures.STATE_C).get();
        State nextUnassignedVariable = new MapColoringProblem(unsolved).getNextUnassignedVariable();

        assertEquals("(State B: WHITE) -> [(State A: RED)]", nextUnassignedVariable.toString());
    }

    @Test
    public void delegatesVariablePossibleValuesToState() {
        Country unsolved = new Country(Fixtures.COUNTRY_1_STATES, 3)
                .setColor(Color.RED, Fixtures.STATE_A).get()
                .setColor(Color.BLUE, Fixtures.STATE_C).get();

        MapColoringProblem problem = new MapColoringProblem(unsolved);
        State nextUnassignedVariable = problem.getNextUnassignedVariable();
        Collection<Color> possibleValuesForVariable = problem.getPossibleValuesForVariable(nextUnassignedVariable);

        assertEquals(2, possibleValuesForVariable.size());
        assertTrue(possibleValuesForVariable.contains(Color.GREEN));
        assertTrue(possibleValuesForVariable.contains(Color.BLUE));
    }

    @Test
    public void delegatesDefineVariableToSetValue() {
        Country unsolved = new Country(Fixtures.COUNTRY_1_STATES, 3)
                .setColor(Color.RED, Fixtures.STATE_A).get()
                .setColor(Color.BLUE, Fixtures.STATE_C).get();

        MapColoringProblem problem = new MapColoringProblem(unsolved);
        State nextUnassignedVariable = problem.getNextUnassignedVariable();
        Optional<MapColoringProblem> solved = problem.defineVariable(nextUnassignedVariable, Color.GREEN);

        assertTrue(solved.isPresent());
        assertTrue(solved.get().isSolved());
        assertFalse(problem.isSolved());
    }
}

