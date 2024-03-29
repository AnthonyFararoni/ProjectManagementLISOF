package projectmanagementlisof.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import projectmanagementlisof.utils.Utilities;

public class FXMLDeveloperLandingController implements Initializable
{
      private Utilities utilities = new Utilities();

      @FXML private AnchorPane apBackground;

      @Override public void initialize(URL url, ResourceBundle rb) {}

      @FXML private void btnLogOut(MouseEvent event)
      {
            Utilities.backToLogIn(event);
      }

      @FXML private void btnShowActivies(ActionEvent event)
      {
            Utilities.loadFXML(
                "/projectmanagementlisof/gui/FXMLDeveloperActivities.fxml", apBackground);
      }

      @FXML private void btnShowChanges(ActionEvent event)
      {
            Utilities.loadFXML(
                "/projectmanagementlisof/gui/FXMLDeveloperChangesOption.fxml", apBackground);
      }

      @FXML private void btnShowChangeRequests(ActionEvent event)
      {
            Utilities.loadFXML(
                "/projectmanagementlisof/gui/FXMLDeveloperChangeRequestsOption.fxml", apBackground);
      }

      @FXML private void btnShowDefects(ActionEvent event)
      {
            Utilities.loadFXML(
                "/projectmanagementlisof/gui/FXMLDeveloperDefectsOption.fxml", apBackground);
      }

      public AnchorPane getApBackground()
      {
            return apBackground;
      }
}
