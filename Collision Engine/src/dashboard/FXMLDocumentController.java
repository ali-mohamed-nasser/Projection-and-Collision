package dashboard;

// Start Librarys Import Area
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.event.ChangeListener;

import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Duration;
// End Librarys Import Area
import mainApp.MainEngine;

// Start Main Class Controller
public class FXMLDocumentController implements Initializable {
    
	// Physics Variables
	private static double VI;						// Initial Velocity
	private static double G;							// Gravity Constant
	private static double AX;						// Wind on Axis X
	private static double AY;						// Wind on Axis Y
	private static double AZ;						// Wind on Axis Z
	private static double AIRRESISTCOEFFICIENT;		// Air Resistance Drag Parameter (b/m)
	private static double FRICTIONCOEFFICIENT;		// Friction Coefficient
	private static double MASS; 						// Shape Mass
	private static double GROUNDMASS; 				// Ground Mass
	private static int SOUND;
	private static int LIGHT;
	private static int MATERIAL;
	
    // Transition Variables
    @FXML
    private static final FadeTransition FADEIN = new FadeTransition();         // Fade In Effect Transition
    private static final FadeTransition FADEOUT = new FadeTransition();        // Fade Out Effect Transition
    
    // Start Interface Variables
    @FXML
    private AnchorPane DASHTABONE, DASHTABONEPANE, DASHTABONEBACK,       // Dashboard > General Settings > Pane + Back
                       DASHTABTWO, DASHTABTWOPANE, DASHTABTWOBACK,       // Dashboard > Semulation > Pane + Back
                       DASHTABTHREE, DASHTABTHREEPANE, DASHTABTHREEBACK, // Dashboard > Wind & Air > Pane + Back
                       DASHTABFOUR, DASHTABFOURPANE, DASHTABFOURBACK;    // Dashboard > Shape Material > Pane + Back

    @FXML
    private ImageView  DASHTABONEICON, DASHTABONEBACKICON,               // Dashboard > General Settings > Icon
                       DASHTABTWOICON, DASHTABTWOBACKICON,               // Dashboard > Semulation > Icon
                       DASHTABTHREEICON, DASHTABTHREEBACKICON,           // Dashboard > Wind & Air > Icon
                       DASHTABFOURICON, DASHTABFOURBACKICON;             // Dashboard > Shape Material > Icon
    
    @FXML
    private Text       DASHTABONETITLE, DASHTABONETEXT,                  // Dashboard > General Settings > Title + Text
                       DASHTABTWOTITLE, DASHTABTWOTEXT,                  // Dashboard > Semulation > Title + Text
                       DASHTABTHREETITLE, DASHTABTHREETEXT,              // Dashboard > Wind & Air > Title + Text
                       DASHTABFOURTITLE, DASHTABFOURTEXT;                // Dashboard > Shape Material > Title + Text

    @FXML
    private AnchorPane HOME, ABOUT, PHYSICS, DASHBOARD, EXIT, BACKGROUND;
    
    @FXML
    private Button HOMEBUTTON, ABOUTBUTTON, PHYSICSBUTTON, DASHBOARDBUTTON, SEMULATIONBUTTON, EXITBUTTON, EXITYES, EXITNO, LEARNMORE, PAPER, PRESENTATION;
    
    @FXML
    private Pane HOMEUNDERLINE, ABOUTUNDERLINE, PHYSICSUNDERLINE, DASHBOARDUNDERLINE, SIMULATIONUNDERLINE;
    
    @FXML
    private ChoiceBox GRAVITY;
    
    @FXML
    private Slider AirSliderResistance, FrictionCoefficientSlider;
    
    ObservableList<String> SELECTGRAVITY = FXCollections.observableArrayList("Earth", "Moon", "Mars");
    // End Interface Variables
    
    // Start Saving Data Variables
    @FXML
    private int soundStatus, lightStatus, airResistanceStatus, shapeMaterial;
    
    @FXML
    private double Gravity, IntialVelocity, ShapeMass, GroundMass, AirResistance, FrictionCoefficient, WindMovementX, WindMovementY, WindMovementZ;
    
