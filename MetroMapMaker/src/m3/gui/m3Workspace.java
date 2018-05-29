package m3.gui;

import java.io.IOException;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import static m3.m3LanguageProperty.ELLIPSE_ICON;
import static m3.m3LanguageProperty.ELLIPSE_TOOLTIP;
import static m3.m3LanguageProperty.ADD_IMG_ICON;
import static m3.m3LanguageProperty.ADD_IMG_TOOLTIP;
import static m3.m3LanguageProperty.ADD_TXT_ICON;
import static m3.m3LanguageProperty.ADD_TXT_TOOLTIP;
import static m3.m3LanguageProperty.MOVE_TO_BACK_ICON;
import static m3.m3LanguageProperty.MOVE_TO_BACK_TOOLTIP;
import static m3.m3LanguageProperty.MOVE_TO_FRONT_ICON;
import static m3.m3LanguageProperty.MOVE_TO_FRONT_TOOLTIP;
import static m3.m3LanguageProperty.RECTANGLE_ICON;
import static m3.m3LanguageProperty.RECTANGLE_TOOLTIP;
import static m3.m3LanguageProperty.REMOVE_ICON;
import static m3.m3LanguageProperty.REMOVE_TOOLTIP;
import static m3.m3LanguageProperty.SELECTION_TOOL_ICON;
import static m3.m3LanguageProperty.PLUS_SIGN_ICON;
import static m3.m3LanguageProperty.MINUS_SIGN_ICON;
import static m3.m3LanguageProperty.LIST_SIGN_ICON;
import static m3.m3LanguageProperty.SELECTION_TOOL_TOOLTIP;
import static m3.m3LanguageProperty.SNAPSHOT_ICON;
import static m3.m3LanguageProperty.SNAPSHOT_TOOLTIP;
import m3.data.m3Data;
import static m3.data.m3Data.BLACK_HEX;
import static m3.data.m3Data.WHITE_HEX;
import m3.data.m3State;
import djf.ui.AppYesNoCancelDialogSingleton;
import djf.ui.AppMessageDialogSingleton;
import djf.ui.AppGUI;
import djf.AppTemplate;
import djf.components.AppDataComponent;
import djf.components.AppWorkspaceComponent;
import static djf.settings.AppPropertyType.LOAD_ERROR_MESSAGE;
import static djf.settings.AppPropertyType.LOAD_ERROR_TITLE;
import static djf.settings.AppStartupConstants.FILE_PROTOCOL;
import static djf.settings.AppStartupConstants.PATH_IMAGES;
import static m3.css.m3Style.*;
import m3.m3LanguageProperty;
import static m3.m3LanguageProperty.BOLD_ICON;
import static m3.m3LanguageProperty.BOLD_TOOLTIP;
import static m3.m3LanguageProperty.ITALIC_ICON;
import static m3.m3LanguageProperty.ITALIC_TOOLTIP;
import static m3.m3LanguageProperty.LISTTOOLTIP;
import static m3.m3LanguageProperty.MAX_TOOLTIP;
import static m3.m3LanguageProperty.MINUS_TOOLTIP;
import static m3.m3LanguageProperty.MIN_TOOLTIP;
import static m3.m3LanguageProperty.PLUS_TOOLTIP;
import static m3.m3LanguageProperty.ROTATE_ICON;
import static m3.m3LanguageProperty.ROUTE_ICON;
import static m3.m3LanguageProperty.ROW1ADDTOOLTIP;
import static m3.m3LanguageProperty.ROW1REMOVETOOLTIP;
import static m3.m3LanguageProperty.STRETCH_ICON;
import static m3.m3LanguageProperty.UNSTRETCH_ICON;
import static m3.m3LanguageProperty.ZOOMIN_ICON;
import static m3.m3LanguageProperty.ZOOMIN_TOOLTIP;
import static m3.m3LanguageProperty.ZOOMOUT_ICON;
import static m3.m3LanguageProperty.ZOOMOUT_TOOLTIP;
import java.io.File;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;

import javafx.scene.control.TextArea;
import javafx.scene.paint.Paint;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import org.apache.commons.io.FileUtils;
import properties_manager.PropertiesManager;

/**
 * This class serves as the workspace component for this application, providing
 * the user interface controls for editing work.
 *
 * @author Richard McKenna
 * @author ?
 * @version 1.0
 */
