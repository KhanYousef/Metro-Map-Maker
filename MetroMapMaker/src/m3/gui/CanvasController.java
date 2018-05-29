package m3.gui;

import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.shape.Shape;
import m3.data.m3Data;
import m3.data.Draggable;
import m3.data.m3State;
import static m3.data.m3State.DRAGGING_NOTHING;
import static m3.data.m3State.DRAGGING_SHAPE;
import static m3.data.m3State.SELECTING_SHAPE;
import static m3.data.m3State.SIZING_SHAPE;
import djf.AppTemplate;
import djf.controller.AppFileController;
import djf.ui.AppGUI;
import m3.data.DraggableEllipse;
import m3.data.DraggableRectangle;
import m3.data.EditableDraggableText;
import static m3.data.m3State.DRAGGING_IMAGE;
import static m3.data.m3State.SELECTING_IMAGE;
import java.util.Optional;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import static m3.data.Draggable.LINE;
import m3.data.MetroLine;
import m3.data.MetroStation;
import static m3.data.m3State.ADDING_TO_LINE;
import static m3.data.m3State.REMOVING_FROM_LINE;

/**
 * This class responds to interactions with the rendering surface.
 *
 * @author Richard McKenna
 * @author ?
 * @version 1.0
 */
public class CanvasController {

    AppTemplate app;
    AppFileController filecontroller;
    AppGUI gui;
    
    Shape copiedShape;
    MetroLine metroShape;

    public CanvasController(AppTemplate initApp) {
        app = initApp;
    }

    /**
     * Respond to mouse presses on the rendering surface, which we call canvas,
     * but is actually a Pane.
     */
    public void processCanvasMousePress(int x, int y) {
        m3Workspace workspace = (m3Workspace) app.getWorkspaceComponent();
        m3Data dataManager = (m3Data) app.getDataComponent();
        Pane canvas = workspace.getCanvas();
        

        if (dataManager.isInState(SELECTING_SHAPE)) {
            // SELECT THE TOP SHAPE
            Shape shape = dataManager.selectTopShape(x, y);
            Scene scene = app.getGUI().getPrimaryScene();
            
//            filecontroller.markAsSelected(gui);
            // AND START DRAGGING IT
            if (shape != null) {
                scene.setCursor(Cursor.MOVE);
                dataManager.setState(m3State.DRAGGING_SHAPE);
                app.getGUI().updateToolbarControls(false);
            } else {
                scene.setCursor(Cursor.DEFAULT);
                dataManager.setState(DRAGGING_NOTHING);
                app.getWorkspaceComponent().reloadWorkspace(dataManager);
            }
        } else if (dataManager.isInState(m3State.STARTING_RECTANGLE)) {
            dataManager.startNewRectangle(x, y);
//            filecontroller.markAsUnselectd(gui);
        } else if (dataManager.isInState(m3State.STARTING_ELLIPSE)) {
            dataManager.startNewEllipse(x, y);
//            filecontroller.markAsUnselectd(gui);
        } else if (dataManager.isInState(ADDING_TO_LINE)){
            Scene scene = app.getGUI().getPrimaryScene();
            Shape shape = dataManager.selectTopShape((int)x, (int)y);
            String lineName = "";
            
            if(workspace.getRow1Combo().getValue() != null){
                lineName = workspace.getRow1Combo().getValue().toString();
            }
            if(shape instanceof MetroStation){
                ObservableList<Node> j = dataManager.getShapes();
                for(Node nod : j){
                    if(nod instanceof MetroLine && ((MetroLine)nod).getLineName().equals(lineName)){
                        ((MetroLine)nod).addStation((MetroStation)shape);
                    }
                }
            }
            else{
                System.out.println("this is not a station");
                scene.setCursor(Cursor.DEFAULT);
                dataManager.setState(SELECTING_SHAPE);
                
            }
        } else if (dataManager.isInState(REMOVING_FROM_LINE)){
            Scene scene = app.getGUI().getPrimaryScene();
            Shape shape = dataManager.selectTopShape((int)x, (int)y);
            String lineName = "";
            
            if(workspace.getRow1Combo().getValue() != null){
                lineName = workspace.getRow1Combo().getValue().toString();
            }
            if(shape instanceof MetroStation){
                ObservableList<Node> j = dataManager.getShapes();
                for(Node nod : j){
                    if(nod instanceof MetroLine && ((MetroLine)nod).getLineName().equals(lineName)){
                        ((MetroLine)nod).removeStation((MetroStation)shape);
                    }
                }
            }
            else{
                System.out.println("this is not a station");
                scene.setCursor(Cursor.DEFAULT);
                dataManager.setState(SELECTING_SHAPE);
            }
        }

       workspace.reloadWorkspace(dataManager);
       
    }
    
    
    public void processCanvasNodeCopy(double x, double y){
        m3Workspace workspace = (m3Workspace) app.getWorkspaceComponent();
        m3Data dataManager = (m3Data) app.getDataComponent();
        
        if (dataManager.isInState(SELECTING_SHAPE)) {
            // SELECT THE TOP SHAPE
            Shape shape = dataManager.selectTopShape((int)x, (int)y);
            if(shape instanceof DraggableEllipse){
                DraggableEllipse cloneEllipse = new DraggableEllipse();
                cloneEllipse = ((DraggableEllipse) shape).clone();
                copiedShape = cloneEllipse;
            }
            else if(shape instanceof DraggableRectangle){
                DraggableRectangle cloneRect = new DraggableRectangle();
                cloneRect = ((DraggableRectangle) shape).clone();
                copiedShape = cloneRect;
            }
            else if(shape instanceof EditableDraggableText){
                EditableDraggableText cloneText = new EditableDraggableText();
                cloneText = ((EditableDraggableText) shape).clone();
                copiedShape = cloneText;
            }
        }
        
    }
    
    
    public void processCanvasNodePaste(){
        m3Workspace workspace = (m3Workspace) app.getWorkspaceComponent();
        Pane canvas = workspace.getCanvas();
        
        canvas.getChildren().add(copiedShape);
    }
    
