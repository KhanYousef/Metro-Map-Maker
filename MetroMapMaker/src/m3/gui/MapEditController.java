package m3.gui;

import java.io.File;
import java.io.IOException;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javax.imageio.ImageIO;
import m3.data.m3Data;
import m3.data.m3State;
import djf.AppTemplate;
import djf.ui.AppMessageDialogSingleton;
import m3.data.DraggableRectangle;
import m3.data.EditableDraggableText;
import java.awt.Desktop;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import m3.data.MetroLine;
import m3.data.MetroStation;
import static m3.data.m3State.ADDING_TO_LINE;
import static m3.data.m3State.REMOVING_FROM_LINE;

/**
 * This class responds to interactions with other UI logo editing controls.
 * 
 * @author Richard McKenna
 * @author ?
 * @version 1.0
 */
public class MapEditController {
    AppTemplate app;
    m3Data dataManager;
    
    String metrolineName;
    Color metrolineColour;
    
    String stationName;
    Color stationColour;
    
    double zoomScale = 1.0;

    public String getMetrolineName() {
        return metrolineName;
    }

    public Color getMetrolineColour() {
        return metrolineColour;
    }

    public String getStationName() {
        return stationName;
    }

    public Color getStationColour() {
        return stationColour;
    }
    
    
    
    public MapEditController(AppTemplate initApp) {
	app = initApp;
	dataManager = (m3Data)app.getDataComponent();
    }
    
    /**
     * This method handles the response for selecting either the
     * selection or removal tool.
     */
    public void processSelectSelectionTool() {
	// CHANGE THE CURSOR
	Scene scene = app.getGUI().getPrimaryScene();
	scene.setCursor(Cursor.DEFAULT);
	
	// CHANGE THE STATE
            dataManager.setState(m3State.SELECTING_SHAPE);	
	
	// ENABLE/DISABLE THE PROPER BUTTONS
	m3Workspace workspace = (m3Workspace)app.getWorkspaceComponent();
	workspace.reloadWorkspace(dataManager);
    }
    
    /**
     * This method handles a user request to remove the selected shape.
     */
    public void processRemoveSelectedShape() {
	// REMOVE THE SELECTED SHAPE IF THERE IS ONE
        if(dataManager.getState().equals(m3State.SELECTING_SHAPE)){
            dataManager.removeSelectedShape();
        }

	// ENABLE/DISABLE THE PROPER BUTTONS
	m3Workspace workspace = (m3Workspace)app.getWorkspaceComponent();
	workspace.reloadWorkspace(dataManager);
	app.getGUI().updateToolbarControls(false);
    }
    
    public void processRemoveSelectedStation(){
        // REMOVE THE SELECTED STATION IF THERE IS ONE
        if(dataManager.getState().equals(m3State.SELECTING_SHAPE)){
            dataManager.removeSelectedStation();
        }

	// ENABLE/DISABLE THE PROPER BUTTONS
	m3Workspace workspace = (m3Workspace)app.getWorkspaceComponent();
	workspace.reloadWorkspace(dataManager);
	app.getGUI().updateToolbarControls(false);
    }
    
    public void removeSelectedMetroLine(){
        m3Workspace workspace = (m3Workspace)app.getWorkspaceComponent();
        String lineName = "";
        
        if(workspace.getRow1Combo().getValue() != null){
                lineName = workspace.getRow1Combo().getValue().toString();
            }
        
        ObservableList<Node> j = dataManager.getShapes();
                for(Node nod : j){
                    if(nod instanceof MetroLine && ((MetroLine)nod).getLineName().equals(lineName)){
                        workspace.getRow1Combo().getItems().remove(lineName);
                        dataManager.removeMetroLine((MetroLine)nod);
                        
                    }
                }
    }
    
    /**
     * This method processes a user request to start drawing a rectangle.
     */
    public void processSelectRectangleToDraw() {
	// CHANGE THE CURSOR
	Scene scene = app.getGUI().getPrimaryScene();
	scene.setCursor(Cursor.CROSSHAIR);
	
	// CHANGE THE STATE
	dataManager.setState(m3State.STARTING_RECTANGLE);

	// ENABLE/DISABLE THE PROPER BUTTONS
	m3Workspace workspace = (m3Workspace)app.getWorkspaceComponent();
	workspace.reloadWorkspace(dataManager);
    }
    
