package com.aiebt.utils.plot;

import com.aiebt.ai.regression.core.OrderedPair;

import java.awt.Color;

public class Point {

    final Color color;
    final int width;
    final OrderedPair<Double> coordinates;

    public Point(Color color, int width, OrderedPair<Double> coordinates) {
        this.color = color;
        this.width = width;
        this.coordinates = coordinates;
    }

    void draw(Graphics graphics) {
        Color previousColor = graphics.getColor();
        graphics.setColor(color);
        graphics.fillOval(coordinates.a1, coordinates.a2, width, width);
        graphics.setColor(previousColor);
    }
}
