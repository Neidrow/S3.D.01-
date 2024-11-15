/*
 * TestGestionFichiers.java 18 oct. 2024 IUT de Rodez Info2 TPD
 * 2024-2025, pas de copyright
 */
package tests.museoflow.modele;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import museoflow.modele.GestionReseau;

/**
 * TODO commenter la responsabilité de cette class (SRP)
 */
public class TestGestionReseau {

    @BeforeEach
    void setUp() {
        try {
            if (GestionReseau.serverSocket != null
                    && !GestionReseau.serverSocket.isClosed()) {
                GestionReseau.arreterServeur();
            }
        } catch (IOException e) {
            System.out.println("Erreur lors de l'arrêt du serveur : "
                    + e.getMessage());
        }
    }

    @AfterEach
    void tearDown() {
        // S'assurer que le serveur est arrêté après chaque test
        try {
            GestionReseau.arreterServeur();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testAfficherIP() {
        InetAddress ip;
        try {
            ip = InetAddress.getLocalHost();
            assertEquals(ip.getHostAddress(), GestionReseau.afficherIP());
        } catch (UnknownHostException e) {
            assertEquals("0.0.0.0", GestionReseau.afficherIP());
        }
    }

    @Test
    void testDemarrerServeur() {
        assertDoesNotThrow(() -> {
            GestionReseau.demarrerServeur();

            // Vérification que le serveur est en cours d'exécution
            assertNotNull(GestionReseau.serverSocket,
                    "Le socket du serveur devrait être initialisé.");
            assertFalse(GestionReseau.serverSocket.isClosed(),
                    "Le socket du serveur ne devrait pas être fermé après le démarrage.");
        });
    }

    @Test
    void testDemarrerServeurDejaEnCours() {
        // Démarre le serveur une première fois
        assertDoesNotThrow(() -> GestionReseau.demarrerServeur(),
                "Le serveur n'a pas pu démarrer la première fois.");

        // Vérifie que le serveur est bien en cours d'exécution
        assertTrue(GestionReseau.isRunning,
                "Le serveur devrait être en cours d'exécution après le premier démarrage.");

        // Tente de démarrer le serveur une deuxième fois et vérifie
        // l'état
        assertDoesNotThrow(() -> GestionReseau.demarrerServeur(),
                "Le serveur devrait permettre plusieurs appels à demarrerServeur sans lever une exception.");
        assertTrue(GestionReseau.isRunning,
                "Le serveur devrait toujours être en cours d'exécution.");
    }

    @Test
    void testArreterServeur() throws IOException {
        GestionReseau.demarrerServeur(); // Démarre le serveur
        assertDoesNotThrow(() -> {
            GestionReseau.arreterServeur();
        }, "Le serveur n'a pas pu être arrêté sans lever d'exception.");

        // Vérification que le serveur est arrêté
        assertTrue(GestionReseau.serverSocket.isClosed(),
                "Le socket du serveur devrait être fermé après l'arrêt.");
    }

    @Test
    void testArreterServeurNonDemarre() {
        assertDoesNotThrow(() -> {
            GestionReseau.arreterServeur();
        }, "Appel à arreterServeur sur un serveur non démarré devrait réussir sans exception.");
    }

    @Test
    void testExporterFichierEnvoiAvecIPValide() throws IOException {
        // Créer un fichier CSV temporaire pour le test
        File fichierTest = File.createTempFile("testFichier", ".csv");
        try (FileWriter writer = new FileWriter(fichierTest)) {
            writer.write("test");
        }

        // Utiliser l'adresse localhost pour simuler l'envoi
        String ipDistant = InetAddress.getLocalHost().getHostAddress();

        // Démarrer le serveur
        GestionReseau.demarrerServeur();

        // Vérifier que l'envoi ne lève pas d'exception
        assertDoesNotThrow(
                () -> GestionReseau.exporterFichier(ipDistant,
                        fichierTest.getAbsolutePath(), ipDistant),
                "L'envoi du fichier n'a pas pu être réalisé sans lever d'exception.");

        // Vérifier que le fichier existe et est un fichier CSV
        assertTrue(fichierTest.exists(),
                "Le fichier devrait exister pour être envoyé.");
        assertTrue(fichierTest.getName().endsWith(".csv"),
                "Le fichier doit être au format CSV.");
    }

    @Test
    void testExporterFichierEnvoiAvecIPInvalide() {
        String ipDistant = "256.256.256.256"; // IP invalide
        String fichierAExporter = "test.csv"; // Nom de fichier fictif

        // Vérifier que l'envoi avec une IP invalide lève une
        // exception
        Exception exception =
                assertThrows(IllegalArgumentException.class, () -> {
                    GestionReseau.exporterFichier(ipDistant, fichierAExporter,
                            fichierAExporter);
                });
        assertEquals("Adresse IP invalide : " + ipDistant,
                exception.getMessage());
    }

    @Test
    void testExporterFichierEnvoiFichierNonExistant() {
        String ipDistant = "127.0.0.1"; // Adresse IP valide
        String fichierInexistant = "fichier_inexistant.csv";

        // Vérifier que l'envoi d'un fichier non existant lève une
        // exception
        Exception exception = assertThrows(IOException.class, () -> {
            GestionReseau.exporterFichier(ipDistant, fichierInexistant,
                    fichierInexistant);
        });
        assertEquals(
                "Fichier non trouvé ou non valide (seuls les fichiers CSV sont acceptés) : "
                        + fichierInexistant,
                exception.getMessage());
    }
}