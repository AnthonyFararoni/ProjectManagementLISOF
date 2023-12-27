package projectmanagementlisof.controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import projectmanagementlisof.model.dao.ChangeRequestDAO;
import projectmanagementlisof.model.pojo.ChangeRequest;
import projectmanagementlisof.model.pojo.Developer;
import projectmanagementlisof.observer.DeveloperObserver;
import projectmanagementlisof.utils.Utilities;

public class FXMLChangeRequestDetailsController implements Initializable, DeveloperObserver
{
      @FXML private TextField tfJustification;
      @FXML private TextField tfDateCreated;
      @FXML private TextField tfRequestedBy;
      @FXML private TextArea taChangeDescription;
      @FXML private TextField tfDateReviewed;

      private DeveloperObserver observer;
      private Integer idChangeRequest;
      private ChangeRequest updateChangeRequest;
      private Integer idDeveloper;
      private String developerNameString;

      @Override public void initialize(URL url, ResourceBundle rb)
      {
            // TODO
      }

      public void initializeInformation(
          Integer idChangeRequest, DeveloperObserver observer, ChangeRequest updateChangeRequest)
      {
            this.observer = observer;
            this.updateChangeRequest = updateChangeRequest;
            this.idChangeRequest = this.updateChangeRequest.getIdChangeRequest();

            loadChangeRequestInformation();
      }

      private void loadChangeRequestInformation()
      {
            try
            {
                  tfJustification.setText(this.updateChangeRequest.getJustification());
                  taChangeDescription.setText(this.updateChangeRequest.getDescription());

                  this.idDeveloper = this.updateChangeRequest.getIdDeveloper();

                  HashMap<String, Object> changeRequestInformation =
                      ChangeRequestDAO.getCompleteDeveloperName(
                          this.updateChangeRequest.getIdDeveloper());

                  tfDateCreated.setText(this.updateChangeRequest.getCreationDate().toString());

                  if (this.updateChangeRequest.getReviewDate() != null)
                  {
                        tfDateReviewed.setText(this.updateChangeRequest.getReviewDate().toString());
                  }

                  if (!(boolean) changeRequestInformation.get("error"))
                  {
                        Developer developer = (Developer) changeRequestInformation.get("developer");
                        developerNameString = developer.getFullName();
                        tfRequestedBy.setText(developerNameString);
                  }
                  else
                  {
                        Utilities.showSimpleAlert("Error de carga",
                            (String) changeRequestInformation.get("message"),
                            Alert.AlertType.ERROR);
                  }
            }
            catch (Exception e)
            {
                  Utilities.showSimpleAlert("Error de carga",
                      "Error al cargar la información de la solicitud de cambio",
                      Alert.AlertType.ERROR);
            }
      }

      private void loadDeveloper(Integer idDeveloper, String developerName)
      {
            this.idDeveloper = idDeveloper;
            tfRequestedBy.setText(developerName);
      }

      @Override public void developerSelected(Integer idDeveloper, String developerName)
      {
            loadDeveloper(idDeveloper, developerName);
            System.out.println(idDeveloper);
      }

      @FXML private void clickImageReturn(MouseEvent event)
      {
            Stage currentStage = (Stage) tfJustification.getScene().getWindow();
            Utilities.closeWindow(currentStage);
      }

      @FXML private void btnAproveChangeRequest(ActionEvent event)
      {
            HashMap<String, Object> answer =
                ChangeRequestDAO.approveChangeRequest(this.idChangeRequest);

            if (!(boolean) answer.get("error"))
            {
                  Utilities.showSimpleAlert("Solicitud de cambio aprobada",
                      (String) answer.get("message"), Alert.AlertType.INFORMATION);
                  this.observer.developerSelected(this.idDeveloper, this.developerNameString);
                  Stage currentStage = (Stage) tfJustification.getScene().getWindow();
                  Utilities.closeWindow(currentStage);
            }
            else
            {
                  Utilities.showSimpleAlert("Error al aprobar la solicitud de cambio",
                      (String) answer.get("message"), Alert.AlertType.ERROR);
            }
      }

      @FXML private void btnRejectChangeRequest(ActionEvent event)
      {
            HashMap<String, Object> answer =
                ChangeRequestDAO.rejectChangeRequest(this.idChangeRequest);

            if (!(boolean) answer.get("error"))
            {
                  Utilities.showSimpleAlert("Solicitud de cambio rechazada",
                      (String) answer.get("message"), Alert.AlertType.INFORMATION);
                  this.observer.developerSelected(this.idDeveloper, this.developerNameString);
                  Stage currentStage = (Stage) tfJustification.getScene().getWindow();
                  Utilities.closeWindow(currentStage);
            }
            else
            {
                  Utilities.showSimpleAlert("Error al rechazar la solicitud de cambio",
                      (String) answer.get("message"), Alert.AlertType.ERROR);
            }
      }

      @FXML private void btnReturn(MouseEvent event)
      {
            Stage currentStage = (Stage) tfJustification.getScene().getWindow();
            Utilities.closeWindow(currentStage);
      }
}
