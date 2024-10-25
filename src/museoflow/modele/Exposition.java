package museoflow.modele;

import java.util.Date;
import java.util.List;

public class Exposition {
	
	private String idExposition;

    public String intituleExposition;

    private int periodeOeuvreDeb;

    private int periodeOeuvreFin;

    private int nombreOeuvre;

    private String motsCles;

    public String resume;

    public Date dateDebutExpo;

    public Date dateFinExpo;

    // Peut-être changer le type de motCles en tableau
    public Exposition(String idExposition, String intitule, int periodeOeuvreDeb, int periodeOeuvreFin, int nbOeuvre, List<String> motsCles, String resume, Date dateDebutExpo, Date dateFinExpo) {
    	this.idExposition = idExposition;
    	this.intituleExposition = intitule;
    	this.periodeOeuvreDeb = periodeOeuvreDeb;
    	this.periodeOeuvreFin = periodeOeuvreFin;
    	this.nombreOeuvre = nbOeuvre;
    	//this.motCle = motsCles;
    	this.resume = resume;
    	this.dateDebutExpo = dateDebutExpo;
    	this.dateFinExpo = dateFinExpo;
    }

}
