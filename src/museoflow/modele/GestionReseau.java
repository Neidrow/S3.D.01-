/*
 * GestionReseau.java                           18 oct. 2024
 * IUT de Rodez Info2 TPD 2024-2025, pas de copyright 
 */
package museoflow.modele;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Gestion de toute la partie communication réseau de l'application
 * MuseoFlow
 * 
 * @author Cylian POUPIN
 */
public class GestionReseau {

    /**
     * Tests manuels
     * 
     * @param args non utilisé
     */
    public static void main(String[] args) {
        System.out.println("IP machine : " + getIP());
    }

    /**
     * Retourne l'IP de la machine executant l'application.
     * 
     * @return l'IP de la machine executant l'application, 0.0.0.0 si
     *         l'adresse ne peut pas être récupérée.
     */
    public static String getIP() {
        InetAddress ip;
        try {
            ip = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            return "0.0.0.0";
        }
        return ip.getHostAddress();
    }
}