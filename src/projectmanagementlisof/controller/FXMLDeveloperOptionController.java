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
import javafx.stage.Modality;
import javafx.stage.Stage;
import projectmanagementlisof.utils.Utilities;

public class FXMLDeveloperOptionController implements Initializable
{
    private Utilities utilities = new Utilities();
      @Override public void initialize(URL url, ResourceBundle rb)
      {
            // TODO
      }

    @FXML
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
}
