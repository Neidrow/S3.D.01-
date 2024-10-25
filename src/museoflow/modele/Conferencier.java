package museoflow.modele;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Conferencier {
	
	public String nomConferencier;

    public String prenomConferencier;

    public String specialite;

    public String telephone;

    public boolean employeParMusee;

    public ArrayList<Date> indisponibilites;

    public Conferencier(String nom, String prenom, String specialite, String telephone, boolean employeParMusee, ArrayList<Date> indisponibilites) throws HomonymeException {
    this.nomConferencier = nom;
    this.prenomConferencier = prenom;
    this.specialite = specialite;
    this.telephone = telephone;
    this.employeParMusee = employeParMusee;
    this.indisponibilites = indisponibilites;
    
    }

}
