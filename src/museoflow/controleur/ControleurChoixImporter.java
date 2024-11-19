/*
 * ControleurChoixImporter.java 4 nov. 2024 IUT de Rodez Info2 TPD
 * 2024-2025, pas de copyright
 */
package museoflow.controleur;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.opencsv.exceptions.CsvException;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import museoflow.modele.GestionFichiers;
import museoflow.modele.GestionReseau;
import museoflow.modele.exceptions.DonneesDejaImporteesException;
import museoflow.modele.exceptions.HomonymeException;
import museoflow.modele.exceptions.IdentifiantDupliqueException;

/**
 * Controleur de la vue d'importation des données.
 */
public class ControleurChoixImporter {

    private final String EXPLICATIONS_IMPORTATION_LOCALE_CSV =
        """
        Vous devez sélectionner les 4 fichiers CSV suivants d'un seul 
        coup (multi-sélection) :
    
            - Données des Conférenciers
            - Données des Employés
            - Données des Expositions
            - Données des Visites
    
        Ces fichiers CSV ont été générés à partir du site web avec une 
        structure compatible avec cette application.
    
        Ces derniers doivent contenir dans leur nom "conferencier", 
        "employe", "exposition" ou "visite" selon leur contenu, pour 
        que cette application puisse les identifier.
        """;

    @FXML
    private Button buttonLocal;

    @FXML
    private Button buttonReseau;

    private boolean serveurEnCours = false;


    @FXML
    private ImageView buttonRetour;