public class m3Workspace extends AppWorkspaceComponent {
    // HERE'S THE APP
    AppTemplate app;

    // IT KNOWS THE GUI IT IS PLACED INSIDE
    AppGUI gui;
    m3Data dataManager;

    // HAS ALL THE CONTROLS FOR EDITING
    VBox editToolbar;
    
    // FIRST ROW
    HBox row1Box;
    HBox row1secondBox;
    VBox row1Vbox;
    Label metroLines;
    ComboBox row1Combo;
    Button editLineButton;
    Button row1plus;
    Button row1minus;
    Button row1add;
    Button row1remove;
    Button row1list;
    Slider metrolineslider;
    
    //SECOND ROW
    HBox row2Box;
    HBox row2secondBox;
    VBox row2Vbox;
    Label metroStations;
    ComboBox row2Combo;
    ColorPicker stationColor;
    Button row2plus;
    Button row2minus;
    Button row2snap;
    Button row2movelabel;
    Button row2rotate;
    Slider metrostationslider;
    
    //THIRD ROW
    HBox row3Box;
    VBox row3VBox;
    ComboBox row3combo1;
    ComboBox row3combo2;
    Button row3route;
    
    //FOURTH ROW
    HBox row4Box;
    HBox row4secondBox;
    VBox row4VBox;
    Label decor;
    ColorPicker decorColor;
    Button row4imgBG;
    Button row4addimg;
    Button row4addlabel;
    Button row4removeelement;
    
    //FIFTH ROW - Edit Text Row
    HBox editTxtBox;
    HBox row5hbox1;
    VBox row5vbox;
    ComboBox fontFamily;
    ComboBox fontSize;
    Button boldToggle;
    Button italicsToggle;
    Button updateText;
    Label fontLabel;
    ColorPicker fontColorPicker;
    
    
    //Image Text Row
    HBox imgtxtBox;
    Button addimgButton;
    Button addtxtButton;
    FileChooser imageFileChooser;
    
    //SIXTH ROW
    VBox row6vbox;
    HBox row6box1;
    HBox row6box2;
    CheckBox row6checkbox;
    Button zoomin;
    Button zoomout;
    Button unstretch;
    Button stretch;
    Label naviLabel;
    
    
    // SECOND ROW
//    Button moveToBackButton;
//    Button moveToFrontButton;

    // THIRD ROW
//    VBox row3Box;
//    Label backgroundColorLabel;
//    Label backgroundColorLabelFR;
//    ColorPicker backgroundColorPicker;

    // FORTH ROW
//    VBox row4Box;
//    Label fillColorLabel;
//    Label fillColorLabelFR;
//    ColorPicker fillColorPicker;
    
    // FIFTH ROW
    VBox row5Box;
    Label outlineColorLabel;
    Label outlineColorLabelFR;
    ColorPicker outlineColorPicker;
        
    // SIXTH ROW
    VBox row6Box;
    Label outlineThicknessLabel;
    Label outlineThicknessLabelFR;
    Slider outlineThicknessSlider;
    
    // SEVENTH ROW
    HBox row7Box;
    Button snapshotButton;
    
    // THIS IS WHERE WE'LL RENDER OUR DRAWING, NOTE THAT WE
    // CALL THIS A CANVAS, BUT IT'S REALLY JUST A Pane
    Pane canvas;
    
    // HERE ARE THE CONTROLLERS
    CanvasController canvasController;
    MapEditController logoEditController;    

    // HERE ARE OUR DIALOGS
    AppMessageDialogSingleton messageDialog;
    AppYesNoCancelDialogSingleton yesNoCancelDialog;
    
    // FOR DISPLAYING DEBUG STUFF
    Text debugText;

    /**
     * Constructor for initializing the workspace, note that this constructor
     * will fully setup the workspace user interface for use.
     *
     * @param initApp The application this workspace is part of.
     *
     * @throws IOException Thrown should there be an error loading application
     * data for setting up the user interface.
     */
    public m3Workspace(AppTemplate initApp) {
	// KEEP THIS FOR LATER
	app = initApp;

	// KEEP THE GUI FOR LATER
	gui = app.getGUI();

        // LAYOUT THE APP
        initLayout();
        
        // HOOK UP THE CONTROLLERS
        initControllers();
        
        // AND INIT THE STYLE FOR THE WORKSPACE
        initStyle();    
    }
    
