/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projectmanagementlisof.model.pojo;

/**
 *
 * @author edmun
 */
public class Project {
    private int idProject;
    private String name;
    private String description;

    public Project() {
    }

    public Project(int idProject, String name, String Description) {
        this.idProject = idProject;
        this.name = name;
        this.description = Description;
    }

    public int getIdProject() {
        return idProject;
    }

    public void setIdProject(int idProject) {
        this.idProject = idProject;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String Description) {
        this.description = Description;
    }
    
    @Override public String toString()
      {
            return name;
      }
    
    
}
