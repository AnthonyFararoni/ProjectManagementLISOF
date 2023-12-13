package projectmanagementlisof.model.pojo;

public class ChangeRequest
{
      private Integer idChangeRequest;
      private String justification;
      private String description;
      private String status;
      private String creationDate;
      private String reviewDate;
      private Integer idDeveloper;
      private Integer idProjectManager;
      private Integer idDefect;

      public ChangeRequest() {}

      public ChangeRequest(Integer idChangeRequest, String justification, String description,
          String status, String creationDate, String reviewDate, Integer idDeveloper,
          Integer idProjectManager, Integer idDefect)
      {
            this.idChangeRequest = idChangeRequest;
            this.justification = justification;
            this.description = description;
            this.status = status;
            this.creationDate = creationDate;
            this.reviewDate = reviewDate;
            this.idDeveloper = idDeveloper;
            this.idProjectManager = idProjectManager;
            this.idDefect = idDefect;
      }

      public Integer getIdChangeRequest()
      {
            return idChangeRequest;
      }

      public void setIdChangeRequest(Integer idChangeRequest)
      {
            this.idChangeRequest = idChangeRequest;
      }

      public String getJustification()
      {
            return justification;
      }

      public void setJustification(String justification)
      {
            this.justification = justification;
      }

      public String getDescription()
      {
            return description;
      }

      public void setDescription(String description)
      {
            this.description = description;
      }

      public String getStatus()
      {
            return status;
      }

      public void setStatus(String status)
      {
            this.status = status;
      }

      public String getCreationDate()
      {
            return creationDate;
      }

      public void setCreationDate(String creationDate)
      {
            this.creationDate = creationDate;
      }

      public String getReviewDate()
      {
            return reviewDate;
      }

      public void setReviewDate(String reviewDate)
      {
            this.reviewDate = reviewDate;
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

      public Integer getIdDefect()
      {
            return idDefect;
      }

      public void setIdDefect(Integer idDefect)
      {
            this.idDefect = idDefect;
      }

      @Override public String toString()
      {
            return "ChangeRequest{"
                + "idChangeRequest=" + idChangeRequest + ", justification=" + justification
                + ", description=" + description + ", status=" + status + ", creationDate="
                + creationDate + ", reviewDate=" + reviewDate + ", idDeveloper=" + idDeveloper
                + ", idProjectManager=" + idProjectManager + ", idDefect=" + idDefect + '}';
      }
}
