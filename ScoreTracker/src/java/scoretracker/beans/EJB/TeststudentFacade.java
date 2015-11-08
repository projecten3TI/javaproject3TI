/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scoretracker.beans.EJB;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import scoretracker.beans.persistence.Teststudent;

/**
 *
 * @author John
 */
@Stateless
public class TeststudentFacade extends AbstractFacade<Teststudent> {

    @PersistenceContext(unitName = "ScoreTrackerPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TeststudentFacade() {
        super(Teststudent.class);
    }
}
