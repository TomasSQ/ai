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
            // TODO instead of hardcoded padding, make it depend on min/max coords;
            double padding = 2.0;
            minCoordinate = OrderedPair.of(Math.min(minCoordinate.a1, -padding), Math.min(minCoordinate.a2, -padding));
            maxCoordinate = OrderedPair.of(maxCoordinate.a1 + padding, maxCoordinate.a2 + padding);
        }
        // TODO make it work when it doesn't start form zero;
    }

    void save(String pathWithFilename) {
        if (!alwaysStartFromZero) {
            throw new IllegalStateException("Must always start from zero. Did you forget to call setAlwaysStartFromZero?");
        }
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
        graphics.drawLine(minCoordinate.a1, 0.0, maxCoordinate.a1, 0.0);
        graphics.drawLine(0.0, minCoordinate.a2, 0.0, maxCoordinate.a2);
    }

    private void paintPoints() {
        points.forEach(point -> point.draw(graphics));
    }

    private void paintLines() {
        lines.forEach(line -> line.draw(graphics, minCoordinate.a1, maxCoordinate.a1));
    }
}
