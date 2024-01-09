/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this
 * license Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projectmanagementlisof.utils;

/**
 *
 * @author edmun
 */
public class SelectedProjectSingleton
{
      private final static SelectedProjectSingleton INSTANCE = new SelectedProjectSingleton();
      private Integer idSelectedProject;
      private Integer numberOfProjects;

      private SelectedProjectSingleton() {}

      public static SelectedProjectSingleton getInstance()
      {
            return INSTANCE;
      }

      public int getIdSelectedProject()
      {
            return idSelectedProject;
      }

      public void setIdSelectedProject(Integer idSelected)
      {
            this.idSelectedProject = idSelected;
      }

    public Integer getNumberOfProjects() {
        return numberOfProjects;
    }

    public void setNumberOfProjects(Integer numberOfProjects) {
        this.numberOfProjects = numberOfProjects;
    }
      
}