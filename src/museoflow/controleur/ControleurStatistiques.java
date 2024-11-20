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
import museoflow.modele.Exposition;
import museoflow.modele.GestionFichiers;
import museoflow.modele.exceptions.HomonymeException;

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
    
    //@FXML
    //private TableView<Conferencier> tableConferenciers;
    
    /*
     * Création d'un tableau contenant le nom des colonnes du fichier
     * expositions
     */
    private final String[] NOMS_COLONNES =
            {"Classement", "Intitulé", "Nombre de visites"};

    /*
     * Création d'un tableau contenant les noms des propriétés de la
     * classe Exposition
     */
    private final String[] PROPRIETES =
            {"classement", "intituleExposition", "nbVisites"};
    
    @FXML
    public void initialize() {
        try {
            initialiserColonnesExpositions();
            initialiserColonnesConferenciers();
        } catch (HomonymeException e) {
            e.printStackTrace();
        }
    }
    
    /**
     *  Cette méthode permet d'initialiser les colonnes de la TableView
     *  pour faire le classement des expositions par nombre de visites.
     */
    private void initialiserColonnesExpositions() throws HomonymeException {
        // Boucle qui permet la création des colonnes
        for (int i = 0; i < NOMS_COLONNES.length; i++) {

            /*
             * Création d'une nouvelle colonne avec le titre
             * correspondant
             */
            TableColumn<Exposition, String> colonne =
                    new TableColumn<>(NOMS_COLONNES[i]);
            
         // Désactiver la possibilité de trier cette colonne
            colonne.setSortable(false);

            if (PROPRIETES[i].equals("classement")) {
            	colonne.setCellValueFactory(cellData -> {
            		// Trouver l'index de l'objet dans la liste actuelle
                    int classement = tableExpositions.getItems().indexOf(cellData.getValue()) + 1;
                    return new SimpleStringProperty(String.valueOf(classement));
            	});
        	} else if (PROPRIETES[i].equals("nbVisites")) {
            	colonne.setCellValueFactory(
            			cellData -> {
            	            Exposition exposition = cellData.getValue();
            	            int nbVisites = GestionFichiers.compterVisitesPourExposition(exposition.getIdExposition());
            	            return new SimpleStringProperty(String.valueOf(nbVisites));
            			});
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
     * Cette méthode permet d'initialiser les colonnes de la TableView
     * pour faire le classement des conférenciers par nombre de visites.
     */
    private void initialiserColonnesConferenciers() throws HomonymeException {
    	
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
    public void handlerBoutonRecherche() {

    }

}
