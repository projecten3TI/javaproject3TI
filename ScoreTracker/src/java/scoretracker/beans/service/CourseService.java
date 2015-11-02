/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scoretracker.beans.service;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import scoretracker.beans.persistence.Course;

/**
 *
 * @author John
 */
@Stateless
public class CourseService {
    
    @PersistenceContext
    private EntityManager em;
    
     public List<Course> getCourses() {
        List<Course> courses = em.createNamedQuery("Course.findAll").getResultList();
        return courses;
    }
    
    public Course getCourse(int id){
        Course course = (Course)em.createNamedQuery("Course.findById").setParameter("id", id).getSingleResult();
        return course;
    }
}
