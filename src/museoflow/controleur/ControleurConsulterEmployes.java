/*
 * ControleurConsulterEmployes.java              7 nov. 2024 
 * IUT de Rodez Info2 TPD 2024-2025, pas de copyright
 */
package museoflow.controleur;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import museoflow.modele.Employe;
import museoflow.modele.exceptions.HomonymeException;

/**
 * Controleur de ConsulterEmployes permettant de créer un tableau
 * contenant les données du fichier des employés afin d'afficher ces
 * données dans l'application
 * 
 * @author LOUBIERE Landry
 */
public class ControleurConsulterEmployes {

    /*
     * Création d'un tableau contenant le nom des colonnes du fichier
     * employés
     */
    private final String[] NOMS_COLONNES =
            { "Identifiant", "Nom", "Prenom", "Telephone" };

    /*
     * Création d'un tableau contenant les noms des propriétés de la
     * classe Employe
     */
    private final String[] PROPRIETES =
            { "idEmploye", "nom", "prenom", "telephone" };

    /*
     * Création de la TableView pour afficher les données sur les
     * employés
     */
    @FXML
    private TableView<Employe> tableEmployes;

    @FXML
    private Button boutonMenuPrincipal;

    @FXML
    private Button boutonRetour;

    @FXML
    private Button boutonVisites;

    @FXML
    private Button boutonExposition;

    @FXML
    private Button boutonConferencier;

    @FXML
    private Button boutonFiltres;

    @FXML
    private void initialiserColonnes() throws HomonymeException {

        System.out.println("initialisationcolonnes"); // TODO Ca
                                                      // appelle pas
                                                      // la méthode
                                                      // aled

       
        // Boucle qui permet la création des colonnes
        for (int i = 0; i < NOMS_COLONNES.length; i++) {

            /*
             * Création d'une nouvelle colonne avec le titre
             * correspondant
             */
            TableColumn<Employe, String> colonne =
                    new TableColumn<>(NOMS_COLONNES[i]);

            /*
             * Association de la colonne à une propriété de la classe
             * Employe. PropertyValueFactory utilise le nom de la
             * propriété pour récupérer les valeurs
             */
            colonne.setCellValueFactory(
                    new PropertyValueFactory<>(PROPRIETES[i]));

            // Ajout de la colonne configurée à la TableView
            tableEmployes.getColumns().add(colonne);

        }

        // Exemple de données
        ObservableList<Employe> employes = FXCollections.observableArrayList(
                new Employe("1", "Dupont", "Jean", "123456789"),
                new Employe("2", "Durand", "Marie", "987654321"));

        tableEmployes.setItems(employes);

    }
    
    // Remplissage de la TableView avec une liste d'objets Employe
    // tableEmployes.setItems(TODO mettre la liste des employes);
    

    /**
     * Fonctionnement de l'application de l'application quand le
     * bouton Menu Principal est cliqué
     */
    public void handlerBoutonMenuPrincipal() {
        try {
            // Charger la scène du menu principal

            Parent newRoot = FXMLLoader.load(
                    getClass().getResource("../vue/MenuPrincipal.fxml"));
            Scene newScene = new Scene(newRoot);

            // Récupérer le stage actuel
            Stage currentStage =
                    (Stage) boutonMenuPrincipal.getScene().getWindow();
            currentStage.setScene(newScene);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Fonctionnement de l'application de l'application quand le
     * bouton Retour est cliqué
     */
    public void handlerBoutonRetour() {
        try {
            // Charger la scène de choix des différentes consultations

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
    
    /**
     * Fonctionnement de l'application de l'application quand le
     * bouton de visites est cliqué
     */
    public void handlerBoutonVisites() {

    }
    
    /**
     * Fonctionnement de l'application de l'application quand le
     * bouton des expositions est cliqué
     */
    public void handlerBoutonExposition() {

    }
    
    /**
     * Fonctionnement de l'application de l'application quand le
     * bouton des conférenciers est cliqué
     */
    public void handlerBoutonConferencier() {

    }
    
    /**
     * Fonctionnement de l'application de l'application quand le
     * bouton des filtres est cliqué
     */
    public void handlerBoutonFiltres() {

    }

}
