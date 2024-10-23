package museoflow.controleur;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ControleurQuitter {

	@FXML
    private ImageView quitterID;
	
	@FXML
    private ImageView annulerID;
	
	@FXML
    void handlerButttonQuitter(MouseEvent event) {
		Stage stage = (Stage) quitterID.getScene().getWindow();
        stage.close();
    }

	@FXML
    void handlerButttonAnnuler(MouseEvent event) {
        try {
            // Charger la scène du menu principal
            Parent newRoot = FXMLLoader.load(getClass().getResource("../vue/MenuPrincipal.fxml"));
            Scene newScene = new Scene(newRoot);

            // Récupérer le stage actuel
            Stage currentStage = (Stage) annulerID.getScene().getWindow();
            currentStage.setScene(newScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}