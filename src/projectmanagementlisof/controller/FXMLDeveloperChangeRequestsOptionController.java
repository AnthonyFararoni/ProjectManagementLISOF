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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import projectmanagementlisof.model.dao.ChangeRequestDAO;
import projectmanagementlisof.model.pojo.ChangeRequest;
import projectmanagementlisof.model.pojo.Developer;
import projectmanagementlisof.utils.Utilities;

public class FXMLDeveloperChangeRequestsOptionController implements Initializable
{
      @FXML private AnchorPane apBackground;

      private ObservableList<ChangeRequest> changeRequests;
      @FXML private TextField tfSearchChangeRequest;
      @FXML private TableView<ChangeRequest> tvChangeRequests;
      @FXML private TableColumn<String, String> colJustification;
      @FXML private TableColumn<String, String> colDate;
      @FXML private TableColumn<String, String> colStatus;

      @Override public void initialize(URL url, ResourceBundle rb)
      {
            // TODO
            loadChangeRequests();
      }

      private void loadChangeRequests()
      {
            HashMap<String, Object> answer = ChangeRequestDAO.getChangeRequests();

            if (!(boolean) answer.get("error"))
            {
                  ArrayList<ChangeRequest> changeRequestsList =
                      (ArrayList<ChangeRequest>) answer.get("changeRequests");
                  changeRequests = FXCollections.observableArrayList(changeRequestsList);
                  tvChangeRequests.setItems(changeRequests);
                  colJustification.setCellValueFactory(new PropertyValueFactory<>("justification"));
                  colDate.setCellValueFactory(new PropertyValueFactory<>("creationDate"));
                  colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
            }
            else
            {
                  Alert alert = new Alert(Alert.AlertType.ERROR);
                  alert.setTitle("Error");
                  alert.setHeaderText("Error al cargar las solicitudes de cambio");
                  alert.setContentText((String) answer.get("message"));
                  alert.showAndWait();
            }
      }

      @FXML private void loadFXMLNewChangeRequestForm(ActionEvent event) throws IOException
      {
            try
            {
                  FXMLLoader fxmlLoader = new FXMLLoader();
                  fxmlLoader.setLocation(getClass().getResource(
                      "/projectmanagementlisof/gui/FXMLNewChangeRequestForm.fxml"));
                  Scene scene = new Scene(fxmlLoader.load());
                  Stage stage = new Stage();
                  stage.setTitle("Crear nueva solicitud de cambio");
                  stage.initModality(Modality.APPLICATION_MODAL);
                  stage.setScene(scene);
                  stage.showAndWait();
            }
            catch (IOException ex)
            {
                  ex.printStackTrace();
            }
      }
}
