/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this
 * license Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this
 * template
 */
package projectmanagementlisof.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import projectmanagementlisof.model.dao.ActivityDAO;
import projectmanagementlisof.model.pojo.Activity;
import projectmanagementlisof.observer.DeveloperObserver;
import projectmanagementlisof.utils.LoggedUserSingleton;
import projectmanagementlisof.utils.SelectedProjectSingleton;
import projectmanagementlisof.utils.Utilities;

/**
 * FXML Controller class
 *
 * @author ferdy
 */
public class FXMLUpdateActivityController implements Initializable, DeveloperObserver
{
      private Integer idDeveloper;
      private Integer idActivity;
      private Integer idProject;
      private Integer idProjectManager;
      private Activity updateActivity;
      private DeveloperObserver observer;
      private Utilities utilities = new Utilities();

      @FXML private TextField tfActivityName;
      @FXML private TextField tfAssignDeveloper;
      @FXML private DatePicker dpStartDate;
      @FXML private DatePicker dpEndDate;
      @FXML private TextArea taActivityDescription;
      @FXML private ImageView btnReturn;
      @FXML private Button btnUpdateActivity;

      /**
       * Initializes the controller class.
       */
      @Override public void initialize(URL url, ResourceBundle rb)
      {
            SelectedProjectSingleton projectInstance = SelectedProjectSingleton.getInstance();
            idProject = projectInstance.getIdSelectedProject();
            LoggedUserSingleton userInstance = LoggedUserSingleton.getInstance();
            idProjectManager = userInstance.getUserId();
            btnUpdateActivity.setDisable(true);
            tfActivityName.textProperty().addListener(
                (observable, oldValue, newValue) -> checkEnableButton());
            dpStartDate.valueProperty().addListener(
                (observable, oldValue, newValue) -> checkEnableButton());
            dpEndDate.valueProperty().addListener(
                (observable, oldValue, newValue) -> checkEnableButton());
            taActivityDescription.textProperty().addListener(
                (observable, oldValue, newValue) -> checkEnableButton());
      }

      private void initializeInformation(Integer idActivity)
      {
            this.idActivity = idActivity;
      }

      @FXML private void btnReturn(MouseEvent event)
      {
            Stage currentStage = (Stage) tfActivityName.getScene().getWindow();
            Utilities.closeWindow(currentStage);

            Utilities.loadFXMLInAnchorPaneAndCloseCurrentForProjectManager(currentStage,
                      "/projectmanagementlisof/gui/FXMLProjectManagerLanding.fxml",
                      "/projectmanagementlisof/gui/FXMLActivitiesOption.fxml");
      }

      @FXML private void btnSelectDeveloper(ActionEvent event)
      {
            try
            {
                  FXMLLoader loader = Utilities.loadView("gui/FXMLChooseDeveloper.fxml");
                  Parent view = loader.load();
                  Scene scene = new Scene(view);
                  FXMLChooseDeveloperController controller = loader.getController();
                  controller.setObserver((DeveloperObserver) this);
                  Stage stage = new Stage();

                  stage.setScene(scene);
                  stage.setTitle("Asignar desarrollador");
                  stage.initModality(Modality.APPLICATION_MODAL);
                  stage.showAndWait();
            }
            catch (IOException ex)
            {
                  ex.printStackTrace();
            }
      }

      @FXML private void btnUpdateActivity(ActionEvent event)
      {
            if (!tfAssignDeveloper.getText().isEmpty())
            {
                  updateAssignedActivity();
            }
            else
            {
                  updateUnassignedActivity();
            }
      }

      private void updateUnassignedActivity()
      {
            Activity activity = new Activity();
            activity.setName(tfActivityName.getText());
            activity.setDescription(taActivityDescription.getText());
            activity.setStartDate(dpStartDate.getValue().toString());
            activity.setEndDate(dpEndDate.getValue().toString());
            activity.setIdProjectManager(idProjectManager);
            activity.setIdProject(idProject);
            activity.setIdActivity(idActivity);

            HashMap<String, Object> answer = ActivityDAO.updateUnassignedActivity(activity);
            if (!(boolean) answer.get("error"))
            {
                  Utilities.showSimpleAlert("Cambios Guardados", (String) answer.get("message"),
                      Alert.AlertType.INFORMATION);
            }
            else
            {
                  Utilities.showSimpleAlert(
                      "Error al guardar", (String) answer.get("message"), Alert.AlertType.ERROR);
            }
      }

      private void updateAssignedActivity()
      {
            Activity activity = new Activity();
            activity.setName(tfActivityName.getText());
            activity.setDescription(taActivityDescription.getText());
            activity.setStartDate(dpStartDate.getValue().toString());
            activity.setEndDate(dpEndDate.getValue().toString());
            activity.setStatus(2); //Cambia el estado a asignado
            activity.setIdDeveloper(idDeveloper);
            activity.setIdProjectManager(idProjectManager);
            activity.setIdProject(idProject);
            activity.setIdActivity(idActivity);
            HashMap<String, Object> answer = ActivityDAO.updateAssignedActivity(activity);
            if (!(boolean) answer.get("error"))
            {
                  Utilities.showSimpleAlert("Cambios Guardados", (String) answer.get("message"),
                      Alert.AlertType.INFORMATION);
                  observer.developerSelected(idDeveloper, activity.getName());
            }
            else
            {
                  Utilities.showSimpleAlert(
                      "Error al guardar", (String) answer.get("message"), Alert.AlertType.ERROR);
            }
      }

      public void initializeInformation(
          Integer idActivity, DeveloperObserver observer, Activity updateActivity)
      {
            this.idActivity = idActivity;
            this.observer = observer;
            this.updateActivity = updateActivity;
            loadUnassignedActivityInformation();
      }

      private void loadUnassignedActivityInformation()
      {
            tfActivityName.setText(this.updateActivity.getName());
            taActivityDescription.setText(this.updateActivity.getDescription());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate startDate = LocalDate.parse(this.updateActivity.getStartDate(), formatter);
            dpStartDate.setValue(startDate);
            LocalDate endDate = LocalDate.parse(this.updateActivity.getEndDate(), formatter);
            dpEndDate.setValue(endDate);
      }

      private void loadDeveloper(Integer idDeveloper, String developerName)
      {
            this.idDeveloper = idDeveloper;
            tfAssignDeveloper.setText(developerName);
      }

      private void checkEnableButton()
      {
            boolean allFieldsFilled = !tfActivityName.getText().isEmpty()
                && dpStartDate.getValue() != null && dpEndDate.getValue() != null
                && !taActivityDescription.getText().isEmpty();

            btnUpdateActivity.setDisable(!allFieldsFilled);
      }

      @Override public void developerSelected(Integer idDeveloper, String developerName)
      {
            loadDeveloper(idDeveloper, developerName);
            System.out.println(idDeveloper);
      }

      @FXML private void changeToDefaultCursor(MouseEvent event)
      {
            btnReturn.setCursor(Cursor.DEFAULT);
      }

      @FXML private void changeToHandCursor(MouseEvent event)
      {
            btnReturn.setCursor(Cursor.HAND);
      }
}
