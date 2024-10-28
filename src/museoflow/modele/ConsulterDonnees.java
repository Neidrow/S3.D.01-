package museoflow.modele;
import java.util.ArrayList;

/**

 * @author Aurelien Valat

 */

public class ConsulterDonnees {

	private ArrayList<Conferencier> listeConferenciers = new ArrayList<>();
	private ArrayList<Exposition> listeExpositions = new ArrayList<>();
	private ArrayList<Visite> listeVisitesProg = new ArrayList<>();

	/**
	 * 
	 * @return la liste des conferenciers
	 */
	public ArrayList<Conferencier> consulterListeConferencier() {

		// Code pour récupérer la liste des conferenciers
		return listeConferenciers;	
	}

	/**
	 * 
	 * @return la liste des expositions
	 */
	public ArrayList<Exposition> consulterListeExpositions() {
		
		// Code pour récupérer la liste des expositions
		return listeExpositions;	
	}


	/**
	 * 
	 * @return la liste des visites programmées
	 */
	public ArrayList<Visite> consulterVisitesProg() {

		// Code pour récupérer la liste des visites
		return listeVisitesProg;	
	}
}