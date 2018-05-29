/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m3.data;

import java.util.ArrayList;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import static m3.data.Draggable.LINE;

/**
 *
 * @author Yousef Khan
 */
public class MetroLine extends Line/*implements  Draggable*/{
    double startX;
    double startY;
//    MetroStation[] stations;
    ArrayList<MetroStation> station;
    DoubleProperty size;
    String lineName;
    Color lineColour;
    Text nameTextL;
    Text nameTextR;
    EditableDraggableText nameText;
    Boolean isCircular;
    
    public MetroLine(String name, Color colour) {
        station = new ArrayList<>();
        size = new SimpleDoubleProperty();
        startX = 100.0;
        startY = 200.0;
        
        setStartX(100.0);
        setStartY(200.0);
        setEndX(500.0);
        setEndY(200.0);
        
        setStroke(colour);
        setStrokeWidth(8.0);
	setOpacity(1.0);
	setStartX(startX);
	setStartY(startY);
        lineName = name;
        lineColour = colour;
        nameTextL = new EditableDraggableText(name);
        nameTextL.setX(100);
        nameTextL.setY(500);
        nameTextR = new EditableDraggableText(name);
        nameTextR.setX(200);
        nameText = new EditableDraggableText(name);
        nameTextR.setY(500);
        
            
        this.startXProperty().bindBidirectional(nameTextL.xProperty());
        this.startYProperty().bindBidirectional(nameTextL.yProperty());
        this.endXProperty().bindBidirectional(nameTextR.xProperty());
        this.endYProperty().bindBidirectional(nameTextR.yProperty());
//             
//        
//        


//        nameTextJ.yProperty().bind(((this.startYProperty().add(this.endYProperty())).divide(3)).multiply(1));

//        nameTextA.xProperty().bind((this.startXProperty().add(this.startYProperty()).divide(3)).multiply(2));
//        nameTextA.xProperty().bind((this.startYProperty().add(this.endYProperty()).divide(3)).multiply(2));
//        nameTextL.layoutXProperty().bind(startXProperty().subtract(75));
//        nameTextL.layoutYProperty().bind(startYProperty());
//        nameTextR.layoutXProperty().bind(endXProperty().add(15));
//        nameTextR.layoutYProperty().bind(endYProperty()); 
        
//        stations = new MetroStation[50];
        isCircular = false;
        
    }

    public String getLineName() {
        return lineName;
    }
    
    public void setLineName(String newName) {
        lineName = newName;
    }

    public Color getLineColour() {
        return lineColour;
    }
    
    public void setLineColour(Color someColour) {
        lineColour = someColour;
        setStroke(someColour);
    }

    public ArrayList<MetroStation> getStations() {
        return station;
    }
    
    public Text getNameTextL() {
        return nameTextL;
    }

    public void setNameTextL(String nameTextL) {
        this.nameTextL.setText(nameTextL);
    }

    public Text getNameTextR() {
        return nameTextR;
    }

    public void setNameTextR(String nameTextR) {
        this.nameTextR.setText(nameTextR);
    }
    
    public boolean getCircular(){
        return isCircular;
    }

    public void addStation(MetroStation newStation){
        newStation.centerXProperty().bind(((this.endXProperty().subtract(this.startXProperty())).multiply(station.size() + 1).divide(size)).add(this.startXProperty()));
        newStation.centerYProperty().bind(((this.endYProperty().subtract(this.startYProperty())).multiply(station.size() + 1).divide(size)).add(this.startYProperty()));
        
        station.add(newStation);
        size.set(station.size());
        
        //        nameTextJ.xProperty().
//        nameTextJ.yProperty().bind
    }
    
    public void removeStation(MetroStation newStation){
        newStation.centerXProperty().unbind();
        newStation.centerYProperty().unbind();
        newStation.setCenterX(100);
        newStation.setCenterY(100);
        station.remove(newStation);
        size.set(station.size());
        int counter = 0;
        for(MetroStation s: station){
            s.centerXProperty().bind(((this.endXProperty().subtract(this.startXProperty())).multiply(counter + 1).divide(size)).add(this.startXProperty()));
            s.centerYProperty().bind(((this.endYProperty().subtract(this.startYProperty())).multiply(counter + 1).divide(size)).add(this.startYProperty()));
            counter++;
        }
    }
    

////    @Override
//    public m3State getStartingState() {
//        return m3State.STARTING_POLYLINE;
//    }

    
    public void start() {
        startX = 100.0;
	startY = 200.0;
	setStartX(startX);
	setStartY(startY);
        setEndX(500.0);
        setEndY(200.0);
    }
    
    public String getShapeType() {
	return LINE;
    }

    
}
