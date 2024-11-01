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
     * Constructeur créant une exposition vide (touts les attrubuts
     * sont initialisés à null).
     */
    public Exposition() {
        idExposition = null;
        intituleExposition = null;
        periodeOeuvreDeb = null;
        periodeOeuvreFin = null;
        nombreOeuvre = null;
        motsCles = null;
        resume = null;
        dateDebutExpo = null;
        dateFinExpo = null;
    }
    
    /**
     * <p>
     * Méthode faisant office de constructeur affectant à une
     * exposition les valeurs passées en paramètres à un objet
     * Exposition supposé vide déja créé.
     * </p>
     * Cette méthode n'est volontairement pas un constructeur pour des
     * raisons techniques propres au fonctionnenent de la création les
     * objets Exposition dans GestionFichiers.
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
    public void construireExposition(String idExposition, 
                                     String intitule,
                                     String periodeOeuvreDeb, 
                                     String periodeOeuvreFin, 
                                     String nbOeuvre,
                                     String[] motsCles,
                                     String resume, 
                                     String dateDebutExpo, 
                                     String dateFinExpo) {

        // TODO tests de ce if ↓

        // On vérifie si touts les attributs sont null pour interdire
        // la modification d'une exposition déja crée (pas d'effet de
        // bords)
        if (this.idExposition == null 
            && this.intituleExposition == null 
            && this.periodeOeuvreDeb == null 
            && this.periodeOeuvreFin == null 
            && this.motsCles == null 
            && this.resume == null 
            && this.dateDebutExpo == null 
            && this.dateFinExpo == null) {
            
        this.idExposition = idExposition;
        this.intituleExposition = intitule;
        this.periodeOeuvreDeb = periodeOeuvreDeb;
        this.periodeOeuvreFin = periodeOeuvreFin;
        this.periodeOeuvreFin = nbOeuvre;
        this.motsCles = motsCles;
        this.resume = resume;
        this.dateDebutExpo = dateDebutExpo;
        this.dateFinExpo = dateFinExpo;

    } else {
        System.out.println("Exposition déja créée avec attributs ! \n"
                + "L'objet n'a pas été modifié.");
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