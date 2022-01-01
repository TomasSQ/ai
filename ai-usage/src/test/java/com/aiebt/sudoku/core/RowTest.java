package com.aiebt.sudoku.core;

public class RowTest extends CellCollectionTest {

    @Override
    CellCollection getCellCollection() {
        return new Row();
    }
}
