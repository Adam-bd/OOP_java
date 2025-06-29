package org.example.breakoutgame;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Ball extends GraphicsItem{
    private Point2D lastPosition;
    private Point2D moveVector = new Point2D(1, -1).normalize();
    private double velocity = 450;

    public void setPosition(Point2D position){
        this.x = position.getX() - width/2;
        this.y = position.getY() - height/2;
        lastPosition = position;
    }

    public void updatePosition(double diff){
        lastPosition = new Point2D(x, y);
        x += moveVector.getX() * velocity * diff;
        y += moveVector.getY() * velocity * diff;
    }


    @Override
    public void draw(GraphicsContext graphicsContext) {
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillOval(x, y, width, height);
    }

    public Point2D getLastPosition(){
        return lastPosition;
    }

    public void bounceHorizontally(){
        moveVector = new Point2D(-moveVector.getX(), moveVector.getY()).normalize();
    }

    public void bounceVertically(){
        moveVector = new Point2D(moveVector.getX(), -moveVector.getY()).normalize();
    }

    public Point2D topLeftExtremePoint(){
        return new Point2D(x - width/2, y - height/2);
    }

    public Point2D topRightExtremePoint(){
        return new Point2D(x + width/2, y - height/2);
    }

    public Point2D bottomLeftExtremePoint(){
        return new Point2D(x + width/2, y + height/2);
    }

    public Point2D bottomRightExtremePoint(){
        return new Point2D(x - width/2, y + height/2);
    }
}
