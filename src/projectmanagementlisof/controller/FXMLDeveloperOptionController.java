package projectmanagementlisof.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import projectmanagementlisof.utils.Utilities;

public class FXMLDeveloperOptionController implements Initializable
{

    @FXML
    private TextField tfSearchDeveloper;
    
      @Override public void initialize(URL url, ResourceBundle rb)
      {
            // TODO
      }

    @FXML
    private void btnShowDeveloperDetails(ActionEvent event) 
    {
    }

    @FXML
    private void btnShowAssignedActivities(ActionEvent event) 
    {
        goAssignedActivities();
    }
    
    private void goAssignedActivities(/*String idDeveloper, String developerName*/)
      {
        try {
            FXMLLoader loader = Utilities.loadView("gui/FXMLViewAssignedActivities.fxml");
            Parent view = loader.load();
            Scene scene = new Scene(view);
            FXMLViewAssignedActivitiesController controller = loader.getController();
            
            Stage currentStage = new Stage();
            currentStage.setScene(scene);
            currentStage.setTitle("Actividades asignadas");
            currentStage.initModality(Modality.APPLICATION_MODAL);
            currentStage.showAndWait();
            Utilities.centerStage(currentStage);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void btnSearchDeveloper(MouseEvent event) {
    }
}
