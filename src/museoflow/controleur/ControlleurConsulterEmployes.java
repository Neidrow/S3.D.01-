/*
 * ControlleurConsulterEmployes.java                           6 nov. 2024
 * IUT de Rodez Info2 TPD 2024-2025, pas de copyright 
 */
package museoflow.controleur;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import museoflow.modele.Employe;

/**
 * TODO commenter la responsabilité de cette class (SRP)
 */
public class ControlleurConsulterEmployes {

   //Création de la TableView pour afficher les données sur les employés
   @FXML
   private TableView<Employe> tableEmployes;
   
   @FXML
   private void initialiserColonnes(){
       
       //Création des colonnes
       TableColumn<Employe, String> colonneIdentifiant =
               new TableColumn<>("Identifiant");
       // TODO finir initialisation des colonnes
   }

}
