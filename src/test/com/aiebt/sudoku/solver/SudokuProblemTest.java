package com.aiebt.sudoku.solver;

import com.aiebt.sudoku.Fixtures;
import com.aiebt.sudoku.core.Sudoku;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class SudokuProblemTest {

    @Test
    public void delegateSudokuIsSolved() {
        Sudoku unsolved = new Sudoku(Fixtures.UNSOLVED_SUDOKU_3);
        Sudoku solved = new Sudoku(Fixtures.SOLVED_SUDOKU_3);

        assertFalse(new SudokuProblem(unsolved).isSolved());
        assertTrue(new SudokuProblem(solved).isSolved());
    }

    @Test
    public void nextUnassignedVariableIsTheNextZeroInline() {
        Sudoku unsolved = new Sudoku(Fixtures.SOLVED_SUDOKU_3.substring(0, 15) + "0" + Fixtures.SOLVED_SUDOKU_3.substring(16));

        CellWithPosition nextUnassignedVariable = new SudokuProblem(unsolved).getNextUnassignedVariable();

        // 15 / 9 = 1 * 9 + 6 (row + col)
        assertEquals(1, nextUnassignedVariable.row);
        assertEquals(6, nextUnassignedVariable.col);
        assertEquals(0, nextUnassignedVariable.cell.getValue());
    }

    @Test
    public void delegatesVariablePossibleValuesToCell() {
        Sudoku unsolved = new Sudoku(Fixtures.SOLVED_SUDOKU_3.substring(0, 15) + "0" + Fixtures.SOLVED_SUDOKU_3.substring(16));

        SudokuProblem sudokuProblem = new SudokuProblem(unsolved);
        CellWithPosition nextUnassignedVariable = sudokuProblem.getNextUnassignedVariable();
        Collection<Integer> possibleValuesForVariable = sudokuProblem.getPossibleValuesForVariable(nextUnassignedVariable);

        assertEquals(1, possibleValuesForVariable.size());
        assertEquals(3, new ArrayList<>(possibleValuesForVariable).get(0));
    }

    @Test
    public void delegatesDefineVariableToSetValue() {
        Sudoku unsolved = new Sudoku(Fixtures.SOLVED_SUDOKU_3.substring(0, 15) + "0" + Fixtures.SOLVED_SUDOKU_3.substring(16));

        SudokuProblem sudokuProblem = new SudokuProblem(unsolved);
        CellWithPosition nextUnassignedVariable = sudokuProblem.getNextUnassignedVariable();
        Optional<SudokuProblem> solved = sudokuProblem.defineVariable(nextUnassignedVariable, 3);

        assertTrue(solved.isPresent());
        assertTrue(solved.get().isSolved());
        assertFalse(sudokuProblem.isSolved());
    }
}
