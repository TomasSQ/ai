package com.aiebt.map_coloring.core;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ColorTest {

    @Test
    public void whiteIsTheUnassignedColor() {
        assertEquals(Color.WHITE, Color.getUnassignedColor());
        assertTrue(Color.WHITE.isUnassignedColor());
        Arrays.stream(Color.values())
                .filter(color -> color != Color.WHITE)
                .forEach(color -> assertFalse(color.isUnassignedColor()));
    }

    @Test
    public void assignmentColorsAreLimitedAndWithoutWhite() {
        for (int i = 1; i < Color.values().length - 1; i++) {
            assertEquals(i, Color.assignementColors(i).size());
        }
    }

    @Test
    public void getColorByIndex() {
        assertEquals(Color.RED, Color.forIndex(1));
        assertEquals(Color.GREEN, Color.forIndex(2));
        assertEquals(Color.BLUE, Color.forIndex(3));
        assertEquals(Color.YELLOW, Color.forIndex(4));
        assertEquals(Color.PINK, Color.forIndex(5));
        assertEquals(Color.ORANGE, Color.forIndex(6));
        assertEquals(Color.PURPLE, Color.forIndex(7));
    }
}
