package com.aiebt.utils.plot;

import com.aiebt.ai.linear_regression.core.OrderedPair;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class Image {

    private final BufferedImage bufferedImage;
    private Graphics graphics;
    private final int width;
    private final int height;

    private Color backgroundColor;
    private boolean withAxis;
    private boolean alwaysStartFromZero;
    private OrderedPair<Double> minCoordinate;
    private OrderedPair<Double> maxCoordinate;
    private OrderedPair<Double> axisPadding;

    private final List<Line> lines = new ArrayList<>();
    private final List<Point> points = new ArrayList<>();

    Image(int width, int height) {
        bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        this.width = width;
        this.height = height;
        // Graphics starts with coordinates like:
        //  |
        // -+-----> x
        //  |
        //  |
        //  V  y
        minCoordinate = OrderedPair.of(0.0, 0.0);
        maxCoordinate = OrderedPair.of(1.0 * width, 1.0 * height);
        axisPadding = OrderedPair.of(0.0, 0.0);
    }

    void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    void setWithAxis(boolean withAxis) {
        this.withAxis = withAxis;
    }

    void setAlwaysStartFromZero(boolean alwaysStartFromZero) {
        this.alwaysStartFromZero = alwaysStartFromZero;
    }

    void addLine(double a, double b, Color color) {
        lines.add(new Line(color, OrderedPair.of(a, b)));
    }

    void addPoints(List<OrderedPair<Double>> coordinates, Color color, int width) {
        coordinates.stream()
                .map(coordinate -> new Point(color, width, coordinate))
                .forEach(points::add);
        minCoordinate = OrderedPair.of(
                points.stream().map(p -> p.coordinates.a1).min(Double::compare).orElse(0.0),
                points.stream().map(p -> p.coordinates.a2).min(Double::compare).orElse(0.0)
        );
        maxCoordinate = OrderedPair.of(
                points.stream().map(p -> p.coordinates.a1).max(Double::compare).orElse(0.0),
                points.stream().map(p -> p.coordinates.a2).max(Double::compare).orElse(0.0)
        );

        if (alwaysStartFromZero) {
            double minX = Math.min(minCoordinate.a1, 0.0);
            double minY = Math.min(minCoordinate.a2, 0.0);
            axisPadding = OrderedPair.of(
                    (maxCoordinate.a1 - minX) * 0.05,
                    (maxCoordinate.a2 - minY) * 0.05
            );
            minCoordinate = OrderedPair.of(minX - axisPadding.a1, minY - axisPadding.a2);
            maxCoordinate = OrderedPair.of(maxCoordinate.a1 + axisPadding.a1, maxCoordinate.a2 + axisPadding.a2);
        } else {
            axisPadding = OrderedPair.of(
                    (maxCoordinate.a1 - minCoordinate.a1) * 0.05,
                    (maxCoordinate.a2 - minCoordinate.a2) * 0.05
            );
            minCoordinate = OrderedPair.of(minCoordinate.a1 - axisPadding.a1, minCoordinate.a2 - axisPadding.a2);
            maxCoordinate = OrderedPair.of(maxCoordinate.a1 + axisPadding.a1, maxCoordinate.a2 + axisPadding.a2);
        }
    }

    void save(String pathWithFilename) {
        initGraphics();
        paintAxis();
        paintPoints();
        paintLines();
        try {
            ImageIO.write(bufferedImage, "png", new File(pathWithFilename));
        } catch (IOException e) {
            System.err.println("Failed to save image to " + pathWithFilename);
            throw new RuntimeException(e);
        }
    }

    private void initGraphics() {
        graphics = new Graphics(bufferedImage.createGraphics(), width, height, backgroundColor, minCoordinate, maxCoordinate);
    }

    private void paintAxis() {
        if (!withAxis) {
            return;
        }
        graphics.setColor(Color.GRAY);
        double x0 = 0.0;
        double y0 = 0.0;
        if (!alwaysStartFromZero) {
            x0 = minCoordinate.a1 + axisPadding.a1 * 0.75;
            y0 = minCoordinate.a2 + axisPadding.a2 * 0.75;
        }

        for (double x = x0; x < maxCoordinate.a1; x += axisPadding.a1) {
            graphics.setColor(Color.LIGHT_GRAY);
            graphics.drawLine(x, minCoordinate.a2, x, maxCoordinate.a2);
            graphics.setColor(Color.GRAY);
            graphics.drawLine(x, y0 - axisPadding.a2 * 0.1, x, y0 + axisPadding.a2 * 0.1);
        }
        for (double y = y0; y < maxCoordinate.a2; y += axisPadding.a2) {
            graphics.setColor(Color.LIGHT_GRAY);
            graphics.drawLine(minCoordinate.a1, y, maxCoordinate.a1, y);
            graphics.setColor(Color.GRAY);
            graphics.drawLine(x0 - axisPadding.a1 * 0.1, y, x0 + axisPadding.a1 * 0.1, y);
        }

        graphics.drawLine(minCoordinate.a1, y0, maxCoordinate.a1, y0);
        graphics.drawLine(x0, minCoordinate.a2, x0, maxCoordinate.a2);
    }

    private void paintPoints() {
        points.forEach(point -> point.draw(graphics));
    }

    private void paintLines() {
        lines.forEach(line -> line.draw(graphics, minCoordinate.a1, maxCoordinate.a1));
    }
}
