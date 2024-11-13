/*
 * GestionFichiers.java                           18 oct. 2024
 * IUT de Rodez Info2 TPD 2024-2025, pas de copyright
 */
package museoflow.modele;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.Socket;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.security.SecureRandom;
import java.util.Random;
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
	
	private static final Random random = new Random();

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

	/**
	 * Génère un nombre premier aléatoire avec une valeur maximale
	 * @param max
	 * @return le nombre premier
	 */
    public static int genererPremier(int max) {
        int p;
        do {
            p = random.nextInt(max - 2) + 2; // On évite 0 et 1
        } while (!estPremier(p) || p % 4 != 3);
        return p;
    }

    /**
     * Vérifie si un nombre est premier
     * @param n
     * @return vrai si le nombre est premier, faux sinon
     */
    public static boolean estPremier(int n) {
        if (n <= 1) return false;
        if (n <= 3) return true;
        if (n % 2 == 0 || n % 3 == 0) return false;
        for (int i = 5; i * i <= n; i += 6) {
            if (n % i == 0 || n % (i + 2) == 0) return false;
        }
        return true;
    }

    /**
     * Trouve un générateur pour le groupe multiplicatif mod p
     * @param p un nombre entier dont on veut trouver un générateur
     * @return un générateur de p
     */
    public static int trouverGenerateur(int p) {
        int pMoins1 = p - 1;
        int q = pMoins1 / 2; // Diviseur de p-1, car p ≡ 3 (mod 4)
        
        int g;
        do {
            g = random.nextInt(p - 2) + 2; // g entre 2 et p-2
        } while (puissanceModulo(g, 2, p) == 1 || puissanceModulo(g, q, p) == 1);
        
        return g;
    }
    
	/**
	 * 
	 * @param socket
	 * @param estServeur
	 * @return
	 */
    private static int methodeDiffieHellman(Socket socket, boolean estServeur) {
        int p = genererPremier(3000);
        int g = trouverGenerateur(p);

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
				int donneeSecrete = puissanceModulo(gPuissanceB, a, p);
				return donneeSecrete; 
			} else {
				// Client : envoie d'abord g^a, puis recevoir g^b
				out.println(gPuissanceA);
				int gPuissanceB = Integer.parseInt(in.nextLine());
				int donneeSecrete = puissanceModulo(gPuissanceB, a, p);
				return donneeSecrete; 

			}

		} catch (IOException e) {
			System.err.println("Erreur lors de la génération de la clé.");
			return 0;
		}
	}
    

    /**
     * Génère une clé de chiffrement aléatoire en utilisant la donnée secrète
     * uniquement comme graine de randomisation.
     * 
     * @param socket Le socket pour échanger la clé Diffie-Hellman
     * @param estServeur Indique si l'appelant est le serveur
     * @return Une clé de chiffrement aléatoire de longueur fixe
     */
    private static String creationCleChiffrement(Socket socket, boolean estServeur) {
    	// Obtenir la donnée secrète avec Diffie-Hellman
        int donneeSecrete = methodeDiffieHellman(socket, estServeur);
        
        if (donneeSecrete == 0) {
            System.err.println("Erreur : La donnée secrète est invalide.");
            return null;
        }

        // Initialiser un générateur aléatoire avec la donnée secrète comme graine
        Random random = new Random(donneeSecrete);

        StringBuilder cleChiffrement = new StringBuilder();

        // Générer une chaîne aléatoire de caractères alphabétiques
        for (int i = 0; i < 20; i++) {
            // Choisir un caractère aléatoire entre 'A' et 'Z'
            char caractereAleatoire = (char) ('a' + random.nextInt(26));
            cleChiffrement.append(caractereAleatoire);
        }
        return cleChiffrement.toString();
    }
    
    /**
     * Exponentiation modulaire. Calcule a à la puissance exposant modulo
     *  modulo, utilisé pour l'échange de clé Diffie-Hellman
     * 
     * @param a nombre
     * @param exposant l'exposant
     * @param m modulo
     * @return résultat de a^exposant mod modulo
     */
    public static int puissanceModulo(int a, int exposant, int modulo) {
    	int resultat = 1;

    	while (exposant > 0) {
    		if (exposant % 2 == 1) {
    			resultat = (resultat * a) % modulo;
    		}
    		a = (a * a) % modulo;
    		exposant = exposant / 2;
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
    public static String crypterOuDecrypterVigenere(String texte, String cle, boolean estDecryptage) {
        if (cle == null || cle.isEmpty()) {
            throw new IllegalArgumentException("La clé de chiffrement ne peut pas être vide.");
        }

        StringBuilder texteResultat = new StringBuilder();
        int longueurCle = cle.length();
        int indiceCle = 0;

        for (int i = 0; i < texte.length(); i++) {
            char caractere = texte.charAt(i);

            if (Character.isLetter(caractere)) {
                char base = Character.isLowerCase(caractere) ? 'a' : 'A';
                int decalage = cle.charAt(indiceCle % longueurCle) - base;

                if (estDecryptage) {
                    // On inverse le décalage pour déchiffrer
                    decalage = -decalage;
                }

                // Calculer le caractère résultant en appliquant le décalage
                char caractereResultat = (char) ((caractere - base + decalage + 26) % 26 + base);
                texteResultat.append(caractereResultat);
                indiceCle++;
            } else {
                texteResultat.append(caractere);
            }
        }

        return texteResultat.toString();
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
    private static String cleVigenere = null;

    public static void exporterFichier(String ipDistant, String fichierAExporter, String dossierReception)
            throws IOException {
        if (ipDistant != null && fichierAExporter != null) {
            // Mode envoi de fichier texte
            if (!validerAdresseIP(ipDistant)) {
                throw new IllegalArgumentException("Adresse IP invalide : " + ipDistant);
            }

            // Validation du fichier
            File fichier = new File(fichierAExporter);
            if (!fichier.exists() || !fichier.getName().endsWith(".csv")) {
                throw new IOException("Fichier non trouvé ou non valide (seuls les fichiers CSV sont acceptés) : " + fichierAExporter);
            }

            // Envoi du fichier
            try (Socket socket = new Socket(ipDistant, SERVEUR_PORT);
                 FileInputStream fichierSource = new FileInputStream(fichier);
                 BufferedOutputStream fluxSortieSocket = new BufferedOutputStream(socket.getOutputStream())) {

                System.out.println("Connexion établie avec " + ipDistant);

                // Générer la clé partagée de Vigenère avec Diffie-Hellman
                if (cleVigenere == null) {
                    cleVigenere = creationCleChiffrement(socket, true);
                    if (cleVigenere == null) {
                        throw new IOException("Erreur lors de la génération de la clé Vigenère partagée.");
                    }
                }

                // Envoi du nom du fichier
                String nomFichier = fichier.getName();
                fluxSortieSocket.write(nomFichier.getBytes());
                fluxSortieSocket.flush();

                // Lecture, chiffrement et envoi des données
                byte[] tampon = new byte[1000000];
                int octetsLus;
                while ((octetsLus = fichierSource.read(tampon)) != -1) {
                    String texteEnClair = new String(tampon, 0, octetsLus);
                    String texteChiffre = crypterOuDecrypterVigenere(texteEnClair, cleVigenere, false);
                    fluxSortieSocket.write(texteChiffre.getBytes());
                }
                fluxSortieSocket.flush();
                System.out.println("Fichier chiffré et envoyé avec succès à : " + ipDistant);

            } catch (IOException erreurEnvoiFichier) {
                System.err.println("Erreur lors de l'envoi du fichier : " + erreurEnvoiFichier.getMessage());
                throw erreurEnvoiFichier;
            }
        } else {
            // Mode réception
            if (serverSocket == null || serverSocket.isClosed()) {
                isRunning = true;
                demarrerServeur(); // Démarrer le serveur
            }

            try (Socket clientSocket = serverSocket.accept();
                 BufferedInputStream fluxEntrant = new BufferedInputStream(clientSocket.getInputStream())) {

                // Générer la clé partagée de Vigenère avec Diffie-Hellman (seulement une fois)
                if (cleVigenere == null) {
                    cleVigenere = creationCleChiffrement(clientSocket, false);
                    if (cleVigenere == null) {
                        throw new IOException("Erreur lors de la génération de la clé Vigenère partagée.");
                    }
                }

                // Lire le nom du fichier
                byte[] nomFichierBuffer = new byte[1024];
                int bytesRead = fluxEntrant.read(nomFichierBuffer);
                String nomFichierRecu = new String(nomFichierBuffer, 0, bytesRead).trim();
                String nomSansExtension = nomFichierRecu.substring(0, nomFichierRecu.lastIndexOf('.'));
                String nomFinal = nomSansExtension + "_recu.csv";

                // Sauvegarder dans un fichier reçu
                try (FileOutputStream fluxDestination = new FileOutputStream(new File(dossierReception, nomFinal))) {

                    byte[] tampon = new byte[1000000];
                    int octetsLus;
                    int totalBytesLus = 0;
                    while ((octetsLus = fluxEntrant.read(tampon)) != -1) {
                        String texteChiffre = new String(tampon, 0, octetsLus);
                        String texteDecrypte = crypterOuDecrypterVigenere(texteChiffre, cleVigenere, true); // Déchiffre
                        fluxDestination.write(texteDecrypte.getBytes());
                        totalBytesLus += octetsLus;
                    }

                    if (totalBytesLus > 0) {
                        System.out.println("Fichier reçu et déchiffré avec succès (" + totalBytesLus + " octets) sous le nom " + nomFinal);
                    } else {
                        System.out.println("Aucune donnée reçue.");
                        throw new IOException("Aucune donnée reçue.");
                    }
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