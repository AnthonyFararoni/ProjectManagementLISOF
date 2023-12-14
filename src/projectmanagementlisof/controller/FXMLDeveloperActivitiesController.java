/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package projectmanagementlisof.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import projectmanagementlisof.model.dao.ActivityDAO;
import projectmanagementlisof.model.pojo.Activity;
import projectmanagementlisof.utils.UserSingleton;
import projectmanagementlisof.utils.Utilities;

/**
 * FXML Controller class
 *
 * @author edmun
 */
public class FXMLDeveloperActivitiesController implements Initializable {
    private Utilities utilities = new Utilities();
    @FXML
    private TextField tfSearchActivity;
    @FXML
    private TableView<Activity> tvDeveloperActivities;
    @FXML
    private TableColumn<Activity, String> colActivityName;
    @FXML
    private TableColumn<Activity, String> colStartDate;
    @FXML
    private TableColumn<Activity, String> colEndDate;
    @FXML
    private TableColumn<Activity, String> colStatus;
    @FXML
    private Button btnViewDetails;
    @FXML
    private Button btnEndActivity;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colActivityName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colStartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        colEndDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        colStatus.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Activity, String>, ObservableValue<String>>() {
        @Override
        public ObservableValue<String> call(TableColumn.CellDataFeatures<Activity, String> param) {
            return new SimpleStringProperty(convertStatusToString(param.getValue().getStatus()));
        }
    });
        fillAssignedActivitiesToDeveloper();
        
        btnViewDetails.setDisable(true);    
        btnEndActivity.setDisable(true);    
        tvDeveloperActivities.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                btnViewDetails.setDisable(false);
                if (newValue.getStatus() == 3) {
                    btnEndActivity.setDisable(true);
                } else {
                    btnEndActivity.setDisable(false);
                }
            } else {
                btnViewDetails.setDisable(true);
                btnEndActivity.setDisable(true);
            }
        });
    }    
    private String convertStatusToString(int status) {
        switch (status) {
            case 1:
                return "No asignada";
            case 2:
                return "Pendiente";
            case 3:
                return "Concluida";
            default:
                return "Desconocido";
        }
    }
    @FXML
    private void btnSearchActivity(MouseEvent event) {
    }

    @FXML
    private void btnViewDetailsClick(ActionEvent event) 
    {
        Activity selectedActivity = tvDeveloperActivities.getSelectionModel().getSelectedItem();
        if (selectedActivity != null) {
            int idActivity = selectedActivity.getIdActivity();
            UserSingleton instance = UserSingleton.getInstace();
            instance.setIdSelected(idActivity);
        } 
        try {
            FXMLLoader loader = utilities.loadView("gui/FXMLActivityDetails.fxml");
            Parent view = loader.load();
            Scene scene = new Scene(view);
            FXMLActivityDetailsController controller = loader.getController();
            Stage stage = new Stage();

            stage.setScene(scene);
            stage.setTitle("Crear defecto");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    private void fillAssignedActivitiesToDeveloper() {
        HashMap<String, Object> result = ActivityDAO.getAssignedActivities(1);
        if (!(boolean) result.get("error")) {
            ArrayList<Activity> activities = (ArrayList<Activity>) result.get("activities");
            ObservableList<Activity> observableActivities = FXCollections.observableArrayList(activities);
            tvDeveloperActivities.setItems(observableActivities);
        } else {
            String errorMessage = (String) result.get("message");
            showAlert(Alert.AlertType.ERROR, "Error", "Error al obtener actividades", errorMessage);
        }
    }
    private void showAlert(Alert.AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    @FXML
    private void btnEndActivity(ActionEvent event) {
        Activity selectedActivity = tvDeveloperActivities.getSelectionModel().getSelectedItem();
        if (selectedActivity != null) {
            int idActivity = selectedActivity.getIdActivity();

            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirmación");
            confirmationAlert.setHeaderText("¿Estás seguro de cambiar el estado de la actividad?");
            confirmationAlert.setContentText("Esta acción cambiará el estado de la actividad. ¿Deseas continuar?");
            Optional<ButtonType> result = confirmationAlert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                HashMap<String, Object> changeResult = ActivityDAO.changeActivityStatus(idActivity);

                if (!(boolean) changeResult.get("error")) {
                    showAlert(Alert.AlertType.INFORMATION, "Éxito", "Estado de la actividad cambiado",
                            "El estado de la actividad se cambió correctamente.");
                    fillAssignedActivitiesToDeveloper();
                } else {
                    String errorMessage = (String) changeResult.get("message");
                    showAlert(Alert.AlertType.ERROR, "Error", "Error al cambiar el estado de la actividad", errorMessage);
                }
            }
        }
    }
    
}
