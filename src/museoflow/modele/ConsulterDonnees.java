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
     * Constructeur permettant d'initialiser les variables
     */
	public ConsulterDonnees() {
		this.listeConferenciers = new ArrayList<>();
        this.listeExpositions = new ArrayList<>();
        this.listeVisitesProg = new ArrayList<>();
    }

    /**
     * Méthode pour importer les expositions
     * @param expositions
     */
    public void importerExpositions(ArrayList<Exposition> expositions) {
        this.listeExpositions = expositions;
    }

	/**
	 * 
	 * @return la liste des conferenciers
	 */
	public ArrayList<Conferencier> consulterListeConferencier() {

		// Code pour récupérer la liste des conferenciers
		return listeConferenciers;	
	}

	/**
     * Retourne la liste des expositions
     * 
     * @return Liste des expositions
     * @throws FichierManquantException
     */
    public ArrayList<Exposition> consulterListeExpositions() throws FichierManquantException {
        if (listeExpositions == null || listeExpositions.isEmpty()) {
            throw new FichierManquantException("Aucune donnée d'exposition n'a été importée.");
        }
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