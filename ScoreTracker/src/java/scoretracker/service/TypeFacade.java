/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scoretracker.service;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import scoretracker.beans.entity.Type;

/**
 *
 * @author Jamie
 */
@Stateless
public class TypeFacade extends AbstractFacade<Type> {
    @PersistenceContext(unitName = "ScoreTrackerPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TypeFacade() {
        super(Type.class);
    }
    
}
