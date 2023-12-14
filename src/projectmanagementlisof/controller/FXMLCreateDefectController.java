/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this
 * license Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this
 * template
 */
package projectmanagementlisof.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import projectmanagementlisof.model.dao.CatalogDAO;
import projectmanagementlisof.model.dao.DefectDAO;
import projectmanagementlisof.model.pojo.CorrectionType;
import projectmanagementlisof.model.pojo.Defect;
import projectmanagementlisof.utils.Utilities;

/**
 * FXML Controller class
 *
 * @author edmun
 */
public class FXMLCreateDefectController implements Initializable
{
      private Utilities utilities = new Utilities();
      private ObservableList<CorrectionType> types = FXCollections.observableArrayList();
      @FXML private ImageView imgBackButton;
      @FXML private TextField tfTimeCost;
      @FXML private ComboBox<CorrectionType> cbType;
      @FXML private TextArea taDescription;

      /**
       * Initializes the controller class.
       */
      @Override public void initialize(URL url, ResourceBundle rb)
      {
            setTypesInComboBox();
      }

      @FXML private void changeToDefaultCursor(MouseEvent event)
      {
            imgBackButton.setCursor(Cursor.DEFAULT);
      }

      @FXML private void changeToHandCursor(MouseEvent event)
      {
            imgBackButton.setCursor(Cursor.HAND);
      }

      @FXML private void goBackToLanding(MouseEvent event)
      {
            Stage currentStage = (Stage) tfTimeCost.getScene().getWindow();
            utilities.closeWindow(currentStage);
      }

      @FXML private void createDefect(ActionEvent event)
      {
            if (validarCampos())
            {
                  int saved = registerNewDefect();
                  utilities.showSimpleAlert("Nuevo Defecto Creado",
                      "El defecto a sido creado y almacenado con éxito",
                      Alert.AlertType.CONFIRMATION);
                  Stage currentStage = (Stage) tfTimeCost.getScene().getWindow();
                  utilities.closeWindow(currentStage);
            }
      }

      private void setTypesInComboBox()
      {
            List<CorrectionType> result = CatalogDAO.getTypes();
            types.addAll(result);
            cbType.setItems(types);
      }

      public boolean validarCampos()
      {
            boolean CamposValidos = true;
            if (tfTimeCost.getText().isEmpty())
            {
                  CamposValidos = false;
            }
            if (taDescription.getText().isEmpty())
            {
                  CamposValidos = false;
            }
            if (cbType.getSelectionModel().isEmpty())
            {
                  CamposValidos = false;
            }
            return CamposValidos;
      }

      private int registerNewDefect()
      {
            Defect defect = new Defect();
            LocalDate date = LocalDate.now();
            defect.setTimeCost(Integer.parseInt(tfTimeCost.getText()));
            System.out.println(utilities.parseDateToString(date));
            defect.setDate(utilities.parseDateToString(date));
            defect.setDescription(taDescription.getText());
            CorrectionType selectedType = cbType.getSelectionModel().getSelectedItem();
            defect.setType(selectedType.getIdType());
            defect.setIdDeveloper(1);
            DefectDAO dao = new DefectDAO();
            int result = dao.registerDefect(defect);
            System.out.println(result);
            return result;
      }
}
