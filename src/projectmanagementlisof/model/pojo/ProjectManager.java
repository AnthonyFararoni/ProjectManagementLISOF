/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projectmanagementlisof.model.pojo;

/**
 *
 * @author edmun
 */
public class ProjectManager {
    private String name;
    private String lastName;
    private String secondLastname;
    private String fullName;
    private String managerLogin;
    private int managerId;

    public ProjectManager() {
    }

    public ProjectManager(String name, String lastName, String secondLastname, String fullName, String managerLogin, int managerId) {
        this.name = name;
        this.lastName = lastName;
        this.secondLastname = secondLastname;
        this.fullName = fullName;
        this.managerLogin = managerLogin;
        this.managerId = managerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSecondLastname() {
        return secondLastname;
    }

    public void setSecondLastname(String secondLastname) {
        this.secondLastname = secondLastname;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName() {
        this.fullName = this.name + " " + this.lastName + " " + this.secondLastname;
    }

    public String getManagerLogin() {
        return managerLogin;
    }

    public void setManagerLogin(String managerLogin) {
        this.managerLogin = managerLogin;
    }

    public int getManagerId() {
        return managerId;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }
}
