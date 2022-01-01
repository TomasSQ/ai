package com.aiebt.sudoku.solver;

import com.aiebt.ai.backtracking.Problem;
import com.aiebt.sudoku.core.Cell;
import com.aiebt.sudoku.core.Sudoku;

import java.util.Collection;
import java.util.Optional;

class SudokuProblem implements Problem<CellWithPosition, Integer> {

    private final Sudoku sudoku;

    SudokuProblem(Sudoku sudoku) {
        this.sudoku = sudoku;
    }

    @Override
    public boolean isSolved() {
        return sudoku.isSolved();
    }

    @Override
    public CellWithPosition getNextUnassignedVariable() {
        for (int row = 0; row < Sudoku.fieldLength(); row++) {
            for (int col = 0; col < Sudoku.fieldLength(); col++) {
                Cell cell = sudoku.getCell(row, col);

                if (!cell.hasAssignedValue()) {
                    return new CellWithPosition(cell, row, col);
                }
            }
        }
        throw new IllegalArgumentException("Problem is already solved");
    }

    @Override
    public Collection<Integer> getPossibleValuesForVariable(CellWithPosition cellWithPosition) {
        return cellWithPosition.cell.getPossibleValues();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Optional<SudokuProblem> defineVariable(CellWithPosition cellWithPosition, Integer value) {
        return sudoku.setCell(cellWithPosition.row, cellWithPosition.col, value)
                .map(SudokuProblem::new);
    }

    public Sudoku get() {
        return sudoku;
    }
}