    private void afficherMessage(String titre, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(titre);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void afficherErreur(String titre, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(titre);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean demandeConfirmation(String titre, String message) {
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

    @FXML
    void handleButtonLocal(ActionEvent event) {
        if ((GestionFichiers.getConferenciers().size() == 0
                && GestionFichiers.getEmployes().size() == 0
                && GestionFichiers.getExpositions().size() == 0
                && GestionFichiers.getVisites().size() == 0)
            || demandeConfirmation("Données déja importées",
                    "Voulez-vous remplacer les données déjà importées ?")) {
            
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Sélectionner des fichiers CSV");
            // Filtre pour les fichiers CSV
            FileChooser.ExtensionFilter extFilter =
                    new FileChooser.ExtensionFilter(
                            "Fichiers CSV (*.csv)", "*.csv");
            fileChooser.getExtensionFilters().add(extFilter);
            // Permettre la sélection multiple
            List<File> fichiersSelectionnes =
                    fileChooser.showOpenMultipleDialog(
                            // Récupération de la fenêtre actuelle
                            buttonRetour.getScene().getWindow());
            // Si on a bien sélectionné des fichiers
            if (fichiersSelectionnes != null) {

                // Si les fichiers sélectionnés sont au nombre de 4
                if (fichiersSelectionnes.size() == 4) {

                    // On regarde quel fichier contient quoi via leurs
                    // noms
                    String cheminConferenciers = null,
                            cheminEmployes = null,
                            cheminExpositions = null,
                            cheminVisites = null;

                    // On parcouts les 4 fichiers en vérifiant quel
                    // fichier correspond à quelle donnée et en vérifiant
                    // que le nom du fichier ne contienne pas deux mots
                    // clés.
                    for (File fichierActuel : fichiersSelectionnes) {

                        if (fichierActuel.getName().contains("conferencier")
                                && !fichierActuel.getName().contains("employe")
                                && !fichierActuel.getName()
                                        .contains("exposition")
                                && !fichierActuel.getName()
                                        .contains("visite")) {
                            cheminConferenciers =
                                    fichierActuel.getAbsolutePath();

                        } else if (fichierActuel.getName().contains("employe")
                                && !fichierActuel.getName()
                                        .contains("conferencier")
                                && !fichierActuel.getName()
                                        .contains("exposition")
                                && !fichierActuel.getName()
                                        .contains("visite")) {
                            cheminEmployes = fichierActuel.getAbsolutePath();

                        } else if (fichierActuel.getName()
                                .contains("exposition")
                                && !fichierActuel.getName()
                                        .contains("conferencier")
                                && !fichierActuel.getName().contains("employe")
                                && !fichierActuel.getName()
                                        .contains("visite")) {
                            cheminExpositions = fichierActuel.getAbsolutePath();

                        } else if (fichierActuel.getName().contains("visite")
                                && !fichierActuel.getName()
                                        .contains("conferencier")
                                && !fichierActuel.getName()
                                        .contains("exposition")
                                && !fichierActuel.getName()
                                        .contains("employe")) {
                            cheminVisites = fichierActuel.getAbsolutePath();
                        }
                    }

                    // Vérification que tout les fichiers aient été identifiés
                    if (cheminConferenciers == null
                            || cheminEmployes == null
                            || cheminExpositions == null
                            || cheminVisites == null) {
                        afficherMessage("Erreur sur un fichier",
                                "Au moins un fichier ne peut pas être analysé "
                                  + "pour déterminer son contenu. Vérifiez que "
                                  + "les noms des fichiers correspondent aux "
                                  + "données qu'ils contiennent.");
                        // On arrête le processus d'importation et on rend
                        // la main à l'appelant
                        return;
                    }

                    // Si on remplace les données, on supprime d'abord
                    // les anciennes
                    if (!(GestionFichiers.getConferenciers().size() == 0
                            && GestionFichiers.getEmployes().size() == 0
                            && GestionFichiers.getExpositions().size() == 0
                            && GestionFichiers.getVisites().size() == 0)) {
                        GestionFichiers.effacerDonneesMemoire();
                    }

                    // Si tout s'est bien passé, on importe les données
                    // en mémoire
                    try {
                        GestionFichiers.importerConferenciers(
                                GestionFichiers
                                        .lectureCsv(cheminConferenciers));

                        GestionFichiers.importerEmployes(
                                GestionFichiers.lectureCsv(cheminEmployes));

                        GestionFichiers.importerExpositions(
                                GestionFichiers.lectureCsv(cheminExpositions));

                        GestionFichiers.importerVisites(
                                GestionFichiers.lectureCsv(cheminVisites));

                        afficherMessage("Données Importées",
                                "Les données ont été importées avec succès");

                        // Gestion des erreurs de données avec message
                        // explicite pour l'utilisateur
                    } catch (CsvException e) {
                        afficherErreur("Erreur d'importation d'un fichier CSV",
                                "Au moins un fichier ne peut pas être analysé.\n"
                                        + e.getMessage());
                    } catch (DonneesDejaImporteesException e) {
                        afficherErreur("Données déja importées",
                                e.getMessage());
                    } catch (IOException e) {
                        afficherErreur("Erreur de lecture d'un fichier CSV",
                                e.getMessage());
                    } catch (HomonymeException e) {
                        afficherErreur(
                            "Une même personne apparait plusieurs fois dans un "
                             + "CSV",
                                e.getMessage());
                    } catch (IdentifiantDupliqueException e) {
                        afficherErreur(
                            "Un fichier CSV contient plusieurs fois un même ID",
                             e.getMessage());
                    } catch (IllegalArgumentException e) {
                        afficherErreur("Données incohérentes", e.getMessage());
                    } catch (IllegalStateException e) {
                        afficherErreur(
                                "Fichier CSV manquant ou vide",
                                e.getMessage());
                    } catch (IndexOutOfBoundsException e) {
                        afficherErreur("Erreur de données dans un CSV",
                                e.getMessage());
                    }

                } else {
                    afficherMessage("Fichiers sélectionnés incorrects",
                            EXPLICATIONS_IMPORTATION_LOCALE_CSV);
                }
            } else {
                afficherMessage("Aucun fichier sélectionné",
                        "Vous devez sélectionner les fichiers CSV à importer.");
            } 
        }
    }

    @FXML
    void handleButtonReseau(ActionEvent event) {
        String ipLocale = GestionReseau.afficherIP();
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
                    // Passer le dossier de réception à la méthode
                    // exporterFichier pour recevoir le fichier
                    GestionReseau.exporterFichier(null, null, dossierReception
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

    @FXML
    void handlerButtonRetour() {
        try {
            Parent newRoot = FXMLLoader.load(getClass().getResource(
                    "../vue/MenuPrincipal.fxml"));
            Scene newScene = new Scene(newRoot);

            // Récupérer le stage actuel
            Stage currentStage = (Stage) buttonRetour.getScene().getWindow();
            currentStage.setScene(newScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}