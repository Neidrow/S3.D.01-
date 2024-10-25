package museoflow.modele;

import java.util.ArrayList;

/**
 * 
 */
public class ConsulterDonnees {
	
	private ArrayList<String> listeConferenciers = new ArrayList<>();
	private ArrayList<String> listeExpositions = new ArrayList<>();
	private ArrayList<String> listeVisitesProg = new ArrayList<>();

	/**
	 * 
	 * @return la liste des conferenciers
	 */
	public ArrayList<String> consulterListeConferencier() {
		// Code pour récupérer la liste des conferenciers	
		return listeConferenciers;	
	}
	
	/**
	 * 
	 * @return la liste des expositions
	 */
	public ArrayList<String> consulterListeExpositions() {
		// Code pour récupérer la liste des expositions
		return listeExpositions;	
	}
	
	/**
	 * 
	 * @return la liste des visites programmées
	 */
	public ArrayList<String> consulterVisitesProg() {
		// Code pour récupérer la liste des visites
		return listeVisitesProg;	
	}
}
