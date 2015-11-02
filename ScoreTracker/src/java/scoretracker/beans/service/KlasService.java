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
import scoretracker.beans.persistence.Klas;

/**
 *
 * @author John
 */
@Stateless
public class KlasService {
    
    @PersistenceContext
    private EntityManager em;
    
     public List<Klas> getKlassen() {
        List<Klas> klassen = em.createNamedQuery("Klas.findAll").getResultList();
        return klassen;
    }
    
    public Klas getKlas(int id){
        Klas klas = (Klas)em.createNamedQuery("Klas.findById").setParameter("id", id).getSingleResult();
        return klas;
    }
}
