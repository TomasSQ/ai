package com.aiebt.sudoku.core;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class CellCollectionTest {

    abstract CellCollection getCellCollection();

    @Test
    public void initsAsValidWithNoAssignedValues() {
        CellCollection cellCollection = getCellCollection();
        assertTrue(cellCollection.isValid());
        assertEquals(cellCollection.assignedValues().size(), 0);
    }

    @Test
    public void canSetCell() {
        CellCollection cellCollection = getCellCollection();
        cellCollection.setCell(0, new CellValue(1));
        assertEquals(cellCollection.assignedValues().size(), 1);
        assertTrue(cellCollection.assignedValues().contains(new CellValue(1)));

        cellCollection.setCell(1, new CellValue(2));
        assertEquals(cellCollection.assignedValues().size(), 2);
        assertTrue(cellCollection.assignedValues().containsAll(List.of(new CellValue(1), new CellValue(2))));
    }

    @Test
    public void canOverrideCell() {
        CellCollection cellCollection = getCellCollection();
        cellCollection.setCell(0, new CellValue(1));
        assertEquals(cellCollection.assignedValues().size(), 1);
        assertTrue(cellCollection.assignedValues().contains(new CellValue(1)));

        cellCollection.setCell(0, new CellValue(2));
        assertEquals(cellCollection.assignedValues().size(), 1);
        assertTrue(cellCollection.assignedValues().contains(new CellValue(2)));

    }

    @Test
    public void itKnowsIfItIsValid() {
        CellCollection cellCollection = getCellCollection();
        cellCollection.setCell(0, new CellValue(1));
        assertTrue(cellCollection.isValid());
        cellCollection.setCell(8, new CellValue(2));
        assertTrue(cellCollection.isValid());
        cellCollection.setCell(1, new CellValue(3));
        assertTrue(cellCollection.isValid());
        cellCollection.setCell(7, new CellValue(4));
        assertTrue(cellCollection.isValid());
        cellCollection.setCell(2, new CellValue(5));
        assertTrue(cellCollection.isValid());
        cellCollection.setCell(6, new CellValue(6));
        assertTrue(cellCollection.isValid());
        cellCollection.setCell(3, new CellValue(2));
        assertFalse(cellCollection.isValid());
    }

    @Test
    public void toStringReturnsCellValuesOrDash() {
        CellCollection cellCollection = getCellCollection();
        cellCollection.setCell(0, new CellValue(1));
        cellCollection.setCell(3, new CellValue(4));
        cellCollection.setCell(7, new CellValue(2));

        assertEquals("1--4---2-", cellCollection.toString());
    }
}
