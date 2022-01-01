package com.aiebt.sudoku.solver;

import com.aiebt.sudoku.Fixtures;
import com.aiebt.sudoku.core.Sudoku;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SudokuSolverTest {

    @Test
    public void canSolveSudokus() {
        Sudoku sudoku = new Sudoku(Fixtures.UNSOLVED_SUDOKU_1);
        Sudoku solved = new SudokuSolver(sudoku).solve();
        assertEquals(Fixtures.SOLVED_SUDOKU_1, solved.toString());
        assertTrue(solved.isSolved());

        sudoku = new Sudoku(Fixtures.UNSOLVED_SUDOKU_2);
        solved = new SudokuSolver(sudoku).solve();
        assertEquals(Fixtures.SOLVED_SUDOKU_2, solved.toString());
        assertTrue(solved.isSolved());

        sudoku = new Sudoku(Fixtures.UNSOLVED_SUDOKU_3);
        solved = new SudokuSolver(sudoku).solve();
        assertEquals(Fixtures.SOLVED_SUDOKU_3, solved.toString());
        assertTrue(solved.isSolved());
    }
}
