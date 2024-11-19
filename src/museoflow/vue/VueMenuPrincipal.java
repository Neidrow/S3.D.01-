/*
 * VueMenuPrincipal.java 18 oct. 2024 IUT de Rodez Info2 TPD
 * 2024-2025, pas de copyright
 */

package museoflow.vue;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import museoflow.controleur.ControleurMenuPrincipal;

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

	@Override
    public void start(Stage primaryStage) {
        try {
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
            // serveur
            primaryStage.setOnCloseRequest(event -> {
                // Arrêt du serveur à la fermeture
                controleurMenuPrincipal.fermerServeur();
            });

        } catch (Exception e) {
            e.printStackTrace();
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
