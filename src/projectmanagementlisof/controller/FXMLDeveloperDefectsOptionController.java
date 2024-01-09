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
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import projectmanagementlisof.model.dao.DefectDAO;
import projectmanagementlisof.model.pojo.Defect;
import projectmanagementlisof.utils.SelectedItemSingleton;
import projectmanagementlisof.utils.Utilities;
/**
 * FXML Controller class
 *
 * @author edmun
 */
public class FXMLDeveloperDefectsOptionController implements Initializable
{
      private Utilities utilities = new Utilities();
      private DefectDAO defectDAO = new DefectDAO();
      @FXML private TableView<Defect> tvDeveloperDefects;
      @FXML private TableColumn<Defect, Integer> colDefectID;
      @FXML private TableColumn<Defect, String> colTimeCost;
      @FXML private TableColumn<Defect, String> colFoundDate;
      @FXML private TableColumn<Defect, String> colDescription;
      @FXML private Button btnViewDetails;
    @FXML
    private TextField tfSeachDefect;
    private ArrayList<Defect> defects;
      /**
       * Initializes the controller class.
       */
      @Override public void initialize(URL url, ResourceBundle rb)
      {
            colDefectID.setCellValueFactory(
                data -> new SimpleIntegerProperty(data.getValue().getIdDefect()).asObject());
            colTimeCost.setCellValueFactory(
                data -> new SimpleStringProperty(String.valueOf(data.getValue().getTimeCost())));
            colFoundDate.setCellValueFactory(
                data -> new SimpleStringProperty(data.getValue().getDate()));
            colDescription.setCellValueFactory(
                data -> new SimpleStringProperty(data.getValue().getDescription()));

            loadDeveloperDefects();
            btnViewDetails.setDisable(true);

            tvDeveloperDefects.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                      if (newValue != null)
                      {
                            btnViewDetails.setDisable(false);
                      }
                      else
                      {
                            btnViewDetails.setDisable(true);
                      }
                });
      }

      @FXML private void btnRegisterDefect(ActionEvent event)
      {
            try
            {
                  FXMLLoader loader = utilities.loadView("gui/FXMLCreateDefect.fxml");
                  Parent view = loader.load();
                  Scene scene = new Scene(view);
                  FXMLCreateDefectController controller = loader.getController();
                  Stage stage = new Stage();

                  stage.setScene(scene);
                  stage.setTitle("Crear defecto");
                  stage.initModality(Modality.APPLICATION_MODAL);
                  stage.showAndWait();
                  loadDeveloperDefects();
            }
            catch (IOException ex)
            {
                  ex.printStackTrace();
            }
      }
      private void loadDeveloperDefects()
      {
            int idDeveloper = 1;
            HashMap<String, Object> result = DefectDAO.getDefectsById(idDeveloper);

            if (!(boolean) result.get("error"))
            {
                  defects = (ArrayList<Defect>) result.get("defects");
                  tvDeveloperDefects.getItems().setAll(defects);
            }
            else
            {
                  String errorMessage = (String) result.get("message");
            }
      }

      @FXML private void btnViewDetailsclick(ActionEvent event)
      {
            Defect selectedDefect = tvDeveloperDefects.getSelectionModel().getSelectedItem();
            if (selectedDefect != null)
            {
                  int idDefect = selectedDefect.getIdDefect();
                  SelectedItemSingleton instance = SelectedItemSingleton.getInstance();
                  instance.setIdSelected(idDefect);

                  try
                  {
                        FXMLLoader loader = utilities.loadView("gui/FXMLDefectDetails.fxml");
                        Parent view = loader.load();
                        Scene scene = new Scene(view);
                        Stage stage = new Stage();

                        stage.setScene(scene);
                        stage.setTitle("Detalles del defecto");
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.showAndWait();
                  }
                  catch (IOException ex)
                  {
                        ex.printStackTrace();
                  }
            }
      }

    @FXML
    private void searchDefect(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            String filter = tfSeachDefect.getText().toLowerCase().trim();
            if (filter.isEmpty()) {
                tvDeveloperDefects.getItems().setAll(defects);
            } else {
                ObservableList<Defect> filteredList = FXCollections.observableArrayList();

                for (Defect defect : defects) {
                    if (String.valueOf(defect.getIdDefect()).toLowerCase().contains(filter) ||
                        String.valueOf(defect.getTimeCost()).contains(filter) ||
                        defect.getDate().toLowerCase().contains(filter) ||
                        defect.getDescription().toLowerCase().contains(filter)) {

                        filteredList.add(defect);
                    }
                }
                tvDeveloperDefects.setItems(filteredList);
            }
        }
    }
}
