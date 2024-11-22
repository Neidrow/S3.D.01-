
/*
 * ControleurStatistiques.java                           nov. 2024
 * IUT de Rodez Info2 TPD 2024-2025, pas de copyright 
 */
package museoflow.controleur;

import java.io.IOException;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import museoflow.modele.Conferencier;
import museoflow.modele.Exposition;
import museoflow.modele.GestionFichiers;
import museoflow.modele.Statistique;
import museoflow.modele.exceptions.HomonymeException;

/**
 * Contrôleur de la vue des statistiques
 */
public class ControleurStatistiques {

    @FXML
    private Button boutonRetour;

    @FXML
    private Button boutonMenuPrincipal;

    @FXML
    private Button boutonRecherche;

    @FXML
    private TableView<Exposition> tableExpositions;

    @FXML
    private TableView<Conferencier> tableConferenciers;
    
    @FXML
    private ComboBox<String> filtreConferenciers;

    /**
     * Création d'un tableau contenant le nom des colonnes pour le
     * classement des expositions par nombre de visites
     */
    private final String[] NOMS_COLONNES_EXPO =
            { "Classement", "Intitulé", "Nombre de visites" };

    /**
     * Création d'un tableau contenant les noms des propriétés pour le
     * classement des expositions par nombre de visites
     */
    private final String[] PROPRIETES_EXPO =
            { "classement", "intituleExposition", "nbVisites" };

    /**
     * Création d'un tableau contenant le nom des colonnes pour le
     * classement des conferenciers par nombre de visites
     */
    private final String[] NOMS_COLONNES_CONFERENCIER =
            { "Classement", "Nom", "Prénom", "Nombre de visites effectuées",
                    "Specialités", "Numéro de téléphone" };

    /**
     * Création d'un tableau contenant le nom des propriétés pour le
     * classement des conferenciers par nombre de visites
     */
    private final String[] PROPRIETES_CONFERENCIER =
            { "classement", "nomConferencier", "prenomConferencier",
                    "nbVisites", "specialite", "telephone" };

    @FXML
    private TabPane tabPane;

    @FXML
    private Tab ongletConferenciers; // Onglet conférenciers

    @FXML
    private Tab ongletExpositions; // Onglet expositions

