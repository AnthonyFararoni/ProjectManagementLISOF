package projectmanagementlisof.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import projectmanagementlisof.model.dao.ChangeRequestDAO;
import projectmanagementlisof.model.dao.ProjectManagerDAO;
import projectmanagementlisof.model.pojo.ChangeRequest;
import projectmanagementlisof.model.pojo.ChangeRequestStatus;
import projectmanagementlisof.model.pojo.Developer;
import projectmanagementlisof.model.pojo.ProjectManager;
import projectmanagementlisof.observer.DeveloperObserver;
import projectmanagementlisof.utils.LoggedUserSingleton;
import projectmanagementlisof.utils.Utilities;

public class FXMLChangeRequestDetailsController implements Initializable, DeveloperObserver
{
      @FXML private TextField tfJustification;
      @FXML private TextField tfDateCreated;
      @FXML private TextField tfRequestedBy;
      @FXML private TextArea taChangeDescription;
      @FXML private TextField tfDateReviewed;
      @FXML private TextField tfReviewedBy;
      @FXML private ComboBox<ChangeRequestStatus> cbStatus;
      private ObservableList<ChangeRequestStatus> statuses = FXCollections.observableArrayList();

      private DeveloperObserver observer;
      private Integer idChangeRequest;
      private ChangeRequest selectedChangeRequest;
      private Integer idDeveloper;
      private String developerNameString;
      private Integer idProjectManager;

      @Override public void initialize(URL url, ResourceBundle rb)
      {
            Utilities.setItemsInComboBoxStatuses(statuses, cbStatus);
      }

      public void setSelectedChangeRequest(ChangeRequest selectedChangeRequest)
      {
            this.selectedChangeRequest = selectedChangeRequest;
            this.idChangeRequest = selectedChangeRequest.getIdChangeRequest();

            loadChangeRequestInformation();
      }

      @FXML private void btnSaveChanges(ActionEvent event)
      {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "¿Está seguro de actualizar el estado de la solicitud de cambio?", ButtonType.YES,
                ButtonType.NO);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES)
            {
                  updateChangeRequestStatusFromComboBox();
                  Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                  Utilities.closeWindow(stage);

                  Utilities.loadFXMLInAnchorPaneAndCloseCurrentForProjectManager(stage,
                      "/projectmanagementlisof/gui/FXMLProjectManagerLanding.fxml",
                      "/projectmanagementlisof/gui/FXMLChangeRequestsOption.fxml");
            }
      }

      private void updateChangeRequestStatusFromComboBox()
      {
            if (cbStatus.getSelectionModel().getSelectedItem() != null)
            {
                  ChangeRequestStatus changeRequestStatus =
                      cbStatus.getSelectionModel().getSelectedItem();

                  ChangeRequest changeRequest = new ChangeRequest();
                  changeRequest.setReviewDate(LocalDate.now().toString());
                  changeRequest.setIdProjectManager(LoggedUserSingleton.getInstance().getUserId());
                  changeRequest.setIdChangeRequest(this.idChangeRequest);

                  HashMap<String, Object> answerChangeRequest =
                      ChangeRequestDAO.updateIdProjectManagerAndReviewDate(changeRequest);

                  HashMap<String, Object> answer = ChangeRequestDAO.updateChangeRequestStatus(
                      this.idChangeRequest, changeRequestStatus.getIdChangeRequestStatus());

                  if (!(boolean) answer.get("error") && !(boolean) answerChangeRequest.get("error"))
                  {
                        Utilities.showSimpleAlert("Éxito",
                            "El estado de la solicitud de cambio ha sido actualizado exitosamente.",
                            Alert.AlertType.INFORMATION);
                  }
                  else
                  {
                        Utilities.showSimpleAlert("Error de actualización",
                            "Ha ocurrido un error al tratar de actualizar el estado de la solicitud de cambio.",
                            Alert.AlertType.ERROR);
                  }
            }
            else
            {
                  Utilities.showSimpleAlert("Error de actualización",
                      "Debe seleccionar un estado para la solicitud de cambio",
                      Alert.AlertType.ERROR);
            }
      }

      private void loadChangeRequestInformation()
      {
            tfJustification.setText(this.selectedChangeRequest.getJustification());
            taChangeDescription.setText(this.selectedChangeRequest.getDescription());
            this.idDeveloper = this.selectedChangeRequest.getIdDeveloper();

            HashMap<String, Object> changeRequestInformation =
                ChangeRequestDAO.getCompleteDeveloperName(
                    this.selectedChangeRequest.getIdDeveloper());

            tfDateCreated.setText(this.selectedChangeRequest.getCreationDate().toString());

            if (this.selectedChangeRequest.getReviewDate() != null)
            {
                  tfDateReviewed.setText(this.selectedChangeRequest.getReviewDate().toString());
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
                      "Ha ocurrido un error al cargar el nombre del desarrollador la información de la solcitud",
                      Alert.AlertType.ERROR);
            }

            try
            {
                  HashMap<String, Object> projectManagerInformation =
                      ProjectManagerDAO.getProjectManagerById(
                          this.selectedChangeRequest.getIdProjectManager());

                  if (projectManagerInformation != null
                      && projectManagerInformation.containsKey("projectManager")
                      && projectManagerInformation.get("projectManager") instanceof ProjectManager)
                  {
                        ProjectManager projectManager =
                            (ProjectManager) projectManagerInformation.get("projectManager");
                        if (projectManager != null)
                        {
                              tfReviewedBy.setText(projectManager.getFullName());
                        }
                        else
                        {
                              System.out.println("Project Manager is null");
                        }
                  }
                  else
                  {
                        System.out.println("Project Manager Information is null");
                  }
            }
            catch (Exception e)
            {
                  Utilities.showSimpleAlert(
                      "Error de carga", e.getMessage(), Alert.AlertType.ERROR);
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

      @FXML private void btnReturn(MouseEvent event)
      {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            Utilities.loadFXMLInAnchorPaneAndCloseCurrentForProjectManager(stage,
                "/projectmanagementlisof/gui/FXMLProjectManagerLanding.fxml",
                "/projectmanagementlisof/gui/FXMLChangeRequestsOption.fxml");
      }
}
