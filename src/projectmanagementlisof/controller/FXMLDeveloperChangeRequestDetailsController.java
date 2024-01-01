package projectmanagementlisof.controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import projectmanagementlisof.model.dao.ActivityDAO;
import projectmanagementlisof.model.dao.ChangeRequestDAO;
import projectmanagementlisof.model.dao.DeveloperDAO;
import projectmanagementlisof.model.pojo.Activity;
import projectmanagementlisof.model.pojo.ChangeRequest;
import projectmanagementlisof.model.pojo.Developer;
import projectmanagementlisof.observer.DeveloperObserver;
import projectmanagementlisof.utils.Utilities;

public class FXMLDeveloperChangeRequestDetailsController implements Initializable, DeveloperObserver
{
      @FXML private AnchorPane apNewChangeRequestForm;
      @FXML private TextField tfRequestedBy;
      @FXML private DatePicker dpDate;
      @FXML private TextField tfJustification;
      @FXML private TextField tfRequestNumber;
      @FXML private TextArea taChangeDescription;

      private DeveloperObserver observer;
      private Integer idChangeRequest;
      private ChangeRequest updateChangeRequest;
      private Integer idDeveloper;
      private String developerNameString;

      @Override public void initialize(URL url, ResourceBundle rb)
      {
            // TODO Auto-generated method stub
      }

      public void initializeInformation(
          Integer idChangeRequest, DeveloperObserver observer, ChangeRequest updateChangeRequest)
      {
            this.idChangeRequest = idChangeRequest;
            this.observer = observer;
            this.updateChangeRequest = updateChangeRequest;
            loadChangeRequestInformation();
      }

      private void loadChangeRequestInformation()
      {
            tfJustification.setText(this.updateChangeRequest.getJustification());
            taChangeDescription.setText(this.updateChangeRequest.getDescription());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate creationDate =
                LocalDate.parse(this.updateChangeRequest.getCreationDate(), formatter);
            dpDate.setValue(creationDate);

            this.idDeveloper = this.updateChangeRequest.getIdDeveloper();

            HashMap<String, Object> changeRequestInformation =
                ChangeRequestDAO.getCompleteDeveloperName(
                    this.updateChangeRequest.getIdDeveloper());

            if (!(boolean) changeRequestInformation.get("error"))
            {
                  Developer developer = (Developer) changeRequestInformation.get("developer");
                  developerNameString = developer.getFullName();
                  tfRequestedBy.setText(developerNameString);
            }
            else
            {
                  Utilities.showSimpleAlert("Error de carga",
                      (String) changeRequestInformation.get("message"), Alert.AlertType.ERROR);
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
            Utilities.loadFXMLInAnchorPaneAndCloseCurrentForDeveloper(currentStage,
                "/projectmanagementlisof/gui/FXMLDeveloperLanding.fxml",
                "/projectmanagementlisof/gui/FXMLDeveloperChangeRequestsOption.fxml");
      }
}
