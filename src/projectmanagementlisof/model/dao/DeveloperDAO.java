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
public class DeveloperDAO 
{
    
    public static HashMap<String, Object> getDevelopers()
    {
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
                PreparedStatement preparedStatement = connectionBD.prepareStatement(query);
                ResultSet developersList = preparedStatement.executeQuery();
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
                    developer.setIdSchoolPeriod(developersList.getInt("idSchoolPeriod"));
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
            answer.put("message", "Hubo un error al intentar conectar con la base de datos. Intente "
                    + "de nuevo más tarde");
        }
        return answer;
    }
    
    public static HashMap<String, Object> getDevelopersById(Integer idDeveloper)
    {
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
                        + "where d.enrollment = 1 and d.idDeveloper = ?";
                PreparedStatement preparedStatement = connectionBD.prepareStatement(query);
                preparedStatement.setInt(1, idDeveloper);  
                ResultSet developersList = preparedStatement.executeQuery();
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
                    developer.setIdSchoolPeriod(developersList.getInt("idSchoolPeriod"));
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
            answer.put("message", "Hubo un error al intentar conectar con la base de datos. Intente "
                    + "de nuevo más tarde");
        }
        return answer;
    }
    
    public static HashMap<String, Object> disableDeveloper(Integer idDeveloper)
    {
        HashMap<String, Object> answer = new HashMap<>();
        answer.put("error", true);
        Connection connectionBD = ConnectionDB.getConnection();
        if(connectionBD != null){
            try{
                String query = "UPDATE developer SET enrollment = 0 WHERE idDeveloper = ?;";
                PreparedStatement preparedStatement = connectionBD.prepareStatement(query);
                preparedStatement.setInt(1, idDeveloper);           
                int filasAfectadas = preparedStatement.executeUpdate();
                connectionBD.close();
                if(filasAfectadas == 1){
                    answer.put("error", false);
                    answer.put("mensaje", "Desarrollador desactivado con éxito");
                }else{
                    answer.put("mensaje", "Hubo un error al intentar desactivar la informacion del desarrollador. "
                            + "Intente de nuevo más tarde");
                }
            }catch(SQLException ex){
                answer.put("mensaje", "Error: " + ex.getMessage());
            }
        }else{
            answer.put("mensaje", "Hubo un error al intentar conectar con la base de datos. Intente "
                    + "de nuevo más tarde");
        }
        return answer;
    }
    
    public static HashMap<String, Object> searchDeveloperByName(String developerName)
    {
        HashMap<String, Object> answer = new HashMap<>();
        answer.put("error", true);
        Connection connectionBD = ConnectionDB.getConnection();
        if(connectionBD != null){
            try{
                String query = "select d.idDeveloper, d.developerLogin, d.name, d.lastname, d.secondlastname, "
                        + "d.email, d.enrollment, d.idProject, p.name as projectName, d.idSchoolPeriod, sp.startDate,"
                        + " sp.endDate from developer d inner join project p on d.idProject = p.idProject "
                        + "inner join schoolperiod sp on sp.idSchoolPeriod = d.idSchoolPeriod "
                        + "where d.name like ?;";
                PreparedStatement preparedStatement = connectionBD.prepareStatement(query);
                preparedStatement.setString(1, developerName + "%");   
                ResultSet developersList = preparedStatement.executeQuery();
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
                    developer.setIdSchoolPeriod(developersList.getInt("idSchoolPeriod"));
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
            answer.put("message", "Hubo un error al intentar conectar con la base de datos. Intente "
                    + "de nuevo más tarde");
        }
        return answer;
    }
    
    public static HashMap<String, Object> searchDeveloperByDeveloperLogin(String developerLogin)
    {
        HashMap<String, Object> answer = new HashMap<>();
        answer.put("error", true);
        Connection connectionBD = ConnectionDB.getConnection();
        if(connectionBD != null){
            try{
                String query = "select d.idDeveloper, d.developerLogin, d.name, d.lastname, d.secondlastname, "
                        + "d.email, d.enrollment, d.idProject, p.name as projectName, d.idSchoolPeriod, sp.startDate,"
                        + " sp.endDate from developer d inner join project p on d.idProject = p.idProject "
                        + "inner join schoolperiod sp on sp.idSchoolPeriod = d.idSchoolPeriod "
                        + "where d.developerLogin like ?;";
                PreparedStatement preparedStatement = connectionBD.prepareStatement(query);
                preparedStatement.setString(1, developerLogin + "%");   
                ResultSet developersList = preparedStatement.executeQuery();
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
                    developer.setIdSchoolPeriod(developersList.getInt("idSchoolPeriod"));
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
            answer.put("message", "Hubo un error al intentar conectar con la base de datos. Intente "
                    + "de nuevo más tarde");
        }
        return answer;
    }
}
