/*
 * GestionFichiers.java                           18 oct. 2024
 * IUT de Rodez Info2 TPD 2024-2025, pas de copyright
 */
package museoflow.modele;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Gestion de toute la partie communication réseau de l'application
 * MuseoFlow
 *
 * @author Cylian POUPIN, Amjed SEHIL, Aurélien VALAT
 */
public class GestionFichiers {

	/** Numéro de port utilisé */
	public static final int SERVEUR_PORT = 12346; 

	/** Réference au ServerSocket pour pouvoir le fermer */
	public static ServerSocket serverSocket;

	/** Etat du serveur */
	public static boolean isRunning = false;

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
	 * Démarre le serveur pour recevoir des fichiers.
	 * 
	 * @throws IOException Si une erreur d'E/S survient
	 */
	public static void demarrerServeur() throws IOException {
		if (serverSocket == null || serverSocket.isClosed()) {
			serverSocket = new ServerSocket(SERVEUR_PORT);
			isRunning = true; // Mettre à jour l'état du serveur
			System.out.println("Serveur démarré sur le port : " + SERVEUR_PORT);
		} else {
			System.out.println("Le serveur est déjà en cours d'exécution.");
		}
	}

	/**
	 * Arrête le serveur s'il est en cours.
	 * 
	 * @throws IOException Si une erreur d'E/S survient
	 */
	public static void arreterServeur() throws IOException {
		if (serverSocket != null && !serverSocket.isClosed()) {
			serverSocket.close();
			System.out.println("Serveur arrêté.");
			isRunning = false; // Mettre à jour l'état du serveur
		} else {
			System.out.println("Le serveur n'était pas en cours d'exécution.");
		}
	}
	
    private static String generationClePartagee(Socket socket, boolean estServeur) {
        int p = 179; // nombre premier pour Diffie-Hellman
        int g = 18;  // base

        try {
            PrintStream out = new PrintStream(socket.getOutputStream());
            Scanner in = new Scanner(new InputStreamReader(socket.getInputStream()));

            // Générer la clé privée et le calcul de g^a mod p
            int a = (int) (1 + Math.random() * (p - 1));
            int gPuissanceA = puissanceModulo(g, a, p);

            if (estServeur) {
                // Serveur : recevoit d'abord g^a du client, puis envoyer g^b
                int gPuissanceB = Integer.parseInt(in.nextLine());
                out.println(gPuissanceA);
                int cle = puissanceModulo(gPuissanceB, a, p);
                System.out.println("cle = " + cle);
                return "" + cle; // calcul de la clé partagée
            } else {
                // Client : envoie d'abord g^a, puis recevoir g^b
                out.println(gPuissanceA);
                int gPuissanceB = Integer.parseInt(in.nextLine());
                int cle = puissanceModulo(gPuissanceB, a, p);
                System.out.println("cle = " + cle);
                return "" + cle; // calcul de la clé partagée
                
            }

        } catch (IOException e) {
            System.err.println("Erreur lors de la génération de la clé.");
            return null;
        }
    }
    
    /**
     * Calcule g puissance a mod p, utilisé pour l'échange de clé Diffie-Hellman
     * 
     * @param g base
     * @param a exposant
     * @param p module
     * @return résultat de g^a mod p
     */
    private static int puissanceModulo(int g, int a, int p) {
        int resultat = 1;
        for (int i = 0; i < a; i++) {
            resultat = (resultat * g) % p;
        }
        return resultat;
    }
    
	/**
	 * Chiffre un texte en clair en utilisant l'algorithme de Vigenère.
	 * 
	 * @param texteEnClair Le texte à chiffrer.
	 * @param cle La clé de chiffrement à utiliser (doit être un texte sans espaces).
	 * @return Le texte chiffré.
	 */
	public static String crypterVigenere(String texteEnClair, String cle) {
	    if (cle == null || cle.isEmpty()) {
	        throw new IllegalArgumentException("La clé de chiffrement ne peut pas être vide.");
	    }

	    StringBuilder texteChiffre = new StringBuilder();
	    int longueurCle = cle.length();
	    int indiceCle = 0;

	    for (int i = 0; i < texteEnClair.length(); i++) {
	        char caractere = texteEnClair.charAt(i);

	        if (Character.isLetter(caractere)) {
	            char base = Character.isLowerCase(caractere) ? 'a' : 'A';
	            int decalage = cle.charAt(indiceCle % longueurCle) - base;
	            char caractereChiffre = (char) ((caractere - base + decalage) % 26 + base);
	            texteChiffre.append(caractereChiffre);
	            indiceCle++;
	        } else {
	            texteChiffre.append(caractere);
	        }
	    }

	    return texteChiffre.toString();
	}


