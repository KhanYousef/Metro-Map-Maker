package m3.data;

import java.util.ArrayList;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;
import static m3.data.m3State.SELECTING_SHAPE;
import static m3.data.m3State.SIZING_SHAPE;
import m3.gui.m3Workspace;
import djf.components.AppDataComponent;
import djf.AppTemplate;
import javafx.event.Event;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import m3.gui.MapEditController;

/**
 * This class serves as the data management component for this application.
 *
 * @author Richard McKenna
 * @author ?
 * @version 1.0
 */
public class m3Data implements AppDataComponent {
    // FIRST THE THINGS THAT HAVE TO BE SAVED TO FILES
    
    // THESE ARE THE SHAPES TO DRAW
    ObservableList<Node> shapes;
    ObservableList<Node> images;
    ObservableList<String> shapeNames;
    
    // THE BACKGROUND COLOR
    Color backgroundColor;
    
    // AND NOW THE EDITING DATA

    // THIS IS THE SHAPE CURRENTLY BEING SIZED BUT NOT YET ADDED
    Shape newShape;
    ImageView newImage;
    // THIS IS THE SHAPE CURRENTLY SELECTED
    Shape selectedShape;
    ImageView selectedImage;

    // FOR FILL AND OUTLINE
    Color currentFillColor;
    Color currentOutlineColor;
    double currentBorderWidth;

    // CURRENT STATE OF THE APP
    m3State state;

    // THIS IS A SHARED REFERENCE TO THE APPLICATION
    AppTemplate app;
    MapEditController controller;
    
    // USE THIS WHEN THE SHAPE IS SELECTED
    Effect highlightedEffect;

    public static final String WHITE_HEX = "#FFFFFF";
    public static final String BLACK_HEX = "#000000";
    public static final String YELLOW_HEX = "#EEEE00";
    public static final Paint DEFAULT_BACKGROUND_COLOR = Paint.valueOf(WHITE_HEX);
    public static final Paint HIGHLIGHTED_COLOR = Paint.valueOf(YELLOW_HEX);
    public static final int HIGHLIGHTED_STROKE_THICKNESS = 3;

    /**
     * THis constructor creates the data manager and sets up the
     *
     *
     * @param initApp The application within which this data manager is serving.
     */
    public m3Data(AppTemplate initApp) {
	// KEEP THE APP FOR LATER
	app = initApp;

	// NO SHAPE STARTS OUT AS SELECTED
	newShape = null;
	selectedShape = null;
        newImage = null;
        selectedImage = null;

	// INIT THE COLORS
	currentFillColor = Color.web(WHITE_HEX);
	currentOutlineColor = Color.web(BLACK_HEX);
	currentBorderWidth = 1;
	
	// THIS IS FOR THE SELECTED SHAPE
	DropShadow dropShadowEffect = new DropShadow();
	dropShadowEffect.setOffsetX(0.0f);
	dropShadowEffect.setOffsetY(0.0f);
	dropShadowEffect.setSpread(1.0);
	dropShadowEffect.setColor(Color.YELLOW);
	dropShadowEffect.setBlurType(BlurType.GAUSSIAN);
	dropShadowEffect.setRadius(15);
	highlightedEffect = dropShadowEffect;
    }
    
    public ObservableList<Node> getShapes() {
	return shapes;
    }
    
    public ObservableList<Node> getImages() {
	return images;
    }

    public Color getBackgroundColor() {
	return backgroundColor;
    }
    
    public Color getCurrentFillColor() {
	return currentFillColor;
    }

    public Color getCurrentOutlineColor() {
	return currentOutlineColor;
    }

    public double getCurrentBorderWidth() {
	return currentBorderWidth;
    }
    
    public void setShapes(ObservableList<Node> initShapes) {
	shapes = initShapes;
    }
    
    public void setImages(ObservableList<Node> initImages) {
	images  = initImages;
    }

    public ObservableList<String> getShapeNames() {
        return shapeNames;
    }
    
    
    public void setBackgroundColor(Color initBackgroundColor) {
	backgroundColor = initBackgroundColor;
	m3Workspace workspace = (m3Workspace)app.getWorkspaceComponent();
	Pane canvas = workspace.getCanvas();
	BackgroundFill fill = new BackgroundFill(backgroundColor, null, null);
	Background background = new Background(fill);
	canvas.setBackground(background);
    }
    
    public void setBackgroundImage(){
        
    }

    public void setCurrentFillColor(Color initColor) {
	currentFillColor = initColor;
	if (selectedShape != null)
	    selectedShape.setFill(currentFillColor);
    }

    public void setCurrentOutlineColor(Color initColor) {
	currentOutlineColor = initColor;
	if (selectedShape != null) {
	    selectedShape.setStroke(initColor);
	}
    }

    public void setCurrentOutlineThickness(int initBorderWidth) {
	currentBorderWidth = initBorderWidth;
	if (selectedShape != null) {
	    selectedShape.setStrokeWidth(initBorderWidth);
	}
    }
    