    /**
     * Note that this is for displaying text during development.
     */
    public void setDebugText(String text) {
	debugText.setText(text);
    }
    
    // ACCESSOR METHODS FOR COMPONENTS THAT EVENT HANDLERS
    // MAY NEED TO UPDATE OR ACCESS DATA FROM
    
//    public ColorPicker getFillColorPicker() {
//	return fillColorPicker;
//    }
    
    public ColorPicker getOutlineColorPicker() {
	return outlineColorPicker;
    }
    
    public ColorPicker getBackgroundColorPicker() {
	return decorColor;
    }
    
    public Slider getOutlineThicknessSlider() {
	return outlineThicknessSlider;
    }
    
    public ComboBox getFontFamCombo(){
        return fontFamily;
    }
    
    public ComboBox getFontSizeCombo(){
        return fontSize;
    }
    

    public Pane getCanvas() {
	return canvas;
    }

    public ComboBox getRow1Combo() {
        return row1Combo;
    }

    public void setRow1Combo(ComboBox row1Combo) {
        this.row1Combo = row1Combo;
    }
    
    public ComboBox getRow2Combo() {
        return row2Combo;
    }

    public void setRow2Combo(ComboBox row2Combo) {
        this.row2Combo = row2Combo;
    }
    
    public Slider getMetroSlider(){
        return metrolineslider;
    }

    public MapEditController getLogoEditController() {
        return logoEditController;
    }
    
    
    
    
        
