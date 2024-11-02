/*
 * TestGestionReseau.java                           18 oct. 2024
 * IUT de Rodez Info2 TPD 2024-2025, pas de copyright 
 */
package tests.museoflow.modele;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.junit.jupiter.api.Test;

/**
 * Tests de GestionReseau
 * 
 * @author Cylian POUPIN
 */
class TestGestionReseau {

    @Test
    void testGetIP() {
        InetAddress ip = null;
        try {
            ip = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            assertEquals("0.0.0.0", museoflow.modele.GestionReseau.getIP());
        }
        assertEquals(ip.getHostAddress(),
                museoflow.modele.GestionReseau.getIP());
    }
}