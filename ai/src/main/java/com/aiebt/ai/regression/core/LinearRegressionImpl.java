package com.aiebt.ai.regression.core;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class LinearRegressionImpl implements Regression<OrderedPair<BigDecimal>> {

    private final BigDecimal learningRate;
    private final BigDecimal worstAcceptableCoefficientOfDetermination;
    private OrderedPair<BigDecimal> coefficients;
    private BigDecimal coefficientOfDetermination;

    public LinearRegressionImpl(BigDecimal learningRate, BigDecimal worstAcceptableCoefficientOfDetermination) {
        if (learningRate.compareTo(BigDecimal.ZERO) <= 0 || learningRate.compareTo(BigDecimal.ONE) > 0) {
            throw new IllegalArgumentException("Learning rate should be bounded to 0..1");
        }
        if (worstAcceptableCoefficientOfDetermination.compareTo(BigDecimal.ZERO) <= 0 || worstAcceptableCoefficientOfDetermination.compareTo(BigDecimal.ONE) > 0) {
            throw new IllegalArgumentException("Worst acceptable coefficient of determination should be bounded to to 0..1");
        }
        this.learningRate = learningRate;
        this.worstAcceptableCoefficientOfDetermination = worstAcceptableCoefficientOfDetermination;
    }

    @Override
    public OrderedPair<BigDecimal> initCoefficients() {
        coefficients = OrderedPair.of(BigDecimal.ZERO, BigDecimal.ZERO);
        return coefficients;
    }

    @Override
    public void validateAccuracy() {
        if (coefficientOfDetermination.compareTo(worstAcceptableCoefficientOfDetermination) < 0) {
            throw new IllegalArgumentException("R2 is too low (" + coefficientOfDetermination + "). Use its predictions at your own risk");
        }
    }

    @Override
    public BigDecimal predict(BigDecimal x) {
        return coefficients.a1.add(coefficients.a2.multiply(x));
    }

    @Override
    public void setCoefficientOfDetermination(BigDecimal coefficientOfDetermination) {
        this.coefficientOfDetermination = coefficientOfDetermination;
    }

    @Override
    public BigDecimal getCoefficientOfDetermination() {
        return coefficientOfDetermination;
    }

    @Override
    public List<BigDecimal> calculateYi(List<OrderedPair<BigDecimal>> dataPoints) {
        // Yi = a1 + a2 * Xi
        return dataPoints.stream()
                .map(dataPoint -> coefficients.a1.add(coefficients.a2.multiply(dataPoint.a1)))
                .collect(Collectors.toList());
    }

    @Override
    public OrderedPair<BigDecimal> refineCoefficients(List<BigDecimal> error, List<OrderedPair<BigDecimal>> dataPoints) {
        coefficients = OrderedPair.of(refineA1(error), refineA2(error, dataPoints));
        return coefficients;
    }

    private BigDecimal refineA1(List<BigDecimal> error) {
        // a1 = a1 - alfa * 2 * Sum Ei_n / Dimension(Error)
        BigDecimal errorSum = error.stream().reduce(BigDecimal.ZERO, BigDecimal::add, BigDecimal::add);
        return coefficients.a1.subtract(
                learningRate
                        .multiply(BigDecimal.valueOf(2))
                        .multiply(errorSum)
                        .divide(BigDecimal.valueOf(error.size()), MathContext.DECIMAL128)
        );
    }

    private BigDecimal refineA2(List<BigDecimal> error, List<OrderedPair<BigDecimal>> dataPoints) {
        // a2 = a2 - alfa * 2 * Sum (Ei_n * X_n) / Dimension(Error)
        BigDecimal weightedErrorSum = BigDecimal.ZERO;
        for (int i = 0; i < error.size(); i++) {
            weightedErrorSum = weightedErrorSum.add(error.get(i).multiply(dataPoints.get(i).a1));
        }
        return coefficients.a2.subtract(
                learningRate
                        .multiply(BigDecimal.valueOf(2))
                        .multiply(weightedErrorSum)
                        .divide(BigDecimal.valueOf(error.size()), MathContext.DECIMAL128)
        );
    }


}
