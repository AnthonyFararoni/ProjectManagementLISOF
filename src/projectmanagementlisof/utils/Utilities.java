package projectmanagementlisof.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Utilities
{
      public static void centerStage(Stage stage)
      {
            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
            stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
      }
      
      public static boolean validateIdDeveloper(String idDeveloper) 
      {
        String patron = "^(zs|zS|ZS)\\d{8}$";
        Pattern pattern = Pattern.compile(patron);
        Matcher matcher = pattern.matcher(idDeveloper);
        return matcher.matches() && !idDeveloper.trim().isEmpty();
    }
}
