package tests.museoflow;

import java.util.ArrayList;

import museoflow.modele.ConsulterDonnees;
import museoflow.modele.Exposition;
import museoflow.vue.VueExposition;

/**
 * Classe de test pour la consultation des données
 * 
 * @author Aurélien VALAT
 */
public class TestsConsulterDonnees {

    /**
     * Teste la consultation des expositions
     */
    public static void testConsulterExpositions() {
        ConsulterDonnees consulterDonnees = new ConsulterDonnees();

        consulterDonnees.importerExpositions(obtenirListeExpositionsExemple());

        // Création de la vue pour afficher les expositions
        VueExposition vueExposition = new VueExposition();
        vueExposition.afficherExpositions(consulterDonnees);
    }

    private static ArrayList<Exposition> obtenirListeExpositionsExemple() {
        ArrayList<Exposition> expositions = new ArrayList<>();
        
        String[] array = {"Contemporain", "France"};
        Exposition exposition = new Exposition();
        exposition.construireExposition("001", "Art Moderne", "1900", "2000", "120", array, 
                                       "Exploration de l'art du XXe siècle", "2024", "2024"); 
        Exposition exposition2 = new Exposition();
        exposition2.construireExposition("002", "Art Moderne", "1950", "2000", "20", null, 
                                       "Exploration de l'art du XXe siècle", "", "");
        // Ajouter l'exposition à la liste
        expositions.add(exposition);
        expositions.add(exposition2);
        
        return expositions;
    }

    /**
     * TODO commenter le rôle de cette méthode (SRP)
     * 
     * @param args
     */
    public static void main(String[] args) {
        testConsulterExpositions();
    }
}
