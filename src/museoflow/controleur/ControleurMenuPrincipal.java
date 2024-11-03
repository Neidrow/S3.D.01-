 package museoflow.controleur;


import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import museoflow.modele.FichierManquantException;
import museoflow.modele.GestionFichiers;

/**
 * TODO commenter la responsabilité de cette class (SRP)
 */
public class ControleurMenuPrincipal {

    private static final String TITRE_FICHIER_INTROUVABLE 
    = "Erreur : fichier introuvable.";

    private static final String HEADER_ERREUR_OUVERTURE_FICHIER 
    = "Le fichier demandé ne peut pas s'ouvrir.";

    private static final String BUREAU_NON_SUPPORTE 
    = "L'ouverture de fichiers n'est pas pris en charge sur cette plateforme.";

    private static final String ERREUR_INCONNUE_OUVERTURE_FICHIER 
    = "Impossible d'ouvrir le fichier suite à une erreur inconnue.";


    @FXML
    private ImageView aideID;

    @FXML
    private ImageView consulterID;

    @FXML
    private ImageView exporterID;

    @FXML
    private ImageView importerID;

    @FXML
    private ImageView quitterID;

    @FXML
    private ImageView statID;

    @FXML
    void handlerButttonAide(MouseEvent event) {
    	
        ControleurMenuPrincipal controleurMenuPrincipal =
                new ControleurMenuPrincipal();
        controleurMenuPrincipal.ouvrirFichier(
                "src/museoflow/vue/documentation/NoticeUtilisation.pdf");
    }

    @FXML
    void handlerButttonConsulter(MouseEvent event) {

    	try {
            // Charger la nouvelle scène 
            Parent newRoot = FXMLLoader.load(getClass().getResource("../vue/ConsulterDonnees.fxml"));
            Scene newScene = new Scene(newRoot);

            // Récupérer le stage actuel
            Stage currentStage = (Stage) consulterID.getScene().getWindow();
            currentStage.setScene(newScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handlerButttonExporter(MouseEvent event) {

    }

    @FXML
    void handlerButttonImporter(MouseEvent event) {
    	GestionFichiers gestionFichiers = new GestionFichiers();

    	try {
            // Création d'un FileChooser pour JavaFX
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Sélectionner un fichier à importer");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers CSV", "*.xls")); 
            
            // Affiche le dialogue et récupère le fichier sélectionné
            Window stage = ((ImageView) event.getSource()).getScene().getWindow();
            File fichier = fileChooser.showOpenDialog(stage);

            if (fichier != null) { // Vérifie qu'un fichier a bien été sélectionné
                // Appelle la méthode pour importer le fichier via le réseau
                gestionFichiers.importerFichierReseau(fichier);

                // Affiche un message de succès
                Alert alertSuccess = new Alert(Alert.AlertType.INFORMATION);
                alertSuccess.setTitle("Succès");
                alertSuccess.setHeaderText(null);
                alertSuccess.setContentText("Fichier importé avec succès !");
                alertSuccess.showAndWait();
            } else {
                // Si aucun fichier n'est sélectionné, message d'avertissement
                Alert alertNoFile = new Alert(Alert.AlertType.WARNING);
                alertNoFile.setTitle("Aucun fichier sélectionné");
                alertNoFile.setHeaderText(null);
                alertNoFile.setContentText("Veuillez sélectionner un fichier à importer.");
                alertNoFile.showAndWait();
            }

        } catch (IOException e) {
            // Affiche une alerte en cas de problème de connexion
            Alert alertConnection = new Alert(Alert.AlertType.ERROR);
            alertConnection.setTitle("Erreur de connexion");
            alertConnection.setHeaderText("Problème de connexion réseau");
            alertConnection.setContentText("Impossible de se connecter au serveur distant.");
            alertConnection.showAndWait();
        }
    }

    @FXML
    void handlerButttonQuitter(MouseEvent event) {
        try {
            // Charger la nouvelle scène de confirmation de sortie
            Parent newRoot = FXMLLoader.load(getClass().getResource("../vue/EcranQuitter.fxml"));
            Scene newScene = new Scene(newRoot);

            // Récupérer le stage actuel
            Stage currentStage = (Stage) quitterID.getScene().getWindow();
            currentStage.setScene(newScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void handlerButttonRapport(MouseEvent event) {

    }

    @FXML
    void handlerButttonStat(MouseEvent event) {

    }

    /**
     * Ouvre le fichier spécifié en argument avec l'application par
     * défaut du système d'exploitation.
     * 
     * @param cheminFichier chemin du fichier à ouvrir
     */
    @FXML
    public void ouvrirFichier(String cheminFichier) {
        File fichierAOuvrir = new File(cheminFichier);
        if (!fichierAOuvrir.exists()) {
            Alert boiteFichierIntrouvable = new Alert(Alert.AlertType.ERROR,
                    TITRE_FICHIER_INTROUVABLE, ButtonType.OK);

            boiteFichierIntrouvable.setTitle(TITRE_FICHIER_INTROUVABLE);
            boiteFichierIntrouvable
                    .setHeaderText(HEADER_ERREUR_OUVERTURE_FICHIER);

            Optional<ButtonType> fichierIntrouvable = boiteFichierIntrouvable
                    .showAndWait();

        } else {
            if (!Desktop.isDesktopSupported()) {
                Alert boiteBureauNonSupporte = new Alert(Alert.AlertType.ERROR,
                        HEADER_ERREUR_OUVERTURE_FICHIER, ButtonType.OK);

                boiteBureauNonSupporte
                        .setTitle(HEADER_ERREUR_OUVERTURE_FICHIER);
                boiteBureauNonSupporte.setHeaderText(BUREAU_NON_SUPPORTE);

                Optional<ButtonType> bureauNonSupporte = boiteBureauNonSupporte
                        .showAndWait();

            } else {
                Desktop desktop = Desktop.getDesktop();
                try {
                    desktop.open(fichierAOuvrir);
                } catch (IOException e) {
                    Alert boiteErreurInconnueOuverture = new Alert(
                            Alert.AlertType.ERROR,
                            HEADER_ERREUR_OUVERTURE_FICHIER, ButtonType.OK);

                    boiteErreurInconnueOuverture
                            .setTitle(HEADER_ERREUR_OUVERTURE_FICHIER);
                    boiteErreurInconnueOuverture
                            .setHeaderText(ERREUR_INCONNUE_OUVERTURE_FICHIER);

                    Optional<ButtonType> erreurInconnue = 
                            boiteErreurInconnueOuverture.showAndWait();
                }
            }
        }
    }
}