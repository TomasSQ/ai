package com.aiebt.ai.regression.core;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LinearRegression extends LinearRegressionImpl {

    /**
     * Instantiate a new linear regression model with given learning rate ]0, 1] and worst acceptable R2 ]0, 1]. </br>
     * Once instantiated, it must be trained before using it to make predictions. </br>
     * Example:<br>
     * <code>
     * var lr = new LinearRegression(BigDecimal.of(0.001d), BigDecimal.of(0.95); </br>
     * lr.train(List.of(OrderedPair.of(...), ...), 1_000); </br>
     * lr.predict(BigDecimal.of(7)); </br>
     * </code>
     *
     * The model refines the coefficients a1 and a2 in y = a1 + a2 * x, to minimize the distances between ground truth y and
     *
     * @param learningRate                              a constant (alfa) that defines how much the model learns per epoch
     *                                                  using gradient descent.
     * @param worstAcceptableCoefficientOfDetermination a constant that defines who good the training must be in order
     *                                                  to make usable predictions. Usually should be grater than 0.9.
     */
    public LinearRegression(BigDecimal learningRate, BigDecimal worstAcceptableCoefficientOfDetermination) {
        super(learningRate, worstAcceptableCoefficientOfDetermination);
    }

    public OrderedPair<BigDecimal> train(List<OrderedPair<BigDecimal>> dataPoints, int epochs) {
        return train(dataPoints, epochs, false);
    }

    public OrderedPair<BigDecimal> train(List<OrderedPair<BigDecimal>> dataPoints, int epochs, boolean ignoreBadFit) {
        return super.train(dataPoints, epochs, ignoreBadFit);
    }

    public BigDecimal predict(BigDecimal x) {
        return super.predict(x);
    }

    public BigDecimal getCoefficientOfDetermination() {
        return super.getCoefficientOfDetermination();
    }
}
