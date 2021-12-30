package com.aiebt.sudoku.core;

import java.util.HashSet;
import java.util.Set;

class CellValue {

    private final int EMPTY_VALUE = 0;
    private final int MAX_VALUE = 9;

    private final int value;

    CellValue(int value) {
        if (value < EMPTY_VALUE || value > MAX_VALUE) {
            throw new IllegalArgumentException(String.format("CellValue value should be between %d and %d, but got %d", EMPTY_VALUE, MAX_VALUE, value));
        }
        this.value = value;
    }

    boolean hasAssignedValue() {
        return value != EMPTY_VALUE;
    }

    Set<Integer> getPossibleValues(Row row, Column column, Quadrant quadrant) {
        if (hasAssignedValue()) {
            return Set.of();
        }
        Set<CellValue> takenValues = new HashSet<>(row.assignedValues());
        takenValues.addAll(column.assignedValues());
        takenValues.addAll(quadrant.assignedValues());

        Set<Integer> possibleValues = new HashSet<>();
        for (int i = EMPTY_VALUE + 1; i <= MAX_VALUE; i++) {
            if (!takenValues.contains(new CellValue(i))) {
                possibleValues.add(i);
            }
        }
        return possibleValues;
    }

    int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return hasAssignedValue() ? String.format("%d", value) : " ";
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof CellValue)) {
            return false;
        }
        return ((CellValue) o).value == value;
    }

    @Override
    public int hashCode() {
        return Integer.valueOf(value).hashCode();
    }
}
