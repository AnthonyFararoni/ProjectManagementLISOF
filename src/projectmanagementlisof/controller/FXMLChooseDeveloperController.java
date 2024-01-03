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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import projectmanagementlisof.model.dao.DeveloperDAO;
import projectmanagementlisof.model.pojo.Developer;
import projectmanagementlisof.observer.DeveloperObserver;
import projectmanagementlisof.utils.SelectedProjectSingleton;
import projectmanagementlisof.utils.Utilities;

public class FXMLChooseDeveloperController implements Initializable
{
      private ObservableList<Developer> developers;
      private Integer idDeveloper;
      private String developerName;
      private Integer idProjectSelected;
      private DeveloperObserver observer;

      @FXML private TableColumn colDeveloperLogin;
      @FXML private TableColumn colDeveloperName;
      @FXML private TableColumn colDeveloperEmail;
      @FXML private TableView<Developer> tvDevelopers;
      @FXML private TextField tfSearchDeveloper;
      @FXML private Button btnAssignDeveloperToActivity;

      public void setObserver(DeveloperObserver observer)
      {
            this.observer = observer;
      }

      @Override public void initialize(URL url, ResourceBundle rb)
      {
          SelectedProjectSingleton instance = SelectedProjectSingleton.getInstance();
           idProjectSelected = instance.getIdSelectedProject();
            configureDevelopersTable();
            getDevelopersForTable();
      }

      @FXML private void btnReturn(MouseEvent event)
      {
            Stage currentStage = (Stage) tfSearchDeveloper.getScene().getWindow();
            Utilities.closeWindow(currentStage);
      }

      @FXML private void btnAssignDeveloperToActivity(ActionEvent event)
      {
            if (idDeveloper != null && observer != null)
            {
                  observer.developerSelected(idDeveloper, developerName);
                  Stage currentStage = (Stage) btnAssignDeveloperToActivity.getScene().getWindow();
                  currentStage.close();
            }
      }

      @FXML private void btnSearchDeveloper(MouseEvent event)
      {
            searchDeveloper();
      }

      private void configureDevelopersTable()
      {
            this.colDeveloperLogin.setCellValueFactory(new PropertyValueFactory("developerLogin"));
            this.colDeveloperName.setCellValueFactory(new PropertyValueFactory("fullName"));
            this.colDeveloperEmail.setCellValueFactory(new PropertyValueFactory("email"));
            showDeveloperSelected();
      }

      private void showDeveloperSelected()
      {
            tvDevelopers.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Developer>() {
                      @Override
                      public void changed(ObservableValue<? extends Developer> observable,
                          Developer oldValue, Developer newValue)
                      {
                            if (newValue != null)
                            {
                                  btnAssignDeveloperToActivity.setDisable(false);
                                  int selectedPosition =
                                      tvDevelopers.getSelectionModel().getSelectedIndex();
                                  Developer selectedDeveloper = developers.get(selectedPosition);
                                  idDeveloper = selectedDeveloper.getIdDeveloper();
                                  developerName = selectedDeveloper.getFullName();
                            }
                      }
                });
      }

      private void getDevelopersForTable()
      {
            HashMap<String, Object> answer = DeveloperDAO.getDevelopers(idProjectSelected);
            if (!(boolean) answer.get("error"))
            {
                  developers = FXCollections.observableArrayList();
                  ArrayList<Developer> list = (ArrayList<Developer>) answer.get("developers");
                  developers.addAll(list);
                  tvDevelopers.setItems(developers);
            }
            else
            {
                  Utilities.showSimpleAlert(
                      "Error de carga", (String) answer.get("message"), Alert.AlertType.ERROR);
            }
      }

      private void searchDeveloper()
      {
            String searchDeveloper = tfSearchDeveloper.getText();
            if (Utilities.validateIdDeveloper(searchDeveloper))
            {
                  HashMap<String, Object> answer =
                      DeveloperDAO.searchDeveloperByDeveloperLogin(searchDeveloper, idProjectSelected);
                  showDevelopers(answer);
            }
            else if (Utilities.validateStringsFields(searchDeveloper))
            {
                  HashMap<String, Object> answer =
                      DeveloperDAO.searchDeveloperByName(searchDeveloper,idProjectSelected);
                  showDevelopers(answer);
            }
            else
            {
                  Utilities.showSimpleAlert("Busqueda incorrecta",
                      "La estructura de los criterios de "
                          + "busqueda es incorrecta. Intente de nuevo",
                      Alert.AlertType.ERROR);
            }
      }

      private void showDevelopers(HashMap<String, Object> answer)
      {
            if (!(boolean) answer.get("error"))
            {
                  developers = FXCollections.observableArrayList();
                  ArrayList<Developer> list = (ArrayList<Developer>) answer.get("developers");
                  developers.addAll(list);
                  tvDevelopers.setItems(developers);
            }
            else
            {
                  Utilities.showSimpleAlert(
                      "Error de carga", (String) answer.get("mensaje"), Alert.AlertType.ERROR);
            }
      }
}
