package com.aiebt.sudoku.core;

import java.util.Set;

public class Cell {

    private final CellValue cellValue;
    private final Row row;
    private final Column column;
    private final Quadrant quadrant;

    public Cell(CellValue cellValue, Row row, Column column, Quadrant quadrant) {
        this.cellValue = cellValue;
        this.row = row;
        this.column = column;
        this.quadrant = quadrant;
    }

    public Set<Integer> getPossibleValues() {
        return cellValue.getPossibleValues(row, column, quadrant);
    }

    public boolean hasAssignedValue() {
        return cellValue.hasAssignedValue();
    }

    public int getValue() {
        return cellValue.getValue();
    }

    @Override
    public String toString() {
        return cellValue.toString();
    }
}
