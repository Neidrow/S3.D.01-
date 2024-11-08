package museoflow.modele;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe objet représentant un conférencier.
 * 
 * @author Amjed SEHIL
 * @author Cylian POUPIN
 */
public class Conferencier {

    private String IdConferencier;
	
    private String nomConferencier;

    private String prenomConferencier;

    private String[] specialite;

    private String telephone;

    private boolean employeParMusee;

    private List<String> indisponibilites = new ArrayList<>();

    /**
     * <p>
     * Construit un Conferencier avec les valeurs en paramètres.
     * </p>
     * 
     * @param IdConferencier   ID du conférencier
     * @param nom              Nom du conférencier
     * @param prenom           Prénom du conférencier
     * @param specialite       Spécialité du conférencier
     * @param telephone        Téléphone du conférencier
     * @param employeParMusee  true si le conférencier est interne au
     *                         musée, false sinon
     * @param indisponibilites Liste des indisponibilités du
     *                         conférencier
     * @return true si la construction a été effectuée, false sinon
     */
    public Conferencier(String IdConferencier,
            String nom,
            String prenom,
            String[] specialite,
            String telephone,
            boolean employeParMusee,
            List<String> indisponibilites) {

            this.IdConferencier = IdConferencier;
            this.nomConferencier = nom;
            this.prenomConferencier = prenom;
            this.specialite = specialite;
            this.telephone = telephone;
            this.employeParMusee = employeParMusee;
            this.indisponibilites = indisponibilites;
    }

    /**
     * Retourne l'ID du conférencier.
     * 
     * @return ID du conférencier
     */
    public String getIdConferencier() {
        return IdConferencier;
    }

    /**
     * Retourne le nom du conférencier.
     * 
     * @return Nom du conférencier
     */
    public String getNom() {
        return nomConferencier;
    }

    /**
     * Retourne le prénom du conférencier.
     * 
     * @return Prénom du conférencier
     */
    public String getPrenom() {
        return prenomConferencier;
    }

    /**
     * Retourne la spécialité du conférencier.
     * 
     * @return Spécialité du conférencier
     */
    public String[] getSpecialite() {
        return specialite;
    }

    /**
     * Retourne le téléphone du conférencier.
     * 
     * @return Téléphone du conférencier
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * Indique si le conférencier est employé par le musée.
     * 
     * @return true si le conférencier est interne au musée, false
     *         sinon
     */
    public boolean isEmployeParMusee() {
        return employeParMusee;
    }

    /**
     * Retourne les indisponibilités du conférencier.
     * 
     * @return Indisponibilités du conférencier
     */
    public List<String> getIndisponibilites() {
        return indisponibilites;
    }
}