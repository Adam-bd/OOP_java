package org.example.breakoutgame;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class GameCanvas extends Canvas{
    private GraphicsContext graphicsContext;

     public GameCanvas(double v, double v1){
         super(v, v1);
         this.graphicsContext = this.getGraphicsContext2D();
     }

     public void draw(){
         graphicsContext.setFill(Color.BLACK);
         graphicsContext.fillRect(0, 0, this.getWidth(), this.getHeight());
     }
}

