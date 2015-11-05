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
import scoretracker.beans.persistence.Class;

/**
 *
 * @author John
 */
@Stateless
public class ClassService {
    
    @PersistenceContext
    private EntityManager em;
    
     public List<Class> getClasses() {
        List<Class> klassen = em.createNamedQuery("Class.findAll").getResultList();
        return klassen;
    }
    
    public Class getClass(int id){
        Class klas = (Class)em.createNamedQuery("Class.findById").setParameter("id", id).getSingleResult();
        return klas;
    }
}
