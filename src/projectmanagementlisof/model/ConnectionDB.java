package projectmanagementlisof.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import projectmanagementlisof.utils.Constant;

public class ConnectionDB
{
      public static final String URL_CONNECTION = "jdbc:mysql://" + Constant.HOSTNAME + ":"
          + Constant.PORT + "/" + Constant.NAME_DB + "?allowPublicKeyRetrieval=true&useSSL=false";

      public static Connection getConnection()
      {
            Connection connectionDB = null;
            try
            {
                  Class.forName(Constant.DRIVER);
                  connectionDB =
                      DriverManager.getConnection(URL_CONNECTION, Constant.USER, Constant.PASSWORD);
            }
            catch (ClassNotFoundException e)
            {
                  e.printStackTrace();
            }
            catch (SQLException e)
            {
                  e.printStackTrace();
            }
            return connectionDB;
      }

      public static int getLastId(String string)
      {
            int id = 0;
            Connection connectionDB = null;
            try
            {
                  Class.forName(Constant.DRIVER);
                  connectionDB =
                      DriverManager.getConnection(URL_CONNECTION, Constant.USER, Constant.PASSWORD);
            }
            catch (ClassNotFoundException e)
            {
                  e.printStackTrace();
            }
            catch (SQLException e)
            {
                  e.printStackTrace();
            }
            return id;
      }
}
