package com.aiebt.ai.linear_regression;

import com.aiebt.ai.linear_regression.core.OrderedPair;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LinearRegressionTest {

    private static final List<OrderedPair<BigDecimal>> DATA_POINTS = List.of(
            OrderedPair.of(BigDecimal.valueOf(2), BigDecimal.valueOf(2)),
            OrderedPair.of(BigDecimal.valueOf(3), BigDecimal.valueOf(4)),
            OrderedPair.of(BigDecimal.valueOf(4), BigDecimal.valueOf(6)),
            OrderedPair.of(BigDecimal.valueOf(6), BigDecimal.valueOf(7))
    );

    @Test
    void trainWorks() {
        LinearRegression linearRegression = new LinearRegression(BigDecimal.valueOf(0.001d), BigDecimal.valueOf(0.88));
        OrderedPair<BigDecimal> result = linearRegression.train(DATA_POINTS, 1000);

        // The line is anywhere between y = 1/4 + 6 / 5 x and y = 1/7 + 8/7
        // And we expect the R2 to be better than an 88% fit
        assertEquals(BigDecimal.valueOf(0.1).compareTo(result.a1), -1);
        assertEquals(BigDecimal.valueOf(0.25).compareTo(result.a1), 1);
        assertEquals(BigDecimal.valueOf(1.0).compareTo(result.a2), -1);
        assertEquals(BigDecimal.valueOf(1.3).compareTo(result.a2), 1);

        assertEquals(BigDecimal.valueOf(0.88).compareTo(linearRegression.getCoefficientOfDetermination()), -1);
    }

    @Test
    void predictAfterTrainingWorks() {
        LinearRegression linearRegression = new LinearRegression(BigDecimal.valueOf(0.001d), BigDecimal.valueOf(0.88));
        linearRegression.train(DATA_POINTS, 1000);

        BigDecimal predicted = linearRegression.predict(BigDecimal.valueOf(5));

        assertEquals(BigDecimal.valueOf(6).compareTo(predicted), -1);
        assertEquals(BigDecimal.valueOf(7).compareTo(predicted), 1);
    }

    @Test
    void throwsWhenTrainingDoestResultWithAccurateCoefficient() {
        LinearRegression tooSlowLearner = new LinearRegression(BigDecimal.valueOf(0.00001d), BigDecimal.valueOf(0.88));
        assertThrows(IllegalArgumentException.class, () -> tooSlowLearner.train(DATA_POINTS, 1000));

        LinearRegression tooFewEpochs = new LinearRegression(BigDecimal.valueOf(0.001d), BigDecimal.valueOf(0.88));
        assertThrows(IllegalArgumentException.class, () -> tooFewEpochs.train(DATA_POINTS, 10));
    }

    @Test
    void throwsWhenPredictBeforeTraining() {
        LinearRegression linearRegression = new LinearRegression(BigDecimal.valueOf(0.001d), BigDecimal.valueOf(0.88));
        assertThrows(IllegalStateException.class, () -> linearRegression.predict(BigDecimal.valueOf(0.2)));
    }
}
