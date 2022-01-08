package com.aiebt.utils.plot;

import com.aiebt.ai.linear_regression.core.OrderedPair;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

class Graphics {

    private final Graphics2D graphics2D;
    private final OrderedPair<Double> scale;
    private final OrderedPair<Double> translate;
    private final OrderedPair<Double> minCoordinate;
    private final OrderedPair<Double> maxCoordinate;

    public Graphics(Graphics2D graphics2D, int width, int height, Color backgroundColor, OrderedPair<Double> minCoordinate, OrderedPair<Double> maxCoordinate) {
        this.graphics2D = graphics2D;
        this.minCoordinate = minCoordinate;
        this.maxCoordinate = maxCoordinate;

        translate = OrderedPair.of(
                -((maxCoordinate.a1 + minCoordinate.a1) / 2.0),
                -((maxCoordinate.a2 + minCoordinate.a2) / 2.0)
        );
        scale = OrderedPair.of(
                (1.0 * width) / (Math.abs(maxCoordinate.a1) + Math.abs(minCoordinate.a1)),
                (1.0 * height) / (Math.abs(maxCoordinate.a2) + Math.abs(minCoordinate.a2))
        );

        centralize(width, height);
        paintBackground(backgroundColor, width, height);
    }

    Color getColor() {
        return graphics2D.getColor();
    }

    void setColor(Color color) {
        graphics2D.setColor(color);
    }

    void drawLine(double x0, double y0, double x1, double y1) {
        graphics2D.drawLine(xToInt(x0), yToInt(y0), xToInt(x1), yToInt(y1));
    }

    void fillOval(double x0, double y0, int width, int height) {
        graphics2D.fillOval(xToInt(x0) - width / 2, yToInt(y0) - height / 2, width, height);
    }

    private void centralize(int width, int height) {
        AffineTransform centralize = new AffineTransform();
        centralize.scale(1, -1);
        centralize.translate(0, -height);
        centralize.translate(width / 2.0, height / 2.0);
        graphics2D.transform(centralize);
    }

    private void paintBackground(Color backgroundColor, int width, int height) {
        graphics2D.setBackground(backgroundColor);
        graphics2D.clearRect((int) ((minCoordinate.a1 + translate.a1) * scale.a1), (int) ((minCoordinate.a2 + translate.a2) * scale.a2), width, height);
    }

    private int xToInt(double x) {
        return (int) Math.round((x + translate.a1) * scale.a1);
    }

    private int yToInt(double y) {
        return (int) Math.round((y + translate.a2) * scale.a2);
    }
}
