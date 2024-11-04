/*
 * ControleurChoixImporter.java                           4 nov. 2024
 * IUT de Rodez Info2 TPD 2024-2025, pas de copyright 
 */
package museoflow.controleur;

import java.io.File;
import java.io.IOException;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.DirectoryChooser;
import museoflow.modele.GestionFichiers;

/**
 * TODO commenter la responsabilité de cette class (SRP)
 */
public class ControleurChoixImporter {

    @FXML
    private Button buttonLocal;

    @FXML
    private Button buttonReseau;
    
    private boolean serveurEnCours = false;
    
    private void afficherMessage(String titre, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(titre);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void handleButtonLocal(ActionEvent event) {

    }

    @FXML
    void handleButtonReseau(ActionEvent event) {
        String ipLocale = GestionFichiers.afficherIP();
        if (serveurEnCours) {
            afficherMessage("Serveur déjà en cours", "Le serveur est déjà en "
                    + "attente de connexion..."
                    + " \nVeuillez transmettre cette IP " + ipLocale + " à "
                            + "l'expéditeur.");
            return;
        }

        serveurEnCours = true;

        afficherMessage("Votre IP", "Votre adresse IP locale est : " + ipLocale
                + "\nVeuillez transmettre cette IP à l'expéditeur.");
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Sélectionner le dossier de réception");
        File dossierReception = directoryChooser.showDialog(null);

        if (dossierReception == null) {
            afficherMessage("Erreur", "Aucun dossier sélectionné.");
            serveurEnCours = false;
            return;
        }

        Task<Void> receptionTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                try {
                    // Passer le dossier de réception à la méthode exporterFichier pour recevoir le fichier
                    GestionFichiers.exporterFichier(null, null, dossierReception
                            .getAbsolutePath());
                    updateMessage("Fichier reçu avec succès.");
                } catch (IOException e) {
                    updateMessage("Échec de la réception du fichier : " 
                + e.getMessage());
                } finally {
                    serveurEnCours = false;
                }
                return null;
            }
        };

        receptionTask.setOnSucceeded(e -> {
            afficherMessage("Succès", receptionTask.getMessage());
        });
        receptionTask.setOnFailed(e -> {
            afficherMessage("Erreur", receptionTask.getMessage());
        });

        new Thread(receptionTask).start();
    }

}


