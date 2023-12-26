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
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import projectmanagementlisof.model.dao.ChangeRequestDAO;
import projectmanagementlisof.model.pojo.Activity;
import projectmanagementlisof.model.pojo.ChangeRequest;
<<<<<<< Updated upstream
=======
import projectmanagementlisof.model.pojo.Developer;
import projectmanagementlisof.observer.DeveloperObserver;
import projectmanagementlisof.utils.SelectedItemSingleton;
>>>>>>> Stashed changes
import projectmanagementlisof.utils.Utilities;

/**
 * FXML Controller class
 *
 * @author nando
 */
public class FXMLChangeRequestOptionController implements Initializable, DeveloperObserver
{
      private ObservableList<ChangeRequest> changeRequests;
      private ChangeRequest changeRequest;

<<<<<<< Updated upstream
    private ObservableList<ChangeRequest> changeRequests;
    private ChangeRequest sendChangeRequest;
    
    @FXML
    private TextField tfSearchChangeRequest;
    @FXML
    private TableView<ChangeRequest> tvChangeRequest;
    @FXML
    private TableColumn colJustification;
    @FXML
    private TableColumn colCreationDate;
    @FXML
    private TableColumn colStatus;
    @FXML
    private Button btnShowChangeRequestDetails;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configureChangeRequestTable();
        getChangeRequestPendingForTable();
        //getAssignedChangeRequestForTable();
    }    

    @FXML
    private void btnSearchChangeRequest(MouseEvent event) {
        searchChangeRequest();
    }

    @FXML
    private void btnShowChangeRequestDetails(ActionEvent event) {
        try
        {
            FXMLLoader loader = Utilities.loadView("gui/FXMLChangeRequestDetails.fxml");
            Parent view = loader.load();
            Scene scene = new Scene(view);
            FXMLChangeRequestDetailsController controller = loader.getController();
            controller.initializeChangeRequest(sendChangeRequest);
            Stage stage = new Stage();

            stage.setScene(scene);
            stage.setTitle("Detalles de solicitud de cambio");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();           
        } 
        catch (IOException ex)
        {   
            ex.printStackTrace();
        }
    }
    
    private void configureChangeRequestTable(){
        this.colJustification.setCellValueFactory(new PropertyValueFactory("justification"));
        this.colCreationDate.setCellValueFactory(new PropertyValueFactory("creationDate"));
        this.colStatus.setCellValueFactory(new PropertyValueFactory("status"));
        showChangeRequestSelected();
    }
    
    private void showChangeRequestSelected(){
        tvChangeRequest.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ChangeRequest>() {
            @Override
            public void changed(ObservableValue<? extends ChangeRequest> observable, ChangeRequest oldValue, 
                    ChangeRequest newValue) {
                if(newValue != null){
                    btnShowChangeRequestDetails.setDisable(false);
                    int selectedPosition = tvChangeRequest.getSelectionModel().getSelectedIndex();
                    ChangeRequest selectedChangeRequest = changeRequests.get(selectedPosition);
                    sendChangeRequest = selectedChangeRequest;
                    
                }
            }
        });
    }
        
    private void getAssignedChangeRequestForTable()
    {
        HashMap<String, Object> answer = ChangeRequestDAO.getAssignedChangeRequests();
        if(!(boolean)answer.get("error")){
            changeRequests = FXCollections.observableArrayList();
            ArrayList<ChangeRequest> list = (ArrayList<ChangeRequest>) answer.get("changeRequests");
            changeRequests.addAll(list);
            tvChangeRequest.setItems(changeRequests);           
        }else{
            Utilities.showSimpleAlert("Error de carga", (String)answer.get("message"), 
                    Alert.AlertType.ERROR);
        }        
    }
    
    private void getChangeRequestPendingForTable()
    {             
        HashMap<String, Object> changeRequestsPending = ChangeRequestDAO.getChangeRequestsPending();
        HashMap<String, Object> assignedChangeRequest = ChangeRequestDAO.getAssignedChangeRequests();
        Map<String, Object> mergedchangeRequests= new HashMap<>(changeRequestsPending);
        mergedchangeRequests.putAll(assignedChangeRequest);
        
        if(!(boolean)mergedchangeRequests.get("error")){
            changeRequests = FXCollections.observableArrayList();
            ArrayList<ChangeRequest> list = (ArrayList<ChangeRequest>) mergedchangeRequests.get("changeRequests");
            changeRequests.addAll(list);
            tvChangeRequest.setItems(changeRequests); 
        }else{
            Utilities.showSimpleAlert("Error de carga", (String)mergedchangeRequests.get("message"), 
                    Alert.AlertType.ERROR);
        }        
    }
        
    private void showChangeRequests(HashMap<String, Object> answer){
        if(!(boolean)answer.get("error")){
            changeRequests = FXCollections.observableArrayList();
            ArrayList<ChangeRequest> list = (ArrayList<ChangeRequest>) answer.get("changerequests");
            changeRequests.addAll(list);
            tvChangeRequest.setItems(changeRequests);  
        }else{
            Utilities.showSimpleAlert("Error de carga", (String)answer.get("message"), 
                    Alert.AlertType.ERROR);
        }
    }
    
    private void searchChangeRequest()
    {
        String searchChangeRequest = tfSearchChangeRequest.getText();
        if(Utilities.validateStringsFields(searchChangeRequest)){
            HashMap<String, Object> answer = ChangeRequestDAO.searchChangeRequestByJustification(
                    searchChangeRequest);
            showChangeRequests(answer);    
        }else{
            Utilities.showSimpleAlert("Busqueda incorrecta", "La estructura de los criterios de "
                    + "busqueda es incorrecta. Intente de nuevo", Alert.AlertType.ERROR);
        } 
    }  
    
