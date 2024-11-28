/*
 * Filtre.java                                  oct. 2024 
 * IUT de Rodez Info2 TPD 2024-2025, pas de copyright
 */
package museoflow.modele;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import museoflow.modele.exceptions.ValidationException;

/**
 * Classe gérant les filtres
 * 
 * @author Landry Loubière Aurélien Valat
 */
public class Filtre {
    private String typeExposition; // "permanente" ou "temporaire"
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private LocalTime heureDebut;
    private LocalTime heureFin;

    /**
     * Constructeur des filtres
     * 
     * @param typeExposition
     * @param dateDebut
     * @param dateFin
     * @param heureDebut
     * @param heureFin
     */
    public Filtre(String typeExposition, LocalDate dateDebut, LocalDate dateFin,
            LocalTime heureDebut, LocalTime heureFin) {
        this.typeExposition = typeExposition;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
    }

    /**
     * Méthode pour filtrer les conférenciers en fonction du type
     * (internes, externes ou tous)
     * 
     * @param filtre Le filtre à appliquer
     * @return Les filtres appliqués aux conférenciers
     */
    public static ObservableList<Conferencier> filtreTypeConferenciersFiltres(
            String filtre) {
        ObservableList<Conferencier> tousConferenciers = FXCollections
                .observableArrayList(GestionFichiers.getConferenciers());

        ObservableList<Conferencier> conferenciersFiltres =
                FXCollections.observableArrayList();

        for (Conferencier conferencier : tousConferenciers) {
            if ("Internes".equals(filtre) && conferencier.isEmployeParMusee()) {
                conferenciersFiltres.add(conferencier);
            } else if ("Externes".equals(filtre)
                    && !conferencier.isEmployeParMusee()) {
                conferenciersFiltres.add(conferencier);
            } else if ("Tous".equals(filtre)
                    || "Filtrer par type de conferencier".equals(filtre)) {
                conferenciersFiltres.add(conferencier);
            }
        }

        // Trier les expositions filtrées et recalculer leur
        // classement
        Statistique.trierConferenciersParVisites(conferenciersFiltres);

        return conferenciersFiltres;
    }

    /**
     * Méthode pour filtrer les conférenciers en fonction du type
     * (internes, externes ou tous)
     * 
     * @param filtre Le filtre à appliquer
     * @return Les filtres appliqués aux expositions
     */
    public static ObservableList<Exposition> filtreTypeExpo(String filtre) {
        ObservableList<Exposition> toutesExposition = FXCollections
                .observableArrayList(GestionFichiers.getExpositions());

        ObservableList<Exposition> expositionFiltres =
                FXCollections.observableArrayList();

        for (Exposition exposition : toutesExposition) {
            if ("Permanentes".equals(filtre) && exposition.isExpoPermanente()) {
                expositionFiltres.add(exposition);
            } else if ("Temporaires".equals(filtre)
                    && !exposition.isExpoPermanente()) {
                expositionFiltres.add(exposition);
            } else if ("Toutes".equals(filtre)
                    || "Filtrer par type d'exposition".equals(filtre)) {
                expositionFiltres.add(exposition);
            }
        }

        // Trier les expositions filtrées et recalculer leur
        // classement
        Statistique.trierExpositionsParVisites(expositionFiltres);

        return expositionFiltres;
    }

    /**
     * Filtre les visites en fonction des critères donnés.
     *
     * @param visites         La liste originale de visites.
     * @param idConferencier  Critère de recherche par ID du
     *                        conférencier.
     * @param idExposition    Critère de recherche par ID d'exposition
     * @param dateDebut       Critère de date de début.
     * @param dateFin         Critère de date de fin.
     * @param heureDebutPlage Critère d'horaire de début
     * @param heureFinPlage   Critère d'horaire de fin
     * @return Une liste observable de visites filtrées.
     */
    public static ObservableList<Visite> filtrerVisites(
            ObservableList<Visite> visites,
            String idConferencier,
            String idExposition,
            LocalDate dateDebut,
            LocalDate dateFin,
            String heureDebutPlage,
            String heureFinPlage) {

        // Créer une liste pour stocker les visites filtrées
        ObservableList<Visite> visitesFiltrees =
                FXCollections.observableArrayList();

        // Parcourir chaque visite et appliquer les filtres
        for (Visite visite : visites) {
            if (estVisiteValide(visite, idConferencier, idExposition, dateDebut,
                    dateFin, heureDebutPlage, heureFinPlage)) {
                visitesFiltrees.add(visite);
            }
        }

        return visitesFiltrees;
    }

