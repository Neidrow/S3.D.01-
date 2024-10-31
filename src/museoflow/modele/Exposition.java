package museoflow.modele;

import java.util.ArrayList;
import java.util.Date;

public class Exposition {
	
	private String idExposition;

    public String intituleExposition;

    private int periodeOeuvreDeb;

    private int periodeOeuvreFin;

    private int nombreOeuvre;

    private ArrayList<String> motsCles;

    public String resume;

    public Date dateDebutExpo;

    public Date dateFinExpo;

    // Peut-être changer le type de motCles en tableau
    public Exposition(String idExposition, String intitule, int periodeOeuvreDeb, int periodeOeuvreFin, int nbOeuvre, ArrayList<String> motsCles, String resume, Date dateDebutExpo, Date dateFinExpo) {
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

    public String getIdExposition() {
		return idExposition;
	}

	public String getIntituleExposition() {
		return intituleExposition;
	}

	public int getPeriodeOeuvreDeb() {
		return periodeOeuvreDeb;
	}

	public int getPeriodeOeuvreFin() {
		return periodeOeuvreFin;
	}

	public int getNombreOeuvre() {
		return nombreOeuvre;
	}

	public ArrayList<String> getMotsCles() {
		return motsCles;
	}

	public String getResume() {
		return resume;
	}
	
	public Date getDateDebutExpo() {
		return dateDebutExpo;
	}
	
	public Date getDateFinExpo() {
		return dateFinExpo;
	}

}