    public void processCanvasNodeCut(double x, double y){
        m3Workspace workspace = (m3Workspace) app.getWorkspaceComponent();
        m3Data dataManager = (m3Data) app.getDataComponent();
        
        if (dataManager.isInState(SELECTING_SHAPE)) {
            // SELECT THE TOP SHAPE
            Shape shape = dataManager.selectTopShape((int)x, (int)y);
            if(shape instanceof DraggableEllipse){
                DraggableEllipse cloneEllipse = new DraggableEllipse();
                cloneEllipse = ((DraggableEllipse) shape).clone();
                copiedShape = cloneEllipse;
                dataManager.removeSelectedShape();
            }
            else if(shape instanceof DraggableRectangle){
                DraggableRectangle cloneRect = new DraggableRectangle();
                cloneRect = ((DraggableRectangle) shape).clone();
                copiedShape = cloneRect;
                dataManager.removeSelectedShape();
            }
            else if(shape instanceof EditableDraggableText){
                EditableDraggableText cloneText = new EditableDraggableText();
                cloneText = ((EditableDraggableText) shape).clone();
                copiedShape = cloneText;
                dataManager.removeSelectedShape();
            }
        }
        
    }
    
    
    public void processCanvasMouseDoubleClicked(double x, double y){
        m3Workspace workspace = (m3Workspace) app.getWorkspaceComponent();
        m3Data dataManager = (m3Data) app.getDataComponent();
        Pane canvas = workspace.getCanvas();
        
        if(dataManager.isInState(SELECTING_SHAPE)){
            Shape shape = dataManager.selectTopShape((int)x, (int)y);
            if(shape instanceof EditableDraggableText){
                String samepleText = ((EditableDraggableText) shape).getText();
                TextInputDialog chooseText = new TextInputDialog(samepleText);
                chooseText.setTitle("Text Node");
                chooseText.setHeaderText("Enter text to be included");
                
                Optional<String> result = chooseText.showAndWait();
                if(result.isPresent()){
                    ((EditableDraggableText) shape).setText(result.get());
                }
            }
            
        }
        workspace.reloadWorkspace(dataManager);
    }
    
