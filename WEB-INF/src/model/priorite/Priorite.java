package model.priorite;

import annotation.FieldMapping;
import annotation.Table;
import model.route.*;
import model.route.pk.Pk;;

@Table(tableName="priorite", idTable = "id_priorite", sequence = "id_priorite", nbNumerique = 6)
public class Priorite {
    @FieldMapping(columnName = "id_priorite")
    int idPriorite;
    @FieldMapping(columnName = "nom_priorite")
    String nomPriorite;
    public Priorite(int idPriorite, String nomPriorite) {
        this.idPriorite = idPriorite;
        this.nomPriorite = nomPriorite;
    }
    public Priorite() {
    }
    public int getIdPriorite() {
        return idPriorite;
    }
    public void setIdPriorite(int idPriorite) {
        this.idPriorite = idPriorite;
    }
    public String getNomPriorite() {
        return nomPriorite;
    }
    public void setNomPriorite(String nomPriorite) {
        this.nomPriorite = nomPriorite;
    }

    public Pk[] getPrioritePk(Route route) {
        Pk[] result=new Pk[route.getListePk().length];
        if(this.getIdPriorite()==1) {
            int k=0;
            int deb=0;
            int end=result.length-1;
            int pas=0;
            while(k<result.length) {
                result[k]=route.getListePk()[deb+pas];
                if(k!=result.length-1) {
                    result[k+1]=route.getListePk()[end-pas];
                }
                pas++;
                k+=2;
            }
        } else {
            int k=0;
            if(result.length%2==0) {
                int deb=(result.length/2)-1;
                int end=deb+1;
                int pas=0;
                while(k<result.length) {
                    result[k]=route.getListePk()[deb-pas];
                    if(k!=result.length-1) {
                        result[k+1]=route.getListePk()[end+pas];
                    }
                    pas++;
                    k+=2;
                }
            } else {
                result[0]=route.getListePk()[((result.length+1)/2)-1];
                int deb=((result.length+1)/2)-2;
                int end=((result.length+1)/2);
                int pas=0;
                while(k<result.length) {
                    result[k]=route.getListePk()[deb-pas];
                    if(k!=result.length-1) {
                        result[k+1]=route.getListePk()[end+pas];
                    }
                    pas++;
                    k+=2;
                }
            }
        }
        return result;
    }
}
