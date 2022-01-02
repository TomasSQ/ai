package com.aiebt.binary_operations.core;

import java.util.Arrays;
import java.util.List;

public enum BinaryOperations {
    XOR(0.0d, List.of(0.0d, 1.0d, 1.0d, 0.0d)),
    NOT_XOR(0.2d, List.of(1.0d, 0.0d, 0.0d, 1.0d)),
    OR(0.4d, List.of(1.0d, 1.0d, 1.0d, 0.0d)),
    NOT_OR(0.6d, List.of(0.0d, 0.0d, 0.0d, 1.0d)),
    AND(0.8d, List.of(1.0d, 0.0d, 0.0d, 0.0d)),
    NOT_AND(1.0d, List.of(0.0d, 1.0d, 1.0d, 1.0d));

    public final double value;
    public final List<Double> output;

    BinaryOperations(double value, List<Double> output) {
        this.value = value;
        this.output = output;
    }

    public static BinaryOperations from(double value) {
        return Arrays.stream(BinaryOperations.values())
                .filter(binaryOperation -> binaryOperation.value == value)
                .findFirst()
                .orElseThrow();
    }
}
