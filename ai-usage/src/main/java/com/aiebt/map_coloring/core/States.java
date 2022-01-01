package com.aiebt.map_coloring.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.function.Predicate.not;

public class States {

    private final Set<State> states = new HashSet<>();

    void add(State state) {
        states.remove(state);
        states.add(state);
        states.forEach(existentState -> existentState.replaceNeighbor(state));
    }

    boolean hasStateWithUnassignedColor() {
        return states.stream().anyMatch(not(State::hasColorAssigned));
    }

    boolean isColored(int maxNumberOfColorsAllowed) {
        if (hasStateWithUnassignedColor()) {
            return false;
        }
        int colorsUsed = states.stream()
                .map(State::getColor)
                .collect(Collectors.toSet())
                .size();
        return colorsUsed <= maxNumberOfColorsAllowed;
    }

    State getNextUncoloredState() {
        return states.stream().filter(not(State::hasColorAssigned)).findFirst().orElseThrow(() -> new IllegalStateException("Map is fully colored"));
    }

    Map<String, Set<String>> getNeighborMapping() {
        return states.stream().reduce(
                new HashMap<>(),
                (acc, state) -> {
                    acc.putIfAbsent(state.getName(), new HashSet<>());
                    acc.get(state.getName()).addAll(state.getNeighborsNames());
                    return acc;
                },
                (a, b) -> {
                    b.keySet().forEach(stateName -> {
                        a.putIfAbsent(stateName, new HashSet<>());
                        a.get(stateName).addAll(b.get(stateName));
                    });
                    return a;
                }
        );
    }

    Map<String, Color> getColors() {
        return states.stream().reduce(
                new HashMap<>(),
                (acc, state) -> {
                    acc.put(state.getName(), state.getColor());
                    return acc;
                },
                (a, b) -> {
                    a.putAll(b);
                    return a;
                }
        );
    }

    Optional<State> getStateByName(String stateName) {
        return states.stream()
                .filter(state -> state.getName().equals(stateName))
                .findFirst();
    }

    @Override
    public String toString() {
        return new ArrayList<>(states)
                .stream().sorted()
                .reduce(
                        new StringBuilder(),
                        (acc, state) -> acc.append(state.toString()).append('\n'),
                        StringBuilder::append
                )
                .toString();
    }
}
