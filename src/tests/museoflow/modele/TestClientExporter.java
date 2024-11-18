/*
 * TestClientExporter.java 12 nov. 2024 IUT de Rodez Info2 TPD
 * 2024-2025, pas de copyright
 */
package tests.museoflow.modele;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.io.IOException;

import org.junit.Test;

import museoflow.modele.GestionReseau;

/**
 * Classe de test pour le client.
 * Cette classe permet d'envoyer un fichier à un serveur spécifié.
 */
public class TestClientExporter {

    /**
     * TODO commenter le rôle de cette méthode (SRP)
     */
    @Test
    public void testExporterFichier() {
        String ipServeur = "127.0.0.1"; // Adresse IP du serveur en
                                        // local
        String fichierAEnvoyer = "src\\tests\\museoflow\\modele\\test.csv";

        // Appel de la méthode pour envoyer le fichier
        // Le troisième paramètre peut être null
        assertDoesNotThrow(() -> {
            try {
                GestionReseau.exporterFichier(ipServeur, fichierAEnvoyer, null);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}