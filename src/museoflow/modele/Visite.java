/*
 * Employe.java                                             nov. 2024 
 * IUT de Rodez Info2 TPD 2024-2025, pas de copyright
 */
package museoflow.modele;

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
     * passées en paramètres.
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
     */
    public Visite(String idVisite, 
                  String exposition,
                  String conferencier, 
                  String employe, 
                  String dateVisite,
                  String horaireDebutVisite,
                  String intitule, 
                  String telephoneConferencier) {

        // TODO gestion d'erreur de données CSV
        
        this.idVisite = idVisite;
        this.exposition = exposition;
        this.conferencier = conferencier;
        this.employe = employe;
        this.dateVisite = dateVisite;
        this.horaireDebutVisite = horaireDebutVisite;
        this.intitule = intitule;
        this.telephoneConferencier = telephoneConferencier;
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