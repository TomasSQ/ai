package com.aiebt.sudoku.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class QuadrantTest {

    @Test
    public void itKnownsQuadrantsFromRowCol() {
        assertEquals(0, Quadrant.of(0, 0));
        assertEquals(0, Quadrant.of(0, 1));
        assertEquals(0, Quadrant.of(0, 2));
        assertEquals(0, Quadrant.of(1, 0));
        assertEquals(0, Quadrant.of(1, 1));
        assertEquals(0, Quadrant.of(1, 2));
        assertEquals(0, Quadrant.of(2, 0));
        assertEquals(0, Quadrant.of(2, 1));
        assertEquals(0, Quadrant.of(2, 2));

        assertEquals(3, Quadrant.of(3, 0));
        assertEquals(3, Quadrant.of(3, 1));
        assertEquals(3, Quadrant.of(3, 2));
        assertEquals(3, Quadrant.of(4, 0));
        assertEquals(3, Quadrant.of(4, 1));
        assertEquals(3, Quadrant.of(4, 2));
        assertEquals(3, Quadrant.of(5, 0));
        assertEquals(3, Quadrant.of(5, 1));
        assertEquals(3, Quadrant.of(5, 2));

        assertEquals(6, Quadrant.of(6, 0));
        assertEquals(6, Quadrant.of(6, 1));
        assertEquals(6, Quadrant.of(6, 2));
        assertEquals(6, Quadrant.of(7, 0));
        assertEquals(6, Quadrant.of(7, 1));
        assertEquals(6, Quadrant.of(7, 2));
        assertEquals(6, Quadrant.of(8, 0));
        assertEquals(6, Quadrant.of(8, 1));
        assertEquals(6, Quadrant.of(8, 2));

        assertEquals(1, Quadrant.of(0, 3));
        assertEquals(1, Quadrant.of(0, 4));
        assertEquals(1, Quadrant.of(0, 5));
        assertEquals(1, Quadrant.of(1, 3));
        assertEquals(1, Quadrant.of(1, 4));
        assertEquals(1, Quadrant.of(1, 5));
        assertEquals(1, Quadrant.of(2, 3));
        assertEquals(1, Quadrant.of(2, 4));
        assertEquals(1, Quadrant.of(2, 5));

        assertEquals(4, Quadrant.of(3, 3));
        assertEquals(4, Quadrant.of(3, 4));
        assertEquals(4, Quadrant.of(3, 5));
        assertEquals(4, Quadrant.of(4, 3));
        assertEquals(4, Quadrant.of(4, 4));
        assertEquals(4, Quadrant.of(4, 5));
        assertEquals(4, Quadrant.of(5, 3));
        assertEquals(4, Quadrant.of(5, 4));
        assertEquals(4, Quadrant.of(5, 5));

        assertEquals(7, Quadrant.of(6, 3));
        assertEquals(7, Quadrant.of(6, 4));
        assertEquals(7, Quadrant.of(6, 5));
        assertEquals(7, Quadrant.of(7, 3));
        assertEquals(7, Quadrant.of(7, 4));
        assertEquals(7, Quadrant.of(7, 5));
        assertEquals(7, Quadrant.of(8, 3));
        assertEquals(7, Quadrant.of(8, 4));
        assertEquals(7, Quadrant.of(8, 5));

        assertEquals(2, Quadrant.of(0, 6));
        assertEquals(2, Quadrant.of(0, 7));
        assertEquals(2, Quadrant.of(0, 8));
        assertEquals(2, Quadrant.of(1, 6));
        assertEquals(2, Quadrant.of(1, 7));
        assertEquals(2, Quadrant.of(1, 8));
        assertEquals(2, Quadrant.of(2, 6));
        assertEquals(2, Quadrant.of(2, 7));
        assertEquals(2, Quadrant.of(2, 8));

        assertEquals(5, Quadrant.of(3, 6));
        assertEquals(5, Quadrant.of(3, 7));
        assertEquals(5, Quadrant.of(3, 8));
        assertEquals(5, Quadrant.of(4, 6));
        assertEquals(5, Quadrant.of(4, 7));
        assertEquals(5, Quadrant.of(4, 8));
        assertEquals(5, Quadrant.of(5, 6));
        assertEquals(5, Quadrant.of(5, 7));
        assertEquals(5, Quadrant.of(5, 8));

        assertEquals(8, Quadrant.of(6, 6));
        assertEquals(8, Quadrant.of(6, 7));
        assertEquals(8, Quadrant.of(6, 8));
        assertEquals(8, Quadrant.of(7, 6));
        assertEquals(8, Quadrant.of(7, 7));
        assertEquals(8, Quadrant.of(7, 8));
        assertEquals(8, Quadrant.of(8, 6));
        assertEquals(8, Quadrant.of(8, 7));
        assertEquals(8, Quadrant.of(8, 8));
    }

    @Test
    public void toStringReturnsNicelyShapedQuadrant() {
        Quadrant quadrant = new Quadrant();
        quadrant.setCell(0, 0, new CellValue(1));
        quadrant.setCell(0, 1, new CellValue(2));
        quadrant.setCell(0, 2, new CellValue(3));
        quadrant.setCell(1, 0, new CellValue(4));
        quadrant.setCell(1, 1, new CellValue(5));
        quadrant.setCell(1, 2, new CellValue(6));
        quadrant.setCell(2, 0, new CellValue(7));
        quadrant.setCell(2, 1, new CellValue(8));
        quadrant.setCell(2, 2, new CellValue(9));
        assertEquals("[1, 2, 3, 4, 5, 6, 7, 8, 9]", quadrant.toString());

        quadrant.setCell(0, 3, new CellValue(9));
        quadrant.setCell(0, 4, new CellValue(8));
        quadrant.setCell(0, 5, new CellValue(7));
        quadrant.setCell(1, 3, new CellValue(6));
        quadrant.setCell(1, 4, new CellValue(5));
        quadrant.setCell(1, 5, new CellValue(4));
        quadrant.setCell(2, 3, new CellValue(3));
        quadrant.setCell(2, 4, new CellValue(2));
        quadrant.setCell(2, 5, new CellValue(1));
        assertEquals("[9, 8, 7, 6, 5, 4, 3, 2, 1]", quadrant.toString());

        quadrant.setCell(0, 6, new CellValue(1));
        quadrant.setCell(0, 7, new CellValue(2));
        quadrant.setCell(0, 8, new CellValue(3));
        quadrant.setCell(1, 6, new CellValue(4));
        quadrant.setCell(1, 7, new CellValue(5));
        quadrant.setCell(1, 8, new CellValue(6));
        quadrant.setCell(2, 6, new CellValue(7));
        quadrant.setCell(2, 7, new CellValue(8));
        quadrant.setCell(2, 8, new CellValue(9));
        assertEquals("[1, 2, 3, 4, 5, 6, 7, 8, 9]", quadrant.toString());

        quadrant.setCell(3, 0, new CellValue(1));
        quadrant.setCell(3, 1, new CellValue(2));
        quadrant.setCell(3, 2, new CellValue(3));
        quadrant.setCell(4, 0, new CellValue(4));
        quadrant.setCell(4, 1, new CellValue(5));
        quadrant.setCell(4, 2, new CellValue(6));
        quadrant.setCell(5, 0, new CellValue(7));
        quadrant.setCell(5, 1, new CellValue(8));
        quadrant.setCell(5, 2, new CellValue(9));
        assertEquals("[1, 2, 3, 4, 5, 6, 7, 8, 9]", quadrant.toString());

        quadrant.setCell(3, 3, new CellValue(9));
        quadrant.setCell(3, 4, new CellValue(8));
        quadrant.setCell(3, 5, new CellValue(7));
        quadrant.setCell(4, 3, new CellValue(6));
        quadrant.setCell(4, 4, new CellValue(5));
        quadrant.setCell(4, 5, new CellValue(4));
        quadrant.setCell(5, 3, new CellValue(3));
        quadrant.setCell(5, 4, new CellValue(2));
        quadrant.setCell(5, 5, new CellValue(1));
        assertEquals("[9, 8, 7, 6, 5, 4, 3, 2, 1]", quadrant.toString());

        quadrant.setCell(3, 6, new CellValue(1));
        quadrant.setCell(3, 7, new CellValue(2));
        quadrant.setCell(3, 8, new CellValue(3));
        quadrant.setCell(4, 6, new CellValue(4));
        quadrant.setCell(4, 7, new CellValue(5));
        quadrant.setCell(4, 8, new CellValue(6));
        quadrant.setCell(5, 6, new CellValue(7));
        quadrant.setCell(5, 7, new CellValue(8));
        quadrant.setCell(5, 8, new CellValue(9));
        assertEquals("[1, 2, 3, 4, 5, 6, 7, 8, 9]", quadrant.toString());

        quadrant.setCell(6, 0, new CellValue(1));
        quadrant.setCell(6, 1, new CellValue(2));
        quadrant.setCell(6, 2, new CellValue(3));
        quadrant.setCell(7, 0, new CellValue(4));
        quadrant.setCell(7, 1, new CellValue(5));
        quadrant.setCell(7, 2, new CellValue(6));
        quadrant.setCell(8, 0, new CellValue(7));
        quadrant.setCell(8, 1, new CellValue(8));
        quadrant.setCell(8, 2, new CellValue(9));
        assertEquals("[1, 2, 3, 4, 5, 6, 7, 8, 9]", quadrant.toString());

        quadrant.setCell(6, 3, new CellValue(9));
        quadrant.setCell(6, 4, new CellValue(8));
        quadrant.setCell(6, 5, new CellValue(7));
        quadrant.setCell(7, 3, new CellValue(6));
        quadrant.setCell(7, 4, new CellValue(5));
        quadrant.setCell(7, 5, new CellValue(4));
        quadrant.setCell(8, 3, new CellValue(3));
        quadrant.setCell(8, 4, new CellValue(2));
        quadrant.setCell(8, 5, new CellValue(1));
        assertEquals("[9, 8, 7, 6, 5, 4, 3, 2, 1]", quadrant.toString());

        quadrant.setCell(6, 6, new CellValue(1));
        quadrant.setCell(6, 7, new CellValue(2));
        quadrant.setCell(6, 8, new CellValue(3));
        quadrant.setCell(7, 6, new CellValue(4));
        quadrant.setCell(7, 7, new CellValue(5));
        quadrant.setCell(7, 8, new CellValue(6));
        quadrant.setCell(8, 6, new CellValue(7));
        quadrant.setCell(8, 7, new CellValue(8));
        quadrant.setCell(8, 8, new CellValue(9));
        assertEquals("[1, 2, 3, 4, 5, 6, 7, 8, 9]", quadrant.toString());
    }
}
