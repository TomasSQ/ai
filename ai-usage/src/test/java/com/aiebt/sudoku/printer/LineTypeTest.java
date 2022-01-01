package com.aiebt.sudoku.printer;

import com.aiebt.sudoku.core.Sudoku;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LineTypeTest {

    @Test
    public void knowsWhenToUseEachType() {
        assertEquals(LineType.TOP_LINE, LineType.forRow(0));
        assertEquals(LineType.MIDDLE_LINE, LineType.forRow(2));
        assertEquals(LineType.BOTTOM_LINE, LineType.forRow(Sudoku.fieldLength()));
    }

    @Test
    public void hasCorrectFirstSign() {
        assertEquals("┏━", LineType.TOP_LINE.getSignForColumn(0));
        assertEquals("┣━", LineType.MIDDLE_LINE.getSignForColumn(0));
        assertEquals("┗━", LineType.BOTTOM_LINE.getSignForColumn(0));
    }

    @Test
    public void hasCorrectMiddleSign() {
        assertEquals("━┳━", LineType.TOP_LINE.getSignForColumn(Sudoku.quadrantLength()));
        assertEquals("━╋━", LineType.MIDDLE_LINE.getSignForColumn(Sudoku.quadrantLength()));
        assertEquals("━┻━", LineType.BOTTOM_LINE.getSignForColumn(Sudoku.quadrantLength()));
    }

    @Test
    public void hasCorrectEndSign() {
        assertEquals("━┓", LineType.TOP_LINE.getSignForColumn(Sudoku.fieldLength()));
        assertEquals("━┫", LineType.MIDDLE_LINE.getSignForColumn(Sudoku.fieldLength()));
        assertEquals("━┛", LineType.BOTTOM_LINE.getSignForColumn(Sudoku.fieldLength()));
    }

    @Test
    public void hasCorrectValueRepresentation() {
        assertEquals("━━━", LineType.TOP_LINE.getValueRepresentation(1));
        assertEquals("━━━", LineType.MIDDLE_LINE.getValueRepresentation(1));
        assertEquals("━━━", LineType.BOTTOM_LINE.getValueRepresentation(1));
        assertEquals(" 1 ", LineType.VALUE.getValueRepresentation(1));
        assertEquals("   ", LineType.VALUE.getValueRepresentation(0));
    }
}
