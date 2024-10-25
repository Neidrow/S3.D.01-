package museoflow.modele;

import java.util.Date;
import java.util.List;

public class Conferencier {
	
	public String nomConferencier;

    public String prenomConferencier;

    public String specialite;

    public String telephone;

    public boolean employeParMusee;

    public String indisponibilites;

    public Employe employe;

    public Conferencier(Employe employe, String nom, String prenom, String specialite, String telephone, boolean employeParMusee, List<Date> indisponibilites) throws HomonymeException {
    }

}
