package projectmanagementlisof.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import projectmanagementlisof.model.pojo.Developer;

public class FXMLChooseDeveloperController implements Initializable
{

    @FXML
    private TableColumn colDeveloperLogin;
    @FXML
    private TableColumn colDeveloperName;
    @FXML
    private TableColumn colDeveloperEmail;
    @FXML
    private TableView<Developer> tvDevelopers;
    
      @Override public void initialize(URL url, ResourceBundle rb)
      {
            // TODO
      }

      @FXML private void btnReturn(MouseEvent event) 
      {
      
      }

      @FXML private void btnAssignDeveloperToActivity(ActionEvent event) 
      {
      
      }
}
