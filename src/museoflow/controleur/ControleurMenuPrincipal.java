 package museoflow.controleur;


import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import museoflow.modele.GestionReseau;

/**
 * TODO commenter la responsabilité de cette class (SRP)
 */
public class ControleurMenuPrincipal {

	private static final String TITRE_FICHIER_INTROUVABLE 
	= "Erreur : fichier introuvable.";

	private static final String HEADER_ERREUR_OUVERTURE_FICHIER 
	= "Le fichier demandé ne peut pas s'ouvrir.";

	private static final String BUREAU_NON_SUPPORTE 
	= "L'ouverture de fichiers n'est pas pris en charge sur cette plateforme.";

	private static final String ERREUR_INCONNUE_OUVERTURE_FICHIER 
	= "Impossible d'ouvrir le fichier suite à une erreur inconnue.";


	@FXML
	private ImageView aideID;

	@FXML
	private ImageView consulterID;

	@FXML
	private ImageView exporterID;

	@FXML
	private ImageView importerID;

	@FXML
	private ImageView quitterID;

	@FXML
	private ImageView statID;


    @FXML
    void handlerButttonAide() {	
        ControleurMenuPrincipal controleurMenuPrincipal =
                new ControleurMenuPrincipal();
        controleurMenuPrincipal.ouvrirFichier(
                "src/museoflow/vue/documentation/NoticeUtilisation.pdf");
    }


	@FXML
	void handlerButttonConsulter(MouseEvent event) {
    	try {
            // Charger la nouvelle scène 
            Parent newRoot = FXMLLoader.load(getClass().getResource("../vue/ConsulterDonnees.fxml"));
            Scene newScene = new Scene(newRoot);

            // Récupérer le stage actuel
            Stage currentStage = (Stage) consulterID.getScene().getWindow();
            currentStage.setScene(newScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


	private String demanderIp() {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Adresse IP");
		dialog.setHeaderText("Entrez l'adresse IP de la machine distante");
		dialog.setContentText("IP :");

		// Créer un TextFormatter pour limiter la saisie
		TextFormatter<String> ipFormatter = new TextFormatter<>(change -> {
			String newText = change.getControlNewText();
			// Vérifier que le nouveau texte est soit vide, soit un nombre ou un point
			if (newText.matches("^[0-9.]*$")) { // Autoriser la modification en retournant true
				return change;
			}
			return null; // Refuse la modification
		});
		
		dialog.getEditor().setTextFormatter(ipFormatter);

		Optional<String> result = dialog.showAndWait();
		return result.orElse(null);
	}

	private void afficherMessage(String titre, String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(titre);
		alert.setContentText(message);
		alert.showAndWait();
	}

	private File choisirFichierCSV() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Sélectionner un fichier CSV");
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Fichiers CSV",
				"*.csv"));
		File fichier = fileChooser.showOpenDialog(null);

		if (fichier != null && !fichier.getName().endsWith(".csv")) {
			afficherMessage("Erreur", "Veuillez sélectionner un fichier CSV"
					+ " valide.");
			return null;
		}
		return fichier;
	}
	@FXML
	void handlerButttonExporter(MouseEvent event) {
		String ipDistant;

		do {
			ipDistant = demanderIp();

            if (ipDistant != null && !GestionReseau.validerAdresseIP(
					ipDistant)) {
				afficherMessage("Erreur", "Adresse IP invalide. Veuillez entrer "
						+ "une adresse IP valide.");
			}
        } while (ipDistant != null && !GestionReseau.validerAdresseIP(
				ipDistant));

		// Si l'utilisateur a annulé la saisie de l'IP (ipDistant est null), on ne continue pas
		if (ipDistant == null) {
			return; // Sortir de la méthode sans demander de fichier CSV
		}


		File fichierSelectionne = choisirFichierCSV();
		if (fichierSelectionne != null && ipDistant != null) {
			try {
				// Envoi du fichier sans le supprimer
                GestionReseau.exporterFichier(ipDistant, fichierSelectionne.
						getPath(), null);
				afficherMessage("Succès", "Fichier envoyé à " + ipDistant);
			} catch (IOException e) {
				afficherMessage("Erreur", "Échec de l'envoi du fichier : " 
						+ e.getMessage());
			}

		} else {
			afficherMessage("Erreur", "Veuillez entrer un fichier CSV.");
		}
	}

