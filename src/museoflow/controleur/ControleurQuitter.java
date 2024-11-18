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
 * TODO commenter la responsabilité de cette classe (SRP)
 */
public class ControleurQuitter {

    @FXML
    private Button quitterID;

    @FXML
    private Button annulerID;
  
    void handlerButtonQuitter(MouseEvent event) throws IOException {
        GestionReseau.arreterServeur();
        Stage stage = (Stage) quitterID.getScene().getWindow();
        stage.close();
    }

    @FXML
    void handlerButttonAnnuler(MouseEvent event) {
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