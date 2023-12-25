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
                              //changeRequest.setStatus(changeRequestList.getInt("status"));
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
                        preparedStatement.setInt(3, changeRequest.getIdStatus());
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
      
    public static HashMap<String, Object> getAssignedChangeRequests()
    {
        HashMap<String, Object> answer = new LinkedHashMap<>();
        answer.put("error", true);
        Connection connectionBD = ConnectionDB.getConnection();
        if (connectionBD != null)
        {
            try
            {
                String query = "SELECT c.idChangeRequest, c.justification, c.description, c.status, "
                        + "cs.status as statusName, c.creationDate, c.reviewDate, c.idDeveloper, c.idProjectManager,"
                        + " d.name as developerName, p.name as projectManagerName FROM changeRequest c "
                        + "INNER JOIN developer d ON c.idDeveloper = d.idDeveloper "
                        + "INNER JOIN projectmanager p ON c.idProjectManager = p.idProjectManager "
                        + "INNER JOIN changerequeststatus cs on c.status = cs.idChangeRequestStatus";
                PreparedStatement preparedStatement = connectionBD.prepareStatement(query);
                ResultSet changeRequestList = preparedStatement.executeQuery();
                ArrayList<ChangeRequest> changeRequests = new ArrayList<>();
                while (changeRequestList.next())
                {
                    ChangeRequest changeRequest = new ChangeRequest();
                    changeRequest.setIdChangeRequest(changeRequestList.getInt("idChangeRequest"));
                    changeRequest.setJustification(changeRequestList.getString("justification"));
                    changeRequest.setDescription(changeRequestList.getString("description"));
                    changeRequest.setIdStatus(changeRequestList.getInt("status"));
                    changeRequest.setStatus(changeRequestList.getString("statusName"));
                    changeRequest.setCreationDate(changeRequestList.getString("creationDate"));
                    changeRequest.setReviewDate(changeRequestList.getString("reviewDate"));
                    changeRequest.setIdDeveloper(changeRequestList.getInt("idDeveloper"));
                    changeRequest.setIdProjectManager(changeRequestList.getInt("idProjectManager"));
                    changeRequest.setDeveloperName(changeRequestList.getString("developerName"));  
                    changeRequest.setProjectManagerName(changeRequestList.getString("projectmanagerName"));
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
    
    public static HashMap<String, Object> getChangeRequestsPending()
    {
        HashMap<String, Object> answer = new LinkedHashMap<>();
        answer.put("error", true);
        Connection connectionBD = ConnectionDB.getConnection();
        if (connectionBD != null)
        {
            try
            {
                String query = "SELECT c.idChangeRequest, c.justification, c.description, c.status, "
                        + "cs.status as statusName, c.creationDate, c.idDeveloper,"
                        + " d.name as developerName FROM changeRequest c "
                        + "INNER JOIN developer d ON c.idDeveloper = d.idDeveloper "
                        + "INNER JOIN changerequeststatus cs on c.status = cs.idChangeRequestStatus "
                        + "where c.status = 1";
                PreparedStatement preparedStatement = connectionBD.prepareStatement(query);
                ResultSet changeRequestList = preparedStatement.executeQuery();
                ArrayList<ChangeRequest> changeRequests = new ArrayList<>();
                while (changeRequestList.next())
                {
                    ChangeRequest changeRequest = new ChangeRequest();
                    changeRequest.setIdChangeRequest(changeRequestList.getInt("idChangeRequest"));
                    changeRequest.setJustification(changeRequestList.getString("justification"));
                    changeRequest.setDescription(changeRequestList.getString("description"));
                    changeRequest.setIdStatus(changeRequestList.getInt("status"));
                    changeRequest.setStatus(changeRequestList.getString("statusName"));
                    changeRequest.setCreationDate(changeRequestList.getString("creationDate"));
                    changeRequest.setIdDeveloper(changeRequestList.getInt("idDeveloper"));
                    changeRequest.setDeveloperName(changeRequestList.getString("developerName"));  
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
    
     public static HashMap<String, Object> searchChangeRequestByJustification(String justification)
    {
        HashMap<String, Object> answer = new LinkedHashMap<>();
        answer.put("error", true);
        Connection connectionBD = ConnectionDB.getConnection();
        if (connectionBD != null)
        {
            try
            {
                String query = "SELECT c.idChangeRequest, c.justification, c.description, c.status, "
                        + "cs.status as statusName, c.creationDate, c.reviewDate, c.idDeveloper, c.idProjectManager,"
                        + " d.name as developerName, p.name as projectManagerName FROM changeRequest c "
                        + "INNER JOIN developer d ON c.idDeveloper = d.idDeveloper "
                        + "INNER JOIN projectmanager p ON c.idProjectManager = p.idProjectManager "
                        + "INNER JOIN changerequeststatus cs on c.status = cs.idChangeRequestStatus "
                        + "WHERE c.justification like ?;";   
                PreparedStatement preparedStatement = connectionBD.prepareStatement(query);
                preparedStatement.setString(1, justification + "%");  
                ResultSet changeRequestList = preparedStatement.executeQuery();
                ArrayList<ChangeRequest> changeRequests = new ArrayList<>();
                while (changeRequestList.next())
                {
                    ChangeRequest changeRequest = new ChangeRequest();
                    changeRequest.setIdChangeRequest(changeRequestList.getInt("idChangeRequest"));
                    changeRequest.setJustification(changeRequestList.getString("justification"));
                    changeRequest.setDescription(changeRequestList.getString("description"));
                    changeRequest.setIdStatus(changeRequestList.getInt("status"));
                    changeRequest.setStatus(changeRequestList.getString("statusName"));
                    changeRequest.setCreationDate(changeRequestList.getString("creationDate"));
                    changeRequest.setReviewDate(changeRequestList.getString("reviewDate"));
                    changeRequest.setIdDeveloper(changeRequestList.getInt("idDeveloper"));
                    changeRequest.setIdProjectManager(changeRequestList.getInt("idProjectManager"));
                    changeRequest.setDeveloperName(changeRequestList.getString("developerName"));  
                    changeRequest.setProjectManagerName(changeRequestList.getString("projectmanagerName"));
                    changeRequests.add(changeRequest);
                }
                connectionBD.close();
                answer.put("error", false);
                answer.put("changerequests", changeRequests);
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
    
}
