/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this
 * license Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projectmanagementlisof.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import projectmanagementlisof.model.ConnectionDB;
import projectmanagementlisof.model.pojo.Change;

/**
 *
 * @author ferdy
 */
public class ChangeDAO
{
      public static HashMap<String, Object> registerChange(Change change)
      {
            HashMap<String, Object> answer = new HashMap();
            answer.put("error", true);
            Connection connectionBD = ConnectionDB.getConnection();
            if (connectionBD != null)
            {
                  try
                  {
                        String statement =
                            "insert into `change` (type, description, dateCreated, idDeveloper) values(?, ?, ?, ?)";
                        PreparedStatement prepareStatement =
                            connectionBD.prepareStatement(statement);
                        prepareStatement.setInt(1, change.getType());
                        prepareStatement.setString(2, change.getDescription());
                        prepareStatement.setString(3, change.getDateCreated());
                        prepareStatement.setInt(4, change.getIdDeveloper());
                        int affectedRows = prepareStatement.executeUpdate();
                        connectionBD.close();
                        if (affectedRows > 0)
                        {
                              answer.put("error", false);
                              answer.put("message", "Cambio Registrado.");
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

      public static HashMap<String, Object> getChangesByDeveloperId(int idDeveloper)
      {
            HashMap<String, Object> answer = new HashMap<>();
            answer.put("error", true);
            Connection connectionBD = ConnectionDB.getConnection();
            if (connectionBD != null)
            {
                  try
                  {
                        String query =
                            "SELECT ch.idChange, ch.type, ch.description, ch.dateCreated, "
                            + "t.idType AS typeId, t.type AS typeName, "
                            + "ch.idDeveloper "
                            + "FROM `Change` ch "
                            + "INNER JOIN `Type` t ON ch.type = t.idType "
                            + "WHERE ch.idDeveloper = ?";
                        PreparedStatement preparedStatement = connectionBD.prepareStatement(query);
                        preparedStatement.setInt(1, idDeveloper);
                        ResultSet activitiesList = preparedStatement.executeQuery();
                        ArrayList<Change> changes = new ArrayList<>();
                        while (activitiesList.next())
                        {
                              Change change = new Change();
                              change.setIdChange(activitiesList.getInt("idChange"));
                              change.setType(activitiesList.getInt("type"));
                              change.setTypeName(activitiesList.getString("typeName"));
                              change.setDescription(activitiesList.getString("description"));
                              change.setDateCreated(activitiesList.getString("dateCreated"));

                              changes.add(change);
                        }
                        connectionBD.close();
                        answer.put("error", false);
                        answer.put("changes", changes);
                  }
                  catch (SQLException ex)
                  {
                        System.out.println(ex.getMessage());
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

      public static HashMap<String, Object> getChangeDetails(int idChange)
      {
            HashMap<String, Object> answer = new HashMap<>();
            answer.put("error", true);
            Connection connectionBD = ConnectionDB.getConnection();
            if (connectionBD != null)
            {
                  try
                  {
                        String query =
                            "SELECT ch.idChange, ch.type, ch.description, ch.dateCreated, "
                            + "t.idType AS typeId, t.type AS typeName, "
                            + "ch.idDeveloper "
                            + "FROM `Change` ch "
                            + "INNER JOIN `Type` t ON ch.type = t.idType "
                            + "WHERE ch.idChange = ?";
                        PreparedStatement preparedStatement = connectionBD.prepareStatement(query);
                        preparedStatement.setInt(1, idChange);
                        ResultSet changeResult = preparedStatement.executeQuery();

                        if (changeResult.next())
                        {
                              Change change = new Change();
                              change.setIdChange(changeResult.getInt("idChange"));
                              change.setType(changeResult.getInt("type"));
                              change.setTypeName(changeResult.getString("typeName"));
                              change.setDescription(changeResult.getString("description"));
                              change.setDateCreated(changeResult.getString("dateCreated"));
                              change.setIdDeveloper(changeResult.getInt("idDeveloper"));

                              connectionBD.close();
                              answer.put("error", false);
                              answer.put("change", change);
                        }
                        else
                        {
                              answer.put(
                                  "message", "No se encontró el cambio con el ID proporcionado.");
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
}
