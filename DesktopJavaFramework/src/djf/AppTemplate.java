package djf;

import djf.ui.*;
import djf.components.*;
import javafx.application.Application;
import javafx.stage.Stage;
import properties_manager.PropertiesManager;
import static djf.settings.AppPropertyType.*;
import static djf.settings.AppStartupConstants.*;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Clock;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import properties_manager.InvalidXMLFileFormatException;

/**
 * This is the framework's JavaFX application. It provides the start method
 * that begins the program initialization, which delegates component
 * initialization to the application-specific child class' hook function.
 * 
 * @author Richard McKenna
 * @version 1.0
 */
public abstract class AppTemplate extends Application {

    // THIS IS THE APP'S FULL JavaFX GUI. NOTE THAT ALL APPS WOULD
    // SHARE A COMMON UI EXCEPT FOR THE CUSTOM WORKSPACE
    protected AppGUI gui;

    // THIS CLASS USES A COMPONENT ARCHITECTURE DESIGN PATTERN, MEANING IT
    // HAS OBJECTS THAT CAN BE SWAPPED OUT FOR OTHER COMPONENTS
    // THIS APP HAS 4 COMPONENTS
    
    // THE COMPONENT FOR MANAGING CUSTOM APP DATA
    protected AppDataComponent dataComponent;
    
    // THE COMPONENT FOR MANAGING CUSTOM FILE I/O
    protected AppFileComponent fileComponent;

    // THE COMPONENT FOR THE GUI WORKSPACE
    protected AppWorkspaceComponent workspaceComponent;
        
    // THIS METHOD MUST BE OVERRIDDEN WHERE THE CUSTOM BUILDER OBJECT
    // WILL PROVIDE THE CUSTOM APP COMPONENTS

    /**
     * This function must be overridden, it should initialize all
     * of the components used by the app in the proper order according
     * to the particular app's dependencies.
     */
    public abstract void buildAppComponentsHook();
    
    // COMPONENT ACCESSOR METHODS

    /**
     *  Accessor for the data component.
     */
    public AppDataComponent getDataComponent() { return dataComponent; }

    /**
     *  Accessor for the file component.
     */
    public AppFileComponent getFileComponent() { return fileComponent; }

    /**
     *  Accessor for the workspace component.
     */
    public AppWorkspaceComponent getWorkspaceComponent() { return workspaceComponent; }
    
    /**
     *  Accessor for the gui. Note that the GUI would contain the workspace.
     */
    public AppGUI getGUI() { return gui; }

    /**
     * This is where our Application begins its initialization, it will load
     * the custom app properties, build the components, and fully initialize
     * everything to get the app rolling.
     *
     * @param primaryStage This application's window.
     */
    
    boolean change;
    
