/*
 * Employe.java                           6 nov. 2024
 * IUT de Rodez Info2 TPD 2024-2025, pas de copyright 
 */
package museoflow.modele;


/**
 * Classe objet représentant un employé.
 * 
 * @author Amjed SEHIL
 * @author Cylian POUPIN
 */
public class Employe {

    private String idEmploye;

    private String nomEmploye;

    private String prenomEmploye;

    private String telephone;

    /**
     * Constructeur créant un employé vide (touts les attrubuts sont
     * initialisés à null).
     */
    public Employe() {
        this.idEmploye = null;
        this.nomEmploye = null;
        this.prenomEmploye = null;
        this.telephone = null;
    }

    /**
     * <p>
     * Méthode faisant office de constructeur affectant à un employé
     * les valeurs passées en paramètres à un objet Employé vide déja
     * créé.
     * </p>
     * Cette méthode n'est volontairement pas un constructeur pour des
     * raisons techniques propres au fonctionnenent de la création des
     * objets dans GestionFichiers.
     * 
     * @param idEmploye     ID de l'employé
     * @param nomEmploye    nom de l'employé
     * @param prenomEmploye prénom de l'employé
     * @param telephone     No de tél. de l'employé
     * @return true si la construction a été effectuée, false sinon
     */
    public boolean construireEmploye(String idEmploye, String nomEmploye,
            String prenomEmploye, String telephone) {
        // On vérifie si touts les attributs sont null pour interdire
        // la modification d'une exposition déja crée (pas d'effet de
        // bords)
        if (this.idEmploye == null
                && this.nomEmploye == null
                && this.prenomEmploye == null
                && this.telephone == null) {

            this.idEmploye = idEmploye;
            this.nomEmploye = nomEmploye;
            this.prenomEmploye = prenomEmploye;
            this.telephone = telephone;

            return true;

        } else {
            System.out.println("Employé déja créé avec attributs ! \n"
                    + "L'objet n'a pas été modifié.");
            return false;
        }
    }

    /**
     * Retourne l'ID de l'employé.
     * 
     * @return ID de l'employé
     */
    public String getIdEmploye() {
        return idEmploye;
    }

    /**
     * Retourne le nom de l'employé.
     * 
     * @return nom de l'employé
     */
    public String getNomEmploye() {
        return nomEmploye;
    }

    /**
     * Retourne le prénom de l'employé.
     * 
     * @return prénom de l'employé
     */
    public String getPrenomEmploye() {
        return prenomEmploye;
    }

    /**
     * Retourne le numéro de téléphone de l'employé.
     * 
     * @return No de tél. de l'employé
     */
    public String getTelephone() {
        return telephone;
    }
}