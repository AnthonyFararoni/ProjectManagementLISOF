/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projectmanagementlisof.model.pojo;

/**
 *
 * @author ferdy
 */
public class Change {
    
    private Integer idChange;
    private Integer type;
    private String description;
    private String dateCreated;
    private Integer idDeveloper;

    public Change() {
    }

    public Change(Integer idChange, Integer type, String description, String dateCreated, Integer idDeveloper) {
        this.idChange = idChange;
        this.type = type;
        this.description = description;
        this.dateCreated = dateCreated;
        this.idDeveloper = idDeveloper;
    }

    public Integer getIdChange() {
        return idChange;
    }

    public void setIdChange(Integer idChange) {
        this.idChange = idChange;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Integer getIdDeveloper() {
        return idDeveloper;
    }

    public void setIdDeveloper(Integer idDeveloper) {
        this.idDeveloper = idDeveloper;
    }
    
    
}
