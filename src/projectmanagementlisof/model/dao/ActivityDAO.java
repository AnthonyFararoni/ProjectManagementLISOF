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
import projectmanagementlisof.model.ConnectionDB;
import projectmanagementlisof.model.pojo.Activity;
import projectmanagementlisof.model.pojo.Developer;

/**
 *
 * @author ferdy
 */
public class ActivityDAO {
    
    public static HashMap<String, Object> registerAssignedActivity(Activity activity){
        HashMap<String, Object> answer = new HashMap();
        answer.put("error",true);
        Connection connectionBD = ConnectionDB.getConnection();
        if(connectionBD != null){
            try{
                String statement = "insert into activity (name, description, status, startDate, "+
                    "endDate, idDeveloper) " + "values(?, ?, ?, ?, ?, ?)";
                PreparedStatement prepareStatement = connectionBD.prepareStatement(statement);
                prepareStatement.setString(1, activity.getName());
                prepareStatement.setString(2, activity.getDescription());
                prepareStatement.setInt(3, activity.getStatus());
                prepareStatement.setString(4, activity.getStartDate());
                prepareStatement.setString(5, activity.getEndDate());
                prepareStatement.setInt(6, activity.getIdDeveloper());
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
    
    public static HashMap<String, Object> registerUnassignedActivity(Activity activity){
        HashMap<String, Object> answer = new HashMap();
        answer.put("error",true);
        Connection connectionBD = ConnectionDB.getConnection();
        if(connectionBD != null){
            try{
                String statement = "insert into activity (name, description, status, startDate, endDate) " + 
                        "values(?, ?, ?, ?, ?)";
                PreparedStatement prepareStatement = connectionBD.prepareStatement(statement);
                prepareStatement.setString(1, activity.getName());
                prepareStatement.setString(2, activity.getDescription());
                prepareStatement.setInt(3, activity.getStatus());
                prepareStatement.setString(4, activity.getStartDate());
                prepareStatement.setString(5, activity.getEndDate());
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
    
    public static HashMap<String, Object> getAssignedActivities(int idDeveloper)
    {
        HashMap<String, Object> answer = new HashMap<>();
        answer.put("error", true);
        Connection connectionBD = ConnectionDB.getConnection();
        if(connectionBD != null){
            try{
                String query = "select a.idActivity, a.name, a.description, a.status , s.status as statusName, "
                        + "a.startDate, a.endDate, a.idDeveloper, a.idProjectManager from activity a "
                        + "inner join status s on a.status = s.idStatus "
                        + "where a.idDeveloper = ?";
                PreparedStatement preparedStatement = connectionBD.prepareStatement(query);
                preparedStatement.setInt(1, idDeveloper);   
                ResultSet activitiesList = preparedStatement.executeQuery();
                ArrayList<Activity> activities = new ArrayList<>();                
                while(activitiesList.next()){
                    Activity activity = new Activity();
                    activity.setIdActivity(activitiesList.getInt("idActivity"));
                    activity.setName(activitiesList.getString("name"));
                    activity.setDescription(activitiesList.getString("description"));
                    activity.setStatus(activitiesList.getInt("status"));
                    activity.setStatusName(activitiesList.getString("statusName"));
                    activity.setStartDate(activitiesList.getString("startDate"));
                    activity.setEndDate(activitiesList.getString("endDate"));
                    activity.setIdDeveloper(activitiesList.getInt("idDeveloper"));
                    activity.setIdProjectManager(activitiesList.getInt("idProjectManager"));
                    activities.add(activity);
                }
                connectionBD.close();
                answer.put("error", false);
                answer.put("activities", activities);
            } catch (SQLException ex) {
                answer.put("message","Error: " + ex.getMessage());
            }     
        }else{
            answer.put("message", "Hubo un error al intentar conectar con la base de datos. Intente "
                    + "de nuevo más tarde");
        }
        return answer;
    }
    
    public static HashMap<String, Object> getUnassignedActivities()
    {
        HashMap<String, Object> answer = new HashMap<>();
        answer.put("error", true);
        Connection connectionBD = ConnectionDB.getConnection();
        if(connectionBD != null){
            try{
                String query = "select a.idActivity, a.name, a.description, a.status , s.status as statusName, "
                        + "a.startDate, a.endDate, a.idDeveloper, a.idProjectManager from activity a "
                        + "inner join status s on a.status = s.idStatus "
                        + "where a.status = 1";
                PreparedStatement preparedStatement = connectionBD.prepareStatement(query);  
                ResultSet activitiesList = preparedStatement.executeQuery();
                ArrayList<Activity> activities = new ArrayList<>();                
                while(activitiesList.next()){
                    Activity activity = new Activity();
                    activity.setIdActivity(activitiesList.getInt("idActivity"));
                    activity.setName(activitiesList.getString("name"));
                    activity.setDescription(activitiesList.getString("description"));
                    activity.setStatus(activitiesList.getInt("status"));
                    activity.setStatusName(activitiesList.getString("statusName"));
                    activity.setStartDate(activitiesList.getString("startDate"));
                    activity.setEndDate(activitiesList.getString("endDate"));
                    activity.setIdDeveloper(activitiesList.getInt("idDeveloper"));
                    activity.setIdProjectManager(activitiesList.getInt("idProjectManager"));
                    activities.add(activity);
                }
                connectionBD.close();
                answer.put("error", false);
                answer.put("activities", activities);
            } catch (SQLException ex) {
                answer.put("message","Error: " + ex.getMessage());
            }     
        }else{
            answer.put("message", "Hubo un error al intentar conectar con la base de datos. Intente "
                    + "de nuevo más tarde");
        }
        return answer;
    }
    
    public static HashMap<String, Object> deleteActivity(Integer idActivity)
    {
        HashMap<String, Object> answer = new HashMap<>();
        answer.put("error", true);
        Connection connectionBD = ConnectionDB.getConnection();
        if(connectionBD != null){
            try{
                String query = "DELETE FROM activity WHERE idActivity = ?;";
                PreparedStatement preparedStatement = connectionBD.prepareStatement(query);
                preparedStatement.setInt(1, idActivity);           
                int filasAfectadas = preparedStatement.executeUpdate();
                connectionBD.close();
                if(filasAfectadas == 1){
                    answer.put("error", false);
                    answer.put("mensaje", "Actividad eliminada con éxito");
                }else{
                    answer.put("mensaje", "Hubo un error al intentar eliminar la actividad seleccioanda. "
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
    
    public static HashMap<String, Object> searchActivity(String activityName)
    {
        HashMap<String, Object> answer = new HashMap<>();
        answer.put("error", true);
        Connection connectionBD = ConnectionDB.getConnection();
        if(connectionBD != null){
            try{
                String query = "select a.idActivity, a.name, a.description, a.status , s.status as statusName, "
                        + "a.startDate, a.endDate, a.idDeveloper, a.idProjectManager from activity a "
                        + "inner join status s on a.status = s.idStatus "
                        + "where a.name like ?";
                PreparedStatement preparedStatement = connectionBD.prepareStatement(query);  
                preparedStatement.setString(1, activityName + "%");  
                ResultSet activitiesList = preparedStatement.executeQuery();
                ArrayList<Activity> activities = new ArrayList<>();                
                while(activitiesList.next()){
                    Activity activity = new Activity();
                    activity.setIdActivity(activitiesList.getInt("idActivity"));
                    activity.setName(activitiesList.getString("name"));
                    activity.setDescription(activitiesList.getString("description"));
                    activity.setStatus(activitiesList.getInt("status"));
                    activity.setStatusName(activitiesList.getString("statusName"));
                    activity.setStartDate(activitiesList.getString("startDate"));
                    activity.setEndDate(activitiesList.getString("endDate"));
                    activity.setIdDeveloper(activitiesList.getInt("idDeveloper"));
                    activity.setIdProjectManager(activitiesList.getInt("idProjectManager"));
                    activities.add(activity);
                }
                connectionBD.close();
                answer.put("error", false);
                answer.put("activities", activities);
            } catch (SQLException ex) {
                answer.put("message","Error: " + ex.getMessage());
            }     
        }else{
            answer.put("message", "Hubo un error al intentar conectar con la base de datos. Intente "
                    + "de nuevo más tarde");
        }
        return answer;
    }
    
    public static HashMap<String, Object> updateUnassignedActivity(Activity activity){
        HashMap<String, Object> answer = new HashMap();
        answer.put("error",true);
        Connection connectionBD = ConnectionDB.getConnection();
        if(connectionBD != null){
            try{
                String statement = "update activity set name = ?, description = ?, startDate = ?, " + 
                        "endDate = ? where idActivity = ?";
                PreparedStatement prepareStatement = connectionBD.prepareStatement(statement);
                prepareStatement.setString(1, activity.getName());
                prepareStatement.setString(2, activity.getDescription());
                prepareStatement.setString(3, activity.getStartDate());
                prepareStatement.setString(4, activity.getEndDate());
                prepareStatement.setInt(5, activity.getIdActivity());
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
    
    public static HashMap<String, Object> updateAssignedActivity(Activity activity){
        HashMap<String, Object> answer = new HashMap();
        answer.put("error",true);
        Connection connectionBD = ConnectionDB.getConnection();
        if(connectionBD != null){
            try{
                String statement = "update activity set name = ?, description = ?, startDate = ?, " + 
                        "endDate = ?, idDeveloper = ?, status = ? where idActivity = ?";
                PreparedStatement prepareStatement = connectionBD.prepareStatement(statement);
                prepareStatement.setString(1, activity.getName());
                prepareStatement.setString(2, activity.getDescription());
                prepareStatement.setString(3, activity.getStartDate());
                prepareStatement.setString(4, activity.getEndDate());
                prepareStatement.setInt(5, activity.getIdDeveloper());
                prepareStatement.setInt(6, 2);
                prepareStatement.setInt(7, activity.getIdActivity());
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
