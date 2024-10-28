/*
 * GestionFichiers.java                           18 oct. 2024
 * IUT de Rodez Info2 TPD 2024-2025, pas de copyright
 */
package museoflow.modele;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Gestion de toute la partie communication réseau de l'application
 * MuseoFlow
 *
 * @author Cylian POUPIN, Aurélien VALAT
 */
public class GestionFichiers {

	private static final String ADRESSE_SERVEUR = "192.168.1.19";  
    private static final int PORT = 12345; 
    
	/**
	 * Retourne l'IP de la machine executant l'application.
	 *
	 * @return l'IP de la machine executant l'application, 0.0.0.0 si
	 *         l'adresse ne peut pas être récupérée.
	 */
	public static String afficherIP() {
		// On crée un objet 'ip' de type InetAdress
		InetAddress ip;
		try {
			// On essaye de récupérer les identifiants réseau de la
			// machine
			ip = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			// Si cela échoue, on renvoie l'ip d'erreur '0.0.0.0'
			return "0.0.0.0";
		}
		// Si les identifiants ont étés récupérés correctement, on
		// renvoie seulement l'ip
		return ip.getHostAddress();
	}

	/**
     * Méthode pour envoyer un fichier via le réseau après avoir vérifié et chiffré son contenu.
     * 
     * @param fichier Chemin du fichier à envoyer
     * @throws IOException Si le fichier n'est pas trouvé ou si une erreur réseau survient
     * @throws FichierManquantException Si le fichier spécifié est introuvable
     */
	public void importerFichierReseau(File fichier) throws IOException {
        try {
        	Socket socket = new Socket(ADRESSE_SERVEUR, PORT);
        
             FileInputStream fileInputStream = new FileInputStream(fichier);
             OutputStream outputStream = socket.getOutputStream();

            byte[] buffer = new byte[4096];
            int bytesRead;

            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            System.out.println("Fichier envoyé avec succès !");
        } catch (IOException e) {
            throw new IOException("Erreur lors de l'envoi du fichier.", e);
        }
	}

	/**
	 *
	 *
	 * @param args non utilisé
	 */
	public static void main(String[] args) {
		System.out.println("IP machine : " + afficherIP());

	}
}