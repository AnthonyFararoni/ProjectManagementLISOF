package projectmanagementlisof.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import projectmanagementlisof.model.dao.ChangeRequestDAO;
import projectmanagementlisof.model.dao.DeveloperDAO;
import projectmanagementlisof.model.pojo.Activity;
import projectmanagementlisof.model.pojo.ChangeRequest;
import projectmanagementlisof.model.pojo.Developer;
import projectmanagementlisof.utils.UserSingleton;
import projectmanagementlisof.utils.Utilities;

public class FXMLNewChangeRequestFormController implements Initializable
{
      @FXML private AnchorPane apNewChangeRequestForm;
      @FXML private TextField tfRequestedBy;
      @FXML private DatePicker dpDate;
      @FXML private TextField tfJustification;
      @FXML private TextField tfRequestNumber;
      @FXML private TextField tfChangeDescription;

      @Override public void initialize(URL url, ResourceBundle rb)
      {
            setDate();
      }

      @FXML private void btnCreateChangeRequest(ActionEvent event)
      {
            if (validateFields())
                  createChangeRequest();
      }

      @FXML private void clickImageReturn(MouseEvent event) throws Exception
      {
            Stage stage = (Stage) apNewChangeRequestForm.getScene().getWindow();
            stage.close();
      }

      private void setDate()
      {
            dpDate.setValue(LocalDate.now());
      }

      private void createChangeRequest()
      {
            ChangeRequest changeRequest = new ChangeRequest();
            changeRequest.setJustification(tfJustification.getText());
            changeRequest.setDescription(tfChangeDescription.getText());
            changeRequest.setStatus(1);
            changeRequest.setCreationDate(LocalDate.now().toString());
            changeRequest.setIdDeveloper(1);
            changeRequest.setIdProjectManager(1);
            changeRequest.setIdDefect(1);

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