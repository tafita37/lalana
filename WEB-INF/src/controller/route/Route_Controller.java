package controller.route;

import java.sql.Connection;
import java.sql.Timestamp;

import annotation.DefaultParameter;
import annotation.Parameters;
import annotation.Url;
import bdd.BddObject;
import database.ConnexionBdd;
import model.priorite.Priorite;
import model.reparation.Reparation;
import model.route.Route;
import model.route.pk.EtatPk;
import model.route.pk.Pk;
import url.ModelView;

public class Route_Controller {
    @Url(link = "formRoute.htm")
    public Object getFormRoute()
    throws Exception {
        ModelView result=new ModelView("web/pages/formRoute.jsp");
        return result;
    }

    @Url(link = "traitementNewRoute.htm")
    @Parameters(args = {"nom_route", "nb_pk"})
    public Object newRoute(String nom_route, int nb_pk) 
    throws Exception {
        ModelView result=new ModelView();
        Route route=new Route(nom_route, nb_pk);
        route.newRoute(null);
        result.setUrlRedirect("formRoute.htm");
        return result;
    }

    @Url(link = "listeRoute.htm")
    public Object getListeRoute()
    throws Exception {
        ModelView result=new ModelView("web/pages/listeRoute.jsp");
        result.addItem("allRoute", Route.getListeRoute(null));
        return result;
    }

    @Url(link = "formReparation.htm")
    @Parameters(args = {"id_route"})
    public Object getFormReparation(String idRoute)
    throws Exception {
        ModelView result=new ModelView("web/pages/formReparation.jsp");
        Connection con=ConnexionBdd.connexionPostgress("postgres", "AnaTaf37", "lalana");
        try {
            result.addItem("allPriorite", BddObject.selectAllFromBdd(con, Priorite.class, "postgres", "AnaTaf37", "lalana"));
            result.addItem("route", Route.getRoueById(con, idRoute));
            result.addItem("allEtat", BddObject.selectAllFromBdd(con, EtatPk.class, "postgres", "AnaTaf37", "lalana"));
        } catch (Exception e) {
            return e;
        } finally {
            con.close();
        }
        return result;
    }

    @Url(link = "newReparation.htm")
    @Parameters(args = {"choix", "date", "heure", "budget", "etatPk", "id_pk", "id_route"})
    public Object newReparation(String choix, String date, String heure, double budget, int[] etatPk, int[] idPk, String idRoute)
    throws Exception {
        ModelView result=new ModelView();
        Connection con=ConnexionBdd.connexionPostgress("postgres", "AnaTaf37", "lalana");
        try {
            if(etatPk.length!=idPk.length) {
                throw new Exception("Veuillez entrer le meme nombre");
            }
            Pk[] listeAbime=new Pk[idPk.length];
            for(int i=0; i<listeAbime.length; i++) {
                listeAbime[i]=Pk.findPkById(con, idPk[i]);
                listeAbime[i].setEtatPk((EtatPk) BddObject.findById(con, EtatPk.class, String.valueOf(etatPk[i]), "postgres", "AnaTaf37", "lalana"));
            }
            Reparation reparation=new Reparation(con, choix, idRoute, Timestamp.valueOf(date+" "+heure+":00"), listeAbime, budget);
            reparation.newReparation(con);
            result.setUrlRedirect("formReparation.htm?id_route="+idRoute);
        } catch (Exception e) {
            e.printStackTrace();
            return e;
        } finally {
            con.close();
        }
        return result;
    }

    @Url(link = "allReparation.htm")
    @Parameters(args = {"id_route"})
    public Object getAllReparation(@DefaultParameter(defaultValue = "ROAD000001") String idRoute)
    throws Exception {
        ModelView result=new ModelView("web/pages/listeReparation.jsp");
        Connection con=ConnexionBdd.connexionPostgress("postgres", "AnaTaf37", "lalana");
        try {
            result.addItem("allRoute", BddObject.selectAllFromBdd(con, Route.class, "postgres", "AnaTaf37", "lalana"));
            Route route=Route.getRoueById(con, idRoute);
            result.addItem("listeReparations", route.getReparationByIdRoute(con));
        } catch (Exception e) {
            throw e;
        } finally {
            con.close();
        }
        return result;
    }

    @Url(link = "showProposition.htm")
    @Parameters(args = {"id_reparation"})
    public Object getProposition(String idReparation)
    throws Exception {
        ModelView result=new ModelView("web/pages/proposition.jsp");
        Connection con=ConnexionBdd.connexionPostgress("postgres", "AnaTaf37", "lalana");
        try {
            Reparation reparation=Reparation.getReparationById(con, idReparation);
            result.addItem("reparation", reparation);
            result.addItem("listePk", reparation.getListeTraitable(con));
        } catch (Exception e) {
            e.printStackTrace();
            return e;
        } finally {
            con.close();
        }
        return result;
    }

    @Url(link = "detailTraitement.htm")
    @Parameters(args = {"id_reparation", "id_pk"})
    public Object getListeTraitement(String idReparation, int idPk)
    throws Exception {
        ModelView result=new ModelView("web/pages/detailProposition.jsp");
        Connection con=ConnexionBdd.connexionPostgress("postgres", "AnaTaf37", "lalana");
        try {
            Reparation reparation=Reparation.getReparationById(con, idReparation);
            Pk pk=reparation.getCorrespondingPk(con, idPk);
            result.addItem("traitements", pk.getListeTraitement(con));
            result.addItem("pk", pk);
        } catch (Exception e) {
            throw e;
        } finally {
            con.close();
        }
        return result;
    }
}
