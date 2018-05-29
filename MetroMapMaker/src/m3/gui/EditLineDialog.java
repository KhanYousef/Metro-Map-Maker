/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m3.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;

/**
 *
 * @author Yousef Khan
 */
public class EditLineDialog extends Stage{
    // HERE'S THE SINGLETON
    static EditLineDialog singleton;
    
    // GUI CONTROLS FOR OUR DIALOG
    VBox messagePane;
    Scene messageScene;
    Label messageLabel;
    Button okButton;
    Button cancelButton;
    CheckBox circularCheck;
    String selection;
    TextField stationName;
    ColorPicker colour;
    HBox controlPane;
    
    // CONSTANT CHOICES

    public static final String OK = "Ok";
    public static final String CANCEL = "Cancel";
    
    /**
     * @param primaryStage The owner of this modal dialog.
     */
    
    /**
     * The static accessor method for this singleton.
     * 
     * @return The singleton object for this type.
     */
    public static EditLineDialog getSingleton() {
	if (singleton == null)
	    singleton = new EditLineDialog();
	return singleton;
    }
	
    /**
     * This method initializes the singleton for use.
     * 
     * @param primaryStage The window above which this
     * dialog will be centered.
     */
    public void init(Stage primaryStage) {
        // MAKE THIS DIALOG MODAL, MEANING OTHERS WILL WAIT
        // FOR IT WHEN IT IS DISPLAYED
        initModality(Modality.WINDOW_MODAL);
        initOwner(primaryStage);
        
        // LABEL TO DISPLAY THE CUSTOM MESSAGE
        messageLabel = new Label();        

        // YES, NO, AND CANCEL BUTTONS
        okButton = new Button(OK);
        cancelButton = new Button(CANCEL);
	
	// MAKE THE EVENT HANDLER FOR THESE BUTTONS
        EventHandler<ActionEvent> decisionHandler = (ActionEvent ae) -> {
            Button sourceButton = (Button)ae.getSource();
            EditLineDialog.this.selection = sourceButton.getText();
            EditLineDialog.this.hide();
        };
        
	// AND THEN REGISTER THEM TO RESPOND TO INTERACTIONS
        okButton.setOnAction(decisionHandler);
        cancelButton.setOnAction(decisionHandler);

        // NOW ORGANIZE OUR BUTTONS
        HBox buttonBox = new HBox();
        buttonBox.getChildren().add(okButton);
        buttonBox.getChildren().add(cancelButton);
        buttonBox.setSpacing(10);
        
        //CONTROLS FOR METRO LINE
        controlPane = new HBox();
        stationName = new TextField();
        colour = new ColorPicker();
        circularCheck = new CheckBox("Circular");
        controlPane.getChildren().add(stationName);
        controlPane.getChildren().add(colour);
        controlPane.getChildren().add(circularCheck);
        controlPane.setSpacing(20);
        
        // WE'LL PUT EVERYTHING HERE
        messagePane = new VBox();
        messagePane.setAlignment(Pos.CENTER);
        messagePane.getChildren().add(messageLabel);
        messagePane.getChildren().add(controlPane);
        messagePane.getChildren().add(buttonBox);
        
        // MAKE IT LOOK NICE
        messagePane.setPadding(new Insets(10, 20, 20, 20));
        messagePane.setSpacing(10);

        // AND PUT IT IN THE WINDOW
        messageScene = new Scene(messagePane);
        this.setScene(messageScene);
    }

    public void setCircularCheck(Boolean circularCheck) {
        this.circularCheck.setSelected(circularCheck);
    }

    public void setStationName(String stationName) {
        this.stationName.setText(stationName);
    }

    public void setColour(Color colour) {
        this.colour.setValue(colour);
    }
    
    

    /**
     * Accessor method for getting the selection the user made.
     * 
     * @return Either YES, NO, or CANCEL, depending on which
     * button the user selected when this dialog was presented.
     */
    public String getSelection() {
        return selection;
    }
 
    /**
     * This method loads a custom message into the label
     * then pops open the dialog.
     * 
     * @param title The title to appear in the dialog window bar.
     * 
     * @param message Message to appear inside the dialog.
     */
    public void show(String title, String message) {
	// SET THE DIALOG TITLE BAR TITLE
	setTitle(title);
	
	// SET THE MESSAGE TO DISPLAY TO THE USER
        messageLabel.setText(message);
	
	// AND OPEN UP THIS DIALOG, MAKING SURE THE APPLICATION
	// WAITS FOR IT TO BE RESOLVED BEFORE LETTING THE USER
	// DO MORE WORK.
        showAndWait();
    }
}
