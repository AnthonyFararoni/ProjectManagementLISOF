/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this
 * license Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this
 * template
 */
package projectmanagementlisof.controller;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import projectmanagementlisof.model.dao.ActivityDAO;
import projectmanagementlisof.model.pojo.Activity;
import projectmanagementlisof.utils.UserSingleton;
import projectmanagementlisof.utils.Utilities;

/**
 * FXML Controller class
 *
 * @author edmun
 */
public class FXMLActivityDetailsController implements Initializable
{
      private Utilities utilities = new Utilities();
      private int idActivity;
      @FXML private ImageView imgBackButton;
      @FXML private TextField tfStartDate;
      @FXML private TextField tfActivityName;
      @FXML private TextField tfStatus;
      @FXML private TextField tfEndDate;
      @FXML private TextArea taDescription;

      /**
       * Initializes the controller class.
       */
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

      @FXML private void changeToDefaultCursor(MouseEvent event)
      {
            imgBackButton.setCursor(Cursor.DEFAULT);
      }

      @FXML private void changeToHandCursor(MouseEvent event)
      {
            imgBackButton.setCursor(Cursor.HAND);
      }

      @FXML private void goBackToLanding(MouseEvent event)
      {
            Stage currentStage = (Stage) tfStartDate.getScene().getWindow();
            utilities.closeWindow(currentStage);
      }

      private void fillActivityDetails()
      {
            HashMap<String, Object> result = ActivityDAO.getActivityByID(idActivity);

            if (!(boolean) result.get("error"))
            {
                  Activity activity = (Activity) result.get("activity");
                  tfActivityName.setText(activity.getName());
                  taDescription.setText(activity.getDescription());
                  tfStatus.setText(activity.getStatusName());
                  tfStartDate.setText(activity.getStartDate());
                  tfEndDate.setText(activity.getEndDate());
            }
            else
            {
                  String errorMessage = (String) result.get("message");
            }
      }
}
