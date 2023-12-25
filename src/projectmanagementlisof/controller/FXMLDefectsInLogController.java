/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this
 * license Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this
 * template
 */
package projectmanagementlisof.controller;

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
import javafx.stage.Modality;
import javafx.stage.Stage;
import projectmanagementlisof.model.dao.DefectDAO;
import projectmanagementlisof.model.pojo.Activity;
import projectmanagementlisof.model.pojo.Defect;
import projectmanagementlisof.utils.SelectedItemSingleton;
import projectmanagementlisof.utils.Utilities;

/**
 * FXML Controller class
 *
 * @author edmun
 */
public class FXMLDefectsInLogController implements Initializable
{
      @FXML private TableView<Defect> tvLogDefects;
      @FXML private TableColumn<Defect, Integer> colDefectID;
      @FXML private TableColumn<Defect, Integer> colTimeCost;
      @FXML private TableColumn<Defect, String> colDateFound;
      @FXML private TableColumn<Defect, String> colDescription;
      @FXML private Button btnViewDetails;

      private Utilities utilities = new Utilities();
      private ObservableList<Defect> defects;
      private int idDeveloper;
      /**
       * Initializes the controller class.
       */
      @Override public void initialize(URL url, ResourceBundle rb)
      {
            receiveData();
            configureDefectsTable();
            getDefectsForTable();

            btnViewDetails.setDisable(true);
            tvLogDefects.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                      if (newSelection != null)
                      {
                            btnViewDetails.setDisable(false);
                      }
                      else
                      {
                            btnViewDetails.setDisable(true);
                      }
                });
      }
      public void receiveData()
      {
            SelectedItemSingleton instance = SelectedItemSingleton.getInstance();
            idDeveloper = instance.getIdSelected();
      }
      private void configureDefectsTable()
      {
            colDefectID.setCellValueFactory(
                data -> new SimpleIntegerProperty(data.getValue().getIdDefect()).asObject());
            colTimeCost.setCellValueFactory(cellData
                -> new SimpleIntegerProperty(cellData.getValue().getTimeCost()).asObject());
            colDateFound.setCellValueFactory(
                data -> new SimpleStringProperty(data.getValue().getDate()));
            colDescription.setCellValueFactory(
                data -> new SimpleStringProperty(data.getValue().getDescription()));
      }

      private void getDefectsForTable()
      {
            HashMap<String, Object> answer = DefectDAO.getDefectsById(1);

            if (!(boolean) answer.get("error"))
            {
                  defects = FXCollections.observableArrayList();
                  ArrayList<Defect> list = (ArrayList<Defect>) answer.get("defects");
                  defects.addAll(list);
                  tvLogDefects.setItems(defects);
            }
            else
            {
                  System.out.println("Error: " + answer.get("message"));
            }
      }
      @FXML private void btnViewDetailsClick(ActionEvent event)
      {
            Defect selectedDefect = tvLogDefects.getSelectionModel().getSelectedItem();
            if (selectedDefect != null)
            {
                  int idDefect = selectedDefect.getIdDefect();
                  Utilities.showDetails(
                      idDefect, "gui/FXMLDefectDetails.fxml", "Detalles del defecto");
            }
      }
}
