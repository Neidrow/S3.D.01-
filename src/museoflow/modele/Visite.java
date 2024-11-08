/*
 * Employe.java nov. 2024 IUT de Rodez Info2 TPD 2024-2025, pas de
 * copyright
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

    private Exposition exposition;

    private Conferencier conferencier;

    private String horaireDebutVisite;

    private String dateVisite;

    private String intitule;

    private String telephoneConferencier;

    /**
     * Constructeur créant un employé vide (touts les attrubuts sont
     * initialisés à null).
     */
    public Visite() {
        this.idVisite = null;
        this.exposition = null;
        this.conferencier = null;
        this.horaireDebutVisite = null;
        this.dateVisite = null;
        this.intitule = null;
        this.telephoneConferencier = null;
    }

    /**
     * <p>
     * Méthode faisant office de constructeur affectant à une visite
     * les valeurs passées en paramètres à un objet Visite vide déja
     * créé.
     * </p>
     * Cette méthode n'est volontairement pas un constructeur pour des
     * raisons techniques propres au fonctionnenent de la création des
     * objets dans GestionFichiers.
     * 
     * @param idVisite              ID de la visite
     * @param exposition            ID de l'exposition
     * @param conferencier          ID du conférencier
     * @param horaireDebutVisite    Horaire de début de la visite
     * @param dateVisite            Date de la visite
     * @param intitule              Intitulé de la visite
     * @param telephoneConferencier No de téléphone du conférencier
     *                              assurant la visite
     * @return true si la construction a été effectuée, false sinon
     */
    public boolean construireEmploye(String idVisite, Exposition exposition,
            Conferencier conferencier, String horaireDebutVisite,
            String dateVisite, String intitule, String telephoneConferencier) {
        // On vérifie si touts les attributs sont null pour interdire
        // la modification d'une exposition déja crée (pas d'effet de
        // bords)
        if (this.idVisite == null
                && this.exposition == null
                && this.conferencier == null
                && this.horaireDebutVisite == null
                && this.dateVisite == null
                && this.intitule == null
                && this.telephoneConferencier == null) {

            // TODO changer les objets en string si pas besoin
            // d'avoir la ref ?

            this.exposition = exposition;
            this.conferencier = conferencier;
            this.horaireDebutVisite = horaireDebutVisite;
            this.dateVisite = dateVisite;
            this.intitule = intitule;
            this.intitule = intitule;
            this.telephoneConferencier = telephoneConferencier;

            return true;

        } else {
            System.out.println("Employé déja créé avec attributs ! \n"
                    + "L'objet n'a pas été modifié.");
            return false;
        }
    }

}
