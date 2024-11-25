/*
 * VueMenuPrincipal.java 18 oct. 2024 IUT de Rodez Info2 TPD
 * 2024-2025, pas de copyright
 */

package museoflow.vue;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import museoflow.controleur.ControleurMenuPrincipal;
import museoflow.modele.GestionFichiers;
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

    private static void afficherErreur(String titre, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(titre);
        alert.setContentText(message);
        alert.showAndWait();
    }

	@Override
    public void start(Stage primaryStage) {
        try {
            // Importation des données précédemment sauvegardées si
            // elles existent
            try {
                GestionSauvegarde.importerDonnees();
                // Catch Exception car de multiples exceptions peuvent
                // survenir à la lecture du fichier de dernière
                // sauvegarde (ex. le fichier contient des données
                // binaires)
            } catch (Exception e) {
                afficherErreur("Erreur du chargement des données importées",
                        "Une erreur est survenue durant le chargement des "
                        + "données sauvegardées précédemment importées.");
                try {
                    GestionSauvegarde.sauvegardeEnErreur();
                } catch (Exception e1) {
                    afficherErreur("La suppression de la sauvegarde n'a pas "
                            + "abouti.", e1.getMessage());
                }
            }

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
            primaryStage.setTitle("MuseoFlow");
            primaryStage.show();


            // Ajout de l'événement de fermeture pour arrêter le
            // serveur et sauvegarder les données
            primaryStage.setOnCloseRequest(event -> {
                // Arrêt du serveur à la fermeture
                controleurMenuPrincipal.fermerServeur();

                // Sauvegarde des données avant fermeture si l'on a
                // importé des données
                if (GestionFichiers.getConferenciers().size() != 0
                        && GestionFichiers.getEmployes().size() != 0
                        && GestionFichiers.getExpositions().size() != 0
                        && GestionFichiers.getVisites().size() != 0) {
                    try {
                        GestionSauvegarde.sauvegarde();
                    } catch (IOException e) {
                        afficherErreur("Erreur lors de la sauvegarde",
                                e.getMessage());
                    }
                }
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
