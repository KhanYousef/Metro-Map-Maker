/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m3.data;

import javafx.beans.property.DoubleProperty;
import javafx.scene.image.ImageView;

/**
 *
 * @author Yousef Khan
 */
public class DraggableImage extends ImageView implements Draggable{
    
    double startX;
    double startY;
    
    public DraggableImage(){
        setX(0.0);
	setY(0.0);
	setOpacity(1.0);
        startX = 0.0;
	startY = 0.0;
    }

    @Override
    public m3State getStartingState() {
        return m3State.STARTING_IMAGE;
    }

    @Override
    public void start(int x, int y) {
        startX = x;
	startY = y;
        setX(x);
        setY(y);
    }

    @Override
    public void drag(int x, int y) {
        double diffX = x - (getX() + (getFitWidth()/2));
	double diffY = y - (getY() + (getFitHeight()/2));
	double newX = getX() + diffX;
	double newY = getY() + diffY;
	xProperty().set(newX);
	yProperty().set(newY);
	startX = x;
	startY = y;
    }

    @Override
    public void size(int x, int y) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    public void setLocationAndSize(double initX, double initY) {
        xProperty().set(initX);
	yProperty().set(initY);
    }

    @Override
    public String getShapeType() {
        return IMAGE;
    }

    @Override
    public double getWidth() {
        return getFitWidth();
    }

    @Override
    public double getHeight() {
        return getFitHeight();
    }

    @Override
    public void setLocationAndSize(double initX, double initY, double initWidth, double initHeight) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    
}
