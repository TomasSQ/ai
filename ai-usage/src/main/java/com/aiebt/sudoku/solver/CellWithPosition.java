package com.aiebt.sudoku.solver;

import com.aiebt.sudoku.core.Cell;

class CellWithPosition {

    final Cell cell;
    final int row;
    final int col;

    CellWithPosition(Cell cell, int row, int col) {
        this.cell = cell;
        this.row = row;
        this.col = col;
    }
}
