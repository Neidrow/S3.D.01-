/*
 * ControlleurConsulterEmployes.java              7 nov. 2024 
 * IUT de Rodez Info2 TPD 2024-2025, pas de copyright
 */
package museoflow.controleur;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import museoflow.modele.Employe;

/**
 * Controleur de ConsulterEmployes permettant de créer un tableau
 * contenant les données du fichier des employés afin d'afficher ces
 * données dans l'application
 * 
 * @author LOUBIERE Landry
 */
public class ControlleurConsulterEmployes {

    /*
     * Création d'un tableau contenant le nom des colonnes du fichier
     * employés
     */
    private final String[] NOMS_COLONNES =
            { "Identifiant, Nom, Prenom, Telephone" };

    /*
     * Création d'un tableau contenant les noms des propriétés de la
     * classe Employe
     */
    private final String[] PROPRIETES =
            { "identifiant, nom, prenom, telephone" };

    /*
     * Création de la TableView pour afficher les données sur les
     * employés
     */
    @FXML
    private TableView<Employe> tableEmployes;

    @FXML
    private ImageView boutonRetour;

    @FXML
    private void initialiserColonnes() {

       
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

        // Remplissage de la TableView avec une liste d'objets Employe
        // tableEmployes.setItems(TODO mettre la liste des employes);
        
    }

    void handlerBoutonRetour() {
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

}
