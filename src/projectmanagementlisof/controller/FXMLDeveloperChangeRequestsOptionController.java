package projectmanagementlisof.controller;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import javafx.scene.control.TableCell;
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
import projectmanagementlisof.utils.LoggedUserSingleton;
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
            configureChangeRequestTable();
            getChangeRequestForTable();
      }

      private void configureChangeRequestTable()
      {
            colJustification.setCellValueFactory(new PropertyValueFactory<>("justification"));
            colDate.setCellValueFactory(new PropertyValueFactory<>("creationDate"));

            colDate.setCellFactory(column -> {
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
      }

      private void getChangeRequestForTable()
      {
            HashMap<String, Object> answer = ChangeRequestDAO.getChangeRequestsByDeveloper(
                LoggedUserSingleton.getInstance().getUserId());

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
                              System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@ ChangeRequest: " + obj);
                        }
                  }

                  changeRequests.addAll(list);
                  tvChangeRequests.setItems(changeRequests);
                  masterData.addAll(changeRequests);
            }
            else
            {
                  Utilities.showSimpleAlert(
                      "Error de carga", (String) answer.get("message"), Alert.AlertType.ERROR);
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
                        else if (changeRequest.getStatus().toLowerCase().contains(lowerCaseFilter))
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
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            Utilities.<FXMLNewChangeRequestFormController>closeCurrentWindowAndOpenAnotherOne(
                "/projectmanagementlisof/gui/FXMLNewChangeRequestForm.fxml", stage, event);
      }

      @FXML private void loadFXMLChangeRequestDetails(ActionEvent event)
      {
            try
            {
                  ChangeRequest cr = tvChangeRequests.getSelectionModel().getSelectedItem();

                  if (cr != null)
                  {
                        Utilities.closeWindow(
                            (Stage) ((Node) event.getSource()).getScene().getWindow());

                        FXMLLoader loader =
                            Utilities.loadView("gui/FXMLDeveloperChangeRequestDetails.fxml");
                        Parent view = loader.load();
                        Scene scene = new Scene(view);
                        FXMLDeveloperChangeRequestDetailsController controller =
                            loader.getController();
                        ChangeRequest selectedChangeRequest =
                            tvChangeRequests.getSelectionModel().getSelectedItem();
                        controller.initializeInformation(
                            idChangeRequest, this, selectedChangeRequest);

                        Stage stage = new Stage();

                        stage.setScene(scene);
                        stage.setTitle("Detalles de solicitud de cambio");
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.showAndWait();
                  }
                  else
                  {
                        Utilities.showSimpleAlert("Error de selección",
                            "Debe seleccionar una solicitud de cambio.", Alert.AlertType.ERROR);
                  }
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
