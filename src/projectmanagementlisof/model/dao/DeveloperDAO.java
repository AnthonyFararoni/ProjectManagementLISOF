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
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import projectmanagementlisof.model.ConnectionDB;
import projectmanagementlisof.model.pojo.Developer;

/**
 *
 * @author nando
 */
public class DeveloperDAO
{
      public static HashMap<String, Object> getDevelopers(int idProject)
      {
            HashMap<String, Object> answer = new LinkedHashMap<>();
            answer.put("error", true);
            Connection connectionBD = ConnectionDB.getConnection();
            if (connectionBD != null)
            {
                  try
                  {
                        String query =
                            "select d.idDeveloper, d.developerLogin, d.name, d.lastname, d.secondlastname, "
                            + "d.email, d.enrollment, d.idProject, p.name as projectName, d.idSchoolPeriod, sp.startDate,"
                            + "sp.endDate from developer d "
                            + "inner join project p on d.idProject = p.idProject "
                            + "inner join schoolperiod sp on sp.idSchoolPeriod = d.idSchoolPeriod "
                            + "where d.enrollment = 1 and d.idProject = ?";
                        PreparedStatement preparedStatement = connectionBD.prepareStatement(query);
                        preparedStatement.setInt(1, idProject);
                        ResultSet developersList = preparedStatement.executeQuery();
                        ArrayList<Developer> developers = new ArrayList<>();
                        while (developersList.next())
                        {
                              Developer developer = new Developer();
                              developer.setIdDeveloper(developersList.getInt("idDeveloper"));
                              developer.setDeveloperLogin(
                                  developersList.getString("developerLogin"));
                              developer.setName(developersList.getString("name"));
                              developer.setLastName(developersList.getString("lastname"));
                              developer.setSecondLastName(
                                  developersList.getString("secondlastname"));
                              developer.setEmail(developersList.getString("email"));
                              developer.setEnrollment(developersList.getBoolean("enrollment"));
                              developer.setIdProject(developersList.getInt("idProject"));
                              developer.setProjectName(developersList.getString("projectName"));
                              developer.setStarDateSchoolPeriod(
                                  developersList.getString("startDate"));
                              developer.setEndDateSchoolPeriod(developersList.getString("endDate"));
                              developers.add(developer);
                        }
                        connectionBD.close();
                        answer.put("error", false);
                        answer.put("developers", developers);
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

      public static boolean validateDeveloper(Integer idDeveloper)
      {
            boolean resultValidation = false;
            try
            {
                  String query = "select * from activity where idDeveloper = ?;";
                  ConnectionDB connectionDB = new ConnectionDB();
                  Connection connection = connectionDB.getConnection();
                  PreparedStatement statement = connection.prepareStatement(query);
                  statement.setInt(1, idDeveloper);
                  ResultSet resultSet = statement.executeQuery();
                  if (!resultSet.next())
                  {
                        resultValidation = true;
                  }
            }
            catch (SQLException ex)
            {
                  ex.printStackTrace();
            }
            return resultValidation;
      }

      public static HashMap<String, Object> disableDeveloper(Integer idDeveloper)
      {
            HashMap<String, Object> answer = new HashMap<>();
            answer.put("error", true);
            Connection connectionBD = ConnectionDB.getConnection();
            if (connectionBD != null)
            {
                  try
                  {
                        String query = "UPDATE developer SET enrollment = 0 WHERE idDeveloper = ?;";
                        PreparedStatement preparedStatement = connectionBD.prepareStatement(query);
                        preparedStatement.setInt(1, idDeveloper);
                        int filasAfectadas = preparedStatement.executeUpdate();
                        connectionBD.close();
                        if (filasAfectadas == 1)
                        {
                              answer.put("error", false);
                              answer.put("mensaje", "Desarrollador desactivado con éxito");
                        }
                        else
                        {
                              answer.put("mensaje",
                                  "Hubo un error al intentar desactivar la informacion del desarrollador. "
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

      public static HashMap<String, Object> searchDeveloperByName(String developerName, int idProject)
      {
            HashMap<String, Object> answer = new HashMap<>();
            answer.put("error", true);
            Connection connectionBD = ConnectionDB.getConnection();
            if (connectionBD != null)
            {
                  try
                  {
                        String query =
                            "select d.idDeveloper, d.developerLogin, d.name, d.lastname, d.secondlastname, "
                            + "d.email, d.enrollment, d.idProject, p.name as projectName, d.idSchoolPeriod, sp.startDate,"
                            + " sp.endDate from developer d inner join project p on d.idProject = p.idProject "
                            + "inner join schoolperiod sp on sp.idSchoolPeriod = d.idSchoolPeriod "
                            + "where d.name like ? and d.idProject = ?;";
                        PreparedStatement preparedStatement = connectionBD.prepareStatement(query);
                        preparedStatement.setString(1, developerName + "%");
                        preparedStatement.setInt(2, idProject);
                        ResultSet developersList = preparedStatement.executeQuery();
                        ArrayList<Developer> developers = new ArrayList<>();
                        while (developersList.next())
                        {
                              Developer developer = new Developer();
                              developer.setIdDeveloper(developersList.getInt("idDeveloper"));
                              developer.setDeveloperLogin(
                                  developersList.getString("developerLogin"));
                              developer.setName(developersList.getString("name"));
                              developer.setLastName(developersList.getString("lastname"));
                              developer.setSecondLastName(
                                  developersList.getString("secondlastname"));
                              developer.setEmail(developersList.getString("email"));
                              developer.setEnrollment(developersList.getBoolean("enrollment"));
                              developer.setIdProject(developersList.getInt("idProject"));
                              developer.setProjectName(developersList.getString("projectName"));
                              developer.setIdSchoolPeriod(developersList.getInt("idSchoolPeriod"));
                              developer.setStarDateSchoolPeriod(
                                  developersList.getString("startDate"));
                              developer.setEndDateSchoolPeriod(developersList.getString("endDate"));
                              developers.add(developer);
                        }
                        connectionBD.close();
                        answer.put("error", false);
                        answer.put("developers", developers);
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

      public static HashMap<String, Object> searchDeveloperByDeveloperLogin(String developerLogin, int idProject)
      {
            HashMap<String, Object> answer = new HashMap<>();
            answer.put("error", true);
            Connection connectionBD = ConnectionDB.getConnection();
            if (connectionBD != null)
            {
                  try
                  {
                        String query =
                            "select d.idDeveloper, d.developerLogin, d.name, d.lastname, d.secondlastname, "
                            + "d.email, d.enrollment, d.idProject, p.name as projectName, d.idSchoolPeriod, sp.startDate,"
                            + " sp.endDate from developer d inner join project p on d.idProject = p.idProject "
                            + "inner join schoolperiod sp on sp.idSchoolPeriod = d.idSchoolPeriod "
                            + "where d.developerLogin like ? and d.idProject = ?;";
                        PreparedStatement preparedStatement = connectionBD.prepareStatement(query);
                        preparedStatement.setString(1, developerLogin + "%");
                        preparedStatement.setInt(2, idProject);
                        ResultSet developersList = preparedStatement.executeQuery();
                        ArrayList<Developer> developers = new ArrayList<>();
                        while (developersList.next())
                        {
                              Developer developer = new Developer();
                              developer.setIdDeveloper(developersList.getInt("idDeveloper"));
                              developer.setDeveloperLogin(
                                  developersList.getString("developerLogin"));
                              developer.setName(developersList.getString("name"));
                              developer.setLastName(developersList.getString("lastname"));
                              developer.setSecondLastName(
                                  developersList.getString("secondlastname"));
                              developer.setEmail(developersList.getString("email"));
                              developer.setEnrollment(developersList.getBoolean("enrollment"));
                              developer.setIdProject(developersList.getInt("idProject"));
                              developer.setProjectName(developersList.getString("projectName"));
                              developer.setIdSchoolPeriod(developersList.getInt("idSchoolPeriod"));
                              developer.setStarDateSchoolPeriod(
                                  developersList.getString("startDate"));
                              developer.setEndDateSchoolPeriod(developersList.getString("endDate"));
                              developers.add(developer);
                        }
                        connectionBD.close();
                        answer.put("error", false);
                        answer.put("developers", developers);
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

      public static HashMap<String, Object> getDeveloperById(Integer idDeveloper)
      {
            HashMap<String, Object> answer = new LinkedHashMap<>();
            answer.put("error", true);
            Connection connectionBD = ConnectionDB.getConnection();
            if (connectionBD != null)
            {
                  try
                  {
                        String query =
                            "select d.idDeveloper, d.developerLogin, d.name, d.lastname, d.secondlastname, "
                            + "d.email, d.enrollment, d.idProject, p.name as projectName, d.idSchoolPeriod, sp.startDate,"
                            + "sp.endDate from developer d "
                            + "inner join project p on d.idProject = p.idProject "
                            + "inner join schoolperiod sp on sp.idSchoolPeriod = d.idSchoolPeriod "
                            + "where d.idDeveloper = ?;";
                        PreparedStatement prepararSentencia = connectionBD.prepareStatement(query);
                        prepararSentencia.setInt(1, idDeveloper);
                        ResultSet developersList = prepararSentencia.executeQuery();
                        ArrayList<Developer> developers = new ArrayList<>();
                        while (developersList.next())
                        {
                              Developer developer = new Developer();
                              developer.setIdDeveloper(developersList.getInt("idDeveloper"));
                              developer.setDeveloperLogin(
                                  developersList.getString("developerLogin"));
                              developer.setName(developersList.getString("name"));
                              developer.setLastName(developersList.getString("lastname"));
                              developer.setSecondLastName(
                                  developersList.getString("secondlastname"));
                              developer.setEmail(developersList.getString("email"));
                              developer.setEnrollment(developersList.getBoolean("enrollment"));
                              developer.setIdProject(developersList.getInt("idProject"));
                              developer.setProjectName(developersList.getString("projectName"));
                              developer.setIdSchoolPeriod(developersList.getInt("idSchoolPeriod"));
                              developer.setStarDateSchoolPeriod(
                                  developersList.getString("startDate"));
                              developer.setEndDateSchoolPeriod(developersList.getString("endDate"));
                              developers.add(developer);
                        }
                        connectionBD.close();
                        answer.put("error", false);
                        answer.put("developers", developers);
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

      public static HashMap<String, Object> getDeveloperByCredentials(String user, String password)
      {
            HashMap<String, Object> answer = new HashMap<>();
            answer.put("error", true);
            Connection connectionBD = ConnectionDB.getConnection();

            if (connectionBD != null)
            {
                  try
                  {
                        String query =
                            "SELECT * FROM developer WHERE developerLogin = ? AND password = ?";
                        PreparedStatement preparedStatement = connectionBD.prepareStatement(query);
                        preparedStatement.setString(1, user);
                        preparedStatement.setString(2, password);

                        ResultSet developerResult = preparedStatement.executeQuery();

                        if (developerResult.next())
                        {
                              Developer developer = new Developer();
                              developer.setIdDeveloper(developerResult.getInt("idDeveloper"));
                              developer.setDeveloperLogin(
                                  developerResult.getString("developerLogin"));
                              developer.setName(developerResult.getString("name"));
                              developer.setLastName(developerResult.getString("lastname"));
                              developer.setSecondLastName(
                                  developerResult.getString("secondlastname"));
                              developer.setEmail(developerResult.getString("email"));
                              developer.setEnrollment(developerResult.getBoolean("enrollment"));
                              developer.setIdProject(developerResult.getInt("idProject"));

                              connectionBD.close();
                              answer.put("error", false);
                              answer.put("developer", developer);
                        }
                        else
                        {
                              answer.put("message",
                                  "No se encontró ningún desarrollador con las credenciales proporcionadas.");
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
                      "Hubo un error al intentar conectar con la base de datos. Intente de nuevo más tarde.");
            }

            return answer;
      }

      public static HashMap<String, Object> checkDeveloper(String user)
      {
            HashMap<String, Object> answer = new HashMap<>();
            answer.put("error", true);
            int exists = 0;
            Connection connectionBD = ConnectionDB.getConnection();

            if (connectionBD != null)
            {
                  try
                  {
                        String query =
                            "SELECT COUNT(*) AS count FROM developer WHERE developerLogin = ?";
                        PreparedStatement preparedStatement = connectionBD.prepareStatement(query);
                        preparedStatement.setString(1, user);
                        ResultSet result = preparedStatement.executeQuery();

                        if (result.next())
                        {
                              int count = result.getInt("count");
                              exists = (count > 0) ? 1 : 0;
                              answer.put("error", false);
                        }

                        connectionBD.close();
                  }
                  catch (SQLException ex)
                  {
                        answer.put("message", "Error: " + ex.getMessage());
                  }
            }
            else
            {
                  answer.put("message", "No ha sido posible conectar con la base de datos.");
            }

            answer.put("exists", exists);
            return answer;
      }

      public static HashMap<String, Object> checkDeveloperLogIn(String user, String password)
      {
            HashMap<String, Object> answer = new HashMap<>();
            HashMap<String, Object> userExist = new HashMap<>();
            answer.put("error", true);
            int result = 0;
            Connection connectionBD = ConnectionDB.getConnection();
            if (connectionBD != null)
            {
                  userExist = checkDeveloper(user);
                  int exists = (int) userExist.get("exists");
                  if (exists == 1)
                  {
                        result = 1;
                        try
                        {
                              String query =
                                  "SELECT COUNT(*) AS count FROM developer WHERE developerLogin = ? AND password = ?";
                              PreparedStatement preparedStatement =
                                  connectionBD.prepareStatement(query);
                              preparedStatement.setString(1, user);
                              preparedStatement.setString(2, password);

                              ResultSet resultSet = preparedStatement.executeQuery();

                              if (resultSet.next())
                              {
                                    int count = resultSet.getInt("count");
                                    if (count > 0)
                                    {
                                          result = 2;
                                          answer.put("error", false);
                                    }
                              }
                              connectionBD.close();
                        }
                        catch (SQLException ex)
                        {
                              answer.put("message", "Error: " + ex.getMessage());
                        }
                  }
                  else
                  {
                        answer.put("error", false);
                        answer.put("message", "Usuario no existe");
                  }
            }
            else
            {
                  answer.put("message", "No ha sido posible conectar con la base de datos.");
            }

            answer.put("result", result);
            return answer;
      }
}
