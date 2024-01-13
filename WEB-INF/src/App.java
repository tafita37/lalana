import java.util.ArrayList;

import model.reparation.Reparation;
import model.reparation.Traitement;
import model.route.Route;

public class App {
    public static void main(String[] args) throws Exception {
        // Reparation reparation = Reparation.getReparationById(null, "3");
        // for(int i=0; i<reparation.getListeAbime().length; i++) {
        //     ArrayList<Traitement> listeTraitement=reparation.getListeAbime()[i].getListeTraitement(null);
        //     System.out.println("Pk : "+reparation.getListeAbime()[i].getBorne());
        //     for(int j=0; j<listeTraitement.size(); j++) {
        //         System.out.println(listeTraitement.get(j).getNomTraitement()+" "+listeTraitement.get(j).getPrix());
        //     }
        // }
        System.out.println(Route.getRoueById(null, "ROAD000001"));
    }
}