    public boolean getLangChange(){
        return change;
    }
    @Override
    public void start(Stage primaryStage) {
        final String dir = System.getProperty("user.dir");

        change = false;
        List<String> choices = new ArrayList<>();
        choices.add("English");
        choices.add("French");
        
        ChoiceDialog<String> langdialog = new ChoiceDialog<>("English", choices);
        langdialog.setTitle("Choice Dialog");
        langdialog.setHeaderText("Look, a choice dialog!");
        langdialog.setContentText("Choose a language");
        
        File f = new File(dir + "\\LanguageFile.txt");
        String fileContent = "Nothing";
        if(f.exists() && !f.isDirectory()){
//            System.out.println("The file is here, this is what I typed");
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            DataInputStream dis = null;
            try {
                fis = new FileInputStream(f);
                
                bis = new BufferedInputStream(fis); //bis for fast reading
                dis = new DataInputStream(bis);
                
                while(dis.available() != 0){ // will only return zero if file does not have more lines
                    fileContent = dis.readLine(); //reads line from file and prints to console
                    if(fileContent.equals("English")){
                        change = false;
                    }
                    else if(fileContent.equals("French")){
                        change = true;
                    }
                }
                fis.close();
                bis.close();
                dis.close();
            } catch (FileNotFoundException ex) {
                System.out.println(ex.getMessage());
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
        else if(!f.exists()){
            Optional<String> result = langdialog.showAndWait();
        
            if(result.isPresent()){
                if(result.get().equals("French")){
                    change = true;
                }
            }
        
            result.ifPresent(letter -> System.out.println("Your choice: " + letter));
            
            try {
                PrintWriter fstream = new PrintWriter("LanguageFile.txt");
                if(change == true){
                   fstream.write("French"); 
                }
                else{
                    fstream.write("English");
                }
                fstream.close();
            } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            }
            
        }
        

        
        
        // LET'S START BY INITIALIZING OUR DIALOGS
	AppMessageDialogSingleton messageDialog = AppMessageDialogSingleton.getSingleton();
	messageDialog.init(primaryStage);
	AppYesNoCancelDialogSingleton yesNoDialog = AppYesNoCancelDialogSingleton.getSingleton();
	yesNoDialog.init(primaryStage);
	PropertiesManager props = PropertiesManager.getPropertiesManager();

        
        //Welcome Dialog
        BorderPane secondaryBorder = new BorderPane();
        VBox recentsBox = new VBox();
        HBox topArea = new HBox();
        HBox bottomArea = new HBox();
        Label topLabel = new Label("Choose Recent Work or Create New Map");
        topLabel.setStyle("-fx-font-size:40; -fx-font-weight:bold;");
        Label sideLabel = new Label("Recent Work");
        sideLabel.setStyle("-fx-font-size:30; -fx-font-weight:bold;");
        Button b1 = new Button("Boston");
        Button b2 = new Button("NYC");
        Button b3 = new Button("San Fran");
        Button b4 = new Button("Seoul");
        Button b5 = new Button("Tokyo");
        Button b6 = new Button("London");
        Button newMap = new Button("Create new map");
        Button closeWelcome = new Button("Close");
        
        File imgfile = new File(dir + "\\images\\M3Logo.png");
        Image iconImg = new Image(imgfile.toURI().toString());
        ImageView logoimg = new ImageView(iconImg);
        
        recentsBox.getChildren().add(sideLabel);
        recentsBox.getChildren().add(b1);
        recentsBox.getChildren().add(b2);
        recentsBox.getChildren().add(b3);
        recentsBox.getChildren().add(b4);
        recentsBox.getChildren().add(b5);
        recentsBox.getChildren().add(b6);
        recentsBox.setPadding(new Insets(75, 0, 0, 0));
        recentsBox.setStyle("-fx-spacing: 30;");

        
        topArea.getChildren().add(topLabel);
        bottomArea.getChildren().add(newMap);
        bottomArea.getChildren().add(closeWelcome);
        bottomArea.setPadding(new Insets(0, 0, 350, 950));
        bottomArea.setStyle("-fx-spacing: 30;");
        
        secondaryBorder.setLeft(recentsBox);
        secondaryBorder.setTop(topArea);
        secondaryBorder.setBottom(bottomArea);
        secondaryBorder.setCenter(logoimg);
        
        
        Scene welcomeScene = new Scene(secondaryBorder);
        
        primaryStage.setScene(welcomeScene);

	try {
	    // LOAD APP PROPERTIES, BOTH THE BASIC UI STUFF FOR THE FRAMEWORK
	    // AND THE CUSTOM UI STUFF FOR THE WORKSPACE
	    boolean success;
            if(change == false){
                success = loadProperties(APP_PROPERTIES_FILE_NAME);
            }
            else{
                success = loadProperties(APP_PROPERTIES_ALTERNATE_FILE_NAME);
            }
	    
	    if (success) {
                // GET THE TITLE FROM THE XML FILE
		String appTitle = props.getProperty(APP_TITLE);
                
                // BUILD THE BASIC APP GUI OBJECT FIRST
		gui = new AppGUI(primaryStage, appTitle, this);

                // THIS BUILDS ALL OF THE COMPONENTS, NOTE THAT
                // IT WOULD BE DEFINED IN AN APPLICATION-SPECIFIC
                // CHILD CLASS
		buildAppComponentsHook();
                
                // NOW OPEN UP THE WINDOW
                primaryStage.setScene(welcomeScene);
                primaryStage.show();
                
                newMap.setOnAction(e ->{
//                    e.consume();
                    primaryStage.setScene(gui.getPrimaryScene());
                    primaryStage.show();
                    gui.newMapFromWelcomeDialog();
                });
                
                closeWelcome.setOnAction(e ->{
//                    e.consume();
                    primaryStage.setScene(gui.getPrimaryScene());
                    primaryStage.show();
                });
                
	    } 
	}catch (Exception e) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(PROPERTIES_LOAD_ERROR_TITLE), props.getProperty(PROPERTIES_LOAD_ERROR_MESSAGE));
	}
    }
    
    /**
     * Loads this application's properties file, which has a number of settings
     * for initializing the user interface.
     *
     * @param propertiesFileName The XML file containing properties to be
     * loaded in order to initialize the UI.
     * 
     * @return true if the properties file was loaded successfully, false
     * otherwise.
     */
    public boolean loadProperties(String propertiesFileName) {
	    PropertiesManager props = PropertiesManager.getPropertiesManager();
	try {
	    // LOAD THE SETTINGS FOR STARTING THE APP
	    props.addProperty(PropertiesManager.DATA_PATH_PROPERTY, PATH_DATA);
	    props.loadProperties(propertiesFileName, PROPERTIES_SCHEMA_FILE_NAME);
	    return true;
	} catch (InvalidXMLFileFormatException ixmlffe) {
	    // SOMETHING WENT WRONG INITIALIZING THE XML FILE
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(PROPERTIES_LOAD_ERROR_TITLE), props.getProperty(PROPERTIES_LOAD_ERROR_MESSAGE));
	    return false;
	}
    }
}