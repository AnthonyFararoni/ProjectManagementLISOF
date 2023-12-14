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

      public static HashMap<String, Object> updateChangeRequestStatus(ChangeRequest changeRequest)
      {
        HashMap<String, Object> answer = new HashMap();
        answer.put("error",true);
        Connection connectionBD = ConnectionDB.getConnection();
        if(connectionBD != null){
            try{
                String statement = "update changerequest set status = ?, reviewDate = ?, idProjectManager = ?, " + 
                        "where idChangeRequest = ?";
                PreparedStatement prepareStatement = connectionBD.prepareStatement(statement);
                prepareStatement.setInt(1, changeRequest.getIdStatus());
                prepareStatement.setString(2, changeRequest.getReviewDate());
                prepareStatement.setInt(3, changeRequest.getIdProjectManager());
                prepareStatement.setInt(4, changeRequest.getIdChangeRequest());
                int  affectedRows = prepareStatement.executeUpdate();
                connectionBD.close();
                if(affectedRows > 0){
                    answer.put("error", false);
                    answer.put("message", "Cambio Guardado.");
                }else{
                    answer.put("message", "Error en la base de datos.");
                }
                
            }catch(SQLException e){
                answer.put("message", "Error: "+ e.getMessage());
            }          
        }else{
            answer.put("message", "Error en la conexion a la base de datos.");
        }
        return answer;
      }
}
