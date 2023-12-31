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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import projectmanagementlisof.model.dao.ChangeDAO;
import projectmanagementlisof.model.pojo.Activity;
import projectmanagementlisof.model.pojo.Change;
import projectmanagementlisof.utils.SelectedItemSingleton;
import projectmanagementlisof.utils.Utilities;

/**
 * FXML Controller class
 *
 * @author edmun
 */
public class FXMLChangesInLogController implements Initializable
{
      @FXML private TableView<Change> tvLogChanges;
      @FXML private TableColumn<Change, Integer> colType;
      @FXML private TableColumn<Change, String> colDescription;
      @FXML private TableColumn<Change, String> colDate;
      @FXML private Button btnDetails;
      private ObservableList<Change> changes;
      private int idDeveloper;
      private Utilities utilities = new Utilities();
      /**
       * Initializes the controller class.
       */
      @Override public void initialize(URL url, ResourceBundle rb)
      {
            receiveData();
            configureChangesTable();
            getDeveloperChanges();

            btnDetails.setDisable(true);
            tvLogChanges.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                      if (newSelection != null)
                      {
                            btnDetails.setDisable(false);
                      }
                      else
                      {
                            btnDetails.setDisable(true);
                      }
                });
      }

      public void receiveData()
      {
            SelectedItemSingleton instance = SelectedItemSingleton.getInstance();
            idDeveloper = instance.getIdSelected();
      }

      private void configureChangesTable()
      {
            colType.setCellValueFactory(new PropertyValueFactory<>("typeName"));
            colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
            colDate.setCellValueFactory(new PropertyValueFactory<>("dateCreated"));
      }

      private void getDeveloperChanges()
      {
            HashMap<String, Object> answer = ChangeDAO.getChangesByDeveloperId(idDeveloper);
            if (!(boolean) answer.get("error"))
            {
                  changes = FXCollections.observableArrayList();
                  ArrayList<Change> list = (ArrayList<Change>) answer.get("changes");
                  changes.addAll(list);
                  tvLogChanges.setItems(changes);
            }
            else
            {
                  System.out.println("Error al obtener cambios: " + answer.get("message"));
            }
      }

      @FXML private void btnDetailsclick(ActionEvent event)
      {
            Change selectedChange = tvLogChanges.getSelectionModel().getSelectedItem();
            if (selectedChange != null)
            {
                  int idChange = selectedChange.getIdChange();
                  Utilities.showDetails(
                      idChange, "gui/FXMLChangesDetails.fxml", "Detalles del cambio");
            }
      }
}
