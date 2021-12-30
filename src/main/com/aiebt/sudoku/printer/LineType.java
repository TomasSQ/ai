package com.aiebt.sudoku.printer;

import com.aiebt.sudoku.core.Sudoku;

enum LineType {
    TOP_LINE('┏', '┓', '┳', "━━━"),
    MIDDLE_LINE('┣', '┫', '╋', "━━━"),
    BOTTOM_LINE('┗', '┛', '┻', "━━━"),
    VALUE('┃', '┃', '┃', "━━━");

    private final char initial;
    private final char last;
    private final char middle;
    private final String value;

    LineType(char initial, char last, char middle, String value) {
        this.initial = initial;
        this.last = last;
        this.middle = middle;
        this.value = value;
    }

    String getValueRepresentation(int value) {
        if (this == VALUE) {
            return " " + (value == 0 ? " " : value) + " ";
        }
        return this.value;
    }

    String getSignForColumn(int column) {
        if (this == VALUE) {
            return (column == 0 ? "" : " ") + initial + (column == Sudoku.fieldLength() ? "" : " ");
        }
        if (column == 0) {
            return initial + "━";
        }
        if (column == Sudoku.fieldLength()) {
            return "━" + last;
        }
        return "━" + middle + "━";
    }


    static LineType forRow(int row) {
        if (row == 0) {
            return LineType.TOP_LINE;
        }
        if (row == Sudoku.fieldLength()) {
            return LineType.BOTTOM_LINE;
        }
        return LineType.MIDDLE_LINE;
    }
}
