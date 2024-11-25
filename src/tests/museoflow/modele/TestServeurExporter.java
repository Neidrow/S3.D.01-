/*
 * TestServeurExporter.java                           nov. 2024
 * IUT de Rodez Info2 TPD 2024-2025, pas de copyright 
 */
package tests.museoflow.modele;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import org.junit.Test;

import museoflow.modele.GestionReseau;

/**
 * Classe de test pour le serveur. Cette classe démarre un serveur
 * capable de recevoir des fichiers envoyés par des clients.
 */
public class TestServeurExporter {

    private static boolean isServerRunning = false; // Indicateur de
                                                    // l'état du
                                                    // serveur

    /**
     * Méthode principale pour exécuter le serveur.
     */
    @Test
    public void testServeur() {
        try {
            GestionReseau.demarrerServeur();
            isServerRunning = true; // Indiquer que le serveur est en
                                    // cours d'exécution
            System.out.println("Serveur démarré, en attente de fichiers...");

            // Recevoir un fichier
            GestionReseau.exporterFichier(null, null, null);

            // Une fois le fichier reçu, arrêter le serveur
            System.out.println("Fichier reçu, arrêt du serveur...");
            GestionReseau.arreterServeur();
            isServerRunning = false; // Indiquer que le serveur a été
                                     // arrêté
            System.out.println("Serveur arrêté avec succès.");
            assertFalse(isServerRunning);

        } catch (IOException e) {
            System.err.println("Erreur lors de l'exécution du serveur : "
                    + e.getMessage());
        } finally {
            // S'assurer que le serveur est arrêté si quelque chose
            // échoue
            try {
                if (isServerRunning) {
                    GestionReseau.arreterServeur();
                    System.out.println("Serveur arrêté dans le bloc finally.");
                    isServerRunning = false; // Indiquer que le
                                             // serveur a été arrêté
                    fail("Serveur arrêté dans le bloc finally");
                }
            } catch (IOException e) {
                System.err.println("Erreur lors de l'arrêt du serveur dans le "
                        + "bloc finally : " + e.getMessage());
                fail("Erreur lors de l'arrêt du serveur dans le bloc finally");
            }
        }
    }
}
