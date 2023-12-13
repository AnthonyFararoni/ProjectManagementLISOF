package projectmanagementlisof.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import projectmanagementlisof.utils.Utilities;

public class FXMLProjectManagerLandingController implements Initializable
{
      @FXML private AnchorPane apBackground;

      @Override public void initialize(URL url, ResourceBundle rb)
      {
            // TODO
      }

      @FXML private void btnLogOut(MouseEvent event)
      {
            try
            {
                Stage currentStage = (Stage) apBackground.getScene().getWindow();
                Parent newView =
                    FXMLLoader.load(FXMLProjectManagerLandingController.class.getResource(
                        "/projectmanagementlisof/gui/FXMLLogIn.fxml"));
                Scene scene = new Scene(newView);
                currentStage.setScene(scene);
                currentStage.setTitle("Iniciar sesión");
                currentStage.show();
                Utilities.centerStage(currentStage);
            }
            catch (IOException ex)
            {
                  ex.printStackTrace();
            }
      }

      @FXML private void btnShowDevelopers(ActionEvent event)
      {
            loadFXML("/projectmanagementlisof/gui/FXMLDevelopersOption.fxml");
      }

      @FXML private void btnShowActivities(ActionEvent event)
      {
            loadFXML("/projectmanagementlisof/gui/FXMLActivitiesOption.fxml");
      }

      @FXML private void btnShowChanges(ActionEvent event)
      {
            loadFXML("/projectmanagementlisof/gui/FXMLChangesOption.fxml");
      }

      @FXML private void btnShowChangeRequests(ActionEvent event)
      {
            loadFXML("/projectmanagementlisof/gui/FXMLChangeRequestsOption.fxml");
      }

      @FXML private void btnShowDefects(ActionEvent event)
      {
            loadFXML("/projectmanagementlisof/gui/FXMLDefectsOption.fxml");
      }

      private void loadFXML(String fxmlFile)
      {
            try
            {
                
                FXMLLoader loader = new FXMLLoader(getClass().getResource(
                        "/projectmanagementlisof/gui/" + fxmlName +".fxml"));
                Node newNode = loader.load();
                apBackground.getChildren().clear();
                apBackground.getChildren().add(newNode);
                
            }
            catch (IOException ex)
            {
                  ex.printStackTrace();
            }
      }
}
