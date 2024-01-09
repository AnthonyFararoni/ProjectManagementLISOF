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
import projectmanagementlisof.model.dao.DefectDAO;
import projectmanagementlisof.model.pojo.Defect;
import projectmanagementlisof.utils.SelectedItemSingleton;
import projectmanagementlisof.utils.Utilities;

/**
 * FXML Controller class
 *
 * @author edmun
 */
public class FXMLDefectDetailsController implements Initializable
{
      private int idDefect;
      private Utilities utilities = new Utilities();
      @FXML private ImageView imgBackButton;
      @FXML private TextField tfFoundDate;
      @FXML private TextField tfIdDefect;
      @FXML private TextField tfTimeCost;
      @FXML private TextField tfType;
      @FXML private TextArea taDescription;

      /**
       * Initializes the controller class.
       */
      @Override public void initialize(URL url, ResourceBundle rb)
      {
            receiveData();
            System.out.println(idDefect);
            fillDefectDetails();
      }
      public void receiveData()
      {
            SelectedItemSingleton instance = SelectedItemSingleton.getInstance();
            idDefect = instance.getIdSelected();
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
            Stage currentStage = (Stage) tfFoundDate.getScene().getWindow();
            utilities.closeWindow(currentStage);
      }

      private void fillDefectDetails()
      {
            HashMap<String, Object> result = DefectDAO.getDefectById(idDefect);

            if (!(boolean) result.get("error"))
            {
                  Defect defect = (Defect) result.get("defect");
                  tfIdDefect.setText(String.valueOf(defect.getIdDefect()));
                  tfType.setText(defect.getTypeName());
                  taDescription.setText(defect.getDescription());
                  tfTimeCost.setText(String.valueOf(defect.getTimeCost()));
                  tfFoundDate.setText(defect.getDate());
            }
            else
            {
                  System.out.println("No jalo");
                  String errorMessage = (String) result.get("message");
            }
      }
}
