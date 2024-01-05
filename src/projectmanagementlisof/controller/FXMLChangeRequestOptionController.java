/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this
 * license Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this
 * template
 */
package projectmanagementlisof.controller;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
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
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import projectmanagementlisof.model.dao.ChangeRequestDAO;
import projectmanagementlisof.model.pojo.ChangeRequest;
import projectmanagementlisof.observer.DeveloperObserver;
import projectmanagementlisof.utils.SelectedItemSingleton;
import projectmanagementlisof.utils.SelectedProjectSingleton;
import projectmanagementlisof.utils.Utilities;

/**
 * FXML Controller class
 *
 * @author nando
 */
public class FXMLChangeRequestOptionController implements Initializable, DeveloperObserver
{
      private ObservableList<ChangeRequest> changeRequests;
      private ChangeRequest changeRequestObject;

      @FXML private TextField tfSearchChangeRequest;
      @FXML private TableView<ChangeRequest> tvChangeRequest;
      @FXML private TableColumn<String, String> colJustification;
      @FXML private TableColumn<String, String> colCreationDate;
      @FXML private TableColumn<String, String> colStatus;
      @FXML private Button btnShowChangeRequestDetails;

      private Integer idChangeRequest;
      private Integer selectedDeveloperId;
      private String selectedDeveloperName;
      private Integer idProjectSelected;

      private ObservableList<ChangeRequest> masterData = FXCollections.observableArrayList();
      private FilteredList<ChangeRequest> filteredData;

      /**
       * Initializes the controller class.
       */
      @Override public void initialize(URL url, ResourceBundle rb)
      {
            SelectedProjectSingleton instance = SelectedProjectSingleton.getInstance();
            idProjectSelected = instance.getIdSelectedProject();
            configureChangeRequestTable();
            getChangeRequestForTable();
      }

      @FXML private void btnSearchChangeRequest(MouseEvent event) {}

      @FXML private void btnShowChangeRequestDetails(ActionEvent event) {}

      private void configureChangeRequestTable()
      {
            colJustification.setCellValueFactory(new PropertyValueFactory<>("justification"));

            colCreationDate.setCellValueFactory(new PropertyValueFactory<>("creationDate"));

            colCreationDate.setCellFactory(column -> {
                  TableCell<String, String> cell = new TableCell<String, String>() {
                        private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

                        @Override protected void updateItem(String item, boolean empty)
                        {
                              super.updateItem(item, empty);
                              if (empty)
                              {
                                    setText(null);
                              }
                              else
                              {
                                    try
                                    {
                                          Date date =
                                              new SimpleDateFormat("yyyy-MM-dd").parse(item);
                                          setText(format.format(date));
                                    }
                                    catch (ParseException e)
                                    {
                                          setText(item);
                                    }
                              }
                        }
                  };
                  return cell;
            });

            colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

            getChangeRequestForTable();
      }

      private void getChangeRequestForTable()
      {
            HashMap<String, Object> answer =
                ChangeRequestDAO.getChangeRequestsAccordingIdProjectSelected(idProjectSelected);

            if (!(boolean) answer.get("error"))
            {
                  changeRequests = FXCollections.observableArrayList();
                  ArrayList<?> rawList = (ArrayList<?>) answer.get("changeRequests");
                  ArrayList<ChangeRequest> list = new ArrayList<>();

                  for (Object obj : rawList)
                  {
                        if (obj instanceof ChangeRequest)
                        {
                              list.add((ChangeRequest) obj);
                        }
                  }

                  changeRequests.addAll(list);
                  tvChangeRequest.setItems(changeRequests);
                  masterData.clear();
                  masterData.addAll(changeRequests);
            }
            else
            {
                  Utilities.showSimpleAlert(
                      "Error de carga", (String) answer.get("message"), Alert.AlertType.ERROR);
            }
      }

      @FXML private void loadFXMLChangeRequestDetails(ActionEvent event)
      {
            ChangeRequest selectedChangeRequest =
                tvChangeRequest.getSelectionModel().getSelectedItem();

            if (selectedChangeRequest != null)
            {
                  Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                  FXMLChangeRequestDetailsController detailsController =
                      Utilities
                          .<FXMLChangeRequestDetailsController>closeCurrentWindowAndOpenAnotherOne(
                              "/projectmanagementlisof/gui/FXMLChangeRequestDetails.fxml", stage,
                              event);

                  detailsController.setSelectedChangeRequest(selectedChangeRequest);
            }
            else
            {
                  Utilities.showSimpleAlert("Error de selección",
                      "Debe seleccionar una solicitud de cambio.", Alert.AlertType.ERROR);
            }
      }

      @Override public void developerSelected(Integer idDeveloper, String developerName)
      {
            this.selectedDeveloperId = idDeveloper;
            this.selectedDeveloperName = developerName;
      }

      @FXML private void clickSearchChangeRequest(KeyEvent event)
      {
            searchChangeRequest();
      }

      private void searchChangeRequest()
      {
            String filter = tfSearchChangeRequest.getText();

            if (filter == null || filter.length() == 0)
            {
                  filteredData = new FilteredList<>(masterData, p -> true);
            }
            else
            {
                  filteredData = new FilteredList<>(masterData, p -> true);

                  filteredData.setPredicate(changeRequest -> {
                        if (filter == null || filter.isEmpty())
                        {
                              return true;
                        }
                        String lowerCaseFilter = filter.toLowerCase();
                        if (changeRequest.getJustification().toLowerCase().contains(
                                lowerCaseFilter))
                        {
                              return true;
                        }
                        else if (changeRequest.getCreationDate().toLowerCase().contains(
                                     lowerCaseFilter))
                        {
                              return true;
                        }
                        else if (changeRequest.getStatus().toLowerCase().contains(lowerCaseFilter))
                        {
                              return true;
                        }
                        return false;
                  });
            }

            tvChangeRequest.setItems(filteredData);
      }
}
