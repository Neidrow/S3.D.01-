package tests.museoflow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import museoflow.modele.ConsulterDonnees;
import museoflow.modele.Exposition;
import museoflow.modele.FichierManquantException;

public class TestsConsulterDonnees {

	public static void testConsulterExpositions() {
        ConsulterDonnees consulterDonnees = new ConsulterDonnees();

        consulterDonnees.importerExpositions(obtenirListeExpositionsExemple());
        
        try {
            ArrayList<Exposition> expositions = consulterDonnees.consulterListeExpositions();
            for (Exposition exposition : expositions) {
                System.out.println("ID: " + exposition.getIdExposition());
                System.out.println("Intitulé: " + exposition.getIntituleExposition());
                System.out.println("Période début: " + exposition.getPeriodeOeuvreDeb());
                System.out.println("Période fin: " + exposition.getPeriodeOeuvreFin());
                System.out.println("Nombre d'œuvres: " + exposition.getNombreOeuvre());
                System.out.println("Résumé: " + exposition.getResume());
                System.out.println("Date de début: " + exposition.getDateDebutExpo());
                System.out.println("Date de fin: " + exposition.getDateFinExpo());
                
                // Vérification si la liste des mots clés est null
                //TODO erreur toujours null
                if (exposition.getMotsCles() != null) {
                    System.out.println("Mots clés: " + exposition.getMotsCles());
                } else {
                    System.out.println("Mots clés: Aucun");
                }
                
                System.out.println("------------------------");
            }
        } catch (FichierManquantException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }

    // Méthode d'exemple pour simuler des données d'expositions
    private static ArrayList<Exposition> obtenirListeExpositionsExemple() {
       
        ArrayList<Exposition> expositions = new ArrayList<>();
        expositions.add(new Exposition("001", "Art Moderne", 1900, 2000, 120, new ArrayList<>(Arrays.asList("Cubisme", "Suréalisme")), 
                                       "Exploration de l'art du XXe siècle", new Date(2024), new Date())); //TODO dates marche pas 
        
        return expositions;
    }

    public static void main(String[] args) {
    	testConsulterExpositions();
    }
}

