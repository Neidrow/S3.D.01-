/*
 * Statistique.java nov. 2024 IUT de Rodez Info2 TPD 2024-2025, pas de
 * copyright
 */
package museoflow.modele;

import javafx.collections.ObservableList;

/**
 * Calcul de statistiques sur les données importées
 */
public class Statistique {

    /**
     * Trie les expositions par visites et modifie leur classement 
     * 
     * @param expositions
     */
    public static void trierExpositionsParVisites(
            ObservableList<Exposition> expositions) {
        expositions.sort((expo1, expo2) -> {
            int visites1 = Integer.parseInt(getNombreDeVisites(expo1));
            int visites2 = Integer.parseInt(getNombreDeVisites(expo2));
            // Tri décroissant par nombre de visites
            return Integer.compare(visites2, visites1);
        });

        // Assigner le classement après le tri
        for (int i = 0; i < expositions.size(); i++) {
            // Classement basé sur la position dans la liste triée
            expositions.get(i).setClassement(i + 1);
        }
    }

    /**
     * Trie les conférenciers par visites et modifie leur classement
     * 
     * @param conferenciers
     */
    public static void trierConferenciersParVisites(
            ObservableList<Conferencier> conferenciers) {
        conferenciers.sort((conf1, conf2) -> {
            int visites1 = Integer.parseInt(getNombreDeVisites(conf1));
            int visites2 = Integer.parseInt(getNombreDeVisites(conf2));
            // Tri décroissant par nombre de visites
            return Integer.compare(visites2, visites1);
        });

        // Assigner le classement après le tri
        for (int i = 0; i < conferenciers.size(); i++) {
            // Classement basé sur la position dans la liste triée
            conferenciers.get(i).setClassement(i + 1);
        }
    }

    /**
     * Renvoie le nombre de visites pour l'exposition en paramètre
     * 
     * @param exposition
     * @return le nombre de visites en chaine de caractère
     */
    public static String getNombreDeVisites(Exposition exposition) {
        return String.valueOf(GestionFichiers
                .compterVisitesPourExposition(exposition.getIdExposition()));
    }

    /**
     * Renvoie le nombre de visites pour le conferencier en paramètre
     * 
     * @param conferencier
     * @return le nombre de visites en chaine de caractère
     */
    public static String getNombreDeVisites(Conferencier conferencier) {
        return String.valueOf(GestionFichiers.compterVisitesPourConferencier(
                conferencier.getIdConferencier()));
    }
    
    /**
     * Calcule un pourcentage
     */
    public void calculPourcentage() {
    }

    /**
     * Génère un rapport PDF
     */
    public void genererRapport() {
    }
}