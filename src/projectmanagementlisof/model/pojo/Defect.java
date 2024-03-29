/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this
 * license Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projectmanagementlisof.model.pojo;

/**
 *
 * @author edmun
 */
public class Defect
{
      private String description;
      private Integer timeCost;
      private String date;
      private Integer type;
      private String typeName;
      private Integer idDeveloper;
      private Integer idDefect;

      public Defect() {}

      public Defect(
              
          String description, Integer timeCost, String date, Integer type, Integer idDeveloper)
      {
            this.description = description;
            this.timeCost = timeCost;
            this.date = date;
            this.type = type;
            this.idDeveloper = idDeveloper;
      }

    public Defect(String description, Integer timeCost, String date, Integer type, String typeName, Integer idDeveloper, Integer idDefect) {
        this.description = description;
        this.timeCost = timeCost;
        this.date = date;
        this.type = type;
        this.typeName = typeName;
        this.idDeveloper = idDeveloper;
        this.idDefect = idDefect;
    }

      public Defect(String description, Integer timeCost, String date, Integer type,
          Integer idDeveloper, Integer idDefect)
      {
            this.description = description;
            this.timeCost = timeCost;
            this.date = date;
            this.type = type;
            this.idDeveloper = idDeveloper;
            this.idDefect = idDefect;
      }
      public String getDescription()
      {
            return description;
      }

      public void setDescription(String description)
      {
            this.description = description;
      }

      public Integer getTimeCost()
      {
            return timeCost;
      }

      public void setTimeCost(Integer timeCost)
      {
            this.timeCost = timeCost;
      }

      public String getDate()
      {
            return date;
      }

      public void setDate(String date)
      {
            this.date = date;
      }

      public Integer getType()
      {
            return type;
      }

      public void setType(Integer type)
      {
            this.type = type;
      }

      public Integer getIdDeveloper()
      {
            return idDeveloper;
      }

      public void setIdDeveloper(Integer idDeveloper)
      {
            this.idDeveloper = idDeveloper;
      }
      public Integer getIdDefect()
      {
            return idDefect;
      }

      public void setIdDefect(Integer idDefect)
      {
            this.idDefect = idDefect;
      }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
