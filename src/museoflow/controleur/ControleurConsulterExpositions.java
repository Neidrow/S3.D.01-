/*
 * ControleurConsulterExpositions.java 7 nov. 2024 IUT de Rodez Info2
 * TPD 2024-2025, pas de copyright
 */
package museoflow.controleur;

import java.io.IOException;
import java.time.LocalDate;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import museoflow.modele.Exposition;
import museoflow.modele.GestionFichiers;
import museoflow.modele.exceptions.HomonymeException;

/**
 * Controleur de ConsulterExpositions permettant de créer un tableau
 * contenant les données du fichier des employés afin d'afficher ces
 * données dans l'application
 * 
 * @author LOUBIERE Landry
 */
public class ControleurConsulterExpositions {

    /*
     * Création d'un tableau contenant le nom des colonnes du fichier
     * expositions
     */
    private final String[] NOMS_COLONNES =
            { "Identifiant", "Intitulé", "Début Période Oeuvres",
                    "Fin Période Oeuvres", "Nombre d'oeuvres", "Mots Clés",
                    "Résumé", "Date début expo", "Date fin expo" };

    /*
     * Création d'un tableau contenant les noms des propriétés de la
     * classe Exposition
     */
    private final String[] PROPRIETES =
            { "idExposition", "intituleExposition", "periodeOeuvreDeb",
                    "periodeOeuvreFin", "nombreOeuvre", "motsCles", "resume",
                    "dateDebutExpo", "dateFinExpo" };

    /*
     * Création de la TableView pour afficher les données sur les
     * expositions
     */
    @FXML
    private TableView<Exposition> tableExpositions;

    @FXML
    private Button boutonMenuPrincipal;

    @FXML
    private Button boutonRetour;

    @FXML
    private Button boutonVisites;

    @FXML
    private Button boutonEmployes;

    @FXML
    private Button boutonConferencier;

    @FXML
    private Button boutonRecherche;
    
    @FXML
    private Button boutonCalendrier;

    @FXML
    private TextField fieldRechercheId; // Champ pour rechercher par ID

    @FXML
    private TextField fieldRechercheIntitule; // Champ pour rechercher par intitulé

    // ObservableList qui contiendra les expositions filtrées
    private ObservableList<Exposition> expositionsFiltrees;

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

