package projectmanagementlisof.controller;

import com.mysql.cj.x.protobuf.MysqlxDatatypes.Array;
import com.mysql.cj.xdevapi.Table;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import projectmanagementlisof.model.dao.ActivityDAO;
import projectmanagementlisof.model.dao.ChangeRequestDAO;
import projectmanagementlisof.model.pojo.Activity;
import projectmanagementlisof.model.pojo.ChangeRequest;
import projectmanagementlisof.model.pojo.Developer;
import projectmanagementlisof.observer.DeveloperObserver;
import projectmanagementlisof.utils.Utilities;

public class FXMLEditAssignedActivityDetailsController implements Initializable
{
      @FXML private AnchorPane apEditAssignedActivityDetails;
      @FXML private TextField tfActivityName;
      @FXML private TextField tfAssignedDeveloper;
      @FXML private TextField tfActivityDescription;
      @FXML private DatePicker dpStartDate;
      @FXML private DatePicker dpEndDate;

      @Override public void initialize(URL url, ResourceBundle rb)
      {
            // TODO
      }

      @FXML private void btnEditAssignedActivity(ActionEvent event)
      {
            Activity activity = new Activity();
            activity.setName(tfActivityName.getText());
            activity.setDescription(tfActivityDescription.getText());
            activity.setStartDate(LocalDate.now().toString());
            activity.setEndDate(LocalDate.now().toString());
            activity.setStatus(1);
            activity.setIdDeveloper(1);
            activity.setIdActivity(1);

            HashMap<String, Object> answer = ActivityDAO.updateAssignedActivity(activity);

            if (!(boolean) answer.get("error"))
            {
                  Alert alert = new Alert(AlertType.INFORMATION);
                  alert.setTitle("Information Dialog");
                  alert.setHeaderText(null);
                  alert.setContentText("The activity was updated successfully!");
                  alert.showAndWait();
            }
            else
            {
                  Alert alert = new Alert(AlertType.ERROR);
                  alert.setTitle("Error Dialog");
                  alert.setHeaderText(null);
                  alert.setContentText("The activity could not be updated!");
                  alert.showAndWait();
            }
      }

      /*@FXML private void clickChooseDeveloper(MouseEvent event) throws IOException
      {
            Stage stage = (Stage) apEditAssignedActivityDetails.getScene().getWindow();
            Utilities.openAnotherWindowWithoutClosingCurrentOne(
                stage, "/projectmanagementlisof/gui/FXMLChooseDeveloper.fxml");
      }

      @FXML private void clickDeassignDeveloper(MouseEvent event)
      {
            tfAssignedDeveloper.setText("");
      }*/

      @FXML private void clickImageReturn(MouseEvent event)
      {
            Stage stage = (Stage) apEditAssignedActivityDetails.getScene().getWindow();
            stage.close();
      }
}
