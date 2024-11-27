/*
 * ControleurStatistiques.java                      nov. 2024 
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
import javafx.scene.control.ListCell;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import museoflow.modele.Conferencier;
import museoflow.modele.Exposition;
import museoflow.modele.Filtre;
import museoflow.modele.GestionFichiers;
import museoflow.modele.Statistique;
import museoflow.modele.exceptions.HomonymeException;

/**
 * Contrôleur de l'interface utilisateur pour gérer les statistiques.
 * Permet la navigation entre les onglets, la gestion des filtres, et
 * l'affichage des données dans les tableaux correspondants.
 */
public class ControleurStatistiques {

    /** Bouton permettant de revenir à l'écran précédent. */
    @FXML
    private Button boutonRetour;

    /** Bouton permettant de retourner au menu principal. */
    @FXML
    private Button boutonMenuPrincipal;

    /** Table affichant le classement des expositions. */
    @FXML
    private TableView<Exposition> tableExpositions;

    /** Table affichant le classement des conférenciers. */
    @FXML
    private TableView<Conferencier> tableConferenciers;

    /** Table affichant les pourcentages pour les expos */
    @FXML
    private TableView<Exposition> tablePourcentageExpos;

    /** Table affichant les pourcentages pour les conferenciers */
    @FXML
    private TableView<Conferencier> tablePourcentageConfs;

    /**
     * Filtre pour sélectionner un type de conférencier
     * (interne/externe).
     */
    @FXML
    private ComboBox<String> filtreConferenciers;

    /**
     * Filtre pour sélectionner un type d'exposition
     * (permanente/temporaire).
     */
    @FXML
    private ComboBox<String> filtreTypeExpo;

    /** Onglets de navigation. */
    @FXML
    private TabPane tabPane;

    /**
     * Onglet dédié à l'affichage et la gestion du classement des
     * conférenciers.
     */
    @FXML
    private Tab ongletConferenciers;

    /**
     * Onglet dédié à l'affichage et la gestion du classement des
     * expositions.
     */
    @FXML
    private Tab ongletExpositions;

    /** Onglet dédié à l'affichage des pourcentages pour les expos */
    @FXML
    private Tab ongletPourcentageExpos;

    /**
     * Onglet dédié à l'affichage des pourcentages pour les
     * conferenciers
     */
    @FXML
    private Tab ongletPourcentageConfs;

    /**
     * Tableau contenant les noms des colonnes pour l'affichage des
     * expositions classées par nombre de visites.
     */
    private final String[] NOMS_COLONNES_EXPO =
            { "Classement", "Nombre de visites", "Identifiant", "Intitulé",
                    "Début Période Oeuvres",
                    "Fin Période Oeuvres", "Nombre d'oeuvres", "Mots Clés",
                    "Résumé", "Date début expo", "Date fin expo" };

    /**
     * Tableau contenant les propriétés des expositions correspondant
     * aux colonnes affichées dans la table des expositions.
     */
    private final String[] PROPRIETES_EXPO =
            { "classement", "nbVisites", "idExposition", "intituleExposition",
                    "periodeOeuvreDeb",
                    "periodeOeuvreFin", "nombreOeuvre", "motsCles", "resume",
                    "dateDebutExpo", "dateFinExpo" };

    /**
     * Tableau contenant les noms des colonnes pour l'affichage des
     * conférenciers classés par nombre de visites.
     */
    private final String[] NOMS_COLONNES_CONFERENCIER =
            { "Classement", "Nombre de visites effectuées", "Identifiant",
                    "Nom", "Prénom", "Specialités",
                    "Numéro de téléphone", "Employé par le musée",
                    "Indisponibilités (début, fin)" };

    /**
     * Tableau contenant les propriétés des conférenciers
     * correspondant aux colonnes affichées dans la table des
     * conférenciers.
     */
    private final String[] PROPRIETES_CONFERENCIER =
            { "classement", "nbVisites", "idConferencier", "nomConferencier",
                    "prenomConferencier",
                    "specialite", "telephone", "employeParMusee",
                    "indisponibilites" };

    /**
     * Tableau contenant les noms des colonnes pour l'affichage des
     * conférenciers classés par nombre de visites.
     */
    private final String[] NOMS_COLONNES_POURCENTS_EXPO =
            { "Identifiant", "Intitulé", "Nombre de visites",
                    "Pourcentage de visites" };

    /**
     * Tableau contenant les propriétés des conférenciers
     * correspondant aux colonnes affichées dans la table des
     * conférenciers.
     */
    private final String[] PROPRIETES_POURCENTS_EXPO =
            { "idExposition", "intituleExposition", "nbVisites",
                    "pourcentageVisite" };

