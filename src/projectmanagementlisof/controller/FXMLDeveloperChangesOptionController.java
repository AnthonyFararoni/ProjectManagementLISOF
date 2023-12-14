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
import javafx.scene.control.TableColumn;
import javafx.stage.Modality;
import javafx.stage.Stage;
import projectmanagementlisof.utils.Utilities;

/**
 * FXML Controller class
 *
 * @author ferdy
 */
public class FXMLDeveloperChangesOptionController implements Initializable {
    private Utilities utilities = new Utilities();
    
    @FXML
    private TableColumn<?, ?> colType;
    @FXML
    private TableColumn<?, ?> colDescription;
    @FXML
    private TableColumn<?, ?> colDateCreated;

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
    private void btnRegisterChange(ActionEvent event) {
     try
        {
            FXMLLoader loader = utilities.loadView("gui/FXMLRegisterChange.fxml");
            Parent view = loader.load();
            Scene scene = new Scene(view);
            FXMLRegisterChangeController controller = loader.getController();
            Stage stage = new Stage();

            stage.setScene(scene);
            stage.setTitle("Registrar cambio");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();            
        } 
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    
    }
    
}
