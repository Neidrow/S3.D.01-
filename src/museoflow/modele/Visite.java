package museoflow.modele;

import java.util.Date;

/**
 * Classe objet représentant une visite
 */
public class Visite {
	
    private String idVisite;

    private Exposition exposition;

    private Conferencier conferencier;

    private String horaireDebutVisite;

    private Date dateVisite;

    private String intitule;

    private String telephoneClient;

    /**
     * Constructeur créant une visite
     * 
     * @param idVisite
     * @param exposition
     * @param conferencier
     * @param horaireDebutVisite
     * @param dateVisite
     * @param intitule
     * @param telephoneClient
     */
    public Visite(String idVisite, Exposition exposition, Conferencier conferencier, String horaireDebutVisite, Date dateVisite, String intitule, String telephoneClient) {
    	this.idVisite = idVisite;
    	this.exposition = exposition;
    	this.conferencier = conferencier;
    	this.horaireDebutVisite = horaireDebutVisite;
    	this.dateVisite = dateVisite;
    	this.intitule = intitule;
    	this.telephoneClient = telephoneClient;
    }

}
