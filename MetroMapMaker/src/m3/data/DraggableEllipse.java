package m3.data;

import javafx.scene.shape.Ellipse;

/**
 * This is a draggable ellipse for our goLogoLo application.
 * 
 * @author Richard McKenna
 * @author ?
 * @version 1.0
 */
public class DraggableEllipse extends Ellipse implements Draggable {
    double startCenterX;
    double startCenterY;
    
    public DraggableEllipse() {
	setCenterX(0.0);
	setCenterY(0.0);
	setRadiusX(0.0);
	setRadiusY(0.0);
	setOpacity(1.0);
	startCenterX = 0.0;
	startCenterY = 0.0;
    }
    
    public DraggableEllipse clone(){
        DraggableEllipse newEllipse = new DraggableEllipse();
        newEllipse.startCenterX = this.startCenterX;
        newEllipse.startCenterY = this.startCenterY;
        newEllipse.setSize(this.getWidth(), this.getHeight());
        newEllipse.setFill(this.getFill());
        newEllipse.setStroke(this.getStroke());
        
        return newEllipse;
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