    public void processEditMetroLine(){
        m3Workspace workspace = (m3Workspace)app.getWorkspaceComponent();
        Stage primaryStage = app.getGUI().getWindow();
        
        String lineName = "";
            
            if(workspace.getRow1Combo().getValue() != null){
                lineName = workspace.getRow1Combo().getValue().toString();
                ObservableList<Node> j = dataManager.getShapes();
                
                EditLineDialog editLine = new EditLineDialog();
                editLine.init(primaryStage);
                for(Node nod : j){
                    if(nod instanceof MetroLine && ((MetroLine)nod).getLineName().equals(lineName)){
                        editLine.setStationName(lineName);
                        editLine.setColour(((MetroLine)nod).getLineColour());
                    }
                }
                editLine.show("Edit Metro Line", "Edit line name and colour");
                
                
                if(editLine.getSelection().equals("Cancel")){
                    
                }
                else if(editLine.getSelection().equals("Ok")){
                    for(Node nod : j){
                        if(nod instanceof MetroLine && ((MetroLine)nod).getLineName().equals(lineName)){
                            ((MetroLine)nod).setLineName(editLine.stationName.getText());
                            ((MetroLine)nod).setNameTextL(editLine.stationName.getText());
                            ((MetroLine)nod).setNameTextR(editLine.stationName.getText());
                            ((MetroLine)nod).setLineColour(editLine.colour.getValue());
                            String l = lineName;
                            workspace.getRow1Combo().getItems().add(editLine.stationName.getText());
                            workspace.getRow1Combo().getItems().remove(l);
                        }
                    }
                }
                
                
            }
            else{
                System.out.println("No Line Selected");
            }
    }
    
    /**
     * This method processes a user request to start drawing a metro line.
     */
    public void processSelectMetroLineToDraw() {
        
        m3Workspace workspace = (m3Workspace)app.getWorkspaceComponent();
        Stage primaryStage = app.getGUI().getWindow();
        Pane canvas = workspace.getCanvas();
        ColorPicker lineColour = new ColorPicker();
        
        AddMetroLineDialog lineName = new AddMetroLineDialog();
        lineName.init(primaryStage);
        lineName.show("Add New Metro Line", "Choose a line name and colour");
        if(lineName.getSelection().equals("Cancel")){
            
        }
        else if(lineName.getSelection().equals("Ok")){
            metrolineName = lineName.stationName.getText();
            metrolineColour = lineName.colour.getValue();
            
            dataManager.addNewMetroLine(metrolineName, metrolineColour);
                    
//            workspace.getRow1Combo().getItems().add(metrolineName);
            
           // CHANGE THE CURSOR
	Scene scene = app.getGUI().getPrimaryScene();
//	scene.setCursor(Cursor.CROSSHAIR);
	
	// CHANGE THE STATE
//	dataManager.setState(m3State.STARTING_POLYLINE);
        }

	// ENABLE/DISABLE THE PROPER BUTTONS
	
	workspace.reloadWorkspace(dataManager);
    }
    
    public void processAddStation(){
        Stage primaryStage = app.getGUI().getWindow();
        m3Workspace workspace = (m3Workspace)app.getWorkspaceComponent();
        Pane canvas = workspace.getCanvas();
        Scene scene = app.getGUI().getPrimaryScene();
        
        AddMetroLineDialog stationNameDialog = new AddMetroLineDialog();
        stationNameDialog.init(primaryStage);
        stationNameDialog.show("Add New Metro Station", "Choose a name and colour");
        if(stationNameDialog.getSelection().equals("Cancel")){
            
        }
        else if(stationNameDialog.getSelection().equals("Ok")){
            stationName = stationNameDialog.stationName.getText();
            stationColour = stationNameDialog.colour.getValue();
            
            dataManager.addNewStation(stationName, stationColour);
//            MetroStation newStation = new MetroStation(stationName, stationColour);   
//            
//            canvas.getChildren().add(newStation);
//            Text nameText = new Text(stationName);
            
            
            workspace.getRow2Combo().getItems().add(stationName);
        }
        
    }
    
    
    
