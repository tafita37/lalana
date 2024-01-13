package model.route.pk;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import database.ConnexionBdd;
import model.reparation.Traitement;
import model.route.Route;

public class Pk {
    int idPk;
    int debutKm;
    int finKm;
    Route route;
    EtatPk etatPk;
    public Pk(Connection con, int idPk, int debutKm, int finKm, String idRoute) 
    throws Exception {
        boolean jAiOuvert=false;
        if(con==null) {
            jAiOuvert=true;
            con=ConnexionBdd.connexionPostgress("postgres", "AnaTaf37", "lalana");
        }
        try {
            this.idPk = idPk;
            this.debutKm = debutKm;
            this.finKm = finKm;
            this.route = Route.getRoueById(con, idRoute);
        } catch (Exception e) {
            throw e;
        } finally {
            if(jAiOuvert) {
                con.close();
            }
        }
    }
    public Pk(int idPk, int debutKm, int finKm, Route route) {
        this.idPk = idPk;
        this.debutKm = debutKm;
        this.finKm = finKm;
        this.route = route;
    }
    public Pk(int debutKm, int finKm) {
        this.debutKm = debutKm;
        this.finKm = finKm;
    }
    public Pk(int idPk, int debutKm, int finKm, Route route, EtatPk etatPk) {
        this.idPk = idPk;
        this.debutKm = debutKm;
        this.finKm = finKm;
        this.route = route;
        this.etatPk = etatPk;
    }
    public Pk() {
    }
    public int getIdPk() {
        return idPk;
    }
    public void setIdPk(int idPk) {
        this.idPk = idPk;
    }
    public int getDebutKm() {
        return debutKm;
    }
    public void setDebutKm(int debutKm) {
        this.debutKm = debutKm;
    }
    public int getFinKm() {
        return finKm;
    }
    public void setFinKm(int finKm) {
        this.finKm = finKm;
    }
    public Route getRoute() {
        return route;
    }
    public void setRoute(Route route) {
        this.route = route;
    }
    public EtatPk getEtatPk() {
        return etatPk;
    }
    public void setEtatPk(EtatPk etatPk) {
        this.etatPk = etatPk;
    }

    public ArrayList<Traitement> getTraitementNecessaire(Connection con)
    throws Exception {
        ArrayList<Traitement> result=new ArrayList<Traitement>();
        boolean jAiOuvert=false;
        if(con==null) {
            jAiOuvert=true;
            con=ConnexionBdd.connexionPostgress("postgres", "AnaTaf37", "lalana");
        }
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        try {
            String sql="select * from traitement where ? between id_debut and id_fin";
            preparedStatement=con.prepareStatement(sql);
            preparedStatement.setInt(1, this.getEtatPk().getNiveauEtatPk());
            resultSet=preparedStatement.executeQuery();
            // System.out.println(preparedStatement.toString());
            while(resultSet.next()) {
                // System.out.println("e");
                Traitement traitement=new Traitement(con, resultSet.getString("id_traitement"), resultSet.getString("nom_traitement"), resultSet.getInt("id_objectif"), resultSet.getInt("id_debut"), resultSet.getInt("id_fin"), resultSet.getDouble("prix_traitement"));
                for(int i=this.getEtatPk().getNiveauEtatPk(); i<traitement.getObjectif().getNiveauEtatPk(); i++) {
                    result.add(traitement);
                }
                // System.out.println(traitement);
                this.setEtatPk(traitement.getObjectif());
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if(preparedStatement!=null) {
                preparedStatement.close();
            }
            if(jAiOuvert) {
                con.close();
            }
        }
        return result;
    }

    public ArrayList<Traitement> getListeTraitement(Connection con)
    throws Exception {
        ArrayList<Traitement> result=new ArrayList<Traitement>();
        boolean jAiOuvert=false;
        if(con==null) {
            jAiOuvert=true;
            con=ConnexionBdd.connexionPostgress("postgres", "AnaTaf37", "lalana");
        }
        try {
            EtatPk oldEtat=this.getEtatPk();
            while(this.getEtatPk().getNiveauEtatPk()!=10) {
                ArrayList<Traitement> listeTraitement=this.getTraitementNecessaire(con);
                for(int i=0; i<listeTraitement.size(); i++) {
                    result.add(listeTraitement.get(i));
                }
            }
            this.setEtatPk(oldEtat);
        } catch (Exception e) {
            throw e;
        } finally {
            if(jAiOuvert) {
                con.close();
            }
        }
        return result;
    }

    public double getCoutTotalTraitement(Connection con)
    throws Exception {
        double result=0;
        boolean jAiOuvert=false;
        if(con==null) {
            jAiOuvert=true;
            con=ConnexionBdd.connexionPostgress("postgres", "AnaTaf37", "lalana");
        }
        try {
            result=Traitement.sommePrix(this.getListeTraitement(con));
        } catch (Exception e) {
            throw e;
        } finally {
            if(jAiOuvert) {
                con.close();
            }
        }
        return result;
    }

    public String getBorne() {
        return this.getDebutKm()+"-"+this.getFinKm();
    }

    public static Pk findPkById(Connection con, int idPk)
    throws Exception {
        Pk result=null;
        boolean jAiOuvert=false;
        if(con==null) {
            jAiOuvert=true;
            con=ConnexionBdd.connexionPostgress("postgres", "AnaTaf37", "lalana");
        }
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        try {
            String sql="select*from pk where id_pk=?";
            preparedStatement=con.prepareStatement(sql);
            preparedStatement.setInt(1, Integer.valueOf(idPk));
            System.out.println(preparedStatement.toString());
            resultSet=preparedStatement.executeQuery();
            while(resultSet.next()) {
                result=new Pk(con, resultSet.getInt("id_pk"), resultSet.getInt("debut_pk"), resultSet.getInt("fin_pk"), resultSet.getString("id_route"));
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if(jAiOuvert) {
                con.close();
            }
        }
        return result;
    }
}
