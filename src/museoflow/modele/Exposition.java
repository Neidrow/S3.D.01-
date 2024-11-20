/*
 * Exposition.java                                  oct. 2024 
 * IUT de Rodez Info2 TPD 2024-2025, pas de copyright
 */
package museoflow.modele;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

/**
 * Classe objet représentant une exposition
 * 
 * @author Aurelien VALAT
 * @author Cylian POUPIN
 * @author Landry LOUBIERE
 */
public class Exposition {
	
	private String idExposition;

    private String intituleExposition;

    private String periodeOeuvreDeb;

    private String periodeOeuvreFin;

    private String nombreOeuvre;

    private String[] motsCles;

    private String resume;

    private String dateDebutExpo;

    private String dateFinExpo;
    
    /**
     * <p>
     * Crée une Exposition avec les valeurs en paramètres.
     * </p>
     * 
     * @param idExposition     ID de l'exposition
     * @param intitule         Intitulé de l'exposition
     * @param periodeOeuvreDeb Période de début du mouvenemt
     *                         artistique de l'oeuvre
     * @param periodeOeuvreFin Période de fin du mouvenemt artistique
     *                         de l'oeuvre
     * @param nbOeuvre         Nombre d'oeuvres comprises dans
     *                         l'exposition
     * @param motsCles         Mots clés de l'exposition
     * @param resume           Résumé de l'exposition
     * @param dateDebutExpo    Date de début de l'exposition
     * @param dateFinExpo      Date de fin de l'exposition
     */
    public Exposition(String idExposition,
                      String intitule,
                      String periodeOeuvreDeb,
                      String periodeOeuvreFin,
                      String nbOeuvre,
                      String[] motsCles,
                      String resume,
                      String dateDebutExpo,
                      String dateFinExpo) {

        // ---------- Vérification des données ----------

        // Véfification de la présence des données
        if (estNullOuVide(idExposition)) {
            throw new IllegalArgumentException(
                    "L'identifiant d'une exposition n'est pas renseigné.");
        }
        if (estNullOuVide(intitule)) {
            throw new IllegalArgumentException(
                    "L'intitulé d'une exposition n'est pas renseigné.");
        }
        if (estNullOuVide(periodeOeuvreDeb)) {
            throw new IllegalArgumentException(
                    "La période de début du mouvenemt artistique d'une oeuvre"
                            + " n'est pas renseignée.");
        }
        if (estNullOuVide(periodeOeuvreFin)) {
            throw new IllegalArgumentException(
                    "La période de fin du mouvenemt artistique d'une oeuvre"
                            + " n'est pas renseigné.");
        }
        if (estNullOuVide(nbOeuvre)) {
            throw new IllegalArgumentException(
                    "Le nombre d'oeuvres comprises dans l'exposition"
                            + " n'est pas renseigné.");
        }
        if (motsCles == null) {
            throw new IllegalArgumentException(
                    "Les mots clés de l'oeuvre ne sont pas renseignés.");
        }
        if (estNullOuVide(resume)) {
            throw new IllegalArgumentException(
                    "Le résumé d'une exposition n'est pas renseigné.");
        }
        if (dateDebutExpo == null) {
            throw new IllegalArgumentException(
                    "La date de début d'une exposition n'est pas renseigné.");
        }
        if (dateFinExpo == null) {
            throw new IllegalArgumentException(
                    "La date de fin d'une exposition n'est pas renseigné.");
        }
        
        // Vérification de la présence des deux dates
        if (estNullOuVide(dateDebutExpo) && !estNullOuVide(dateFinExpo)) {
            throw new IllegalArgumentException(
                    "Une exposition a une date de fin sans date de début.");
        }
        if (!estNullOuVide(dateDebutExpo) && estNullOuVide(dateFinExpo)) {
            throw new IllegalArgumentException(
                    "Une exposition a une date de début sans date de fin.");
        }
        
        // Vérification de la conformité des dates, si renseignées
        if (!"".equals(dateDebutExpo) && !"".equals(dateFinExpo)) {
            verifierDates(dateDebutExpo, dateFinExpo, "exposition",
                    "dd/MM/yyyy",
                    false);
            verifierDates(periodeOeuvreDeb, periodeOeuvreFin,
                    "mouvement artistique", "yyyy", true);
        }
        // Vérification des mots clés (max. 10)
        if (motsCles.length > 10) {
            throw new IllegalArgumentException(
                    "Les mots clés d'une exposition sont trop nombreux "
                            + "(max. 10 ; trouvés : " + motsCles.length + ").");
        }

        // Vérification du nombre d'oeuvres (doit être positif)
        try {
            if (Integer.parseInt(nbOeuvre) <= 0) {
                throw new IllegalArgumentException(
                      "Le nombre d'oeuvres d'une exposition est inférieur à 1");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "Le nombre d'oeuvres d'une exposition n'est pas un nombre"
                            + " correct.");
        }

        // Toutes les vérifications n'ont renvoyé aucune erreur
        this.idExposition = idExposition;
        this.intituleExposition = intitule;
        this.periodeOeuvreDeb = periodeOeuvreDeb;
        this.periodeOeuvreFin = periodeOeuvreFin;
        this.nombreOeuvre = nbOeuvre;
        this.motsCles = motsCles;
        this.resume = resume;
        this.dateDebutExpo = dateDebutExpo;
        this.dateFinExpo = dateFinExpo;
    }

