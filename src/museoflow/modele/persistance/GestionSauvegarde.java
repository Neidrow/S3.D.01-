/*
 * GestionSauvegarde.java 23 nov. 2024 IUT de Rodez Info2 TPD
 * 2024-2025, pas de copyright
 */
package museoflow.modele.persistance;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
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
import javafx.stage.DirectoryChooser;
import museoflow.modele.Conferencier;
import museoflow.modele.Employe;
import museoflow.modele.Exposition;
import museoflow.modele.GestionFichiers;
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
     * @throws IOException Si une erreur surviens durant la sauveragde
     */
    public static void sauvegarde() throws IOException {
        if (demandeConfirmation(
                "Voulez-vous sauvegarder les données importées ?",
                "Voulez-vous sauvegarder les données importées"
                        + " pour qu'elles soient directement disponibles"
                        + " à la prochaine ouverture de cette application ?")) {

            DirectoryChooser choisirDossier;
            File dossierSelectionne;

            Optional<ButtonType> choixSauvegarde;

            Alert fichierExistant = new Alert(Alert.AlertType.WARNING,
                    "Voulez-vous écraser "
                            + "le fichier de sauvegarde ?",
                    ButtonType.YES, ButtonType.NO);

            Alert sauvegardeAnnulee = new Alert(Alert.AlertType.INFORMATION,
                    "La sauvegarde a été annulée.");

            fichierExistant.setTitle("Sauvegarde fichier");

            choisirDossier = new DirectoryChooser();
            
            // On positionne la sélection de dossier directement dans
            // le dossier choisi précédemment s'il existe
            if (Files.exists(Path.of("dernierEmplacementSauvegarde.txt"))) {
                File dernierEmplacement = new File(getDernierEmplacement());
                choisirDossier.setInitialDirectory(dernierEmplacement);
            }

            choisirDossier.setTitle("Selectionnez un dossier");

            try {
                dossierSelectionne = choisirDossier.showDialog(null);
            
            // Si le fichier de dernier emplacement est corrompu
            } catch (IllegalArgumentException e) {
                choisirDossier.setInitialDirectory(null);
                dossierSelectionne = choisirDossier.showDialog(null);
            }

            if (dossierSelectionne == null) {
                // L'utilisateur a annulé la sélection du dossier
                sauvegardeAnnulee.showAndWait();

            } else if (isSauvegardeExistante(
                    dossierSelectionne.toPath())) {
                choixSauvegarde = fichierExistant.showAndWait();

                if (choixSauvegarde.get() == ButtonType.YES) {
                    supprimerDonnees(dossierSelectionne.toPath());
                    sauvegarderDonnees(GestionFichiers.getConferenciers(),
                            GestionFichiers.getEmployes(),
                            GestionFichiers.getExpositions(),
                            GestionFichiers.getVisites(),
                            dossierSelectionne.toPath());
                    sauvegardeEmplacement(dossierSelectionne.toString());
                }

            } else {
                sauvegarderDonnees(GestionFichiers.getConferenciers(),
                        GestionFichiers.getEmployes(),
                        GestionFichiers.getExpositions(),
                        GestionFichiers.getVisites(),
                        dossierSelectionne.toPath());
                sauvegardeEmplacement(dossierSelectionne.toString());
            }

        // L'utilisateur ne souhaite pas sauvegarder
        } else {
        try {
            Path.of(getDernierEmplacement());
        } catch (IOException e) {
            /*
             * Le fichier de dernier emplacement n'est pas trouvé, on
             * n'affiche pas le message consernant la dernière
             * sauvegarde ; pour ce faire on doit quitter la méthode.
             */
            // break
            return;
        }
        
        if (isSauvegardeExistante(Path.of(getDernierEmplacement()))) {
            if (demandeConfirmation("Données précédemment sauvegardées",
                    "Voulez vous supprimer les données précédemment "
                            + "sauvegardées ? \nSi vous annulez, la sauvegarde "
                            + "précédente sera restaurée au prochain démmarage "
                            + "de l'application.")) {
                supprimerDonnees(Path.of(getDernierEmplacement()));
            }
        }
    }
}


    /**
     * Sauvegarde de l'emplacement pour charger les données
     * automatiquement à la réouverture de l'app
     * 
     * @param chemin Dossier de sauvegarde
     * @throws IOException Si l'opération échoue
     */
    private static void sauvegardeEmplacement(String chemin)
            throws IOException {
        BufferedWriter writer =
                new BufferedWriter(
                        new FileWriter("dernierEmplacementSauvegarde.txt"));
        writer.write(chemin);
        writer.close();
    }

    /**
     * Charge la liste des conférenciers en les restaurant depuis un
     * fichier de sauvegarde.
     * 
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
    public static List<Conferencier> chargerConferenciers()
            throws IOException, ClassNotFoundException {

        Path cheminSourceSauvegarde =
                Path.of(getDernierEmplacement() + NOM_CONFERENCIERS_SAUVEGARDE);

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
    public static List<Employe> chargerEmployes()
            throws IOException, ClassNotFoundException {

        Path cheminSourceSauvegarde =
                Path.of(getDernierEmplacement() + NOM_EMPLOYES_SAUVEGARDE);

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
    public static List<Exposition> chargerExpositions()
            throws IOException, ClassNotFoundException {

        Path cheminSourceSauvegarde =
                Path.of(getDernierEmplacement() + NOM_EXPOSITIONS_SAUVEGARDE);

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
    public static List<Visite> chargerVisites()
            throws IOException, ClassNotFoundException {


        Path cheminSourceSauvegarde =
                Path.of(getDernierEmplacement() + NOM_VISITES_SAUVEGARDE);

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

    /**
     * Importation des données précédemment sauvegardées, si elles
     * existent
     * 
     * @throws IOException            Si la restauration échoue
     * @throws ClassNotFoundException Si la restauration échoue
     */
    public static void importerDonnees()
            throws ClassNotFoundException, IOException {
        if (Files.exists(Path.of("dernierEmplacementSauvegarde.txt"))
                && isSauvegardeExistante(Path.of(getDernierEmplacement()))) {
            GestionFichiers.setConferenciers(chargerConferenciers());
            GestionFichiers.setEmployes(chargerEmployes());
            GestionFichiers.setExpositions(chargerExpositions());
            GestionFichiers.setVisites(chargerVisites());
        }
    }

    private static String getDernierEmplacement() throws IOException {
        // Try avec ressources pour fermer automatiquement le reader
        try (BufferedReader reader = new BufferedReader(
                new FileReader("dernierEmplacementSauvegarde.txt"))) {
            return reader.readLine();
        }
    }

    /**
     * Gestion de l'action à effectuer si la sauvegarde précédente est
     * corrompue
     * 
     * @throws IOException Si la suppression de la sauvegade corrompue
     *                     n'a pas réussi
     */
    public static void sauvegardeEnErreur() throws IOException {
        if (demandeConfirmation("Supprimer la sauvegarde précédente ?",
                "Le chargement de la sauvegarde précédente n'a pas "
                        + "réussi. La sauvegarde est probablement corrompue. "
                        + "Voulez-vous la supprimer ?")) {
            GestionSauvegarde
                    .supprimerDonnees(Path.of(getDernierEmplacement()));
        }
    }
}