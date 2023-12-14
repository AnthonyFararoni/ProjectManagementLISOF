package projectmanagementlisof.utils;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.sun.javafx.scene.SceneUtils;
import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
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
      
    public static FXMLLoader loadView(String pathFXML)throws IOException
    {
        URL url = projectmanagementlisof.ProjectManagementLISOF.class.getResource(pathFXML);
        return new FXMLLoader(url);
    }
      
      public static boolean validateIdDeveloper(String idDeveloper) 
      {
        String rightPattern = "^(zs|zS|ZS)\\d{8}$";
        Pattern pattern = Pattern.compile(rightPattern);
        Matcher matcher = pattern.matcher(idDeveloper);
        return matcher.matches() && !idDeveloper.trim().isEmpty();
    }
    
    public static boolean validateStringsFields(String fieldToCheck)
    {
        String rightPattern = "^[a-zA-ZáéíóúüñÁÉÍÓÚÜÑ]+$";
        Pattern pattern = Pattern.compile(rightPattern);
        Matcher matcher = pattern.matcher(fieldToCheck);
        return matcher.matches() && !fieldToCheck.trim().isEmpty();
    }
    
    public static void showSimpleAlert(String title, String message, Alert.AlertType type)
    {
        Alert simpleAlert = new Alert(type);
        simpleAlert.setTitle(title);
        simpleAlert.setContentText(message);
        simpleAlert.setHeaderText(null);
        simpleAlert.showAndWait();
    }
    
    public static boolean showConfirmationAlert(String title, String message)
    {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle(title);
        confirmationAlert.setContentText(message);
        confirmationAlert.setHeaderText(null);
        Optional<ButtonType> buttonClic = confirmationAlert.showAndWait();
        return (buttonClic.get() == ButtonType.OK);
    }
    
    public static String getFullName(String name, String lastName, String secondLastName)
    {
        String fullName = name + " " + lastName + " " + secondLastName;
        return fullName;
    }
    
    public static void loadFXML(String fxmlFile, AnchorPane anchorPane) {
        try {
            AnchorPane FXMLFile = FXMLLoader.load(Utilities.class.getResource(fxmlFile));
            anchorPane.getChildren().setAll(FXMLFile);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
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
    
    public static void closeWindow(Stage currentStage) {
        currentStage.close();
    }
}
