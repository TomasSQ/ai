package com.aiebt.sudoku.solver;

import com.aiebt.ai.neural_network.core.NeuralNetwork;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MlConstraintSolver {

    private static final int MAX_DIGIT = 4;

    private final List<List<Integer>> randomCellsRows;
    private final List<List<Integer>> expectedOutcome;

    public MlConstraintSolver() {
        randomCellsRows = generateTrainingSet();
        expectedOutcome = generateExpectedOutcome();
        System.out.println(randomCellsRows);
        System.out.println(expectedOutcome);
    }

    public void execute() {
        NeuralNetwork<List<Integer>> neuralNetwork = new NeuralNetwork<>(
                MAX_DIGIT,
                List.of(5, 5),
                MAX_DIGIT,
                0.1,
                output -> output.stream()
                        .map(aDouble -> aDouble * MAX_DIGIT)
                        .map(Math::round)
                        .map(Long::intValue)
                        .collect(Collectors.toList())
        );
        train(neuralNetwork);

        makePredictions(neuralNetwork);
    }

    private void train(NeuralNetwork<List<Integer>> neuralNetwork) {
        neuralNetwork.train(
                asListOfListOfDouble(randomCellsRows),
                asListOfListOfDouble(expectedOutcome),
                5_0000
        );
    }

    private void makePredictions(NeuralNetwork<List<Integer>> neuralNetwork) {
        randomCellsRows.forEach(inputValues -> System.out.println(inputValues + "->" + neuralNetwork.predict(asListOfDouble(inputValues))));
    }

    private List<List<Integer>> generateTrainingSet() {
        List<Integer> oneToNine = IntStream.rangeClosed(1, MAX_DIGIT).boxed().collect(Collectors.toList());
        List<List<Integer>> randomCellsRows = new ArrayList<>();
        while (randomCellsRows.size() < 200) {
            List<Integer> randomCellRow = new ArrayList<>(oneToNine);
            Collections.shuffle(randomCellRow);
            randomCellRow.set(Long.valueOf(Math.round(Math.floor(Math.random() * randomCellRow.size()))).intValue(), 0);
            randomCellsRows.add(randomCellRow);
        }
        return randomCellsRows;
    }

    private List<List<Integer>> generateExpectedOutcome() {
        return randomCellsRows.stream()
                .map(randomCellsRow -> {
                    final int missingNumber = MAX_DIGIT * (1 + MAX_DIGIT) / 2 - randomCellsRow.stream().reduce(0, Integer::sum, Integer::sum);
                    return randomCellsRow.stream()
                            .map(integer -> integer == 0 ? missingNumber : integer)
                            .collect(Collectors.toList());
                })
                .collect(Collectors.toList());
    }

    private List<List<Double>> asListOfListOfDouble(List<List<Integer>> intListOfList) {
        return intListOfList.stream().map(this::asListOfDouble).collect(Collectors.toList());
    }

    private List<Double> asListOfDouble(List<Integer> intList) {
        return intList.stream().map(integer -> integer / (1.0 * MAX_DIGIT)).collect(Collectors.toList());
    }
}
