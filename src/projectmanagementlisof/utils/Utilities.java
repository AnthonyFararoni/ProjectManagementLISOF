package projectmanagementlisof.utils;

import com.sun.javafx.scene.SceneUtils;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Callback;
import javafx.util.converter.IntegerStringConverter;
import projectmanagementlisof.controller.FXMLChangeRequestDetailsController;
import projectmanagementlisof.controller.FXMLDeveloperLandingController;
import projectmanagementlisof.controller.FXMLNewChangeRequestFormController;
import projectmanagementlisof.controller.FXMLProjectManagerLandingController;
import projectmanagementlisof.model.dao.CatalogDAO;
import projectmanagementlisof.model.pojo.ChangeRequest;

public class Utilities
{
      public static void centerStage(Stage stage)
      {
            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
            stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
      }

      public static FXMLLoader loadView(String pathFXML) throws IOException
      {
            URL url = projectmanagementlisof.ProjectManagementLISOF.class.getResource(pathFXML);
            return new FXMLLoader(url);
      }

      public static boolean validateIdDeveloper(String idDeveloper)
      {
            String rightPattern = "^(zs|zS|ZS)\\d{8}$";
            Pattern pattern = Pattern.compile(rightPattern);
            Matcher matcher = pattern.matcher(idDeveloper);
            return matcher.matches() && !idDeveloper.trim().isEmpty();
      }

      public static boolean validateStringsFields(String fieldToCheck)
      {
            String rightPattern = "^[a-zA-ZáéíóúüñÁÉÍÓÚÜÑ]+$";
            Pattern pattern = Pattern.compile(rightPattern);
            Matcher matcher = pattern.matcher(fieldToCheck);
            return matcher.matches() && !fieldToCheck.trim().isEmpty();
      }

      public static void showSimpleAlert(String title, String message, Alert.AlertType type)
      {
            Alert simpleAlert = new Alert(type);
            simpleAlert.setTitle(title);
            simpleAlert.setContentText(message);
            simpleAlert.setHeaderText(null);
            simpleAlert.showAndWait();
      }

      public static boolean showConfirmationAlert(String title, String message)
      {
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle(title);
            confirmationAlert.setContentText(message);
            confirmationAlert.setHeaderText(null);
            Optional<ButtonType> buttonClic = confirmationAlert.showAndWait();
            return (buttonClic.get() == ButtonType.OK);
      }

      public static String getFullName(String name, String lastName, String secondLastName)
      {
            String fullName = name + " " + lastName + " " + secondLastName;
            return fullName;
      }

      public static void loadFXML(String fxmlFile, AnchorPane anchorPane)
      {
            try
            {
                  AnchorPane FXMLFile = FXMLLoader.load(Utilities.class.getResource(fxmlFile));
                  anchorPane.getChildren().setAll(FXMLFile);
            }
            catch (IOException ex)
            {
                  ex.printStackTrace();
            }
      }

      public static <T> T closeCurrentWindowAndOpenAnotherOne(
          String fxmlPath, Stage currentStage, ActionEvent event)
      {
            try
            {
                  FXMLLoader loader = new FXMLLoader(Utilities.class.getResource(fxmlPath));
                  Parent root = loader.load();

                  T controller = loader.getController();

                  Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                  stage.setScene(new Scene(root));

                  currentStage.close();

                  stage.show();

                  return controller;
            }
            catch (IOException e)
            {
                  throw new RuntimeException("Failed to load FXML file", e);
            }
      }

      public static void closeCurrentWindowAndOpenAnotherOneMouseEvent(
          String fxmlPath, Stage currentStage, MouseEvent event)
      {
            try
            {
                  FXMLLoader loader = new FXMLLoader(Utilities.class.getResource(fxmlPath));
                  Parent root = loader.load();

                  Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                  stage.setScene(new Scene(root));

                  currentStage.close();

                  stage.show();
            }
            catch (IOException e)
            {
                  e.printStackTrace();
            }
      }

      public static void loadFXMLInAnchorPane(AnchorPane anchorPane, String fxmlPath)
      {
            try
            {
                  FXMLLoader loader = new FXMLLoader(Utilities.class.getResource(fxmlPath));
                  Parent content = loader.load();

                  anchorPane.getChildren().setAll(content);
            }
            catch (IOException ex)
            {
                  ex.printStackTrace();
            }
      }

