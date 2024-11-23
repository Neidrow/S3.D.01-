/*
 * VueMenuPrincipal.java 18 oct. 2024 IUT de Rodez Info2 TPD
 * 2024-2025, pas de copyright
 */

package museoflow.vue;

import java.util.Optional;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import museoflow.controleur.ControleurMenuPrincipal;
import museoflow.modele.persistance.GestionSauvegarde;

/**
 * Lanceur MuseoFlow - Chargeur JavaFX
 * 
 * @author LOUBIERE Landry
 * @author POUPIN Cylian
 * @author SEHIL Amjed
 * @author VALAT Aurélien
 */
public class VueMenuPrincipal extends Application {
	private ControleurMenuPrincipal controleurMenuPrincipal;

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

    private static void afficherErreur(String titre, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(titre);
        alert.setContentText(message);
        alert.showAndWait();
    }

	@Override
    public void start(Stage primaryStage) {
        try {
            // TODO on regarde s'il existe une sauvegarde, si oui on
            // l'importe en gérant IOException

            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "../vue/MenuPrincipal.fxml"));
            Parent root = loader.load();

            // On récupère le controleur
            controleurMenuPrincipal = loader.getController();

            Scene scene = new Scene(root, 1250, 700);

            // Logo de l'application
            primaryStage.getIcons().add(new Image(VueMenuPrincipal.class
                    .getResourceAsStream("images/logo.png")));

            scene.getStylesheets()
                    .add(getClass().getResource("../vue/CSS/MenuPrincipal.css")
                            .toExternalForm());

            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();


            // Ajout de l'événement de fermeture pour arrêter le
            // serveur et sauvegarder les données
            primaryStage.setOnCloseRequest(event -> {
                // Arrêt du serveur à la fermeture
                controleurMenuPrincipal.fermerServeur();

                // Sauvegarde des données avant fermeture
                GestionSauvegarde.sauvegarde();
            });

        } catch (Exception e) {
            afficherErreur(
                 "Une erreur est survenue durant le démmarage de l'application",
                 e.getLocalizedMessage());
        }
    }


    /**
     * Lanceur de l'application MuseoFlow
     * 
     * @param args arguments sur ligne de commande non utilisé
     */
	public static void main(String[] args) {
		launch(args);
	}
}