    private boolean estNullOuVide(String chaine) {
        return chaine == null || chaine.trim().isEmpty();
    }

    private void verifierDates(String dateDebut, String dateFin,
            String typeDate, String formatDate, boolean anneeUniquement) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatDate);
        if (!anneeUniquement) {
            try {
                // Si ne lève pas d'exception, la date est valide.
                LocalDate.parse(dateDebut, formatter);

            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException(
                        "La date de début d'un(e) " + typeDate + " \""
                                + dateDebut
                                + "\" n'est pas une date valide.");
            }
        } else {
            try {
                // Si ne lève pas d'exception, l'année est valide.
                Year.parse(dateDebut, formatter);
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException(
                        "La date de début d'un(e) " + typeDate + " \""
                                + dateDebut
                                + "\" n'est pas une date valide.");
            }
        }

        if (!anneeUniquement) {
            try {
                // Si ne lève pas d'exception, la date est valide.
                LocalDate.parse(dateFin, formatter);

            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException(
                        "La date de fin d'un(e) " + typeDate + " \"" + dateFin
                                + "\" n'est pas une date valide.");
            }
        } else {
            try {
                // Si ne lève pas d'exception, l'année est valide.
                Year.parse(dateFin, formatter);
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException(
                        "La date de fin d'un(e) " + typeDate + " \"" + dateFin
                                + "\" n'est pas une date valide.");
            }
        }

        // Vérification de la cohérence des dates
        SimpleDateFormat formatDateSimple = new SimpleDateFormat(formatDate);

        try {
            Date dateDebutformatDate = formatDateSimple.parse(dateDebut);
            Date dateFinFormatDate = formatDateSimple.parse(dateFin);

            if (dateDebutformatDate.compareTo(dateFinFormatDate) > 0) {
                throw new IllegalArgumentException(
                        "Un(e) " + typeDate
                                + " est terminé(e) avant d'être commencé(e) "
                                + "(date(s) début/fin incorrecte(s)).");
            }
        } catch (ParseException e) {
            throw new IllegalArgumentException(
                    "Une date d'un(e) " + typeDate + " n'est pas au "
                            + "format JJ/MM/AAAA");
        }
    }


    /**
     * Retourne l'ID de l'exposition.
     * 
     * @return ID de l'exposition
     */
    public String getIdExposition() {
        return idExposition;
    }

    /**
     * Retourne l'intitulé de l'exposition.
     * 
     * @return Intitulé de l'exposition
     */
    public String getIntituleExposition() {
        return intituleExposition;
    }

    /**
     * Retourne la période de début du mouvement artistique de
     * l'œuvre.
     * 
     * @return Période de début du mouvement artistique de l'œuvre
     */
    public String getPeriodeOeuvreDeb() {
        return periodeOeuvreDeb;
    }

    /**
     * Retourne la période de fin du mouvement artistique de l'œuvre.
     * 
     * @return Période de fin du mouvement artistique de l'œuvre
     */
    public String getPeriodeOeuvreFin() {
        return periodeOeuvreFin;
    }

    /**
     * Retourne le nombre d'œuvres comprises dans l'exposition.
     * 
     * @return Nombre d'œuvres comprises dans l'exposition
     */
    public String getNombreOeuvre() {
        return nombreOeuvre;
    }

    /**
     * Retourne les mots clés de l'exposition.
     * 
     * @return Mots clés de l'exposition
     */
    public String[] getMotsCles() {
        return motsCles;
    }

    /**
     * Obtient les mots-clés sous forme de chaîne, séparés par des
     * virgules
     * 
     * @return chaine de mots clés séparés par des virgules
     */
    public String getMotsClesString() {
        return String.join(", ", motsCles);
    }

    /**
     * Retourne le résumé de l'exposition.
     * 
     * @return Résumé de l'exposition
     */
    public String getResume() {
        return resume;
    }

    /**
     * Retourne la date de début de l'exposition.
     * 
     * @return Date de début de l'exposition
     */
    public String getDateDebutExpo() {
        return dateDebutExpo;
    }

    /**
     * Retourne la date de fin de l'exposition.
     * 
     * @return Date de fin de l'exposition
     */
    public String getDateFinExpo() {
        return dateFinExpo;
    }
    
}