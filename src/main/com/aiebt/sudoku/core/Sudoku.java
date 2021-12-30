package com.aiebt.sudoku.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Sudoku {

    public static final int CELLS_COUNT = 81;

    private final Map<Integer, Map<Integer, CellValue>> field = new HashMap<>();
    private final Map<Integer, Row> rows = new HashMap<>();
    private final Map<Integer, Column> columns = new HashMap<>();
    private final Map<Integer, Quadrant> quadrants = new HashMap<>();

    public Sudoku(String fieldAsString) {
        int fieldLength = Sudoku.fieldLength();
        for (int row = 0; row < fieldLength; row++) {
            for (int col = 0; col < fieldLength; col++) {
                int cellValue = Integer.parseInt("" + fieldAsString.charAt(row * fieldLength + col));
                addCell(row, col, cellValue);
            }
        }
    }

    private Sudoku(Sudoku source, int row, int col, int newValue) {
        source.field.forEach((rowIndex, colSet) -> colSet.forEach((colIndex, cell) -> addCell(rowIndex, colIndex, rowIndex == row && colIndex == col ? newValue : cell.getValue())));
        if (getCell(row, col) == null) {
            addCell(row, col, newValue);
        }
    }

    public static int fieldLength() {
        return (int) Math.sqrt(Sudoku.CELLS_COUNT);
    }

    public static int quadrantLength() {
        return (int) Math.sqrt(fieldLength());
    }

    public boolean isSolved() {
        return hasValidQuadrants() && hasValidColumns() && hasValidRows() &&
                rows.entrySet().stream().allMatch(entry -> entry.getValue().assignedValues().size() == Sudoku.fieldLength());
    }

    public Cell getCell(int row, int col) {
        if (field.containsKey(row) && field.get(row).containsKey(col)) {
            return new Cell(field.get(row).get(col), rows.get(row), columns.get(col), quadrants.get(Quadrant.of(row, col)));
        }
        return null;
    }

    public Optional<Sudoku> setCell(int row, int col, int value) {
        Sudoku clone = new Sudoku(this, row, col, value);
        if (isValid(clone)) {
            return Optional.of(clone);
        }
        return Optional.empty();
    }

    @Override
    public String toString() {
        return rows.keySet().stream().map(rows::get).reduce(
                "",
                (accumulated, row) -> accumulated + row.toString(),
                (a, b) -> a + b);
    }

    private void addCell(int row, int col, int cellValue) {
        CellValue cell = new CellValue(cellValue);
        setCellToField(row, col, cell);
        setCellToRow(row, col, cell);
        setCellToCol(row, col, cell);
        setCellToQuadrant(row, col, cell);
    }

    private void setCellToField(int row, int col, CellValue cell) {
        if (!field.containsKey(row)) {
            field.put(row, new HashMap<>());
        }
        field.get(row).put(col, cell);
    }

    private void setCellToRow(int row, int col, CellValue cell) {
        if (!rows.containsKey(row)) {
            rows.put(row, new Row());
        }
        rows.get(row).setCell(col, cell);
    }

    private void setCellToCol(int row, int col, CellValue cell) {
        if (!columns.containsKey(col)) {
            columns.put(col, new Column());
        }
        columns.get(col).setCell(row, cell);
    }

    private boolean isValid(Sudoku clone) {
        return clone.hasValidColumns() && clone.hasValidRows() && clone.hasValidQuadrants();
    }

    private boolean hasValidColumns() {
        return columns.keySet().stream()
                .allMatch(key -> columns.get(key).isValid());
    }

    private boolean hasValidRows() {
        return rows.keySet().stream()
                .allMatch(key -> rows.get(key).isValid());
    }

    private boolean hasValidQuadrants() {
        return quadrants.keySet().stream()
                .allMatch(key -> quadrants.get(key).isValid());
    }

    private void setCellToQuadrant(int row, int col, CellValue cell) {
        if (!quadrants.containsKey(Quadrant.of(row, col))) {
            quadrants.put(Quadrant.of(row, col), new Quadrant());
        }
        quadrants.get(Quadrant.of(row, col)).setCell(row, col, cell);
    }
}
