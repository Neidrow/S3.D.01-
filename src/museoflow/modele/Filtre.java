/*
 * Filtre.java                           oct. 2024
 * IUT de Rodez Info2 TPD 2024-2025, pas de copyright 
 */
package museoflow.modele;

import java.time.LocalDate;
import java.time.LocalTime;

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
