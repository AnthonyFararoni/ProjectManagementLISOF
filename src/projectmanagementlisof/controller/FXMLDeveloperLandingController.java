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

      @Override public void initialize(URL url, ResourceBundle rb)
      {
            // TODO
      }

      @FXML private void btnLogOut(MouseEvent event) {}

      @FXML private void btnShowActivies(ActionEvent event)
      {
            utilities.loadFXML(
                "/projectmanagementlisof/gui/FXMLDeveloperActivitiesOption.fxml", apBackground);
      }

      @FXML private void btnShowChanges(ActionEvent event)
      {
            utilities.loadFXML(
                "/projectmanagementlisof/gui/FXMLDeveloperChangesOption.fxml", apBackground);
      }

      @FXML private void btnShowChangeRequests(ActionEvent event)
      {
            utilities.loadFXML(
                "/projectmanagementlisof/gui/FXMLDeveloperChangeRequestsOption.fxml", apBackground);
      }

      @FXML private void btnShowDefects(ActionEvent event)
      {
            utilities.loadFXML(
                "/projectmanagementlisof/gui/FXMLDeveloperDefectsOption.fxml", apBackground);
      }
}
