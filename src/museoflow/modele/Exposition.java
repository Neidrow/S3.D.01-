/*
 * Exposition.java                                  oct. 2024 
 * IUT de Rodez Info2 TPD 2024-2025, pas de copyright
 */
package museoflow.modele;

/**
 * Classe objet représentant une exposition
 * 
 * @author Aurelien VALAT
 * @author Cylian POUPIN
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

        // TODO gestion d'erreur de données CSV
        
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