	@FXML
	void handlerButttonImporter(MouseEvent event) {
		try {
			// Charger la nouvelle scène de confirmation de sortie
			Parent newRoot = FXMLLoader.load(getClass().getResource(
					"../vue/ChoixImporter.fxml"));
			Scene newScene = new Scene(newRoot);

			// Récupérer le stage actuel
			Stage currentStage = (Stage) importerID.getScene().getWindow();
			currentStage.setScene(newScene);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void handlerButttonQuitter(MouseEvent event) {
		try {
			// Charger la nouvelle scène de confirmation de sortie
			Parent newRoot = FXMLLoader.load(getClass().getResource(
					"../vue/EcranQuitter.fxml"));
			Scene newScene = new Scene(newRoot);

			// Récupérer le stage actuel
			Stage currentStage = (Stage) quitterID.getScene().getWindow();
			currentStage.setScene(newScene);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	@FXML
	void handlerButttonRapport(MouseEvent event) {

	}

	@FXML
	void handlerButttonStat(MouseEvent event) {

	}


	/**
	 * Ouvre le fichier spécifié en argument avec l'application par
	 * défaut du système d'exploitation.
	 * 
	 * @param cheminFichier chemin du fichier à ouvrir
	 */
	@FXML
	public void ouvrirFichier(String cheminFichier) {
		File fichierAOuvrir = new File(cheminFichier);
		System.out.println("Chemin du fichier : " + cheminFichier);

		if (!fichierAOuvrir.exists()) {
			Alert boiteFichierIntrouvable = new Alert(Alert.AlertType.ERROR,
					TITRE_FICHIER_INTROUVABLE, ButtonType.OK);

			boiteFichierIntrouvable.setTitle(TITRE_FICHIER_INTROUVABLE);
			boiteFichierIntrouvable
			.setHeaderText(HEADER_ERREUR_OUVERTURE_FICHIER);

			Optional<ButtonType> fichierIntrouvable = boiteFichierIntrouvable
					.showAndWait();

		} else {
			if (!Desktop.isDesktopSupported()) {
				Alert boiteBureauNonSupporte = new Alert(Alert.AlertType.ERROR,
						HEADER_ERREUR_OUVERTURE_FICHIER, ButtonType.OK);

				boiteBureauNonSupporte
				.setTitle(HEADER_ERREUR_OUVERTURE_FICHIER);
				boiteBureauNonSupporte.setHeaderText(BUREAU_NON_SUPPORTE);

				Optional<ButtonType> bureauNonSupporte = boiteBureauNonSupporte
						.showAndWait();

			} else {
				Desktop desktop = Desktop.getDesktop();
				try {
					desktop.open(fichierAOuvrir);
				} catch (IOException e) {
					Alert boiteErreurInconnueOuverture = new Alert(
							Alert.AlertType.ERROR,
							HEADER_ERREUR_OUVERTURE_FICHIER, ButtonType.OK);

					boiteErreurInconnueOuverture
					.setTitle(HEADER_ERREUR_OUVERTURE_FICHIER);
					boiteErreurInconnueOuverture
					.setHeaderText(ERREUR_INCONNUE_OUVERTURE_FICHIER);

					Optional<ButtonType> erreurInconnue = 
							boiteErreurInconnueOuverture.showAndWait();
				}
			}
		}
	}

	/**
	 * Ferme le serveur lorsque l'application se ferme
	 */
	public void fermerServeur() {
		try {
            GestionReseau.arreterServeur(); // Arrêtez le serveur
		} catch (IOException e) {
			System.err.println("Erreur lors de l'arrêt du serveur : " 
					+ e.getMessage());
		}
	}
}