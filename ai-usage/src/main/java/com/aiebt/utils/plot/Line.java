package com.aiebt.utils.plot;

import com.aiebt.ai.regression.core.OrderedPair;

import java.awt.Color;

class Line {

    private final Color color;
    private final OrderedPair<Double> coefficients;

    public Line(Color color, OrderedPair<Double> coefficients) {
        this.color = color;
        this.coefficients = coefficients;
    }

    void draw(Graphics graphics, double minX, double maxX) {
        Color previousColor = graphics.getColor();

        graphics.setColor(color);
        graphics.drawLine(
                minX,
                coefficients.a1 + coefficients.a2 * minX,
                maxX,
                coefficients.a1 + coefficients.a2 * maxX
        );

        graphics.setColor(previousColor);
    }
}
