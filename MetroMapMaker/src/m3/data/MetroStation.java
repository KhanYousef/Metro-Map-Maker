/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m3.data;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.text.Text;

/**
 *
 * @author Yousef Khan
 */
public class MetroStation extends Ellipse implements Draggable{
    double startCenterX;
    double startCenterY;
    String stationName;
    Color stationColour;
    Text stationText;
    

   public MetroStation(String name, Color colour) {
        stationName = name;
        stationColour = colour;
        stationText = new Text(name);
        setFill(colour);
	setCenterX(0.0);
	setCenterY(0.0);
	setRadiusX(10);
	setRadiusY(10);
	setOpacity(1.0);
	startCenterX = 0.0;
	startCenterY = 0.0;
        stationText.layoutXProperty().bind(centerXProperty().add(15.0));
        stationText.layoutYProperty().bind(centerYProperty().add(15.0));
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public Color getStationColour() {
        return stationColour;
    }

    public void setStationColour(Color stationColour) {
        this.stationColour = stationColour;
    }

    public Text getStationText() {
        return stationText;
    }

    public void setStationText(String stationText) {
        this.stationText.setText(stationText);
    }
    
    
    
    
   
   
    
    @Override
    public m3State getStartingState() {
	return m3State.STARTING_ELLIPSE;
    }
    
    @Override
    public void start(int x, int y) {
	startCenterX = x;
	startCenterY = y;
    }
    
    @Override
    public void drag(int x, int y) {
	double diffX = x - startCenterX;
	double diffY = y - startCenterY;
	double newX = getCenterX() + diffX;
	double newY = getCenterY() + diffY;
	setCenterX(newX);
	setCenterY(newY);
	startCenterX = x;
	startCenterY = y;
    }
    
    @Override
    public void size(int x, int y) {
	double width = x - startCenterX;
	double height = y - startCenterY;
	double centerX = startCenterX + (width / 2);
	double centerY = startCenterY + (height / 2);
	setCenterX(centerX);
	setCenterY(centerY);
	setRadiusX(width / 2);
	setRadiusY(height / 2);	
	
    }
        
    @Override
    public double getX() {
	return getCenterX() - getRadiusX();
    }

    @Override
    public double getY() {
	return getCenterY() - getRadiusY();
    }

    @Override
    public double getWidth() {
	return getRadiusX() * 2;
    }

    @Override
    public double getHeight() {
	return getRadiusY() * 2;
    }
        
    @Override
    public void setLocationAndSize(double initX, double initY, double initWidth, double initHeight) {
	setCenterX(initX + (initWidth/2));
	setCenterY(initY + (initHeight/2));
	setRadiusX(initWidth/2);
	setRadiusY(initHeight/2);
    }
    
    public void setSize(double initWidth, double initHeight){
        setRadiusX(initWidth/2);
	setRadiusY(initHeight/2);
    }
    
    @Override
    public String getShapeType() {
	return ELLIPSE;
    }
    
}
