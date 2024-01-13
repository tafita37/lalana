package model.reparation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import bdd.BddObject;
import database.ConnexionBdd;
import model.priorite.Priorite;
import model.route.Route;
import model.route.pk.EtatPk;
import model.route.pk.Pk;

public class Reparation {
    String idReparation;
    Priorite choix;
    Route route;
    Timestamp dateHeureReparation;
    Pk[] listeAbime;
    double budget;
    public Reparation(Connection con, String idChoix, String idRoute, Timestamp dateHeureReparation, Pk[] listeAbime, double budget)
    throws Exception {
        boolean jAiOuvert=false;
        if(con==null) {
            jAiOuvert=true;
            con=ConnexionBdd.connexionPostgress("postgres", "AnaTaf37", "lalana");
        }
        try {
            this.choix = (Priorite) BddObject.findById(con, Priorite.class, idChoix, "postgres", "AnaTaf37", "lalana");
            this.route = Route.getRoueById(con, idRoute);
            this.dateHeureReparation = dateHeureReparation;
            this.listeAbime = listeAbime;
            this.budget = budget;
        } catch (Exception e) {
            throw e;
        } finally {
            if(jAiOuvert) {
                con.close();
            }
        }
    }

    public Reparation(Priorite choix, Route route, Timestamp dateHeureReparation, Pk[] listeAbime, double budget) {
        this.choix = choix;
        this.route = route;
        this.dateHeureReparation = dateHeureReparation;
        this.listeAbime = listeAbime;
        this.budget = budget;
    }

    public Reparation(Connection con, String idReparation, String idchoix, String idRoute, Timestamp dateHeureReparation, Pk[] listeAbime, double budget)
    throws Exception {
        boolean jAiOuvert=false;
        if(con==null) {
            jAiOuvert=true;
            con=ConnexionBdd.connexionPostgress("postgres", "AnaTaf37", "lalana");
        }
        try {
            this.idReparation = idReparation;
            this.choix = (Priorite) BddObject.findById(con, Priorite.class, idchoix, "postgres", "AnaTaf37", "lalana");
            this.route = (Route) BddObject.findById(con, Route.class, idRoute, "postgres", "AnaTaf37", "lalana");
            this.route.setListePk(this.route.getListePk(con));
            this.dateHeureReparation = dateHeureReparation;
            this.listeAbime = listeAbime;
            this.budget = budget;
        } catch (Exception e) {
            throw e;
        } finally {
            if(jAiOuvert) {
                con.close();
            }
        }
    }
    public Reparation() {
    }
    public String getIdReparation() {
        return idReparation;
    }
    public void setIdReparation(String idReparation) {
        this.idReparation = idReparation;
    }
    public Priorite getChoix() {
        return choix;
    }
    public void setChoix(Priorite choix) {
        this.choix = choix;
    }
    public Timestamp getDateHeureReparation() {
        return dateHeureReparation;
    }
    public void setDateHeureReparation(Timestamp dateHeureReparation) {
        this.dateHeureReparation = dateHeureReparation;
    }
    public Pk[] getListeAbime() {
        return listeAbime;
    }
    public void setListeAbime(Pk[] listeAbime) {
        this.listeAbime = listeAbime;
    }
    public double getBudget() {
        return budget;
    }
    public void setBudget(double budget) {
        this.budget = budget;
    }

