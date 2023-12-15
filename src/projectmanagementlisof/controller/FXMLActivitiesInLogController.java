/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this
 * license Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this
 * template
 */
package projectmanagementlisof.controller;

import com.sun.glass.events.MouseEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.beans.InvalidationListener;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import projectmanagementlisof.controller.FXMLDeveloperLogController;
import projectmanagementlisof.model.dao.ActivityDAO;
import projectmanagementlisof.model.dao.DeveloperDAO;
import projectmanagementlisof.model.pojo.Activity;
import projectmanagementlisof.model.pojo.Developer;
import projectmanagementlisof.utils.UserSingleton;
import projectmanagementlisof.utils.Utilities;

/**
 * FXML Controller class
 *
 * @author edmun
 */
public class FXMLActivitiesInLogController implements Initializable
{
      private ObservableList<Activity> activities;
      @FXML private AnchorPane apActivitiesLogAnchorPane;
      private int idDeveloper;
      private int idActivity;
      private Utilities utilities = new Utilities();

      @FXML private TableView<Activity> tvAssignedActivities;
      @FXML private TableColumn colActivityName;
      @FXML private TableColumn colStartDate;
      @FXML private TableColumn colEndDate;
      @FXML private TableColumn colStatus;
      @FXML private Button btnShowAssignedActivityDetails;
      @FXML private Button btnEditAssignedActivity;
      @FXML private Button btnDeleteAssignedActivity;

      /**
       * Initializes the controller class.
       */
      @Override public void initialize(URL url, ResourceBundle rb)
      {
            receiveData();
            configureActivitiesTable();
            getAssignedActivitiesForTable();
            System.out.println(idDeveloper);
      }

      public void receiveData()
      {
            UserSingleton instance = UserSingleton.getInstace();
            idDeveloper = instance.getIdSelected();
      }

      @FXML private void btnDeleteAssignedActivity(ActionEvent event)
      {
            boolean confirmation = Utilities.showConfirmationAlert("¿Eliminar actividad?",
                "¿Esta seguro"
                    + " de eliminar la actividad seleccionada?");
            if (confirmation)
            {
                  deleteActivity(idActivity);
            }
      }

      private void configureActivitiesTable()
      {
            this.colActivityName.setCellValueFactory(new PropertyValueFactory("name"));
            this.colStartDate.setCellValueFactory(new PropertyValueFactory("startDate"));
            this.colEndDate.setCellValueFactory(new PropertyValueFactory("endDate"));
            this.colStatus.setCellValueFactory(new PropertyValueFactory("statusName"));
            showActivitySelected();
      }

      private void showActivitySelected()
      {
            tvAssignedActivities.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Activity>() {
                      @Override
                      public void changed(ObservableValue<? extends Activity> observable,
                          Activity oldValue, Activity newValue)
                      {
                            if (newValue != null)
                            {
                                  btnShowAssignedActivityDetails.setDisable(false);
                                  btnEditAssignedActivity.setDisable(false);
                                  btnDeleteAssignedActivity.setDisable(false);
                                  int selectedPosition =
                                      tvAssignedActivities.getSelectionModel().getSelectedIndex();
                                  Activity selectedActivity = activities.get(selectedPosition);
                                  idActivity = selectedActivity.getIdActivity();
                            }
                      }
                });
      }

      private void getAssignedActivitiesForTable()
      {
            HashMap<String, Object> answer = ActivityDAO.getAssignedActivities(idDeveloper);
            if (!(boolean) answer.get("error"))
            {
                  activities = FXCollections.observableArrayList();
                  ArrayList<Activity> list = (ArrayList<Activity>) answer.get("activities");
                  activities.addAll(list);
                  tvAssignedActivities.setItems(activities);
            }
            else
            {
                  Utilities.showSimpleAlert(
                      "Error de carga", (String) answer.get("message"), Alert.AlertType.ERROR);
            }
      }

      private void deleteActivity(int idActivity)
      {
            HashMap<String, Object> answer = ActivityDAO.deleteActivity(idActivity);
            if (!(boolean) answer.get("error"))
            {
                  Utilities.showSimpleAlert("Eliminacion exitosa", (String) answer.get("mensaje"),
                      Alert.AlertType.INFORMATION);
                  getAssignedActivitiesForTable();
            }
            else
            {
                  Utilities.showSimpleAlert(
                      "Eliminacion fallida", (String) answer.get("mensaje"), Alert.AlertType.ERROR);
            }
            btnDeleteAssignedActivity.setDisable(true);
            btnEditAssignedActivity.setDisable(true);
      }

      @FXML private void btnShowAssignedActivityDetailsclick(ActionEvent event)
      {
            Activity selectedActivity = tvAssignedActivities.getSelectionModel().getSelectedItem();
            if (selectedActivity != null)
            {
                  int idActivity = selectedActivity.getIdActivity();
                  UserSingleton instance = UserSingleton.getInstace();
                  instance.setIdSelected(idActivity);
                  try
                  {
                        FXMLLoader loader = utilities.loadView("gui/FXMLActivityDetails.fxml");
                        Parent view = loader.load();
                        Scene scene = new Scene(view);
                        Stage stage = new Stage();

                        stage.setScene(scene);
                        stage.setTitle("Detalles del defecto");
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.showAndWait();
                  }
                  catch (java.io.IOException ex)
                  {
                        ex.printStackTrace();
                  }
            }
      }

      @FXML private void btnEditAssignedActivity(ActionEvent event)
      {
            Activity selectedActivity = tvAssignedActivities.getSelectionModel().getSelectedItem();

            if (selectedActivity != null)
            {
                  int idActivity = selectedActivity.getIdActivity();
                  UserSingleton instance = UserSingleton.getInstace();
                  instance.setIdSelected(idActivity);

                  Stage stage = (Stage) apActivitiesLogAnchorPane.getScene().getWindow();
                  Utilities.closeCurrentWindowAndOpenAnotherOne(
                      stage, "/projectmanagementlisof/gui/FXMLEditAssignedActivityDetails.fxml");
            }
      }
}
