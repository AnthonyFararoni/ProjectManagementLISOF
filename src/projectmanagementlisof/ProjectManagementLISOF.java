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
                FXMLLoader.load(getClass().getResource("gui/FXMLProjectManagerLanding.fxml"));

            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.setTitle("Iniciar sesión");
            stage.show();
            Utilities.centerStage(stage);

            Stage stage2 = new Stage();
            Parent root2 =
                FXMLLoader.load(getClass().getResource("gui/FXMLProjectManagerLanding.fxml"));
            Scene scene2 = new Scene(root2);
            stage2.setScene(scene2);
            stage2.setTitle("Crear solicitud de cambio");
            stage2.show();
            Utilities.centerStage(stage2);
      }

      public static void main(String[] args)
      {
            launch(args);
      }
}
