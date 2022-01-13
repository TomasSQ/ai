package com.aiebt.running;

import com.aiebt.ai.regression.core.LinearRegression;
import com.aiebt.ai.regression.core.OrderedPair;
import com.aiebt.utils.plot.PlotAsImage;

import java.awt.Color;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

public class ExpectedPaceWithLinearRegression {

    public static final Color RED = new Color(200, 45, 45);
    public static final Color BLUE = new Color(45, 45, 200);

    public static void main(String[] args) {
        var distanceVersusPace = getDistanceVersusPaceTrainingData();
        var lr = new LinearRegression(BigDecimal.valueOf(0.001d), BigDecimal.valueOf(0.5));
        var coefficients = lr.train(distanceVersusPace, 10_000, true);
        MathContext twoDecimals = new MathContext(2, RoundingMode.HALF_EVEN);
        System.out.println("y = " + coefficients.a1.round(twoDecimals) + " + (" + coefficients.a2.round(twoDecimals) + ")x");
        System.out.println("R2: " + lr.getCoefficientOfDetermination().round(twoDecimals) + "min/km");
        System.out.println("5k: " + lr.predict(BigDecimal.valueOf(5)).round(twoDecimals) + "min/km");
        System.out.println("10k: " + lr.predict(BigDecimal.valueOf(10)).round(twoDecimals) + "min/km");
        System.out.println("Half Marathon: " + lr.predict(BigDecimal.valueOf(21.1)).round(twoDecimals) + "min/km");
        System.out.println("Marathon: " + lr.predict(BigDecimal.valueOf(42.2)).round(twoDecimals) + "min/km");

        plot(distanceVersusPace, coefficients);
    }

    private static void plot(List<OrderedPair<BigDecimal>> distanceVersusPace, OrderedPair<BigDecimal> coefficients) {
        List<OrderedPair<Double>> points = distanceVersusPace.stream()
                .map(point -> OrderedPair.of(point.a1.doubleValue(), point.a2.doubleValue()))
                .collect(Collectors.toList());

        new PlotAsImage(480, 480)
                .backgroundColor(Color.WHITE)
                .withAxis()
                .alwaysStartFromZero()
                .addLine(coefficients.a1.doubleValue(), coefficients.a2.doubleValue(), RED)
                .addPoints(points, BLUE)
                .save("a.png");
    }

    private static List<OrderedPair<BigDecimal>> getDistanceVersusPaceTrainingData() {
        return List.of(
                new RunningData(42, 37, 8.00).asOrderedPair(),
                new RunningData(38, 56, 7.01d).asOrderedPair(),
                new RunningData(34, 13, 6.01d).asOrderedPair(),
                new RunningData(35, 42, 6.00d).asOrderedPair(),
                new RunningData(60 + 58, 34, 21.21d).asOrderedPair(),
                new RunningData(36, 38, 7.00d).asOrderedPair(),
                new RunningData(60 + 1, 51, 12.00d).asOrderedPair(),
                new RunningData(36, 49, 7.00d).asOrderedPair(),
                new RunningData(24, 19, 5.00d).asOrderedPair(),
                new RunningData(25, 54, 5.00d).asOrderedPair(),
                new RunningData(43, 37, 8.01d).asOrderedPair(),
                new RunningData(29, 28, 6.00d).asOrderedPair(),
                new RunningData(28, 1, 5.30d).asOrderedPair(),
                new RunningData(25, 12, 5.01d).asOrderedPair(),
                new RunningData(60 + 23, 37, 15.00d).asOrderedPair(),
                new RunningData(28, 12, 5.00d).asOrderedPair(),
                new RunningData(28, 07, 5.00d).asOrderedPair(),
                new RunningData(60 + 19, 4, 14.01d).asOrderedPair(),
                new RunningData(57, 49, 10.02d).asOrderedPair(),
                new RunningData(50, 59, 8.00d).asOrderedPair()
        );
    }

    private static class RunningData {
        private final BigDecimal pace;
        private final BigDecimal distanceRan;

        RunningData(int minutesRunning, int secondsRunning, double distanceRan) {
            pace = BigDecimal.valueOf((minutesRunning + (1.0d * secondsRunning / 60.0d)) / distanceRan);
            this.distanceRan = BigDecimal.valueOf(distanceRan);
        }

        OrderedPair<BigDecimal> asOrderedPair() {
            return OrderedPair.of(distanceRan, pace);
        }
    }
}