    public void addStationtoLine(){
        m3Workspace workspace = (m3Workspace)app.getWorkspaceComponent();
        Pane canvas = workspace.getCanvas();
        Scene scene = app.getGUI().getPrimaryScene();
        dataManager.setState(ADDING_TO_LINE);
        scene.setCursor(Cursor.HAND);
    }
    
    public void removeStationfromLine(){
        m3Workspace workspace = (m3Workspace)app.getWorkspaceComponent();
        Pane canvas = workspace.getCanvas();
        Scene scene = app.getGUI().getPrimaryScene();
        dataManager.setState(REMOVING_FROM_LINE);
        scene.setCursor(Cursor.HAND);
    }
    
    
    
    /**
     * This method provides a response to the user requesting to start
     * drawing an ellipse.
     */
    public void processSelectEllipseToDraw() {
	// CHANGE THE CURSOR
	Scene scene = app.getGUI().getPrimaryScene();
	scene.setCursor(Cursor.CROSSHAIR);
	
	// CHANGE THE STATE
	dataManager.setState(m3State.STARTING_ELLIPSE);

	// ENABLE/DISABLE THE PROPER BUTTONS
	m3Workspace workspace = (m3Workspace)app.getWorkspaceComponent();
	workspace.reloadWorkspace(dataManager);
    }
    
    
    
    public void processOpenImageFile(){
        Stage primaryStage = app.getGUI().getWindow();
        m3Workspace workspace = (m3Workspace)app.getWorkspaceComponent();
        Pane canvas = workspace.getCanvas();
        Scene scene = app.getGUI().getPrimaryScene();
        FileChooser imageFileChooser = new FileChooser();
        File file;
        ImageView imageview;
        Image image;
        Desktop desktop = Desktop.getDesktop();
        imageFileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg")
        );
        file = imageFileChooser.showOpenDialog(primaryStage);
        if(file != null){
                image = new Image(file.toURI().toString(), 0, 0, true, true);
                imageview = new ImageView(image);
//                imageview.setFitWidth(150);
//                imageview.setFitHeight(150);
                imageview.setPreserveRatio(true);
                imageview.setSmooth(true);
                DraggableRectangle newDR = new DraggableRectangle();
                newDR.setWidth(150);
                newDR.setHeight(150);
                newDR.setFill(new ImagePattern(image));
                canvas.getChildren().add(newDR);
                
        }
        
    }
    
    
    
    public void processOpenTextNode(){
        m3Workspace workspace = (m3Workspace)app.getWorkspaceComponent();
        Pane canvas = workspace.getCanvas();
        Scene scene = app.getGUI().getPrimaryScene();
        
        TextInputDialog chooseText = new TextInputDialog();
        chooseText.setTitle("Text Node");
        chooseText.setHeaderText("Enter text to be included");
        
        Optional<String> result = chooseText.showAndWait();
        if(result.isPresent()){
            EditableDraggableText textNode = new EditableDraggableText(result.get());
            
//            DraggableRectangle newDR = new DraggableRectangle();
//            newDR.setFill(new Label(result));
            canvas.getChildren().add(textNode);
        }
    }
    
    /**
     * This method processes a user request to move the selected shape
     * down to the back layer.
     */
    public void processMoveSelectedShapeToBack() {
	dataManager.moveSelectedShapeToBack();
	app.getGUI().updateToolbarControls(false);
    }
    
    /**
     * This method processes a user request to move the selected shape
     * up to the front layer.
     */
    public void processMoveSelectedShapeToFront() {
	dataManager.moveSelectedShapeToFront();
	app.getGUI().updateToolbarControls(false);
    }
        
    /**
     * This method processes a user request to select a fill color for
     * a shape.
     */
    public void processSelectFillColor() {
	m3Workspace workspace = (m3Workspace)app.getWorkspaceComponent();
//	Color selectedColor = workspace.getFillColorPicker().getValue();
//	if (selectedColor != null) {
//	    dataManager.setCurrentFillColor(selectedColor);
	    app.getGUI().updateToolbarControls(false);
	}
    
    /**
     * This method processes a user request to select the outline
     * color for a shape.
     */
