/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package mg.itu.tovintsoa.tp4banquetovintsoa.jsf;

import jakarta.ejb.EJB;
import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;
import java.io.Serializable;
import mg.itu.tovintsoa.tp4banquetovintsoa.ejb.GestionnaireCompte;
import mg.itu.tovintsoa.tp4banquetovintsoa.entities.CompteBancaire;

/**
 *
 * @author Tovintsoa-Capri
 */
@Named(value = "ajoutCompte")
@ViewScoped
public class AjoutCompte implements Serializable {
    private String nomUser;
    private String soldeUser;
    @EJB
    private GestionnaireCompte gestionnaireCompte;
    /**
     * Creates a new instance of AjoutCompte
     */
    public AjoutCompte() {
    }
    
    public String getNomUser() {
        return nomUser;
    }

    public void setNomUser(String nomUser) {
        this.nomUser = nomUser;
    }

    public String getSoldeUser() {
        return soldeUser;
    }

    public void setSoldeUser(String soldeUser) {
        this.soldeUser = soldeUser;
    }
    
    public String save(){
        CompteBancaire newCompte = new CompteBancaire(nomUser,Integer.parseInt(soldeUser));
        gestionnaireCompte.persist(newCompte);
        return "listeComptes?faces-redirect=true";
    }
    
}
