/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scoretracker.beans.web;

import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import scoretracker.beans.persistence.Course;
import scoretracker.beans.persistence.Klas;
import scoretracker.beans.persistence.Test;
import scoretracker.beans.persistence.Teststudent;
import scoretracker.beans.persistence.User;
import scoretracker.beans.service.DataService;

/**
 *
 * @author Jamie
 */
@ManagedBean
@RequestScoped
public class DataBean {
    
    @EJB
    private DataService service;
    
    private int klasId;
    
    public DataBean(){
    }
    
    public int getKlasId() {
        return klasId;
    }

    public void setKlasId(int klasId) {
        this.klasId = klasId;
    }
    
    public void runSubmit() {
        System.out.println("Submit executed");
    }
    
    public List<Klas> getKlasses(){
        return service.getKlasses();
    }
    
    public Klas getKlas(int id){
        return service.getKlas(id);
    }
    
    public Course getCourse(int id){
        return service.getCourse(id);
    }
    
    public List<Course> getCourses(){
        return service.getCourses();
    }
    
    public String getKlasTests(){
        return service.getKlasTest(klasId);
    }
    
    public List<Test> getTests(){
        return service.getTests();
    }
   
    public List<Teststudent> getTeststudents(){
        return service.getTeststudents();
    }
    
    public User getUsersFromTeststudents(int userId){
        return service.getUserFromTeststudents(userId);   
    }
}
