package projectmanagementlisof.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.collections.ListChangeListener.Change;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import projectmanagementlisof.model.dao.ChangeRequestDAO;
import projectmanagementlisof.model.pojo.ChangeRequest;
import projectmanagementlisof.utils.Utilities;

public class FXMLNewChangeRequestFormController implements Initializable
{
      private Utilities utilities = new Utilities();
      @FXML private TextField tfRequestedBy;
      @FXML private DatePicker dpDate;
      @FXML private TextField tfJustification;
      @FXML private TextField tfRequestNumber;
      @FXML private TextField tfChangeDescription;

      @Override public void initialize(URL url, ResourceBundle rb)
      {
            // btnRegisterChangeRequest.setCursor(Cursor.HAND);
      }

      @FXML private void btnCreateChangeRequest(ActionEvent event)
      {
            if (validateFields())
            {
                  createChangeRequest();
            }
      }

      private void createChangeRequest()
      {
            ChangeRequest changeRequest = new ChangeRequest();
            changeRequest.setJustification(tfJustification.getText());
            changeRequest.setDescription(tfChangeDescription.getText());
            changeRequest.setStatus("1");
            changeRequest.setCreationDate(LocalDate.now().toString());
            changeRequest.setReviewDate(dpDate.getValue().toString());
            // changeRequest.setIdDeveloper(1);
            // changeRequest.setIdProjectManager(1);
            // changeRequest.setIdDefect(1);

            HashMap<String, Object> answer = ChangeRequestDAO.createChangeRequest(changeRequest);
            if ((boolean) answer.get("error"))
            {
                  Utilities.showSimpleAlert(
                      "Error", (String) answer.get("message"), Alert.AlertType.ERROR);
            }
            else
            {
                  Utilities.showSimpleAlert(
                      "Éxito", "Solicitud de cambio registrada", Alert.AlertType.INFORMATION);
            }
      }

      private boolean validateFields()
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