/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projectmanagementlisof.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import projectmanagementlisof.model.ConnectionDB;
import projectmanagementlisof.model.pojo.Activity;

/**
 *
 * @author ferdy
 */
public class ActivityDAO {
    public static HashMap<String, Object> registerActivity(Activity activity){
        HashMap<String, Object> answer = new HashMap();
        answer.put("error",true);
        Connection connectionBD = ConnectionDB.getConnection();
        if(connectionBD != null){
            try{
                String statement = "insert into activity (name, description, status, startDate, "+
                    "endDate, idDeveloper, idProjectManager) " + "values(?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement prepareStatement = connectionBD.prepareStatement(statement);
                prepareStatement.setString(1, activity.getName());
                prepareStatement.setString(2, activity.getDescription());
                prepareStatement.setInt(3, activity.getStatus());
                prepareStatement.setString(4, activity.getStartDate());
                prepareStatement.setString(5, activity.getEndDate());
                prepareStatement.setInt(6, activity.getIdDeveloper());
                prepareStatement.setInt(7, activity.getIdProjectManager());
                int  affectedRows = prepareStatement.executeUpdate();
                connectionBD.close();
                if(affectedRows > 0){
                    answer.put("error", false);
                    answer.put("message", "Actividad Guardada.");
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
