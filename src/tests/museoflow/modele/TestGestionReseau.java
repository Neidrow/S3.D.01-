package tests.museoflow.modele;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.net.Socket;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import museoflow.modele.GestionReseau;

class TestGestionReseau {
	/*
    @BeforeEach
    void setUp() {
        try {
            GestionReseau.demarrerServeur();
        } catch (IOException e) {
            fail("Erreur lors du démarrage du serveur : " + e.getMessage());
        }
    }

    @AfterEach
    void tearDown() {
        try {
            GestionReseau.arreterServeur();
        } catch (IOException e) {
            System.err.println("Erreur lors de l'arrêt du serveur : " + e.getMessage());
        }
    }

    @Test
    void testAfficherIP() {
        try {
            InetAddress ip = InetAddress.getLocalHost();
            assertEquals(ip.getHostAddress(), GestionReseau.afficherIP(), 
                "L'adresse IP retournée n'est pas correcte.");
        } catch (UnknownHostException e) {
            assertEquals("0.0.0.0", GestionReseau.afficherIP(),
                "L'adresse IP par défaut en cas d'erreur devrait être '0.0.0.0'.");
        }
    }

    @Test
    void testDemarrerServeur() {
        assertDoesNotThrow(() -> {
            GestionReseau.demarrerServeur();
        }, "Le serveur n'a pas pu démarrer sans lever une exception.");

        assertNotNull(GestionReseau.serverSocket, 
            "Le socket du serveur devrait être initialisé.");
        assertFalse(GestionReseau.serverSocket.isClosed(), 
            "Le socket du serveur ne devrait pas être fermé après le démarrage.");
    }

    @Test
    void testDemarrerServeurDejaEnCours() {
        assertTrue(GestionReseau.isRunning, 
            "Le serveur devrait déjà être en cours d'exécution après l'initialisation.");
        assertDoesNotThrow(() -> {
            GestionReseau.demarrerServeur();
        }, "Le serveur devrait permettre plusieurs appels à demarrerServeur sans lever d'exception.");
    }

    @Test
    void testArreterServeur() {
        assertDoesNotThrow(() -> {
            GestionReseau.arreterServeur();
        }, "Le serveur n'a pas pu être arrêté sans lever une exception.");
        assertTrue(GestionReseau.serverSocket.isClosed(), 
            "Le socket du serveur devrait être fermé après l'arrêt.");
    }

    @Test
    void testArreterServeurNonDemarre() {
        try {
            GestionReseau.arreterServeur();
        } catch (IOException e) {
            fail("Appel à arreterServeur sur un serveur non démarré ne devrait pas lever d'exception.");
        }
    }

    

    @Test
    void testExporterFichierEnvoiAvecIPInvalide() {
        String ipDistant = "256.256.256.256"; // IP invalide
        String fichierAExporter = "test.csv";

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            GestionReseau.exporterFichier(ipDistant, fichierAExporter, fichierAExporter);
        });
        assertEquals("Adresse IP invalide : " + ipDistant, exception.getMessage(),
            "Le message d'erreur devrait correspondre à une adresse IP invalide.");
    }

    @Test
    void testExporterFichierEnvoiFichierNonExistant() {
        String ipDistant = "127.0.0.1";
        String fichierInexistant = "fichier_inexistant.csv";

        Exception exception = assertThrows(IOException.class, () -> {
            GestionReseau.exporterFichier(ipDistant, fichierInexistant, fichierInexistant);
        });
        assertEquals("Fichier non trouvé ou non valide (seuls les fichiers CSV sont acceptés) : " + fichierInexistant,
            exception.getMessage(), "Le message d'erreur devrait indiquer que le fichier est introuvable.");
    }

    @Test
    void testCrypterVigenereTexte() {
        String texteClair = "Hello";
        String cle = "aaaa";
        String cle2 = "rodez";

        String texteChiffre = GestionReseau.crypter(texteClair, cle);
        String texteChiffre2 = GestionReseau.crypter(texteClair, cle2);

        assertEquals("Hello", texteChiffre, 
            "Le texte n'est pas correctement chiffré avec une clé sans décalage.");
        assertEquals("YsopN", texteChiffre2, 
            "Le texte n'est pas correctement chiffré avec la clé 'rodez'.");
    }

    @Test
    void testCrypterVigenereTexteAvecCaracteresSpeciaux() {
        String texteClair = "Hello, World!";
        String cle = "pzjzeni";

        String texteChiffre = GestionReseau.crypter(texteClair, cle);
        assertEquals("WDuKsjbÙNAKhl", texteChiffre, 
            "Le texte avec des caractères spéciaux n'est pas correctement chiffré.");
    }


    @Test
    void testCrypterVigenereTexteVide() {
        String texteClair = "";
        String cle = "KEY";

        String texteChiffre = GestionReseau.crypter(texteClair, cle);
        assertEquals("", texteChiffre, 
            "Un texte vide devrait renvoyer une chaîne vide après chiffrement.");
    }
    
    @Test
    void testExporterFichierEnvoiAvecIPValide() throws IOException {
        File fichierTest = File.createTempFile("testFichier", ".csv");
        try (FileWriter writer = new FileWriter(fichierTest)) {
            writer.write("test");
        }
        
        System.out.println("1");
        
        System.out.println("2");
        
        GestionReseau.exporterFichier("10.2.14.24", fichierTest.getAbsolutePath(), null); //TODO marche pas

        System.out.println("3");
        
        assertTrue(fichierTest.exists(), 
            "Le fichier devrait exister pour être envoyé.");
        assertTrue(fichierTest.getName().endsWith(".csv"), 
            "Le fichier devrait avoir l'extension '.csv'.");
        System.out.println("4");
    }
    */
    @Test
    void testPuissanceModulo() {
    	assertEquals(3, GestionReseau.puissanceModulo(2, 3, 5), "2^3 mod 5 devrait être 3");

        assertEquals(1, GestionReseau.puissanceModulo(3, 0, 7), "3^0 mod 7 devrait être 1 ");

        assertEquals(3, GestionReseau.puissanceModulo(3, 1, 7), "3^1 mod 7 devrait être 3");

        assertEquals(4, GestionReseau.puissanceModulo(2, 10, 5), "2^10 mod 5 devrait être 4");

        assertEquals(0, GestionReseau.puissanceModulo(100, 123, 1), "100^123 mod 1 devrait être 0");

        assertEquals(43, GestionReseau.puissanceModulo(3, 5, 100), "3^5 mod 100 devrait être 43");

        assertEquals(3, GestionReseau.puissanceModulo(7, 4, 11), "7^4 mod 11 devrait être 3");

        assertEquals(2, GestionReseau.puissanceModulo(8, 3, 17), "8^3 mod 17 devrait être 2");
        
        assertEquals(0, GestionReseau.puissanceModulo(1, 1, 1), "1^1 mod 1 devrait être 1");
        
        assertNotEquals(8, GestionReseau.puissanceModulo(1, 8, 5));
        assertNotEquals(8, GestionReseau.puissanceModulo(8, 1, 5));
        assertNotEquals(8, GestionReseau.puissanceModulo(1, 8, 8));
    }
    
