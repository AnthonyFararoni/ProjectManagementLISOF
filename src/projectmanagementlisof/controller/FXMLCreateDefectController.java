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
            utilities.onlyNumbers(tfTimeCost);
            utilities.limitTextFieldCharacters(tfTimeCost, 10);
            utilities.limitTextAreaCharacters(taDescription, 255);
            
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

    @FXML
    private void createDefect(ActionEvent event) 
    {
        if (validarCampos()) 
        {
            HashMap<String, Object> result = registerNewDefect();
            if (!(boolean) result.get("error")) 
            {
                utilities.showSimpleAlert("Nuevo Defecto Creado", "El defecto ha sido creado y almacenado con éxito", Alert.AlertType.INFORMATION);
            } else 
            {
                utilities.showSimpleAlert("Error", "Ha ocurrido un error al crear el defecto. Inténtelo más tarde", Alert.AlertType.ERROR);
            }
            Stage currentStage = (Stage) tfTimeCost.getScene().getWindow();
            utilities.closeWindow(currentStage);
        } 
        else 
        {
            utilities.showSimpleAlert("Error", "Por favor, llene los campos marcados con información válida", Alert.AlertType.WARNING);
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
                tfTimeCost.setStyle("-fx-border-color: red;");
                CamposValidos = false;
            }
            if (taDescription.getText().isEmpty())
            {   
                taDescription.setStyle("-fx-border-color: red;");
                CamposValidos = false;
            }
            if (cbType.getSelectionModel().isEmpty())
            {
                cbType.setStyle("-fx-border-color: red;");
                CamposValidos = false;
            }
            return CamposValidos;
      }

    private HashMap<String, Object> registerNewDefect() {
        Defect defect = new Defect();
        LocalDate date = LocalDate.now();
        defect.setTimeCost(Integer.parseInt(tfTimeCost.getText()));
        defect.setDate(utilities.parseDateToString(date));
        defect.setDescription(taDescription.getText());
        CorrectionType selectedType = cbType.getSelectionModel().getSelectedItem();
        defect.setType(selectedType.getIdType());
        defect.setIdDeveloper(1); // Cambiar al ID correcto del desarrollador
        DefectDAO dao = new DefectDAO();
        return dao.registerDefect(defect);
    }
}
