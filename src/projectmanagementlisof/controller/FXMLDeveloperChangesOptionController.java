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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import projectmanagementlisof.model.dao.ChangeDAO;
import projectmanagementlisof.model.pojo.Change;
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

      /**
       * Initializes the controller class.
       */
      @Override public void initialize(URL url, ResourceBundle rb)
      {
            colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
            colDateCreated.setCellValueFactory(new PropertyValueFactory<>("dateCreated"));
            colType.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Change, String>,
                ObservableValue<String>>() {
                  @Override
                  public ObservableValue<String> call(
                      TableColumn.CellDataFeatures<Change, String> param)
                  {
                        return new SimpleStringProperty(
                            convertTypeToString(param.getValue().getType()));
                  }
            });
            fillAssignedActivitiesToDeveloper();
      }

      @FXML private void btnViewDetails(ActionEvent event) {}

      @FXML private void btnRegisterChange(ActionEvent event)
      {
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

      private String convertTypeToString(int type)
      {
            switch (type)
            {
                  case 1:
                        return "JavaScript";
                  case 2:
                        return "Base de datos";
                  case 3:
                        return "Interfaz";
                  case 4:
                        return "Codigo";
                  default:
                        return "Otros";
            }
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