      public static void loadFXMLInAnchorPaneAndCloseCurrentForDeveloper(
          Stage currentStage, String landingFxmlPath, String contentFxmlPath)
      {
            try
            {
                  currentStage.close();

                  FXMLLoader landingLoader =
                      new FXMLLoader(Utilities.class.getResource(landingFxmlPath));
                  Parent landingRoot = landingLoader.load();

                  FXMLLoader contentLoader =
                      new FXMLLoader(Utilities.class.getResource(contentFxmlPath));
                  Parent contentRoot = contentLoader.load();

                  FXMLDeveloperLandingController landingController = landingLoader.getController();

                  AnchorPane apBackground = landingController.getApBackground();

                  apBackground.getChildren().clear();
                  apBackground.getChildren().add(contentRoot);

                  Stage stage = new Stage();
                  stage.setScene(new Scene(landingRoot));
                  stage.show();
            }
            catch (IOException ex)
            {
                  ex.printStackTrace();
            }
      }

      public static void loadFXMLInAnchorPaneAndCloseCurrentForProjectManager(
          Stage currentStage, String landingFxmlPath, String contentFxmlPath)
      {
            try
            {
                  currentStage.close();

                  FXMLLoader landingLoader =
                      new FXMLLoader(Utilities.class.getResource(landingFxmlPath));
                  Parent landingRoot = landingLoader.load();

                  FXMLLoader contentLoader =
                      new FXMLLoader(Utilities.class.getResource(contentFxmlPath));
                  Parent contentRoot = contentLoader.load();

                  FXMLProjectManagerLandingController landingController =
                      landingLoader.getController();

                  AnchorPane apBackground = landingController.getApBackground();

                  apBackground.getChildren().clear();
                  apBackground.getChildren().add(contentRoot);

                  Stage stage = new Stage();
                  stage.setScene(new Scene(landingRoot));
                  stage.show();
            }
            catch (IOException ex)
            {
                  ex.printStackTrace();
            }
      }

      public static void closeView(Stage currentStage, String fxmlPath)
      {
            try
            {
                  Parent vista = FXMLLoader.load(Utilities.class.getResource(fxmlPath));
                  Scene escena = new Scene(vista);
                  currentStage.setScene(escena);
                  currentStage.showAndWait();
            }
            catch (IOException ex)
            {
                  ex.printStackTrace();
            }
      }

      public String parseDateToString(LocalDate date)
      {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String parsedDate = formatter.format(date);
            return parsedDate;
      }

      public static void closeWindow(Stage currentStage)
      {
            currentStage.close();
      }

      public static <T> void setItemsInComboBox(ObservableList<T> items, ComboBox<T> comboBox)
      {
            List<T> result = (List<T>) CatalogDAO.getTypes();
            items.addAll(result);
            comboBox.setItems(items);
      }

      public static <T> void setItemsInComboBoxStatuses(
          ObservableList<T> items, ComboBox<T> comboBox)
      {
            List<T> result = (List<T>) CatalogDAO.getStatuses();
            items.addAll(result);
            comboBox.setItems(items);
      }

      public static void onlyNumbers(TextField textField)
      {
            TextFormatter<Integer> formatter =
                new TextFormatter<>(new IntegerStringConverter(), null, change -> {
                      String newText = change.getControlNewText();
                      if (newText.matches("\\d*"))
                      {
                            return change;
                      }
                      return null;
                });
            textField.setTextFormatter(formatter);
      }
      public static void limitTextFieldCharacters(TextField textField, int maxCharacters)
      {
            textField.textProperty().addListener((observable, oldValue, newValue) -> {
                  if (newValue != null && newValue.length() > maxCharacters)
                  {
                        textField.setText(oldValue);
                  }
            });
      }
      public static void limitTextAreaCharacters(TextArea textArea, int maxCharacters)
      {
            textArea.textProperty().addListener((observable, oldValue, newValue) -> {
                  if (newValue != null && newValue.length() > maxCharacters)
                  {
                        textArea.setText(oldValue);
                  }
            });
      }

