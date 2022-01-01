package com.aiebt.sudoku;

import com.aiebt.sudoku.core.Sudoku;
import com.aiebt.sudoku.printer.SudokuPrinter;
import com.aiebt.sudoku.reader.SudokuReader;
import com.aiebt.sudoku.solver.SudokuSolver;

public class Main {

    public static void main(String[] args) {
//        Sudoku sudoku = new SudokuReader().read();
//        Sudoku sudoku = new SudokuReader().from("000406000089001007700000600405600000800000006000007901006000004200700830000203000");
        Sudoku sudoku = new SudokuReader().from("010065008050910607690040031900000700000508000008000002320050074705084090400320050");
//        Sudoku sudoku = new SudokuReader().from("000000050200700090003100084060008000180020760004000000030004000600200800000003509");
        System.out.println(sudoku.toString());
        new SudokuPrinter(sudoku).print();

        Sudoku solved = new SudokuSolver(sudoku).solve();

        new SudokuPrinter(solved).print();
        System.out.println(solved.toString());
    }
}
