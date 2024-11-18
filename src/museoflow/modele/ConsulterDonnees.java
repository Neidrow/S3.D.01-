/*
 * ConsulterDonnees.java                           nov. 2024
 * IUT de Rodez Info2 TPD 2024-2025, pas de copyright 
 */
package museoflow.modele;

import java.util.ArrayList;

import museoflow.modele.exceptions.FichierManquantException;

/**
 * Traitements nessésaires à la consultation des données.
 * 
 * @author Aurelien Valat Landry Loubière
 */
public class ConsulterDonnees {

	private ArrayList<Conferencier> listeConferenciers = new ArrayList<>();

	private ArrayList<Exposition> listeExpositions = new ArrayList<>();

	private ArrayList<Visite> listeVisitesProg = new ArrayList<>();

    private ArrayList<Employe> listeEmployes = new ArrayList<>();

	
    /**
     * Constructeur permettant d'initialiser les variables
     */
	public ConsulterDonnees() {
		this.listeConferenciers = new ArrayList<>();
        this.listeExpositions = new ArrayList<>();
        this.listeVisitesProg = new ArrayList<>();
        this.listeEmployes = new ArrayList<>();
    }

    /**
     * Méthode pour importer les expositions
     * @param expositions
     */
    public void importerExpositions(ArrayList<Exposition> expositions) {
        this.listeExpositions = expositions;
    }

	/**
     * Récupération de la liste des conférenciers
     * 
     * @return la liste des conferenciers
     */
	public ArrayList<Conferencier> consulterListeConferencier() {

		// Code pour récupérer la liste des conferenciers
		return listeConferenciers;	
	}

    /**
     * Récupération de la liste des employés
     * 
     * @return la liste des Employes
     * @throws FichierManquantException
     */
    public ArrayList<Employe> consulterListeEmployes()
            throws FichierManquantException {
        if (listeExpositions == null || listeExpositions.isEmpty()) {
            throw new FichierManquantException(
                    "Aucune donnée d'employé n'a été importée.");
        }
        return listeEmployes;
    }

	/**
     * Retourne la liste des expositions
     * 
     * @return Liste des expositions
     * @throws FichierManquantException
     */
    public ArrayList<Exposition> consulterListeExpositions()
            throws FichierManquantException {
        if (listeExpositions == null || listeExpositions.isEmpty()) {
            throw new FichierManquantException(
                    "Aucune donnée d'exposition n'a été importée.");
        }
        return listeExpositions;
    }


	/**
     * Récupération de la liste des visites programmées
     * 
     * @return la liste des visites programmées
     */
	public ArrayList<Visite> consulterVisitesProg() {

		// Code pour récupérer la liste des visites
		return listeVisitesProg;	
	}
}