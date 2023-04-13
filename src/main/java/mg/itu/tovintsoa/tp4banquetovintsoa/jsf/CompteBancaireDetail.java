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
import jakarta.transaction.HeuristicMixedException;
import jakarta.transaction.HeuristicRollbackException;
import jakarta.transaction.NotSupportedException;
import jakarta.transaction.RollbackException;
import jakarta.transaction.Status;
import jakarta.transaction.SystemException;
import jakarta.transaction.UserTransaction;
import java.io.Serializable;
import mg.itu.tovintsoa.tp4banquetovintsoa.ejb.GestionnaireCompte;
import mg.itu.tovintsoa.tp4banquetovintsoa.entities.CompteBancaire;
import mg.itu.tovintsoa.tp4banquetovintsoa.jsf.util.Util;

/**
 *
 * @author Tovintsoa-Capri
 */
@Named(value = "compteBancaireDetail")
@ViewScoped
public class CompteBancaireDetail implements Serializable {
    private int id;
    private int soldeUser;
    private String action;
    private CompteBancaire compteBancaire;
    @EJB
    private GestionnaireCompte gestionnaireCompte;
    @Resource
    private UserTransaction userTransaction;
    /**
     * Creates a new instance of CompteBancaireDetail
     */
    public CompteBancaireDetail() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CompteBancaire getCompteBancaire() {
        return compteBancaire;
    }

    public void setCompteBancaire(CompteBancaire compteBancaire) {
        this.compteBancaire = compteBancaire;
    }

    public int getSoldeUser() {
        return soldeUser;
    }

    public void setSoldeUser(int soldeUser) {
        this.soldeUser = soldeUser;
    }
    
     public void loadCompteBancaire() {
        this.compteBancaire = gestionnaireCompte.findById(String.valueOf(id));
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
     public String save() throws NotSupportedException, SystemException, RollbackException, HeuristicMixedException, HeuristicRollbackException{
         try{
               userTransaction.begin();
               System.out.print(soldeUser);
               if("versement".equals(this.getAction())){
                    System.out.print(soldeUser);
                   compteBancaire.deposer(soldeUser);
               }
               else if("retrait".equals(this.getAction())) {
                    System.out.print("aaa");
                   compteBancaire.retirer(soldeUser);
               }
                gestionnaireCompte.update(compteBancaire);
                userTransaction.commit();
                Util.addFlashInfoMessage("Transfert correctement effectué");
         }
         catch (IllegalStateException | NumberFormatException | SecurityException ex) {
            if (userTransaction.getStatus() == Status.STATUS_ACTIVE) {
                userTransaction.rollback();
            }
            throw ex;
            
        }
        return "listeComptes?faces-redirect=true";
     }
}