=======
      @FXML private TextField tfSearchChangeRequest;
      @FXML private TableView<ChangeRequest> tvChangeRequest;
      @FXML private TableColumn colJustification;
      @FXML private TableColumn colCreationDate;
      @FXML private TableColumn colStatus;
      @FXML private Button btnShowChangeRequestDetails;

      private Integer idChangeRequest;
      private Integer selectedDeveloperId;
      private String selectedDeveloperName;

      /**
       * Initializes the controller class.
       */
      @Override public void initialize(URL url, ResourceBundle rb)
      {
            configureChangeRequestTable();
            getChangeRequestForTable();
      }

      @FXML private void btnSearchChangeRequest(MouseEvent event) {}

      @FXML private void btnShowChangeRequestDetails(ActionEvent event) {}

      private void configureChangeRequestTable()
      {
            this.colJustification.setCellValueFactory(new PropertyValueFactory("justification"));
            this.colCreationDate.setCellValueFactory(new PropertyValueFactory("creationDate"));
            this.colStatus.setCellValueFactory(new PropertyValueFactory("status"));
      }

      private void getChangeRequestForTable()
      {
            HashMap<String, Object> answer = ChangeRequestDAO.getAllChangeRequests();
            if (!(boolean) answer.get("error"))
            {
                  changeRequests = FXCollections.observableArrayList();
                  ArrayList<ChangeRequest> list =
                      (ArrayList<ChangeRequest>) answer.get("changeRequests");
                  changeRequests.addAll(list);
                  tvChangeRequest.setItems(changeRequests);
            }
            else
            {
                  Utilities.showSimpleAlert(
                      "Error de carga", (String) answer.get("message"), Alert.AlertType.ERROR);
            }
      }

      @FXML private void loadFXMLChangeRequestDetails(ActionEvent event)
      {
            try
            {
                  FXMLLoader loader = Utilities.loadView("gui/FXMLChangeRequestDetails.fxml");
                  Parent view = loader.load();
                  Scene scene = new Scene(view);
                  FXMLChangeRequestDetailsController controller = loader.getController();
                  ChangeRequest selectedChangeRequest =
                      tvChangeRequest.getSelectionModel().getSelectedItem();
                  controller.initializeInformation(idChangeRequest, this, selectedChangeRequest);

                  Stage stage = new Stage();

                  stage.setScene(scene);
                  stage.setTitle("Detalles de solicitud de cambio");
                  stage.initModality(Modality.APPLICATION_MODAL);
                  stage.show();
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
>>>>>>> Stashed changes
}