    @FXML
    private RadioButton soundOn, lightOn, airResistanceOn;
    
    @FXML
    private RadioButton Wood, Glass, Iron, Gold, Copper, Rubber, Steel;
    
    @FXML
    private TextField gravity, initialVelocity, shapeMass, groundMass, air, frictionCoefficient, windMovementX, windMovementY, windMovementZ;
    
    @FXML
    private Text GeneralDefaults, SimulationDefaults, WindAirDefaults, MaterialDefaults, ShapeVal, GetHelpOne, GetHelpTwo, GetHelpThree, GetHelpFour, DownloadPaperOne, DownloadPaperTwo;
    // End Saving Data Variables
    
    @FXML // Start Mouse Event Function
    private void MouseButtonAction(MouseEvent event) throws URISyntaxException, IOException {
          if(event.getTarget() == DASHTABONE || event.getTarget() == DASHTABONEICON || event.getTarget() == DASHTABONETITLE || event.getTarget() == DASHTABONETEXT ) {
              showTransition(DASHTABONEPANE, 700);
          } else if (event.getTarget() == DASHTABONEBACK || event.getTarget() == DASHTABONEBACKICON) {
              hideTransition(DASHTABONEPANE, 700);
          } else if(event.getTarget() == DASHTABTWO || event.getTarget() == DASHTABTWOICON || event.getTarget() == DASHTABTWOTITLE || event.getTarget() == DASHTABTWOTEXT ) {
              showTransition(DASHTABTWOPANE, 700);
          } else if (event.getTarget() == DASHTABTWOBACK || event.getTarget() == DASHTABTWOBACKICON) {
              hideTransition(DASHTABTWOPANE, 700);
          } else if(event.getTarget() == DASHTABTHREE || event.getTarget() == DASHTABTHREEICON || event.getTarget() == DASHTABTHREETITLE || event.getTarget() == DASHTABTHREETEXT ) {
              showTransition(DASHTABTHREEPANE, 700);
          } else if (event.getTarget() == DASHTABTHREEBACK || event.getTarget() == DASHTABTHREEBACKICON) {
              hideTransition(DASHTABTHREEPANE, 700);
          } else if(event.getTarget() == DASHTABFOUR || event.getTarget() == DASHTABFOURICON || event.getTarget() == DASHTABFOURTITLE || event.getTarget() == DASHTABFOURTEXT ) {
              showTransition(DASHTABFOURPANE, 700);
          } else if (event.getTarget() == DASHTABFOURBACK || event.getTarget() == DASHTABFOURBACKICON) {
              hideTransition(DASHTABFOURPANE, 700);
          }
          
          // Start Reset Default Settings 
          if(event.getTarget() == GeneralDefaults) {
              soundOn.setSelected(true);                                // Turn On The Sounds
              lightOn.setSelected(true);                                // Turn On The Lights
          } else if(event.getTarget() == SimulationDefaults) {
              GRAVITY.setValue("Earth");                                // Make The Plane Is Earth
              gravity.setText("9.8");                                   // Reset The Gravity To 9.8
              initialVelocity.setText("5.0");                           // Reset The Initial Velocity To 70.0
              shapeMass.setText("4.0");                                 // Reset The Shape Mass To 10.0
              groundMass.setText("3000.0");                             // Reset The Ground Mass To 3000.0
          } else if(event.getTarget() == WindAirDefaults) {
              airResistanceOn.setSelected(true);                        // Turn On The Air Resistance
              AirSliderResistance.setValue(0.1);                        // Reset The Air Resistance Slider Value
              FrictionCoefficientSlider.setValue(0.4);                  // Reset Thr Friction Coefficient Slider Value
              air.setText("0.91");                                      // Reset Thr Friction Resistance Value
              frictionCoefficient.setText("0.4");                       // Reset Thr Friction Coefficient Value
              windMovementX.setText("0.0");                             // Reset The Wind Movement X Value
              windMovementY.setText("0.0");                             // Reset The Wind Movement Y Value
              windMovementZ.setText("0.0");                             // Reset The Wind Movement Z Value
          } else if(event.getTarget() == MaterialDefaults) {
              Wood.setSelected(true);                                   // Reset The Shae Material To Wooden Ball
              ShapeVal.setText("0.4");                                  // Reset Shape Value To Default Value
          }
          // End Reset Default Settings 
          String dir = System.getProperty("user.dir");
          dir = dir.replace( " ", "%20");
          if(event.getTarget() == GetHelpOne || event.getTarget() == GetHelpTwo || event.getTarget() == GetHelpThree || event.getTarget() == GetHelpFour){
              try {
                  java.awt.Desktop.getDesktop().browse(new URL("file:///" + dir + "/Web%20Demo/index.html").toURI());
              } catch (URISyntaxException ex) {
                  Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
              }
          }
          
          if(event.getTarget() == DownloadPaperOne || event.getTarget() == DownloadPaperTwo){
              try {
                  java.awt.Desktop.getDesktop().browse(new URL("file:///" + dir + "/Paper.pdf").toURI());
              } catch (URISyntaxException ex) {
                  Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
              }
          }
    }
    // End Mouse Event Function
    
