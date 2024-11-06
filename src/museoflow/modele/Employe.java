package museoflow.modele;

/**
 * Classe représentant un employé
 * 
 * @author Landry LOUBIERE
 * @author Cylian POUPIN
 * @author Amjed SEHIL
 * @author Aurelien VALAT
 */
public class Employe {
	
	private String idEmploye;

    private String nomEmploye;

    private String prenomEmploye;

    private String telephone;

    /**
     * Constructeur créant un employé
     * 
     * @param idEmploye
     * @param nom
     * @param prenom
     * @param telephone
     * @throws HomonymeException
     */
    public Employe(String idEmploye, String nom, String prenom, String telephone) throws HomonymeException {
    	this.idEmploye=idEmploye;
    	this.nomEmploye=nom;
    	this.prenomEmploye=prenom;
    	this.telephone=telephone;
    }

}
