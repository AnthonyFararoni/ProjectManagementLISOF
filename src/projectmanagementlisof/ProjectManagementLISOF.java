package projectmanagementlisof;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import projectmanagementlisof.utils.Utilities;

public class ProjectManagementLISOF extends Application
{
      @Override public void start(Stage stage) throws Exception
      {
            Parent root =
                FXMLLoader.load(getClass().getResource("gui/FXMLDeveloperLanding.fxml"));

            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.setTitle("Iniciar sesión");
            stage.show();
            Utilities.centerStage(stage);
      }

      public static void main(String[] args)
      {
            launch(args);
      }
}
