package museoflow.controleur;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import museoflow.modele.ConsulterDonnees;

/**
 * Controleur de ConsulterExpositions
 */
public class ControleurConsulterExpositions {

    @FXML
    private ListView<String> listViewExpositions; // Liste des titres des expositions

    @FXML
    private TextArea textAreaDetails; // Affiche les détails de l'exposition sélectionnée

    @FXML
    private ImageView boutonRetour;

    private ConsulterDonnees consulterDonnees = new ConsulterDonnees(); // Votre gestionnaire de données



    /**
     * Gére le bouton Retour
     */
    @FXML
    public void handlerBoutonRetour() {
        // Charge la scène du menu principal
        try {
            // Charger la scène du menu principal
            Parent newRoot = FXMLLoader.load(
                    getClass().getResource("../vue/ConsulterDonnees.fxml"));
            Scene newScene = new Scene(newRoot);

            // Récupérer le stage actuel
            Stage currentStage = (Stage) boutonRetour.getScene().getWindow();
            currentStage.setScene(newScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