    @FXML // Start Button Events Function
    private void handleButtonAction(ActionEvent event) throws IOException {
        
        HOMEBUTTON.setOnAction(e -> { setActiveSctionButton(HOMEBUTTON, HOMEUNDERLINE); setActiveSction(HOME); });
        ABOUTBUTTON.setOnAction(e -> { setActiveSctionButton(ABOUTBUTTON, ABOUTUNDERLINE); setActiveSction(ABOUT); });
        LEARNMORE.setOnAction(e -> { setActiveSctionButton(ABOUTBUTTON, ABOUTUNDERLINE); setActiveSction(ABOUT); });
        PHYSICSBUTTON.setOnAction(e -> { setActiveSctionButton(PHYSICSBUTTON, PHYSICSUNDERLINE); setActiveSction(PHYSICS); });
        DASHBOARDBUTTON.setOnAction(e -> { setActiveSctionButton(DASHBOARDBUTTON, DASHBOARDUNDERLINE); setActiveSction(DASHBOARD); });
        EXITBUTTON.setOnAction( e -> { showTransition(EXIT, 300); }); // Show Exit Pane
        EXITYES.setOnAction( e -> { System.exit(0); });               // Exit If Click Yes
        EXITNO.setOnAction( e -> { hideTransition(EXIT, 300); });     // Hide Exit Pane If Click No
        
        GRAVITY.setOnAction(e -> {
            if(GRAVITY.getSelectionModel().getSelectedItem() == "Earth"){
                gravity.setText("9.8");
            } else if(GRAVITY.getSelectionModel().getSelectedItem() == "Moon"){
                gravity.setText("1.622");
            } else if(GRAVITY.getSelectionModel().getSelectedItem() == "Mars"){
                gravity.setText("3.711");
            } 
        });
        
        AirSliderResistance.setOnMouseDragged(e -> {
            double TheValue = AirSliderResistance.getValue();
            air.setText(Double.toString(TheValue));
        });
        
        FrictionCoefficientSlider.setOnMouseDragged(e -> {
            double TheValue = FrictionCoefficientSlider.getValue();
            frictionCoefficient.setText(Double.toString(TheValue));
        });
        
        Wood.setOnAction(e -> { ShapeVal.setText("0.4"); });
        Glass.setOnAction(e -> { ShapeVal.setText("0.64"); });
        Iron.setOnAction(e -> { ShapeVal.setText("0.7"); });
        Gold.setOnAction(e -> { ShapeVal.setText("0.76"); });
        Copper.setOnAction(e -> { ShapeVal.setText("0.78"); });
        Rubber.setOnAction(e -> { ShapeVal.setText("0.35"); });
        Steel.setOnAction(e -> { ShapeVal.setText("0.7"); });
    }
    // End Button Events Function
    
