package com.aiebt.sudoku.core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

abstract class CellCollection {

    public static final int SIZE = Sudoku.fieldLength();

    protected final List<CellValue> cells;

    CellCollection() {
        this.cells = new ArrayList<>();
        for (int i = 0; i < SIZE; i++) {
            cells.add(new CellValue(0));
        }
    }

    void setCell(int col, CellValue cell) {
        cells.set(col, cell);
    }

    boolean isValid() {
        List<CellValue> cellsWithValue = cells.stream().filter(CellValue::hasAssignedValue).collect(Collectors.toList());
        return cellsWithValue.size() == new HashSet<>(cellsWithValue).size();
    }

    List<CellValue> assignedValues() {
        return cells.stream().filter(CellValue::hasAssignedValue).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return cells.stream().reduce(
                "",
                (accumulated, cell) -> accumulated + cell.toString(),
                (a, b) -> a + b
        ).replaceAll(" ", "-");
    }
}