    /**
     * Initialise les composants de l'interface utilisateur. Configure
     * les tableaux, les filtres et gère les changements d'onglets.
     */
    @FXML
    public void initialize() {
        try {
            // Initialiser colonnes des tableaux
            initialiserColonnesExpositions();
            initialiserColonnesConferenciers();
            initialiserColonnesPourcentageExpo();
            initialiserColonnesPourcentageConf();

            // Initialisation du filtre pour le type de Conferencier
            filtreConferenciers.getItems().addAll("Filtrer par type de "
                    + "conferencier", "Tous", "Internes", "Externes");
            // Rendre un élèment de la liste déroulante non cliquable
            filtreConferenciers.setCellFactory(lv -> new ListCell<>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setText(null);
                        setDisable(false);
                    } else {
                        setText(item);
                        // Désactiver l'option Filtrer par type de
                        // conferencier
                        setDisable("Filtrer par type de conferencier"
                                .equals(item));
                    }
                }
            });
            filtreConferenciers.setVisible(false);

            // Initialisation du filtre pour le type d'Exposition
            filtreTypeExpo.getItems().addAll("Filtrer par type d'exposition",
                    "Toutes", "Permanentes", "Temporaires");
            // Rendre un élèment de la liste déroulante non cliquable
            filtreTypeExpo.setCellFactory(lv -> new ListCell<>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setText(null);
                        setDisable(false);
                    } else {
                        setText(item);
                        // Désactiver l'option Filtrer par type
                        // d'exposition
                        setDisable("Filtrer par type d'exposition"
                                .equals(item));
                    }
                }
            });

            // Écouter les changements d'onglets
            tabPane.getSelectionModel().selectedItemProperty()
                    .addListener((observable, oldTab, newTab) -> {

                        // Onglet Conferencier
                        if (newTab.equals(ongletConferenciers)) {
                            // Rendre visible pour conférenciers
                            filtreConferenciers.setVisible(true);
                            // Rendre invisible pour conférenciers
                            filtreTypeExpo.setVisible(false);

                            // Onglet Stats expo
                        } else if (newTab.equals(ongletPourcentageExpos)) {
                            // Rendre invisible pour Stats expo
                            filtreConferenciers.setVisible(false);
                            filtreTypeExpo.setVisible(true);
                        } else {
                            // Rendre invisible pour expositions
                            filtreConferenciers.setVisible(false);
                            // Réinitialiser le filtre pour qu'il
                            // affiche tout
                            filtreConferenciers.setValue(
                                    "Filtrer par type de conferencier");
                            // Rendre visible pour expositions
                            filtreTypeExpo.setVisible(true);
                            // Réinitialiser le filtre pour qu'il
                            // affiche tout
                            filtreTypeExpo
                                    .setValue("Filtrer par type d'exposition");
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

            if (PROPRIETES_EXPO[i].equals("motsCles")) {
                colonneExpo.setCellValueFactory(
                        cellData -> new SimpleStringProperty(
                                cellData.getValue().getMotsClesString()));
            } else if (PROPRIETES_EXPO[i].equals("classement")) {
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
            } else if (PROPRIETES_CONFERENCIER[i].equals("employeParMusee")) {
                colonneConf.setCellValueFactory(cellData -> {
                    boolean employeParMusee =
                            cellData.getValue().isEmployeParMusee();
                    return new SimpleStringProperty(
                            employeParMusee ? "Oui" : "Non");
                });

            } else if (PROPRIETES_CONFERENCIER[i].equals("indisponibilites")) {
                // suppression des artefacts de lecture des String[]
                // (virgules seules, crochets)
                colonneConf.setCellValueFactory(
                        cellData -> new SimpleStringProperty(
                                cellData.getValue().formatterDates(cellData
                                        .getValue().getIndisponibilites())));

            } else {
                colonneConf.setCellValueFactory(
                        new PropertyValueFactory<>(PROPRIETES_CONFERENCIER[i]));
            }

            tableConferenciers.getColumns().add(colonneConf);
        }

        // Lier la liste triée à la TableView
        tableConferenciers.setItems(conferenciers);
    }

    /**
     * Cette méthode permet d'initialiser les colonnes de la TableView
     * pour faire les pourcentages sur les expositions
     */
    private void initialiserColonnesPourcentageExpo() {
        // Récupérer la liste des expositions
        ObservableList<Exposition> expositions = FXCollections
                .observableArrayList(GestionFichiers.getExpositions());

        // Calculer le total des visites pour toutes les expositions
        int totalVisites = Statistique.calculerTotalVisites(expositions);

        // Initialiser les colonnes de la TableView
        for (int i = 0; i < NOMS_COLONNES_POURCENTS_EXPO.length; i++) {
            TableColumn<Exposition, String> colonnePourcentExpo =
                    new TableColumn<>(NOMS_COLONNES_POURCENTS_EXPO[i]);

            if (PROPRIETES_POURCENTS_EXPO[i].equals("pourcentageVisite")) {
                // Afficher le pourcentage de visites
                colonnePourcentExpo.setCellValueFactory(cellData -> {
                    Exposition expo = cellData.getValue();
                    int nbVisites = Integer
                            .parseInt(Statistique.getNombreDeVisites(expo));
                    double pourcentage = (totalVisites == 0) ? 0
                            : (double) nbVisites / totalVisites * 100;
                    return new SimpleStringProperty(
                            String.format("%.2f%%", pourcentage));
                });
            } else if (PROPRIETES_POURCENTS_EXPO[i].equals("nbVisites")) {
                colonnePourcentExpo.setCellValueFactory(
                        cellData -> new SimpleStringProperty(Statistique
                                .getNombreDeVisites(cellData.getValue())));
            } else {
                // Afficher les autres propriétés
                colonnePourcentExpo
                        .setCellValueFactory(new PropertyValueFactory<>(
                                PROPRIETES_POURCENTS_EXPO[i]));
            }

            tablePourcentageExpos.getColumns().add(colonnePourcentExpo);
        }

        // Lier la liste des expositions à la TableView
        tablePourcentageExpos.setItems(expositions);
    }

    /**
     * Cette méthode permet d'initialiser les colonnes de la TableView
     * pour faire les pourcentages sur les conferenciers
     */
    private void initialiserColonnesPourcentageConf() {

    }

    /**
     * Gère l'événement du bouton Retour. Cette méthode est déclenchée
     * lorsque l'utilisateur clique sur le bouton "Retour".
     */
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
     * Fonctionnement de l'application de l'application quand la
     * comboBox pour filtrer par type de conferencier est utilisée
     */
    @FXML
    public void filtrerTypeConferencier() {
        String filtreSelectionne = filtreConferenciers.getValue();

        // Obtenir les conférenciers filtrés
        ObservableList<Conferencier> conferenciersFiltres =
                Filtre.filtreTypeConferenciersFiltres(filtreSelectionne);

        // Mettre à jour la table des conférenciers avec les données
        // filtrées
        tableConferenciers.setItems(conferenciersFiltres);
    }

    /**
     * Fonctionnement de l'application de l'application quand la
     * comboBox pour filtrer par type d'exposition est utilisée
     */
    @FXML
    public void filtrerTypeExpo() {
        String filtreSelectionne = filtreTypeExpo.getValue();

        // Obtenir les expositions filtrées
        ObservableList<Exposition> expositionsFiltres =
                Filtre.filtreTypeExpo(filtreSelectionne);

        // Calculer le total des visites pour les expositions filtrées
        int totalVisitesFiltres =
                Statistique.calculerTotalVisites(expositionsFiltres);

        // Mettre à jour la table des expositions avec les données
        // filtrées
        tableExpositions.setItems(expositionsFiltres);

        // Mettre à jour les pourcentages pour tablePourcentageExpos
        tablePourcentageExpos.getColumns().clear();
        for (int i = 0; i < NOMS_COLONNES_POURCENTS_EXPO.length; i++) {
            TableColumn<Exposition, String> colonnePourcentExpo =
                    new TableColumn<>(NOMS_COLONNES_POURCENTS_EXPO[i]);

            if (PROPRIETES_POURCENTS_EXPO[i].equals("pourcentageVisite")) {
                // Afficher le pourcentage de visites recalculé
                colonnePourcentExpo.setCellValueFactory(cellData -> {
                    Exposition expo = cellData.getValue();
                    int nbVisites = Integer
                            .parseInt(Statistique.getNombreDeVisites(expo));
                    double pourcentage = (totalVisitesFiltres == 0) ? 0
                            : (double) nbVisites / totalVisitesFiltres * 100;
                    return new SimpleStringProperty(
                            String.format("%.2f%%", pourcentage));
                });
            } else if (PROPRIETES_POURCENTS_EXPO[i].equals("nbVisites")) {
                colonnePourcentExpo.setCellValueFactory(
                        cellData -> new SimpleStringProperty(Statistique
                                .getNombreDeVisites(cellData.getValue())));
            } else {
                // Afficher les autres propriétés
                colonnePourcentExpo
                        .setCellValueFactory(new PropertyValueFactory<>(
                                PROPRIETES_POURCENTS_EXPO[i]));
            }

            tablePourcentageExpos.getColumns().add(colonnePourcentExpo);
        }

        // Lier la liste filtrée à la TableView pourcentage
        tablePourcentageExpos.setItems(expositionsFiltres);
    }
}
