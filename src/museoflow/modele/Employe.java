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

    /**
     * @return valeur de idEmploye
     */
    public String getIdEmploye() {
        return idEmploye;
    }

    /**
     * @return valeur de nomEmploye
     */
    public String getNomEmploye() {
        return nomEmploye;
    }

    /**
     * @return valeur de prenomEmploye
     */
    public String getPrenomEmploye() {
        return prenomEmploye;
    }

    /**
     * @return valeur de telephone
     */
    public String getTelephone() {
        return telephone;
    }



}
