/*
 * VueMenuPrincipal.java                           18 oct. 2024
 * IUT de Rodez Info2 TPD 2024-2025, pas de copyright 
 */

package museoflow.vue;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

            controleurMenuPrincipal = loader.getController(); // Obtenez le contrôleur

            Scene scene = new Scene(root, 1250, 700);
            scene.getStylesheets()
                    .add(getClass().getResource("../vue/CSS/MenuPrincipal.css")
                            .toExternalForm());

            primaryStage.setScene(scene);
            primaryStage.show();

            // Test ouverture PDF (pour l'aide de l'app)
            controleurMenuPrincipal.ouvrirFichier(
                    "src/museoflow/vue/documentation/sample.pdf");

            // Ajoutez l'événement de fermeture pour arrêter le serveur
            primaryStage.setOnCloseRequest(event -> {
                controleurMenuPrincipal.fermerServeur(); // Fermer le serveur à la fermeture
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Lanceur principal
     * 
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}
