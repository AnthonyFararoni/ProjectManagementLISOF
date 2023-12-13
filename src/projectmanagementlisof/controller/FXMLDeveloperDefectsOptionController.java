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
import projectmanagementlisof.utils.Utilities;

/**
 * FXML Controller class
 *
 * @author edmun
 */
public class FXMLDeveloperDefectsOptionController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private Utilities utilities = new Utilities();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void btnViewDetails(ActionEvent event) {
    }

    @FXML
    private void btnRegisterDefect(ActionEvent event) {
        try
        {
            FXMLLoader loader = utilities.loadView("gui/FXMLCreateDefect.fxml");
            Parent view = loader.load();
            Scene scene = new Scene(view);
            FXMLCreateDefectController controller = loader.getController();
            Stage stage = new Stage();
                        
            stage.setScene(scene);
            stage.setTitle("Crear defecto");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();            
        } 
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
        
    }
    
}
