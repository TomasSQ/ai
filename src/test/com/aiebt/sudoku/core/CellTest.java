package com.aiebt.sudoku.core;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CellTest {

    @Test
    public void throwsOnNegativeValues() {
        assertThrows(IllegalArgumentException.class, () -> new CellValue(-1));
        assertThrows(IllegalArgumentException.class, () -> new CellValue(-10));
        assertThrows(IllegalArgumentException.class, () -> new CellValue(-91283));
    }

    @Test
    public void throwsOnTooBigNumbers() {
        assertThrows(IllegalArgumentException.class, () -> new CellValue(10));
        assertThrows(IllegalArgumentException.class, () -> new CellValue(11));
        assertThrows(IllegalArgumentException.class, () -> new CellValue(91283));
    }

    @Test
    public void toStringReturnsCellsValue() {
        for (int i = 1; i <= 9; i++) {
            assertEquals(i + "", new CellValue(i).toString());
        }
    }

    @Test
    public void toStringReturnsSpaceForEmptyCells() {
        assertEquals(" ", new CellValue(0).toString());
    }

    @Test
    public void itKnowsWhenHasAssignedValue() {
        for (int i = 1; i <= 9; i++) {
            assertTrue(new CellValue(i).hasAssignedValue());
        }
        assertFalse(new CellValue(0).hasAssignedValue());
    }

    @Test
    public void equalCellsAreEqual() {
        for (int i = 1; i <= 9; i++) {
            assertEquals(new CellValue(i), new CellValue(i));
        }

        assertEquals(2, new HashSet<>(List.of(new CellValue(1), new CellValue(2), new CellValue(1))).size());
    }

    @Test
    public void itKnowsItPossibleValues() {
        Row row = new Row();
        row.setCell(2, new CellValue(1));
        row.setCell(5, new CellValue(3));
        Column column = new Column();
        column.setCell(3, new CellValue(4));
        column.setCell(2, new CellValue(8));
        Quadrant quadrant = new Quadrant();
        quadrant.setCell(1, 4, new CellValue(7));

        CellValue cell = new CellValue(0);
        assertEquals(Set.of(2, 5, 6, 9), cell.getPossibleValues(row, column, quadrant));
    }
}
