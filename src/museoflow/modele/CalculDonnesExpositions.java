package museoflow.modele;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * Cette classe est responsable des calculs statistiques et des filtrages liés aux expositions.
 * Elle fournit des méthodes pour :
 * - Identifier les expositions sans visite planifiée dans une période donnée.
 * - Calculer des moyennes de visites par jour ou par semaine, en fonction d'un filtre donné.
 */
public class CalculDonnesExpositions {
	
    private Filtre filtre; // Filtre utilisé pour définir les critères de recherche.

    /**
     * Retourne une liste des expositions qui n'ont aucune visite planifiée
     * dans la période définie par le filtre.
     *
     * @param filtre Objet contenant les critères de filtrage (période, type d'exposition, etc.)
     * @return Liste des expositions sans visite.
     */
    public List<Exposition> pasDeVisite(Filtre filtre) {
        List<Exposition> expositionsSansVisite = new ArrayList<>();
        
        // Récupère toutes les expositions disponibles
        List<Exposition> toutesLesExpositions = obtenirToutesLesExpositions();
        
        // Filtre les expositions selon les critères définis par le filtre
        for (Exposition exposition : toutesLesExpositions) {
            if (correspondFiltre(exposition, filtre) && aucuneVisite(exposition, filtre)) {
                expositionsSansVisite.add(exposition);
            }
        }
        return expositionsSansVisite;
    }

    /**
     * Vérifie si une exposition donnée n'a aucune visite planifiée
     * dans la période et les horaires spécifiés par le filtre.
     *
     * @param exposition L'exposition à vérifier.
     * @param filtre Critères pour vérifier les visites.
     * @return true si aucune visite n'est prévue, false sinon.
     */
    private boolean aucuneVisite(Exposition exposition, Filtre filtre) {
        return obtenirVisitesParExposition(exposition, filtre).isEmpty();
    }

    /**
     * Calcule le nombre moyen de visites par jour pour les expositions
     * filtrées selon les critères spécifiés.
     *
     * @param filtre Critères pour filtrer les expositions et visites.
     * @return Nombre moyen de visites par jour (0 si aucune exposition ne correspond).
     */
    public double nbMoyenVisiteJour(Filtre filtre) {
        List<Exposition> expositionsFiltrees = filtrerExpositions(filtre);

        double totalVisites = 0;
        double totalJours = 0;

        for (Exposition exposition : expositionsFiltrees) {
            List<Visite> visites = obtenirVisitesParExposition(exposition, filtre);
            totalVisites += visites.size();

            LocalDate debut = LocalDate.parse(exposition.getDateDebutExpo());
            LocalDate fin = LocalDate.parse(exposition.getDateFinExpo());
            
            long jours = ChronoUnit.DAYS.between(debut, fin) + 1; // Inclut les deux dates
            totalJours += jours;
        }

        return totalJours == 0 ? 0 : totalVisites / totalJours;
    }

    /**
     * TODO : Implémenter la méthode pour calculer le nombre moyen de visites par semaine.
     *
     * @param filtre Critères pour filtrer les expositions et visites.
     */
    public void nbMoyenVisiteSemaine(Filtre filtre) {
        // À implémenter
    }

    /**
     * Filtre les expositions selon les critères définis dans le filtre.
     *
     * @param filtre Critères de filtrage.
     * @return Liste des expositions correspondant aux critères.
     */
    private List<Exposition> filtrerExpositions(Filtre filtre) {
        List<Exposition> resultat = new ArrayList<>();

        for (Exposition exposition : obtenirToutesLesExpositions()) {
            if (correspondFiltre(exposition, filtre)) {
                resultat.add(exposition);
            }
        }

        return resultat;
    }

    /**
     * Vérifie si une exposition correspond aux critères spécifiés par le filtre.
     *
     * @param exposition L'exposition à vérifier.
     * @param filtre Critères de filtrage.
     * @return true si l'exposition correspond aux critères, false sinon.
     */
    private boolean correspondFiltre(Exposition exposition, Filtre filtre) {
        boolean correspondType = (filtre.getTypeExposition() == null ||
            exposition.getResume().contains(filtre.getTypeExposition()));
        
        boolean correspondDates = (filtre.getDateDebut() == null || 
            filtre.getDateFin() == null || 
            (!LocalDate.parse(exposition.getDateDebutExpo()).isAfter(filtre.getDateFin()) &&
             !LocalDate.parse(exposition.getDateFinExpo()).isBefore(filtre.getDateDebut())));
        
        return correspondType && correspondDates;
    }

    /**
     * Récupère les visites associées à une exposition donnée,
     * filtrées selon les critères spécifiés.
     *
     * @param exposition L'exposition concernée.
     * @param filtre Critères pour filtrer les visites.
     * @return Liste des visites correspondant aux critères.
     */
    private List<Visite> obtenirVisitesParExposition(Exposition exposition, Filtre filtre) {
        List<Visite> toutesLesVisites = obtenirToutesLesVisites(); // Récupère toutes les visites
        List<Visite> visitesFiltrees = new ArrayList<>();

        for (Visite visite : toutesLesVisites) {
            if (visite.getExposition().equals(exposition.getIdExposition())
                && correspondFiltreVisite(visite, filtre)) {
                visitesFiltrees.add(visite);
            }
        }

        return visitesFiltrees;
    }

    /**
     * Vérifie si une visite correspond aux critères définis dans le filtre.
     *
     * @param visite La visite à vérifier.
     * @param filtre Critères de filtrage.
     * @return true si la visite correspond aux critères, false sinon.
     */
    private boolean correspondFiltreVisite(Visite visite, Filtre filtre) {
        LocalDate dateVisite = LocalDate.parse(visite.getDateVisite());
        LocalDate dateDebut = filtre.getDateDebut();
        LocalDate dateFin = filtre.getDateFin();

        boolean correspondDates = (dateDebut == null || dateFin == null
            || (!dateVisite.isBefore(dateDebut) && !dateVisite.isAfter(dateFin)));

        LocalTime heureVisite = LocalTime.parse(visite.getDateVisite());
        LocalTime heureDebut = filtre.getHeureDebut();
        LocalTime heureFin = filtre.getHeureFin();

        boolean correspondHeures = (heureDebut == null || heureFin == null
            || (!heureVisite.isBefore(heureDebut) && !heureVisite.isAfter(heureFin)));

        return correspondDates && correspondHeures;
    }

    // Méthodes stub pour simuler l'accès aux données (à implémenter avec des sources réelles).
    private List<Exposition> obtenirToutesLesExpositions() {
        return new ArrayList<>(); // Exemple à remplacer par une vraie source.
    }

    private List<Visite> obtenirToutesLesVisites() {
        return new ArrayList<>(); // Exemple à remplacer par une vraie source.
    }
}
