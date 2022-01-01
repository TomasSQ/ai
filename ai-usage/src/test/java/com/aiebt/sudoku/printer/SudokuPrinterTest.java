package com.aiebt.sudoku.printer;

import com.aiebt.sudoku.core.Sudoku;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SudokuPrinterTest {

    @Test
    public void printSolvedSudoku() {
        String expected = """ 
            ┏━━━━━━━━━━━┳━━━━━━━━━━━┳━━━━━━━━━━━┓
            ┃  4  9  6  ┃  8  3  2  ┃  1  5  7  ┃
            ┃  2  1  8  ┃  7  4  5  ┃  3  9  6  ┃
            ┃  7  5  3  ┃  1  9  6  ┃  2  8  4  ┃
            ┣━━━━━━━━━━━╋━━━━━━━━━━━╋━━━━━━━━━━━┫
            ┃  9  6  2  ┃  3  7  8  ┃  4  1  5  ┃
            ┃  1  8  5  ┃  4  2  9  ┃  7  6  3  ┃
            ┃  3  7  4  ┃  5  6  1  ┃  9  2  8  ┃
            ┣━━━━━━━━━━━╋━━━━━━━━━━━╋━━━━━━━━━━━┫
            ┃  5  3  1  ┃  9  8  4  ┃  6  7  2  ┃
            ┃  6  4  9  ┃  2  5  7  ┃  8  3  1  ┃
            ┃  8  2  7  ┃  6  1  3  ┃  5  4  9  ┃
            ┗━━━━━━━━━━━┻━━━━━━━━━━━┻━━━━━━━━━━━┛
            """;
        Sudoku sudoku = new Sudoku("496832157218745396753196284962378415185429763374561928531984672649257831827613549");
        SudokuPrinter sudokuPrinter = new SudokuPrinter(sudoku);
        String actual = sudokuPrinter.printAsString();
        assertEquals(expected, actual);
    }

    @Test
    public void printUnsolvedSudoku() {
        String expected = """ 
            ┏━━━━━━━━━━━┳━━━━━━━━━━━┳━━━━━━━━━━━┓
            ┃           ┃           ┃     5     ┃
            ┃  2        ┃  7        ┃     9     ┃
            ┃        3  ┃  1        ┃     8  4  ┃
            ┣━━━━━━━━━━━╋━━━━━━━━━━━╋━━━━━━━━━━━┫
            ┃     6     ┃        8  ┃           ┃
            ┃  1  8     ┃     2     ┃  7  6     ┃
            ┃        4  ┃           ┃           ┃
            ┣━━━━━━━━━━━╋━━━━━━━━━━━╋━━━━━━━━━━━┫
            ┃     3     ┃        4  ┃           ┃
            ┃  6        ┃  2        ┃  8        ┃
            ┃           ┃        3  ┃  5     9  ┃
            ┗━━━━━━━━━━━┻━━━━━━━━━━━┻━━━━━━━━━━━┛
            """;
        Sudoku sudoku = new Sudoku("000000050200700090003100084060008000180020760004000000030004000600200800000003509");
        SudokuPrinter sudokuPrinter = new SudokuPrinter(sudoku);
        String actual = sudokuPrinter.printAsString();
        assertEquals(expected, actual);
    }
}