    // Start Fade In Animation Function
    private void showTransition(AnchorPane anchorPane, int millis) {
        FADEIN.setNode(anchorPane);                                    // The node that we want to show it
        FADEIN.setDuration(Duration.millis(millis));                   // Show the node in a half minute
        FADEIN.setFromValue(0.0);                                      // Start Showing from 0 opacity
        FADEIN.setToValue(1.0);                                        // End Showing in 1 opacity
        anchorPane.setVisible(true);                                   // Make the node visible after transition
        FADEIN.play();                                                 // Finish and play the function
    }
    // End Fade In Animation Function
    
    // Start Fade Out Animation Function
    private void hideTransition(AnchorPane anchorPane, int millis) {
        FADEOUT.setNode(anchorPane);                                   // The node that we want to show it
        FADEOUT.setDuration(Duration.millis(millis));                  // Show the node in a half minute
        FADEOUT.setFromValue(1.0);                                     // Start Showing from 0 opacity
        FADEOUT.setToValue(0.0);                                       // End Showing in 1 opacity
        anchorPane.setVisible(false);                                  // Make the node visible after transition
        FADEOUT.play();                                                // Finish and play the function
    }
    // End Fade Out Animation Function
    
    // Start Add Active Section Button Effect
    private void activeSection(Button button, Pane pane) {
        button.setStyle("-fx-text-fill: #0080CE;");                    // Make Button Inner Color Blue
        pane.setVisible(true);                                         // Set Underline Visible
    }
    // End Add Active Section Button Effect
    
    // Start Remove Active Section Button Effect
    private void noneActiveSection(Button button, Pane pane) {
        button.setStyle("-fx-text-fill: #999;");                       // Make Button Inner Color Gray
        pane.setVisible(false);                                        // Set Underline Unvisible
    }
    // End Remove Active Section Button Effect
    
    // Start Hide Show Effect
    private void setActiveSctionButton(Button button, Pane pane){
        noneActiveSection(HOMEBUTTON, HOMEUNDERLINE);                  // Hide Home Active Effect
        noneActiveSection(ABOUTBUTTON, ABOUTUNDERLINE);                // Hide About Us Active Effect
        noneActiveSection(PHYSICSBUTTON, PHYSICSUNDERLINE);            // Hide Physics Active Effect
        noneActiveSection(DASHBOARDBUTTON, DASHBOARDUNDERLINE);        // Hide User Dashboard Active Effect
        noneActiveSection(SEMULATIONBUTTON, SIMULATIONUNDERLINE);      // Hide Semulation Active Effect
        activeSection(button, pane);                                   // Show The Active Effect For Active Section
    }
    // End Hide Show Effect
    
    // Start Hide Show Sections
    private void setActiveSction(AnchorPane anchorPane){ 
        hideTransition(HOME, 700);                                     // Hide Home Section
        hideTransition(ABOUT, 700);                                    // Hide About Us Section
        hideTransition(PHYSICS, 700);                                  // Hide Physics Section
        hideTransition(DASHBOARD, 700);                                // Hide Dashboard Section
        showTransition(anchorPane, 700);                               // Show Anchor Pane
        
        // Show Balls Background Or Not
        if(anchorPane.equals(HOME)) {
            showTransition(BACKGROUND, 700);
        } else { 
            hideTransition(BACKGROUND, 700);
        }
    }
    // End Hide Show Sections
    
