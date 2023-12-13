/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projectmanagementlisof.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import projectmanagementlisof.model.ConnectionDB;
import projectmanagementlisof.model.pojo.Defect;

/**
 *
 * @author edmun
 */
public class DefectDAO {
    public int registerDefect(Defect defect)
    {
        String query = "insert into defect(type, description, timecost, dateFound, idDeveloper) values (?,?,?,?,?)";
        Connection conectionBD;
        PreparedStatement statement;
        int result = 0;
        try
        {
            conectionBD = ConnectionDB.getConnection();
            statement = conectionBD.prepareStatement(query);
            statement.setInt(1, defect.getType());
            statement.setString(2, defect.getDescription());
            statement.setInt(3, defect.getTimeCost());
            statement.setString(4, defect.getDate());
            statement.setInt(5, defect.getIdDeveloper());
            result = statement.executeUpdate();
        }
        catch(SQLException e)
        {
            result = -1;
        }
        return result;
    }
}