    // HELPER SETUP METHOD
    private void initLayout() {
        String dir = System.getProperty("user.dir");
        File imgfile = new File(dir + "\\images\\goLogoLo.png");
        Image iconImg = new Image(imgfile.toURI().toString());
        app.getGUI().getWindow().getIcons().add(iconImg);
	// THIS WILL GO IN THE LEFT SIDE OF THE WORKSPACE
	editToolbar = new VBox();
	
	// ROW 1
        row1Vbox = new VBox();
	row1Box = new HBox();
        row1secondBox = new HBox();
        metroLines = new Label("Metro Lines");
        
        dataManager = (m3Data) app.getDataComponent();
        ObservableList<String> metrolinenameslist = dataManager.getShapeNames();
        row1Combo = new ComboBox();
        
        editLineButton = new Button("Edit Line");
        metrolineslider = new Slider();
        metrolineslider.setMin(1.0);
        metrolineslider.setMax(12.0);
        metrolineslider.setShowTickMarks(true);
        metrolineslider.setShowTickLabels(true);
        metrolineslider.setBlockIncrement(1.0);
        
        row1secondBox.getChildren().add(metroLines);
        row1secondBox.getChildren().add(row1Combo);
        row1secondBox.getChildren().add(editLineButton);
        
	row1plus = gui.initChildButton(row1Box, PLUS_SIGN_ICON.toString(), PLUS_TOOLTIP.toString(), false);
	row1minus = gui.initChildButton(row1Box, MINUS_SIGN_ICON.toString(), MINUS_TOOLTIP.toString(), false);
	row1add = gui.initChildButtonNoIcon(row1Box, "Add Station", ROW1ADDTOOLTIP.toString(), false);
	row1remove = gui.initChildButtonNoIcon(row1Box, "Remove Station", ROW1REMOVETOOLTIP.toString(), false);
        row1list = gui.initChildButton(row1Box, LIST_SIGN_ICON.toString(), LISTTOOLTIP.toString(), false);
        
        row1Vbox.getChildren().add(row1secondBox);
        row1Vbox.getChildren().add(row1Box);
        row1Vbox.getChildren().add(metrolineslider);
        
        
        // ROW 2
        row2Vbox = new VBox();
	row2Box = new HBox();
        row2secondBox = new HBox();
        metroStations = new Label("Metro Stations");
        row2Combo = new ComboBox();
        stationColor = new ColorPicker();
        metrostationslider = new Slider();
        
        row2secondBox.getChildren().add(metroStations);
        row2secondBox.getChildren().add(row2Combo);
        row2secondBox.getChildren().add(stationColor);
        
	row2plus = gui.initChildButton(row2Box, PLUS_SIGN_ICON.toString(), PLUS_TOOLTIP.toString(), false);
	row2minus = gui.initChildButton(row2Box, MINUS_SIGN_ICON.toString(), MINUS_TOOLTIP.toString(), false);
	row2snap = gui.initChildButtonNoIcon(row2Box, "Snap", RECTANGLE_TOOLTIP.toString(), false);
	row2movelabel = gui.initChildButtonNoIcon(row2Box, "Move Label", ELLIPSE_TOOLTIP.toString(), false);
        row2rotate = gui.initChildButton(row2Box, ROTATE_ICON.toString(), REMOVE_TOOLTIP.toString(), false);
        
        row2Vbox.getChildren().add(row2secondBox);
        row2Vbox.getChildren().add(row2Box);
        row2Vbox.getChildren().add(metrostationslider);
        
        // ROW 3
        row3Box = new HBox();
        row3VBox = new VBox();
        row3combo1 = new ComboBox(row2Combo.getItems());
        row3combo2 = new ComboBox(row2Combo.getItems());
        
        
        row3VBox.getChildren().add(row3combo1);
        row3VBox.getChildren().add(row3combo2);
        row3Box.getChildren().add(row3VBox);
        row3route = gui.initChildButton(row3Box, ROUTE_ICON.toString(), REMOVE_TOOLTIP.toString(), false);
       
        // ROW 4
        row4VBox = new VBox();
        row4Box = new HBox();
        row4secondBox = new HBox();
        decor = new Label("Decor");
        decorColor = new ColorPicker();
        
        row4Box.getChildren().add(decor);
        row4Box.getChildren().add(decorColor);
        
        row4imgBG = gui.initChildButtonNoIcon(row4secondBox, "Set Image Background", ELLIPSE_TOOLTIP.toString(), false);
        row4addimg = gui.initChildButtonNoIcon(row4secondBox, "Add Image", ELLIPSE_TOOLTIP.toString(), false);
        row4addlabel = gui.initChildButtonNoIcon(row4secondBox, "Add Label", ELLIPSE_TOOLTIP.toString(), false);
        row4removeelement = gui.initChildButtonNoIcon(row4secondBox, "Remove Element", ELLIPSE_TOOLTIP.toString(), false);
        
        row4VBox.getChildren().add(row4Box);
        row4VBox.getChildren().add(row4secondBox);
        
        // ROW 5/Edit Text Row
        row5hbox1 = new HBox();
        editTxtBox = new HBox();
        row5vbox = new VBox();
        fontLabel = new Label("Font");
        fontColorPicker = new ColorPicker();
        fontSize = new ComboBox();
        ObservableList<String> fontFams = FXCollections.observableArrayList(javafx.scene.text.Font.getFamilies());
        fontFamily = new ComboBox(fontFams);
        fontSize.getItems().addAll(
                "10", "12", "14", "16", "18", "20", "22", "24", "26", "28",
                "30", "32", "34", "36", "38", "40", "42", "44", "46", "48"
        );
        
        row5hbox1.getChildren().add(fontLabel);
        row5hbox1.getChildren().add(fontColorPicker);
 
        editTxtBox.getChildren().add(fontFamily);
        editTxtBox.getChildren().add(fontSize);
        boldToggle = gui.initChildButton(editTxtBox, BOLD_ICON.toString(), BOLD_TOOLTIP.toString(), workspaceActivated);
        italicsToggle = gui.initChildButton(editTxtBox, ITALIC_ICON.toString(), ITALIC_TOOLTIP.toString(), workspaceActivated);
        updateText = new Button("Update");
        editTxtBox.getChildren().add(updateText);
        
        row5vbox.getChildren().add(row5hbox1);
        row5vbox.getChildren().add(editTxtBox);
        
        //Add Image and Text Row
        imgtxtBox = new HBox();
        addimgButton = gui.initChildButton(imgtxtBox, ADD_IMG_ICON.toString() , ADD_IMG_TOOLTIP.toString(), workspaceActivated);
        addtxtButton = gui.initChildButton(imgtxtBox, ADD_TXT_ICON.toString() , ADD_TXT_TOOLTIP.toString(), workspaceActivated);
        imageFileChooser = new FileChooser();
        imageFileChooser.getExtensionFilters().addAll(
                new ExtensionFilter("Image Files", "*.png", "*.jpeg")
        );
        
        
        //ROW 6
        row6vbox = new VBox();
        row6box1 = new HBox();
        row6box2 = new HBox();
        
        naviLabel = new Label("Navigation");
        row6checkbox = new CheckBox("Show Grid");
        
        row6box1.getChildren().add(naviLabel);
        row6box1.getChildren().add(row6checkbox);
        
        zoomin = gui.initChildButton(row6box2, ZOOMIN_ICON.toString(), ZOOMIN_TOOLTIP.toString(), false);
        zoomout = gui.initChildButton(row6box2, ZOOMOUT_ICON.toString(), ZOOMOUT_TOOLTIP.toString(), false);
        unstretch = gui.initChildButton(row6box2, UNSTRETCH_ICON.toString(), MIN_TOOLTIP.toString(), false);
        stretch = gui.initChildButton(row6box2, STRETCH_ICON.toString(), MAX_TOOLTIP.toString(), false);
        
        row6vbox.getChildren().add(row6box1);
        row6vbox.getChildren().add(row6box2);
        
        
        
        
        
        
        

	// ROW 2
//	row2Box = new HBox();
//	moveToBackButton = gui.initChildButton(row2Box, MOVE_TO_BACK_ICON.toString(), MOVE_TO_BACK_TOOLTIP.toString(), true);
//	moveToFrontButton = gui.initChildButton(row2Box, MOVE_TO_FRONT_ICON.toString(), MOVE_TO_FRONT_TOOLTIP.toString(), true);

	// ROW 3
//	row3Box = new VBox();
//        if(app.getLangChange() == true){
//            backgroundColorLabel = new Label("Coleur D’Arrière-Plan");
//        }
//        else{
//            backgroundColorLabel = new Label("Background Color");
//        }
//	backgroundColorPicker = new ColorPicker(Color.valueOf(WHITE_HEX));
//	row3Box.getChildren().add(backgroundColorLabel);
//	row3Box.getChildren().add(backgroundColorPicker);

	// ROW 4
//	row4Box = new VBox();
//        if(app.getLangChange() == true){
//            fillColorLabel = new Label("Couleur de Remplissage");
//        }
//        else{
//            fillColorLabel = new Label("Fill Color");
//        }
//	fillColorPicker = new ColorPicker(Color.valueOf(WHITE_HEX));
//	row4Box.getChildren().add(fillColorLabel);
//	row4Box.getChildren().add(fillColorPicker);
	
	// ROW 5
	row5Box = new VBox();
        if(app.getLangChange() == true){
            outlineColorLabel = new Label("Couleur de Contour");
        }
        else{
            outlineColorLabel = new Label("Outline Color");
        }
	outlineColorPicker = new ColorPicker(Color.valueOf(BLACK_HEX));
	row5Box.getChildren().add(outlineColorLabel);
	row5Box.getChildren().add(outlineColorPicker);
	
	// ROW 6
	row6Box = new VBox();
        if(app.getLangChange() == true){
            outlineThicknessLabel = new Label("Épaisseur de Contour");
        }
        else{
            outlineThicknessLabel = new Label("Outline Thickness");
        }
	outlineThicknessSlider = new Slider(0, 10, 1);
	row6Box.getChildren().add(outlineThicknessLabel);
	row6Box.getChildren().add(outlineThicknessSlider);
	
	// ROW 7
	row7Box = new HBox();
	snapshotButton = gui.initChildButton(row7Box, SNAPSHOT_ICON.toString(), SNAPSHOT_TOOLTIP.toString(), false);
	
	// NOW ORGANIZE THE EDIT TOOLBAR
	editToolbar.getChildren().add(row1Vbox);
        editToolbar.getChildren().add(row2Vbox);
        editToolbar.getChildren().add(row3Box);
        editToolbar.getChildren().add(row4VBox);
        editToolbar.getChildren().add(row5vbox);
//	editToolbar.getChildren().add(row3Box);
//	editToolbar.getChildren().add(row4Box);
//	editToolbar.getChildren().add(row5Box);
	editToolbar.getChildren().add(row6vbox);
//	editToolbar.getChildren().add(row7Box);

        ScrollPane sidePane = new ScrollPane();
        sidePane.setContent(editToolbar);
	
	// WE'LL RENDER OUR STUFF HERE IN THE CANVAS
	canvas = new Pane();
	debugText = new Text();
	canvas.getChildren().add(debugText);
	debugText.setX(100);
	debugText.setY(100);
	
	// AND MAKE SURE THE DATA MANAGER IS IN SYNCH WITH THE PANE
	m3Data data = (m3Data)app.getDataComponent();
	data.setShapes(canvas.getChildren());

	// AND NOW SETUP THE WORKSPACE
	workspace = new BorderPane();
	((BorderPane)workspace).setLeft(sidePane);
	((BorderPane)workspace).setCenter(canvas);
    }
    
