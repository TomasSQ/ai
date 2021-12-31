package com.aiebt.map_coloring.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StateTest {

    @Test
    public void knowsWhenStateHasNoColorAssigned() {
        assertFalse(new State("State A", Color.WHITE).hasColorAssigned());
        assertTrue(new State("State B", Color.BLUE).hasColorAssigned());
    }

    @Test
    public void equalStatesAreEqualByName() {
        assertEquals(new State("A", Color.WHITE), new State("A", Color.RED));
        assertNotEquals(new State("B", Color.WHITE), new State("A", Color.RED));
    }

    @Test
    public void hashCodeComesFromName() {
        assertEquals("State A".hashCode(), new State("State A", Color.WHITE).hashCode());
    }

    @Test
    public void canAddNeighbors() {
        State stateA = new State("State A", Color.WHITE);
        State stateB = new State("State B", Color.WHITE);
        State state = new State("The State", Color.WHITE);

        assertEquals(0, state.getNeighborsCount());
        state.addNeighbor(stateA);
        assertEquals(1, state.getNeighborsCount());
        state.addNeighbor(stateB);
        assertEquals(2, state.getNeighborsCount());
        state.addNeighbor(stateB);
        assertEquals(2, state.getNeighborsCount());
        state.addNeighbor(state);
        assertEquals(2, state.getNeighborsCount());
    }

    @Test
    public void knowsPossibleColors() {
        State state = buildStateWithNeighbors();

        assertEquals(0, state.getPossibleColors(2).size());
        assertEquals(1, state.getPossibleColors(3).size());
        assertTrue(state.getPossibleColors(3).contains(Color.forIndex(3)));
        assertEquals(2, state.getPossibleColors(4).size());
        assertTrue(state.getPossibleColors(4).contains(Color.forIndex(3)));
        assertTrue(state.getPossibleColors(4).contains(Color.forIndex(4)));
    }

    @Test
    public void canSetColorWhenValid() {
        State state = buildStateWithNeighbors();

        assertTrue(state.setColor(Color.forIndex(1), 3).isEmpty());
        assertTrue(state.setColor(Color.forIndex(2), 3).isEmpty());
        assertTrue(state.setColor(Color.forIndex(3), 3).isPresent());
        assertTrue(state.setColor(Color.forIndex(4), 4).isPresent());
        assertTrue(state.setColor(Color.forIndex(4), 5).isPresent());
    }

    @Test
    public void toStringShowsColorsAndNeighbors() {
        State state = buildStateWithNeighbors();

        assertEquals("(The State: WHITE) -> [(State A: RED), (State B: GREEN)]", state.toString());
    }

    private State buildStateWithNeighbors() {
        State stateA = new State("State A", Color.forIndex(1));
        State stateB = new State("State B", Color.forIndex(2));
        State state = new State("The State", Color.WHITE);

        state.addNeighbor(stateA).addNeighbor(stateB);
        return state;
    }
}
