package com.aiebt.ai.regression.core;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;

interface Regression<T> {

    /**
     * Refine coefficients in the given objective function, to minimize the distances between ground truth y and
     * the predicted y using those coefficients.
     *
     * @param dataPoints   a list of y and x - the train set
     * @param epochs       how many iterations the training will take place
     * @param ignoreBadFit true if you don't care how bad the fit will be. if false, an exception is thrown if bad fit
     * @return The coefficients of the best fit curve found
     * @throws IllegalArgumentException if the R2 (coefficient of Determination) is lower than the worst acceptable
     */
    default T train(List<OrderedPair<BigDecimal>> dataPoints, int epochs, boolean ignoreBadFit) {
        BigDecimal ym = calculateMeanY(dataPoints);

        T coefficients = initCoefficients();
        for (int epoch = 0; epoch < epochs; epoch++) {
            List<BigDecimal> yi = calculateYi(dataPoints);
            List<BigDecimal> error = calculateError(yi, dataPoints);
            coefficients = refineCoefficients(error, dataPoints);
            BigDecimal coefficientDetermination = calculateCoefficientDetermination(yi, dataPoints, ym);
            setCoefficientOfDetermination(coefficientDetermination);
        }

        if (!ignoreBadFit) {
            validateAccuracy();
        }

        return coefficients;
    }

    BigDecimal predict(BigDecimal x);

    T initCoefficients();

    void setCoefficientOfDetermination(BigDecimal coefficientOfDetermination);

    BigDecimal getCoefficientOfDetermination();

    List<BigDecimal> calculateYi(List<OrderedPair<BigDecimal>> dataPoints);

    T refineCoefficients(List<BigDecimal> error, List<OrderedPair<BigDecimal>> dataPoints);

    void validateAccuracy();

    default BigDecimal calculateMeanY(List<OrderedPair<BigDecimal>> dataPoints) {
        return dataPoints.stream()
                .map(dataPoint -> dataPoint.a2)
                .reduce(
                        BigDecimal.ZERO,
                        BigDecimal::add,
                        BigDecimal::add)
                .divide(BigDecimal.valueOf(dataPoints.size()), MathContext.DECIMAL128);
    }

    default List<BigDecimal> calculateError(List<BigDecimal> yi, List<OrderedPair<BigDecimal>> dataPoints) {
        // Ei = Yi - Y
        // The nth error is defined by Ei_n = Yi_n - Y_n
        List<BigDecimal> error = new ArrayList<>(yi.size());
        for (int i = 0; i < yi.size(); i++) {
            error.add(yi.get(i).subtract(dataPoints.get(i).a2));
        }
        return error;
    }

    default BigDecimal calculateCoefficientDetermination(List<BigDecimal> yi, List<OrderedPair<BigDecimal>> dataPoints, BigDecimal ym) {
        // R^2 = 1 - (Sum Y_n - Yi_n)^2 / Sum(Y_n - Ym_n)
        // Where Ym is the mean Y
        BigDecimal sumOfSquaredRegression = BigDecimal.ZERO;
        BigDecimal sumOfSquaresTotal = BigDecimal.ZERO;
        for (int i = 0; i < yi.size(); i++) {
            sumOfSquaredRegression = sumOfSquaredRegression.add(dataPoints.get(i).a2.subtract(yi.get(i)).pow(2));
            sumOfSquaresTotal = sumOfSquaresTotal.add(dataPoints.get(i).a2.subtract(ym).pow(2));
        }
        if (sumOfSquaresTotal.compareTo(BigDecimal.ZERO) == 0) {
            throw new IllegalStateException("Sum of squares total is zero. Are you sure your data is real?");
        }
        return BigDecimal.ONE.subtract(
                sumOfSquaredRegression.divide(sumOfSquaresTotal, MathContext.DECIMAL128)
        );
    }
}
