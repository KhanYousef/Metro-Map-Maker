/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package djf.ui;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Modality;
import static djf.settings.AppStartupConstants.CLOSE_BUTTON_LABEL;
import java.io.File;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Yousef Khan
 */
public class AboutAppDialogSingleton extends Stage{
    // HERE'S THE SINGLETON OBJECT
    static AboutAppDialogSingleton singleton = null;
    
    // HERE ARE THE DIALOG COMPONENTS
    VBox messagePane;
    Scene messageScene;
    Label messageLabel1;
    Label messageLabel2;
    Label messageLabel3;
    Image logoimg;
    Button closeButton;
    
    /**
     * Initializes this dialog so that it can be used repeatedly
     * for all kinds of messages. Note this is a singleton design
     * pattern so the constructor is private.
     * 
     * @param owner The owner stage of this modal dialoge.
     * 
     * @param closeButtonText Text to appear on the close button.
     */
    public AboutAppDialogSingleton() {}
    
    /**
     * A static accessor method for getting the singleton object.
     * 
     * @return The one singleton dialog of this object type.
     */
    public static AboutAppDialogSingleton getSingleton() {
	if (singleton == null)
	    singleton = new AboutAppDialogSingleton();
	return singleton;
    }
    
    /**
     * This function fully initializes the singleton dialog for use.
     * 
     * @param owner The window above which this dialog will be centered.
     */
    public void init(Stage owner) {
        // MAKE IT MODAL
        initModality(Modality.WINDOW_MODAL);
        initOwner(owner);
        
        // LABEL TO DISPLAY THE CUSTOM MESSAGE
        messageLabel1 = new Label();     
        messageLabel2 = new Label();
        messageLabel3 = new Label();
        
        final String dir = System.getProperty("user.dir");
        File file = new File(dir + "\\image\\goLogoLoLogo.png");
        logoimg = new Image(file.toURI().toString(), 50, 50, true, true);
        
        ImageView iv = new ImageView(logoimg);
        iv.setPreserveRatio(true);
        iv.setSmooth(true);
        

        // CLOSE BUTTON
        closeButton = new Button(CLOSE_BUTTON_LABEL);
        closeButton.setOnAction(e->{ AboutAppDialogSingleton.this.close(); });

        // WE'LL PUT EVERYTHING HERE
        messagePane = new VBox();
        messagePane.setAlignment(Pos.CENTER);
        messagePane.getChildren().add(messageLabel1);
        messagePane.getChildren().add(iv);
        messagePane.getChildren().add(messageLabel2);
        messagePane.getChildren().add(messageLabel3);
        messagePane.getChildren().add(closeButton);
        
        // MAKE IT LOOK NICE
        messagePane.setPadding(new Insets(100, 80, 100, 80));
        messagePane.setSpacing(20);

        // AND PUT IT IN THE WINDOW
        messageScene = new Scene(messagePane);
        this.setScene(messageScene);
    }
 
    /**
     * This method loads a custom message into the label and
     * then pops open the dialog.
     * 
     * @param title The title to appear in the dialog window.
     * 
     * @param message Message to appear inside the dialog.
     * 
     * @param msg2 Next line of message to appear.
     * 
     * @param msg3 Following line of message to appear.
     */
    public void show(String title, String message,  String msg2, String msg3) {
	// SET THE DIALOG TITLE BAR TITLE
	setTitle(title);
	
	// SET THE MESSAGE TO DISPLAY TO THE USER
        messageLabel1.setText(message);
        messageLabel2.setText(msg2);
        messageLabel3.setText(msg3);
	
	// AND OPEN UP THIS DIALOG, MAKING SURE THE APPLICATION
	// WAITS FOR IT TO BE RESOLVED BEFORE LETTING THE USER
	// DO MORE WORK.
        showAndWait();
    }
    
}
