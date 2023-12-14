/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package projectmanagementlisof.controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import static java.time.temporal.TemporalQueries.localDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import projectmanagementlisof.model.dao.CatalogDAO;
import projectmanagementlisof.model.dao.ChangeDAO;
import projectmanagementlisof.model.pojo.Change;
import projectmanagementlisof.model.pojo.CorrectionType;
import projectmanagementlisof.utils.Utilities;

/**
 * FXML Controller class
 *
 * @author ferdy
 */
public class FXMLRegisterChangeController implements Initializable {

    private Utilities utilities = new Utilities();
    private ObservableList<CorrectionType> types = FXCollections.observableArrayList();
    
    @FXML
    private TextArea taDescription;
    @FXML
    private ComboBox<CorrectionType> cbChangeType;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        utilities.setItemsInComboBox(types, cbChangeType);
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
        CorrectionType type = cbChangeType.getSelectionModel().getSelectedItem();
        change.setType(type.getIdType());
        change.setIdDeveloper(1);
        LocalDate date = LocalDate.now();
        Date fechaComoDate = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
        change.setDateCreated(date.toString());
        System.out.println(date);
                
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