//    public void processSelectOutlineColor() {
//	golWorkspace workspace = (golWorkspace)app.getWorkspaceComponent();
//	Color selectedColor = workspace.getOutlineColorPicker().getValue();
//	if (selectedColor != null) {
//	    dataManager.setCurrentOutlineColor(selectedColor);
//	    app.getGUI().updateToolbarControls(false);
//	}    
//    }
    
    /**
     * This method processes a user request to select the 
     * background color.
     */
    public void processSelectBackgroundColor() {
	m3Workspace workspace = (m3Workspace)app.getWorkspaceComponent();
	Color selectedColor = workspace.getBackgroundColorPicker().getValue();
	if (selectedColor != null) {
	    dataManager.setBackgroundColor(selectedColor);
	    app.getGUI().updateToolbarControls(false);
	}
    }
    
    public void setBackgroundImage(){
        m3Workspace workspace = (m3Workspace)app.getWorkspaceComponent();
        Stage primaryStage = app.getGUI().getWindow();
        Pane canvas = workspace.getCanvas();
        
        FileChooser imageFileChooser = new FileChooser();
        File file;
        ImageView imageview;
        Image image;
        Desktop desktop = Desktop.getDesktop();
        imageFileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg")
        );
        file = imageFileChooser.showOpenDialog(primaryStage);
        if(file != null){
            image = new Image(file.toURI().toString(), 0, 0, true, true);
            imageview = new ImageView(image);
            BackgroundFill newFill = new BackgroundFill(new ImagePattern(image), null, null);
            Background bg = new Background(newFill);
            canvas.setBackground(bg);
        }
    }
    
    public void mapZoomIn(){
        m3Workspace workspace = (m3Workspace)app.getWorkspaceComponent();
        Pane canvas = workspace.getCanvas();
        zoomScale = zoomScale + 0.5;
        canvas.setScaleX(zoomScale);
    }
    
    public void mapZoomOut(){
        m3Workspace workspace = (m3Workspace)app.getWorkspaceComponent();
        Pane canvas = workspace.getCanvas();
        zoomScale = zoomScale - 0.5;
        canvas.setScaleX(zoomScale);
    }
    
    public void adjustLineThickness(){
        m3Workspace workspace = (m3Workspace)app.getWorkspaceComponent();
        
        String lineName = "";
            
            if(workspace.getRow1Combo().getValue() != null){
                lineName = workspace.getRow1Combo().getValue().toString();
                
                ObservableList<Node> j = dataManager.getShapes();
                for(Node nod : j){
                    if(nod instanceof MetroLine && ((MetroLine)nod).getLineName().equals(lineName)){
                        ((MetroLine)nod).setStrokeWidth(workspace.getMetroSlider().getValue());
                    }
                }
                
            }
                
    }
    
    /**
     * This method processes a user request to select the outline
     * thickness for shape drawing.
     */
//    public void processSelectOutlineThickness() {
//	golWorkspace workspace = (golWorkspace)app.getWorkspaceComponent();
//	int outlineThickness = (int)workspace.getOutlineThicknessSlider().getValue();
//	dataManager.setCurrentOutlineThickness(outlineThickness);
//	app.getGUI().updateToolbarControls(false);
//    }
    
    /**
     * This method processes a user request to take a snapshot of the
     * current scene.
     */
    public void processSnapshot() {
	m3Workspace workspace = (m3Workspace)app.getWorkspaceComponent();
	Pane canvas = workspace.getCanvas();
	WritableImage image = canvas.snapshot(new SnapshotParameters(), null);
	File file = new File("Map.png");
	try {
	    ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
	}
	catch(IOException ioe) {
	    ioe.printStackTrace();
	}
    }

}