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
import projectmanagementlisof.model.dao.ChangeDAO;
import projectmanagementlisof.model.pojo.Change;
import projectmanagementlisof.utils.SelectedItemSingleton;
import projectmanagementlisof.utils.Utilities;

/**
 * FXML Controller class
 *
 * @author edmun
 */
public class FXMLChangesDetailsController implements Initializable
{
      @FXML private ImageView imgBackButton;
      @FXML private TextField tfFoundDate;
      @FXML private TextField tfChangeType;
      @FXML private TextArea taDescription;
      private Utilities utilities = new Utilities();
      private int idChange;
      /**
       * Initializes the controller class.
       */
      @Override public void initialize(URL url, ResourceBundle rb)
      {
            receiveData();
            System.out.println(idChange);
            fillChangeDetails();
      }
      public void receiveData()
      {
            SelectedItemSingleton instance = SelectedItemSingleton.getInstance();
            System.out.println(instance.getIdSelected());
            idChange = instance.getIdSelected();
      }

      private void fillChangeDetails()
      {
            HashMap<String, Object> result = ChangeDAO.getChangeDetails(idChange);

            if (!(boolean) result.get("error"))
            {
                  Change change = (Change) result.get("change");
                  tfFoundDate.setText(change.getDateCreated());
                  tfChangeType.setText(String.valueOf(change.getType()));
                  taDescription.setText(change.getDescription());
            }
            else
            {
                  String errorMessage = (String) result.get("message");
            }
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
}
