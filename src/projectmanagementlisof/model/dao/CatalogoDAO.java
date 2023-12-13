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
import projectmanagementlisof.model.pojo.Type;

/**
 *
 * @author edmun
 */
public class CatalogoDAO {
    public static List<Type> getTypes(){
        List<Type> typeList = new ArrayList<>();
        Connection conexionBD = ConnectionDB.getConnection();
        if (conexionBD != null){
            try{
                String query = "Select * From type";
                PreparedStatement preparedStatement = conexionBD.prepareStatement(query);
                ResultSet types = preparedStatement.executeQuery(query);
                while(types.next()){
                    Type type = new Type();
                    type.setIdType(types.getInt("idType"));
                    type.setType(types.getString("type"));
                    System.out.println(type.getType());
                    typeList.add(type);
                }
                conexionBD.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
        return typeList;
    }
}
