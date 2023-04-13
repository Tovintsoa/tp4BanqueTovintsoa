/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mg.itu.tovintsoa.tp4banquetovintsoa.jsf;

import jakarta.ejb.EJB;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.FacesConverter;
import jakarta.faces.convert.Converter;
import mg.itu.tovintsoa.tp4banquetovintsoa.ejb.GestionnaireCompte;
import mg.itu.tovintsoa.tp4banquetovintsoa.entities.CompteBancaire;

/**
 *
 * @author Tovintsoa-Capri
 */
@FacesConverter(value="compteBancaireConverter",managed=true)
public class CompteBancaireConverter implements Converter<CompteBancaire> {
    @EJB
    private GestionnaireCompte gestionnaireCompte;
    
    @Override
    public CompteBancaire getAsObject(FacesContext fc, UIComponent uic, String string) {
         if (string == null) return null;
        return gestionnaireCompte.findById(string);
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, CompteBancaire t) {
        if (t == null) return "";
         return t.getId().toString();
    }
}
