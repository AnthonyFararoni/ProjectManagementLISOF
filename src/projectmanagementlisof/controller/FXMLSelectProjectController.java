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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import projectmanagementlisof.model.dao.ProjectDAO;
import projectmanagementlisof.model.pojo.Activity;
import projectmanagementlisof.model.pojo.Project;
import projectmanagementlisof.utils.LoggedUserSingleton;
import projectmanagementlisof.utils.Utilities;

/**
 * FXML Controller class
 *
 * @author edmun
 */
public class FXMLSelectProjectController implements Initializable
{
      @FXML private TableView<Project> tvProjects;
      @FXML private TableColumn colProjectName;
      @FXML private TableColumn colProjectDescription;
      @FXML private Button btnSelectProject;

      private int idManager;
      private ObservableList<Project> projects;
      private int projectId;

      /**
       * Initializes the controller class.
       */
      @Override public void initialize(URL url, ResourceBundle rb)
      {
            getData();
            configureTable();
            getProjects();
      }

      @FXML private void backToLogIn(MouseEvent event)
      {
            Utilities.backToLogIn(event);
      }

      @FXML private void access(ActionEvent event)
      {
            Stage currentStage = (Stage) btnSelectProject.getScene().getWindow();
            Utilities.goTolandingFromAProject(
                currentStage, projectId, "gui/FXMLProjectManagerLanding.fxml", "Inicio");
      }

      private void getData()
      {
            LoggedUserSingleton singleton = LoggedUserSingleton.getInstance();
            idManager = singleton.getUserId();
      }

      private void getProjects()
      {
            HashMap<String, Object> answer = ProjectDAO.getManagerProjects(idManager);
            if (!(boolean) answer.get("error"))
            {
                  projects = FXCollections.observableArrayList();
                  ArrayList<Project> list = (ArrayList<Project>) answer.get("projects");
                  projects.addAll(list);
                  tvProjects.setItems(projects);
            }
            else
            {
                  Utilities.showSimpleAlert(
                      "Error de carga", (String) answer.get("message"), Alert.AlertType.ERROR);
            }
      }

      private void configureTable()
      {
            this.colProjectName.setCellValueFactory(new PropertyValueFactory("name"));
            this.colProjectDescription.setCellValueFactory(new PropertyValueFactory("description"));
            showSelectedProject();
      }

      private void showSelectedProject()
      {
            tvProjects.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Project>() {
                      @Override
                      public void changed(ObservableValue<? extends Project> observable,
                          Project oldValue, Project newValue)
                      {
                            if (newValue != null)
                            {
                                  btnSelectProject.setDisable(false);
                                  int selectedPosition =
                                      tvProjects.getSelectionModel().getSelectedIndex();
                                  Project selectedProject = projects.get(selectedPosition);
                                  projectId = selectedProject.getIdProject();
                            }
                      }
                });
      }
}
