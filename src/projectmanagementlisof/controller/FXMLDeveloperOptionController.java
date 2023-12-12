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
<<<<<<< HEAD
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
=======
>>>>>>> Edmundo
import javafx.stage.Modality;
import javafx.stage.Stage;
import projectmanagementlisof.utils.Utilities;

public class FXMLDeveloperOptionController implements Initializable
{
<<<<<<< HEAD

    @FXML
    private TextField tfSearchDeveloper;
    
=======
    private Utilities utilities = new Utilities();
>>>>>>> Edmundo
      @Override public void initialize(URL url, ResourceBundle rb)
      {
            // TODO
      }

    @FXML
<<<<<<< HEAD
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
            FXMLLoader loader = Utilities.loadView("");
            Parent view = loader.load();
            Scene scene = new Scene(view);
            
            
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
=======
    private void btnShowDeveloperLog(ActionEvent event) {
            try{
            FXMLLoader loader = utilities.loadView("gui/FXMLDeveloperLog.fxml");
            Parent view = loader.load();
            Scene scene = new Scene(view);
            FXMLDeveloperLogController controller = loader.getController();
            Stage stage = new Stage();
                        
            stage.setScene(scene);
            stage.setTitle("Bitacora del desarrollador");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();            
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }
>>>>>>> Edmundo
}
