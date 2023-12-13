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
import java.util.HashMap;
import java.util.LinkedHashMap;
import projectmanagementlisof.model.ConnectionDB;
import projectmanagementlisof.model.pojo.Developer;

/**
 *
 * @author nando
 */
public class DeveloperDAO {
    
    public static HashMap<String, Object> getDevelopers(){
        HashMap<String, Object> answer = new LinkedHashMap<>();
        answer.put("error", true);
        Connection connectionBD = ConnectionDB.getConnection();
        if(connectionBD != null){
            try {
                String query = "select d.idDeveloper, d.developerLogin, d.name, d.lastname, d.secondlastname, "
                        + "d.email, d.enrollment, d.idProject, p.name as projectName, d.idSchoolPeriod, sp.startDate,"
                        + "sp.endDate from developer d "
                        + "inner join project p on d.idProject = p.idProject "
                        + "inner join schoolperiod sp on sp.idSchoolPeriod = d.idSchoolPeriod "
                        + "where d.enrollment = 1";
                PreparedStatement prepararSentencia = connectionBD.prepareStatement(query);
                ResultSet developersList = prepararSentencia.executeQuery();
                ArrayList<Developer> developers = new ArrayList<>();                
                while(developersList.next()){
                    Developer developer = new Developer();
                    developer.setIdDeveloper(developersList.getInt("idDeveloper"));
                    developer.setDeveloperLogin(developersList.getString("developerLogin"));
                    developer.setName(developersList.getString("name"));
                    developer.setLastName(developersList.getString("lastname"));
                    developer.setSecondLastName(developersList.getString("secondlastname"));
                    developer.setEmail(developersList.getString("email"));
                    developer.setEnrollment(developersList.getBoolean("enrollment"));
                    developer.setIdProject(developersList.getInt("idProject"));
                    developer.setProjectName(developersList.getString("projectName"));
                    developer.setStarDateSchoolPeriod(developersList.getString("startDate"));
                    developer.setEndDateSchoolPeriod(developersList.getString("endDate"));
                    developers.add(developer);
                }
                connectionBD.close();
                answer.put("error", false);
                answer.put("developers", developers);
            } catch (SQLException ex) {
                answer.put("message","Error: " + ex.getMessage());
            }     
        }else{
            answer.put("message", "Por el momento no hay conexión. Intente más tarde.");
        }
        return answer;
    }
}
