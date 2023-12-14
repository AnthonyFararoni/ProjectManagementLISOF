package projectmanagementlisof.model.dao;

import java.lang.reflect.Array;
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
      public static HashMap<String, Object> getChangeRequests(Integer idDeveloper)
      {
            HashMap<String, Object> answer = new LinkedHashMap<>();
            answer.put("error", true);
            Connection connectionBD = ConnectionDB.getConnection();

            if (connectionBD != null)
            {
                  try
                  {
                        String query = "SELECT c.idChangeRequest, c.justification, c.description, "
                            + "c.status, c.creationDate, c.reviewDate, FROM ChangeRequest c, "
                            + "INNER JOIN Developer d ON c.idDeveloper = d.idDeveloper "
                            + "WHERE c.idDeveloper = " + idDeveloper + ";";

                        PreparedStatement preparedStatement = connectionBD.prepareStatement(query);
                        ResultSet changeRequestList = preparedStatement.executeQuery();
                        ArrayList<ChangeRequest> changeRequests = new ArrayList<>();

                        while (changeRequestList.next())
                        {
                              ChangeRequest changeRequest = new ChangeRequest();
                              changeRequest.setIdChangeRequest(
                                  changeRequestList.getInt("idChangeRequest"));
                              changeRequest.setJustification(
                                  changeRequestList.getString("justification"));
                              changeRequest.setDescription(
                                  changeRequestList.getString("description"));
                              changeRequest.setStatus(changeRequestList.getString("status"));
                              changeRequest.setCreationDate(
                                  changeRequestList.getString("creationDate"));
                              changeRequest.setReviewDate(
                                  changeRequestList.getString("reviewDate"));
                              changeRequests.add(changeRequest);
                        }
                        connectionBD.close();
                        answer.put("error", false);
                        answer.put("changeRequests", changeRequests);
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

      public static HashMap<String, Object> getChangeRequestsById(Integer idChangeRequest)
      {
            HashMap<String, Object> answer = new LinkedHashMap<>();
            answer.put("error", true);
            Connection connectionBD = ConnectionDB.getConnection();

            if (connectionBD != null)
            {
                  try
                  {
                        String query = "SELECT c.idChangeRequest, c.justification, c.description, "
                            + "c.status, c.creationDate, c.reviewDate, FROM ChangeRequest c, "
                            + "INNER JOIN Developer d ON c.idDeveloper = d.idDeveloper "
                            + "WHERE c.idChangeRequest = " + idChangeRequest + ";";

                        PreparedStatement preparedStatement = connectionBD.prepareStatement(query);
                        ResultSet changeRequestList = preparedStatement.executeQuery();
                        ArrayList<ChangeRequest> changeRequests = new ArrayList<>();

                        while (changeRequestList.next())
                        {
                              ChangeRequest changeRequest = new ChangeRequest();
                              changeRequest.setIdChangeRequest(
                                  changeRequestList.getInt("idChangeRequest"));
                              changeRequest.setJustification(
                                  changeRequestList.getString("justification"));
                              changeRequest.setDescription(
                                  changeRequestList.getString("description"));
                              changeRequest.setStatus(changeRequestList.getString("status"));
                              changeRequest.setCreationDate(
                                  changeRequestList.getString("creationDate"));
                              changeRequest.setReviewDate(
                                  changeRequestList.getString("reviewDate"));
                              changeRequests.add(changeRequest);
                        }
                        connectionBD.close();
                        answer.put("error", false);
                        answer.put("changeRequests", changeRequests);
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

      public int createChangeRequest(ChangeRequest changeRequest)
      {
            String query =
                "INSERT INTO ChangeRequest(justification, description, status, creationDate, "
                + "reviewDate, idDeveloper, idProjectManager, idDefect) values (?,?,?,?,?,?,?,?)";

            Connection conectionBD;
            PreparedStatement statement;
            int result = 0;

            try
            {
                  conectionBD = ConnectionDB.getConnection();
                  statement = conectionBD.prepareStatement(query);
                  statement.setString(1, changeRequest.getJustification());
                  statement.setString(2, changeRequest.getDescription());
                  statement.setString(3, changeRequest.getStatus());
                  statement.setString(4, changeRequest.getCreationDate());
                  statement.setString(5, changeRequest.getReviewDate());
                  statement.setInt(6, changeRequest.getIdDeveloper());
                  statement.setInt(7, changeRequest.getIdProjectManager());
                  statement.setInt(8, changeRequest.getIdDefect());
                  result = statement.executeUpdate();
            }
            catch (SQLException e)
            {
                  result = -1;
            }
            return result;
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
