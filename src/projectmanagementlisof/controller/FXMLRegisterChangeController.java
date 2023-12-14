/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package projectmanagementlisof.controller;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import projectmanagementlisof.model.dao.ChangeDAO;
import projectmanagementlisof.model.pojo.Change;
import projectmanagementlisof.utils.Utilities;

/**
 * FXML Controller class
 *
 * @author ferdy
 */
public class FXMLRegisterChangeController implements Initializable {

    @FXML
    private TextArea taDescription;
    @FXML
    private ComboBox cbChangeType;

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
    private void btnRegisterChange(ActionEvent event) {
        registerChange();
    }
    
    private void registerChange(){
        Change change = new Change();
        change.setDescription(taDescription.getText());
        Type type = cbType.getSelectionModel().getSelectedItem();
        change.setType(type.getType());
        
        
        HashMap<String, Object> answer = ChangeDAO.registerChange(change);
        if(!(boolean) answer.get("error")){
            Utilities.showSimpleAlert("Cambio Registrado", (String)answer.get("message"),
                    Alert.AlertType.INFORMATION);
        }else{
            Utilities.showSimpleAlert("Error al guardar", (String)answer.get("message"),
                    Alert.AlertType.ERROR);
        }
    }   
    
}
