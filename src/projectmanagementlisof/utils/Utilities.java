package projectmanagementlisof.utils;

import com.sun.javafx.scene.SceneUtils;
import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Utilities
{
    public static void centerStage(Stage stage)
      {
            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
            stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
      }
      
    public static void loadFXML(String fxmlFile, AnchorPane anchorPane) {
        try {
            AnchorPane FXMLFile = FXMLLoader.load(Utilities.class.getResource(fxmlFile));
            anchorPane.getChildren().setAll(FXMLFile);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public static void showSimpleAlert(String title, String message, Alert.AlertType type){
        Alert alertaSimple = new Alert(type);
        alertaSimple.setTitle(title);
        alertaSimple.setContentText(message);
        alertaSimple.setHeaderText(null);
        alertaSimple.showAndWait();
    }
    
    public static FXMLLoader loadView(String PathFXML) throws IOException{
        URL url = projectmanagementlisof.ProjectManagementLISOF.class.getResource(PathFXML);
        return new FXMLLoader(url);
    }
    
    public static void closeView(Stage currentStage, String fxmlPath) {
        try {
            Parent vista = FXMLLoader.load(Utilities.class.getResource(fxmlPath));
            Scene escena = new Scene(vista);
            currentStage.setScene(escena);
            currentStage.showAndWait();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public static void cerrarVentana(Stage currentStage) {
        currentStage.close();
    }

    
}
