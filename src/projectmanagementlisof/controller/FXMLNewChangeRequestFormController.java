package projectmanagementlisof.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import projectmanagementlisof.model.dao.ChangeRequestDAO;
import projectmanagementlisof.model.pojo.ChangeRequest;
import projectmanagementlisof.utils.LoggedUserSingleton;
import projectmanagementlisof.utils.Utilities;

public class FXMLNewChangeRequestFormController implements Initializable
{
      @FXML private AnchorPane apNewChangeRequestForm;
      @FXML private TextField tfRequestedBy;
      @FXML private DatePicker dpDate;
      @FXML private TextField tfJustification;
      @FXML private TextField tfRequestNumber;
      @FXML private TextArea taChangeDescription;

      @Override public void initialize(URL url, ResourceBundle rb)
      {
            setDate();
            setRequestedBy();
            Utilities.limitTextFieldCharacters(tfJustification, 50);
            Utilities.limitTextAreaCharacters(taChangeDescription, 65535);
      }

      @FXML private void btnCreateChangeRequest(ActionEvent event)
      {
            LocalDate selectedDate = dpDate.getValue();
            LocalDate currentDate = LocalDate.now();

            if (selectedDate.isBefore(currentDate))
            {
                  Utilities.showSimpleAlert("Error en fecha de solicitud",
                      "La fecha seleccionada no puede ser anterior a la fecha del día de hoy.",
                      Alert.AlertType.ERROR);
                  return;
            }

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "¿Está seguro de crear esta solicitud de cambio?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES)
            {
                  if (validateFields())
                        createChangeRequest();
            }
      }

      @FXML private void clickImageReturn(MouseEvent event)
      {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "¿Está seguro de cancelar el proceso de registro de la solicitud de cambio?",
                ButtonType.YES, ButtonType.NO);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES)
            {
                  Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                  Utilities.loadFXMLInAnchorPaneAndCloseCurrentForDeveloper(currentStage,
                      "/projectmanagementlisof/gui/FXMLDeveloperLanding.fxml",
                      "/projectmanagementlisof/gui/FXMLDeveloperChangeRequestsOption.fxml");
            }
      }

      private void setDate()
      {
            dpDate.setValue(LocalDate.now());
      }

      private void setRequestedBy()
      {
            tfRequestedBy.setText(LoggedUserSingleton.getInstance().getUserFullName());
      }

      private void createChangeRequest()
      {
            ChangeRequest changeRequest = new ChangeRequest();
            changeRequest.setJustification(tfJustification.getText());
            changeRequest.setDescription(taChangeDescription.getText());
            changeRequest.setCreationDate(dpDate.getValue().toString());
            changeRequest.setDeveloperName(tfRequestedBy.getText());
            changeRequest.setIdDeveloper(LoggedUserSingleton.getInstance().getUserId());
            changeRequest.setIdStatus(5);

            HashMap<String, Object> answer = ChangeRequestDAO.createChangeRequest(changeRequest);

            if (!(boolean) answer.get("error"))
            {
                  Utilities.showSimpleAlert(
                      "Éxito", "Solicitud de cambio creada.", Alert.AlertType.INFORMATION);
                  Stage currentStage = (Stage) apNewChangeRequestForm.getScene().getWindow();
                  Utilities.loadFXMLInAnchorPaneAndCloseCurrentForDeveloper(currentStage,
                      "/projectmanagementlisof/gui/FXMLDeveloperLanding.fxml",
                      "/projectmanagementlisof/gui/FXMLDeveloperChangeRequestsOption.fxml");
            }
            else
            {
                  Utilities.showSimpleAlert(
                      "Error", "No se pudo crear la solicitud de cambio", Alert.AlertType.ERROR);
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
            else if (taChangeDescription.getText().isEmpty())
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