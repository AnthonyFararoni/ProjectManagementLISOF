package projectmanagementlisof.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.collections.ListChangeListener.Change;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import projectmanagementlisof.model.dao.ChangeRequestDAO;
import projectmanagementlisof.model.pojo.ChangeRequest;
import projectmanagementlisof.utils.Utilities;

public class FXMLNewChangeRequestFormController implements Initializable
{
      //@FXML private Button btnRegisterChangeRequest;
      @FXML private TextField tfRequestedBy;
      @FXML private DatePicker dpDate;
      @FXML private TextField tfJustification;
      @FXML private TextField tfRequestNumber;
      @FXML private TextField tfChangeDescription;

      private Utilities utilities = new Utilities();

      @Override public void initialize(URL url, ResourceBundle rb)
      {
            // btnRegisterChangeRequest.setCursor(Cursor.HAND);
      }

      @FXML private void createChangeRequest(ActionEvent event)
      {
            if (validateFields())
            {
                  int saved = registerNewChangeRequest();
            }

            System.out.println("Solicitud de cambio creada con éxito");
      }

      public int registerNewChangeRequest()
      {
            ChangeRequest changeRequest = new ChangeRequest();
            LocalDate date = LocalDate.now();
            changeRequest.setJustification(tfJustification.getText());
            changeRequest.setDescription(tfChangeDescription.getText());
            changeRequest.setCreationDate(dpDate.getValue().toString());
            changeRequest.setStatus("Pendiente");
            changeRequest.setIdDeveloper(1);
            changeRequest.setIdProjectManager(1);
            changeRequest.setIdDefect(1);

            ChangeRequestDAO dao = new ChangeRequestDAO();
            int result = dao.createChangeRequest(changeRequest);
            System.out.println(result);

            return result;
      }

      public boolean validateFields()
      {
            boolean isValid = true;

            if (tfJustification.getText().isEmpty())
            {
                  Utilities.showSimpleAlert(
                      "Error", "Debe ingresar una justificación", Alert.AlertType.ERROR);
                  isValid = false;
            }
            else if (tfChangeDescription.getText().isEmpty())
            {
                  Utilities.showSimpleAlert(
                      "Error", "Debe ingresar una descripción del cambio", Alert.AlertType.ERROR);
                  isValid = false;
            }
            else if (dpDate.getValue() == null)
            {
                  Utilities.showSimpleAlert(
                      "Error", "Debe ingresar una fecha", Alert.AlertType.ERROR);
                  isValid = false;
            }

            return isValid;
      }
}