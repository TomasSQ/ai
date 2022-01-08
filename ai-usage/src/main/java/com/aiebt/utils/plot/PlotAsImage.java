package com.aiebt.utils.plot;

import com.aiebt.ai.linear_regression.core.OrderedPair;

import java.awt.Color;
import java.util.List;

public class PlotAsImage {

    private final Image image;

    public PlotAsImage(int width, int height) {
        image = new Image(width, height);
    }

    public PlotAsImage backgroundColor(Color color) {
        image.setBackgroundColor(color);
        return this;
    }

    public PlotAsImage withAxis() {
        image.setWithAxis(true);
        return this;
    }

    public PlotAsImage alwaysStartFromZero() {
        image.setAlwaysStartFromZero(true);
        return this;
    }

    /**
     * Adds a line in the format y = a + bx, where a and b are constants
     * @param a the linear coefficient
     * @param b the linear constant
     * @return itself
     */
    public PlotAsImage addLine(double a, double b, Color color) {
        image.addLine(a, b, color);
        return this;
    }

    public PlotAsImage addPoints(List<OrderedPair<Double>> coordinates, Color color) {
        image.addPoints(coordinates, color, 5);
        return this;
    }

    public void save(String pathWithFilename) {
        image.save(pathWithFilename);
    }
}