    public void removeSelectedShape() {
        m3Workspace workspace = (m3Workspace)app.getWorkspaceComponent();
	if (selectedShape != null) {
            if(selectedShape instanceof MetroStation){
                workspace.getRow2Combo().getItems().remove(((MetroStation)selectedShape).stationName);
                shapes.remove(((MetroStation) selectedShape).getStationText());
            }
            else if (selectedShape instanceof MetroLine){
                workspace.getRow1Combo().getItems().remove(((MetroLine)selectedShape).lineName);
            }
	    shapes.remove(selectedShape);
	    selectedShape = null;
	}
    }
    
    public void removeSelectedStation() {
        m3Workspace workspace = (m3Workspace)app.getWorkspaceComponent();
	if (selectedShape != null) {
            if(selectedShape instanceof MetroStation){
                workspace.getRow2Combo().getItems().remove(((MetroStation)selectedShape).stationName);
                shapes.remove(((MetroStation) selectedShape).getStationText());
                shapes.remove(selectedShape);
            }
	    selectedShape = null;
	}
    }
    
    public void removeSelectedImage(){
        if (selectedImage != null) {
	    images.remove(selectedImage);
	    selectedImage = null;
	}
    }
    
    public void moveSelectedShapeToBack() {
	if (selectedShape != null) {
	    shapes.remove(selectedShape);
	    if (shapes.isEmpty()) {
		shapes.add(selectedShape);
	    }
	    else {
		ArrayList<Node> temp = new ArrayList<>();
		temp.add(selectedShape);
		for (Node node : shapes)
		    temp.add(node);
		shapes.clear();
		for (Node node : temp)
		    shapes.add(node);
	    }
	}
    }
    
    public void moveSelectedImageToBack() {
	if (selectedImage != null) {
	    images.remove(selectedImage);
	    if (images.isEmpty()) {
		images.add(selectedImage);
	    }
	    else {
		ArrayList<Node> temp = new ArrayList<>();
		temp.add(selectedImage);
		for (Node node : images)
		    temp.add(node);
		images.clear();
		for (Node node : temp)
		    images.add(node);
	    }
	}
    }
    
    public void moveSelectedShapeToFront() {
	if (selectedShape != null) {
	    shapes.remove(selectedShape);
	    shapes.add(selectedShape);
	}
    }
    
    public void moveSelectedImageToFront() {
	if (selectedImage != null) {
	    images.remove(selectedImage);
	    images.add(selectedImage);
	}
    }
 
    /**
     * This function clears out the HTML tree and reloads it with the minimal
     * tags, like html, head, and body such that the user can begin editing a
     * page.
     */
    @Override
    public void resetData() {
	setState(SELECTING_SHAPE);
	newShape = null;
	selectedShape = null;
        newImage = null;
        selectedImage = null;

	// INIT THE COLORS
	currentFillColor = Color.web(WHITE_HEX);
	currentOutlineColor = Color.web(BLACK_HEX);
	
	shapes.clear();
	((m3Workspace)app.getWorkspaceComponent()).getCanvas().getChildren().clear();
    }

    public void selectSizedShape() {
	if (selectedShape != null)
	    unhighlightShape(selectedShape);
	selectedShape = newShape;
	highlightShape(selectedShape);
	newShape = null;
	if (state == SIZING_SHAPE) {
	    state = ((Draggable)selectedShape).getStartingState();
	}
    }
    
    public void unhighlightShape(Shape shape) {
	selectedShape.setEffect(null);
    }
    
    public void unhighlightImage(ImageView image) {
	selectedImage.setEffect(null);
    }
    
    public void highlightShape(Shape shape) {
	shape.setEffect(highlightedEffect);
    }
    
    public void highlightImage(ImageView image) {
	image.setEffect(highlightedEffect);
    }

    public void startNewRectangle(int x, int y) {
	DraggableRectangle newRectangle = new DraggableRectangle();
	newRectangle.start(x, y);
	newShape = newRectangle;
	initNewShape();
    }

    public void startNewEllipse(int x, int y) {
	DraggableEllipse newEllipse = new DraggableEllipse();
	newEllipse.start(x, y);
        newShape = newEllipse;
	initNewShape();
    }
    
    
    public void addNewMetroLine(String name, Color colour){
        m3Workspace workspace = (m3Workspace) app.getWorkspaceComponent();
        Pane canvas = workspace.getCanvas();
        controller = workspace.getLogoEditController();
        MetroLine newLine = new MetroLine(controller.getMetrolineName(), controller.getMetrolineColour());
//        newLine.start();
        
        newShape = newLine;
        
        m3Data dataManager = (m3Data) app.getDataComponent();
        workspace.reloadWorkspace(dataManager);
        
        // DESELECT THE SELECTED SHAPE IF THERE IS ONE
	if (selectedShape != null) {
	    unhighlightShape(selectedShape);
	    selectedShape = null;
	}
        
        shapes.add(newShape);
        shapes.add(newLine.nameTextL);
        shapes.add(newLine.nameTextR);
        state = m3State.SELECTING_SHAPE;
        workspace.getRow1Combo().getItems().add(newLine.lineName);
        workspace.getMetroSlider().setValue(newLine.getStrokeWidth());
    }
    
