package projectmanagementlisof.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import projectmanagementlisof.model.dao.ChangeRequestDAO;
import projectmanagementlisof.model.pojo.ChangeRequest;
import projectmanagementlisof.observer.DeveloperObserver;
import projectmanagementlisof.utils.Utilities;

public class FXMLDeveloperChangeRequestsOptionController implements Initializable, DeveloperObserver
{
      @FXML private AnchorPane apDeveloperChangeRequestOptions;

      private ObservableList<ChangeRequest> changeRequests;
      @FXML private TextField tfSearchChangeRequest;
      @FXML private TableView<ChangeRequest> tvChangeRequests;
      @FXML private TableColumn<String, String> colJustification;
      @FXML private TableColumn<String, String> colDate;
      @FXML private TableColumn<String, String> colStatus;

      private ObservableList<ChangeRequest> masterData = FXCollections.observableArrayList();
      private FilteredList<ChangeRequest> filteredData;

      private Integer idChangeRequest;
      private Integer selectedDeveloperId;
      private String selectedDeveloperName;

      @Override public void initialize(URL url, ResourceBundle rb)
      {
            // TODO
            loadChangeRequests();
      }

      private void loadChangeRequests()
      {
            HashMap<String, Object> answer = ChangeRequestDAO.getAllChangeRequests();

            if (!(boolean) answer.get("error"))
            {
                  ArrayList<ChangeRequest> changeRequestsList =
                      (ArrayList<ChangeRequest>) answer.get("changeRequests");
                  changeRequests = FXCollections.observableArrayList(changeRequestsList);
                  masterData.addAll(changeRequests); // Add this line
                  tvChangeRequests.setItems(changeRequests);
                  colJustification.setCellValueFactory(new PropertyValueFactory<>("justification"));
                  colDate.setCellValueFactory(new PropertyValueFactory<>("creationDate"));
                  colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
            }
            else
            {
                  Alert alert = new Alert(Alert.AlertType.ERROR);
                  alert.setTitle("Error");
                  alert.setHeaderText("Error al cargar las solicitudes de cambio");
                  alert.setContentText((String) answer.get("message"));
                  alert.showAndWait();
            }
      }

      @FXML private void clickSearchChangeRequest(KeyEvent event)
      {
            searchChangeRequest();
      }

      private void searchChangeRequest()
      {
            filteredData = new FilteredList<>(masterData, p -> true);

            tfSearchChangeRequest.textProperty().addListener((observable, oldValue, newValue) -> {
                  filteredData.setPredicate(changeRequest -> {
                        if (newValue == null || newValue.isEmpty())
                        {
                              return true;
                        }

                        String lowerCaseFilter = newValue.toLowerCase();

                        if (changeRequest.getJustification().toLowerCase().contains(
                                lowerCaseFilter))
                        {
                              return true;
                        }
                        else if (changeRequest.getDescription().toLowerCase().contains(
                                     lowerCaseFilter))
                        {
                              return true;
                        }
                        return false;
                  });
            });

            tvChangeRequests.setItems(filteredData);
      }

      @FXML private void loadFXMLNewChangeRequestForm(ActionEvent event)
      {
            try
            {
                  FXMLLoader loader = new FXMLLoader(getClass().getResource(
                      "/projectmanagementlisof/gui/FXMLNewChangeRequestForm.fxml"));
                  Parent root = loader.load();

                  Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                  stage.setScene(new Scene(root));

                  stage.show();
            }
            catch (IOException e)
            {
                  e.printStackTrace();
            }
      }

      private void showSelectedChangeRequest()
      {
            tvChangeRequests.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<ChangeRequest>() {
                      @Override
                      public void changed(ObservableValue<? extends ChangeRequest> observable,
                          ChangeRequest oldValue, ChangeRequest newValue)
                      {
                            if (newValue != null)
                            {
                                  // btnShowAssignedActivityDetails.setDisable(false);
                                  // btnEditAssignedActivity.setDisable(false);
                                  // btnDeleteAssignedActivity.setDisable(false);
                                  int selectedPosition =
                                      tvChangeRequests.getSelectionModel().getSelectedIndex();
                                  ChangeRequest selectedChangeRequest =
                                      changeRequests.get(selectedPosition);
                                  idChangeRequest = selectedChangeRequest.getIdChangeRequest();
                            }
                      }
                });
      }

      @FXML private void loadFXMLChangeRequestDetails(ActionEvent event)
      {
            try
            {
                  FXMLLoader loader =
                      Utilities.loadView("gui/FXMLDeveloperChangeRequestDetails.fxml");
                  Parent view = loader.load();
                  Scene scene = new Scene(view);
                  FXMLDeveloperChangeRequestDetailsController controller = loader.getController();
                  ChangeRequest selectedChangeRequest =
                      tvChangeRequests.getSelectionModel().getSelectedItem();
                  controller.initializeInformation(idChangeRequest, this, selectedChangeRequest);

                  Stage stage = new Stage();

                  stage.setScene(scene);
                  stage.setTitle("Detalles de solicitud de cambio");
                  stage.initModality(Modality.APPLICATION_MODAL);
                  stage.showAndWait();
            }
            catch (Exception ex)
            {
                  Logger.getLogger(FXMLDeveloperChangeRequestsOptionController.class.getName())
                      .log(Level.SEVERE, null, ex);
            }
      }

      @Override public void developerSelected(Integer idDeveloper, String developerName)
      {
            this.selectedDeveloperId = idDeveloper;
            this.selectedDeveloperName = developerName;
      }
}
