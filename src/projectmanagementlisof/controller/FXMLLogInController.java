package projectmanagementlisof.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import projectmanagementlisof.model.dao.DeveloperDAO;
import projectmanagementlisof.model.dao.ProjectDAO;
import projectmanagementlisof.model.dao.ProjectManagerDAO;
import projectmanagementlisof.model.pojo.Developer;
import projectmanagementlisof.model.pojo.Project;
import projectmanagementlisof.model.pojo.ProjectManager;
import projectmanagementlisof.utils.SelectedProjectSingleton;
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
                  if (event.getCode() == KeyCode.ENTER)
                  {
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
          if(filledFields()){
            int developerExits = checkDeveloperInDB();
            int managerExists = checkProjectManagerInDB();
            if(developerExits == 0 && managerExists == 0){
              lbUserDontExist.setVisible(true);
            }   
          }
      }

      private int checkDeveloperInDB()
      {
            HashMap<String, Object> answer = DeveloperDAO.checkDeveloperLogIn(user, password);
            int developerResult = (int) answer.get("result");
            int methodResponse = 0;
            if (developerResult == 2)
            {
                  methodResponse = 1;
                  HashMap<String, Object> loggedDeveloper =
                      DeveloperDAO.getDeveloperByCredentials(user, password);
                  Developer developer = (Developer) loggedDeveloper.get("developer");
                  Stage currentStage = (Stage) txUser.getScene().getWindow();
                  Utilities.goTolanding(currentStage, developer.getFullName(),
                      developer.getDeveloperLogin(), developer.getIdDeveloper(),
                      "gui/FXMLDeveloperLanding.fxml", "Inicio");
            }
            else if (developerResult == 1)
            {
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
               managerFollowUp(manager);
          } 
          else if (managerResult == 1){
              response = 1;
              lbWrongPassword.setVisible(true);
          }
          return response;
      }

      private boolean filledFields()
      {
            boolean filledFields = true;
            if (txUser.getText().isEmpty())
            {
                  Utilities.redBorder(txUser);
                  filledFields = false;
            }
            if (pswPassword.getText().isEmpty())
            {
                  Utilities.redBorder(pswPassword);
                  filledFields = false;
            }
            if (filledFields == false)
            {
                  lbEmptyFields.setVisible(true);
            }
            return filledFields;
        }
      
      private void managerFollowUp(ProjectManager manager){
          int idManager = manager.getManagerId();
          Stage currentStage = (Stage) txUser.getScene().getWindow();
          HashMap<String, Object> daoResponse = new HashMap<>();
          daoResponse = ProjectDAO.isInMoreProjects(idManager);
          int projects = (int) daoResponse.get("projects");
          switch (projects){
            case 0:
                Utilities.showSimpleAlert("Error", "No se han encontrado proyectos a su cargo. Porfavor comuniquese con el director de carrera", Alert.AlertType.WARNING);
            break;
            case 1:    
                setProjectt(idManager);
                Utilities.goTolanding(currentStage, manager.getFullName(), manager.getManagerLogin(), manager.getManagerId(),"gui/FXMLProjectManagerLanding.fxml", "Inicio");
            break;
            case 2:
            Utilities.goTolanding(currentStage, manager.getFullName(), manager.getManagerLogin(), manager.getManagerId(),"gui/FXMLSelectProject.fxml", "Inicio");
            break;
          }
      }
      
      private void setProjectt(int idManager){
          HashMap<String, Object> daoProject = new HashMap<>();
          daoProject = ProjectDAO.getManagerProjects(idManager);
          ArrayList<Project> list = (ArrayList<Project>) daoProject.get("projects");
          Project project = list.get(0);
          int projectId = project.getIdProject() ;
          SelectedProjectSingleton instance = SelectedProjectSingleton.getInstance();
          instance.setIdSelectedProject(projectId);
      }
}