    public void processCanvasMouseTextEdit(double  x, double y){
        m3Workspace workspace = (m3Workspace) app.getWorkspaceComponent();
        m3Data dataManager = (m3Data) app.getDataComponent();
        Pane canvas = workspace.getCanvas();
        
        if(dataManager.isInState(SELECTING_SHAPE)){
            Shape shape = dataManager.selectTopShape((int)x, (int)y);
            if(shape instanceof EditableDraggableText){
                String sampleText = ((EditableDraggableText) shape).getText();
                ComboBox font = workspace.getFontFamCombo();
                ComboBox fontSize = workspace.getFontSizeCombo();
                font.setValue(((EditableDraggableText) shape).getFont().getName());
                fontSize.setValue((int)((EditableDraggableText) shape).getFont().getSize());
//                filecontroller.markAsSelected(gui);
            }
        }
    }
    
    public void processCanvasTextUpdate(double x, double y){
        m3Workspace workspace = (m3Workspace) app.getWorkspaceComponent();
        m3Data dataManager = (m3Data) app.getDataComponent();
        Pane canvas = workspace.getCanvas();
        
        if(dataManager.isInState(SELECTING_SHAPE)){
            Shape shape = dataManager.selectTopShape((int)x, (int)y);
            if(shape instanceof EditableDraggableText){
                String sampleText = ((EditableDraggableText) shape).getText();
                ComboBox font = workspace.getFontFamCombo();
                ComboBox fontSize = workspace.getFontSizeCombo();
                double size = Integer.parseInt(fontSize.getValue().toString());
                ((EditableDraggableText) shape).setFont(Font.font(font.getValue().toString(), size));
            }
        }
    }
    
    public void processCanvasTextBold(double x, double y){
        m3Workspace workspace = (m3Workspace) app.getWorkspaceComponent();
        m3Data dataManager = (m3Data) app.getDataComponent();
        Pane canvas = workspace.getCanvas();
        
        if(dataManager.isInState(SELECTING_SHAPE)){
            Shape shape = dataManager.selectTopShape((int)x, (int)y);
            if(shape instanceof EditableDraggableText){
                String sampleText = ((EditableDraggableText) shape).getText();
                ComboBox font = workspace.getFontFamCombo();
                ComboBox fontSize = workspace.getFontSizeCombo();
                double size = Integer.parseInt(fontSize.getValue().toString());
                if(((EditableDraggableText) shape).isBold() == false){
                    if(((EditableDraggableText) shape).isItalic() == false){
                        ((EditableDraggableText) shape).setFont(Font.font(font.getValue().toString(), FontWeight.BOLD, FontPosture.REGULAR, size));
                        ((EditableDraggableText) shape).setBold(true);
                    }
                    else{
                        ((EditableDraggableText) shape).setFont(Font.font(font.getValue().toString(), FontWeight.BOLD, FontPosture.ITALIC, size));
                        ((EditableDraggableText) shape).setBold(true);
                    }
                }
                else if(((EditableDraggableText) shape).isBold() == true){
                    if(((EditableDraggableText) shape).isItalic() == false){
                        ((EditableDraggableText) shape).setFont(Font.font(font.getValue().toString(), FontWeight.NORMAL, FontPosture.REGULAR, size));
                        ((EditableDraggableText) shape).setBold(false);
                    }
                    else{
                        ((EditableDraggableText) shape).setFont(Font.font(font.getValue().toString(), FontWeight.NORMAL, FontPosture.ITALIC, size));
                        ((EditableDraggableText) shape).setBold(false);
                    }
                }
            }
        }
    }
    
    
    
