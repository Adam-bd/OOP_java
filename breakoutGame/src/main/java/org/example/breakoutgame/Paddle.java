package org.example.breakoutgame;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Paddle extends GraphicsItem{
    public Paddle(){
        this.height = 0.02 * canvasHeight;
        this.width = 0.3 * canvasWidth;
        this.x = (canvasWidth - width) / 2.0;
        this.y = 0.9 * canvasHeight;
    }

    @Override
    public void draw(GraphicsContext graphicsContext) {
        graphicsContext.setFill(Color.RED);
        graphicsContext.fillRect(x, y, width, height);
    }
    public void setPosition(double x){
        this.x = clamp(x - width/2, 0, canvasWidth - width);
    }
    public static double clamp(double val, double min, double max){
        return Math.max(min, Math.min(max, val));
    }
}
