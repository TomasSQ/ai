package com.aiebt.map_coloring.core;

import java.util.Arrays;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

public enum Color {
    WHITE, RED, GREEN, BLUE, YELLOW, PINK, ORANGE, PURPLE;

    public static Set<Color> assignementColors(int maxNumberOfColorsAllowed) {
        return Arrays.stream(Color.values())
                .filter(color -> !color.isUnassignedColor())
                .limit(maxNumberOfColorsAllowed)
                .collect(toSet());
    }

    public static Color forIndex(int i) {
        if (i <= 0 || i >= Color.values().length) {
            throw new IllegalArgumentException("index must be between (inclusive) 1 and " + (Color.values().length - 1));
        }
        return Color.values()[i];
    }

    public boolean isUnassignedColor() {
        return this == Color.getUnassignedColor();
    }

    public static Color getUnassignedColor() {
        return WHITE;
    }
}
