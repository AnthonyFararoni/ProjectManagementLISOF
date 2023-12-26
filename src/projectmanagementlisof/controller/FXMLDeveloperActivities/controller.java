/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package projectmanagementlisof.controller.FXMLDeveloperActivities;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author edmun
 */
public class controller implements Initializable {

    @FXML
    private TextField tfSearchActivity;
    @FXML
    private TableView<?> tvDeveloperActivities;
    @FXML
    private TableColumn<?, ?> colActivityName;
    @FXML
    private TableColumn<?, ?> colStartDate;
    @FXML
    private TableColumn<?, ?> colEndDate;
    @FXML
    private TableColumn<?, ?> colStatus;
    @FXML
    private Button btnViewDetails;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void btnSearchActivity(MouseEvent event) {
    }

    @FXML
    private void btnViewDetailsClick(ActionEvent event) {
    }
    
}
