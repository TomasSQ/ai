package com.aiebt.binary_operations.all_operations;

import com.aiebt.ai.neural_network.core.NeuralNetwork;
import com.aiebt.binary_operations.core.BinaryOperations;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LearnAllBinaryOperations {

    private final List<List<Double>> trainingSet;
    private final List<List<Double>> expectedValuesSet;

    public LearnAllBinaryOperations() {
        List<BinaryOperations> binaryOperations = Arrays.asList(BinaryOperations.values());
        trainingSet = generateTrainingSet(binaryOperations);
        expectedValuesSet = generateExpectedValues(binaryOperations);
    }

    public void execute() {
        NeuralNetwork<Integer> neuralNetwork = new NeuralNetwork<>(3, List.of(10), 1, 0.1, output -> output.get(0) > 0.5 ? 1 : 0);
        train(neuralNetwork);

        makePredictions(neuralNetwork);
    }

    private void train(NeuralNetwork<Integer> neuralNetwork) {
        Date now = new Date();
        System.out.println("Started training");
        neuralNetwork.train(trainingSet, expectedValuesSet, 50_000);
        long millisecondsTraining = new Date().getTime() - now.getTime();
        System.out.println("Done trainging (" + millisecondsTraining + "ms)");
    }

    private void makePredictions(NeuralNetwork<Integer> neuralNetwork) {
        System.out.println("Predictions");
        int correctPredictions = 0;
        int wrongPredictions = 0;
        for (int i = 0; i < trainingSet.size(); i++) {
            List<Double> inputValues = trainingSet.get(i);
            int predictedValue = neuralNetwork.predict(inputValues);
            boolean predictedCorrectly = predictedValue == Math.round(expectedValuesSet.get(i).get(0));
            correctPredictions += predictedCorrectly ? 1 : 0;
            wrongPredictions += predictedCorrectly ? 0 : 1;
            System.out.printf(
                    "%7s %.0f %.0f: %d (%b)\n",
                    BinaryOperations.from(inputValues.get(0)),
                    inputValues.get(1),
                    inputValues.get(2),
                    predictedValue,
                    predictedCorrectly
            );
        }
        System.out.printf("Correct: %d\n", correctPredictions);
        System.out.printf("Wrong: %d\n", wrongPredictions);
    }

    private List<List<Double>> generateTrainingSet(List<BinaryOperations> binaryOperations) {
        return binaryOperations.stream()
                .flatMap(binaryOperation ->
                        Stream.of(
                                List.of(binaryOperation.value, 1.0d, 1.0d),
                                List.of(binaryOperation.value, 0.0d, 1.0d),
                                List.of(binaryOperation.value, 1.0d, 0.0d),
                                List.of(binaryOperation.value, 0.0d, 0.0d)
                        )
                ).collect(Collectors.toList());
    }

    private List<List<Double>> generateExpectedValues(List<BinaryOperations> binaryOperations) {
        return binaryOperations.stream()
                .flatMap(binaryOperation -> binaryOperation.output.stream().map(List::of))
                .collect(Collectors.toList());
    }
}
