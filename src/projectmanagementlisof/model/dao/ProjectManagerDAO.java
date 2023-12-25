/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projectmanagementlisof.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import projectmanagementlisof.model.ConnectionDB;

/**
 *
 * @author nando
 */
public class ProjectManagerDAO {
    
    public static int validateLoginProjectManager(String managerLogin, String password){
    int registerResult = 0;
    try {             
            Connection connectionDB = ConnectionDB.getConnection();
            String query = "select * from projectManager where managerLogin = ? and password = ?;";
            PreparedStatement preparedStatement = connectionDB.prepareStatement(query);
            preparedStatement.setString(1, managerLogin);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                registerResult = 1;
            }else{
                registerResult = -1;
            }
            return registerResult;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return registerResult;
    }
}
