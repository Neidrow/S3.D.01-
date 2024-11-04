package tests.museoflow;

import museoflow.modele.GestionFichiers;
import java.io.IOException;

/**
 * Classe de test pour le client.
 * Cette classe permet d'envoyer un fichier à un serveur spécifié.
 */
public class ClientTestExporter {

    /**
     * Méthode principale pour envoyer un fichier.
     * @param args arguments de la ligne de commande
     */
    public static void main(String[] args) {
        try {
            String ipServeur = "127.0.0.1"; // Adresse IP du serveur en local
            String fichierAEnvoyer = "C:\\Users\\aurelien.valat\\Downloads\\expositions 28_08_24 17_26.csv"; // Chemin vers le fichier à envoyer

            // Appel de la méthode pour envoyer le fichier
            GestionFichiers.exporterFichier(ipServeur, fichierAEnvoyer, null); // Le troisième paramètre peut être nul

        } catch (IOException e) {
            System.err.println("Erreur lors de l'envoi du fichier : " 
        + e.getMessage());
        }
    }
}
