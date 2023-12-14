/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this
 * license Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this
 * template
 */
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import projectmanagementlisof.model.dao.ActivityDAO;
import projectmanagementlisof.model.pojo.Activity;
import projectmanagementlisof.observer.DeveloperObserver;
import projectmanagementlisof.utils.Utilities;

/**
 * FXML Controller class
 *
 * @author ferdy
 */
public class FXMLUpdateActivityController implements Initializable, DeveloperObserver
{
      private Integer idDeveloper;
      private Activity updateActivity;
      private DeveloperObserver observer;

      @FXML private TextField tfActivityName;
      @FXML private TextField tfAssignDeveloper;
      @FXML private DatePicker dpStartDate;
      @FXML private DatePicker dpEndDate;
      @FXML private TextArea taActivityDescription;

      /**
       * Initializes the controller class.
       */
      @Override public void initialize(URL url, ResourceBundle rb)
      {
            // TODO
      }

      @FXML private void btnReturn(MouseEvent event) {}

      @FXML private void btnSelectDeveloper(ActionEvent event) {}

      @FXML private void btnUpdateActivity(ActionEvent event)
      {
            updateActivity();
      }

      private void updateActivity()
      {
            Activity activity = new Activity();
            activity.setName(tfActivityName.getText());
            activity.setDescription(taActivityDescription.getText());
            activity.setStartDate(dpStartDate.getValue().toString());
            activity.setEndDate(dpEndDate.getValue().toString());
            if (!tfAssignDeveloper.getText().isEmpty())
            {
                  activity.setIdDeveloper(idDeveloper);
            }
            HashMap<String, Object> answer = ActivityDAO.registerActivity(activity);
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

      public void initializeInformation(Activity updateActivity, DeveloperObserver observer)
      {
            this.updateActivity = updateActivity;
            this.observer = observer;
            if (this.updateActivity != null)
            {
                  loadActivityInformation();
            }
      }

      private void loadActivityInformation()
      {
            tfActivityName.setText(this.updateActivity.getName());
            dpStartDate.setValue(LocalDate.parse(
                this.updateActivity.getStartDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            dpStartDate.setValue(LocalDate.parse(
                this.updateActivity.getEndDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            taActivityDescription.setText(this.updateActivity.getDescription());
      }

      @Override public void developerSelected(Integer idDeveloper, String developerName)
      {
            throw new UnsupportedOperationException(
                "Not supported yet."); // Generated from
                                       // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
      }
}
