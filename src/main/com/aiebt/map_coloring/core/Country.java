package com.aiebt.map_coloring.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class Country {

    private final States states = new States();
    private final int maxNumberOfColorsAllowed;

    public Country(Map<String, Set<String>> stateNamesWithNeighbors, int maxNumberOfColorsAllowed) {
        this(stateNamesWithNeighbors, maxNumberOfColorsAllowed, Map.of());
    }

    private Country(Country country) {
        this(
                country.states.getNeighborMapping(),
                country.maxNumberOfColorsAllowed,
                country.states.getColors()
        );
    }

    public Country(Map<String, Set<String>> stateNamesWithNeighbors, int maxNumberOfColorsAllowed, Map<String, Color> stateColors) {
        this.maxNumberOfColorsAllowed = maxNumberOfColorsAllowed;

        Map<String, State> statesByName = new HashMap<>(stateNamesWithNeighbors.size());
        stateNamesWithNeighbors.forEach((stateName, neighborsNames) -> {
            State state = new State(stateName, stateColors.getOrDefault(stateName, Color.getUnassignedColor()));
            statesByName.put(stateName, state);
        });

        stateNamesWithNeighbors.forEach((stateName, neighborsNames) -> {
            State state = statesByName.get(stateName);
            neighborsNames.forEach(neighborsName -> state.addNeighbor(statesByName.get(neighborsName)));
            states.add(state);
        });
    }

    public boolean hasStateWithUnassignedColor() {
        return states.hasStateWithUnassignedColor();
    }

    public boolean isColored() {
        return states.isColored(maxNumberOfColorsAllowed);
    }

    public Optional<Country> setColor(Color color, State state) {
        Country clone = new Country(this);
        Optional<State> maybeStateToSetColor = clone.states.getStateByName(state.getName());

        if (maybeStateToSetColor.isEmpty()) {
            return Optional.empty();
        }

        Optional<State> maybeColoredState = maybeStateToSetColor.get().setColor(color, maxNumberOfColorsAllowed);

        if (maybeColoredState.isEmpty()) {
            return Optional.empty();
        }

        clone.states.add(maybeColoredState.get());
        return Optional.of(clone);
    }

    public State getNextUncoloredState() {
        return states.getNextUncoloredState();
    }

    public int getMaxNumberOfColorsAllowed() {
        return maxNumberOfColorsAllowed;
    }

    @Override
    public String toString() {
        return states.toString();
    }
}
