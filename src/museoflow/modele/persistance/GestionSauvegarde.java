/*
 * GestionSauvegarde.java 23 nov. 2024 IUT de Rodez Info2 TPD
 * 2024-2025, pas de copyright
 */
package museoflow.modele.persistance;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import museoflow.modele.Conferencier;
import museoflow.modele.Employe;
import museoflow.modele.Exposition;
import museoflow.modele.Visite;

/**
 * Gère la sauvegarde et le chargement des données de l'application
 * MuseoFlow.
 * <p>
 * Cette classe permet également de vérifier la présence d'une
 * sauvegarde et de supprimer une sauvegarde dans le cas où une serait
 * présente.
 * 
 * @author Cylian Poupin
 */
public class GestionSauvegarde {

    private static final String ERREUR_FICHIER_INTROUVABLE =
            "Erreur : le fichier de sauvegarde \"%s\" est introuvable";

    private static final String ERREUR_FICHIER_LECTURE =
            "Erreur : le fichier de sauvegarde \"%s\" ne peut pas être lu";

    private static final String NOM_CONFERENCIERS_SAUVEGARDE =
            "\\conferenciers.sav";

    private static final String NOM_EMPLOYES_SAUVEGARDE = "\\employes.sav";

    private static final String NOM_EXPOSITIONS_SAUVEGARDE =
            "\\expositions.sav";

    private static final String NOM_VISITES_SAUVEGARDE = "\\visites.sav";

