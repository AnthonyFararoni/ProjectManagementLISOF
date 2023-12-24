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
import projectmanagementlisof.model.pojo.Defect;

/**
 *
 * @author edmun
 */
public class DefectDAO
{
      public static HashMap<String, Object> registerDefect(Defect defect)
      {
            HashMap<String, Object> answer = new HashMap<>();
            answer.put("error", true);

            String query =
                "insert into defect(type, description, timecost, dateFound, idDeveloper) values (?,?,?,?,?)";
            Connection connectionBD = null;
            PreparedStatement statement = null;

            try
            {
                  connectionBD = ConnectionDB.getConnection();
                  if (connectionBD != null)
                  {
                        statement = connectionBD.prepareStatement(query);
                        statement.setInt(1, defect.getType());
                        statement.setString(2, defect.getDescription());
                        statement.setInt(3, defect.getTimeCost());
                        statement.setString(4, defect.getDate());
                        statement.setInt(5, defect.getIdDeveloper());

                        int result = statement.executeUpdate();
                        connectionBD.close();

                        if (result > 0)
                        {
                              answer.put("error", false);
                              answer.put("message", "Defecto registrado correctamente.");
                        }
                        else
                        {
                              answer.put("message", "Error al registrar el defecto.");
                        }
                  }
                  else
                  {
                        answer.put("message", "Error en la conexión a la base de datos.");
                  }
            }
            catch (SQLException e)
            {
                  answer.put("message", "Error: " + e.getMessage());
            }
            finally
            {
                  try
                  {
                        if (statement != null)
                        {
                              statement.close();
                        }
                        if (connectionBD != null)
                        {
                              connectionBD.close();
                        }
                  }
                  catch (SQLException ex)
                  {
                        answer.put("message", "Error al cerrar la conexión: " + ex.getMessage());
                  }
            }
            return answer;
      }

      public static HashMap<String, Object> getDefectsById(int idDeveloper)
      {
            HashMap<String, Object> answer = new HashMap<>();
            answer.put("error", true);
            Connection connectionBD = ConnectionDB.getConnection();
            if (connectionBD != null)
            {
                  try
                  {
                        String query = "SELECT * FROM defect WHERE idDeveloper = ?";
                        PreparedStatement preparedStatement = connectionBD.prepareStatement(query);
                        preparedStatement.setInt(1, idDeveloper);
                        ResultSet defectsList = preparedStatement.executeQuery();
                        ArrayList<Defect> defects = new ArrayList<>();

                        while (defectsList.next())
                        {
                              Defect defect = new Defect();
                              defect.setIdDeveloper(defectsList.getInt("idDeveloper"));
                              defect.setType(defectsList.getInt("type"));
                              defect.setDescription(defectsList.getString("description"));
                              defect.setTimeCost(defectsList.getInt("timecost"));
                              defect.setDate(defectsList.getString("dateFound"));
                              defect.setIdDefect(defectsList.getInt("idDefect"));
                              defects.add(defect);
                        }

                        connectionBD.close();
                        answer.put("error", false);
                        answer.put("defects", defects);
                  }
                  catch (SQLException ex)
                  {
                        answer.put("message", "Error: " + ex.getMessage());
                  }
            }
            else
            {
                  answer.put("message",
                      "Hubo un error al intentar conectar con la base de datos. Intenta de nuevo más tarde");
            }
            return answer;
      }

      public static HashMap<String, Object> getDefectById(int idDefect)
      {
            HashMap<String, Object> answer = new HashMap<>();
            answer.put("error", true);
            Connection connectionBD = ConnectionDB.getConnection();
            if (connectionBD != null)
            {
                  try
                  {
                        String query = "SELECT * FROM defect WHERE idDefect = ?";
                        PreparedStatement preparedStatement = connectionBD.prepareStatement(query);
                        preparedStatement.setInt(1, idDefect);
                        ResultSet defectResult = preparedStatement.executeQuery();

                        if (defectResult.next())
                        {
                              Defect defect = new Defect();
                              defect.setIdDefect(defectResult.getInt("idDefect"));
                              defect.setType(defectResult.getInt("type"));
                              defect.setDescription(defectResult.getString("description"));
                              defect.setTimeCost(defectResult.getInt("timecost"));
                              defect.setDate(defectResult.getString("dateFound"));
                              defect.setIdDeveloper(defectResult.getInt("idDeveloper"));

                              connectionBD.close();
                              answer.put("error", false);
                              answer.put("defect", defect);
                        }
                        else
                        {
                              answer.put("message", "No se encontró ningún defecto con ese ID.");
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
                      "Hubo un error al intentar conectar con la base de datos. Intenta de nuevo más tarde");
            }
            return answer;
      }
}
