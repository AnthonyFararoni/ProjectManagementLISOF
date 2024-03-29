/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this
 * license Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this
 * template
 */
package projectmanagementlisof.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import projectmanagementlisof.model.dao.ChangeDAO;
import projectmanagementlisof.model.pojo.Change;
import projectmanagementlisof.utils.SelectedItemSingleton;
import projectmanagementlisof.utils.Utilities;

/**
 * FXML Controller class
 *
 * @author ferdy
 */
public class FXMLDeveloperChangesOptionController implements Initializable
{
      private Utilities utilities = new Utilities();

      @FXML private TableColumn<Change, String> colType;
      @FXML private TableColumn<Change, String> colDescription;
      @FXML private TableColumn<Change, String> colDateCreated;
      @FXML private TableView<Change> tvDeveloperChanges;
    @FXML
    private Button btnChangeDetails;
      /**
       * Initializes the controller class.
       */
      @Override public void initialize(URL url, ResourceBundle rb)
      {
            colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
            colDateCreated.setCellValueFactory(new PropertyValueFactory<>("dateCreated"));
            colType.setCellValueFactory(new PropertyValueFactory<>("typeName"));
            fillAssignedActivitiesToDeveloper();
            
            btnChangeDetails.setDisable(true);
            tvDeveloperChanges.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                      if (newSelection != null)
                      {
                            btnChangeDetails.setDisable(false);
                      }
                      else
                      {
                            btnChangeDetails.setDisable(true);
                      }
                });
            
      }

      @FXML private void btnViewDetails(ActionEvent event) {
            Change selectedChange = tvDeveloperChanges.getSelectionModel().getSelectedItem();
            if (selectedChange != null)
            {
                  int idChange = selectedChange.getIdChange();
                  Utilities.showDetails(
                      idChange, "gui/FXMLChangesDetails.fxml", "Detalles del cambio");
            }
      }

      @FXML private void btnRegisterChange(ActionEvent event)
      {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Utilities.<FXMLChangeRequestDetailsController>closeCurrentWindowAndOpenAnotherOne(
                "/projectmanagementlisof/gui/FXMLRegisterChange.fxml", stage, event);
      }

      private void fillAssignedActivitiesToDeveloper()
      {
            HashMap<String, Object> result = ChangeDAO.getChangesByDeveloperId(1);
            if (!(boolean) result.get("error"))
            {
                  ArrayList<Change> activities = (ArrayList<Change>) result.get("changes");
                  ObservableList<Change> observableChanges =
                      FXCollections.observableArrayList(activities);
                  tvDeveloperChanges.setItems(observableChanges);
            }
            else
            {
                  String errorMessage = (String) result.get("message");
                  showAlert(
                      Alert.AlertType.ERROR, "Error", "Error al obtener cambios", errorMessage);
            }
      }

      private void showAlert(
          Alert.AlertType alertType, String title, String headerText, String contentText)
      {
            Alert alert = new Alert(alertType);
            alert.setTitle(title);
            alert.setHeaderText(headerText);
            alert.setContentText(contentText);
            alert.showAndWait();
      }
}