    // HELPER SETUP METHOD
    private void initControllers() {
	// MAKE THE EDIT CONTROLLER
	logoEditController = new MapEditController(app);
	
	// NOW CONNECT THE BUTTONS TO THEIR HANDLERS
//	selectionToolButton.setOnAction(e->{
//              logoEditController.processSelectSelectionTool();
//	});
        row1plus.setOnAction(e->{
	    logoEditController.processSelectMetroLineToDraw();
	});
        
        row2plus.setOnAction(e->{
	    logoEditController.processAddStation();
	});
        
        row1add.setOnAction(e->{
            logoEditController.addStationtoLine();
        });
        
        row1remove.setOnAction(e->{
            logoEditController.removeStationfromLine();
        });
        
        editLineButton.setOnAction(e->{
            logoEditController.processEditMetroLine();
        });
        
        row2minus.setOnAction(e->{
	    logoEditController.processRemoveSelectedStation();
	});
        
        row1minus.setOnAction(e->{
	    logoEditController.removeSelectedMetroLine();
	});

	row4removeelement.setOnAction(e->{
	    logoEditController.processRemoveSelectedShape();
	});
//	rectButton.setOnAction(e->{
//	    logoEditController.processSelectRectangleToDraw();
//	});
//	ellipseButton.setOnAction(e->{
//	    logoEditController.processSelectEllipseToDraw();
//	});
        row4addimg.setOnAction(e->{
	    logoEditController.processOpenImageFile();
	});
        row4addlabel.setOnAction(e->{
	    logoEditController.processOpenTextNode();
	});
        
        row4imgBG.setOnAction(e->{
            logoEditController.setBackgroundImage();
        });
	
//	moveToBackButton.setOnAction(e->{
//	    logoEditController.processMoveSelectedShapeToBack();
//	});
//	moveToFrontButton.setOnAction(e->{
//	    logoEditController.processMoveSelectedShapeToFront();
//	});

	decorColor.setOnAction(e->{
	    logoEditController.processSelectBackgroundColor();
	});
        
        zoomin.setOnAction(e->{
            logoEditController.mapZoomIn();
        });
        
        zoomout.setOnAction(e->{
            logoEditController.mapZoomOut();
        });
//	fillColorPicker.setOnAction(e->{ 
//	    logoEditController.processSelectFillColor();
//	});
//	outlineColorPicker.setOnAction(e->{
//	    logoEditController.processSelectOutlineColor();
//	});
//	outlineThicknessSlider.valueProperty().addListener(e-> {
//	    logoEditController.processSelectOutlineThickness();
//	});

        workspace.setOnKeyPressed(e->{
            if(e.getCode() == KeyCode.MINUS){
                logoEditController.mapZoomOut();
            }
            if(e.getCode() == KeyCode.EQUALS){
                logoEditController.mapZoomIn();
            }
        });
        
        metrolineslider.setOnMouseReleased(e->{
            logoEditController.adjustLineThickness();
        });

        Button exportBt = gui.getExportButton();
	exportBt.setOnAction(e->{
	    logoEditController.processSnapshot();
            
            PropertiesManager props = PropertiesManager.getPropertiesManager();
        try {
	    // OTHERWISE WE NEED TO PROMPT THE USER
		// PROMPT THE USER FOR A FILE NAME
                DirectoryChooser dc = new DirectoryChooser();
                File f = dc.showDialog(app.getGUI().getWindow());
                String dir = System.getProperty("user.dir");
                File myFile = new File(dir + "\\Map.png");
                File nextFile = new File(dir + "\\work\\SampleMap.json");
                
                if(f == null){
                    f.mkdirs();
                }
                FileUtils.copyFileToDirectory(myFile, f);
                FileUtils.copyFileToDirectory(nextFile, f);
                
                
//		FileChooser fc = new FileChooser();
//		fc.setInitialDirectory(new File(PATH_WORK));
//		fc.setTitle(props.getProperty(SAVE_WORK_TITLE));
//		fc.getExtensionFilters().addAll(
//		new ExtensionFilter(props.getProperty(WORK_FILE_EXT_DESC), props.getProperty(WORK_FILE_EXT)));
//
//		File selectedFile = fc.showSaveDialog(app.getGUI().getWindow());
//		if (selectedFile != null) {
//		    saveWork(selectedFile);
//		}
        } catch (IOException ioe) {
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(LOAD_ERROR_TITLE), props.getProperty(LOAD_ERROR_MESSAGE));
        }
	});
        
