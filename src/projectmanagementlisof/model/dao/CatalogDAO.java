/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projectmanagementlisof.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import projectmanagementlisof.model.ConnectionDB;
import projectmanagementlisof.model.pojo.CorrectionType;

/**
 *
 * @author edmun
 */
public class CatalogDAO {
     public static List<CorrectionType> getTypes(){
        List<CorrectionType> typeList = new ArrayList<>();
        Connection conexionBD = ConnectionDB.getConnection();
        if (conexionBD != null){
            try{
                String query = "Select * From type";
                PreparedStatement preparedStatement = conexionBD.prepareStatement(query);
                ResultSet types = preparedStatement.executeQuery(query);
                while(types.next()){
                    CorrectionType type = new CorrectionType();
                    type.setIdType(types.getInt("idType"));
                    type.setTypeName(types.getString("type"));
                    typeList.add(type);
                }
                conexionBD.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
        return typeList;
    }
    public static int checkProjectManager(String user, String password) {
        int result = 0;
        Connection connectionBD = ConnectionDB.getConnection();

        if (connectionBD != null) {
            try {
                String query = "SELECT COUNT(*) AS count FROM projectmanager WHERE managerLogin = ? AND password = ?";
                PreparedStatement preparedStatement = connectionBD.prepareStatement(query);
                preparedStatement.setString(1, user);
                preparedStatement.setString(2, password);

                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    int count = resultSet.getInt("count");
                    if (count > 0) {
                        result = 1;
                    }
                }
                connectionBD.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }
}
