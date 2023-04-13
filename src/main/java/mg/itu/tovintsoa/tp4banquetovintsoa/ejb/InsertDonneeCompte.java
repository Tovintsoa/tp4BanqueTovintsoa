/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mg.itu.tovintsoa.tp4banquetovintsoa.ejb;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import mg.itu.tovintsoa.tp4banquetovintsoa.entities.CompteBancaire;

/**
 *
 * @author Tovintsoa-Capri
 */
@Singleton
@Startup
public class InsertDonneeCompte {
    @PersistenceContext(unitName = "banquePU")
    private EntityManager entityManager;
    
    @PostConstruct
    public void init(){
         if(nbComptes() == 0){
            CompteBancaire compte1 = new CompteBancaire("John Lennon",150000);
            entityManager.persist(compte1);
            CompteBancaire compte2 = new CompteBancaire("Paul McCartney",950000);
            entityManager.persist(compte2);
            CompteBancaire compte3 = new CompteBancaire("Ringo Starr",20000);
            entityManager.persist(compte3);
            CompteBancaire compte4 = new CompteBancaire("Georges Harrisson",100000);
            entityManager.persist(compte4);
         }
        
    }
    
    public int  nbComptes(){
        Query query = entityManager.createQuery("SELECT COUNT(c) FROM CompteBancaire c");
        Long nbComptes = (Long) query.getSingleResult();
        return nbComptes.intValue();
    }
  
}
