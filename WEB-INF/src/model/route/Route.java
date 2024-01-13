package model.route;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import annotation.FieldMapping;
import annotation.PrimaryKey;
import annotation.Table;
import bdd.BddObject;
import database.ConnexionBdd;
import model.reparation.Reparation;
import model.route.pk.Pk;

@Table(tableName="route", idTable = "id_route", prefixe = "ROAD", sequence = "id_route", nbNumerique = 6)
public class Route {
    @PrimaryKey
    @FieldMapping(columnName = "id_route")
    String idRoute;
    @FieldMapping(columnName = "nom_route")
    String nomRoute;
    Pk[] listePk;
    public Route(String nomRoute, int nbKm) {
        this.nomRoute = nomRoute;
        this.setListePk(new Pk[nbKm]);
        for(int i=0; i<nbKm; i++) {
            this.getListePk()[i]=new Pk(i, i+1);
        }
    }
    public Route(String idRoute, String nomRoute) {
        this.idRoute = idRoute;
        this.nomRoute = nomRoute;
    }
    public Route() {
    }
    public String getIdRoute() {
        return idRoute;
    }
    public void setIdRoute(String idRoute) {
        this.idRoute = idRoute;
    }
    public String getNomRoute() {
        return nomRoute;
    }
    public void setNomRoute(String nomRoute) {
        this.nomRoute = nomRoute;
    }
    public Pk[] getListePk() {
        return listePk;
    }
    public void setListePk(Pk[] listePk) {
        this.listePk = listePk;
    }

    public int countListePk(Connection con)
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
            String sql="select count(*) as nb from pk where id_route=?";
            preparedStatement=con.prepareStatement(sql);
            preparedStatement.setString(1, this.getIdRoute());
            resultSet=preparedStatement.executeQuery();
            while(resultSet.next()) {
                result=resultSet.getInt("nb");
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

    public Pk[] getListePk(Connection con)
    throws Exception {
        Pk[] result=null;
        boolean jAiOuvert=false;
        if(con==null) {
            jAiOuvert=true;
            con=ConnexionBdd.connexionPostgress("postgres", "AnaTaf37", "lalana");
        }
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        try {
            result=new Pk[this.countListePk(con)];
            String sql="select*from pk where id_route=?";
            preparedStatement=con.prepareStatement(sql);
            preparedStatement.setString(1, this.getIdRoute());
            resultSet=preparedStatement.executeQuery();
            int i=0;
            while(resultSet.next()) {
                result[i]=new Pk(resultSet.getInt("id_pk"), resultSet.getInt("debut_pk"), resultSet.getInt("fin_pk"), this);
                i++;
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

    public static Route getLastRoute(Connection con)
    throws Exception {
        Route result=null;
        boolean jAiOuvert=false;
        if(con==null) {
            jAiOuvert=true;
            con=ConnexionBdd.connexionPostgress("postgres", "AnaTaf37", "lalana");
        }
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        try {
            String sql="select*from v_last_route";
            preparedStatement=con.prepareStatement(sql);
            resultSet=preparedStatement.executeQuery();
            while(resultSet.next()) {
                result=new Route(resultSet.getString("id_route"), resultSet.getString("nom_route"));
                result.setListePk(result.getListePk(con));
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

    public void newRoute(Connection con)
    throws Exception {
        boolean jAiOuvert=false;
        if(con==null) {
            jAiOuvert=true;
            con=ConnexionBdd.connexionPostgress("postgres", "AnaTaf37", "lalana");
        }
        PreparedStatement preparedStatement=null;
        try {
            BddObject.insert(con, this, "postgres", "AnaTaf37", "lalana");
            Route lastRoute=Route.getLastRoute(con);
            String sql="insert into pk(debut_pk, fin_pk, id_route) values(?, ?, ?)";
            preparedStatement=con.prepareStatement(sql);
            for(int i=0; i<this.getListePk().length; i++) {
                preparedStatement.setInt(1, this.getListePk()[i].getDebutKm());
                preparedStatement.setInt(2, this.getListePk()[i].getFinKm());
                preparedStatement.setString(3, lastRoute.getIdRoute());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        } catch (Exception e) {
            throw e;
        } finally {
            if(jAiOuvert) {
                con.close();
            }
        }
    }

    public static Route[] getListeRoute(Connection con)
    throws Exception {
        Route[] result=null;
        boolean jAiOuvert=false;
        if(con==null) {
            jAiOuvert=true;
            con=ConnexionBdd.connexionPostgress("postgres", "AnaTaf37", "lalana");
        }
        try {
            Object[] listeRoute=BddObject.selectAllFromBdd(con, Route.class, "postgres", "AnaTaf37", "lalana");
            result=new Route[listeRoute.length];
            for(int i=0; i<listeRoute.length; i++) {
                result[i]=(Route) listeRoute[i];
                result[i].setListePk(result[i].getListePk(con));
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

    public static Route getRoueById(Connection con, String idRoute)
    throws Exception {
        System.out.println("id de route"+idRoute);
        Route result=null;
        boolean jAiOuvert=false;
        if(con==null) {
            jAiOuvert=true;
            con=ConnexionBdd.connexionPostgress("postgres", "AnaTaf37", "lalana");
        }
        try {
            Object route=BddObject.findById(con, Route.class, idRoute, "postgres", "AnaTaf37", "lalana");
            // System.out.println(idRoute);
            result=(Route) route;
            result.setListePk(result.getListePk(con));
        } catch (Exception e) {
            throw e;
        } finally {
            if(jAiOuvert) {
                con.close();
            }
        }
        return result;
    }

    public int countReparationByIdRoute(Connection con)
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
            String sql="select count(*) as nb from reparation where id_route=?";
            preparedStatement=con.prepareStatement(sql);
            preparedStatement.setString(1, this.getIdRoute());
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
    
    public Reparation[] getReparationByIdRoute(Connection con)
    throws Exception {
        Reparation[] result=null;
        boolean jAiOuvert=false;
        if(con==null) {
            jAiOuvert=true;
            con=ConnexionBdd.connexionPostgress("postgres", "AnaTaf37", "lalana");
        }
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        try {
            result=new Reparation[this.countReparationByIdRoute(con)];
            String sql="select*from reparation where id_route=?";
            preparedStatement=con.prepareStatement(sql);
            preparedStatement.setString(1, this.getIdRoute());
            resultSet=preparedStatement.executeQuery();
            int i=0;
            while(resultSet.next()) {
                result[i]=new Reparation(con, resultSet.getString("id_reparation"), resultSet.getString("id_priorite"), resultSet.getString("id_route"), resultSet.getTimestamp("date_heure_reparation"), Reparation.getListePkAReparerByIdReparation(con, resultSet.getString("id_reparation")), resultSet.getDouble("budget"));
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
}
