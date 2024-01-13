package model.route.pk;

import annotation.FieldMapping;
import annotation.PrimaryKey;
import annotation.Table;

/**
 * EtatPk
 */

@Table(tableName="etat_pk", idTable = "niveau_etat_pk", prefixe = "ETPK", sequence = "niveau_etat_pk", nbNumerique = 6)
public class EtatPk {
    @PrimaryKey
    @FieldMapping(columnName = "niveau_etat_pk")
    int niveauEtatPk;
    @FieldMapping(columnName = "nom_etat_pk")
    String nomEtatPk;
    public EtatPk(int niveauEtatPk, String nomEtatPk) {
        this.niveauEtatPk = niveauEtatPk;
        this.nomEtatPk = nomEtatPk;
    }
    public EtatPk() {
    }
    public int getNiveauEtatPk() {
        return niveauEtatPk;
    }
    public void setNiveauEtatPk(int niveauEtatPk) {
        this.niveauEtatPk = niveauEtatPk;
    }
    public String getNomEtatPk() {
        return nomEtatPk;
    }
    public void setNomEtatPk(String nomEtatPk) {
        this.nomEtatPk = nomEtatPk;
    }
}