package tests.museoflow;

import museoflow.modele.GestionFichiers;
import java.io.IOException;

/**
 * Classe de test pour le serveur.
 * Cette classe démarre un serveur capable de recevoir des fichiers envoyés par des clients.
 */
public class ServeurTestExporter {

    private static boolean isServerRunning = false; // Indicateur de l'état du serveur

    /**
     * Méthode principale pour exécuter le serveur.
     * @param args arguments de la ligne de commande
     */
    public static void main(String[] args) {
        try {
            GestionFichiers.demarrerServeur();
            isServerRunning = true; // Indiquer que le serveur est en cours d'exécution
            System.out.println("Serveur démarré, en attente de fichiers...");

            // Recevoir un fichier 
            GestionFichiers.exporterFichier(null, null, null);

            // Une fois le fichier reçu, arrêter le serveur
            System.out.println("Fichier reçu, arrêt du serveur...");
            GestionFichiers.arreterServeur();
            isServerRunning = false; // Indiquer que le serveur a été arrêté
            System.out.println("Serveur arrêté avec succès.");

        } catch (IOException e) {
            System.err.println("Erreur lors de l'exécution du serveur : " 
        + e.getMessage());
        } finally {
            // S'assurer que le serveur est arrêté si quelque chose échoue
            try {
                if (isServerRunning) {
                    GestionFichiers.arreterServeur();
                    System.out.println("Serveur arrêté dans le bloc finally.");
                    isServerRunning = false; // Indiquer que le serveur a été arrêté
                }
            } catch (IOException e) {
                System.err.println("Erreur lors de l'arrêt du serveur dans le "
                        + "bloc finally : " + e.getMessage());
            }
        }
    }
}
