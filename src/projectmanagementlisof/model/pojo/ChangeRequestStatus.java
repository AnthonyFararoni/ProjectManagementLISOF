/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this
 * license Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projectmanagementlisof.model.pojo;

/**
 *
 * @author ferdy
 */
public class ChangeRequestStatus
{
      private Integer idChangeRequestStatus;
      private String status;

      public ChangeRequestStatus() {}

      public ChangeRequestStatus(Integer idChangeRequestStatus, String status)
      {
            this.idChangeRequestStatus = idChangeRequestStatus;
            this.status = status;
      }

      public Integer getIdChangeRequestStatus()
      {
            return idChangeRequestStatus;
      }

      public void setIdChangeRequestStatus(Integer idChangeRequestStatus)
      {
            this.idChangeRequestStatus = idChangeRequestStatus;
      }

      public String getStatus()
      {
            return status;
      }

      public void setStatus(String status)
      {
            this.status = status;
      }
}
