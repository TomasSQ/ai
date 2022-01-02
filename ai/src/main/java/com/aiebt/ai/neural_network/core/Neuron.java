package com.aiebt.ai.neural_network.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

class Neuron {

    private static final Random random = new Random();

    private final List<Double> weights = new ArrayList<>();
    private final int biasWeightIndex;

    private double output = 0.0d;
    private double delta = 0.0d;

    Neuron(int previousLayerNeuronCount) {
        for (int i = 0; i < previousLayerNeuronCount; i++) {
            weights.add(random.nextDouble());
        }
        // Add bias weight as last weight
        weights.add(random.nextDouble());
        biasWeightIndex = weights.size() - 1;
    }

    double activate(List<Double> inputValues) {
        double activation = weights.get(biasWeightIndex);
        for (int i = 0; i < biasWeightIndex; i++) {
            activation += weights.get(i) * inputValues.get(i);
        }

        output = transfer(activation);
        return output;
    }

    double transfer(double activation) {
        return 1.0d / (1.0d + Math.exp(-1.0 * activation));
    }

    void updateWeight(List<Double> previousLayerOutput, double learingRate) {
        for (int i = 0; i < previousLayerOutput.size(); i++) {
            weights.set(i, weights.get(i) - learingRate * delta * previousLayerOutput.get(i));
        }
        weights.set(biasWeightIndex, weights.get(biasWeightIndex) - learingRate * delta);
    }

    void defineDelta(double weightError) {
        delta = weightError * transferDerivative();
    }

    double getErrorFromExpectedValue(double expectedValue) {
        return output - expectedValue;
    }

    double getShareForErrorOnNthConnection(int n) {
        return weights.get(n) * delta;
    }

    static List<Double> getOutput(List<Neuron> neurons) {
        return neurons.stream().map(neuron -> neuron.output).collect(Collectors.toList());
    }

    private double transferDerivative() {
        return output * (1 - output);
    }
}
