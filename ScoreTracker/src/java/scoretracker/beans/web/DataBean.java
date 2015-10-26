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
import scoretracker.beans.persistence.Test;
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
    
    public DataBean(){
    }
    
    public List<Class> getClasses(){
        return service.getClasses();
    }
    
    public Class getClasss(){
        return service.getClass();
    }
    
    public Course getCourse(int id){
        return service.getCourse(id);
    }
    
    public List<Course> getCourses(){
        return service.getCourses();
    }
    
    public List<Test> getTests(){
        return service.getTests();
    }
}
