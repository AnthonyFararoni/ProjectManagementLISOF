package projectmanagementlisof.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import projectmanagementlisof.model.ConnectionDB;
import projectmanagementlisof.model.pojo.ChangeRequest;

public class ChangeRequestDAO
{
      // public static HashMap<String, Object> getChangeRequests(Integer idDeveloper)

      // public static HashMap<String, Object> getChangeRequestsById(Integer idChangeRequest)

      public static HashMap<String, Object> getChangeRequests()
      {
            HashMap<String, Object> answer = new LinkedHashMap<>();
            ArrayList<ChangeRequest> changeRequests = new ArrayList<>();
            Connection connectionBD = ConnectionDB.getConnection();

            if (connectionBD != null)
            {
                  try
                  {
                        String query = "SELECT * FROM ChangeRequest;";
                        PreparedStatement preparedStatement = connectionBD.prepareStatement(query);
                        ResultSet resultSet = preparedStatement.executeQuery();

                        while (resultSet.next())
                        {
                              ChangeRequest changeRequest = new ChangeRequest();
                              changeRequest.setIdChangeRequest(resultSet.getInt("idChangeRequest"));
                              changeRequest.setJustification(resultSet.getString("justification"));
                              changeRequest.setDescription(resultSet.getString("description"));
                              changeRequest.setStatus(resultSet.getString("status"));
                              changeRequest.setCreationDate(resultSet.getString("creationDate"));
                              changeRequest.setReviewDate(resultSet.getString("reviewDate"));
                              changeRequest.setIdDeveloper(resultSet.getInt("idDeveloper"));
                              changeRequests.add(changeRequest);
                        }

                        connectionBD.close();
                        answer.put("error", false);
                        answer.put("changeRequests", changeRequests);
                  }
                  catch (SQLException ex)
                  {
                        answer.put("error", true);
                        answer.put("message", "Error: " + ex.getMessage());
                  }
            }
            else
            {
                  answer.put("error", true);
                  answer.put("message",
                      "Hubo un error al intentar conectar con la base de datos. Intente "
                          + "de nuevo más tarde");
            }

            return answer;
      }

      public static HashMap<String, Object> searchChangeRequest(String search)
      {
            HashMap<String, Object> answer = new LinkedHashMap<>();
            ArrayList<ChangeRequest> changeRequests = new ArrayList<>();
            Connection connectionBD = ConnectionDB.getConnection();

            if (connectionBD != null)
            {
                  try
                  {
                        String query = "SELECT * FROM ChangeRequest WHERE idChangeRequest = ?;";
                        PreparedStatement preparedStatement = connectionBD.prepareStatement(query);
                        preparedStatement.setString(1, search);
                        ResultSet resultSet = preparedStatement.executeQuery();

                        while (resultSet.next())
                        {
                              ChangeRequest changeRequest = new ChangeRequest();
                              changeRequest.setIdChangeRequest(resultSet.getInt("idChangeRequest"));
                              changeRequest.setJustification(resultSet.getString("justification"));
                              changeRequest.setDescription(resultSet.getString("description"));
                              changeRequest.setStatus(resultSet.getString("status"));
                              changeRequest.setCreationDate(resultSet.getString("creationDate"));
                              changeRequest.setReviewDate(resultSet.getString("reviewDate"));
                              changeRequest.setIdDeveloper(resultSet.getInt("idDeveloper"));
                              changeRequests.add(changeRequest);
                        }

                        connectionBD.close();
                        answer.put("error", false);
                        answer.put("changeRequests", changeRequests);
                  }
                  catch (SQLException ex)
                  {
                        answer.put("error", true);
                        answer.put("message", "Error: " + ex.getMessage());
                  }
            }
            else
            {
                  answer.put("error", true);
                  answer.put("message",
                      "Hubo un error al intentar conectar con la base de datos. Intente "
                          + "de nuevo más tarde");
            }

            return answer;
      }

      public static HashMap<String, Object> createChangeRequest(ChangeRequest changeRequest)
      {
            HashMap<String, Object> answer = new LinkedHashMap<>();
            answer.put("error", true);
            Connection connectionBD = ConnectionDB.getConnection();

            if (connectionBD != null)
            {
                  try
                  {
                        String query =
                            "INSERT INTO ChangeRequest (justification, description, status, "
                            + "creationDate, idDeveloper) VALUES (?, ?, ?, ?, ?);";

                        PreparedStatement preparedStatement = connectionBD.prepareStatement(query);
                        preparedStatement.setString(1, changeRequest.getJustification());
                        preparedStatement.setString(2, changeRequest.getDescription());
                        preparedStatement.setString(3, changeRequest.getStatus());
                        preparedStatement.setString(4, changeRequest.getCreationDate());
                        preparedStatement.setInt(5, changeRequest.getIdDeveloper());
                        preparedStatement.executeUpdate();
                        connectionBD.close();
                        answer.put("error", false);
                  }
                  catch (SQLException ex)
                  {
                        answer.put("message", "Error: " + ex.getMessage());
                  }
            }
            else
            {
                  answer.put("message",
                      "Hubo un error al intentar conectar con la base de datos. Intente "
                          + "de nuevo más tarde");
            }

            return answer;
      }

      public static void editChangeRequest(ChangeRequest changeRequest)
      {
            Connection connectionBD = ConnectionDB.getConnection();

            if (connectionBD != null)
            {
                  try
                  {
                        String query =
                            "UPDATE ChangeRequest SET justification = ?, description = ?, "
                            + "status = ?, creationDate = ?, reviewDate = ?, idDeveloper = ? "
                            + "WHERE idChangeRequest = ?;";

                        PreparedStatement preparedStatement = connectionBD.prepareStatement(query);
                        preparedStatement.setString(1, changeRequest.getJustification());
                        preparedStatement.setString(2, changeRequest.getDescription());
                        preparedStatement.setString(3, changeRequest.getStatus());
                        preparedStatement.setString(4, changeRequest.getCreationDate());
                        preparedStatement.setString(5, changeRequest.getReviewDate());
                        preparedStatement.setInt(6, changeRequest.getIdDeveloper());
                        preparedStatement.setInt(7, changeRequest.getIdChangeRequest());
                        preparedStatement.executeUpdate();
                        connectionBD.close();
                  }
                  catch (SQLException ex)
                  {
                        System.out.println("Error: " + ex.getMessage());
                  }
            }
      }
}
