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
import javafx.scene.Cursor;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import projectmanagementlisof.utils.Utilities;

/**
 * FXML Controller class
 *
 * @author edmun
 */
public class FXMLDeveloperLogController implements Initializable {
    private Utilities utilities = new Utilities();
    @FXML
    private AnchorPane apDeveloperlog;
    @FXML
    private ImageView imgBackButton;
    @FXML
    private TextField tfDeveloperName;
    @FXML
    private TextField tfDeveloperID;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void btnShowActivitiesInLog(ActionEvent event) {
        utilities.loadFXML( "/projectmanagementlisof/gui/FXMLActivitiesInLog.fxml",apDeveloperlog);
    }

    @FXML
    private void btnShowDefectsInLog(ActionEvent event) {
         utilities.loadFXML( "/projectmanagementlisof/gui/FXMLDefectsInLog.fxml",apDeveloperlog);
    }

    @FXML
    private void btnShowChangesInLog(ActionEvent event) {
         utilities.loadFXML( "/projectmanagementlisof/gui/FXMLChangesInLog.fxml",apDeveloperlog);
    }

    @FXML
    private void changeToDefaultCursor(MouseEvent event) {
        imgBackButton.setCursor(Cursor.DEFAULT);
    }

    @FXML
    private void changeToHandCursor(MouseEvent event) {
        imgBackButton.setCursor(Cursor.HAND);
    }

    @FXML
    private void goBackToLanding(MouseEvent event) {
        Stage currentStage = (Stage) tfDeveloperName.getScene().getWindow();
        utilities.cerrarVentana(currentStage);
    }
    
    
}
