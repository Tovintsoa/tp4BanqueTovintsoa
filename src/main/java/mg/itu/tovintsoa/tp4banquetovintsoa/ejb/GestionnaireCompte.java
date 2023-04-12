/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mg.itu.tovintsoa.tp4banquetovintsoa.ejb;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import mg.itu.tovintsoa.tp4banquetovintsoa.entities.CompteBancaire;

/**
 *
 * @author Tovintsoa-Capri
 */
@Stateless
public class GestionnaireCompte {
    @PersistenceContext(unitName = "banquePU")
    private EntityManager em;
    
    public void creerCompte(CompteBancaire c) {
     
    } 
    List<CompteBancaire> getAllComptes() {
        return null;
    } 
}
