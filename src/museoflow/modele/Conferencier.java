package museoflow.modele;

import java.util.ArrayList;
import java.util.Date;

/**
 * Classe objet représentant un conferencier
 * 
 * @author Aurélien VALAT
 */
public class Conferencier {
	
    private String nomConferencier;

    private String prenomConferencier;

    private String specialite;

    private String telephone;

    private boolean employeParMusee;

    private ArrayList<Date> indisponibilites;

    /**
     * Constructeur créant un conferencier
     * 
     * @param nom
     * @param prenom
     * @param specialite
     * @param telephone
     * @param employeParMusee
     * @param indisponibilites
     * @throws HomonymeException
     */
    public Conferencier(String nom, String prenom, String specialite, String telephone, boolean employeParMusee, ArrayList<Date> indisponibilites) throws HomonymeException {
    this.nomConferencier = nom;
    this.prenomConferencier = prenom;
    this.specialite = specialite;
    this.telephone = telephone;
    this.employeParMusee = employeParMusee;
    this.indisponibilites = indisponibilites;
    
    }

}
