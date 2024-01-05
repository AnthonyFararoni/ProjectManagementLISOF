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
import projectmanagementlisof.model.pojo.Project;

/**
 *
 * @author edmun
 */
public class ProjectDAO
{
      public static HashMap<String, Object> isInMoreProjects(int idManager)
      {
            HashMap<String, Object> answer = new HashMap<>();
            int count = 0;
            int projects = 0;
            Connection connectionBD = ConnectionDB.getConnection();

            if (connectionBD != null)
            {
                  try
                  {
                        String query =
                            "SELECT COUNT(idProject) AS projectCount FROM projectmanagerresponsible WHERE idProjectManager = ?";
                        PreparedStatement preparedStatement = connectionBD.prepareStatement(query);
                        preparedStatement.setInt(1, idManager);

                        ResultSet projectCountResult = preparedStatement.executeQuery();

                        if (projectCountResult.next())
                        {
                              count = projectCountResult.getInt("projectCount");
                        }
                        answer.put("error", false);
                        answer.put("projects", projects);

                        connectionBD.close();
                  }
                  catch (SQLException ex)
                  {
                        answer.put("error", true);
                        answer.put("message",
                            "Ocurrió un error al conectar con la base de datos: "
                                + ex.getMessage());
                  }
            }
            else
            {
                  answer.put("error", true);
                  answer.put("message", "No hay conexión con la base de datos.");
            }

            if (count == 1)
            {
                  projects = 1;
                  answer.put("projects", projects);
            }
            else if (count > 1)
            {
                  projects = 2;
                  answer.put("projects", projects);
            }
            return answer;
      }

      public static HashMap<String, Object> getManagerProjects(int idManager)
      {
            HashMap<String, Object> answer = new HashMap<>();
            answer.put("error", true);
            Connection connectionBD = ConnectionDB.getConnection();

            if (connectionBD != null)
            {
                  try
                  {
                        String query =
                            "SELECT project.idProject, project.name, project.status, project.description "
                            + "FROM project "
                            + "INNER JOIN projectmanagerresponsible ON project.idProject = projectmanagerresponsible.idProject "
                            + "WHERE project.status = 1 AND project.idProject IN ( "
                            + "    SELECT idProject FROM projectmanagerresponsible WHERE idProjectManager = ? "
                            + ")";

                        PreparedStatement preparedStatement = connectionBD.prepareStatement(query);
                        preparedStatement.setInt(1, idManager);

                        ResultSet projectsResult = preparedStatement.executeQuery();

                        ArrayList<Project> projects = new ArrayList<>();

                        while (projectsResult.next())
                        {
                              Project project = new Project();
                              project.setIdProject(projectsResult.getInt("idProject"));
                              project.setName(projectsResult.getString("name"));
                              project.setDescription(projectsResult.getString("description"));

                              projects.add(project);
                        }

                        connectionBD.close();

                        if (!projects.isEmpty())
                        {
                              answer.put("error", false);
                              answer.put("projects", projects);
                        }
                        else
                        {
                              answer.put("message",
                                  "El gerente no tiene proyectos asignados o los proyectos no están activos.");
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
}
