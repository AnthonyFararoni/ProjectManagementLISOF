package projectmanagementlisof.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class FXMLChangeRequestDetailsController implements Initializable
{
      @FXML private TextField tfJustification;
      @FXML private TextField tfDateCreated;
      @FXML private TextField tffSolicitedBy;
      @FXML private TextField tfRequestId;
      @FXML private TextArea taDescription;

      @Override public void initialize(URL url, ResourceBundle rb)
      {
            // TODO
      }

      @FXML private void btnReturn(MouseEvent event) {}
}
