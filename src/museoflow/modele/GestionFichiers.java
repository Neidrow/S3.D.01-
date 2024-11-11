/*
 * GestionFichiers.java                           18 oct. 2024
<<<<<<< HEAD
 * IUT de Rodez Info2 TPD 2024-2025, pas de copyright
=======
 * IUT de Rodez Info2 TPD 2024-2025, pas de copyright 
>>>>>>> origin/Exporter_Fichiers
 */
package museoflow.modele;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.net.ServerSocket;
import java.net.UnknownHostException;
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
	 * Gère l'exportation d'un fichier (envoi ou réception) via un socket réseau.
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
	public static void exporterFichier(String ipDistant, String fichierAExporter,
			String dossierReception, String cleVigenere) 
					throws IOException {
		if (ipDistant != null && fichierAExporter != null) {
			// Validation de l'adresse IP fournie
			if (!validerAdresseIP(ipDistant)) {
				throw new IllegalArgumentException("Adresse IP invalide : " + ipDistant);
			}

			// Validation de l'existence du fichier et vérification de son extension
			File fichier = new File(fichierAExporter);
			if (!fichier.exists() || !fichier.getName().endsWith(".csv")) {
				throw new IOException("Fichier non trouvé ou non valide (seuls les fichiers CSV sont acceptés) : " + fichierAExporter);
			}

			// Envoi du fichier chiffré
			try (Socket socket = new Socket(ipDistant, SERVEUR_PORT);
					FileInputStream fichierSource = new FileInputStream(fichier);
					BufferedOutputStream fluxSortieSocket = new BufferedOutputStream(socket.getOutputStream())) {

				System.out.println("Connexion établie avec " + ipDistant);

				// Envoi du nom du fichier
				String nomFichier = fichier.getName();
				fluxSortieSocket.write(nomFichier.getBytes());
				fluxSortieSocket.flush();

				// Lecture, chiffrement et envoi des données par paquets de 4096 octets
				byte[] tampon = new byte[4096];
				int octetsLus;
				while ((octetsLus = fichierSource.read(tampon)) != -1) {
					// Convertir le tampon lu en chaîne pour chiffrer
					String texteEnClair = new String(tampon, 0, octetsLus);
					String texteChiffre = crypterVigenere(texteEnClair, cleVigenere);

					// Envoi du texte chiffré
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
			// Assurez-vous que le serveur est démarré
			if (serverSocket == null || serverSocket.isClosed()) {
				isRunning = true;
				demarrerServeur(); // Démarrer le serveur
			}

			// Réception du fichier
			try (Socket clientSocket = serverSocket.accept();
					BufferedInputStream fluxEntrant = new BufferedInputStream(
							clientSocket.getInputStream())) {

				// Lire le nom du fichier
				byte[] nomFichierBuffer = new byte[1024]; // Vérifier que le Buffer est assez grand
				int bytesRead = fluxEntrant.read(nomFichierBuffer);
				String nomFichierRecu = new String(nomFichierBuffer, 0, 
						bytesRead).trim(); // Nom du fichier reçu

				// Créer le nouveau nom pour le fichier reçu
				String nomSansExtension = nomFichierRecu.substring(0, 
						nomFichierRecu.lastIndexOf('.'));
				String nomFinal = nomSansExtension + "_recu.csv";

				// Utiliser le chemin spécifié pour le fichier reçu
				try (FileOutputStream fluxDestination = new FileOutputStream(
						new File(dossierReception, nomFinal))) {

					System.out.println("Connexion de " + clientSocket.
							getInetAddress().getHostAddress());

					byte[] tampon = new byte[1000000];
					int octetsLus;
					int totalBytesLus = 0; // Compteur d'octets reçus
					while ((octetsLus = fluxEntrant.read(tampon)) != -1) {
						fluxDestination.write(tampon, 0, octetsLus);
						totalBytesLus += octetsLus; // Ajouter au total des octets lus
					}

					if (totalBytesLus > 0) {
						System.out.println("Fichier reçu avec succès (" 
								+ totalBytesLus + " octets) sous le nom " + nomFinal);
					} else {
						System.out.println("Aucune donnée reçue.");
						throw new IOException("Aucune donnée reçue.");
					}
				}
			} catch (IOException erreurReception) {
				System.err.println("Erreur lors de la réception du fichier : "
						+ "serveur fermé");
				throw erreurReception;
			} finally {
				isRunning = false; // Indiquer que le serveur n'est plus en cours d'exécution
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

