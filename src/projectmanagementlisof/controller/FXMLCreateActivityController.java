/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this
 * license Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this
 * template
 */
package projectmanagementlisof.controller;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import projectmanagementlisof.model.dao.ActivityDAO;
import projectmanagementlisof.model.pojo.Activity;
import projectmanagementlisof.observer.DeveloperObserver;
import projectmanagementlisof.utils.Utilities;

/**
 * FXML Controller class
 *
 * @author ferdy
 */
public class FXMLCreateActivityController implements DeveloperObserver, Initializable
{
      private Integer idDeveloper;
      private String developerName;

      @FXML private TextField tfActivityName;
      @FXML private TextField tfAssignDeveloper;
      @FXML private DatePicker dpStartDate;
      @FXML private DatePicker dpEndDate;
      @FXML private TextArea taActivityDescription;

      /**
       * Initializes the controller class.
       */
      @Override public void initialize(URL url, ResourceBundle rb) {}

      public void loadDeveloperName() {}

      @FXML private void btnSelectDeveloper(ActionEvent event)
      {
            try
            {
                  FXMLLoader loader = Utilities.loadView("gui/FXMLChooseDeveloper.fxml");
                  Parent view = loader.load();
                  Scene scene = new Scene(view);
                  FXMLChooseDeveloperController controller = loader.getController();
                  controller.setObserver(this);
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

      @FXML private void btnCreateActivity(ActionEvent event)
      {
            registerActivity();
      }

      private void loadDeveloper(Integer idDeveloper, String developerName)
      {
            this.idDeveloper = idDeveloper;
            tfAssignDeveloper.setText(developerName);
      }

      private void registerActivity()
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
                  Utilities.showSimpleAlert("Actividad Guardada", (String) answer.get("message"),
                      Alert.AlertType.INFORMATION);
            }
            else
            {
                  Utilities.showSimpleAlert(
                      "Error al guardar", (String) answer.get("message"), Alert.AlertType.ERROR);
            }
      }

      @Override public void developerSelected(Integer idDeveloper, String developerName)
      {
            loadDeveloper(idDeveloper, developerName);
            System.out.println(idDeveloper);
      }
}
