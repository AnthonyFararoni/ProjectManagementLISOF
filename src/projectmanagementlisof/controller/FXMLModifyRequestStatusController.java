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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author ferdy
 */
public class FXMLModifyRequestStatusController implements Initializable {

    @FXML
    private TextField tfJustification;
    @FXML
    private TextField tfDeveloper;
    @FXML
    private TextField tfDateRequested;
    @FXML
    private TextArea taChangeDescription;
    @FXML
    private ComboBox<?> cbStatus;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void btnSaveChange(ActionEvent event) {
    }
    
}
