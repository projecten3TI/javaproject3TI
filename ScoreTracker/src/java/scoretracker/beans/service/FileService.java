/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scoretracker.beans.service;

import scoretracker.beans.persistence.Class;
import scoretracker.beans.persistence.Course;
import scoretracker.beans.persistence.User;
import scoretracker.beans.persistence.Test;
import scoretracker.beans.persistence.Teststudent;
import scoretracker.beans.persistence.Type;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;

/**
 *
 * @author Lou Cox
 */
public class FileService {
    //    private String[] messages = {"hello world from EJB !", "Bonjour", "Yolo !"};
    @PersistenceContext
    private EntityManager em;
    
    
/* 
    public String getRandomMessage() {
      return messages[(ThreadLocalRandom.current().nextInt(messages.length))];
        Query q = em.createQuery("select m from Message m");       
        List<Message> messages = q.getResultList();
        return messages.get((ThreadLocalRandom.current().nextInt(messages.size()))).getText();
    }
*/
        
        
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
}
