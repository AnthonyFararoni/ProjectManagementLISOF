/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package projectmanagementlisof.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author ferdy
 */
public class FXMLUpdateActivityController implements Initializable {

    @FXML
    private TextField tfActivityName;
    @FXML
    private TextField tfAssignDeveloper;
    @FXML
    private DatePicker dpStartDate;
    @FXML
    private DatePicker dpEndDate;
    @FXML
    private TextArea taActivityDescripcion;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void btnReturn(MouseEvent event) {
    }

    @FXML
    private void btnSelectDeveloper(ActionEvent event) {
    }

    @FXML
    private void btnUpdateActivity(ActionEvent event) {
    }
    
}
