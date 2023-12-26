/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this
 * license Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this
 * template
 */
package projectmanagementlisof.controller;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import projectmanagementlisof.model.dao.ChangeRequestDAO;
import projectmanagementlisof.model.pojo.ChangeRequest;
import projectmanagementlisof.model.pojo.ChangeRequestStatus;
import projectmanagementlisof.utils.Utilities;

/**
 * FXML Controller class
 *
 * @author ferdy
 */
public class FXMLModifyRequestStatusController implements Initializable
{
      private Utilities utilities = new Utilities();
      private ObservableList<ChangeRequestStatus> statuses = FXCollections.observableArrayList();

      @FXML private TextField tfJustification;
      @FXML private TextField tfDeveloper;
      @FXML private TextField tfDateRequested;
      @FXML private TextArea taChangeDescription;
      @FXML private ComboBox<ChangeRequestStatus> cbStatus;

      /**
       * Initializes the controller class.
       */
      @Override public void initialize(URL url, ResourceBundle rb)
      {
            utilities.setItemsInComboBox(statuses, cbStatus);
      }

      @FXML private void btnSaveChange(ActionEvent event)
      {
            registerChange();
      }

      private void registerChange()
      {
            ChangeRequest changeRequest = new ChangeRequest();
            ChangeRequestStatus status = cbStatus.getSelectionModel().getSelectedItem();
            changeRequest.setIdStatus(status.getIdChangeRequestStatus());

            HashMap<String, Object> answer =
                ChangeRequestDAO.updateChangeRequestStatus(changeRequest);
            if (!(boolean) answer.get("error"))
            {
                  Utilities.showSimpleAlert("Cambio Registrado", (String) answer.get("message"),
                      Alert.AlertType.INFORMATION);
            }
            else
            {
                  Utilities.showSimpleAlert(
                      "Error al guardar", (String) answer.get("message"), Alert.AlertType.ERROR);
            }
      }
}
