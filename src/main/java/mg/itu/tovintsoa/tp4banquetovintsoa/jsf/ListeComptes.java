/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package mg.itu.tovintsoa.tp4banquetovintsoa.jsf;

import jakarta.ejb.EJB;
import jakarta.inject.Named;
import jakarta.enterprise.context.Dependent;
import jakarta.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.List;
import mg.itu.tovintsoa.tp4banquetovintsoa.ejb.GestionnaireCompte;
import mg.itu.tovintsoa.tp4banquetovintsoa.entities.CompteBancaire;

/**
 *
 * @author Tovintsoa-Capri
 */
@Named(value = "listeComptes")
@ViewScoped
public class ListeComptes implements Serializable {
     private List<CompteBancaire> compteBancaireList;  
     @EJB
     private GestionnaireCompte gestionnaireCompte;
    /**
     * Creates a new instance of ListeComptes
     */
    public ListeComptes() {
    }
     public List<CompteBancaire> getAllcomptes(){
        if(compteBancaireList == null){
            compteBancaireList = gestionnaireCompte.getAllComptes();
        }
        return compteBancaireList;
    }
   
}
