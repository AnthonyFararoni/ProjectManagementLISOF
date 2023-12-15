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
import javafx.scene.Parent;
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
import projectmanagementlisof.utils.UserSingleton;
import projectmanagementlisof.utils.Utilities;

public class FXMLEditAssignedActivityDetailsController implements Initializable
{
      @FXML private AnchorPane apEditAssignedActivityDetails;
      @FXML private TextField tfActivityName;
      @FXML private TextField tfAssignedDeveloper;
      @FXML private TextField tfActivityDescription;
      @FXML private DatePicker dpStartDate;
      @FXML private DatePicker dpEndDate;
      private int idActivity;

      @Override public void initialize(URL url, ResourceBundle rb)
      {
            receiveData();
            fillActivityDetails();
      }

      public void receiveData()
      {
            UserSingleton instance = UserSingleton.getInstace();
            System.out.println(instance.getIdSelected());
            idActivity = instance.getIdSelected();
      }

      private void fillActivityDetails()
      {
            HashMap<String, Object> result = ActivityDAO.getActivityByID(idActivity);

            if (!(boolean) result.get("error"))
            {
                  Activity activity = (Activity) result.get("activity");
                  tfActivityName.setText(activity.getName());
                  tfActivityDescription.setText(activity.getDescription());
                  dpStartDate.setValue(LocalDate.parse(activity.getStartDate()));
                  dpEndDate.setValue(LocalDate.parse(activity.getEndDate()));
                  tfAssignedDeveloper.setText(activity.getName());
            }
            else
            {
                  Alert alert = new Alert(AlertType.ERROR);
                  alert.setTitle("Error Dialog");
                  alert.setHeaderText(null);
                  alert.setContentText("The activity could not be loaded!");
                  alert.showAndWait();
            }
      }

      @FXML private void btnEditAssignedActivity(ActionEvent event)
      {
            Activity activity = new Activity();
            activity.setName(tfActivityName.getText());
            activity.setDescription(tfActivityDescription.getText());
            activity.setStartDate(dpStartDate.getValue().toString());
            activity.setEndDate(dpEndDate.getValue().toString());
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

      @FXML private void clickChooseDeveloper(ActionEvent event) throws IOException
      {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/projectmanagementlisof/gui/FXMLChooseDeveloper.fxml"));
            Parent root = loader.load();
            FXMLChooseDeveloperController controller = loader.getController();
            controller.setObserver(new DeveloperObserver() {
                  @Override public void developerSelected(Integer idDeveloper, String developerName)
                  {
                        loadDeveloper(idDeveloper, developerName);
                  }

                  private void loadDeveloper(Integer idDeveloper, String developerName)
                  {
                        tfAssignedDeveloper.setText(developerName);
                  }
            });
            Scene scene = new Scene(root);
            Stage stage = new Stage();

            stage.setScene(scene);
            stage.setTitle("Asignar desarrollador");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
      }

      @FXML private void clickDeassignDeveloper(ActionEvent event)
      {
            tfAssignedDeveloper.setText("");
      }

      @FXML private void clickImageReturn(MouseEvent event)
      {
            Stage stage = (Stage) apEditAssignedActivityDetails.getScene().getWindow();
            stage.close();
      }
}
