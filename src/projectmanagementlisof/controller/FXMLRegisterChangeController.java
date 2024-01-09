/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this
 * license Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this
 * template
 */
package projectmanagementlisof.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import projectmanagementlisof.model.dao.ChangeDAO;
import projectmanagementlisof.model.pojo.Change;
import projectmanagementlisof.model.pojo.CorrectionType;
import projectmanagementlisof.utils.Utilities;

/**
 * FXML Controller class
 *
 * @author ferdy
 */
public class FXMLRegisterChangeController implements Initializable
{
      private Utilities utilities = new Utilities();
      private ObservableList<CorrectionType> types = FXCollections.observableArrayList();

      @FXML private TextArea taDescription;
      @FXML private ComboBox<CorrectionType> cbChangeType;
      @FXML private ImageView btnReturn;
      @FXML private Button btnRegisterChange;

      /**
       * Initializes the controller class.
       */
      @Override public void initialize(URL url, ResourceBundle rb)
      {
            System.out.println(types);
            utilities.setItemsInComboBox(types, cbChangeType);
            btnRegisterChange.setDisable(true);
            taDescription.textProperty().addListener(
                (observable, oldValue, newValue) -> checkEnableButton());
            cbChangeType.valueProperty().addListener(
                (observable, oldValue, newValue) -> checkEnableButton());
      }

      @FXML private void btnReturn(MouseEvent event)
      {
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Utilities.closeWindow(currentStage);

            Utilities.loadFXMLInAnchorPaneAndCloseCurrentForDeveloper(currentStage,
                "/projectmanagementlisof/gui/FXMLDeveloperLanding.fxml",
                "/projectmanagementlisof/gui/FXMLDeveloperChangesOption.fxml");
      }

      @FXML private void btnRegisterChange(ActionEvent event)
      {
            registerChange();
      }

      private void registerChange()
      {
            Change change = new Change();
            change.setDescription(taDescription.getText());
            CorrectionType type = cbChangeType.getSelectionModel().getSelectedItem();
            change.setType(type.getIdType());
            change.setIdDeveloper(1);
            LocalDate date = LocalDate.now();
            change.setDateCreated(date.toString());

            HashMap<String, Object> answer = ChangeDAO.registerChange(change);
            if (!(boolean) answer.get("error"))
            {
                  Utilities.showSimpleAlert("Cambio Registrado", (String) answer.get("message"),
                      Alert.AlertType.INFORMATION);
                  taDescription.clear();
                  cbChangeType.setValue(null);
            }
            else
            {
                  Utilities.showSimpleAlert(
                      "Error al guardar", (String) answer.get("message"), Alert.AlertType.ERROR);
            }
      }

      private void checkEnableButton()
      {
            boolean allFieldsFilled =
                !taDescription.getText().isEmpty() && cbChangeType.getValue() != null;
            btnRegisterChange.setDisable(!allFieldsFilled);
      }

      @FXML private void changeToDefaultCursor(MouseEvent event)
      {
            btnReturn.setCursor(Cursor.DEFAULT);
      }

      @FXML private void changeToHandCursor(MouseEvent event)
      {
            btnReturn.setCursor(Cursor.HAND);
      }
}
