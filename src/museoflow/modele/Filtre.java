package museoflow.modele;

import java.time.LocalDate;
import java.time.LocalTime;

public class Filtre {
    private String typeExposition; // "permanente" ou "temporaire"
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private LocalTime heureDebut;
    private LocalTime heureFin;

    public Filtre(String typeExposition, LocalDate dateDebut, LocalDate dateFin,
                  LocalTime heureDebut, LocalTime heureFin) {
        this.typeExposition = typeExposition;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
    }

    public String getTypeExposition() {
        return typeExposition;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public LocalTime getHeureDebut() {
        return heureDebut;
    }

    public LocalTime getHeureFin() {
        return heureFin;
    }
}
