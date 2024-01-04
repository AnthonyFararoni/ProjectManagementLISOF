package projectmanagementlisof.model.dao;

import com.sun.org.apache.bcel.internal.generic.Select;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import projectmanagementlisof.model.ConnectionDB;
import projectmanagementlisof.model.pojo.ChangeRequest;
import projectmanagementlisof.model.pojo.Developer;
import projectmanagementlisof.utils.LoggedUserSingleton;
import projectmanagementlisof.utils.SelectedProjectSingleton;

public class ChangeRequestDAO
{
      public static HashMap<String, Object> getChangeRequestsByDeveloper(Integer idDeveloper)
      {
            HashMap<String, Object> answer = new LinkedHashMap<>();
            answer.put("error", true);
            Connection connectionBD = ConnectionDB.getConnection();
            if (connectionBD != null)
            {
                  try
                  {
                        String query =
                            "SELECT c.idChangeRequest, c.justification, c.description, c.status, "
                            + "cs.status as statusName, c.creationDate, c.reviewDate, c.idDeveloper,"
                            + "d.name as developerName FROM changeRequest c "
                            + "INNER JOIN developer d ON c.idDeveloper = d.idDeveloper "
                            + "INNER JOIN changerequeststatus cs on c.status = cs.idChangeRequestStatus "
                            + "WHERE c.idDeveloper = ?";
                        PreparedStatement preparedStatement = connectionBD.prepareStatement(query);
                        preparedStatement.setInt(1, idDeveloper);
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
                              changeRequest.setIdChangeRequest(changeRequestList.getInt("status"));
                              changeRequest.setStatus(changeRequestList.getString("statusName"));
                              changeRequest.setCreationDate(
                                  changeRequestList.getString("creationDate"));
                              changeRequest.setReviewDate(
                                  changeRequestList.getString("reviewDate"));
                              changeRequest.setIdDeveloper(changeRequestList.getInt("idDeveloper"));
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

      public static HashMap<String, Object> getChangeRequestsAccordingIdProjectSelected(Integer idProject)
      {
            HashMap<String, Object> answer = new LinkedHashMap<>();
            ArrayList<ChangeRequest> changeRequests = new ArrayList<>();
            Connection connectionBD = ConnectionDB.getConnection();

            if (connectionBD != null)
            {
                  try
                  {
                        String query = "SELECT cr.idChangeRequest, cr.justification, cr.status, "
                                + "s.status AS statusName, cr.creationDate, cr.reviewDate, cr.idDeveloper, "
                                + "cr.idProjectManager, cr.idDefect, cr.description "
                                + "FROM changeRequest cr "
                                + "JOIN Developer d ON cr.idDeveloper = d.idDeveloper "
                                + "INNER JOIN status s on s.idStatus = cr.status "
                                + "WHERE d.idProject = ?";
                        PreparedStatement preparedStatement = connectionBD.prepareStatement(query);
                        preparedStatement.setInt(1, idProject);
                        ResultSet resultSet = preparedStatement.executeQuery();
                        
                        while (resultSet.next())
                        {
                              ChangeRequest changeRequest = new ChangeRequest();
                              changeRequest.setIdChangeRequest(resultSet.getInt("idChangeRequest"));
                              changeRequest.setJustification(resultSet.getString("justification"));
                              changeRequest.setDescription(resultSet.getString("description"));
                              changeRequest.setStatus(resultSet.getString("statusName"));
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

      public static HashMap<String, Object> getChangeRequestsById(Integer idChangeRequest)
      {
            HashMap<String, Object> answer = new LinkedHashMap<>();
            ChangeRequest changeRequest = new ChangeRequest();
            Connection connectionBD = ConnectionDB.getConnection();

            if (connectionBD != null)
            {
                  try
                  {
                        String query = "SELECT * FROM ChangeRequest WHERE idChangeRequest = ?;";
                        PreparedStatement preparedStatement = connectionBD.prepareStatement(query);
                        preparedStatement.setInt(1, idChangeRequest);
                        ResultSet resultSet = preparedStatement.executeQuery();

                        if (resultSet.next())
                        {
                              changeRequest.setIdChangeRequest(resultSet.getInt("idChangeRequest"));
                              changeRequest.setJustification(resultSet.getString("justification"));
                              changeRequest.setDescription(resultSet.getString("description"));
                              changeRequest.setStatus(resultSet.getInt("status"));
                              changeRequest.setCreationDate(resultSet.getString("creationDate"));
                              changeRequest.setReviewDate(resultSet.getString("reviewDate"));
                              changeRequest.setIdDeveloper(resultSet.getInt("idDeveloper"));
                              changeRequest.setIdProjectManager(
                                  resultSet.getInt("idProjectManager"));
                        }

                        connectionBD.close();
                        answer.put("error", false);
                        answer.put("changeRequest", changeRequest);
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
                            + "creationDate, idDeveloper, idProjectManager) VALUES (?, ?, ?, ?, ?, ?);";

                        PreparedStatement preparedStatement = connectionBD.prepareStatement(query);
                        preparedStatement.setString(1, changeRequest.getJustification());
                        preparedStatement.setString(2, changeRequest.getDescription());
                        preparedStatement.setInt(3, changeRequest.getIdStatus());
                        preparedStatement.setString(4, changeRequest.getCreationDate());
                        preparedStatement.setInt(5, changeRequest.getIdDeveloper());

                        if (changeRequest.getIdProjectManager() == null)
                        {
                              preparedStatement.setNull(6, java.sql.Types.INTEGER);
                        }
                        else
                        {
                              preparedStatement.setInt(6, changeRequest.getIdProjectManager());
                        }

                        preparedStatement.executeUpdate();
                        connectionBD.close();
                        answer.put("error", false);
                        answer.put("message", "changeRequest");
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

      public static HashMap<String, Object> updateChangeRequestStatus(
          Integer idChangeRequest, Integer idChangeRequestStatus)
      {
            HashMap<String, Object> answer = new LinkedHashMap<>();
            answer.put("error", true);
            Connection connectionBD = ConnectionDB.getConnection();

            if (connectionBD != null)
            {
                  try
                  {
                        String query =
                            "UPDATE ChangeRequest SET status = ? WHERE idChangeRequest = ?;";

                        PreparedStatement preparedStatement = connectionBD.prepareStatement(query);
                        preparedStatement.setInt(1, idChangeRequestStatus);
                        preparedStatement.setInt(2, idChangeRequest);
                        preparedStatement.executeUpdate();
                        connectionBD.close();
                        answer.put("error", false);
                        answer.put("message", "changeRequest");
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

      public static HashMap<String, Object> getChangeRequestByJustification(String justification)
      {
            HashMap<String, Object> answer = new LinkedHashMap<>();
            answer.put("error", true);
            Connection connectionBD = ConnectionDB.getConnection();
            if (connectionBD != null)
            {
                  try
                  {
                        String query =
                            "SELECT c.idChangeRequest, c.justification, c.description, c.status, "
                            + "cs.status as statusName, c.creationDate, c.reviewDate, c.idDeveloper, c.idProjectManager,"
                            + " d.name as developerName, p.name as projectManagerName FROM changeRequest c "
                            + "INNER JOIN developer d ON c.idDeveloper = d.idDeveloper "
                            + "INNER JOIN projectmanager p ON c.idProjectManager = p.idProjectManager"
                            + "INNER JOIN changerequeststatus cs on c.status = cs.idChangeRequestStatus "
                            + "WHERE justification like ?";
                        PreparedStatement preparedStatement = connectionBD.prepareStatement(query);
                        preparedStatement.setString(1, justification + "%");
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
                              changeRequest.setIdChangeRequest(changeRequestList.getInt("status"));
                              changeRequest.setIdStatus(changeRequestList.getInt("idStatus"));
                              changeRequest.setStatus(changeRequestList.getString("statusName"));
                              changeRequest.setCreationDate(
                                  changeRequestList.getString("creationDate"));
                              changeRequest.setReviewDate(
                                  changeRequestList.getString("reviewDate"));
                              changeRequest.setIdDeveloper(changeRequestList.getInt("idDeveloper"));
                              changeRequest.setDeveloperName(
                                  changeRequestList.getString("developerName"));
                              changeRequest.setIdProjectManager(
                                  changeRequestList.getInt("idProjectManager"));
                              changeRequest.setProjectManagerName(
                                  changeRequestList.getString("projectmanagerName"));
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

      public static HashMap<String, Object> getCompleteDeveloperName(int idDeveloper)
      {
            HashMap<String, Object> answer = new HashMap<>();
            answer.put("error", true);
            Connection connectionBD = ConnectionDB.getConnection();
            if (connectionBD != null)
            {
                  try
                  {
                        String query =
                            "select name, lastName, secondLastname from developer where idDeveloper = ?";
                        PreparedStatement preparedStatement = connectionBD.prepareStatement(query);
                        preparedStatement.setInt(1, idDeveloper);
                        ResultSet developerResult = preparedStatement.executeQuery();

                        if (developerResult.next())
                        {
                              Developer developer = new Developer();
                              developer.setName(developerResult.getString("name"));
                              developer.setLastName(developerResult.getString("lastName"));
                              developer.setSecondLastName(
                                  developerResult.getString("secondLastname"));

                              connectionBD.close();
                              answer.put("error", false);
                              answer.put("developer", developer);
                        }
                        else
                        {
                              answer.put(
                                  "message", "No se encontró ningún desarrollador con ese ID.");
                        }
                  }
                  catch (SQLException ex)
                  {
                        answer.put("message", "Error: " + ex.getMessage());
                  }
            }
            else
            {
                  answer.put("message",
                      "Hubo un error al intentar conectar con la base de datos. Intente de nuevo más tarde");
            }
            return answer;
      }

      public static HashMap<String, Object> updateIdProjectManagerAndReviewDate(
          ChangeRequest changeRequest)
      {
            HashMap<String, Object> answer = new HashMap();
            answer.put("error", true);
            Connection connectionBD = ConnectionDB.getConnection();
            if (connectionBD != null)
            {
                  try
                  {
                        String statement =
                            "update changerequest set idProjectManager = ?, reviewDate = ? "
                            + "where idChangeRequest = ?";
                        PreparedStatement prepareStatement =
                            connectionBD.prepareStatement(statement);
                        prepareStatement.setInt(1, changeRequest.getIdProjectManager());
                        prepareStatement.setString(2, changeRequest.getReviewDate());
                        prepareStatement.setInt(3, changeRequest.getIdChangeRequest());
                        int affectedRows = prepareStatement.executeUpdate();
                        connectionBD.close();
                        if (affectedRows > 0)
                        {
                              answer.put("error", false);
                              answer.put("message", "Cambio Guardado.");
                        }
                        else
                        {
                              answer.put("message", "Error en la base de datos.");
                        }
                  }
                  catch (SQLException e)
                  {
                        answer.put("message", "Error: " + e.getMessage());
                  }
            }
            else
            {
                  answer.put("message", "Error en la conexion a la base de datos.");
            }
            return answer;
      }

      public static HashMap<String, Object> selectAllFromChangeRequest()
      {
            HashMap<String, Object> answer = new HashMap<>();
            answer.put("error", true);
            Connection connectionBD = ConnectionDB.getConnection();
            if (connectionBD != null)
            {
                  try
                  {
                        String query = "select * from changerequest";
                        PreparedStatement preparedStatement = connectionBD.prepareStatement(query);
                        ResultSet resultSet = preparedStatement.executeQuery();
                        ArrayList<ChangeRequest> changeRequests = new ArrayList<>();
                        while (resultSet.next())
                        {
                              ChangeRequest changeRequest = new ChangeRequest();
                              changeRequest.setIdChangeRequest(resultSet.getInt("idChangeRequest"));
                              changeRequest.setJustification(resultSet.getString("justification"));
                              changeRequest.setDescription(resultSet.getString("description"));
                              changeRequest.setIdStatus(resultSet.getInt("status"));
                              changeRequest.setCreationDate(resultSet.getString("creationDate"));
                              changeRequest.setReviewDate(resultSet.getString("reviewDate"));
                              changeRequest.setIdDeveloper(resultSet.getInt("idDeveloper"));
                              changeRequest.setIdProjectManager(
                                  resultSet.getInt("idProjectManager"));
                              changeRequests.add(changeRequest);
                        }
                        connectionBD.close();
                        answer.put("error", false);
                        answer.put("changeRequests", changeRequests);
                  }
                  catch (SQLException e)
                  {
                        answer.put("message", "Error: " + e.getMessage());
                  }
            }
            else
            {
                  answer.put("message", "Error en la conexion a la base de datos.");
            }
            return answer;
      }
}