    @Override // Start Initialize Function
    public void initialize(URL url, ResourceBundle rb) {
        HOMEBUTTON.setOnAction(e -> { setActiveSctionButton(HOMEBUTTON, HOMEUNDERLINE); setActiveSction(HOME); });
        ABOUTBUTTON.setOnAction(e -> { setActiveSctionButton(ABOUTBUTTON, ABOUTUNDERLINE); setActiveSction(ABOUT); });
        LEARNMORE.setOnAction(e -> { setActiveSctionButton(ABOUTBUTTON, ABOUTUNDERLINE); setActiveSction(ABOUT); });
        PHYSICSBUTTON.setOnAction(e -> { setActiveSctionButton(PHYSICSBUTTON, PHYSICSUNDERLINE); setActiveSction(PHYSICS); });
        DASHBOARDBUTTON.setOnAction(e -> { setActiveSctionButton(DASHBOARDBUTTON, DASHBOARDUNDERLINE); setActiveSction(DASHBOARD); });
        EXITBUTTON.setOnAction( e -> { showTransition(EXIT, 300); }); // Show Exit Pane
        EXITYES.setOnAction( e -> { System.exit(0); });               // Exit If Click Yes
        EXITNO.setOnAction( e -> { hideTransition(EXIT, 300); });     // Hide Exit Pane If Click No
        
        GRAVITY.setValue("Earth");
        GRAVITY.setItems(SELECTGRAVITY);
        
        PAPER.setOnAction(e -> {
            System.out.print("BLA");
            String dir = System.getProperty("user.dir");
            dir = dir.replace( " ", "%20");
            try {
                java.awt.Desktop.getDesktop().browse(new URL("file:///" + dir + "/Paper.pdf").toURI());
            } catch (URISyntaxException | IOException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        PRESENTATION.setOnAction(e -> {
            System.out.print("BLA");
            String dir = System.getProperty("user.dir");
            dir = dir.replace( " ", "%20");
            try {
                java.awt.Desktop.getDesktop().browse(new URL("file:///" + dir + "/Presentation.pdf").toURI());
            } catch (URISyntaxException | IOException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        GRAVITY.setOnAction(e -> {
            if(GRAVITY.getSelectionModel().getSelectedItem() == "Earth"){
                gravity.setText("9.8");
            } else if(GRAVITY.getSelectionModel().getSelectedItem() == "Moon"){
                gravity.setText("1.622");
            } else if(GRAVITY.getSelectionModel().getSelectedItem() == "Mars"){
                gravity.setText("3.711");
            } 
        });
        
        AirSliderResistance.setOnMouseDragged(e -> {
            double TheValue = AirSliderResistance.getValue();
            air.setText(Double.toString(TheValue));
        });
        
        FrictionCoefficientSlider.setOnMouseDragged(e -> {
            double TheValue = FrictionCoefficientSlider.getValue();
            frictionCoefficient.setText(Double.toString(TheValue));
        });
        
        Wood.setOnAction(e -> { ShapeVal.setText("0.4"); });
        Glass.setOnAction(e -> { ShapeVal.setText("0.64"); });
        Iron.setOnAction(e -> { ShapeVal.setText("0.7"); });
        Gold.setOnAction(e -> { ShapeVal.setText("0.76"); });
        Copper.setOnAction(e -> { ShapeVal.setText("0.78"); });
        Rubber.setOnAction(e -> { ShapeVal.setText("0.35"); });
        Steel.setOnAction(e -> { ShapeVal.setText("0.7"); });
        
        Wood.selectedProperty().addListener( ( v, o, n ) -> {
        	if( n ) { shapeMaterial = 1; FrictionCoefficient = 0.4; frictionCoefficient.setText("0.4"); FrictionCoefficientSlider.setValue(0.4); }
        } );
        
        Glass.selectedProperty().addListener( ( v, o, n ) -> {
        	if( n ) { shapeMaterial = 2; FrictionCoefficient = 0.64; frictionCoefficient.setText("0.64"); FrictionCoefficientSlider.setValue(0.64); }
        } );
        
        Iron.selectedProperty().addListener( ( v, o, n ) -> {
        	if( n ) { shapeMaterial = 3; FrictionCoefficient = 0.7; frictionCoefficient.setText("0.7"); FrictionCoefficientSlider.setValue(0.7); }
        } );
        
        Gold.selectedProperty().addListener( ( v, o, n ) -> {
        	if( n ) { shapeMaterial = 4; FrictionCoefficient = 0.76; frictionCoefficient.setText("0.76"); FrictionCoefficientSlider.setValue(0.76); }
        } );
        
        Copper.selectedProperty().addListener( ( v, o, n ) -> {
        	if( n ) { shapeMaterial = 5; FrictionCoefficient = 0.78; frictionCoefficient.setText("0.78"); FrictionCoefficientSlider.setValue(0.78); }
        } );
        
        Rubber.selectedProperty().addListener( ( v, o, n ) -> {
        	if( n ) { shapeMaterial = 6; FrictionCoefficient = 0.35; frictionCoefficient.setText("0.35"); FrictionCoefficientSlider.setValue(0.35); }
        } );
        
        Steel.selectedProperty().addListener( ( v, o, n ) -> {
        	if( n ) { shapeMaterial = 7; FrictionCoefficient = 0.7; frictionCoefficient.setText("0.7"); FrictionCoefficientSlider.setValue(0.7); }
        } );
        
        SEMULATIONBUTTON.setOnAction( e -> {

        	Thread thread = new Thread( new Runnable() {
    			
    			@Override
    			public void run() {
    				MainEngine.main(null);
    			}
    		} );
        	thread.start();
        	
        });
        // Save The Date To The Settings File Every 300 Millisecond
        Timer timer = new Timer();
        timer.schedule(new SaveData(), 0, 300);
    }
    // End Initialize Function
    
    public static double getInitialVelocity() {
		return VI;
	}
	
	public static double getGravity() {
		return G;
	}
	
	public static double getAX() {
		return AX;
	}
	
	public static double getAY() {
		return AY;
	}
	
	public static double getAZ() {
		return AZ;
	}

	public static double getAirresistcoefficient() {
		return AIRRESISTCOEFFICIENT;
	}
	
	public static double getFrictioncoefficient() {
		return FRICTIONCOEFFICIENT;
	}

	public static double getMass() {
		return MASS;
	}
	
	public static double getGROUNDMASS() {
		return GROUNDMASS;
	}
	
	public static int getLight() {
		return LIGHT;
	}
	
	public static int getSound() {
		return SOUND;
	}
	
	public static int getMaterial() {
		return MATERIAL;
	}
    
    class SaveData extends TimerTask {
        @Override
        public void run() {
           saveData();
        }
        // Start Save Data Function
        public void saveData() {
            // At First We Will Get The Data From User In The Interface                                                
            // On/Off Radios Will Be 1/0                                                                               
            // Shape Material Will Start From 1 To 7                                                                   
            // Open The Text File To Write Date On It Then Write All The Following Date                                
            // After Finish Close This Open File                                                                       
            // This Function Will Called After Every Exit Of Options Section In The User Dashboard In The Interface :) 

            // Get General Settings Data
            if(soundOn.isSelected())soundStatus = 1; else soundStatus = 0;
            if(lightOn.isSelected())lightStatus = 1; else lightStatus = 0;

            // Get Simulation Data
            Gravity = Double.parseDouble(gravity.getText());
            IntialVelocity = Double.parseDouble(initialVelocity.getText());
            ShapeMass = Double.parseDouble(shapeMass.getText());
            GroundMass = Double.parseDouble(groundMass.getText());

            // Get Wind & Air Data
            if(airResistanceOn.isSelected()) airResistanceStatus = 1; else airResistanceStatus = 0;
            AirResistance = Double.parseDouble(air.getText());
            FrictionCoefficient = Double.parseDouble(frictionCoefficient.getText());
            WindMovementX = Double.parseDouble(windMovementX.getText());
            WindMovementY = Double.parseDouble(windMovementY.getText());
            WindMovementZ = Double.parseDouble(windMovementZ.getText());
            
            VI = IntialVelocity * 10;
        	G = -Gravity;
        	AX = WindMovementX;
        	AY = WindMovementY;
        	AZ = WindMovementZ;
        	AIRRESISTCOEFFICIENT = AirResistance;
        	FRICTIONCOEFFICIENT = FrictionCoefficient;
        	MASS = ShapeMass;
        	GROUNDMASS = GroundMass;
        	SOUND = soundStatus;
        	LIGHT = lightStatus;
        	MATERIAL = shapeMaterial;
            
        }
        // End Save Data Function
    }

}
// End Main Class Controller