    public void addNewStation(String name, Color colour){
        m3Workspace workspace = (m3Workspace) app.getWorkspaceComponent();
        controller = workspace.getLogoEditController();
        
        MetroStation newStation = new MetroStation(controller.getStationName(), controller.getStationColour());
        newShape = newStation;
        newStation.setStationText(controller.getStationName());
        if (selectedShape != null) {
	    unhighlightShape(selectedShape);
	    selectedShape = null;
	}
        
        //ADD the station to canvas
        shapes.add(newShape);
        
//        newShape = stationText;
        //ADD the station label to canvas
        shapes.add(newStation.getStationText());
    }

    public void initNewShape() {
	// DESELECT THE SELECTED SHAPE IF THERE IS ONE
	if (selectedShape != null) {
	    unhighlightShape(selectedShape);
	    selectedShape = null;
	}

	// USE THE CURRENT SETTINGS FOR THIS NEW SHAPE
	m3Workspace workspace = (m3Workspace)app.getWorkspaceComponent();
//	newShape.setFill(workspace.getFillColorPicker().getValue());
	newShape.setStroke(workspace.getOutlineColorPicker().getValue());
	newShape.setStrokeWidth(workspace.getOutlineThicknessSlider().getValue());
	
	// ADD THE SHAPE TO THE CANVAS
	shapes.add(newShape);
	
	// GO INTO SHAPE SIZING MODE
	state = m3State.SIZING_SHAPE;
    }

    public Shape getNewShape() {
	return newShape;
    }

    public Shape getSelectedShape() {
	return selectedShape;
    }
    
    public ImageView getSelectedImage(){
        return selectedImage;
    }

    public void setSelectedShape(Shape initSelectedShape) {
	selectedShape = initSelectedShape;
    }
    
    public void setSelectedImage(ImageView initSelectedImage) {
	selectedImage  = initSelectedImage;
    }

    public Shape selectTopShape(int x, int y) {
	Shape shape = getTopShape(x, y);
        if (shape != null) {
	    ((Draggable)shape).start(x, y);
	}
        
	if (shape == selectedShape)
	    return shape;
	
	if (selectedShape != null) {
	    unhighlightShape(selectedShape);
	}
	if (shape != null) {
	    highlightShape(shape);
	    m3Workspace workspace = (m3Workspace)app.getWorkspaceComponent();
	    workspace.loadSelectedShapeSettings(shape);
	}
        
	selectedShape = shape;
        
	
	return shape;
    }
    
//    public Shape selectShapeByName() {
//        m3Workspace workspace = (m3Workspace)app.getWorkspaceComponent();
//        String lineName = workspace.getRow1Combo().getValue().toString();
//        if(lineName != null){
////            if(shapes.contains(MetroLine someLine) && ){
//        
////        }
//        }
//        
//	
//	return shape;
//    }
    
    public ImageView selectTopImage(int x, int y) {
	ImageView image = getTopImage(x, y);
	if (image == selectedImage)
	    return image;
	
	if (selectedImage != null) {
	    unhighlightImage(selectedImage);
	}
	if (image != null) {
	    highlightImage(image);
	    m3Workspace workspace = (m3Workspace)app.getWorkspaceComponent();
	}
	selectedImage = image;
	if (image != null) {
	    ((Draggable)image).start(x, y);
	}
	return image;
    }

    public Shape getTopShape(int x, int y) {
	for (int i = shapes.size() - 1; i >= 0; i--) {
	    Shape shape = (Shape)shapes.get(i);
	    if (shape.contains(x, y)) {
		return shape;
	    }
	}
	return null;
    }
    
    public ImageView getTopImage(int x, int y) {
	for (int i = images.size() - 1; i >= 0; i--) {
	    ImageView image = (ImageView)images.get(i);
	    if (image.contains(x, y)) {
		return image;
	    }
	}
	return null;
    }

    public void addShape(Shape shapeToAdd) {
	shapes.add(shapeToAdd);
    }
    
    public void addImage(ImageView imageToAdd){
        images.add(imageToAdd);
    }

    public void removeShape(Shape shapeToRemove) {
	shapes.remove(shapeToRemove);
    }
    
    public void removeMetroLine(MetroLine lineToRemove){
        shapes.remove(lineToRemove.nameTextL);
        shapes.remove(lineToRemove.nameTextR);
        for (MetroStation station : lineToRemove.station) {
            shapes.remove(station);
            shapes.remove(station.stationText);
        }
        shapes.remove(lineToRemove);
    }
    
    public void removeImage(ImageView imageToRemove){
        images.remove(imageToRemove);
    }

    public m3State getState() {
	return state;
    }

    public void setState(m3State initState) {
	state = initState;
    }

    public boolean isInState(m3State testState) {
	return state == testState;
    }
}
