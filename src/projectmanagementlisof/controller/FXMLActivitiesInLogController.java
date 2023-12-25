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
import java.util.logging.Level;
import java.util.logging.Logger;
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
import projectmanagementlisof.observer.DeveloperObserver;
import projectmanagementlisof.utils.SelectedItemSingleton;
import projectmanagementlisof.utils.Utilities;

/**
 * FXML Controller class
 *
 * @author edmun
 */
public class FXMLActivitiesInLogController implements Initializable, DeveloperObserver
{
      private ObservableList<Activity> activities;
      @FXML private AnchorPane apActivitiesLogAnchorPane;
      private int idDeveloper;
      private int idActivity;
      private Utilities utilities = new Utilities();

      private Integer selectedDeveloperId;
      private String selectedDeveloperName;

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

      @FXML private void btnShowAssignedActivityDetailsclick(ActionEvent event)
      {
            Activity selectedActivity = tvAssignedActivities.getSelectionModel().getSelectedItem();
            if (selectedActivity != null)
            {
                  int idActivity = selectedActivity.getIdActivity();
                  Utilities.showDetails(
                      idActivity, "gui/FXMLActivityDetails.fxml", "Detalles de la actividad");
            }
      }

      @Override public void developerSelected(Integer id, String name)
      {
            this.selectedDeveloperId = id;
            this.selectedDeveloperName = name;
      }

      public void receiveData()
      {
            SelectedItemSingleton instance = SelectedItemSingleton.getInstace();
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

      @FXML private void btnEditAssignedActivity(ActionEvent event)
      {
            try
            {
                  FXMLLoader loader =
                      Utilities.loadView("gui/FXMLEditAssignedActivityDetails.fxml");
                  Parent view = loader.load();
                  Scene scene = new Scene(view);
                  FXMLEditAssignedActivityDetailsController controller = loader.getController();
                  Activity selectedActivity =
                      tvAssignedActivities.getSelectionModel().getSelectedItem();
                  controller.initializeInformation(idActivity, this, selectedActivity);
                  Stage stage = new Stage();

                  stage.setScene(scene);
                  stage.setTitle("Editar Actividad Asignada");
                  stage.initModality(Modality.APPLICATION_MODAL);
                  stage.showAndWait();
            }
            catch (Exception ex)
            {
                  Logger.getLogger(FXMLActivitiesOptionController.class.getName())
                      .log(Level.SEVERE, null, ex);
            }
      }
}
