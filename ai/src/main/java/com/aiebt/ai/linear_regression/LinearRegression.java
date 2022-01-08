package com.aiebt.ai.linear_regression;

import com.aiebt.ai.linear_regression.core.OrderedPair;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LinearRegression {

    private final BigDecimal learningRate;
    private final BigDecimal worstAcceptableCoefficientOfDetermination;
    private OrderedPair<BigDecimal> coefficients;
    private BigDecimal coefficientOfDetermination;
    private boolean trained = false;

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
     * @param learningRate                              a constant (alfa) that defines how much the model learns per epoch
     *                                                  using gradient descent.
     * @param worstAcceptableCoefficientOfDetermination a constant that defines who good the training must be in order
     *                                                  to make usable predictions. Usually should be grater than 0.9.
     */
    public LinearRegression(BigDecimal learningRate, BigDecimal worstAcceptableCoefficientOfDetermination) {
        if (learningRate.compareTo(BigDecimal.ZERO) <= 0 || learningRate.compareTo(BigDecimal.ONE) > 0) {
            throw new IllegalArgumentException("Learning rate should be bounded to 0..1");
        }
        if (worstAcceptableCoefficientOfDetermination.compareTo(BigDecimal.ZERO) <= 0 || worstAcceptableCoefficientOfDetermination.compareTo(BigDecimal.ONE) > 0) {
            throw new IllegalArgumentException("Worst acceptable coefficient of determination should be bounded to to 0..1");
        }
        this.learningRate = learningRate;
        this.worstAcceptableCoefficientOfDetermination = worstAcceptableCoefficientOfDetermination;
    }

    public OrderedPair<BigDecimal> train(List<OrderedPair<BigDecimal>> dataPoints, int epochs) {
        return train(dataPoints, epochs, false);
    }

    /**
     * Refine coefficients a1 and a2 in y = a1 + a2 * x, to minimize the distances between ground truth y and
     * the predicted y using those coefficients.
     *
     * @param dataPoints a list of y and x - the train set
     * @param epochs     how many iterations the training will take place
     * @param ignoreBadFit true if you don't care how bad the fit will be. if false, an exception is thrown if bad fit
     * @return The linear coefficients of the best fit line found
     * @throws IllegalArgumentException if the R2 (coefficient of Determination) is lower than the worst acceptable
     */
    public OrderedPair<BigDecimal> train(List<OrderedPair<BigDecimal>> dataPoints, int epochs, boolean ignoreBadFit) {
        BigDecimal ym = calculateMeanY(dataPoints);

        coefficients = OrderedPair.of(BigDecimal.ZERO, BigDecimal.ZERO);
        for (int epoch = 0; epoch < epochs; epoch++) {
            List<BigDecimal> yi = calculateYi(dataPoints);
            List<BigDecimal> error = calculateError(yi, dataPoints);
            refineCoefficients(error, dataPoints);
            coefficientOfDetermination = calculateCoefficientDetermination(yi, dataPoints, ym);
        }
        trained = true;

        if (!ignoreBadFit) {
            validateAccuracy();
        }

        return coefficients;
    }

    private void validateAccuracy() {
        if (coefficientOfDetermination.compareTo(worstAcceptableCoefficientOfDetermination) < 0) {
            throw new IllegalArgumentException("R2 is too low (" + coefficientOfDetermination + "). Use its predictions at your own risk");
        }
    }

    public BigDecimal predict(BigDecimal x) {
        if (!trained) {
            throw new IllegalStateException("Train the linear regression first");
        }
        return coefficients.a1.add(coefficients.a2.multiply(x));
    }

    public BigDecimal getCoefficientOfDetermination() {
        return coefficientOfDetermination;
    }

    private BigDecimal calculateMeanY(List<OrderedPair<BigDecimal>> dataPoints) {
        // Ym = (Sum (Y_n)) / Dimension(Y)
        return dataPoints.stream()
                .map(dataPoint -> dataPoint.a2)
                .reduce(
                        BigDecimal.ZERO,
                        BigDecimal::add,
                        BigDecimal::add)
                .divide(BigDecimal.valueOf(dataPoints.size()), MathContext.DECIMAL128);
    }

    private List<BigDecimal> calculateYi(List<OrderedPair<BigDecimal>> dataPoints) {
        // Yi = a1 + a2 * Xi
        return dataPoints.stream()
                .map(dataPoint -> coefficients.a1.add(coefficients.a2.multiply(dataPoint.a1)))
                .collect(Collectors.toList());
    }

    private void refineCoefficients(List<BigDecimal> error, List<OrderedPair<BigDecimal>> dataPoints) {
        coefficients = OrderedPair.of(refineA1(error), refineA2(error, dataPoints));
    }

    private List<BigDecimal> calculateError(List<BigDecimal> yi, List<OrderedPair<BigDecimal>> dataPoints) {
        // Ei = Yi - Y
        // The nth error is defined by Ei_n = Yi_n - Y_n
        List<BigDecimal> error = new ArrayList<>(yi.size());
        for (int i = 0; i < yi.size(); i++) {
            error.add(yi.get(i).subtract(dataPoints.get(i).a2));
        }
        return error;
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

    private BigDecimal calculateCoefficientDetermination(List<BigDecimal> yi, List<OrderedPair<BigDecimal>> dataPoints, BigDecimal ym) {
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
