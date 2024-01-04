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
import projectmanagementlisof.model.pojo.Activity;
import projectmanagementlisof.model.pojo.Developer;

/**
 *
 * @author ferdy
 */
public class ActivityDAO
{
      public static HashMap<String, Object> registerAssignedActivity(Activity activity)
      {
            HashMap<String, Object> answer = new HashMap();
            answer.put("error", true);
            Connection connectionBD = ConnectionDB.getConnection();
            if (connectionBD != null)
            {
                  try
                  {
                        String statement =
                            "insert into activity (name, description, status, startDate, "
                            + "endDate, idDeveloper, idProjectManager, idProject) "
                            + "values(?, ?, ?, ?, ?, ?, ?, ?)";
                        PreparedStatement prepareStatement =
                            connectionBD.prepareStatement(statement);
                        prepareStatement.setString(1, activity.getName());
                        prepareStatement.setString(2, activity.getDescription());
                        prepareStatement.setInt(3, activity.getStatus());
                        prepareStatement.setString(4, activity.getStartDate());
                        prepareStatement.setString(5, activity.getEndDate());
                        prepareStatement.setInt(6, activity.getIdDeveloper());
                        prepareStatement.setInt(7, activity.getIdProjectManager());
                        prepareStatement.setInt(8, activity.getIdProject());
                        int affectedRows = prepareStatement.executeUpdate();
                        connectionBD.close();
                        if (affectedRows > 0)
                        {
                              answer.put("error", false);
                              answer.put("message", "Actividad Guardada.");
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

      public static HashMap<String, Object> registerUnassignedActivity(Activity activity)
      {
            HashMap<String, Object> answer = new HashMap();
            answer.put("error", true);
            Connection connectionBD = ConnectionDB.getConnection();
            if (connectionBD != null)
            {
                  try
                  {
                        String statement =
                            "insert into activity (name, description, status, startDate, endDate, idProjectManager, idProject) "
                            + "values(?, ?, ?, ?, ?, ?, ?)";
                        PreparedStatement prepareStatement =
                            connectionBD.prepareStatement(statement);
                        prepareStatement.setString(1, activity.getName());
                        prepareStatement.setString(2, activity.getDescription());
                        prepareStatement.setInt(3, activity.getStatus());
                        prepareStatement.setString(4, activity.getStartDate());
                        prepareStatement.setString(5, activity.getEndDate());
                        prepareStatement.setInt(6, activity.getIdProjectManager());
                        prepareStatement.setInt(7, activity.getIdProject());
                        int affectedRows = prepareStatement.executeUpdate();
                        connectionBD.close();
                        if (affectedRows > 0)
                        {
                              answer.put("error", false);
                              answer.put("message", "Actividad Guardada.");
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

      public static HashMap<String, Object> getAssignedActivities(int idDeveloper)
      {
            HashMap<String, Object> answer = new HashMap<>();
            answer.put("error", true);
            Connection connectionBD = ConnectionDB.getConnection();
            if (connectionBD != null)
            {
                  try
                  {
                        String query =
                            "select a.idActivity, a.name, a.description, a.status , s.status as statusName, "
                            + "a.startDate, a.endDate, a.idDeveloper, a.idProjectManager, a.idProject from activity a "
                            + "inner join status s on a.status = s.idStatus "
                            + "where a.idDeveloper = ?";
                        PreparedStatement preparedStatement = connectionBD.prepareStatement(query);
                        preparedStatement.setInt(1, idDeveloper);
                        ResultSet activitiesList = preparedStatement.executeQuery();
                        ArrayList<Activity> activities = new ArrayList<>();
                        while (activitiesList.next())
                        {
                              Activity activity = new Activity();
                              activity.setIdActivity(activitiesList.getInt("idActivity"));
                              activity.setName(activitiesList.getString("name"));
                              activity.setDescription(activitiesList.getString("description"));
                              activity.setStatus(activitiesList.getInt("status"));
                              activity.setStatusName(activitiesList.getString("statusName"));
                              activity.setStartDate(activitiesList.getString("startDate"));
                              activity.setEndDate(activitiesList.getString("endDate"));
                              activity.setIdDeveloper(activitiesList.getInt("idDeveloper"));
                              activity.setIdProjectManager(
                                  activitiesList.getInt("idProjectManager"));
                              activity.setIdProject(activitiesList.getInt("idProject"));
                              activities.add(activity);
                        }
                        connectionBD.close();
                        answer.put("error", false);
                        answer.put("activities", activities);
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

      public static HashMap<String, Object> getUnassignedActivities(Integer idProject)
      {
            HashMap<String, Object> answer = new HashMap<>();
            answer.put("error", true);
            Connection connectionBD = ConnectionDB.getConnection();
            if (connectionBD != null)
            {
                  try
                  {
                        String query =
                            "select a.idActivity, a.name, a.description, a.status , s.status as statusName, "
                            + "a.startDate, a.endDate, a.idDeveloper, a.idProjectManager, a.idProject from activity a "
                            + "inner join status s on a.status = s.idStatus "
                            + "where a.status = 1 and a.idProject = ?";
                        PreparedStatement preparedStatement = connectionBD.prepareStatement(query);
                        preparedStatement.setInt(1, idProject);
                        ResultSet activitiesList = preparedStatement.executeQuery();
                        ArrayList<Activity> activities = new ArrayList<>();
                        while (activitiesList.next())
                        {
                              Activity activity = new Activity();
                              activity.setIdActivity(activitiesList.getInt("idActivity"));
                              activity.setName(activitiesList.getString("name"));
                              activity.setDescription(activitiesList.getString("description"));
                              activity.setStatus(activitiesList.getInt("status"));
                              activity.setStatusName(activitiesList.getString("statusName"));
                              activity.setStartDate(activitiesList.getString("startDate"));
                              activity.setEndDate(activitiesList.getString("endDate"));
                              activity.setIdDeveloper(activitiesList.getInt("idDeveloper"));
                              activity.setIdProjectManager(
                                  activitiesList.getInt("idProjectManager"));
                              activity.setIdProject(activitiesList.getInt("idProject"));
                              activities.add(activity);
                        }
                        connectionBD.close();
                        answer.put("error", false);
                        answer.put("activities", activities);
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

      public static HashMap<String, Object> deleteActivity(Integer idActivity)
      {
            HashMap<String, Object> answer = new HashMap<>();
            answer.put("error", true);
            Connection connectionBD = ConnectionDB.getConnection();
            if (connectionBD != null)
            {
                  try
                  {
                        String query = "DELETE FROM activity WHERE idActivity = ?;";
                        PreparedStatement preparedStatement = connectionBD.prepareStatement(query);
                        preparedStatement.setInt(1, idActivity);
                        int filasAfectadas = preparedStatement.executeUpdate();
                        connectionBD.close();
                        if (filasAfectadas == 1)
                        {
                              answer.put("error", false);
                              answer.put("mensaje", "Actividad eliminada con éxito");
                        }
                        else
                        {
                              answer.put("mensaje",
                                  "Hubo un error al intentar eliminar la actividad seleccioanda. "
                                      + "Intente de nuevo más tarde");
                        }
                  }
                  catch (SQLException ex)
                  {
                        answer.put("mensaje", "Error: " + ex.getMessage());
                  }
            }
            else
            {
                  answer.put("mensaje",
                      "Hubo un error al intentar conectar con la base de datos. Intente "
                          + "de nuevo más tarde");
            }
            return answer;
      }

      public static HashMap<String, Object> searchActivity(String activityName, Integer idProject)
      {
            HashMap<String, Object> answer = new HashMap<>();
            answer.put("error", true);
            Connection connectionBD = ConnectionDB.getConnection();
            if (connectionBD != null)
            {
                  try
                  {
                        String query =
                            "select a.idActivity, a.name, a.description, a.status , s.status as statusName, "
                            + "a.startDate, a.endDate, a.idDeveloper, a.idProjectManager, a.idProject "
                            + "from activity a "
                            + "inner join status s on a.status = s.idStatus "
                            + "where a.name like ? and idProject = ?";
                        PreparedStatement preparedStatement = connectionBD.prepareStatement(query);
                        preparedStatement.setString(1, activityName + "%");
                        preparedStatement.setInt(2, idProject);
                        ResultSet activitiesList = preparedStatement.executeQuery();
                        ArrayList<Activity> activities = new ArrayList<>();
                        while (activitiesList.next())
                        {
                              Activity activity = new Activity();
                              activity.setIdActivity(activitiesList.getInt("idActivity"));
                              activity.setName(activitiesList.getString("name"));
                              activity.setDescription(activitiesList.getString("description"));
                              activity.setStatus(activitiesList.getInt("status"));
                              activity.setStatusName(activitiesList.getString("statusName"));
                              activity.setStartDate(activitiesList.getString("startDate"));
                              activity.setEndDate(activitiesList.getString("endDate"));
                              activity.setIdDeveloper(activitiesList.getInt("idDeveloper"));
                              activity.setIdProjectManager(
                                  activitiesList.getInt("idProjectManager"));
                              activity.setIdProject(activitiesList.getInt("idProject"));
                              activity.setIdProject(activitiesList.getInt("idProject"));
                              activities.add(activity);
                        }
                        connectionBD.close();
                        answer.put("error", false);
                        answer.put("activities", activities);
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

      public static HashMap<String, Object> getActivityByID(int idActividad)
      {
            HashMap<String, Object> answer = new HashMap<>();
            answer.put("error", true);
            Connection connectionBD = ConnectionDB.getConnection();
            if (connectionBD != null)
            {
                  try
                  {
                        String query =
                            "select a.idActivity, a.name, a.description, a.status , s.status as statusName, "
                            + "a.startDate, a.endDate, a.idDeveloper, a.idProjectManager, a.idProject "
                            + "from activity a "
                            + "inner join status s on a.status = s.idStatus "
                            + "where a.idActivity = ?";
                        PreparedStatement preparedStatement = connectionBD.prepareStatement(query);
                        preparedStatement.setInt(1, idActividad);
                        ResultSet activityResult = preparedStatement.executeQuery();

                        if (activityResult.next())
                        {
                              Activity activity = new Activity();
                              activity.setIdActivity(activityResult.getInt("idActivity"));
                              activity.setName(activityResult.getString("name"));
                              activity.setDescription(activityResult.getString("description"));
                              activity.setStatus(activityResult.getInt("status"));
                              activity.setStatusName(activityResult.getString("statusName"));
                              activity.setStartDate(activityResult.getString("startDate"));
                              activity.setEndDate(activityResult.getString("endDate"));
                              activity.setIdDeveloper(activityResult.getInt("idDeveloper"));
                              activity.setIdProjectManager(
                                  activityResult.getInt("idProjectManager"));
                              activity.setIdProject(activityResult.getInt("idProject"));
                              connectionBD.close();
                              answer.put("error", false);
                              answer.put("activity", activity);
                        }
                        else
                        {
                              answer.put("message", "No se encontró ninguna actividad con ese ID.");
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

      public static HashMap<String, Object> changeActivityStatus(int idActivity)
      {
            HashMap<String, Object> answer = new HashMap<>();
            answer.put("error", true);
            Connection connectionBD = ConnectionDB.getConnection();
            if (connectionBD != null)
            {
                  try
                  {
                        String query = "UPDATE activity SET status = ? WHERE idActivity = ?";
                        PreparedStatement preparedStatement = connectionBD.prepareStatement(query);
                        /* Cambia el estado a terminado */
                        preparedStatement.setInt(1, 3);
                        preparedStatement.setInt(2, idActivity);
                        int rowsAffected = preparedStatement.executeUpdate();

                        if (rowsAffected == 1)
                        {
                              connectionBD.close();
                              answer.put("error", false);
                              answer.put(
                                  "message", "Estado de la actividad actualizado correctamente.");
                        }
                        else
                        {
                              answer.put("message",
                                  "No se pudo encontrar la actividad con el ID especificado.");
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
                      "Hubo un error al conectar con la base de datos. Por favor, intenta de nuevo más tarde.");
            }
            return answer;
      }

      public static HashMap<String, Object> updateUnassignedActivity(Activity activity)
      {
            HashMap<String, Object> answer = new HashMap();
            answer.put("error", true);
            Connection connectionBD = ConnectionDB.getConnection();
            if (connectionBD != null)
            {
                  try
                  {
                        String statement =
                            "update activity set name = ?, description = ?, startDate = ?, "
                            + "endDate = ? where idActivity = ?";
                        PreparedStatement prepareStatement =
                            connectionBD.prepareStatement(statement);
                        prepareStatement.setString(1, activity.getName());
                        prepareStatement.setString(2, activity.getDescription());
                        prepareStatement.setString(3, activity.getStartDate());
                        prepareStatement.setString(4, activity.getEndDate());
                        prepareStatement.setInt(5, activity.getIdActivity());
                        int affectedRows = prepareStatement.executeUpdate();
                        connectionBD.close();
                        if (affectedRows > 0)
                        {
                              answer.put("error", false);
                              answer.put("message", "Actividad Guardada.");
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

      public static HashMap<String, Object> updateAssignedActivity(Activity activity)
      {
            HashMap<String, Object> answer = new HashMap();
            answer.put("error", true);
            Connection connectionBD = ConnectionDB.getConnection();
            if (connectionBD != null)
            {
                  try
                  {
                        String statement =
                            "update activity set name = ?, description = ?, status = ?, startDate = ?, "
                            + "endDate = ?, idDeveloper = ? where idActivity = ?";
                        PreparedStatement prepareStatement =
                            connectionBD.prepareStatement(statement);
                        prepareStatement.setString(1, activity.getName());
                        prepareStatement.setString(2, activity.getDescription());
                        prepareStatement.setInt(3, activity.getStatus());
                        prepareStatement.setString(4, activity.getStartDate());
                        prepareStatement.setString(5, activity.getEndDate());

                        if (activity.getIdDeveloper() == null)
                        {
                              prepareStatement.setNull(6, java.sql.Types.INTEGER);
                        }
                        else
                        {
                              prepareStatement.setInt(6, activity.getIdDeveloper());
                        }

                        prepareStatement.setInt(7, activity.getIdActivity());
                        int affectedRows = prepareStatement.executeUpdate();
                        connectionBD.close();
                        if (affectedRows > 0)
                        {
                              answer.put("error", false);
                              answer.put("message", "Actividad Guardada.");
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
}
