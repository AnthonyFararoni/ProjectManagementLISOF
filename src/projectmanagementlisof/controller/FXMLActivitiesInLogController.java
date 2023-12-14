/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package projectmanagementlisof.controller;

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
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import projectmanagementlisof.model.dao.ActivityDAO;
import projectmanagementlisof.model.dao.DeveloperDAO;
import projectmanagementlisof.model.pojo.Activity;
import projectmanagementlisof.model.pojo.Developer;
import projectmanagementlisof.utils.Utilities;
import projectmanagementlisof.controller.FXMLDeveloperLogController;

/**
 * FXML Controller class
 *
 * @author edmun
 */
public class FXMLActivitiesInLogController implements Initializable {

    private ObservableList<Activity> activities;
    private int idDeveloper;
    private int idActivity;
    
    @FXML
    private TableView<Activity> tvAssignedActivities;
    @FXML
    private TableColumn colActivityName;
    @FXML
    private TableColumn colStartDate;
    @FXML
    private TableColumn colEndDate;
    @FXML
    private TableColumn colStatus;
    @FXML
    private Button btnShowAssignedActivityDetails;
    @FXML
    private Button btnEditAssignedActivity;
    @FXML
    private Button btnDeleteAssignedActivity;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        configureActivitiesTable();        
        getAssignedActivitiesForTable();

        //getAssignedActivitiesForTable(sharedControllerIdDeveloper.getIdDeveloper());;
        
    }
    

    @FXML
    private void btnShowAssignedActivityDetails(ActionEvent event) {
    }

    @FXML
    private void btnEditAssignedActivity(ActionEvent event) {
    }

    @FXML
    private void btnDeleteAssignedActivity(ActionEvent event) {
    }
    
    private void configureActivitiesTable(){
        this.colActivityName.setCellValueFactory(new PropertyValueFactory("name"));
        this.colStartDate.setCellValueFactory(new PropertyValueFactory("startDate"));
        this.colEndDate.setCellValueFactory(new PropertyValueFactory("endDate"));
        this.colStatus.setCellValueFactory(new PropertyValueFactory("statusName"));
        showActivitySelected();
    }
    
    private void showActivitySelected(){
        tvAssignedActivities.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Activity>() {
            @Override
            public void changed(ObservableValue<? extends Activity> observable, Activity oldValue, 
                    Activity newValue) {
                if(newValue != null){
                    btnShowAssignedActivityDetails.setDisable(false);
                    btnEditAssignedActivity.setDisable(false);
                    btnDeleteAssignedActivity.setDisable(false);
                    int selectedPosition = tvAssignedActivities.getSelectionModel().getSelectedIndex();
                    Activity selectedActivity = activities.get(selectedPosition);
                    idActivity = selectedActivity.getIdActivity();                    
                }
            }
        });
    }
 
    private void getAssignedActivitiesForTable()
    {
        HashMap<String, Object> answer = ActivityDAO.getAssignedActivities(idDeveloper);
        if(!(boolean)answer.get("error")){
            activities = FXCollections.observableArrayList();
            ArrayList<Activity> list = (ArrayList<Activity>) answer.get("activities");
            activities.addAll(list);
            tvAssignedActivities.setItems(activities);           
        }else{
            Utilities.showSimpleAlert("Error de carga", (String)answer.get("message"), 
                    Alert.AlertType.ERROR);
        }        
    }
    
}