    @Test
    void testEstPremier() {
        assertTrue(GestionReseau.estPremier(2));
        assertTrue(GestionReseau.estPremier(3));
        assertTrue(GestionReseau.estPremier(5));
        assertTrue(GestionReseau.estPremier(97)); 
        
        assertFalse(GestionReseau.estPremier(4));
        assertFalse(GestionReseau.estPremier(1));
        assertFalse(GestionReseau.estPremier(0));
        assertFalse(GestionReseau.estPremier(-7));
    }
    
    @Test
    public void testGenererPremier() {
        for (int i = 0; i < 100; i++) {
            int premier = GestionReseau.genererPremier(3000);
            assertTrue(GestionReseau.estPremier(premier));
            assertEquals(3, premier % 4); // Doit respecter la condition p % 4 == 3
        }
    }

    @Test
    public void testTrouverGenerateur() {
	    for (int i = 0; i < 100; i++) {
	        int p = GestionReseau.genererPremier(3000);
	        int g = GestionReseau.trouverGenerateur(p);
	
	        assertTrue(g > 1 && g < p);
	
	        assertNotEquals(1, GestionReseau.puissanceModulo(g, 2, p));
	        assertNotEquals(1, GestionReseau.puissanceModulo(g, (p - 1) / 2, p));
	    }
    }

    // Test pour vérifier `methodeDiffieHellman` (simulation simplifiée)
    @Test
    public void testMethodeDiffieHellman() {
        try {
            Socket s0 = new Socket(); // Remplacer par un mock approprié
            int secretServeur = GestionReseau.methodeDiffieHellman(s0, true);
            int secretClient = GestionReseau.methodeDiffieHellman(s0, false);

            // Le secret Diffie-Hellman doit être identique des deux côtés
            assertEquals(secretServeur, secretClient);
        } catch (Exception e) {
            fail("Exception inattendue: " + e.getMessage());
        }
    }
}