    /**
     * Instruction qui s'exécute au chargement de la vue
     */
    @FXML
    public void initialize() {
        try {
            // Initialiser colonnes des tableaux
            initialiserColonnesExpositions();
            initialiserColonnesConferenciers();

            // Initialisation du filtre
            filtreConferenciers.getItems().addAll("Tous", "Internes", "Externes");
            filtreConferenciers.setValue("Filtrer par type de conferencier"); // Option par défaut
            filtreConferenciers.setOnAction(event -> handlerFiltre());
            filtreConferenciers.setVisible(false);

         // Écouter les changements d'onglets
         tabPane.getSelectionModel().selectedItemProperty()
                 .addListener((observable, oldTab, newTab) -> {
                if (newTab.equals(ongletConferenciers)) {
                    filtreConferenciers.setDisable(false); // Activer pour conférenciers
                    filtreConferenciers.setVisible(true); // Rendre visible
                } else {
                    filtreConferenciers.setDisable(true); // Désactiver pour les autres onglets
                    filtreConferenciers.setVisible(false); // Rendre invisible
                    filtreConferenciers.setValue("Tous"); // Réinitialiser le filtre
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Cette méthode permet d'initialiser les colonnes de la TableView
     * pour faire le classement des expositions par nombre de visites.
     */
    private void initialiserColonnesExpositions() {
        // Récupérer la liste des expositions
        ObservableList<Exposition> expositions = FXCollections
                .observableArrayList(GestionFichiers.getExpositions());

        // Trier les expositions par nombre de visites et attribuer
        // leur classement
        Statistique.trierExpositionsParVisites(expositions);

        // Initialiser les colonnes de la TableView
        for (int i = 0; i < NOMS_COLONNES_EXPO.length; i++) {
            TableColumn<Exposition, String> colonneExpo =
                    new TableColumn<>(NOMS_COLONNES_EXPO[i]);

            colonneExpo.setSortable(false); // Désactiver la
                                            // possibilité de trier
                                            // cette colonne

            if (PROPRIETES_EXPO[i].equals("classement")) {
                colonneExpo.setCellValueFactory(
                        cellData -> new SimpleStringProperty(String
                                .valueOf(cellData.getValue().getClassement())));
            } else if (PROPRIETES_EXPO[i].equals("nbVisites")) {
                colonneExpo.setCellValueFactory(
                        cellData -> new SimpleStringProperty(Statistique
                                .getNombreDeVisites(cellData.getValue())));
            } else {
                colonneExpo.setCellValueFactory(
                        new PropertyValueFactory<>(PROPRIETES_EXPO[i]));
            }

            tableExpositions.getColumns().add(colonneExpo);
        }

        // Lier la liste triée à la TableView
        tableExpositions.setItems(expositions);
    }

    /**
     * Cette méthode permet d'initialiser les colonnes de la TableView
     * pour faire le classement des conférenciers par nombre de
     * visites.
     */
    private void initialiserColonnesConferenciers() throws HomonymeException {
        // Récupérer la liste des conférenciers
        ObservableList<Conferencier> conferenciers = FXCollections
                .observableArrayList(GestionFichiers.getConferenciers());

        // Trier les conférenciers par nombre de visites et attribuer
        // leur classement
        Statistique.trierConferenciersParVisites(conferenciers);

        // Initialiser les colonnes de la TableView
        for (int i = 0; i < NOMS_COLONNES_CONFERENCIER.length; i++) {
            TableColumn<Conferencier, String> colonneConf =
                    new TableColumn<>(NOMS_COLONNES_CONFERENCIER[i]);

            colonneConf.setSortable(false); // Désactiver la
                                            // possibilité de trier
                                            // cette colonne

            if (PROPRIETES_CONFERENCIER[i].equals("classement")) {
                colonneConf.setCellValueFactory(
                        cellData -> new SimpleStringProperty(String.valueOf(
                                cellData.getValue().getClassement())));

            } else if (PROPRIETES_CONFERENCIER[i].equals("nbVisites")) {
                colonneConf.setCellValueFactory(
                        cellData -> new SimpleStringProperty(
                                Statistique.getNombreDeVisites(
                                        cellData.getValue())));

            } else if (PROPRIETES_CONFERENCIER[i].equals("specialite")) {
                colonneConf.setCellValueFactory(
                        cellData -> new SimpleStringProperty(
                                cellData.getValue().getSpecialiteString()));

            } else {
                colonneConf.setCellValueFactory(
                        new PropertyValueFactory<>(PROPRIETES_CONFERENCIER[i]));
            }

            tableConferenciers.getColumns().add(colonneConf);
        }

        // Lier la liste triée à la TableView
        tableConferenciers.setItems(conferenciers);
    }

    @FXML
    void handlerBoutonRetour() {
        try {
            Parent newRoot = FXMLLoader.load(getClass().getResource(
                    "../vue/MenuPrincipal.fxml"));
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
     * bouton Menu Principal est cliqué
     */
    @FXML
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
     * bouton des filtres est cliqué
     */
    @FXML
    public void handlerFiltre() {

        String filtreSelectionne = filtreConferenciers.getValue();

        // Récupération des conférenciers
        ObservableList<Conferencier> tousConferenciers = FXCollections
                .observableArrayList(GestionFichiers.getConferenciers());

        // Appliquer le filtre
        ObservableList<Conferencier> conferenciersFiltres =
                FXCollections.observableArrayList();

        for (Conferencier conferencier : tousConferenciers) {
            if ("Internes".equals(filtreSelectionne) && conferencier.isEmployeParMusee()) {
                conferenciersFiltres.add(conferencier);
            } else if ("Externes".equals(filtreSelectionne) && !conferencier.isEmployeParMusee()) {
                conferenciersFiltres.add(conferencier);
            } else if ("Tous".equals(filtreSelectionne)) {
                conferenciersFiltres.add(conferencier);
            }
        }
        
     // Trier les conférenciers filtrés par classement
     conferenciersFiltres.sort((c1, c2) -> Integer.compare(c1.getClassement(),
             c2.getClassement()));

        // Mettre à jour la table des conférenciers avec les données filtrées
        tableConferenciers.setItems(conferenciersFiltres);
    }


}
