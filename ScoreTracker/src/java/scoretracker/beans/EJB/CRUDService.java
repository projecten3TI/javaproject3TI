/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scoretracker.beans.EJB;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import scoretracker.beans.persistence.Student;

/**
 *
 * @author John
 */
@Stateless
public class CRUDService {
    
    @PersistenceContext
    private EntityManager em;
    
    public void deleteStudent(Student student){
        Student s = em.find(Student.class, student.getId());
        
        em.getTransaction().begin();
        em.remove(s);
        em.getTransaction().commit();   
    }
}