    public void processCanvasTextItalic(double x, double y){
        m3Workspace workspace = (m3Workspace) app.getWorkspaceComponent();
        m3Data dataManager = (m3Data) app.getDataComponent();
        Pane canvas = workspace.getCanvas();
        
        if(dataManager.isInState(SELECTING_SHAPE)){
            Shape shape = dataManager.selectTopShape((int)x, (int)y);
            if(shape instanceof EditableDraggableText){
                String sampleText = ((EditableDraggableText) shape).getText();
                ComboBox font = workspace.getFontFamCombo();
                ComboBox fontSize = workspace.getFontSizeCombo();
                double size = Integer.parseInt(fontSize.getValue().toString());
                if(((EditableDraggableText) shape).isItalic() == false){
                    if(((EditableDraggableText) shape).isBold() == false){
                        ((EditableDraggableText) shape).setFont(Font.font(font.getValue().toString(), FontWeight.NORMAL, FontPosture.ITALIC, size));
                        ((EditableDraggableText) shape).setItalic(true);
                    }
                    else{
                        ((EditableDraggableText) shape).setFont(Font.font(font.getValue().toString(), FontWeight.BOLD, FontPosture.ITALIC, size));
                        ((EditableDraggableText) shape).setItalic(true);
                    }
                }
                else if(((EditableDraggableText) shape).isItalic() == true){
                    if(((EditableDraggableText) shape).isBold() == false){
                        ((EditableDraggableText) shape).setFont(Font.font(font.getValue().toString(), FontWeight.NORMAL, FontPosture.REGULAR, size));
                        ((EditableDraggableText) shape).setItalic(false);
                    }
                    else{
                        ((EditableDraggableText) shape).setFont(Font.font(font.getValue().toString(), FontWeight.BOLD, FontPosture.REGULAR, size));
                        ((EditableDraggableText) shape).setItalic(false);
                    }
                }
            }
        }
    }
    

    /**
     * Respond to mouse dragging on the rendering surface, which we call canvas,
     * but is actually a Pane.
     */
    public void processCanvasMouseDragged(int x, int y) {
        m3Data dataManager = (m3Data) app.getDataComponent();
        if (dataManager.isInState(SIZING_SHAPE)) {
            Draggable newDraggableShape = (Draggable) dataManager.getNewShape();
            newDraggableShape.size(x, y);
        } else if (dataManager.isInState(DRAGGING_SHAPE)) {
            Draggable selectedDraggableShape = (Draggable) dataManager.getSelectedShape();
            selectedDraggableShape.drag(x, y);
            app.getGUI().updateToolbarControls(false);
        }
    }

    /**
     * Respond to mouse button release on the rendering surface, which we call canvas,
     * but is actually a Pane.
     */
    public void processCanvasMouseRelease(int x, int y) {
        m3Data dataManager = (m3Data) app.getDataComponent();
        m3Workspace workspace = (m3Workspace)app.getWorkspaceComponent();
        if (dataManager.isInState(SIZING_SHAPE)) {
            dataManager.selectSizedShape();
            // Return cursor to default
            Scene scene = app.getGUI().getPrimaryScene();
            scene.setCursor(Cursor.DEFAULT);
	
            // CHANGE THE STATE
            dataManager.setState(m3State.SELECTING_SHAPE);	

            // ENABLE/DISABLE THE PROPER BUTTONS
            workspace.reloadWorkspace(dataManager);
            app.getGUI().updateToolbarControls(false);
//            filecontroller.markAsSelected(gui);
        } else if (dataManager.isInState(m3State.DRAGGING_SHAPE)) {
            dataManager.setState(SELECTING_SHAPE);
            Scene scene = app.getGUI().getPrimaryScene();
            scene.setCursor(Cursor.DEFAULT);
            app.getGUI().updateToolbarControls(false);
//            filecontroller.markAsUnselectd(gui);
        } 

        else if (dataManager.isInState(m3State.DRAGGING_NOTHING)) {
            dataManager.setState(SELECTING_SHAPE);
//            filecontroller.markAsUnselectd(gui);
        }
    }
}
