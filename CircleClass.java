/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ColoredCircles;

import static java.awt.geom.Point2D.distance;

/**
 *
 * @author rahon
 */
private static class CircleClass {
    double radiusOfCircle = 20;
    double xCord, yCord;
    
    //default
    CircleClass(double x, double y){
        this.xCord = x;
        this.yCord = y;
    }
    //check if vertex of new inside old
    public boolean contains(double x, double y){
        double dist = distance(xCord, yCord, x, y);
        return dist <= radiusOfCircle;
    }
    
    //checks for overlap
    public boolean overlaps(CircleClass circle){
        return distance(this.xCord, this.yCord, circle.xCord)
    }
    
}
