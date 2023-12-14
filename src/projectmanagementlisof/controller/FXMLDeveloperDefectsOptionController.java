/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package projectmanagementlisof.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import projectmanagementlisof.ProjectManagementLISOF;
import projectmanagementlisof.utils.Utilities;
/**
 * FXML Controller class
 *
 * @author edmun
 */
public class FXMLDeveloperDefectsOptionController implements Initializable {

    private Utilities utilities = new Utilities();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void btnViewDetails(ActionEvent event) {
    }

    @FXML
    private void btnRegisterDefect(ActionEvent event) throws IOException 
    {
         ProjectManagementLISOF.setRoot("/projectmanagementlisof/gui/FXMLCreateDefect");
    }
    
}
