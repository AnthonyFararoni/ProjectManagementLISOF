/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this
 * license Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projectmanagementlisof.model.pojo;

/**
 *
 * @author edmun
 */
public class CorrectionType
{
      private Integer idType;
      private String typeName;

      public CorrectionType() {}

      public CorrectionType(Integer idType, String typeName)
      {
            this.idType = idType;
            this.typeName = typeName;
      }

      public Integer getIdType()
      {
            return idType;
      }

      public void setIdType(Integer idType)
      {
            this.idType = idType;
      }

      public String getTypeName()
      {
            return typeName;
      }

      public void setTypeName(String typeName)
      {
            this.typeName = typeName;
      }
      @Override public String toString()
      {
            return typeName;
      }
}