    /**
     * Demande à l'utilisateur de confirmer son action.
     * 
     * @param titre   Titre du message
     * @param message Message détaillé de l'action à confirmer
     * @return true si l'utilisateur clique sur OK, false s'il clique
     *         sur "annuler" ou s'il ferme la fenêtre.
     */
    private static boolean demandeConfirmation(String titre, String message) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(titre);
        alert.setHeaderText("Confirmez votre action");
        alert.setContentText(message);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            // On clique sur OK
            return true;
        } else {
            // On annule ou on ferme la fenêtre
            return false;
        }
    }

    /**
     * Sauvegarde les données de l'application sur demande de l'utilisateur.
     */
    public static void sauvegarde() {
        if (demandeConfirmation(
                "Voulez-vous sauvegarder les données importées ?",
                "Voulez-vous sauvegarder les données importées"
                + " pour qu'elles soient directement disponibles"
                + " à la prochaine ouverture de cette application ?")) {

            // TODO on demande le dossier de destination et on
            // appelle la méthode de sauvegarde et on affiche
            // un message pour IOException
        }
        
    }
    
    
    /**
     * Charge la liste des conférenciers en les restaurant depuis un
     * fichier de sauvegarde.
     * 
     * @param cheminSourceSauvegarde le chemin vers du fichier de
     *                               sauvegarde contenant les
     *                               informations de la partie.
     * @return la liste des conférenciers précédemment importés
     * @throws FileNotFoundException  si le fichier de sauvegarde
     *                                n'existe pas ou ne peut être
     *                                trouvé au chemin spécifié.
     * @throws IOException            si une erreur survient lors de
     *                                la lecture du fichier de
     *                                sauvegarde.
     * @throws ClassNotFoundException si une classe spécifiée dans le
     *                                fichier est introuvable.
     */
    public static List<Conferencier> chargerConferenciers(
            Path cheminSourceSauvegarde)
            throws IOException, ClassNotFoundException {

        if (!Files.exists(cheminSourceSauvegarde)) {
            throw new FileNotFoundException(
                    String.format(ERREUR_FICHIER_INTROUVABLE,
                            cheminSourceSauvegarde));
        }

        List<Conferencier> conferenciersRestaures;
        try {
            ObjectInputStream persistanceObjet = new ObjectInputStream(
                    new FileInputStream(cheminSourceSauvegarde.toString()));
            conferenciersRestaures =
                    (List<Conferencier>) persistanceObjet.readObject();
            persistanceObjet.close();

        } catch (IOException e) {
            throw new IOException(String.format(ERREUR_FICHIER_LECTURE,
                    cheminSourceSauvegarde.toString()));
        }

        return conferenciersRestaures;
    }

    /**
     * Charge la liste des employés en les restaurant depuis un
     * fichier de sauvegarde.
     * 
     * @param cheminSourceSauvegarde le chemin vers du fichier de
     *                               sauvegarde contenant les
     *                               informations de la partie.
     * @return la liste des conférenciers précédemment importés
     * @throws FileNotFoundException  si le fichier de sauvegarde
     *                                n'existe pas ou ne peut être
     *                                trouvé au chemin spécifié.
     * @throws IOException            si une erreur survient lors de
     *                                la lecture du fichier de
     *                                sauvegarde.
     * @throws ClassNotFoundException si une classe spécifiée dans le
     *                                fichier est introuvable.
     */
    public static List<Employe> chargerEmployes(
            Path cheminSourceSauvegarde)
            throws IOException, ClassNotFoundException {

        if (!Files.exists(cheminSourceSauvegarde)) {
            throw new FileNotFoundException(
                    String.format(ERREUR_FICHIER_INTROUVABLE,
                            cheminSourceSauvegarde));
        }

        List<Employe> employesRestaures;
        try {
            ObjectInputStream persistanceObjet = new ObjectInputStream(
                    new FileInputStream(cheminSourceSauvegarde.toString()));
            employesRestaures =
                    (List<Employe>) persistanceObjet.readObject();
            persistanceObjet.close();

        } catch (IOException e) {
            throw new IOException(String.format(ERREUR_FICHIER_LECTURE,
                    cheminSourceSauvegarde.toString()));
        }

        return employesRestaures;
    }

    /**
     * Charge la liste des expositions en les restaurant depuis un
     * fichier de sauvegarde.
     * 
     * @param cheminSourceSauvegarde le chemin vers du fichier de
     *                               sauvegarde contenant les
     *                               informations de la partie.
     * @return la liste des conférenciers précédemment importés
     * @throws FileNotFoundException  si le fichier de sauvegarde
     *                                n'existe pas ou ne peut être
     *                                trouvé au chemin spécifié.
     * @throws IOException            si une erreur survient lors de
     *                                la lecture du fichier de
     *                                sauvegarde.
     * @throws ClassNotFoundException si une classe spécifiée dans le
     *                                fichier est introuvable.
     */
    public static List<Exposition> chargerExpositions(
            Path cheminSourceSauvegarde)
            throws IOException, ClassNotFoundException {

        if (!Files.exists(cheminSourceSauvegarde)) {
            throw new FileNotFoundException(
                    String.format(ERREUR_FICHIER_INTROUVABLE,
                            cheminSourceSauvegarde));
        }

        List<Exposition> expositionsRestaurees;
        try {
            ObjectInputStream persistanceObjet = new ObjectInputStream(
                    new FileInputStream(cheminSourceSauvegarde.toString()));
            expositionsRestaurees =
                    (List<Exposition>) persistanceObjet.readObject();
            persistanceObjet.close();

        } catch (IOException e) {
            throw new IOException(String.format(ERREUR_FICHIER_LECTURE,
                    cheminSourceSauvegarde.toString()));
        }

        return expositionsRestaurees;
    }

    /**
     * Charge la liste des visites en les restaurant depuis un fichier
     * de sauvegarde.
     * 
     * @param cheminSourceSauvegarde le chemin vers du fichier de
     *                               sauvegarde contenant les
     *                               informations de la partie.
     * @return la liste des conférenciers précédemment importés
     * @throws FileNotFoundException  si le fichier de sauvegarde
     *                                n'existe pas ou ne peut être
     *                                trouvé au chemin spécifié.
     * @throws IOException            si une erreur survient lors de
     *                                la lecture du fichier de
     *                                sauvegarde.
     * @throws ClassNotFoundException si une classe spécifiée dans le
     *                                fichier est introuvable.
     */
    public static List<Visite> chargerVisites(
            Path cheminSourceSauvegarde)
            throws IOException, ClassNotFoundException {

        if (!Files.exists(cheminSourceSauvegarde)) {
            throw new FileNotFoundException(
                    String.format(ERREUR_FICHIER_INTROUVABLE,
                            cheminSourceSauvegarde));
        }

        List<Visite> visitesRestaurees;
        try {
            ObjectInputStream persistanceObjet = new ObjectInputStream(
                    new FileInputStream(cheminSourceSauvegarde.toString()));
            visitesRestaurees =
                    (List<Visite>) persistanceObjet.readObject();
            persistanceObjet.close();

        } catch (IOException e) {
            throw new IOException(String.format(ERREUR_FICHIER_LECTURE,
                    cheminSourceSauvegarde.toString()));
        }

        return visitesRestaurees;
    }

    /**
     * Détermine si le chemin renseigné pointe vers un fichier de
     * sauvegarde existe.
     * 
     * @param cheminSourceSauvegarde le chemin du fichier dont on
     *                               souhaite récuperer la sauvegarde.
     * @return true si la sauvegarde existe, sinon false
     */
    public static boolean isSauvegardeExistante(Path cheminSourceSauvegarde) {
        String dossierSauvegarde = cheminSourceSauvegarde.toString();

        return Files.exists(
                Path.of(dossierSauvegarde, NOM_CONFERENCIERS_SAUVEGARDE))
                && Files.exists(
                        Path.of(dossierSauvegarde, NOM_EMPLOYES_SAUVEGARDE))
                && Files.exists(
                        Path.of(dossierSauvegarde, NOM_EXPOSITIONS_SAUVEGARDE))
                && Files.exists(
                        Path.of(dossierSauvegarde, NOM_VISITES_SAUVEGARDE));
    }

    /**
     * <p>
     * Sauvegarde les données importées dans le dossier sélectionné.
     * <p>
     * Si les fichier existe déjà dans le dossier source, alors il
     * sera écrasé par le nouveau.
     * 
     * @param conferenciers        liste des donférenciers
     * @param employes             liste des employés
     * @param expositions          liste des expositions
     * @param visites              liste des visites
     * @param cheminDestSauvegarde le chemin du fichier dans lequel on
     *                             souhaite sauvegarder la partie.
     * @throws IOException si une erreur survient lors de la lecture
     *                     du fichier de sauvegarde.
     */
    public static void sauvegarderDonnees(List<Conferencier> conferenciers,
            List<Employe> employes, List<Exposition> expositions,
            List<Visite> visites,
            Path cheminDestSauvegarde)
            throws IOException {

        try {
            ObjectOutputStream persistanceObjet = new ObjectOutputStream(
                    new FileOutputStream(cheminDestSauvegarde.toString()
                            + NOM_CONFERENCIERS_SAUVEGARDE));
            persistanceObjet.writeObject(conferenciers);
            persistanceObjet.flush();
            persistanceObjet.close();

        } catch (IOException e) {
            throw new IOException(String.format(ERREUR_FICHIER_LECTURE,
                    cheminDestSauvegarde.toString()
                            + NOM_CONFERENCIERS_SAUVEGARDE));
        }

        try {
            ObjectOutputStream persistanceObjet = new ObjectOutputStream(
                    new FileOutputStream(cheminDestSauvegarde.toString()
                            + NOM_EMPLOYES_SAUVEGARDE));
            persistanceObjet.writeObject(employes);
            persistanceObjet.flush();
            persistanceObjet.close();

        } catch (IOException e) {
            throw new IOException(String.format(ERREUR_FICHIER_LECTURE,
                    cheminDestSauvegarde.toString()
                            + NOM_EMPLOYES_SAUVEGARDE));
        }

        try {
            ObjectOutputStream persistanceObjet = new ObjectOutputStream(
                    new FileOutputStream(cheminDestSauvegarde.toString()
                            + NOM_EXPOSITIONS_SAUVEGARDE));
            persistanceObjet.writeObject(expositions);
            persistanceObjet.flush();
            persistanceObjet.close();

        } catch (IOException e) {
            throw new IOException(String.format(ERREUR_FICHIER_LECTURE,
                    cheminDestSauvegarde.toString()
                            + NOM_EXPOSITIONS_SAUVEGARDE));
        }

        try {
            ObjectOutputStream persistanceObjet = new ObjectOutputStream(
                    new FileOutputStream(cheminDestSauvegarde.toString()
                            + NOM_VISITES_SAUVEGARDE));
            persistanceObjet.writeObject(visites);
            persistanceObjet.flush();
            persistanceObjet.close();

        } catch (IOException e) {
            throw new IOException(String.format(ERREUR_FICHIER_LECTURE,
                    cheminDestSauvegarde.toString()
                            + NOM_VISITES_SAUVEGARDE));
        }
    }

    /**
     * Supprime la sauvegarde des données précédemment importées.
     * 
     * @param cheminSourceSauvegarde le chemin du fichier dans lequel
     *                               on souhaite supprimer la
     *                               sauvegarder.
     * @throws IOException le fichier n'a pas pu être effacé
     */
    public static void supprimerDonnees(Path cheminSourceSauvegarde)
            throws IOException {

        try {
            Files.delete(Path.of(cheminSourceSauvegarde.toString(),
                    NOM_CONFERENCIERS_SAUVEGARDE));
        } catch (IOException e) {
            throw new IOException(String.format(ERREUR_FICHIER_LECTURE,
                    cheminSourceSauvegarde.toString()
                            + NOM_CONFERENCIERS_SAUVEGARDE));
        }

        try {
            Files.delete(Path.of(cheminSourceSauvegarde.toString(),
                    NOM_EMPLOYES_SAUVEGARDE));
        } catch (IOException e) {
            throw new IOException(String.format(ERREUR_FICHIER_LECTURE,
                    cheminSourceSauvegarde.toString()
                            + NOM_EMPLOYES_SAUVEGARDE));
        }

        try {
            Files.delete(Path.of(cheminSourceSauvegarde.toString(),
                    NOM_EXPOSITIONS_SAUVEGARDE));
        } catch (IOException e) {
            throw new IOException(String.format(ERREUR_FICHIER_LECTURE,
                    cheminSourceSauvegarde.toString()
                            + NOM_EXPOSITIONS_SAUVEGARDE));
        }

        try {
            Files.delete(Path.of(cheminSourceSauvegarde.toString(),
                    NOM_VISITES_SAUVEGARDE));
        } catch (IOException e) {
            throw new IOException(String.format(ERREUR_FICHIER_LECTURE,
                    cheminSourceSauvegarde.toString()
                            + NOM_VISITES_SAUVEGARDE));
        }
    }
}