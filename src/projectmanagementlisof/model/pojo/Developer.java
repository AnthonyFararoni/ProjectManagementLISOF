/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projectmanagementlisof.model.pojo;

/**
 *
 * @author nando
 */
public class Developer {
    
    private Integer idDeveloper;
    private String developerLogin;
    private String name;
    private String lastName;
    private String secondLastName;
    private String email;
    private boolean enrollment;
    private String password;
    private Integer idProject;
    private Integer idSchoolPeriod;
    private String projectName;
    private String starDateSchoolPeriod;
    private String endDateSchoolPeriod;

    public Developer(Integer idDeveloper, String developerLogin, String name, String lastName, 
            String secondLastName, String email, boolean enrollment, String password, Integer idProject, 
            Integer idSchoolPeriod, String projectName, String starDateSchoolPeriod, String endDateSchoolPeriod) 
    {
        this.idDeveloper = idDeveloper;
        this.developerLogin = developerLogin;
        this.name = name;
        this.lastName = lastName;
        this.secondLastName = secondLastName;
        this.email = email;
        this.enrollment = enrollment;
        this.password = password;
        this.idProject = idProject;
        this.idSchoolPeriod = idSchoolPeriod;
        this.projectName = projectName;
        this.starDateSchoolPeriod = starDateSchoolPeriod;
        this.endDateSchoolPeriod = endDateSchoolPeriod;
    }

    public Developer() 
    {
    }

    public Integer getIdDeveloper() 
    {
        return idDeveloper;
    }

    public String getDeveloperLogin() 
    {
        return developerLogin;
    }

    public String getName() 
    {
        return name;
    }

    public String getLastName() 
    {
        return lastName;
    }

    public String getSecondLastName() 
    {
        return secondLastName;
    }

    public String getEmail() 
    {
        return email;
    }

    public boolean isEnrollment() 
    {
        return enrollment;
    }

    public String getPassword() 
    {
        return password;
    }

    public Integer getIdProject() 
    {
        return idProject;
    }

    public Integer getIdSchoolPeriod() 
    {
        return idSchoolPeriod;
    }

    public String getProjectName() 
    {
        return projectName;
    }

    public String getStarDateSchoolPeriod() 
    {
        return starDateSchoolPeriod;
    }

    public String getEndDateSchoolPeriod() 
    {
        return endDateSchoolPeriod;
    }

    public void setIdDeveloper(Integer idDeveloper) 
    {
        this.idDeveloper = idDeveloper;
    }

    public void setDeveloperLogin(String developerLogin) 
    {
        this.developerLogin = developerLogin;
    }

    public void setName(String name) 
    {
        this.name = name;
    }

    public void setLastName(String lastName) 
    {
        this.lastName = lastName;
    }

    public void setSecondLastName(String secondLastName) 
    {
        this.secondLastName = secondLastName;
    }

    public void setEmail(String email) 
    {
        this.email = email;
    }

    public void setEnrollment(boolean enrollment) 
    {
        this.enrollment = enrollment;
    }

    public void setPassword(String password) 
    {
        this.password = password;
    }

    public void setIdProject(Integer idProject) 
    {
        this.idProject = idProject;
    }

    public void setIdSchoolPeriod(Integer idSchoolPeriod) 
    {
        this.idSchoolPeriod = idSchoolPeriod;
    }

    public void setProjectName(String projectName) 
    {
        this.projectName = projectName;
    }

    public void setStarDateSchoolPeriod(String starDateSchoolPeriod) 
    {
        this.starDateSchoolPeriod = starDateSchoolPeriod;
    }

    public void setEndDateSchoolPeriod(String endDateSchoolPeriod) 
    {
        this.endDateSchoolPeriod = endDateSchoolPeriod;
    }

    public String getFullName()
    {
        String fullName = name + " " + lastName + " " + secondLastName;
        return fullName;
    }
  
}
