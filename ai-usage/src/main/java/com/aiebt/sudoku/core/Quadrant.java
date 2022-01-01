package com.aiebt.sudoku.core;

public class Quadrant extends CellCollection {

    public static final int LENGTH = Sudoku.quadrantLength();

    public void setCell(int col, CellValue cell) {
        throw new IllegalStateException("Not implemented");
    }

    public void setCell(int row, int col, CellValue cell) {
        cells.set(row * LENGTH + col - Quadrant.of(row, col) * LENGTH, cell);
    }

    public static int of(int row, int col) {
        return switch (row) {
            case 0, 1, 2 -> (int) Math.floor(1.0 * col / LENGTH);
            case 3, 4, 5 -> (int) Math.floor(1.0 * col / LENGTH) + LENGTH;
            case 6, 7, 8 -> (int) Math.floor(1.0 * col / LENGTH) + 2 * LENGTH;
            default -> -1;
        };
    }

    @Override
    public String toString() {
        return cells.toString();
    }
}
