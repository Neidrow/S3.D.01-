/*
 * GestionFichiers.java                           18 oct. 2024
 * IUT de Rodez Info2 TPD 2024-2025, pas de copyright 
 */
package museoflow.modele;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

/**
 * Gestion de toute la partie communication réseau de l'application
 * MuseoFlow
 * 
 * @author Cylian POUPIN
 * @author Amjed SEHIL
 */
public class GestionFichiers {

    /** Numéro du Port utilisé */
    public static final int SERVER_PORT = 12346;

    /** Référence au ServerSocket pour pouvoir le fermer */
    public static ServerSocket serverSocket; 

    /** État du serveur */
    public static boolean isRunning = false; 

    /**
     * Tests manuels
     * 
     * @param args non utilisé
     */
    public static void main(String[] args) {
        System.out.println("IP machine : " + afficherIP());

    }

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
            serverSocket = new ServerSocket(SERVER_PORT);
            isRunning = true; // Mettre à jour l'état du serveur
            System.out.println("Serveur démarré sur le port : " + SERVER_PORT);
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
            String dossierReception) 
                    throws IOException {
        if (ipDistant != null && fichierAExporter != null) {
            // Validation de l'adresse IP fournie
            if (!validerAdresseIP(ipDistant)) {
                throw new IllegalArgumentException("Adresse IP invalide : " 
                        + ipDistant);
            }
            // Validation de l'existence du fichier et vérification de son extension
            File fichier = new File(fichierAExporter);
            if (!fichier.exists() || !fichier.getName().endsWith(".csv")) {
                throw new IOException("Fichier non trouvé ou non valide (seuls "
                        + "les fichiers CSV sont acceptés) : " + fichierAExporter);
            }
            // Envoi du fichier
            try (Socket socket = new Socket(ipDistant, SERVER_PORT);
                    FileInputStream fichierSource = new FileInputStream(fichier);
                    BufferedOutputStream fluxSortieSocket = 
                            new BufferedOutputStream(socket.getOutputStream())) {

                System.out.println("Connexion établie avec " + ipDistant);

                // Envoi du nom du fichier
                String nomFichier = fichier.getName();
                // Conversion en tableau de bytes car les sockets envoient des 
                //données sous forme de bytes
                fluxSortieSocket.write(nomFichier.getBytes());
                fluxSortieSocket.flush();
                
                
                // Lecture et envoi des données par paquets de 4096 octets
                byte[] tampon = new byte[2048];
                int octetsLus;
                while ((octetsLus = fichierSource.read(tampon)) != -1) {
                    fluxSortieSocket.write(tampon, 0, octetsLus);
                }
                fluxSortieSocket.flush();

                // Utilisation de flush() pour s'assurer que toutes les données en tampon
                // sont bien envoyées au destinataire avant de fermer le flux.
                // flush() force l'envoi des données restant en mémoire, garantissant que
                // le fichier est transmis en entier sans perte ni délai.
                fluxSortieSocket.flush();
                System.out.println("Fichier envoyé avec succès à : " + ipDistant);
            } catch (IOException erreurEnvoiFichier) {
                System.err.println("Erreur lors de l'envoi du fichier : " 
                        + erreurEnvoiFichier.getMessage());
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

                    byte[] tampon = new byte[2048];
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
     * Vérifie si une adresse IP donnée est valide et présente sur l'une des interfaces réseau du serveur.
     *
     * Parcourt toutes les interfaces réseau disponibles sur le serveur 
     * et les adresses IP associées. Elle compare ensuite chaque adresse IP rencontrée
     * avec l'adresse IP fournie en paramètre. Si une correspondance est trouvée,
     * la méthode renvoie `true`, indiquant que l'adresse IP est valide pour ce serveur.
     * 
     * En cas d'erreur lors de la récupération des interfaces réseau, 
     * une exception de type `SocketException` est levée et affichée.
     *
     * @param ip L'adresse IP à valider sous forme de chaîne de caractères (ex. : "192.168.0.1").
     * @return `true` si l'adresse IP est présente sur le serveur, `false` sinon. 
     */
    public static boolean validerAdresseIP(String ip) {
        try {
            // Récupérer toutes les interfaces réseau
            Enumeration<NetworkInterface> interfaces = 
                    NetworkInterface.getNetworkInterfaces();

            while (interfaces.hasMoreElements()) {
                NetworkInterface networkInterface = interfaces.nextElement();

                // TODO Voir si c'est vraiment utile
                // Ignorer les interfaces non valides (loopback, down, etc.)
                // if (networkInterface.isLoopback() || !networkInterface.isUp()) {
                //   continue;
                //}

                // Récupérer les adresses IP associées à cette interface
                Enumeration<InetAddress> addresses = 
                        networkInterface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress address = addresses.nextElement();

                    // Vérifier si l'adresse correspond à l'IP fournie
                    if (address.getHostAddress().equals(ip)) {
                        return true; // L'adresse est valide et présente sur le serveur
                    }
                }
            }
        } catch (SocketException erreurSocket) {
            erreurSocket.printStackTrace(); // Gérer l'exception si besoin
        }

        return true; // L'adresse n'a pas été trouvée sur le serveur
    }
}
