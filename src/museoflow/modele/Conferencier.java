/*
 * Conferencier.java                           nov. 2024
 * IUT de Rodez Info2 TPD 2024-2025, pas de copyright 
 */
package museoflow.modele;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Classe objet représentant un conférencier.
 * 
 * @author Amjed SEHIL
 * @author Cylian POUPIN
 */
public class Conferencier {

    private String IdConferencier;
	
    private String nomConferencier;

    private String prenomConferencier;

    private String[] specialite;

    private String telephone;

    private boolean employeParMusee;

    private List<String> indisponibilites = new ArrayList<>();

    /**
     * <p>
     * Construit un Conferencier avec les valeurs en paramètres.
     * </p>
     * 
     * @param IdConferencier   ID du conférencier
     * @param nom              Nom du conférencier
     * @param prenom           Prénom du conférencier
     * @param specialite       Spécialité du conférencier
     * @param telephone        Téléphone du conférencier
     * @param employeParMusee  true si le conférencier est interne au
     *                         musée, false sinon
     * @param indisponibilites Liste des indisponibilités du
     *                         conférencier
     */
    public Conferencier(String IdConferencier,
                        String nom,
                        String prenom,
                        String[] specialite,
                        String telephone,
                        boolean employeParMusee,
                        List<String> indisponibilites) {

        // ---------- Vérification des données ----------

        // Véfification de la présence des données
        if (estNullOuVide(IdConferencier)) {
            throw new IllegalArgumentException(
                    "L'identifiant d'une conférencier n'est pas renseigné.");
        }
        if (estNullOuVide(nom)) {
            throw new IllegalArgumentException(
                    "Le nom d'un conférencier n'est pas renseigné.");
        }
        if (estNullOuVide(prenom)) {
            throw new IllegalArgumentException(
                    "Le prénom d'un conférencier n'est pas renseigné.");
        }
        if (specialite == null) {
            throw new IllegalArgumentException(
                    "Un conférencier n'a aucune spécialité renseignée.");
        }
        if (estNullOuVide(telephone)) {
            throw new IllegalArgumentException(
                    "Le numéro de téléphone d'un conférencier n'est pas "
                            + "renseigné.");
        }
        if (indisponibilites == null) {
            throw new IllegalArgumentException(
                    "Les indisponibilités d'un conférencier ne sont pas "
                            + "renseignées.");
        }


        // Vérification du numéro de téléphone
        if (!telephone.matches("\\d{10}")) {
            throw new IllegalArgumentException("Le numéro de téléphone \""
                    + telephone
                    + "\" d'un conférencier est incorrect.");
        }

        // --- Vérification de la conformité des dates ---

        // Vérification que la liste contient bien des couples de
        // dates (la taille de la liste doit être paire
        if (indisponibilites.size() % 2 != 0) {
            throw new IllegalArgumentException("Les indisponibiliés d'un "
                    + "conférencier ne sont pas des paires de dates correctes.");
        }

        // Parcours de la liste des indisponibilités pour vérifier les
        // paires de dates
        for (int i = 0; i < indisponibilites.size(); i = i + 2) {

            // Vérification de la présence des paires de dates
            if (estNullOuVide(indisponibilites.get(i))
                    && !estNullOuVide(indisponibilites.get(i + 1))) {
                throw new IllegalArgumentException(
                        "Un conférencier a une date de fin d'indisponibilité "
                                + "sans date de début.");
            }
            if (!estNullOuVide(indisponibilites.get(i))
                    && estNullOuVide(indisponibilites.get(i + 1))) {
                throw new IllegalArgumentException(
                        "Un conférencier a une date de début d'indisponibilité "
                                + "sans date de fin.");
            }

            // Vérification de l'intégrité des dates, si renseignées
            if (!"".equals(indisponibilites.get(i))
                    && !"".equals(indisponibilites.get(i + 1))) {
                verifierDates(indisponibilites.get(i),
                        indisponibilites.get(i + 1),
                        "dd/MM/yyyy");
            }
        }


        // Vérification des mots clés (max. 6)
        if (specialite.length > 6) {
            throw new IllegalArgumentException(
                    "Les mots clés d'une exposition sont trop nombreux "
                            + "(max. 6 ; trouvés : " + specialite.length
                            + ").");
        }


        // ------------------------------------------------

        // Toutes les vérifications n'ont renvoyé aucune erreur
        this.IdConferencier = IdConferencier;
        this.nomConferencier = nom;
        this.prenomConferencier = prenom;
        this.specialite = specialite;
        this.telephone = telephone;
        this.employeParMusee = employeParMusee;
        this.indisponibilites = indisponibilites;
    }

    private boolean estNullOuVide(String chaine) {
        return chaine == null || chaine.trim().isEmpty();
    }

    private void verifierDates(String dateDebut, String dateFin,
                               String formatDate) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatDate);
        try {
            // Si ne lève pas d'exception, la date est valide.
            LocalDate.parse(dateDebut, formatter);

        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(
                    "La date de début \"" + dateDebut + "\" d'une "
                    + "indisponibilité de conférencier n'est pas une"
                    + " date valide.");
        }

        try {
            // Si ne lève pas d'exception, la date est valide.
            LocalDate.parse(dateFin, formatter);

        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(
                    "La date de fin \"" + dateFin + "\" d'une "
                    + "indisponibilité de conférencier n'est pas une"
                    + " date valide.");
        }

        // Vérification de la cohérence des dates
        SimpleDateFormat formatDateSimple = new SimpleDateFormat(formatDate);

        try {
            Date dateDebutformatDate = formatDateSimple.parse(dateDebut);
            Date dateFinFormatDate = formatDateSimple.parse(dateFin);

            if (dateDebutformatDate.compareTo(dateFinFormatDate) > 0) {
                throw new IllegalArgumentException(
                        "Une indisponibilité d'un conférencier est terminé(e) "
                        + "avant d'être commencé(e) (date(s) début/fin "
                        + "incorrecte(s)).");
            }
        } catch (ParseException e) {
            throw new IllegalArgumentException(
                    "Une date d'indisponibilité d'un conférencier n'est pas au "
                    + "format JJ/MM/AAAA");
        }

    }

    /**
     * Retourne l'ID du conférencier.
     * 
     * @return ID du conférencier
     */
    public String getIdConferencier() {
        return IdConferencier;
    }

    /**
     * Retourne le nom du conférencier.
     * 
     * @return Nom du conférencier
     */
    public String getNom() {
        return nomConferencier;
    }

    /**
     * Retourne le prénom du conférencier.
     * 
     * @return Prénom du conférencier
     */
    public String getPrenom() {
        return prenomConferencier;
    }

    /**
     * Retourne la spécialité du conférencier.
     * 
     * @return Spécialité du conférencier
     */
    public String[] getSpecialite() {
        return specialite;
    }

    /**
     * Retourne le téléphone du conférencier.
     * 
     * @return Téléphone du conférencier
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * Indique si le conférencier est employé par le musée.
     * 
     * @return true si le conférencier est interne au musée, false
     *         sinon
     */
    public boolean isEmployeParMusee() {
        return employeParMusee;
    }

    /**
     * Retourne les indisponibilités du conférencier.
     * 
     * @return Indisponibilités du conférencier
     */
    public List<String> getIndisponibilites() {
        return indisponibilites;
    }
}