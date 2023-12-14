package projectmanagementlisof.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import projectmanagementlisof.utils.Utilities;

public class FXMLDeveloperLandingController implements Initializable
{
    private Utilities utilities = new Utilities();
    @FXML
    private AnchorPane apBackground;
      @Override public void initialize(URL url, ResourceBundle rb)
      {
            // TODO
      }

      @FXML private void btnLogOut(MouseEvent event) {}

      @FXML private void btnShowActivies(ActionEvent event) {}
      

      @FXML private void btnShowChanges(ActionEvent event) {}

      @FXML private void btnShowChangeRequests(ActionEvent event) {}

      @FXML private void btnShowDefects(ActionEvent event) 
      {
          utilities.loadFXML( "/projectmanagementlisof/gui/FXMLDeveloperDefectsOption.fxml",apBackground);
      }
}