      public static void showDetails(int id, String fxmlPath, String title)
      {
            SelectedItemSingleton instance = SelectedItemSingleton.getInstance();
            instance.setIdSelected(id);

            try
            {
                  FXMLLoader loader = loadView(fxmlPath);
                  Parent view = loader.load();
                  Scene scene = new Scene(view);
                  Stage stage = new Stage();

                  stage.setScene(scene);
                  stage.setTitle(title);
                  stage.initModality(Modality.APPLICATION_MODAL);
                  stage.showAndWait();
            }
            catch (java.io.IOException ex)
            {
                  ex.printStackTrace();
            }
      }

      public static void showDetailsChangeRequest(
          int selectedPosition, String fxmlPath, String title, ChangeRequest selectedChangeRequest)
      {
            try
            {
                  FXMLLoader loader = new FXMLLoader(Utilities.class.getResource(fxmlPath));
                  Parent root = (Parent) loader.load();

                  FXMLChangeRequestDetailsController controller = loader.getController();
                  controller.setSelectedChangeRequest(selectedChangeRequest);

                  Scene scene = new Scene(root);
                  Stage stage = new Stage();
                  stage.initModality(Modality.APPLICATION_MODAL);
                  stage.setScene(scene);
                  stage.setTitle(title);
                  stage.show();
            }
            catch (IOException ex)
            {
                  Logger.getLogger(Utilities.class.getName()).log(Level.SEVERE, null, ex);
            }
      }

      public static void goTolanding(Stage currentStage, String userFullName, String userLogin,
          int userId, String fxmlPath, String title)
      {
            LoggedUserSingleton instance = LoggedUserSingleton.getInstance();
            instance.setUserData(userFullName, userLogin, userId);
            try
            {
                  FXMLLoader loader = loadView(fxmlPath);
                  Parent view = loader.load();
                  Scene scene = new Scene(view);
                  Stage stage = new Stage();

                  currentStage.setScene(scene);
                  currentStage.setTitle(title);
                  currentStage.show();
                  currentStage.centerOnScreen();
            }
            catch (java.io.IOException ex)
            {
                  ex.printStackTrace();
            }
      }

      public static void goTolandingFromAProject(
          Stage currentStage, int projectId, String fxmlPath, String title)
      {
            SelectedProjectSingleton instance = SelectedProjectSingleton.getInstance();
            instance.setIdSelectedProject(projectId);
            try
            {
                  FXMLLoader loader = loadView(fxmlPath);
                  Parent view = loader.load();
                  Scene scene = new Scene(view);
                  Stage stage = new Stage();

                  currentStage.setScene(scene);
                  currentStage.setTitle(title);
                  currentStage.show();
                  currentStage.centerOnScreen();
            }
            catch (java.io.IOException ex)
            {
                  ex.printStackTrace();
            }
      }

      public static void redBorder(Control field)
      {
            field.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
      }

      public static void noBoder(Control field)
      {
            field.setStyle(null);
      }

      public static void restrictDates(DatePicker datePicker, LocalDate date)
      {
            Callback<DatePicker, DateCell> dayCellFactory = dp -> new DateCell() {
                  @Override public void updateItem(LocalDate item, boolean empty)
                  {
                        super.updateItem(item, empty);
                        setDisable(item.isBefore(date));
                  }
            };
            datePicker.setDayCellFactory(dayCellFactory);
      }

      public static void backToLogIn(MouseEvent event)
      {
            try
            {
                  Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                  Parent newView = FXMLLoader.load(
                      Utilities.class.getResource("/projectmanagementlisof/gui/FXMLLogIn.fxml"));
                  Scene scene = new Scene(newView);
                  currentStage.setScene(scene);
                  currentStage.setTitle("Iniciar sesión");
                  currentStage.show();
                  centerStage(currentStage);
            }
            catch (IOException ex)
            {
                  ex.printStackTrace();
            }
      }
      public static void showSpecificAlert(
          Alert.AlertType alertType, String title, String headerText, String contentText)
      {
            Alert alert = new Alert(alertType);
            alert.setTitle(title);
            alert.setHeaderText(headerText);
            alert.setContentText(contentText);
            alert.showAndWait();
      }

      public static Boolean checkConnection()
      {
            if (CatalogDAO.checkConnection() == false)
            {
                  System.out.println("No se ha podido establecer conexión con la base de datos");
                  return false;
            }
            else
            {
                  return true;
            }
      }
}
