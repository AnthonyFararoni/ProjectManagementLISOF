package projectmanagementlisof.controller;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import projectmanagementlisof.model.dao.DeveloperDAO;
import projectmanagementlisof.model.dao.ProjectManagerDAO;
import projectmanagementlisof.model.pojo.Developer;
import projectmanagementlisof.model.pojo.ProjectManager;
import projectmanagementlisof.utils.Utilities;

public class FXMLLogInController implements Initializable
{

    @FXML
    private TextField txUser;
    @FXML
    private PasswordField pswPassword;
    @FXML
    private Label lbUserDontExist;
    @FXML
    private Label lbWrongPassword;
    @FXML
    private Label lbEmptyFields;
    private String user;
    private String password;
      @Override public void initialize(URL url, ResourceBundle rb)
      {
        pswPassword.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                btnLogIn(new ActionEvent()); 
                event.consume();
            }
        });
      }

      @FXML private void btnLogIn(ActionEvent event) {
          user = txUser.getText();
          password = pswPassword.getText();
          lbWrongPassword.setVisible(false);
          lbUserDontExist.setVisible(false);
          lbEmptyFields.setVisible(false);
          Utilities.noBoder(txUser);
          Utilities.noBoder(pswPassword);
          int developerExits = checkDeveloperInDB();
          int managerExists = checkProjectManagerInDB();
          if(filledFields()){
            if(developerExits == 0 && managerExists == 0){
              lbUserDontExist.setVisible(true);
            }   
          }
      }
      
      private int checkDeveloperInDB(){
          HashMap<String, Object> answer = DeveloperDAO.checkDeveloperLogIn(user, password);
          int  developerResult = (int) answer.get("result");
          int methodResponse = 0;
          if(developerResult == 2){
              methodResponse = 1;
               HashMap<String, Object> loggedDeveloper = DeveloperDAO.getDeveloperByCredentials(user, password);
               Developer developer = (Developer) loggedDeveloper.get("developer");
               Stage currentStage = (Stage) txUser.getScene().getWindow();
               Utilities.goTolanding(currentStage, developer.getFullName(), developer.getDeveloperLogin() , developer.getIdDeveloper(), "gui/FXMLDeveloperLanding.fxml", "Inicio");
          } 
          else if (developerResult == 1){
              lbWrongPassword.setVisible(true);
              methodResponse = 1;
          }
          return methodResponse;
      }
      
      private int checkProjectManagerInDB(){
          HashMap<String, Object> answer = ProjectManagerDAO.checkProjectManagerLogIn(user, password);
          int  managerResult = (int) answer.get("result");
          int response = 0;
          if(managerResult == 2){
              response = 1;
               HashMap<String, Object> loggedManager = ProjectManagerDAO.getProjectManagerByCredentials(user, password);
               ProjectManager manager = (ProjectManager) loggedManager.get("projectManager");
               Stage currentStage = (Stage) txUser.getScene().getWindow();
               Utilities.goTolanding(currentStage, manager.getFullName(), manager.getManagerLogin(), manager.getManagerId(),"gui/FXMLProjectManagerLanding.fxml", "Inicio");
          } 
          else if (managerResult == 1){
              response = 1;
              lbWrongPassword.setVisible(true);
          }
          return response;
      }
      
      private boolean filledFields() {
            boolean filledFields = true;
            if (txUser.getText().isEmpty()) {
                Utilities.redBorder(txUser);
                filledFields = false;
            }
            if (pswPassword.getText().isEmpty()) {
                Utilities.redBorder(pswPassword);
                filledFields = false;
            }
            if(filledFields == false){
                lbEmptyFields.setVisible(true);
            }
            return filledFields;
        }
}