    /**
     * Cette méthode permet d'initialiser les colonnes de la TableView
     * en fonction des propriétés de l'objet Exposition. Elle parcourt
     * chaque nom de colonne, crée la colonne correspondante et la
     * configure pour afficher les valeurs des propriétés de l'objet.
     */
    private void initialiserColonnes() throws HomonymeException {
        System.out.println("initialisationcolonnes");

        // Boucle qui permet la création des colonnes
        for (int i = 0; i < NOMS_COLONNES.length; i++) {

            /*
             * Création d'une nouvelle colonne avec le titre
             * correspondant
             */
            TableColumn<Exposition, String> colonne =
                    new TableColumn<>(NOMS_COLONNES[i]);

            // Vérification si l'index actuel correspond à "motsCles",
            // qui est un tableau de String
            if (PROPRIETES[i].equals("motsCles")) {
                /**
                 * <p>
                 * Si l'attribut est "motsCles", il s'agit d'un
                 * tableau de chaînes, donc il est nécessaire de
                 * transformer ce tableau en une chaîne lisible avant
                 * de l'afficher.
                 * </p>
                 * <p>
                 * Pour ce faire, nous utilisons une méthode
                 * personnalisée :
                 * </p>
                 * <ul>
                 * <li>cellData.getValue() : récupère l'objet
                 * Exposition correspondant à la ligne actuelle de la
                 * TableView.</li>
                 * <li>.getMotsClesString() : appelle la méthode de la
                 * classe Exposition qui transforme le tableau de
                 * mots-clés en une chaîne de caractères, chaque
                 * mot-clé étant séparé par des virgules.</li>
                 * <li>SimpleStringProperty : permet d'afficher cette
                 * chaîne de caractères correctement dans la
                 * TableView.</li>
                 * </ul>
                 */
                colonne.setCellValueFactory(
                        cellData -> new SimpleStringProperty(
                                cellData.getValue().getMotsClesString()));
            } else {

                /*
                 * Association de la colonne à une propriété de la
                 * classe Exposition. PropertyValueFactory utilise le
                 * nom de la propriété associer une colonne à une
                 * propriétée spécifique
                 */
                colonne.setCellValueFactory(
                        new PropertyValueFactory<>(PROPRIETES[i]));
            }

            // Ajout de la colonne configurée à la TableView
            tableExpositions.getColumns().add(colonne);
        }

        /*
         * Conversion de l'ArrayList contenant le données
         * d'expositions en ObservableList
         */
        ObservableList<Exposition> expositions = FXCollections
                        .observableArrayList(GestionFichiers.getExpositions());

        // Ajouts des données dans la tableView
        tableExpositions.setItems(expositions);

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
     * bouton retour est cliqué
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
        try {
            // Charger la scène de choix des différentes consultations

            Parent newRoot = FXMLLoader.load(
                    getClass().getResource("../vue/ConsulterVisites.fxml"));
            Scene newScene = new Scene(newRoot);

            // Récupérer le stage actuel
            Stage currentStage =
                    (Stage) boutonVisites.getScene().getWindow();
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
        // Récupérer les valeurs des champs de texte pour filtrer
        String rechercheId = fieldRechercheId.getText().toLowerCase().trim();
        String rechercheIntitule = fieldRechercheIntitule.getText().toLowerCase().trim();

        // Liste des expositions à filtrer
        ObservableList<Exposition> allExpositions = FXCollections.observableArrayList(GestionFichiers.getExpositions());

        // Appliquer les filtres
        expositionsFiltrees = FXCollections.observableArrayList();

        for (Exposition expo : allExpositions) {
            boolean matchesId = rechercheId.isEmpty() || expo.getIdExposition().toLowerCase().contains(rechercheId);
            boolean matchesIntitule = rechercheIntitule.isEmpty() || expo.getIntituleExposition().toLowerCase().contains(rechercheIntitule);

            // Si l'exposition correspond aux critères, on l'ajoute à la liste filtrée
            if (matchesId && matchesIntitule) {
                expositionsFiltrees.add(expo);
            }
        }

        // Mettre à jour la TableView avec les expositions filtrées
        tableExpositions.setItems(expositionsFiltrees);
    }

    /** Méthode qui gère le clic sur le bouton calendrier */
    public void handlerBoutonCalendrier() {
        // Créer une boîte de dialogue pour choisir une plage de dates
        DatePicker dateDebutPicker = new DatePicker();
        DatePicker dateFinPicker = new DatePicker();

        // Afficher une boîte de dialogue pour choisir la plage de dates
        HBox hBox = new HBox(10, dateDebutPicker, dateFinPicker);
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Filtrer par date");
        alert.setHeaderText("Sélectionnez une période");
        alert.getDialogPane().setContent(hBox);

        ButtonType validerButton = new ButtonType("Valider");
        ButtonType annulerButton = new ButtonType("Annuler");
        alert.getButtonTypes().setAll(validerButton, annulerButton);

        // Afficher l'alerte et attendre la réponse
        alert.showAndWait().ifPresent(response -> {
            if (response == validerButton) {
                // Si l'utilisateur a validé, on filtre les expositions
                LocalDate dateDebut = dateDebutPicker.getValue();
                LocalDate dateFin = dateFinPicker.getValue();

                if (dateDebut != null && dateFin != null && !dateDebut.isAfter(dateFin)) {
                    filtrerExpositionsParDate(dateDebut, dateFin);
                } else {
                    // Si la date de début est après la date de fin, ou si une date est manquante
                    Alert errorAlert = new Alert(AlertType.ERROR);
                    errorAlert.setTitle("Erreur");
                    errorAlert.setHeaderText("Période invalide");
                    errorAlert.setContentText("La date de début ne peut pas être après la date de fin.");
                    errorAlert.showAndWait();
                }
            }
        });
    }

    // Méthode pour filtrer les expositions par date
    private void filtrerExpositionsParDate(LocalDate dateDebut, LocalDate dateFin) {
        // Filtrer les expositions selon la date de début et de fin
        ObservableList<Exposition> allExpositions = FXCollections.observableArrayList(GestionFichiers.getExpositions());
        ObservableList<Exposition> expositionsFiltrees = FXCollections.observableArrayList();

        for (Exposition expo : allExpositions) {
            LocalDate dateDebutExpo = LocalDate.parse(expo.getDateDebutExpo());
            LocalDate dateFinExpo = LocalDate.parse(expo.getDateFinExpo());

            // Vérifier si l'exposition est dans la période sélectionnée
            if (!dateDebutExpo.isAfter(dateFin) && !dateFinExpo.isBefore(dateDebut)) {
                expositionsFiltrees.add(expo);
            }
        }

        // Mettre à jour la TableView avec les expositions filtrées
        tableExpositions.setItems(expositionsFiltrees);
    }
}

