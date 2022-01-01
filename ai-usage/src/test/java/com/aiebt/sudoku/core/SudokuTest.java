package com.aiebt.sudoku.core;

import com.aiebt.sudoku.reader.SudokuReader;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SudokuTest {

    @Test
    public void hasNineColumnsAndRows() {
        assertEquals(9, Sudoku.fieldLength());
    }

    @Test
    public void eachQuadrantHasSizeThree() {
        assertEquals(3, Sudoku.quadrantLength());
    }

    @Test
    public void canInitSudoku() {
        Sudoku sudoku = buildNewSudoku();
        assertEquals(1, sudoku.getCell(0, 0).getValue());
        assertEquals(2, sudoku.getCell(1, 1).getValue());
        assertEquals(3, sudoku.getCell(2, 2).getValue());
        assertEquals(1, sudoku.getCell(3, 6).getValue());
        assertEquals(2, sudoku.getCell(4, 7).getValue());
        assertEquals(3, sudoku.getCell(5, 8).getValue());
    }

    @Test
    public void canCloneWithValueAtCell() {
        Sudoku sudoku = buildNewSudoku();

        Optional<Sudoku> maybeClone = sudoku.setCell(4, 6, 4);

        assertTrue(maybeClone.isPresent());
        Sudoku clone = maybeClone.get();

        assertEquals(1, clone.getCell(3, 6).getValue());
        assertEquals(2, clone.getCell(4, 7).getValue());
        assertEquals(3, clone.getCell(5, 8).getValue());

        assertEquals(4, clone.getCell(4, 6).getValue());
        assertFalse(sudoku.getCell(4, 6).hasAssignedValue());

        assertEquals(clone.getCell(4, 6).getPossibleValues().size(), 0);
        assertEquals(clone.getCell(4, 8).getPossibleValues().size(), 5);
        assertTrue(clone.getCell(4, 8).getPossibleValues().containsAll(List.of(5, 6, 7, 8, 9)));

        maybeClone = clone.setCell(4, 8, 5);
        assertTrue(maybeClone.isPresent());
        clone = maybeClone.get();
        assertEquals(clone.getCell(4, 6).getPossibleValues().size(), 0);
        assertEquals(clone.getCell(4, 8).getPossibleValues().size(), 0);
        assertEquals(clone.getCell(3, 7).getPossibleValues().size(), 4);
        assertTrue(clone.getCell(3, 7).getPossibleValues().containsAll(List.of(6, 7, 8, 9)));
    }

    @Test
    public void canNotCloneWithInvalidValueAtCell() {
        Sudoku sudoku = buildNewSudoku();

        Optional<Sudoku> maybeClone = sudoku.setCell(1, 0, 3);

        assertFalse(maybeClone.isPresent());
    }

    private Sudoku buildNewSudoku() {
        return new SudokuReader().from("""
        1 0 0  0 0 0  0 0 0
        0 2 0  0 0 0  0 0 0
        0 0 3  0 0 0  0 0 0
        
        0 0 0  0 0 0  1 0 0
        0 0 0  0 0 0  0 2 0
        0 0 0  0 0 0  0 0 3
        
        0 0 0  0 0 0  0 0 0
        0 0 0  0 0 0  0 0 0
        0 0 0  0 0 0  0 0 0
        """);
    }


}
