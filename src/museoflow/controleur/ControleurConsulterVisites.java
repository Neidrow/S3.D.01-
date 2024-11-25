/*
 * ControleurConsulterVisites.java                  7 nov. 2024 
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
import museoflow.modele.GestionFichiers;
import museoflow.modele.Visite;
import museoflow.modele.exceptions.HomonymeException;

/**
 * Controleur de ConsulterVisites permettant de créer un tableau
 * contenant les données du fichier des employés afin d'afficher ces
 * données dans l'application
 * 
 * @author LOUBIERE Landry
 */
public class ControleurConsulterVisites {

    /*
     * Création d'un tableau contenant le nom des colonnes du fichier
     * visites
     */
    private final String[] NOMS_COLONNES =
            { "Identifiant visite", "Identifiant exposition",
                    "Identifiant conferencier", "Identifiant employe",
                    "Date de visite", "Horaire de début", "Nom du visiteur",
                    "Numéro de téléphone conférencier" };

    /*
     * Création d'un tableau contenant les noms des propriétés de la
     * classe Visites
     */
    private final String[] PROPRIETES =
            { "idVisite", "exposition", "conferencier", "employe",
                    "dateVisite", "horaireDebutVisite", "intitule",
                    "telephoneConferencier" };

    /*
     * Création de la TableView pour afficher les données sur les
     * visites
     */
    @FXML
    private TableView<Visite> tableVisites;

    @FXML
    private Button boutonMenuPrincipal;

    @FXML
    private Button boutonRetour;

    @FXML
    private Button boutonEmployes;

    @FXML
    private Button boutonExposition;

    @FXML
    private Button boutonConferencier;

    @FXML
    private Button boutonRecherche;

    /**
     * TODO commenter le rôle de cette méthode (SRP)
     */
    @FXML
    public void initialize() {
        try {
            initialiserColonnes();
        } catch (HomonymeException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void initialiserColonnes() throws HomonymeException {
       
        // Boucle qui permet la création des colonnes
        for (int i = 0; i < NOMS_COLONNES.length; i++) {

            /*
             * Création d'une nouvelle colonne avec le titre
             * correspondant
             */
            TableColumn<Visite, String> colonne =
                    new TableColumn<>(NOMS_COLONNES[i]);

            /*
             * Association de la colonne à une propriété de la classe
             * Visite. PropertyValueFactory utilise le nom de la
             * propriété associer une colonne à une propriétée
             * spécifique
             */
            colonne.setCellValueFactory(
                    new PropertyValueFactory<>(PROPRIETES[i]));

            // Ajout de la colonne configurée à la TableView
            tableVisites.getColumns().add(colonne);

        }


        /*
         * Conversion de l'ArrayList contenant le données de visites
         * en ObservableList
         */
        ObservableList<Visite> visites =
                FXCollections.observableArrayList(GestionFichiers.getVisites());

        // Ajouts données dans la tableView
        tableVisites.setItems(visites);

    }
    
    

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
     * bouton Employés est cliqué
     */
    public void handlerBoutonEmployes() {
        try {
            // Charger la scène de choix des différentes consultations

            Parent newRoot = FXMLLoader.load(
                    getClass().getResource("../vue/ConsulterEmployes.fxml"));
            Scene newScene = new Scene(newRoot);

            // Récupérer le stage actuel
            Stage currentStage =
                    (Stage) boutonEmployes.getScene().getWindow();
            currentStage.setScene(newScene);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    
    /**
     * Fonctionnement de l'application de l'application quand le
     * bouton des expositions est cliqué
     */
    public void handlerBoutonExposition() {
        try {
            // Charger la scène de choix des différentes consultations

            Parent newRoot = FXMLLoader.load(
                    getClass().getResource("../vue/ConsulterExpositions.fxml"));
            Scene newScene = new Scene(newRoot);

            // Récupérer le stage actuel
            Stage currentStage =
                    (Stage) boutonExposition.getScene().getWindow();
            currentStage.setScene(newScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    /**
     * Fonctionnement de l'application de l'application quand le
     * bouton des conférenciers est cliqué
     */
    public void handlerBoutonConferencier() {
        try {
            // Charger la scène de choix des différentes consultations

            Parent newRoot = FXMLLoader.load(
                    getClass()
                            .getResource("../vue/ConsulterConferenciers.fxml"));
            Scene newScene = new Scene(newRoot);

            // Récupérer le stage actuel
            Stage currentStage =
                    (Stage) boutonConferencier.getScene().getWindow();
            currentStage.setScene(newScene);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    
    /**
     * Fonctionnement de l'application de l'application quand le
     * bouton des filtres est cliqué
     */
    public void handlerBoutonRecherche() {


    }
}