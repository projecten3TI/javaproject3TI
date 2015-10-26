/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scoretracker.beans.service;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import scoretracker.beans.persistence.Course;
import scoretracker.beans.persistence.Test;

/**
 *
 * @author Jamie
 */
@Stateless
public class DataService {
    //    private String[] messages = {"hello world from EJB !", "Bonjour", "Yolo !"};
    @PersistenceContext
    private EntityManager em;

    public List<Class> getClasses() {
        Query q = em.createQuery("select c from Class c");
        List<Class> classes = q.getResultList();
        return classes;
    }
    
    public Class getClass(int id){
        Query q = em.createQuery("select c from Class c where c.id = 1");
        //q.setParameter(1, id);
        Class klas = (Class)q.getSingleResult();
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

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
}
