package museoflow.modele;

import java.util.Date;
import java.util.List;

public class Exposition {
	
	private String idExposition;

    public String intituleExposition;

    private int periodeOeuvreDeb;

    private int periodeOeuvreFin;

    private int nombreOeuvre;

    private String motCle;

    public String resume;

    public Date dateDebutExpo;

    public Date dateFinExpo;

    public Exposition(String idExposition, String intitule, int periodeOeuvreDeb, int periodeOeuvreFin, int nbOeuvre, List<String> motsCles, String resume, Date dateDebutExpo, Date dateFinExpo) {
    }

}
