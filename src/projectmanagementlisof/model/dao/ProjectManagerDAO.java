/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projectmanagementlisof.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import projectmanagementlisof.model.ConnectionDB;
import projectmanagementlisof.model.pojo.ProjectManager;

/**
 *
 * @author edmun
 */
public class ProjectManagerDAO {
    public static HashMap<String, Object> checkProjectManager(String user) {
        HashMap<String, Object> answer = new HashMap<>();
        answer.put("error", true);
        int exists = 0;
        Connection connectionBD = ConnectionDB.getConnection();

        if (connectionBD != null) {
            try {
                String query = "SELECT COUNT(*) AS count FROM projectmanager WHERE managerLogin = ?";
                PreparedStatement preparedStatement = connectionBD.prepareStatement(query);
                preparedStatement.setString(1, user);
                ResultSet result = preparedStatement.executeQuery();

                if (result.next()) {
                    int count = result.getInt("count");
                    exists = (count > 0) ? 1 : 0;
                    answer.put("error", false);
                }

                connectionBD.close();
            } catch (SQLException ex) {
                answer.put("message", "Error: " + ex.getMessage());
            }
        } else {
            answer.put("message", "No ha sido posible conectar con la base de datos.");
        }

        answer.put("exists", exists);
        return answer;
    }

    public static HashMap<String, Object> checkProjectManagerLogIn(String user, String password) {
        HashMap<String, Object> answer = new HashMap<>();
        HashMap<String, Object> userExist =  new HashMap<>();
        answer.put("error", true);
        int result = 0;
        Connection connectionBD = ConnectionDB.getConnection();
        if (connectionBD != null) {
            userExist =  checkProjectManager(user);
            int exists = (int) userExist.get("exists");
            if(exists == 1){
                result = 1;
                try {
                String query = "SELECT COUNT(*) AS count FROM projectmanager WHERE managerLogin = ? AND password = ?";
                PreparedStatement preparedStatement = connectionBD.prepareStatement(query);
                preparedStatement.setString(1, user);
                preparedStatement.setString(2, password);

                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    int count = resultSet.getInt("count");
                    if (count > 0) {
                        result = 2;
                        answer.put("error", false);
                    }
                }
                connectionBD.close();
                } catch (SQLException ex) {
                    answer.put("message", "Error: " + ex.getMessage());
                }
            } else { 
                answer.put("error", false);
                answer.put("message", "Usuario no existe");
            }
        } else {
            answer.put("message", "No ha sido posible conectar con la base de datos.");
        }
        answer.put("result", result);
        return answer;
    }
    
    public static HashMap<String, Object> getProjectManagerByCredentials(String user, String password) {
        HashMap<String, Object> answer = new HashMap<>();
        answer.put("error", true);
        Connection connectionBD = ConnectionDB.getConnection();

        if (connectionBD != null) {
            try {
                String query = "SELECT * FROM projectmanager WHERE managerLogin = ? AND password = ?";
                PreparedStatement preparedStatement = connectionBD.prepareStatement(query);
                preparedStatement.setString(1, user);
                preparedStatement.setString(2, password);

                ResultSet managerResult = preparedStatement.executeQuery();

                if (managerResult.next()) {
                    ProjectManager manager = new ProjectManager();
                    manager.setName(managerResult.getString("name"));
                    manager.setLastName(managerResult.getString("lastname"));
                    manager.setSecondLastname(managerResult.getString("secondLastname"));
                    manager.setFullName();
                    manager.setManagerLogin(managerResult.getString("managerLogin"));
                    manager.setManagerId(managerResult.getInt("idProjectManager"));

                    connectionBD.close();
                    answer.put("error", false);
                    answer.put("projectManager", manager);
                } else {
                    answer.put("message", "No se encontró ningún responsable del proyecto con las credenciales proporcionadas.");
                }
            } catch (SQLException ex) {
                answer.put("message", "Error: " + ex.getMessage());
            }
        } else {
            answer.put("message", "Hubo un error al intentar conectar con la base de datos. Intente de nuevo más tarde.");
        }
        return answer;
    }
    
}
