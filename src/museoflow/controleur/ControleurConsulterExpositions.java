package museoflow.controleur;

import java.io.IOException;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import museoflow.modele.ConsulterDonnees;
import museoflow.modele.Exposition;
import museoflow.modele.FichierManquantException;

/**
 * Controleur de ConsulterExpositions
 */
public class ControleurConsulterExpositions {

    @FXML
    private ListView<String> listViewExpositions; // Liste des titres
                                                  // des expositions

    @FXML
    private TextArea textAreaDetails; // Affiche les détails de
                                      // l'exposition sélectionnée

    @FXML
    private ImageView boutonRetour;

    // Votre gestionnaire de données
    private ConsulterDonnees consulterDonnees = new ConsulterDonnees();

    // Initialiser les expositions dans la ListView
    /**
     * TODO commenter le rôle de cette méthode (SRP)
     */
    @FXML
    public void initialize() {
        try {
            // Charger les données d'expositions dans la ListView
            ArrayList<Exposition> expositions =
                    consulterDonnees.consulterListeExpositions();
            for (Exposition exposition : expositions) {
                listViewExpositions.getItems()
                        .add(exposition.getIntituleExposition()); // Ajouter
                                                                  // l'intitulé
                                                                  // de
                                                                  // l'exposition
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Gérer le clic sur une exposition dans la ListView
    /**
     * TODO commenter le rôle de cette méthode (SRP)
     * 
     * @param event
     * @throws FichierManquantException
     */
    @FXML
    public void handleListViewClick(MouseEvent event)
            throws FichierManquantException {
        String selectedTitle =
                listViewExpositions.getSelectionModel().getSelectedItem();
        if (selectedTitle != null) {
            // Afficher les détails de l'exposition sélectionnée
            Exposition selectedExposition = getExpositionByTitle(selectedTitle);
            if (selectedExposition != null) {
                textAreaDetails.setText(
                        "ID: " + selectedExposition.getIdExposition() + "\n" +
                                "Période début: "
                                + selectedExposition.getPeriodeOeuvreDeb()
                                + "\n" +
                                "Période fin: "
                                + selectedExposition.getPeriodeOeuvreFin()
                                + "\n" +
                                "Nombre d'œuvres: "
                                + selectedExposition.getNombreOeuvre() + "\n" +
                                "Résumé: " + selectedExposition.getResume()
                                + "\n" +
                                "Date de début: "
                                + selectedExposition.getDateDebutExpo() + "\n" +
                                "Date de fin: "
                                + selectedExposition.getDateFinExpo());
            }
        }
    }

    // Retourner l'exposition correspondante à un titre
    private Exposition getExpositionByTitle(String title)
            throws FichierManquantException {
        for (Exposition exposition : consulterDonnees
                .consulterListeExpositions()) {
            if (exposition.getIntituleExposition().equals(title)) {
                return exposition;
            }
        }
        return null;
    }

    // Gérer le bouton Retour
    /**
     * TODO commenter le rôle de cette méthode (SRP)
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
