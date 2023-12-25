package projectmanagementlisof.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import projectmanagementlisof.model.dao.ChangeRequestDAO;
import projectmanagementlisof.model.pojo.ChangeRequest;
import projectmanagementlisof.utils.Utilities;

public class FXMLChangeRequestDetailsController implements Initializable
{
    private ChangeRequest changeRequest;
    
    @FXML 
    private TextField tfJustification;
    @FXML 
    private TextField tfDateCreated;
    @FXML 
    private TextField tfRequestId;
    @FXML 
    private TextArea taDescription;
    @FXML
    private TextField tfSolicitedBy;
    @FXML
    private TextField tfDateReview;
    @FXML
    private TextField tfReviewedBy;

      @Override public void initialize(URL url, ResourceBundle rb)
      {
            
      }

      @FXML 
      private void btnReturn(MouseEvent event) {
            Stage currentStage = (Stage) tfJustification.getScene().getWindow();
            Utilities.closeWindow(currentStage);
      }
      
      public void initializeChangeRequest(ChangeRequest changeRequest){
            this.changeRequest = changeRequest;
            loadChangeRequestInformation();
      }
      
      private void loadChangeRequestInformation() {
        tfJustification.setText(changeRequest.getJustification());
        tfRequestId.setText(String.valueOf(changeRequest.getIdChangeRequest()));
        tfSolicitedBy.setText(changeRequest.getDeveloperName());
        tfDateCreated.setText(changeRequest.getCreationDate());
        tfReviewedBy.setText(changeRequest.getProjectManagerName());
        tfDateReview.setText(changeRequest.getReviewDate());
        taDescription.setText(changeRequest.getDescription());
    }
}
      
