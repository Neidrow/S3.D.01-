/*
 * Filtre.java                           oct. 2024
 * IUT de Rodez Info2 TPD 2024-2025, pas de copyright 
 */
package museoflow.modele;

import java.time.LocalDate;
import java.time.LocalTime;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Classe gérant les filtres
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
	 * Méthode pour filtrer les conférenciers en fonction du type (internes, externes ou tous)
	 * @param filtre
	 * @return
	 */
    public static ObservableList<Conferencier> filtreTypeConferenciersFiltres(String filtre) {
        ObservableList<Conferencier> tousConferenciers = FXCollections.observableArrayList(GestionFichiers.getConferenciers());

        ObservableList<Conferencier> conferenciersFiltres = FXCollections.observableArrayList();

        for (Conferencier conferencier : tousConferenciers) {
            if ("Internes".equals(filtre) && conferencier.isEmployeParMusee()) {
                conferenciersFiltres.add(conferencier);
            } else if ("Externes".equals(filtre) && !conferencier.isEmployeParMusee()) {
                conferenciersFiltres.add(conferencier);
            } else if ("Tous".equals(filtre)) {
                conferenciersFiltres.add(conferencier);
            }
        }

        // Trier les expositions filtrées et recalculer leur classement
        Statistique.trierConferenciersParVisites(conferenciersFiltres);

        return conferenciersFiltres;
    }
    
	/** 
	 * Méthode pour filtrer les conférenciers en fonction du type (internes, externes ou tous)
	 * @param filtre
	 * @return
	 */
    public static ObservableList<Exposition> filtreTypeExpo(String filtre) {
        ObservableList<Exposition> toutesExposition = FXCollections.observableArrayList(GestionFichiers.getExpositions());

        ObservableList<Exposition> expositionFiltres = FXCollections.observableArrayList();

        for (Exposition exposition : toutesExposition) {
            if ("Permanentes".equals(filtre) && exposition.isExpoPermanente()) {
            	expositionFiltres.add(exposition);
            } else if ("Temporaires".equals(filtre) && !exposition.isExpoPermanente()) {
            	expositionFiltres.add(exposition);
            } else if ("Toutes".equals(filtre)) {
            	expositionFiltres.add(exposition);
            }
        }

        // Trier les expositions filtrées et recalculer leur classement
        Statistique.trierExpositionsParVisites(expositionFiltres);

        return expositionFiltres;
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
