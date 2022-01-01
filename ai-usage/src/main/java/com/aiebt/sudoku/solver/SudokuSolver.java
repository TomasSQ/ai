package com.aiebt.sudoku.solver;

import com.aiebt.ai.backtracking.BacktrackingSearch;
import com.aiebt.sudoku.core.Sudoku;

public class SudokuSolver {

    private final Sudoku sudoku;
    private final BacktrackingSearch backtrackingSearch;

    public SudokuSolver(Sudoku sudoku) {
        this.sudoku = sudoku;
        backtrackingSearch = new BacktrackingSearch();
    }

    public Sudoku solve() {
        SudokuProblem sudokuProblem = new SudokuProblem(sudoku);
        SudokuProblem solvedSudoku = backtrackingSearch.execute(sudokuProblem)
                .orElseThrow(() -> new RuntimeException("Invalid sudoku"));
        return solvedSudoku.get();
    }
}
