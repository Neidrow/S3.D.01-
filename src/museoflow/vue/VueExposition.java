/*
 * VueExposition.java                       nov. 2024 
 * IUT de Rodez Info2 TPD 2024-2025, pas
 * de copyright
 */
package museoflow.vue;

import java.util.ArrayList;

import museoflow.modele.ConsulterDonnees;
import museoflow.modele.Exposition;
import museoflow.modele.exceptions.FichierManquantException;

/**
 * Vue des expositions
 */
public class VueExposition {

    /**
     * Affiche les expositions
     * 
     * @param consulterDonnees Objet de consultation des données
     */
    public static void afficherExpositions(ConsulterDonnees consulterDonnees) {
        try {
            ArrayList<Exposition> expositions =
                    consulterDonnees.consulterListeExpositions();
            for (Exposition exposition : expositions) {
                System.out.println("ID: " + exposition.getIdExposition());
                System.out.println(
                        "Intitulé: " + exposition.getIntituleExposition());
                System.out.println(
                        "Période début: " + exposition.getPeriodeOeuvreDeb());
                System.out.println(
                        "Période fin: " + exposition.getPeriodeOeuvreFin());
                System.out.println(
                        "Nombre d'œuvres: " + exposition.getNombreOeuvre());
                System.out.println("Résumé: " + exposition.getResume());
                System.out.println(
                        "Date de début: " + exposition.getDateDebutExpo());
                System.out
                        .println("Date de fin: " + exposition.getDateFinExpo());

                if (exposition.getMotsCles() != null
                        && exposition.getMotsCles().length > 0
                        && exposition.getMotsCles().length < 10) {
                    System.out.print("Mots clés: ");
                    for (String mot : exposition.getMotsCles()) {
                        System.out.print(mot + ", ");
                    }
                } else {
                    System.out.print("Aucun");
                }

                System.out.println();
                System.out.println("------------------------");
            }
        } catch (FichierManquantException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }
}
