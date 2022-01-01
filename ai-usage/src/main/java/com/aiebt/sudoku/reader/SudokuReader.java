package com.aiebt.sudoku.reader;

import com.aiebt.sudoku.core.Sudoku;

import java.util.Scanner;

public class SudokuReader {

    public Sudoku read() {
        String fieldAsString = getFieldAsString();

        return parseAsSudoku(fieldAsString);
    }

    public Sudoku from(String fieldAsString) {
        String sanitizedFieldAsString = fieldAsString
                .replaceAll("\\s", "")
                .replaceAll("\\n", "")
                .replaceAll("\\t", "");
        return parseAsSudoku(sanitizedFieldAsString);
    }

    private String getFieldAsString() {
        String fieldAsString = new Scanner(System.in).nextLine();

        if (fieldAsString.length() != Sudoku.CELLS_COUNT) {
            throw new IllegalArgumentException("Field should have 81 chars, but got " + fieldAsString.length());
        }
        return fieldAsString;
    }

    private Sudoku parseAsSudoku(String fieldAsString) {
        return new Sudoku(fieldAsString);
    }
}
