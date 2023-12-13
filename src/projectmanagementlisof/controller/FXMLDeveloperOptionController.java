package projectmanagementlisof.controller;

import java.io.IOException;
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
import javafx.scene.control.Alert;
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

public class FXMLDeveloperOptionController implements Initializable
{
    
    private Utilities utilities = new Utilities();
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
    
    @Override public void initialize(URL url, ResourceBundle rb)
    {
          // TODO
        //configureDevelopersTable();
        
        
    }
    
    private void initializeInformation()
    {
        getDevelopersTable();
    }
    
    private void configureDevelopersTable()
    {
        this.colDeveloperLogin.setCellValueFactory(new PropertyValueFactory("developerLogin"));
        this.colDeveloperName.setCellValueFactory(new PropertyValueFactory("name"));
        this.colDeveloperEmail.setCellValueFactory(new PropertyValueFactory("email"));
    }
    
    private void getDevelopersTable()
    {
        HashMap<String, Object> answer = DeveloperDAO.getDevelopers();
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
    private void btnShowDevelopersLog(ActionEvent event) 
    {
        try
        {
            FXMLLoader loader = utilities.loadView("gui/FXMLDeveloperLog.fxml");
            Parent view = loader.load();
            Scene scene = new Scene(view);
            FXMLDeveloperLogController controller = loader.getController();
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

    @FXML
    private void btnDisableDeveloper(ActionEvent event) {
        
    }
}
