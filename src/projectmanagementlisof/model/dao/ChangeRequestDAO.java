package projectmanagementlisof.model.dao;

import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import projectmanagementlisof.model.ConnectionDB;

public class ChangeRequestDAO
{
      public static HashMap<String, Object> getChangeRequests(Integer idDeveloper)
      {
            HashMap<String, Object> response = new LinkedHashMap<>();
            response.put("error", true);
            Connection connection = ConnectionDB.getConnection();

            if (connection != null)
            {
                  try
                  {
                        String query = "SELECT * FROM ChangeRequest WHERE idDeveloper = ?";
                        PreparedStatement statement = connection.prepareStatement(query);
                        prepareStatement.setInt(1, idDeveloper);
                        ResultSet resultSet = statement.executeQuery();
                        ArrayList<ChangeRequest> changeRequests = new ArrayList<>();

                        while (resultSet.next())
                        {
                              ChangeRequest changeRequest = new ChangeRequest();
                              changeRequest.setIdChangeRequest(resultSet.getInt("idChangeRequest"));
                              changeRequest.setIdDeveloper(resultSet.getInt("idDeveloper"));
                              changeRequest.setChangeRequest(resultSet.getString("changeRequest"));
                              changeRequest.setChangeRequestDate(
                                  resultSet.getDate("changeRequestDate"));
                              changeRequest.setChangeRequestStatus(
                                  resultSet.getString("changeRequestStatus"));
                              changeRequests.add(changeRequest);
                        }

                        ConnectionDB.closeConnection();
                        response.put("message", "ChangeRequests obtenidos correctamente");
                        response.put("error", false);
                  }
                  catch (SQLException e)
                  {
                        response.put("message", "Error al obtener los ChangeRequests");
                        response.put("error", false);
                        response.put("errorDetail", e.getMessage());
                  }
            }
      }
}
