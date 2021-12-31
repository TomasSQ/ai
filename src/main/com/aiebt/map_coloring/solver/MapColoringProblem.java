package com.aiebt.map_coloring.solver;

import com.aiebt.ai.backtracking.Problem;
import com.aiebt.map_coloring.core.Color;
import com.aiebt.map_coloring.core.Country;
import com.aiebt.map_coloring.core.State;

import java.util.Collection;
import java.util.Optional;

public class MapColoringProblem implements Problem<State, Color> {

    private final Country country;

    public MapColoringProblem(Country country) {
        this.country = country;
    }

    @Override
    public boolean isSolved() {
        return country.isColored();
    }

    @Override
    public State getNextUnassignedVariable() {
        return country.getNextUncoloredState();
    }

    @Override
    public Collection<Color> getPossibleValuesForVariable(State state) {
        return state.getPossibleColors(country.getMaxNumberOfColorsAllowed());
    }

    @Override
    @SuppressWarnings("unchecked")
    public Optional<MapColoringProblem> defineVariable(State state, Color color) {
        return country.setColor(color, state).map(MapColoringProblem::new);
    }

    public Country get() {
        return country;
    }

    @Override
    public String toString() {
        return country.toString();
    }
}
