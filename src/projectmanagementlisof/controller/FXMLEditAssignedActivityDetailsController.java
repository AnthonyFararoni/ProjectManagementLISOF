package projectmanagementlisof.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import projectmanagementlisof.model.dao.ActivityDAO;
import projectmanagementlisof.model.dao.DeveloperDAO;
import projectmanagementlisof.model.pojo.Activity;
import projectmanagementlisof.model.pojo.Developer;
import projectmanagementlisof.observer.DeveloperObserver;
import projectmanagementlisof.utils.SelectedItemSingleton;
import projectmanagementlisof.utils.Utilities;

public class FXMLEditAssignedActivityDetailsController implements Initializable, DeveloperObserver
{
      @FXML private AnchorPane apEditAssignedActivityDetails;
      @FXML private TextArea taActivityName;
      @FXML private TextField tfAssignedDeveloper;
      @FXML private TextArea taActivityDescription;
      @FXML private DatePicker dpStartDate;
      @FXML private DatePicker dpEndDate;
      @FXML private Button btnEditAssignedActivity;
      @FXML private ImageView ivReturn;
      private String developerNameString;
      private Integer idDeveloper;
      private Integer idActivity;
      private Activity updateActivity;
      private DeveloperObserver observer;
      private Activity activity;

      @Override public void initialize(URL url, ResourceBundle rb)
      {
            btnEditAssignedActivity.setDisable(true);
            taActivityName.textProperty().addListener(
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

      @FXML private void clickImageReturn(MouseEvent event)
      {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "¿Está seguro de volver? Los cambios no guardados se perderán.", ButtonType.YES,
                ButtonType.NO);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES)
            {
                  Stage currentStage = (Stage) taActivityName.getScene().getWindow();
                  Utilities.closeWindow(currentStage);
            }
      }

      @FXML private void clickChooseDeveloper(ActionEvent event)
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
                  stage.setTitle("Reasignar desarrollador");
                  stage.initModality(Modality.APPLICATION_MODAL);
                  stage.showAndWait();
            }
            catch (IOException ex)
            {
                  ex.printStackTrace();
            }
      }

      @FXML private void clickDeassignDeveloper(ActionEvent event)
      {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "¿Está seguro de desasignar el desarrollador?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES)
            {
                  tfAssignedDeveloper.setText("");
                  this.idDeveloper = null;
            }
      }

      @FXML private void btnEditAssignedActivity(ActionEvent event)
      {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "¿Está seguro de guardar los detalles de la actividad?", ButtonType.YES,
                ButtonType.NO);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES)
            {
                  updateAssignedActivity();
                  Stage currentStage = (Stage) taActivityName.getScene().getWindow();
                  Utilities.closeWindow(currentStage);
            }
      }

      private void updateAssignedActivity()
      {
            Activity activity = new Activity();

            activity.setName(taActivityName.getText());
            activity.setDescription(taActivityDescription.getText());
            activity.setStartDate(dpStartDate.getValue().toString());
            activity.setEndDate(dpEndDate.getValue().toString());

            if (this.idDeveloper != null)
            {
                  activity.setIdDeveloper(this.idDeveloper);
            }

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
            loadAssignedActivityInformation();
      }

      private void loadAssignedActivityInformation()
      {
            taActivityName.setText(this.updateActivity.getName());
            taActivityDescription.setText(this.updateActivity.getDescription());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate startDate = LocalDate.parse(this.updateActivity.getStartDate(), formatter);
            dpStartDate.setValue(startDate);
            LocalDate endDate = LocalDate.parse(this.updateActivity.getStartDate(), formatter);
            dpEndDate.setValue(endDate);

            this.idDeveloper = this.updateActivity.getIdDeveloper();

            HashMap<String, Object> answer =
                ActivityDAO.getCompleteDeveloperName(this.updateActivity.getIdDeveloper());

            if (!(boolean) answer.get("error"))
            {
                  Developer developer = (Developer) answer.get("developer");
                  developerNameString = developer.getFullName();
                  tfAssignedDeveloper.setText(developerNameString);
            }
            else
            {
                  Utilities.showSimpleAlert(
                      "Error de carga", (String) answer.get("message"), Alert.AlertType.ERROR);
            }
      }

      private void loadDeveloper(Integer idDeveloper, String developerName)
      {
            this.idDeveloper = idDeveloper;
            tfAssignedDeveloper.setText(developerName);
      }

      private void checkEnableButton()
      {
            boolean allFieldsFilled = !taActivityName.getText().isEmpty()
                && dpStartDate.getValue() != null && dpEndDate.getValue() != null
                && !taActivityDescription.getText().isEmpty();

            btnEditAssignedActivity.setDisable(!allFieldsFilled);
      }

      @Override public void developerSelected(Integer idDeveloper, String developerName)
      {
            loadDeveloper(idDeveloper, developerName);
            System.out.println(idDeveloper);
      }
}
