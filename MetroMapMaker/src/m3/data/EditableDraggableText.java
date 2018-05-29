/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m3.data;

import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 *
 * @author Yousef Khan
 */
public class EditableDraggableText extends Text implements Draggable{
    double startX;
    double startY;
    boolean bold = false;
    boolean italic = false;
    String fillingText;
    
    public String getFillingText() {
        return fillingText;
    }

    public void setFillingText(String fillingText) {
        this.fillingText = fillingText;
    }
    
    
    public EditableDraggableText(String text){
        fillingText = text;
        setText(text);
        setX(0.0);
	setY(0.0);
//	setWidth(0.0);
//	setHeight(0.0);
	setOpacity(1.0);
	startX = 0.0;
	startY = 0.0; 
    }
    
    public EditableDraggableText clone(){
        EditableDraggableText cloneText = new EditableDraggableText();
        cloneText.setText(this.getText());
        cloneText.fillingText = this.fillingText;
        cloneText.startX = this.startX;
        cloneText.startY = this.startY;
        cloneText.setFont(this.getFont());
        cloneText.setFont(Font.font(this.getFont().getName(), this.getFont().getSize()));
        cloneText.setBold(this.isBold());
        cloneText.setItalic(this.isItalic());
        if(this.isBold() == true)
            cloneText.setStyle("-fx-font-weight: bold");
        if(this.isItalic() == true)
            cloneText.setStyle("-fx-font-style: italic");
        
        return cloneText;
    }
    
    public EditableDraggableText(){
        setX(0.0);
	setY(0.0);
//	setWidth(0.0);
//	setHeight(0.0);
	setOpacity(1.0);
	startX = 0.0;
	startY = 0.0; 
    }

    @Override
    public m3State getStartingState() {
        return m3State.DRAGGING_SHAPE;
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
        double diffX = x - (getX()); //+ (getWidth()/2));
	double diffY = y - (getY()); //+ (getHeight()/2));
	double newX = getX() + diffX;
	double newY = getY() + diffY;
	xProperty().set(newX);
	yProperty().set(newY);
	startX = x;
	startY = y;
    }

    @Override
    public void size(int x, int y) {
        double width = x - getX();
//	widthProperty().set(width);
	double height = y - getY();
//	heightProperty().set(height);
    }
    
    public boolean isBold(){
        return bold;
    }
    
    public boolean isItalic(){
        return italic;
    }
    
    public void setBold(Boolean tf){
        bold = tf;
    }
    
    public void setItalic(Boolean tf){
        italic = tf;
    }

    @Override
    public double getWidth() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double getHeight() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setLocationAndSize(double initX, double initY, double initWidth, double initHeight) {
        xProperty().set(initX);
	yProperty().set(initY);
//	widthProperty().set(initWidth);
//	heightProperty().set(initHeight);
    }

    @Override
    public String getShapeType() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
