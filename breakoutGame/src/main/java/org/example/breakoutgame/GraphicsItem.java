package org.example.breakoutgame;

import javafx.scene.canvas.GraphicsContext;

public abstract class GraphicsItem {
    protected static double canvasWidth;
    protected static double canvasHeight;
    protected double x;
    protected double y;
    protected double width;
    protected double height;

//    public static void setCanvasWidth(double canvasWidth) {
//        GraphicsItem.canvasWidth = canvasWidth;
//    }
//
//    public static void setCanvasHeight(double canvasHeight) {
//        GraphicsItem.canvasHeight = canvasHeight;
//    }

    public static void setCanvasSize(double canvasWidth, double canvasHeight) {
        GraphicsItem.canvasWidth = canvasWidth;
        GraphicsItem.canvasHeight = canvasHeight;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public abstract void draw(GraphicsContext graphicsContext);
}
