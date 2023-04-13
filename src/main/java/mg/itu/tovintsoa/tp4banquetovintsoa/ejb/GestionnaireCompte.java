/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mg.itu.tovintsoa.tp4banquetovintsoa.ejb;

import jakarta.annotation.sql.DataSourceDefinition;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.util.List;
import mg.itu.tovintsoa.tp4banquetovintsoa.entities.CompteBancaire;

/**
 *
 * @author Tovintsoa-Capri
 */
@DataSourceDefinition (
    className="com.mysql.cj.jdbc.MysqlDataSource",
    name="java:app/jdbc/banque",
    serverName="localhost",
    portNumber=3306,
    user="Tovintsoa",              // nom et
    password="tovintsoa", // mot de passe que vous avez donnés lors de la création de la base de données
    databaseName="banque",
    properties = {
      "useSSL=false",
      "allowPublicKeyRetrieval=true"
    }
)
@Stateless
public class GestionnaireCompte {
    @PersistenceContext(unitName = "banquePU")
    private EntityManager em;
    
    public void creerCompte(CompteBancaire c) {
        em.persist(c);
    } 
    public List<CompteBancaire> getAllComptes() {
         Query query = em.createNamedQuery("CompteBancaire.findAll");
         return query.getResultList();
    } 
    public CompteBancaire findById(String id){
        return em.find(CompteBancaire.class, Long.valueOf(id));
    }
    public CompteBancaire update(CompteBancaire cb){
         return em.merge(cb);
    } 
     public void persist(CompteBancaire c) {
        em.persist(c);
    }
}