        Button copyBt = gui.getCopyButton();
        Button cutBt = gui.getCutButton();
        Button pasteBt = gui.getPasteButton();
	
	// MAKE THE CANVAS CONTROLLER	
	canvasController = new CanvasController(app);
        canvas.setOnMouseClicked(e->{
            if(e.getClickCount() == 2){
                double x = e.getX();
                double y = e.getY();
                canvasController.processCanvasMouseDoubleClicked(x, y);
            }
            else{
                double x = e.getX();
                double y = e.getY();
                canvasController.processCanvasMouseTextEdit(x, y);
                updateText.setOnAction(f->{
                    canvasController.processCanvasTextUpdate(x, y);
                });
                boldToggle.setOnAction(g->{
                    canvasController.processCanvasTextBold(x, y);
                });
                italicsToggle.setOnAction(h->{
                    canvasController.processCanvasTextItalic(x, y);
                });
                copyBt.setOnAction(i->{
                    canvasController.processCanvasNodeCopy(x, y);
                });
                pasteBt.setOnAction(j->{
                    canvasController.processCanvasNodePaste();
                });
                cutBt.setOnAction(k->{
                    canvasController.processCanvasNodeCut(x, y);
                });
            }
            
        });
	canvas.setOnMousePressed(e->{
	    canvasController.processCanvasMousePress((int)e.getX(), (int)e.getY());
	});
	canvas.setOnMouseReleased(e->{
	    canvasController.processCanvasMouseRelease((int)e.getX(), (int)e.getY());
	});
	canvas.setOnMouseDragged(e->{
	    canvasController.processCanvasMouseDragged((int)e.getX(), (int)e.getY());
	});
    }

    // HELPER METHOD
    public void loadSelectedShapeSettings(Shape shape) {
	if (shape != null) {
            if(shape.getFill() == null){
                
            }
            else if(shape.getFill().getClass().equals("Paint")){
                Color fillColor = (Color)shape.getFill();
//                fillColorPicker.setValue(fillColor);
            }
            else{
                
            }
	    
	    Color strokeColor = (Color)shape.getStroke();
	    double lineThickness = shape.getStrokeWidth();
	    
	    outlineColorPicker.setValue(strokeColor);
	    outlineThicknessSlider.setValue(lineThickness);	    
	}
    }

    /**
     * This function specifies the CSS style classes for all the UI components
     * known at the time the workspace is initially constructed. Note that the
     * tag editor controls are added and removed dynamicaly as the application
     * runs so they will have their style setup separately.
     */
    public void initStyle() {
	// NOTE THAT EACH CLASS SHOULD CORRESPOND TO
	// A STYLE CLASS SPECIFIED IN THIS APPLICATION'S
	// CSS FILE
	canvas.getStyleClass().add(CLASS_RENDER_CANVAS);
	
	// COLOR PICKER STYLE
//	fillColorPicker.getStyleClass().add(CLASS_BUTTON);
//	outlineColorPicker.getStyleClass().add(CLASS_BUTTON);
//	backgroundColorPicker.getStyleClass().add(CLASS_BUTTON);
	
	editToolbar.getStyleClass().add(CLASS_EDIT_TOOLBAR);
        
	row1Vbox.getStyleClass().add(CLASS_EDIT_TOOLBAR_ROW);
        row1Box.getStyleClass().add(CLASS_EDIT_TOOLBAR_ROW);
        row1secondBox.getStyleClass().add(CLASS_EDIT_TOOLBAR_ROW);
        metroLines.getStyleClass().add(CLASS_COLOR_CHOOSER_CONTROL);
        
        row2Vbox.getStyleClass().add(CLASS_EDIT_TOOLBAR_ROW);
        row2Box.getStyleClass().add(CLASS_EDIT_TOOLBAR_ROW);
        row2secondBox.getStyleClass().add(CLASS_EDIT_TOOLBAR_ROW);
        metroStations.getStyleClass().add(CLASS_COLOR_CHOOSER_CONTROL);
        
        imgtxtBox.getStyleClass().add(CLASS_EDIT_TOOLBAR_ROW);
        row5hbox1.getStyleClass().add(CLASS_EDIT_TOOLBAR_ROW);
        editTxtBox.getStyleClass().add(CLASS_EDIT_TOOLBAR_ROW);
        row5vbox.getStyleClass().add(CLASS_EDIT_TOOLBAR_ROW);
        fontLabel.getStyleClass().add(CLASS_COLOR_CHOOSER_CONTROL);
        naviLabel.getStyleClass().add(CLASS_COLOR_CHOOSER_CONTROL);
	row3Box.getStyleClass().add(CLASS_EDIT_TOOLBAR_ROW);
        row3VBox.getStyleClass().add(CLASS_EDIT_TOOLBAR_ROW);
//	backgroundColorLabel.getStyleClass().add(CLASS_COLOR_CHOOSER_CONTROL);
	
	row4Box.getStyleClass().add(CLASS_EDIT_TOOLBAR_ROW);
        row4secondBox.getStyleClass().add(CLASS_EDIT_TOOLBAR_ROW);
        row4VBox.getStyleClass().add(CLASS_EDIT_TOOLBAR_ROW);
        decor.getStyleClass().add(CLASS_COLOR_CHOOSER_CONTROL);
        
        row6vbox.getStyleClass().add(CLASS_EDIT_TOOLBAR_ROW);
        row6box1.getStyleClass().add(CLASS_EDIT_TOOLBAR_ROW);
        row6box2.getStyleClass().add(CLASS_EDIT_TOOLBAR_ROW);
        
//	fillColorLabel.getStyleClass().add(CLASS_COLOR_CHOOSER_CONTROL);
	row5Box.getStyleClass().add(CLASS_EDIT_TOOLBAR_ROW);
	outlineColorLabel.getStyleClass().add(CLASS_COLOR_CHOOSER_CONTROL);
	row6Box.getStyleClass().add(CLASS_EDIT_TOOLBAR_ROW);
	outlineThicknessLabel.getStyleClass().add(CLASS_COLOR_CHOOSER_CONTROL);
	row7Box.getStyleClass().add(CLASS_EDIT_TOOLBAR_ROW);
    }

    /**
     * This function reloads all the controls for editing logos
     * the workspace.
     */
    @Override
    public void reloadWorkspace(AppDataComponent data) {
	m3Data dataManager = (m3Data)data;
        
//	if (dataManager.isInState(golState.STARTING_RECTANGLE)) {
//	    selectionToolButton.setDisable(false);
//	    removeButton.setDisable(true);
//	    rectButton.setDisable(true);
//	    ellipseButton.setDisable(false);
//	}
//	else if (dataManager.isInState(golState.STARTING_ELLIPSE)) {
//	    selectionToolButton.setDisable(false);
//	    removeButton.setDisable(true);
//	    rectButton.setDisable(false);
//	    ellipseButton.setDisable(true);
//	}
//	else if (dataManager.isInState(golState.SELECTING_SHAPE) 
//		|| dataManager.isInState(golState.DRAGGING_SHAPE)
//		|| dataManager.isInState(golState.DRAGGING_NOTHING)) {
//	    boolean shapeIsNotSelected = dataManager.getSelectedShape() == null;
//	    selectionToolButton.setDisable(true);
//	    removeButton.setDisable(shapeIsNotSelected);
//	    rectButton.setDisable(false);
//	    ellipseButton.setDisable(false);
//	    moveToFrontButton.setDisable(shapeIsNotSelected);
//	    moveToBackButton.setDisable(shapeIsNotSelected);
//	}
//	
//	removeButton.setDisable(dataManager.getSelectedShape() == null);
	decorColor.setValue(dataManager.getBackgroundColor());
    }
    
    @Override
    public void resetWorkspace() {
        // WE ARE NOT USING THIS, THOUGH YOU MAY IF YOU LIKE
    }

}