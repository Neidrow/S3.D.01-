/*
 * ControleurConsulterDonnees.java              nov. 2024 
 * IUT de Rodez Info2 TPD 2024-2025, pas de copyright
 */
package museoflow.controleur;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * Controleur de la consultation des données
 * 
 * @authors Aurélien Valat, Landry Loubière
 */
public class ControleurConsulterDonnees {

    @FXML
    private Button boutonVisites;

    @FXML
    private Button boutonExpositions;

    @FXML
    private Button boutonConferenciers;

    @FXML
    private Button boutonEmployes;

    @FXML
    private Button boutonRetour; 

    @FXML
    void handlerBoutonVisites() {
        try {
            // Charger la scène de choix des différentes consultations

            Parent newRoot = FXMLLoader.load(
                    getClass().getResource("../vue/ConsulterVisites.fxml"));
            Scene newScene = new Scene(newRoot);

            // Récupérer le stage actuel
            Stage currentStage =
                    (Stage) boutonVisites.getScene().getWindow();
            currentStage.setScene(newScene);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void handlerBoutonExpositions() {
        try {
            // Charger la nouvelle scène
            Parent newRoot = FXMLLoader.load(
                    getClass().getResource("../vue/ConsulterExpositions.fxml"));
            Scene newScene = new Scene(newRoot);

            // Récupérer le stage actuel
            Stage currentStage =
                    (Stage) boutonExpositions.getScene().getWindow();
            currentStage.setScene(newScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handlerBoutonConferenciers() {
        try {
            // Charger la scène de choix des différentes consultations

            Parent newRoot = FXMLLoader.load(
                    getClass()
                            .getResource("../vue/ConsulterConferenciers.fxml"));
            Scene newScene = new Scene(newRoot);

            // Récupérer le stage actuel
            Stage currentStage =
                    (Stage) boutonConferenciers.getScene().getWindow();
            currentStage.setScene(newScene);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void handlerBoutonEmployes() {
        try {
            // Charger la nouvelle scène
            Parent newRoot = FXMLLoader.load(
                    getClass().getResource("../vue/ConsulterEmployes.fxml"));
            Scene newScene = new Scene(newRoot);

            // Récupérer le stage actuel
            Stage currentStage =
                    (Stage) boutonEmployes.getScene().getWindow();
            currentStage.setScene(newScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handlerBoutonRetour() {
        try {
            // Charger la scène du menu principal
            Parent newRoot = FXMLLoader
                    .load(getClass().getResource("../vue/MenuPrincipal.fxml"));
            Scene newScene = new Scene(newRoot);

            // Récupérer le stage actuel
            Stage currentStage = (Stage) boutonRetour.getScene().getWindow();
            currentStage.setScene(newScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
