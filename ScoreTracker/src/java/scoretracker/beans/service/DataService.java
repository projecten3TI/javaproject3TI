/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scoretracker.beans.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import scoretracker.beans.persistence.Course;
import scoretracker.beans.persistence.Klas;
import scoretracker.beans.persistence.Test;
import scoretracker.beans.persistence.Teststudent;
import scoretracker.beans.persistence.User;

/**
 *
 * @author Jamie
 */
@Stateful
public class DataService {
    //    private String[] messages = {"hello world from EJB !", "Bonjour", "Yolo !"};
    @PersistenceContext
    private EntityManager em;

    public List<Klas> getKlasses() {
        Query q = em.createQuery("select c from Klas c");
        List<Klas> classes = q.getResultList();
        return classes;
    }
    
    public Klas getKlas(int id){
        Query q = em.createQuery("select c from Klas c where c.id = 1");
        //q.setParameter(1, id);
        Klas klas = (Klas)q.getSingleResult();
        return klas;
    }
    
    public Course getCourse(int id){
        Query q = em.createQuery("SELECT c FROM Course c where c.id = 1");
        Course c = (Course)q.getSingleResult();
        return c;
    }
    
    public List<Course> getCourses(){
        Query q = em.createQuery("SELECT c FROM Course c");
        List<Course> courses = q.getResultList();
        return courses;
    }
    
    public List<Test> getTests(){
        Query q = em.createQuery("SELECT t FROM Test t");
        List<Test> tests = q.getResultList();
        return tests;
    }
    
    public List<Teststudent> getTeststudents(int klasId, int courseId, int testId){
        
        String query = "Select ts from Teststudent ts";
        Query q = em.createQuery(query);
        Boolean first = false;
        if (klasId != 0 || courseId != 0 || testId != 0){
            query += " WHERE ";
            if (klasId != 0){
                query += "ts.testId IN (SELECT t FROM Test t WHERE t.klasId.id = :klasId)";
                first = true;
            }
            if (courseId != 0){
                if (first){
                    query += " AND ";
                } else {
                    first = true;
                }
                query += "ts.testId IN (SELECT t FROM Test t WHERE t.courseId.id = :courseId)";       
            }
            if (testId != 0){
                if (first){
                    query += " AND ";
                }
                query += "ts.testId IN (SELECT t from Test t WHERE t.id = :testId)";     
            }
            q = em.createQuery(query);
            if (klasId != 0){
                q.setParameter("klasId", klasId);
            }
            if (courseId != 0){
                q.setParameter("courseId", courseId);
            }
            if (testId != 0){
                q.setParameter("testId", testId);
            }
        } else {
            q = em.createQuery(query);
            
        } 
        return q.getResultList();
    }
    
    public User getUserFromTeststudents(int userId){
            Query q = em.createQuery("Select u from User u WHERE u.id = ?");
            q.setParameter(1, userId);
            return (User)q.getSingleResult();
    }
    
    //Ajax Methods
    public String getKlasTest(int klasId){
        String result = "";
        Query q = em.createQuery("SELECT c from Teststudent c WHERE c.userId IN (SELECT u FROM User u WHERE u.klasId = ?)");
        q.setParameter(1, klasId);
        List<Teststudent> u = q.getResultList();
        for (Teststudent t : u){
            result += "<tr><th>" + t.getTestId().getName() + "</th><th>" + t.getTestId().getCourseId().getName() + "</th><th>" + t.getUserId().getRNr() + "</th><th>" + t.getScore() + "</th><th>" + t.getTestId().getMaxScore() + "</th><th>" + t.getUserId().getKlasId() + "</th></tr>";
        }
        
        return result;
    }
    
    /*public StudentClasses getCourseTest(){
        
    }
    
    public StudentClasses getTest(){
        
    }*/
    //End ajax methods
    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
}
