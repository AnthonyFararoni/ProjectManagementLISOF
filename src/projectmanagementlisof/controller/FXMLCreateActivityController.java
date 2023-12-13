/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package projectmanagementlisof.controller;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import projectmanagementlisof.model.dao.ActivityDAO;
import projectmanagementlisof.model.pojo.Activity;
import projectmanagementlisof.observer.DeveloperObserver;
import projectmanagementlisof.utils.Utilities;

/**
 * FXML Controller class
 *
 * @author ferdy
 */
public class FXMLCreateActivityController implements DeveloperObserver, Initializable {
    
    private Integer idDeveloper;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void btnSelectDeveloper(ActionEvent event) {
    }

    @FXML
    private void btnCreateActivity(ActionEvent event) {
        registerActivity();
    }
    
    private void registerActivity(){
        Activity activity = new Activity();
        activity.setName(tfActivityName.getText());
        activity.setDescription(taActivityDescription.getText());
        activity.setStartDate(dpStartDate.getValue().toString());
        activity.setEndDate(dpEndDate.getValue().toString());
        if(!tfAssignDeveloper.getText().isEmpty()){
            activity.setIdDeveloper(idDeveloper);
        }
        HashMap<String, Object> answer = ActivityDAO.registerActivity(activity);
        if(!(boolean) answer.get("error")){
            Utilities.showSimpleAlert("Actividad Guardada", (String)answer.get("message"),
                    Alert.AlertType.INFORMATION);
        }else{
            Utilities.showSimpleAlert("Error al guardar", (String)answer.get("message"),
                    Alert.AlertType.ERROR);
        }
    }   

    @Override
    public void developerSelected(Integer idDeveloper) {
        this.idDeveloper = idDeveloper;
    }
    
}
