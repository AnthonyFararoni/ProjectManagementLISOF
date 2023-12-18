/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this
 * license Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projectmanagementlisof.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import projectmanagementlisof.model.dao.ActivityDAO;
import projectmanagementlisof.model.pojo.Activity;
import projectmanagementlisof.observer.DeveloperObserver;
import projectmanagementlisof.utils.Utilities;

/**
 *
 * @author nando
 */
public class FXMLActivitiesOptionController implements Initializable, DeveloperObserver
{
      private ObservableList<Activity> activities;
      private Integer idActivity;

      @FXML private TextField tfSearchActivity;
      @FXML private TableView<Activity> tvUnassignedActivities;
      @FXML private TableColumn colActivityName;
      @FXML private TableColumn colStartDate;
      @FXML private TableColumn colEndDate;
      @FXML private TableColumn colStatus;
      @FXML private Button btnShowCreateActivity;
      @FXML private Button btnEditActivity;
      @FXML private Button btnDeleteActivity;

      @Override public void initialize(URL location, ResourceBundle resources)
      {
            configureUnassignedActivitiesTable();
            getUnassignedActivitiesForTable();
      }

      @FXML private void btnShowCreateActivity(ActionEvent event)
      {
            try
            {
                  FXMLLoader loader = Utilities.loadView("gui/FXMLCreateActivity.fxml");
                  Parent view = loader.load();
                  Scene scene = new Scene(view);
                  FXMLCreateActivityController controller = loader.getController();
                  Stage stage = new Stage();

                  stage.setScene(scene);
                  stage.setTitle("Crear actividad");
                  stage.initModality(Modality.APPLICATION_MODAL);
                  stage.showAndWait();
            }
            catch (IOException ex)
            {
                  ex.printStackTrace();
            }
      }

      @FXML private void btnEditActivity(ActionEvent event)
      { // TODO:
            try
            {
                  FXMLLoader loader = Utilities.loadView("gui/FXMLUpdateActivity.fxml");
                  Parent view = loader.load();
                  Scene scene = new Scene(view);
                  FXMLUpdateActivityController controller = loader.getController();
                  Activity selectedActivity =
                      tvUnassignedActivities.getSelectionModel().getSelectedItem();
                  controller.initializeInformation(idActivity, this, selectedActivity);
                  Stage stage = new Stage();

                  stage.setScene(scene);
                  stage.setTitle("Crear actividad");
                  stage.initModality(Modality.APPLICATION_MODAL);
                  stage.showAndWait();
            }
            catch (IOException ex)
            {
                  Logger.getLogger(FXMLActivitiesOptionController.class.getName())
                      .log(Level.SEVERE, null, ex);
            }
      }

      @FXML private void btnSearchActivity(MouseEvent event)
      {
            searchDeveloper();
      }

      @FXML private void btnDeleteActivity(ActionEvent event)
      {
            boolean confirmation = Utilities.showConfirmationAlert("¿Eliminar actividad?",
                "¿Esta seguro"
                    + " de eliminar la actividad seleccionada?");
            if (confirmation)
            {
                  deleteActivity(idActivity);
            }
      }

      private void configureUnassignedActivitiesTable()
      {
            this.colActivityName.setCellValueFactory(new PropertyValueFactory("name"));
            this.colStartDate.setCellValueFactory(new PropertyValueFactory("startDate"));
            this.colEndDate.setCellValueFactory(new PropertyValueFactory("endDate"));
            this.colStatus.setCellValueFactory(new PropertyValueFactory("statusName"));
            showUnassignedActivitySelected();
      }

      private void showUnassignedActivitySelected()
      {
            tvUnassignedActivities.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Activity>() {
                      @Override
                      public void changed(ObservableValue<? extends Activity> observable,
                          Activity oldValue, Activity newValue)
                      {
                            if (newValue != null)
                            {
                                  btnEditActivity.setDisable(false);
                                  btnDeleteActivity.setDisable(false);
                                  int selectedPosition =
                                      tvUnassignedActivities.getSelectionModel().getSelectedIndex();
                                  Activity selectedActivity = activities.get(selectedPosition);
                                  idActivity = selectedActivity.getIdActivity();
                            }
                      }
                });
      }

      private void getUnassignedActivitiesForTable()
      {
            HashMap<String, Object> answer = ActivityDAO.getUnassignedActivities();
            if (!(boolean) answer.get("error"))
            {
                  activities = FXCollections.observableArrayList();
                  ArrayList<Activity> list = (ArrayList<Activity>) answer.get("activities");
                  activities.addAll(list);
                  tvUnassignedActivities.setItems(activities);
            }
            else
            {
                  Utilities.showSimpleAlert(
                      "Error de carga", (String) answer.get("message"), Alert.AlertType.ERROR);
            }
      }

      private void searchDeveloper()
      {
            String searchActivity = tfSearchActivity.getText();
            if (Utilities.validateStringsFields(searchActivity))
            {
                  HashMap<String, Object> answer = ActivityDAO.searchActivity(searchActivity);
                  if (!(boolean) answer.get("error"))
                  {
                        activities = FXCollections.observableArrayList();
                        ArrayList<Activity> list = (ArrayList<Activity>) answer.get("activities");
                        activities.addAll(list);
                        tvUnassignedActivities.setItems(activities);
                  }
                  else
                  {
                        Utilities.showSimpleAlert("Error de carga", (String) answer.get("mensaje"),
                            Alert.AlertType.ERROR);
                  }
            }
            else
            {
                  Utilities.showSimpleAlert("Busqueda incorrecta",
                      "La estructura de los criterios de "
                          + "busqueda es incorrecta. Intente de nuevo",
                      Alert.AlertType.ERROR);
            }
      }

      private void deleteActivity(int idActivity)
      {
            HashMap<String, Object> answer = ActivityDAO.deleteActivity(idActivity);
            if (!(boolean) answer.get("error"))
            {
                  Utilities.showSimpleAlert("Eliminacion exitosa", (String) answer.get("mensaje"),
                      Alert.AlertType.INFORMATION);
                  getUnassignedActivitiesForTable();
            }
            else
            {
                  Utilities.showSimpleAlert(
                      "Eliminacion fallida", (String) answer.get("mensaje"), Alert.AlertType.ERROR);
            }
            btnDeleteActivity.setDisable(true);
            btnEditActivity.setDisable(true);
      }

      @Override public void developerSelected(Integer idDeveloper, String developerName)
      {
            getUnassignedActivitiesForTable();
      }
}
