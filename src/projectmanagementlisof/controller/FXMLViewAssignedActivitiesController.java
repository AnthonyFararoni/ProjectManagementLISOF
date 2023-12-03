/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package projectmanagementlisof.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author nando
 */
public class FXMLViewAssignedActivitiesController implements Initializable {

    @FXML
    private TableColumn<?, ?> colActivityName;
    @FXML
    private TableColumn<?, ?> colStartDate;
    @FXML
    private TableColumn<?, ?> colEndDate;
    @FXML
    private TableColumn<?, ?> colStatus;
    @FXML
    private TableColumn<?, ?> colOptions;
    @FXML
    private TableView<?> tvAssignedActivities;
    @FXML
    private TextField tfDeveloperId;
    @FXML
    private TextField tdDeveloperName;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        // TODO
    }    

    @FXML
    private void btnReturn(MouseEvent event) 
    {     
        Stage stage = (Stage) tfDeveloperId.getScene().getWindow();
        stage.close();
    }
    
}
