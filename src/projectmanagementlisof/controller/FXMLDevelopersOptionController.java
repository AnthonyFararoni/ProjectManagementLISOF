package projectmanagementlisof.controller;

import java.io.IOException;
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
import projectmanagementlisof.model.dao.DeveloperDAO;
import projectmanagementlisof.model.pojo.Developer;
import projectmanagementlisof.utils.Utilities;

public class FXMLDevelopersOptionController implements Initializable
{
    public int idDeveloper;
    public String developerName;
    public String developerLogin;
    private ObservableList<Developer> developers;
    
    @FXML
    private TextField tfSearchDeveloper;
    @FXML
    private TableView<Developer> tvDevelopers;
    @FXML
    private TableColumn colDeveloperLogin;
    @FXML
    private TableColumn colDeveloperName;
    @FXML
    private TableColumn colDeveloperEmail;
    @FXML
    private Button btnDisableDeveloper;
    @FXML
    private Button btnShowDevelopersLog;
    
    @Override public void initialize(URL url, ResourceBundle rb)
    {
        configureDevelopersTable(); 
        getDevelopersForTable();   
    }
    
    @FXML
    private void btnDisableDeveloper(ActionEvent event) 
    {
            boolean confirmation = Utilities.showConfirmationAlert("¿Eliminar desarrollador?", "¿Esta seguro"
                    + " de eliminar al desarrollador seleccionado?");
            if(confirmation){
                disableDeveloper(idDeveloper);
            }
    
    }
    
    @FXML
    private void btnRefreshTableDevelopers(MouseEvent event) {
        getDevelopersForTable();
        tfSearchDeveloper.setText("");
    }
    
    private void configureDevelopersTable()
    {
        this.colDeveloperLogin.setCellValueFactory(new PropertyValueFactory("developerLogin"));
        this.colDeveloperName.setCellValueFactory(new PropertyValueFactory("fullName"));
        this.colDeveloperEmail.setCellValueFactory(new PropertyValueFactory("email"));
        showDeveloperSelected();
    }
    
    private void showDeveloperSelected(){
        tvDevelopers.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Developer>() {
            @Override
            public void changed(ObservableValue<? extends Developer> observable, Developer oldValue, 
                    Developer newValue) {
                if(newValue != null){
                    btnDisableDeveloper.setDisable(false);
                    btnShowDevelopersLog.setDisable(false);
                    int selectedPosition = tvDevelopers.getSelectionModel().getSelectedIndex();
                    Developer selectedDeveloper = developers.get(selectedPosition);
                    idDeveloper = newValue.getIdDeveloper();
                    developerName = selectedDeveloper.getFullName();
                    developerLogin = newValue.getDeveloperLogin();   
                    
                }
            }
        });
    }
    
    private void getDevelopersForTable()
    {
        HashMap<String, Object> answer = DeveloperDAO.getDevelopers();
        if(!(boolean)answer.get("error")){
            developers = FXCollections.observableArrayList();
            ArrayList<Developer> list = (ArrayList<Developer>) answer.get("developers");
            developers.addAll(list);
            tvDevelopers.setItems(developers);           
        }else{
            Utilities.showSimpleAlert("Error de carga", (String)answer.get("message"), 
                    Alert.AlertType.ERROR);
        }        
    }

    @FXML
    private void btnShowDevelopersLog(ActionEvent event) 
    {
        try
        {
            FXMLLoader loader = Utilities.loadView("gui/FXMLDeveloperLog.fxml");
            Parent view = loader.load();
            Scene scene = new Scene(view);
            FXMLDeveloperLogController controller = loader.getController();
            controller.inicializarInformación(idDeveloper, developerName, developerLogin);
            Stage stage = new Stage();

            stage.setScene(scene);
            stage.setTitle("Bitacora del desarrollador");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();           
        } 
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }

    private void disableDeveloper(int idDeveloper)
    {
        HashMap<String, Object> answer = DeveloperDAO.disableDeveloper(idDeveloper);
        if(!(boolean) answer.get("error")){
            Utilities.showSimpleAlert("Eliminacion exitosa", (String)answer.get("mensaje"),
                    Alert.AlertType.INFORMATION);
            getDevelopersForTable();
        }else{
            Utilities.showSimpleAlert("Eliminacion fallida", (String)answer.get("mensaje"),
                    Alert.AlertType.ERROR);
        }
        btnDisableDeveloper.setDisable(true);
        btnShowDevelopersLog.setDisable(true);
    }
    
    private void searchDeveloper()
    {
        String searchDeveloper = tfSearchDeveloper.getText();
        if(Utilities.validateIdDeveloper(searchDeveloper)){
            HashMap<String, Object> answer = DeveloperDAO.searchDeveloperByDeveloperLogin(searchDeveloper);
            showDevelopers(answer);
        }else if(Utilities.validateStringsFields(searchDeveloper)){
            HashMap<String, Object> answer = DeveloperDAO.searchDeveloperByName(searchDeveloper);
            showDevelopers(answer);
        }else{
            Utilities.showSimpleAlert("Busqueda incorrecta", "La estructura de los criterios de "
                    + "busqueda es incorrecta. Intente de nuevo", Alert.AlertType.ERROR);
        } 
    }
    
    private void showDevelopers(HashMap<String, Object> answer){
        if(!(boolean)answer.get("error")){
            developers = FXCollections.observableArrayList();
            ArrayList<Developer> list = (ArrayList<Developer>) answer.get("developers");
            developers.addAll(list);
            tvDevelopers.setItems(developers);           
        }else{
            Utilities.showSimpleAlert("Error de carga", (String)answer.get("mensaje"), 
                    Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void btnSearchDeveloper(MouseEvent event) {
        searchDeveloper();
    }

   
}