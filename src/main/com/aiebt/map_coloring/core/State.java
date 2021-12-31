package com.aiebt.map_coloring.core;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.toSet;

public class State implements Comparable<State> {

    private final String name;
    private final Color color;
    private final Set<State> neighbors;

    public State(String name, Color color) {
        this(name, color, new HashSet<>());
    }

    private State(String name, Color color, Set<State> neighbors) {
        this.name = name;
        this.color = color;
        this.neighbors = neighbors;
    }

    boolean hasColorAssigned() {
        return !color.isUnassignedColor();
    }

    State addNeighbor(State neighbor) {
        if (neighbor == null || this.equals(neighbor)) {
            return this;
        }
        neighbors.remove(neighbor);
        neighbors.add(neighbor);
        return this;
    }

    void replaceNeighbor(State neighbor) {
        if (neighbors.contains(neighbor)) {
            neighbors.remove(neighbor);
            neighbors.add(neighbor);
        }
    }

    public Set<Color> getPossibleColors(int maxNumberOfColorsAllowed) {
        if (hasColorAssigned()) {
            return Set.of(color);
        }

        Set<Color> usedColors = getNeighborsColors();

        return Color.assignementColors(maxNumberOfColorsAllowed).stream()
                .filter(not(usedColors::contains))
                .collect(toSet());
    }

    Optional<State> setColor(Color color, int maxNumberOfColorsAllowed) {
        State clone = new State(name, color, neighbors);
        if (clone.hasValidColor(maxNumberOfColorsAllowed)) {
            return Optional.of(clone);
        }
        return Optional.empty();
    }

    Set<String> getNeighborsNames() {
        return neighbors.stream()
                .map(state -> state.name)
                .collect(Collectors.toSet());
    }

    private boolean hasValidColor(int maxNumberOfColorsAllowed) {
        if (!hasColorAssigned()) {
            return true;
        }

        Set<Color> neighborsColors = getNeighborsColors();

        return neighborsColors.size() <= maxNumberOfColorsAllowed - 1 && !neighborsColors.contains(color);
    }

    int getNeighborsCount() {
        return neighbors.size();
    }

    String getName() {
        return name;
    }

    Color getColor() {
        return color;
    }

    @Override
    public String toString() {
        List<String> neighborsAsString = neighbors.stream()
                .sorted()
                .map(State::toStringWithoutNeighbors)
                .collect(Collectors.toList());

        return toStringWithoutNeighbors() + " -> " + neighborsAsString;
    }

    private Set<Color> getNeighborsColors() {
        return neighbors.stream()
                .filter(State::hasColorAssigned)
                .map(state -> state.color)
                .collect(toSet());
    }

    private String toStringWithoutNeighbors() {
        return "(" + name + ": " + color.name() + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof State)) {
            return false;
        }
        return ((State) o).name.equals(name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public int compareTo(State o) {
        return name.compareTo(o.name);
    }
}
