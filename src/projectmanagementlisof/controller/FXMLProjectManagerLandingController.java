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
import projectmanagementlisof.utils.SelectedProjectSingleton;
import projectmanagementlisof.utils.Utilities;

public class FXMLProjectManagerLandingController implements Initializable
{
      @FXML private AnchorPane apBackground;

      @Override public void initialize(URL url, ResourceBundle rb)
      {
            SelectedProjectSingleton instance = SelectedProjectSingleton.getInstance();
            int idProject = instance.getIdSelectedProject();
            System.out.println(idProject);
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
            loadFXML("FXMLDevelopersOption");
      }

      @FXML private void btnShowActivities(ActionEvent event)
      {
            loadFXML("FXMLActivitiesOption");
      }

      @FXML private void btnShowChanges(ActionEvent event)
      {
            loadFXML("FXMLChangesOption");
      }

      @FXML private void btnShowChangeRequests(ActionEvent event)
      {
            loadFXML("FXMLChangeRequestsOption");
      }

      @FXML private void btnShowDefects(ActionEvent event)
      {
            loadFXML("FXMLDefectsOption");
      }

      private void loadFXML(String fxmlName)
      {
            try
            {
                  FXMLLoader loader = new FXMLLoader(
                      getClass().getResource("/projectmanagementlisof/gui/" + fxmlName + ".fxml"));
                  Node newNode = loader.load();
                  apBackground.getChildren().clear();
                  apBackground.getChildren().add(newNode);
            }
            catch (IOException ex)
            {
                  ex.printStackTrace();
            }
      }

      public AnchorPane getApBackground()
      {
            return apBackground;
      }
}
