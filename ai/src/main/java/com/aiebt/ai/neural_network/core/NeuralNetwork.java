package com.aiebt.ai.neural_network.core;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.function.Function;
import java.util.stream.Collectors;

public class NeuralNetwork<R> {

    private final LinkedList<Layer> layers = new LinkedList<>();
    private final int inputLayerNeuronCount;
    private final int outputLayerNeuronCount;
    private final double learningRate;
    private final Function<List<Double>, R> classifier;

    public NeuralNetwork(int inputLayerNeuronCount, List<Integer> hiddenLayersNeuronCount, int outputLayerNeuronCount, double learningRate, Function<List<Double>, R> classifier) {
        this.inputLayerNeuronCount = inputLayerNeuronCount;
        this.outputLayerNeuronCount = outputLayerNeuronCount;
        this.learningRate = learningRate;
        this.classifier = classifier;

        for (int i = 0; i < hiddenLayersNeuronCount.size(); i++) {
            boolean isFirstLayer = i == 0;
            layers.add(new Layer(isFirstLayer ? inputLayerNeuronCount : hiddenLayersNeuronCount.get(i - 1), hiddenLayersNeuronCount.get(i), isFirstLayer ? LayerType.INPUT : LayerType.HIDDEN));
        }
        layers.add(new Layer(hiddenLayersNeuronCount.get(hiddenLayersNeuronCount.size() - 1), outputLayerNeuronCount, LayerType.OUTPUT));
    }

    public void train(List<List<Double>> inputValuesTrainingSet, List<List<Double>> expectedValuesSet, int epochCount) {
        if (inputValuesTrainingSet.stream().anyMatch(inputValues -> inputValues.size() != inputLayerNeuronCount)) {
            throw new IllegalArgumentException("Expected " + inputLayerNeuronCount + " input values but received " + inputValuesTrainingSet.stream().map(List::size).collect(Collectors.toList()));
        }
        if (expectedValuesSet.stream().anyMatch(expectedValues -> expectedValues.size() != outputLayerNeuronCount)) {
            throw new IllegalArgumentException("Expected " + outputLayerNeuronCount + " expected values but received " + expectedValuesSet.stream().map(List::size).collect(Collectors.toList()));
        }

        for (int currentEpoch = 0; currentEpoch < epochCount; currentEpoch++) {
            for (int i = 0; i < inputValuesTrainingSet.size(); i++) {
                forwardPropagate(inputValuesTrainingSet.get(i));
                backwardPropagateError(expectedValuesSet.get(i));
                updateWeights(inputValuesTrainingSet.get(i));
            }
        }
    }

    public R predict(List<Double> inputValues) {
        if (inputValues.size() != inputLayerNeuronCount) {
            throw new IllegalArgumentException("Expected " + inputLayerNeuronCount + " input values but received " + inputValues.size());
        }
        return classifier.apply(forwardPropagate(inputValues));
    }

    private List<Double> forwardPropagate(List<Double> inputValues) {
        List<Double> valuesToPropagate = new ArrayList<>(inputValues);
        for (Layer layer: layers) {
            valuesToPropagate = layer.forwardPropagate(valuesToPropagate);
        }
        return valuesToPropagate;
    }

    private void backwardPropagateError(List<Double> expectedValues) {
        ListIterator<Layer> iterator = layers.listIterator(layers.size());
        while (iterator.hasPrevious()) {
            Layer nextLayer = !iterator.hasNext() ? null : layers.get(iterator.nextIndex());
            Layer layer = iterator.previous();
            if (layer.isOutput()) {
                layer.defineWeightDeltaForEveryNeuron(expectedValues);
            } else {
                layer.defineWeightDeltaForEveryNeuron(nextLayer);
            }
        }
    }

    private void updateWeights(List<Double> inputValues) {
        ListIterator<Layer> iterator = layers.listIterator();
        while (iterator.hasNext()) {
            Layer previousLayer = !iterator.hasPrevious() ? null : layers.get(iterator.previousIndex());
            Layer layer = iterator.next();
            if (previousLayer == null) {
                layer.updateWeights(inputValues, learningRate);
            } else {
                layer.updateWeights(previousLayer, learningRate);
            }
        }
    }
}