    /**
     * Vérifie si une visite correspond aux critères de filtrage.
     *
     * @param visite         La visite à vérifier.
     * @param idConferencier Le critère d'ID du conférencier.
     * @param idExposition   Le critère d'ID d'exposition.
     * @param dateDebut      La date de début (peut être null).
     * @param dateFin        La date de fin (peut être null).
     * @return true si la visite correspond aux critères, sinon false.
     */
    private static boolean estVisiteValide(
            Visite visite,
            String idConferencier,
            String idExposition,
            LocalDate dateDebut,
            LocalDate dateFin,
            String heureDebutPlage,
            String heureFinPlage) {

        // Créer un formatteur pour les dates
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // Vérifier les critères d'ID
        boolean matchIdConf =
                (idConferencier == null || idConferencier.isEmpty()) ||
                        visite.getConferencier().contains(idConferencier);
        boolean matchIdExpo =
                (idExposition == null || idExposition.isEmpty()) ||
                        visite.getExposition().contains(idExposition);

        // Vérifier les critères de date
        boolean matchDateDebut = true;
        boolean matchDateFin = true;

        // Conversion des horaires en LocalTime
        LocalTime debutPlage = null;
        LocalTime finPlage = null;
        try {
            if (heureDebutPlage != null) {
                debutPlage = convertirEnLocalTime(heureDebutPlage);
            }
            if (heureFinPlage != null) {
                finPlage = convertirEnLocalTime(heureFinPlage);
            }
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            return false;
        }

        // Vérifier la plage horaire
        boolean matchHoraire = true;
        try {
            if (debutPlage != null && finPlage != null) {
                LocalTime heureVisite =
                        LocalTime.parse(visite.getHoraireDebutVisite(),
                                DateTimeFormatter.ofPattern("HH'h'mm"));
                matchHoraire = !heureVisite.isBefore(debutPlage)
                        && !heureVisite.isAfter(finPlage);
            }
        } catch (DateTimeParseException e) {
            System.err.println("Erreur de parsing pour l'heure de visite : "
                    + visite.getHoraireDebutVisite());
            return false;
        }

        // Conversion des dates si elles ne sont pas déjà des
        // LocalDate
        try {

            if (dateDebut != null) {
                LocalDate dateVisite =
                        LocalDate.parse(visite.getDateVisite(), formatter);
                matchDateDebut = !dateVisite.isBefore(dateDebut);
            }
            if (dateFin != null) {
                LocalDate dateVisite =
                        LocalDate.parse(visite.getDateVisite(), formatter);
                matchDateFin = !dateVisite.isAfter(dateFin);
            }
        } catch (DateTimeParseException e) {
            System.err.println("Erreur de parsing pour la date : "
                    + visite.getDateVisite());
            return false;
        }

        return matchIdConf && matchIdExpo && matchDateDebut && matchDateFin
                && matchHoraire;
    }
    

    private static LocalTime convertirEnLocalTime(String heure) {
        if (heure == null || heure.isEmpty()) {
            return null;
        }

        String[] formatsPossibles =
                { "HH:mm", "H:mm", "HH'h'mm", "H'h'mm", "H'h'", "HH", "HH'h'" };
        for (String format : formatsPossibles) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
                return LocalTime.parse(heure, formatter);
            } catch (DateTimeParseException e) {

            }
        }

        // Si aucun format ne correspond, lever une exception
        throw new ValidationException("Format d'heure invalide : " + heure
                + ". Formats acceptés : HH:mm, H:mm, HH'h'mm, H'h'mm, H'h', HH, HH'h'.");
    }

    private static void validerDates(LocalDate dateDebut, LocalDate dateFin) {
        if (dateDebut != null && dateFin != null
                && dateDebut.isAfter(dateFin)) {
            throw new ValidationException(
                    "La date de début ne peut pas être après la date de fin.");
        }
    }

    /**
     * Donne accès à la validations des dates et des formats
     * d'horaires
     * 
     * @param dateDebut
     * @param dateFin
     * @param heureDebut
     * @param heureFin
     */
    public static void verifierFiltres(LocalDate dateDebut, LocalDate dateFin,
            String heureDebut, String heureFin) {
        validerDates(dateDebut, dateFin); // Appelle la méthode privée

        // Convertir et valider les horaires
        convertirEnLocalTime(heureDebut);
        convertirEnLocalTime(heureFin);
    }


    /**
     * Retourne le type de l'exposition
     * 
     * @return typeExposition
     */
    public String getTypeExposition() {
        return typeExposition;
    }

    /**
     * Retourne la date de début
     * 
     * @return dateDebut
     */
    public LocalDate getDateDebut() {
        return dateDebut;
    }

    /**
     * Retourne la date de fin
     * 
     * @return dateFin
     */
    public LocalDate getDateFin() {
        return dateFin;
    }

    /**
     * Retourne l'heure de début
     * 
     * @return heureDebut
     */
    public LocalTime getHeureDebut() {
        return heureDebut;
    }

    /**
     * Retourne l'heure de fin
     * 
     * @return heureFin
     */
    public LocalTime getHeureFin() {
        return heureFin;
    }
}