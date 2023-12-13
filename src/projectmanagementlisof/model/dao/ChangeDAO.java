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
import projectmanagementlisof.model.pojo.Change;

/**
 *
 * @author ferdy
 */
public class ChangeDAO {
    public static HashMap<String, Object> registerActivity(Change change){
        HashMap<String, Object> answer = new HashMap();
        answer.put("error",true);
        Connection connectionBD = ConnectionDB.getConnection();
        if(connectionBD != null){
            try{
                String statement = "insert into change (type, description, dateCreated, ideDeveloper" + 
                        "values(?, ?, ?, ?)";
                PreparedStatement prepareStatement = connectionBD.prepareStatement(statement);
                prepareStatement.setInt(1, change.getType());
                prepareStatement.setString(2, change.getDescription());
                prepareStatement.setString(3, change.getDateCreated());
                prepareStatement.setInt(4, change.getIdDeveloper());
                int  affectedRows = prepareStatement.executeUpdate();
                connectionBD.close();
                if(affectedRows > 0){
                    answer.put("error", false);
                    answer.put("message", "Cambio Registrado.");
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
