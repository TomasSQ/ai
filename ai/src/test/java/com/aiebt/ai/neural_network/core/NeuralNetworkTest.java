package com.aiebt.ai.neural_network.core;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NeuralNetworkTest {

    @Test
    public void itWorks() {
        NeuralNetwork<Integer> neuralNetwork = new NeuralNetwork<>(2, List.of(3), 1, 0.2, output -> output.get(0) > 0.5 ? 1 : 0);
        neuralNetwork.train(
                List.of(
                        List.of(1.0d, 1.0d),
                        List.of(0.0d, 1.0d),
                        List.of(1.0d, 0.0d),
                        List.of(0.0d, 0.0d)
                ),
                List.of(
                        List.of(0.0d),
                        List.of(1.0d),
                        List.of(1.0d),
                        List.of(0.0d)
                ),
                3_000
        );

        assertEquals(0, neuralNetwork.predict(List.of(1.0d, 1.0d)));
        assertEquals(1, neuralNetwork.predict(List.of(0.0d, 1.0d)));
        assertEquals(1, neuralNetwork.predict(List.of(1.0d, 0.0d)));
        assertEquals(0, neuralNetwork.predict(List.of(0.0d, 0.0d)));
    }
}
