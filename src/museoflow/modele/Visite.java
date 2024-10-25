package museoflow.modele;

import java.util.Date;

public class Visite {
	
	public String idVisite;

    public Exposition exposition;

    public Conferencier conferencier;

    public String horaireDebutVisite;

    public Date dateVisite;

    public String intitule;

    public String telephoneClient;

    public Visite(String idVisite, Exposition exposition, Conferencier conferencier, String horaireDebutVisite, Date dateVisite, String intitule, String telephoneClient) {
    	this.idVisite=idVisite;
    	this.exposition=exposition;
    	this.conferencier=conferencier;
    	this.horaireDebutVisite=horaireDebutVisite;
    	this.dateVisite=dateVisite;
    	this.intitule=intitule;
    	this.telephoneClient=telephoneClient;
    }

}
