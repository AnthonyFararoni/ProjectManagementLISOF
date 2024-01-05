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
import javafx.scene.Node;
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
import projectmanagementlisof.utils.SelectedProjectSingleton;
import projectmanagementlisof.utils.Utilities;

/**
 *
 * @author nando
 */
public class FXMLActivitiesOptionController implements Initializable, DeveloperObserver
{
      private ObservableList<Activity> activities;
      private Integer idActivity;
      private Integer idProjectSelected;

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
          SelectedProjectSingleton instance = SelectedProjectSingleton.getInstance();
          idProjectSelected = instance.getIdSelectedProject();  
          configureUnassignedActivitiesTable();
          getUnassignedActivitiesForTable();
      }

      @FXML private void btnShowCreateActivity(ActionEvent event)
      {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Utilities.<FXMLChangeRequestDetailsController>closeCurrentWindowAndOpenAnotherOne(
        "/projectmanagementlisof/gui/FXMLCreateActivity.fxml", stage, event);
            
      }

      @FXML private void btnEditActivity(ActionEvent event)
      {
          Activity selectedActivity =
                tvUnassignedActivities.getSelectionModel().getSelectedItem();
          if (selectedActivity != null)
            {
                  Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                  FXMLUpdateActivityController updateController =
                      Utilities
                          .<FXMLUpdateActivityController>closeCurrentWindowAndOpenAnotherOne(
                              "/projectmanagementlisof/gui/FXMLUpdateActivity.fxml", stage,
                              event);
                  updateController.initializeInformation(idActivity, this, selectedActivity);
            }
            else
            {
                  Utilities.showSimpleAlert("Error de selección",
                      "Debe seleccionar una Actividad.", Alert.AlertType.ERROR);
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
            HashMap<String, Object> answer = ActivityDAO.getUnassignedActivities(idProjectSelected);
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
                  HashMap<String, Object> answer = ActivityDAO.searchActivity(searchActivity, idProjectSelected);
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
