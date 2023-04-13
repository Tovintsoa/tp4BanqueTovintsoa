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
import java.util.Objects;

import mg.itu.tovintsoa.tp4banquetovintsoa.ejb.GestionnaireCompte;
import mg.itu.tovintsoa.tp4banquetovintsoa.entities.CompteBancaire;
import mg.itu.tovintsoa.tp4banquetovintsoa.jsf.util.Util;

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
         boolean erreur = false;
        try {
            int montantVal = 0;
            if(!this.getMontant().isEmpty()){
                montantVal = Integer.parseInt(this.getMontant());
            }
            compteOrigine.setSolde(compteOrigine.getSolde() - montantVal);
            compteDestinataire.setSolde(compteDestinataire.getSolde() + montantVal);
            if(Objects.equals(compteOrigine.getId(), compteDestinataire.getId())){
                Util.messageErreur("On ne peut pas tranférer depuis la meme compte", "On ne peut pas tranférer depuis la meme compte", "form:source");
                erreur = true;
            }
            if(compteOrigine.getSolde() < montantVal){
                 Util.messageErreur("Le montant saisi est supérieur au solde actuel de l'origine", "Le montant saisi est supérieur au solde actuel de l'origine", "form:source");
                 erreur = true;
            }
            if(montantVal == 0){
                Util.messageErreur("Veuillez saisir un montant valide", "Veuillez saisir un montant valide", "form:source");
                erreur = true;
            }
            if(erreur){
                return null;
            }
            gestionnaireCompte.update(compteOrigine);
            gestionnaireCompte.update(compteDestinataire);
            userTransaction.commit();
            Util.addFlashInfoMessage("Transfert correctement effectué");
        }
        catch (HeuristicMixedException | HeuristicRollbackException | RollbackException | SystemException | IllegalStateException | NumberFormatException | SecurityException ex) {
            if (userTransaction.getStatus() == Status.STATUS_ACTIVE) {
                userTransaction.rollback();
            }
            throw ex;
            
        }
        
        return "listeComptes?faces-redirect=true";
        
          
    }
}