    public void newReparationTable(Connection con)
    throws Exception {
        boolean jAiOuvert=false;
        if(con==null) {
            jAiOuvert=true;
            con=ConnexionBdd.connexionPostgress("postgres", "AnaTaf37", "lalana");
        }
        PreparedStatement preparedStatement=null;
        try {
            String sql="insert into Reparation(id_priorite, id_route, date_heure_reparation, budget) values(?, ?, ?, ?)";
            preparedStatement=con.prepareStatement(sql);
            preparedStatement.setInt(1, this.getChoix().getIdPriorite());
            preparedStatement.setString(2, this.getRoute().getIdRoute());
            preparedStatement.setTimestamp(3, this.getDateHeureReparation());
            preparedStatement.setDouble(4, this.getBudget());
            preparedStatement.addBatch();
            preparedStatement.executeBatch();
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
    }

    public static int countListePkAReparerByIdConsultation(Connection con, String idConsultation)
    throws Exception {
        int result=0;
        boolean jAiOuvert=false;
        if(con==null) {
            jAiOuvert=true;
            con=ConnexionBdd.connexionPostgress("postgres", "AnaTaf37", "lalana");
        }
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        try {
            String sql="select count(*) as nb from v_pk_abime where id_reparation=?";
            preparedStatement=con.prepareStatement(sql);
            preparedStatement.setInt(1, Integer.valueOf(idConsultation));
            resultSet=preparedStatement.executeQuery();
            while(resultSet.next()) {
                result=resultSet.getInt("nb");
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if(preparedStatement!=null) {
                preparedStatement.close();
            }
            if(resultSet!=null) {
                resultSet.close();
            }
            if(jAiOuvert) {
                con.close();
            }
        }
        return result;
    }

    public static Pk[] getListePkAReparerByIdReparation(Connection con, String idReparation)
    throws Exception {
        Pk[] result=null;
        boolean jAiOuvert=false;
        if(con==null) {
            jAiOuvert=true;
            con=ConnexionBdd.connexionPostgress("postgres", "AnaTaf37", "lalana");
        }
        result=new Pk[Reparation.countListePkAReparerByIdConsultation(con, idReparation)];
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        try {
            String sql="select*from v_pk_abime where id_reparation=?";
            preparedStatement=con.prepareStatement(sql);
            preparedStatement.setInt(1, Integer.valueOf(idReparation));
            resultSet=preparedStatement.executeQuery();
            int i=0;
            Route route=null;
            while(resultSet.next()) {
                if(route==null) {
                    route=(Route) BddObject.findById(con, Route.class, resultSet.getString("id_route"), "postgres", "AnaTaf37", "lalana");
                }
                result[i]=new Pk(resultSet.getInt("id_pk"), resultSet.getInt("debut_pk"), resultSet.getInt("fin_pk"), route, (EtatPk) BddObject.findById(con, EtatPk.class, resultSet.getString("niveau_etat_pk"), "postgres", "AnaTaf37", "lalana"));
                i++;
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if(preparedStatement!=null) {
                preparedStatement.close();
            }
            if(resultSet!=null) {
                resultSet.close();
            }
            if(jAiOuvert) {
                con.close();
            }
        }
        return result;
    }

    public static Reparation getLastReparation(Connection con)
    throws Exception {
        Reparation result=null;
        boolean jAiOuvert=false;
        if(con==null) {
            jAiOuvert=true;
            con=ConnexionBdd.connexionPostgress("postgres", "AnaTaf37", "lalana");
        }
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        try {
            String sql="select*from v_last_reparation";
            preparedStatement=con.prepareStatement(sql);
            resultSet=preparedStatement.executeQuery();
            while(resultSet.next()) {
                result=new Reparation(con, resultSet.getString("id_reparation"), resultSet.getString("id_priorite"), resultSet.getString("id_route"), resultSet.getTimestamp("date_heure_reparation"), Reparation.getListePkAReparerByIdReparation(con, resultSet.getString("id_reparation")), resultSet.getDouble("budget"));
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if(preparedStatement!=null) {
                preparedStatement.close();
            }
            if(resultSet!=null) {
                resultSet.close();
            }
            if(jAiOuvert) {
                con.close();
            }
        }
        return result;
    }

    public static Reparation getReparationById(Connection con, String idReparation)
    throws Exception {
        Reparation result=null;
        boolean jAiOuvert=false;
        if(con==null) {
            jAiOuvert=true;
            con=ConnexionBdd.connexionPostgress("postgres", "AnaTaf37", "lalana");
        }
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        try {
            String sql="select*from reparation where id_reparation=?";
            preparedStatement=con.prepareStatement(sql);
            preparedStatement.setInt(1, Integer.valueOf(idReparation));
            resultSet=preparedStatement.executeQuery();
            while(resultSet.next()) {
                result=new Reparation(con, resultSet.getString("id_reparation"), resultSet.getString("id_priorite"), resultSet.getString("id_route"), resultSet.getTimestamp("date_heure_reparation"), Reparation.getListePkAReparerByIdReparation(con, resultSet.getString("id_reparation")), resultSet.getDouble("budget"));
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if(preparedStatement!=null) {
                preparedStatement.close();
            }
            if(resultSet!=null) {
                resultSet.close();
            }
            if(jAiOuvert) {
                con.close();
            }
        }
        return result;
    }

    public void newEtatPk(Connection con)
    throws Exception {
        boolean jAiOuvert=false;
        if(con==null) {
            jAiOuvert=true;
            con=ConnexionBdd.connexionPostgress("postgres", "AnaTaf37", "lalana");
        }
        PreparedStatement preparedStatement=null;
        try {
            Reparation reparation=Reparation.getLastReparation(con);
            String sql="insert into reparation_pk(id_reparation, id_pk, niveau_etat_pk) values(?, ?, ?)";
            preparedStatement=con.prepareStatement(sql);
            for(int i=0; i<this.getListeAbime().length; i++) {
                preparedStatement.setInt(1, Integer.valueOf(reparation.getIdReparation()));
                preparedStatement.setInt(2, this.getListeAbime()[i].getIdPk());
                preparedStatement.setInt(3, this.getListeAbime()[i].getEtatPk().getNiveauEtatPk());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
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
    }

    public void newReparation(Connection con)
    throws Exception {
        boolean jAiOuvert=false;
        if(con==null) {
            jAiOuvert=true;
            con=ConnexionBdd.connexionPostgress("postgres", "AnaTaf37", "lalana");
        }
        try {
            this.newReparationTable(con);
            this.newEtatPk(con);
        } catch (Exception e) {
            throw e;
        } finally {
            if(jAiOuvert) {
                con.close();
            }
        }
    }

    public Route getRoute() {
        return route;
    }
    public void setRoute(Route route) {
        this.route = route;
    }

    public Pk[] ordonnerPkTraiter(Connection con)
    throws Exception {
        Pk[] result=new Pk[this.getListeAbime().length];
        boolean jAiOuvert=false;
        if(con==null) {
            jAiOuvert=true;
            con=ConnexionBdd.connexionPostgress("postgres", "AnaTaf37", "lalana");
        }
        int k=0;
        try {
            Pk[] priorites=this.getChoix().getPrioritePk(this.getRoute());
            for(int i=0; i<priorites.length; i++) {
                for(int j=0; j<this.getListeAbime().length; j++) {
                    if(priorites[i].getIdPk()==this.getListeAbime()[j].getIdPk()) {
                        result[k]=this.getListeAbime()[j];
                        k++;
                    }
                }
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

    public Pk[] getListeTraitable(Connection con)
    throws Exception {
        Pk[] result=null;
        boolean jAiOuvert=false;
        if(con==null) {
            jAiOuvert=true;
            con=ConnexionBdd.connexionPostgress("postgres", "AnaTaf37", "lalana");
        }
        try {
            result=this.ordonnerPkTraiter(con);
            double budget=this.getBudget();
            for(int i=0; i<result.length; i++) {
                if(result[i].getCoutTotalTraitement(con)>budget) {
                    result[i]=null;
                } else {
                    budget-=result[i].getCoutTotalTraitement(con);
                }
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

    public Pk getCorrespondingPk(Connection con, int numeroPk)
    throws Exception {
        Pk result=null;
        boolean jAiOuvert=false;
        if(con==null) {
            jAiOuvert=true;
            con=ConnexionBdd.connexionPostgress("postgres", "AnaTaf37", "lalana");
        }
        try {
            Pk[] listeProposition=this.getListeTraitable(con);
            for(int i=0; i<listeProposition.length; i++) {
                if(listeProposition[i]!=null&&listeProposition[i].getIdPk()==numeroPk) {
                    result=listeProposition[i];
                }
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
