/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package mg.itu.tovintsoa.tp4banquetovintsoa.jsf;

import jakarta.annotation.Resource;
import jakarta.ejb.EJB;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.HeuristicMixedException;
import jakarta.transaction.HeuristicRollbackException;
import jakarta.transaction.RollbackException;
import jakarta.transaction.Status;
import jakarta.transaction.SystemException;
import jakarta.transaction.UserTransaction;
import java.io.Serializable;
import java.util.List;
import mg.itu.tovintsoa.tp4banquetovintsoa.ejb.GestionnaireCompte;
import mg.itu.tovintsoa.tp4banquetovintsoa.entities.CompteBancaire;

/**
 *
 * @author Tovintsoa-Capri
 */
@Named(value = "transfertArgent")
@ViewScoped
public class TransfertArgent implements Serializable  {
    private CompteBancaire compteOrigine;
    private CompteBancaire compteDestinataire;
    private String montant;
    @EJB
    private GestionnaireCompte gestionnaireCompte;
    @PersistenceContext(unitName = "banquePU")
    private EntityManager em;
    @Resource
    private UserTransaction userTransaction;
    
    /**
     * Creates a new instance of TransfertArgent
     */
    public TransfertArgent() {
    }
    
    public CompteBancaire getCompteOrigine() {
        return compteOrigine;
    }

    public void setCompteOrigine(CompteBancaire compteOrigine) {
        this.compteOrigine = compteOrigine;
    }

    public CompteBancaire getCompteDestinataire() {
        return compteDestinataire;
    }

    public void setCompteDestinataire(CompteBancaire compteDestinataire) {
        this.compteDestinataire = compteDestinataire;
    }

    public String getMontant() {
        return montant;
    }

    public void setMontant(String montant) {
        this.montant = montant;
    }
  
     public List<CompteBancaire> getAllComptes() {
         Query query = em.createNamedQuery("CompteBancaire.findAll");
         return query.getResultList();
    } 
    public CompteBancaire findById(String id){
        return em.find(CompteBancaire.class, Long.valueOf(id));
    }
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public String action() throws IllegalStateException, Exception {
         userTransaction.begin();
        try {
            int montantVal = Integer.parseInt(this.getMontant());
            compteOrigine.setSolde(compteOrigine.getSolde() - montantVal);
            compteDestinataire.setSolde(compteDestinataire.getSolde() + montantVal);
            
            gestionnaireCompte.update(compteOrigine);
            gestionnaireCompte.update(compteDestinataire);
            userTransaction.commit();
        }
        catch (HeuristicMixedException | HeuristicRollbackException | RollbackException | SystemException | IllegalStateException | NumberFormatException | SecurityException ex) {
            if (userTransaction.getStatus() == Status.STATUS_ACTIVE) {
                userTransaction.rollback();
            }
            throw ex;
            
        }
        finally {
            return "transfertArgent?faces-redirect=true";
        }
          
    }
}
