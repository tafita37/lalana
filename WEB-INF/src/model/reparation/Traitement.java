package model.reparation;

import java.sql.Connection;
import java.util.ArrayList;

import bdd.BddObject;
import database.ConnexionBdd;
import model.route.pk.EtatPk;

public class Traitement {
    String idTraitement;
    String nomTraitement;
    EtatPk objectif;
    EtatPk debut;
    EtatPk fin;
    double prix;
    public Traitement(String idTraitement, String nomTraitement, EtatPk objectif, EtatPk debut,
            EtatPk fin) {
        this.idTraitement = idTraitement;
        this.nomTraitement = nomTraitement;
        this.objectif = objectif;
        this.debut = debut;
        this.fin = fin;
    }

    public Traitement(Connection con, String idTraitement, String nomTraitement, int id_objectif, int id_debut, int id_fin, double prix)
    throws Exception {
        boolean jAiOuvert=false;
        if(con==null) {
            jAiOuvert=true;
            con=ConnexionBdd.connexionPostgress("postgres", "AnaTaf37", "lalana");
        }
        try {
            this.idTraitement = idTraitement;
            this.nomTraitement = nomTraitement;
            this.objectif = (EtatPk) BddObject.findById(con, EtatPk.class, String.valueOf(id_objectif), "postgres", "AnaTaf37", "lalana");
            this.debut = (EtatPk) BddObject.findById(con, EtatPk.class, String.valueOf(id_debut), "postgres", "AnaTaf37", "lalana");;
            this.fin = (EtatPk) BddObject.findById(con, EtatPk.class, String.valueOf(id_fin), "postgres", "AnaTaf37", "lalana");;
            this.prix = prix;
        } catch (Exception e) {
            throw e;
        } finally {
            if(jAiOuvert) {
                con.close();
            }
        }
    }

    public Traitement(String idTraitement, String nomTraitement, EtatPk objectif, EtatPk debut,
            EtatPk fin, double prix) {
        this.idTraitement = idTraitement;
        this.nomTraitement = nomTraitement;
        this.objectif = objectif;
        this.debut = debut;
        this.fin = fin;
        this.prix = prix;
    }
    public Traitement() {
    }
    public String getIdTraitement() {
        return idTraitement;
    }
    public void setIdTraitement(String idTraitement) {
        this.idTraitement = idTraitement;
    }
    public String getNomTraitement() {
        return nomTraitement;
    }
    public void setNomTraitement(String nomTraitement) {
        this.nomTraitement = nomTraitement;
    }
    public EtatPk getObjectif() {
        return objectif;
    }
    public void setObjectif(EtatPk objectif) {
        this.objectif = objectif;
    }
    public EtatPk getDebut() {
        return debut;
    }
    public void setDebut(EtatPk debut) {
        this.debut = debut;
    }
    public EtatPk getFin() {
        return fin;
    }
    public void setFin(EtatPk fin) {
        this.fin = fin;
    }
    public double getPrix() {
        return prix;
    }
    public void setPrix(double prix) {
        this.prix = prix;
    }

    public void newTraitement(Connection con) {

    }

    public static double sommePrix(ArrayList<Traitement> listeTraitement) {
        double result=0;
        for(int i=0; i<listeTraitement.size(); i++) {
            result+=listeTraitement.get(i).getPrix();
        }
        return result;
    }
}
