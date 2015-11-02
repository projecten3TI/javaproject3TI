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
import scoretracker.beans.persistence.Test;

/**
 *
 * @author John
 */
@Stateless
public class TestService {
    
    @PersistenceContext
    private EntityManager em;
    
     public List<Test> getTests() {
        List<Test> tests = em.createNamedQuery("Test.findAll").getResultList();
        return tests;
    }
    
    public Test getTest(int id){
        Test test = (Test)em.createNamedQuery("Test.findById").setParameter("id", id).getSingleResult();
        return test;
    }
}
