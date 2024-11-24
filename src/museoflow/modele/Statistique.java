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
    private String idExposition;
    private String intituleExposition;
    private int nbVisites;
    private double pourcentage;

    /**
     * Constructeur pour initialiser une statistique pour une exposition.
     *
     * @param idExposition      L'ID de l'exposition.
     * @param intituleExposition L'intitulé de l'exposition.
     * @param nbVisites         Le nombre de visites de l'exposition.
     * @param pourcentage       Le pourcentage de visites par rapport au total.
     */
    public Statistique(String idExposition, String intituleExposition, int nbVisites, double pourcentage) {
        this.idExposition = idExposition;
        this.intituleExposition = intituleExposition;
        this.nbVisites = nbVisites;
        this.pourcentage = pourcentage;
    }

    // Getters et Setters

    public String getIdExposition() {
        return idExposition;
    }

    public void setIdExposition(String idExposition) {
        this.idExposition = idExposition;
    }

    public String getIntituleExposition() {
        return intituleExposition;
    }

    public void setIntituleExposition(String intituleExposition) {
        this.intituleExposition = intituleExposition;
    }

    public int getNbVisites() {
        return nbVisites;
    }

    public void setNbVisites(int nbVisites) {
        this.nbVisites = nbVisites;
    }

    public double getPourcentage() {
        return pourcentage;
    }

    public void setPourcentage(double pourcentage) {
        this.pourcentage = pourcentage;
    }

    /**
     * Méthode pour afficher les informations sous forme de chaîne.
     * 
     * @return Une chaîne formatée des informations de la statistique.
     */
    @Override
    public String toString() {
        return "Exposition ID: " + idExposition + ", Intitulé: " + intituleExposition + ", Visites: " 
                + nbVisites + ", Pourcentage: " + String.format("%.2f", pourcentage) + "%";
    }
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
     *  Méthode pour calculer le total des visites
     * @param expositions
     * @return
     */
    public static int calculerTotalVisites(ObservableList<Exposition> expositions) {
        int totalVisites = 0;
        for (Exposition exposition : expositions) {
            totalVisites += Integer.parseInt(Statistique.getNombreDeVisites(exposition));
        }
        return totalVisites;
    }


    /**
     * Génère un rapport PDF
     */
    public void genererRapport() {
    }
}