/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this
 * license Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projectmanagementlisof.model.pojo;

import java.util.Date;

/**
 *
 * @author ferdy
 */
public class Activity
{
      private Integer idActivity;
      private String name;
      private String description;
      private Integer status;
      private String statusName;
      private String startDate;
      private String endDate;
      private Integer idDeveloper;
      private Integer idProjectManager;
      private Integer idProject;

      public Activity() {}

      public Activity(Integer idActivity, String name, String description, Integer status,
          String statusName, String startDate, String endDate, Integer idDeveloper,
          Integer idProjectManager, Integer idProject)
      {
            this.idActivity = idActivity;
            this.name = name;
            this.description = description;
            this.status = status;
            this.statusName = statusName;
            this.startDate = startDate;
            this.endDate = endDate;
            this.idDeveloper = idDeveloper;
            this.idProjectManager = idProjectManager;
            this.idProject = idProject;
      }

      public Integer getIdActivity()
      {
            return idActivity;
      }

      public void setIdActivity(Integer idActivity)
      {
            this.idActivity = idActivity;
      }

      public String getName()
      {
            return name;
      }

      public void setName(String name)
      {
            this.name = name;
      }

      public String getDescription()
      {
            return description;
      }

      public void setDescription(String description)
      {
            this.description = description;
      }

      public Integer getStatus()
      {
            return status;
      }

      public String getStatusName()
      {
            return statusName;
      }

      public void setStatusName(String statusName)
      {
            this.statusName = statusName;
      }

      public void setStatus(Integer status)
      {
            this.status = status;
      }

      public String getStartDate()
      {
            return startDate;
      }

      public void setStartDate(String startDate)
      {
            this.startDate = startDate;
      }

      public String getEndDate()
      {
            return endDate;
      }

      public void setEndDate(String endDate)
      {
            this.endDate = endDate;
      }

      public Integer getIdDeveloper()
      {
            return idDeveloper;
      }

      public void setIdDeveloper(Integer idDeveloper)
      {
            this.idDeveloper = idDeveloper;
      }

      public Integer getIdProjectManager()
      {
            return idProjectManager;
      }

      public void setIdProjectManager(Integer idProjectManager)
      {
            this.idProjectManager = idProjectManager;
      }
      
      public Integer getIdProject()
      {
            return idProject;
      }

      public void setIdProject(Integer idProject)
      {
            this.idProject = idProject;
      }
}
