package museoflow.controleur;


import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
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

    }

    @FXML
    void handlerButttonConsulter(MouseEvent event) {

    }

    @FXML
    void handlerButttonExporter(MouseEvent event) {
        try {
            // Charger la nouvelle scène de confirmation de sortie
            Parent newRoot = FXMLLoader.load(getClass().getResource(
                    "../vue/choixExporter.fxml"));
            Scene newScene = new Scene(newRoot);

            // Récupérer le stage actuel
            Stage currentStage = (Stage) exporterID.getScene().getWindow();
            currentStage.setScene(newScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handlerButttonImporter(MouseEvent event) {
        try {
            // Charger la nouvelle scène de confirmation de sortie
            Parent newRoot = FXMLLoader.load(getClass().getResource(
                    "../vue/ChoixImporter.fxml"));
            Scene newScene = new Scene(newRoot);

            // Récupérer le stage actuel
            Stage currentStage = (Stage) importerID.getScene().getWindow();
            currentStage.setScene(newScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handlerButttonQuitter(MouseEvent event) {
        try {
            // Charger la nouvelle scène de confirmation de sortie
            Parent newRoot = FXMLLoader.load(getClass().getResource(
                    "../vue/EcranQuitter.fxml"));
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

    /**
     * Ferme le serveur lorsque l'application se ferme
     */
    public void fermerServeur() {
        try {
            GestionFichiers.arreterServeur(); // Arrêtez le serveur
        } catch (IOException e) {
            System.err.println("Erreur lors de l'arrêt du serveur : " 
                    + e.getMessage());
        }
    }
}