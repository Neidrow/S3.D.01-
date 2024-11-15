package tests.museoflow.modele;

import java.io.IOException;

import museoflow.modele.GestionReseau;

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
            // Chemin vers le fichier à envoyer
            String fichierAEnvoyer = "src\\tests\\museoflow\\modele\\test.csv";

            // Appel de la méthode pour envoyer le fichier
            // Le troisième paramètre peut être nul
            GestionReseau.exporterFichier(ipServeur, fichierAEnvoyer, null);

        } catch (IOException e) {
            System.err.println("Erreur lors de l'envoi du fichier : " 
        + e.getMessage());
        }
    }
}
