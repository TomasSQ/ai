package com.aiebt.ai.neural_network.core;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class Layer {

    private final LayerType layerType;
    private final List<Neuron> neurons = new ArrayList<>();

    Layer(int previousLayerNeuronCount, int neuronCount, LayerType layerType) {
        this.layerType = layerType;

        for (int i = 0; i < neuronCount; i++) {
            neurons.add(new Neuron(previousLayerNeuronCount));
        }
    }

    boolean isOutput() {
        return layerType == LayerType.OUTPUT;
    }

    List<Double> forwardPropagate(List<Double> inputValues) {
        return neurons.stream()
                .map(neuron -> neuron.activate(inputValues))
                .collect(Collectors.toList());
    }

    void defineWeightDeltaForEveryNeuron(List<Double> expectedValues) {
        defineWeightDelta(calculateError(expectedValues));
    }

    void defineWeightDeltaForEveryNeuron(Layer nextLayer) {
        defineWeightDelta(calculateError(nextLayer));
    }

    void updateWeights(Layer previousLayer, double learingRate) {
        neurons.forEach(neuron -> neuron.updateWeight(previousLayer.getOutput(), learingRate));
    }

    void updateWeights(List<Double> previousLayerOutput, double learingRate) {
        neurons.forEach(neuron -> neuron.updateWeight(previousLayerOutput, learingRate));
    }

    private void defineWeightDelta(List<Double> layerError) {
        for (int i = 0; i < neurons.size(); i++) {
            neurons.get(i).defineDelta(layerError.get(i));
        }
    }

    private List<Double> calculateError(List<Double> expectedValues) {
        List<Double> errors = new ArrayList<>();
        for (int i = 0; i < neurons.size(); i++) {
            errors.add(neurons.get(i).getErrorFromExpectedValue(expectedValues.get(i)));
        }
        return errors;
    }

    private List<Double> calculateError(Layer nextLayer) {
        List<Double> errors = new ArrayList<>();
        for (int i = 0; i < neurons.size(); i++) {
            final int nthConnection = i;
            double error = nextLayer.neurons.stream().reduce(
                    0.0d,
                    (acc, neuron) -> acc + neuron.getShareForErrorOnNthConnection(nthConnection),
                    Double::sum
            );
            errors.add(error);
        }
        return errors;
    }

    private List<Double> getOutput() {
        return Neuron.getOutput(neurons);
    }
}
