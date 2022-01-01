package com.aiebt.sudoku.printer;

import com.aiebt.sudoku.core.Sudoku;

public class SudokuPrinter {

    private final Sudoku sudoku;

    public SudokuPrinter(Sudoku sudoku) {
        this.sudoku = sudoku;
    }

    public void print() {
        System.out.print(printAsString());
    }

    public String printAsString() {
        StringBuilder field = new StringBuilder();
        for (int row = 0; row < Sudoku.fieldLength() + 1; row++) {
            if (isBeginningOfQuadrant(row)) {
                field.append(printRow(row, LineType.forRow(row))).append('\n');
            }
            if (row < Sudoku.fieldLength()) {
                field.append(printRow(row, LineType.VALUE)).append('\n');
            }
        }
        return field.toString();
    }

    private StringBuilder printRow(int row, LineType lineType) {
        StringBuilder line = new StringBuilder();
        for (int col = 0; col < Sudoku.fieldLength() + 1; col++) {
            if (isBeginningOfQuadrant(col)) {
                line.append(lineType.getSignForColumn(col));
            }
            if (col < Sudoku.fieldLength()) {
                int value = sudoku.getCell(row, col) != null ? sudoku.getCell(row, col).getValue() : 0;
                line.append(lineType.getValueRepresentation(value));
            }
        }
        return line;
    }

    private boolean isBeginningOfQuadrant(int columnPosition) {
        return columnPosition % Sudoku.quadrantLength() == 0;
    }
}
