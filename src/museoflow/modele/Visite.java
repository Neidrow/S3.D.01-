/*
 * Employe.java                                             nov. 2024 
 * IUT de Rodez Info2 TPD 2024-2025, pas de copyright
 */
package museoflow.modele;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Classe objet représentant une visite.
 * 
 * @author Aurélien VALAT
 * @author Cylian POUPIN
 */
public class Visite {
	
    private String idVisite;

    private String exposition;

    private String conferencier;

    private String employe;

    private String dateVisite;

    private String horaireDebutVisite;

    private String intitule;

    private String telephoneConferencier;


    /**
     * <p>
     * Constructeur créant et affectant à une visite les valeurs
     * passées en paramètres (après vérification).
     * </p>
     * 
     * @param idVisite              ID de la visite
     * @param exposition            ID de l'exposition
     * @param conferencier          ID du conférencier
     * @param employe               ID de l'employé ayant pris en
     *                              compte la réservation
     * @param dateVisite            Date de la visite
     * @param horaireDebutVisite    Horaire de début de la visite
     * @param intitule              Intitulé de la visite
     * @param telephoneConferencier No de téléphone du conférencier
     *                              assurant la visite
     * @throws IllegalArgumentException si un paramètre est null ou
     *                                  vide.
     */
    public Visite(String idVisite, 
                  String exposition,
                  String conferencier, 
                  String employe, 
                  String dateVisite,
                  String horaireDebutVisite,
                  String intitule, 
                  String telephoneConferencier) 
            throws IllegalArgumentException {

        // ---------- Vérification des données ----------

        // Véfification de la présence des données
        if (estNullOuVide(idVisite)) {
            throw new IllegalArgumentException(
                    "L'identifiant d'une visite n'est pas renseigné.");
        }
        if (estNullOuVide(exposition)) {
            throw new IllegalArgumentException(
                    "L'identifiant d'une exposition n'est pas renseigné.");
        }
        if (estNullOuVide(conferencier)) {
            throw new IllegalArgumentException(
                    "L'identifiant d'un conférencier n'est pas renseigné.");
        }
        if (estNullOuVide(employe)) {
            throw new IllegalArgumentException(
                    "L'identifiant d'un employé n'est pas renseigné.");
        }
        if (estNullOuVide(dateVisite)) {
            throw new IllegalArgumentException(
                    "La date d'une visite n'est pas renseignée.");
        }
        if (estNullOuVide(horaireDebutVisite)) {
            throw new IllegalArgumentException(
                    "L'horaire de début d'une visite n'est pas renseigné.");
        }
        if (estNullOuVide(intitule)) {
            throw new IllegalArgumentException(
                    "L'intitulé d'une visite n'est pas renseigné.");
        }
        if (estNullOuVide(telephoneConferencier)) {
            throw new IllegalArgumentException(
                    "Le numéro de téléphone d'un conférencier n'est pas renseigné.");
        }

        // Vérification de la date de la visite
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            // Si ne lève pas d'exception, la date est valide.
            LocalDate.parse(dateVisite, formatter);

        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("La date de visite \""
                    + dateVisite + "\" n'est pas une date valide.");
        }

        // Vérification de l'heure de la visite
        formatter = DateTimeFormatter.ofPattern("HH'h'mm");
        try {
            // Si ne lève pas d'exception, l'heure est valide.
            LocalDate.parse(horaireDebutVisite, formatter);

        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("L'heure de visite \""
                    + horaireDebutVisite + "\" n'est pas valide.");
        }

        // Vérification du numéro de téléphone
        if (!telephoneConferencier.matches("\\d{10}")) {
            throw new IllegalArgumentException("Le numéro de téléphone "
                    + telephoneConferencier + "est incorrect.");
        }

        // Toutes les vérifications n'ont renvoyé aucune erreur
        this.idVisite = idVisite;
        this.exposition = exposition;
        this.conferencier = conferencier;
        this.employe = employe;
        this.dateVisite = dateVisite;
        this.horaireDebutVisite = horaireDebutVisite;
        this.intitule = intitule;
        this.telephoneConferencier = telephoneConferencier;
    }

    private boolean estNullOuVide(String chaine) {
        return chaine == null || chaine.trim().isEmpty();
    }


    /**
     * @return valeur de idVisite
     */
    public String getIdVisite() {
        return idVisite;
    }

    /**
     * @return valeur de exposition
     */
    public String getExposition() {
        return exposition;
    }

    /**
     * @return valeur de conferencier
     */
    public String getConferencier() {
        return conferencier;
    }

    /**
     * @return valeur de employe
     */
    public String getEmploye() {
        return employe;
    }

    /**
     * @return valeur de dateVisite
     */
    public String getDateVisite() {
        return dateVisite;
    }

    /**
     * @return valeur de horaireDebutVisite
     */
    public String getHoraireDebutVisite() {
        return horaireDebutVisite;
    }

    /**
     * @return valeur de intitule
     */
    public String getIntitule() {
        return intitule;
    }

    /**
     * @return valeur de telephoneConferencier
     */
    public String getTelephoneConferencier() {
        return telephoneConferencier;
    }
}