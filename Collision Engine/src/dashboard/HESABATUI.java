package dashboard;

// Start Librarys Import Area
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
// End Librarys Import Area

// Start HESABATUI Main Application Class
public class HESABATUI extends Application {
    
    @Override // Start Start Function
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml")); // Load XML File
        
        Scene scene = new Scene(root);            // Create New Scene
        stage.initStyle(StageStyle.TRANSPARENT);  // Hide Header Bar
        stage.setScene(scene);                    // Set The Main Stage Scene
        stage.setMaximized(true);                 // Make The Application Full Screen
        stage.show();                             // Show Main Stage
    }
    // End Start Function
    
    // Start Main Function 
    public static void main(String[] args) {
        launch(args);
    }
    // End Main Function 
    
}
// End HESABATUI Main Application Class
