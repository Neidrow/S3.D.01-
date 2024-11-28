/*
 * ControleurConsulterExpositions.java              7 nov. 2024 IUT
 * de Rodez Info2 TPD 2024-2025, pas de copyright
 */
package museoflow.controleur;

import java.io.IOException;

import javafx.animation.TranslateTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
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
    private Button boutonConferenciers;

    @FXML
    private Button boutonAfficherFiltres;

    @FXML
    private VBox vboxFiltres;

    @FXML
    private Button boutonAppliquerFiltres;

    @FXML
    private TextField fieldRechercheId; // Champ pour rechercher par
                                        // ID

    @FXML
    private TextField fieldRechercheIntitule; // Champ pour rechercher
                                              // par intitulé
    private boolean isFiltresVisible = false;

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
                    (Stage) boutonConferenciers.getScene().getWindow();
            currentStage.setScene(newScene);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Handler pour afficher ou masquer les filtres de recherche.
     */
    @FXML
    public void handlerAfficherFiltres() {
        // Animation pour ouvrir/fermer le menu
        TranslateTransition filtresAnimation =
                new TranslateTransition(Duration.millis(300), vboxFiltres);
        TranslateTransition tableAnimation =
                new TranslateTransition(Duration.millis(300), tableExpositions);
        if (!isFiltresVisible) {
            // Ouvrir le menu
            vboxFiltres.setVisible(true);
            filtresAnimation.setToX(0);
            tableAnimation.setToX(250);
            boutonAfficherFiltres.setText("Cacher Filtres");
            isFiltresVisible = true;
        } else {
            // Fermer le menu
            filtresAnimation.setToX(-250);
            tableAnimation.setToX(0);
            vboxFiltres.setVisible(false);
            boutonAfficherFiltres.setText("Filtres");
            isFiltresVisible = false;
        }

        filtresAnimation.play();
        tableAnimation.play();

        // Changer le texte du bouton pour indiquer l'état
    }
    /**
     * Handler pour appliquer les filtres de recherche.
     */
    @FXML
    public void handlerAppliquerFiltres() {
        // Récupérer les valeurs saisies
        String rechercheId = fieldRechercheId.getText().trim();
        String rechercheIntitule = fieldRechercheIntitule.getText().trim();

        // Filtrer les données
        ObservableList<Exposition> expositionsOriginales = FXCollections
                .observableArrayList(GestionFichiers.getExpositions());
        expositionsFiltrees = expositionsOriginales.filtered(expo -> {
            boolean matchId = rechercheId.isEmpty()
                    || expo.getIdExposition().contains(rechercheId);
            boolean matchIntitule = rechercheIntitule.isEmpty()
                    || expo.getIntituleExposition().toLowerCase()
                            .contains(rechercheIntitule.toLowerCase());
            return matchId && matchIntitule;
        });

        // Mettre à jour la table avec les données filtrées
        tableExpositions.setItems(expositionsFiltrees);
    }

}
