/*
 * ControleurConsulterConferenciers.java 7 nov. 2024 IUT de Rodez
 * Info2 TPD 2024-2025, pas de copyright
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import museoflow.modele.Conferencier;
import museoflow.modele.GestionFichiers;
import museoflow.modele.exceptions.HomonymeException;

/**
 * Controleur de ConsulterConferenciers permettant de créer un tableau
 * contenant les données du fichier des employés afin d'afficher ces
 * données dans l'application
 * 
 * @author LOUBIERE Landry
 * @author POUPIN Cylian
 */
public class ControleurConsulterConferenciers {

    /*
     * Création d'un tableau contenant le nom des colonnes du fichier
     * conférenciers
     */
    private final String[] NOMS_COLONNES =
            { "Identifiant", "Nom", "Prénom", "Specialités",
                    "Numéro de téléphone", "Employé par le musée",
                    "Indisponibilités (début, fin)" };

    /*
     * Création d'un tableau contenant les noms des propriétés de la
     * classe Conferencier
     */
    private final String[] PROPRIETES =
            { "idConferencier", "nomConferencier", "prenomConferencier",
                    "specialite", "telephone", "employeParMusee",
                    "indisponibilites" };


    /*
     * Création de la TableView pour afficher les données sur les
     * conférenciers
     */
    @FXML
    private TableView<Conferencier> tableConferenciers;
    
    @FXML
    private Button boutonMenuPrincipal;

    @FXML
    private Button boutonRetour;

    @FXML
    private Button boutonVisites;

    @FXML
    private Button boutonEmployes;

    @FXML
    private Button boutonExpositions;

    @FXML
    private Button boutonRecherche;

    /**
     * Intructions executées au chargement de la vue
     */
    @FXML
    public void initialize() {
        try {
            // Initialiation des colonnes
            initialiserColonnes();

        } catch (HomonymeException e) {
            e.printStackTrace();

        }
    }

    /**
     * Initialisation des colonnes de la TableView en fonction des
     * propriétés de l'objet Conferencier. Parcourt chaque nom de
     * colonne, crée la colonne correspondante et la configure pour
     * afficher les valeurs des propriétés de l'objet.
     */
    private void initialiserColonnes() throws HomonymeException {

        // Boucle qui permet la création des colonnes
        for (int i = 0; i < NOMS_COLONNES.length; i++) {

            /*
             * Création d'une nouvelle colonne avec le titre
             * correspondant
             */
            TableColumn<Conferencier, String> colonne =
                    new TableColumn<>(NOMS_COLONNES[i]);

            /*
             * Vérification si l'index actuel correspond à
             * "specialite", qui est un tableau de String
             */

            if (PROPRIETES[i].equals("specialite")) {
                /**
                 * <p>
                 * Si l'attribut est "specialite", il s'agit d'un
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
                                cellData.getValue().getSpecialiteString()));

            } else if (PROPRIETES[i].equals("employeParMusee")) {
                colonne.setCellValueFactory(cellData -> {
                    boolean employeParMusee =
                            cellData.getValue().isEmployeParMusee();
                    return new SimpleStringProperty(
                            employeParMusee ? "Oui" : "Non");
                });

            } else if (PROPRIETES[i].equals("indisponibilites")) {
                // suppression des artefacts de lecture des String[]
                // (virgules seules, crochets)
                colonne.setCellValueFactory(
                        cellData -> new SimpleStringProperty(
                                cellData.getValue().formatterDates(cellData
                                        .getValue().getIndisponibilites())));

            } else {

                /*
                 * Association de la colonne à une propriété de la
                 * classe Conferencier. PropertyValueFactory utilise
                 * le nom de la propriété associer une colonne à une
                 * propriétée spécifique
                 */
                colonne.setCellValueFactory(
                        new PropertyValueFactory<>(PROPRIETES[i]));
            }

            // Ajout de la colonne configurée à la TableView
            tableConferenciers.getColumns().add(colonne);
        }

        /*
         * Conversion de l'ArrayList contenant les données de
         * conférenciers en ObservableList
         */
        ObservableList<Conferencier> conferenciers = FXCollections
                .observableArrayList(GestionFichiers.getConferenciers());

        // Ajouts des données dans la tableView
        tableConferenciers.setItems(conferenciers);

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
     * bouton des expositions est cliqué
     */
    public void handlerBoutonExposition() {
        try {
            // Charger la scène de choix des différentes consultations

            Parent newRoot = FXMLLoader.load(
                    getClass()
                            .getResource("../vue/ConsulterExpositions.fxml"));
            Scene newScene = new Scene(newRoot);

            // Récupérer le stage actuel
            Stage currentStage =
                    (Stage) boutonExpositions.getScene().getWindow();
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