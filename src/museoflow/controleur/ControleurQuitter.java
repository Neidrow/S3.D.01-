/*
 * ControleurQuitter.java               nov. 2024
 * IUT de Rodez Info2 TPD 2024-2025, pas de copyright
 */
package museoflow.controleur;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import museoflow.modele.GestionReseau;


/**
 * Controleur de la fenêtre d'arret du logiciel
 */
public class ControleurQuitter {

    @FXML
    private Button quitterID;

    @FXML
    private Button annulerID;
  
    @FXML
    void handlerButtonQuitter(MouseEvent event) throws IOException {
        GestionReseau.arreterServeur();
        Stage stage = (Stage) quitterID.getScene().getWindow();
        stage.close();
    }

    @FXML
    void handlerButtonAnnuler(MouseEvent event) {
        try {
            // Charger la scène du menu principal
            Parent newRoot = FXMLLoader.load(getClass().getResource(
                    "../vue/MenuPrincipal.fxml"));
            Scene newScene = new Scene(newRoot);

            // Récupérer le stage actuel
            Stage currentStage = (Stage) annulerID.getScene().getWindow();
            currentStage.setScene(newScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}