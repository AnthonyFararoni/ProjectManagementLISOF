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
import projectmanagementlisof.model.pojo.Developer;

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
                              changeRequest.setStatus(changeRequestList.getInt("status"));
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

      public static HashMap<String, Object> getAllChangeRequests()
      {
            HashMap<String, Object> answer = new LinkedHashMap<>();
            ArrayList<ChangeRequest> changeRequests = new ArrayList<>();
            Connection connectionBD = ConnectionDB.getConnection();

            if (connectionBD != null)
            {
                  try
                  {
                        String query = "SELECT * FROM ChangeRequest;"; // TODO change query
                        PreparedStatement preparedStatement = connectionBD.prepareStatement(query);
                        ResultSet resultSet = preparedStatement.executeQuery();

                        while (resultSet.next())
                        {
                              ChangeRequest changeRequest = new ChangeRequest();
                              changeRequest.setIdChangeRequest(resultSet.getInt("idChangeRequest"));
                              changeRequest.setJustification(resultSet.getString("justification"));
                              changeRequest.setDescription(resultSet.getString("description"));
                              if (resultSet.getInt("status") == 1)
                              {
                                    changeRequest.setStatus("Aprobada");
                              }
                              else if (resultSet.getInt("status") == 2)
                              {
                                    changeRequest.setStatus("Pendiente");
                              }
                              else if (resultSet.getInt("status") == 3)
                              {
                                    changeRequest.setStatus("Rechazada");
                              }
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
                              // changeRequest.setStatus(changeRequestList.getInt("status"));
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
                        preparedStatement.setInt(3, changeRequest.getIdStatus());
                        preparedStatement.setString(4, changeRequest.getCreationDate());
                        preparedStatement.setInt(5, changeRequest.getIdDeveloper());
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

      public static HashMap<String, Object> updateChangeRequestStatus(ChangeRequest changeRequest)
      {
            HashMap<String, Object> answer = new HashMap();
            answer.put("error", true);
            Connection connectionBD = ConnectionDB.getConnection();
            if (connectionBD != null)
            {
                  try
                  {
                        String statement =
                            "update changerequest set status = ?, reviewDate = ?, idProjectManager = ?, "
                            + "where idChangeRequest = ?";
                        PreparedStatement prepareStatement =
                            connectionBD.prepareStatement(statement);
                        prepareStatement.setInt(1, changeRequest.getIdStatus());
                        prepareStatement.setString(2, changeRequest.getReviewDate());
                        prepareStatement.setInt(3, changeRequest.getIdProjectManager());
                        prepareStatement.setInt(4, changeRequest.getIdChangeRequest());
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

      public static HashMap<String, Object> approveChangeRequest(Integer idChangeRequest)
      {
            HashMap<String, Object> answer = new HashMap<>();
            answer.put("error", true);
            Connection connectionBD = ConnectionDB.getConnection();
            if (connectionBD != null)
            {
                  try
                  {
                        String query =
                            "update ChangeRequest set status = 2 where idChangeRequest = ?";
                        PreparedStatement preparedStatement = connectionBD.prepareStatement(query);
                        preparedStatement.setInt(1, idChangeRequest);
                        int affectedRows = preparedStatement.executeUpdate();
                        connectionBD.close();
                        if (affectedRows > 0)
                        {
                              answer.put("error", false);
                              answer.put("message", "Solicitud de cambio aprobada.");
                        }
                        else
                        {
                              answer.put("message", "Error en la base de datos.");
                        }
                  }
                  catch (SQLException ex)
                  {
                        answer.put("message", "Error: " + ex.getMessage());
                  }
            }
            else
            {
                  answer.put("message", "Error en la conexion a la base de datos.");
            }
            return answer;
      }

      public static HashMap<String, Object> rejectChangeRequest(Integer idChangeRequest)
      {
            HashMap<String, Object> answer = new HashMap<>();
            answer.put("error", true);
            Connection connectionBD = ConnectionDB.getConnection();
            if (connectionBD != null)
            {
                  try
                  {
                        String query =
                            "update ChangeRequest set status = 3 where idChangeRequest = ?";
                        PreparedStatement preparedStatement = connectionBD.prepareStatement(query);
                        preparedStatement.setInt(1, idChangeRequest);
                        int affectedRows = preparedStatement.executeUpdate();
                        connectionBD.close();
                        if (affectedRows > 0)
                        {
                              answer.put("error", false);
                              answer.put("message", "Solicitud de cambio rechazada.");
                        }
                        else
                        {
                              answer.put("message", "Error en la base de datos.");
                        }
                  }
                  catch (SQLException ex)
                  {
                        answer.put("message", "Error: " + ex.getMessage());
                  }
            }
            else
            {
                  answer.put("message", "Error en la conexion a la base de datos.");
            }
            return answer;
      }
}