	/**
	 * Gère l'exportation d'un fichier texte (envoi ou réception) via un socket réseau.
	 * En mode envoi, le fichier spécifié est transféré vers l'adresse IP distante.
	 * En mode réception, le fichier est reçu sur le serveur local et sauvegardé sous le nom spécifié.
	 * 
	 * @param ipDistant       Adresse IP du destinataire pour envoyer le fichier. 
	 *                        Si null, la méthode se met en mode réception.
	 * @param fichierAExporter Chemin du fichier à exporter (en mode envoi) ou
	 *                        chemin de sauvegarde pour le fichier reçu (en mode réception).
	 * @param dossierReception 
	 * @throws IOException Si une erreur survient lors de l'envoi ou de la réception du fichier.
	 */
	public static void exporterFichier(String ipDistant, String fichierAExporter, String dossierReception)
            throws IOException {
        if (ipDistant != null && fichierAExporter != null) {
            // Mode envoi de fichier texte
            // Validation de l'adresse IP fournie
            if (!validerAdresseIP(ipDistant)) {
                throw new IllegalArgumentException("Adresse IP invalide : " + ipDistant);
            }

            // Validation de l'existence du fichier
            File fichier = new File(fichierAExporter);
            if (!fichier.exists() || !fichier.getName().endsWith(".csv")) {
                throw new IOException("Fichier non trouvé ou non valide (seuls les fichiers CSV sont acceptés) : " + fichierAExporter);
            }

            try (Socket socket = new Socket(ipDistant, SERVEUR_PORT);
                 BufferedReader fichierSource = new BufferedReader(new FileReader(fichier));
                 PrintWriter fluxSortieSocket = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true)) {

                System.out.println("Connexion établie avec " + ipDistant);

                // Générer la clé partagée de Vigenère avec Diffie-Hellman
                String cleVigenere = generationClePartagee(socket, ipDistant == null);
                if (cleVigenere == null) {
                    throw new IOException("Erreur lors de la génération de la clé Vigenère partagée.");
                }

                // Lire le contenu du fichier entier dans un StringBuilder
                StringBuilder contenuFichier = new StringBuilder();
                String ligne;
                while ((ligne = fichierSource.readLine()) != null) {
                    contenuFichier.append(ligne).append("\n");
                }

                // Chiffrer le contenu avec Vigenère
                String contenuChiffre = crypterVigenere(contenuFichier.toString(), cleVigenere);

                // Envoi du nom du fichier
                fluxSortieSocket.println(fichier.getName());

                // Envoi du contenu chiffré
                fluxSortieSocket.println(contenuChiffre);

                System.out.println("Fichier envoyé avec succès et chiffré avec Vigenère à : " + ipDistant);
            } catch (IOException erreurEnvoiFichier) {
                System.err.println("Erreur lors de l'envoi du fichier : " + erreurEnvoiFichier.getMessage());
                throw erreurEnvoiFichier;
            }
        } else {
            // Mode réception de fichier
            if (serverSocket == null || serverSocket.isClosed()) {
                isRunning = true;
                demarrerServeur();
            }

            try (Socket clientSocket = serverSocket.accept();
                 BufferedReader fluxEntrant = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

                String cleVigenere = generationClePartagee(clientSocket, ipDistant == null);
                if (cleVigenere == null) {
                    throw new IOException("Erreur lors de la génération de la clé Vigenère partagée.");
                }

                // Lire le nom du fichier
                String nomFichierRecu = fluxEntrant.readLine().trim();

                // Lire le contenu chiffré
                StringBuilder contenuChiffre = new StringBuilder();
                String ligne;
                while ((ligne = fluxEntrant.readLine()) != null) {
                    contenuChiffre.append(ligne).append("\n");
                }

             // TODO Déchiffrer le contenu
             // String contenuDechiffre = decrypterVigenere(contenuChiffre.toString(), cleVigenere);

                // Sauvegarder le fichier
                String nomSansExtension = nomFichierRecu.substring(0, nomFichierRecu.lastIndexOf('.'));
                String nomFinal = nomSansExtension + "_recu.csv";

                try (PrintWriter fluxDestination = new PrintWriter(new FileWriter(new File(dossierReception, nomFinal)))) {
                    fluxDestination.write(contenuChiffre.toString()); //TODO changer par contenuDechiffre
                    System.out.println("Fichier reçu et sauvegardé avec succès sous le nom " + nomFinal);
                }

            } catch (IOException erreurReception) {
                System.err.println("Erreur lors de la réception du fichier : " + erreurReception.getMessage());
                throw erreurReception;
            } finally {
                isRunning = false;
            }
        }
    }



	/**
	 * Vérifie si une adresse IP est valide (soit entre 0.0.0.0 et 255.255.255.255)
	 * 
	 * @param ip L'adresse IP à valider sous forme de chaîne de caractères (ex. : "192.168.0.1").
	 * @return `true` si l'adresse IP est présente sur le serveur, `false` sinon. 
	 */
	public static boolean validerAdresseIP(String ip) {
		// Regex pour vérifier le format de l'adresse IP
		String ipRegex = 
				"^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
						"(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
						"(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
						"(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";

		// Vérifie si l'adresse IP correspond à la regex
		return Pattern.matches(ipRegex, ip);

		// TODO vérifier si l'IP est connecté au serveur 
	}

	/**
	 * Tests manuels
	 * 
	 * @param args non utilisé
	 */
	public static void main(String[] args) {
		System.out.println("IP machine : " + afficherIP());

	}
}