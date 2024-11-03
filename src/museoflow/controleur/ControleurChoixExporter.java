package museoflow.controleur;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.Optional;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import museoflow.modele.GestionFichiers;

/**
 * Gère l'interface utilisateur
 * pour l'envoi et la réception de fichiers entre machines via une adresse IP.
 * Il utilise JavaFX pour les interactions utilisateur et gère la validation
 * des adresses IP ainsi que la sélection des fichiers CSV.
 *
 * <p>Cette classe est responsable des opérations suivantes :
 * <ul>
 *   <li>Demander une adresse IP pour l'envoi de fichiers.</li>
 *   <li>Permettre la sélection de fichiers CSV à envoyer.</li>
 *   <li>Gérer l'initialisation d'un serveur pour recevoir des fichiers.</li>
 *   <li>Afficher des messages d'information ou d'erreur à l'utilisateur.</li>
 * </ul>
 * </p>
 * @author Amjed SEHIL
 */
public class ControleurChoixExporter {

    @FXML
    private ImageView envoyerID;

    @FXML
    private ImageView recevoirID;

    @FXML
    private ImageView retour2ID;

    private boolean serveurEnCours = false;

    @FXML
    void handlerButttonEnvoyer(MouseEvent event) {
        String ipDistant;

        do {
            ipDistant = demanderIp();

            if (ipDistant != null && !GestionFichiers.validerAdresseIP(
                    ipDistant)) {
                afficherMessage("Erreur", "Adresse IP invalide. Veuillez entrer "
                        + "une adresse IP valide.");
            }
        } while (ipDistant != null && !GestionFichiers.validerAdresseIP(
                ipDistant));

        File fichierSelectionne = choisirFichierCSV();
        if (fichierSelectionne != null && ipDistant != null) {
            try {
                // Envoi du fichier sans le supprimer
                GestionFichiers.exporterFichier(ipDistant, fichierSelectionne.
                        getPath(), null);
                afficherMessage("Succès", "Fichier envoyé à " + ipDistant);
            } catch (IOException e) {
                afficherMessage("Erreur", "Échec de l'envoi du fichier : " 
            + e.getMessage());
            }

        } else {
            afficherMessage("Erreur", "Veuillez entrer un fichier CSV.");
        }
    }

    @FXML
    void handlerButttonRecevoir(MouseEvent event) {
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

    private File choisirFichierCSV() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Sélectionner un fichier CSV");
        fileChooser.getExtensionFilters().add(new ExtensionFilter("Fichiers CSV",
                "*.csv"));
        File fichier = fileChooser.showOpenDialog(null);

        if (fichier != null && !fichier.getName().endsWith(".csv")) {
            afficherMessage("Erreur", "Veuillez sélectionner un fichier CSV"
                    + " valide.");
            return null;
        }
        return fichier;
    }

    private String demanderIp() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Adresse IP");
        dialog.setHeaderText("Entrez l'adresse IP de la machine distante");
        dialog.setContentText("IP :");

        // Créer un TextFormatter pour limiter la saisie
        TextFormatter<String> ipFormatter = new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            // Vérifier que le nouveau texte est soit vide, soit un nombre ou un point
            if (newText.matches("^[0-9.]*$")) { // Autoriser la modification en retournant true
                return change;
            }
            return null; // Refuse la modification
        });

        dialog.getEditor().setTextFormatter(ipFormatter);

        Optional<String> result = dialog.showAndWait();
        return result.orElse(null);
    }

    @FXML
    void handlerButttonRetour(MouseEvent event) {
        try {
            // Charger la nouvelle scène de confirmation de sortie
            Parent newRoot = FXMLLoader.load(getClass().getResource(
                    "../vue/MenuPrincipal.fxml"));
            Scene newScene = new Scene(newRoot);
            Stage currentStage = (Stage) retour2ID.getScene().getWindow();
            currentStage.setScene(newScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void afficherMessage(String titre, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(titre);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
