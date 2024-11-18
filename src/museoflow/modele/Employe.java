/*
 * Employe.java                                 nov. 2024
 * IUT de Rodez Info2 TPD 2024-2025, pas de copyright
 */
package museoflow.modele;


/**
 * Classe objet représentant un employé.
 * 
 * @author Amjed SEHIL
 * @author Cylian POUPIN
 * @author Landry LOUBIERE
 */
public class Employe {

    private String idEmploye;

    private String nomEmploye;

    private String prenomEmploye;

    private String telephone;


    /**
     * <p>
     * Construit un Employe avec les valeurs en paramètres.
     * </p>
     * 
     * @param idEmploye     ID de l'employé
     * @param nomEmploye    nom de l'employé
     * @param prenomEmploye prénom de l'employé
     * @param telephone     No de tél. de l'employé
     */
    public Employe(String idEmploye, String nomEmploye,
            String prenomEmploye, String telephone) {

        // ---------- Vérification des données ----------

        // Véfification de la présence des données
        if (estNullOuVide(idEmploye)) {
            throw new IllegalArgumentException(
                    "L'identifiant d'un employé n'est pas renseigné.");
        }
        if (estNullOuVide(nomEmploye)) {
            throw new IllegalArgumentException(
                    "Le nom d'un employé n'est pas renseigné.");
        }
        if (estNullOuVide(prenomEmploye)) {
            throw new IllegalArgumentException(
                    "Le prénom d'un employé n'est pas renseigné.");
        }
        if (estNullOuVide(telephone)) {
            throw new IllegalArgumentException(
                    "Le nom d'un employé n'est pas renseigné.");
        }

        // Vérification du numéro de téléphone
        if (!telephone.matches("\\d{4}")) {
            throw new IllegalArgumentException("Le numéro de téléphone \""
                    + telephone + "\" de l'employé est incorrect.");
        }

        // Toutes les vérifications n'ont renvoyé aucune erreur
        this.idEmploye = idEmploye;
        this.nomEmploye = nomEmploye;
        this.prenomEmploye = prenomEmploye;
        this.telephone = telephone;
    }

    private boolean estNullOuVide(String chaine) {
        return chaine == null || chaine.trim().isEmpty();
    }

    /**
     * Retourne l'ID de l'employé.
     * 
     * @return ID de l'employé
     */
    public String getIdEmploye() {
        return idEmploye;
    }

    /**
     * Retourne le nom de l'employé.
     * 
     * @return nom de l'employé
     */
    public String getNomEmploye() {
        return nomEmploye;
    }

    /**
     * Retourne le prénom de l'employé.
     * 
     * @return prénom de l'employé
     */
    public String getPrenomEmploye() {
        return prenomEmploye;
    }

    /**
     * Retourne le numéro de téléphone de l'employé.
     * 
     * @return No de tél. de l'employé
     */
    public String getTelephone() {
        return telephone;
    }
}