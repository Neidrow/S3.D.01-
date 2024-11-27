/*
 * GestionReseau.java                               18 oct. 2024 
 * IUT de Rodez Info2 TPD 2024-2025, pas de copyright
 */
package museoflow.modele;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Gestion de toute la partie communication réseau de l'application
 * MuseoFlow
 *
 * @author Cylian POUPIN
 * @author Amjed SEHIL
 * @author Aurélien VALAT
 */
public class GestionReseau {

    /** Numéro de port utilisé */
    public static final int SERVEUR_PORT = 12346;

    /** Réference au ServerSocket pour pouvoir le fermer */
    public static ServerSocket serverSocket;

    /** Etat du serveur */
    public static boolean isRunning = false;

    private static Random random = new Random();

    /** Alphabet personnalisé */
    public static String alphabet =
            "abcdefghijklmnopqrstuvwxyz"
                    + "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                    + "ÀÂÄÉÈÊËÏÎÔÖÙÛÜŸÇ"
                    + "àâäéèêëïîôöùûüÿç"
                    + " '.,;!?";

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
            /*
             * On essaye de récupérer les identifiants réseau de la
             * machine
             */
            ip = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            // Si cela échoue, on renvoie l'ip d'erreur '0.0.0.0'
            return "0.0.0.0";
        }
        /*
         * Si les identifiants ont étés récupérés correctement, on
         * renvoie seulement l'ip
         */
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
            isRunning = false; // Mettre à jour l'état du serveur
        }
    }

    /**
     * Génère un nombre premier aléatoire avec une valeur maximale
     * 
     * @param max
     * @return le nombre premier
     */
    public static int genererPremier(int max) {
        int p;
        do {
            p = random.nextInt(max - 2) + 2; // +2 pour que p >=2
        } while (!estPremier(p));
        return p;
    }

    /**
     * Vérifie si un nombre est premier
     * 
     * @param n le nombre à tester
     * @return vrai si le nombre est premier, faux sinon
     */
    public static boolean estPremier(int n) {
        // les nombres inférieurs ou égaux à 1 ne sont pas premiers
        if (n <= 1) {
            return false;
        }

        // On vérifie les diviseurs possibles de 2 à √n
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) {
                return false; // n pas premier car divisible par i
            }
        }
        return true;
    }

    /**
     * Exponentiation modulaire. Calcule a à la puissance exposant
     * modulo modulo, utilisé pour l'échange de clé Diffie-Hellman
     * 
     * @param a        le nombre
     * @param exposant l'exposant
     * @param modulo   le modulo
     * @return résultat de a^exposant mod modulo
     */
    public static int expoModulaire(int a, int exposant, int modulo) {

        if (modulo <= 0) { // erreur si le modulo est négatif
            throw new IllegalArgumentException(
                    "Le modulo doit être strictement positif");
        }

        if (modulo == 1) {
            return 0; // a^exposant mod 1 est toujours égal à 0
        }

        int resultat = 1;

        if (exposant < 0) { // erreur si l'exposant est négatif

            throw new IllegalArgumentException(
                    "L'exposant doit être strictement positif");
        }
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
     * Trouve un générateur pour le groupe multiplicatif mod p
     * 
     * @param p un nombre entier dont on veut trouver un générateur
     * @return un générateur de p
     */
    public static int trouverGenerateur(int p) {
        int pMoins1 = p - 1;
        int q = pMoins1 / 2; // Diviseur de p-1, car p ≡ 3 (mod 4)

        int g;
        do {
            g = random.nextInt(p - 2) + 2; // g entre 2 et p-2
        } while (expoModulaire(g, 2, p) == 1
                || expoModulaire(g, q, p) == 1);

        return g;
    }

    /**
     * Chiffrement par méthode de Diffie-Hellman
     * 
     * @param socket     socket réseau
     * @param estServeur Si la machine courante est le serveur
     * @return Donnée secrète
     */
    public static int methodeDiffieHellman(Socket socket, boolean estServeur) {
        int p, g;

        try {
            PrintStream out = new PrintStream(socket.getOutputStream());
            Scanner in =
                    new Scanner(new InputStreamReader(socket.getInputStream()));

            if (estServeur) {
                // Serveur génère p et g, puis les envoie au client
                p = genererPremier(3000);
                g = trouverGenerateur(p);
                out.println(p); // Envoie de p
                out.println(g); // Envoie de g
            } else {
                // Client reçoit p et g depuis le serveur
                p = Integer.parseInt(in.nextLine()); // Réception de p
                g = Integer.parseInt(in.nextLine()); // Réception de g
            }

            // Génération de la clé privée et calcul de g^a mod p
            int a = (int) (1 + Math.random() * (p - 1));
            int gPuissanceA = expoModulaire(g, a, p);

            if (estServeur) {
                // Serveur reçoit d'abord g^b, puis envoie g^a
                int gPuissanceB = Integer.parseInt(in.nextLine());
                out.println(gPuissanceA);
                // Donnée secrète du serveur
                return expoModulaire(gPuissanceB, a, p);
            } else {
                // Client envoie d'abord g^a, puis reçoit g^b
                out.println(gPuissanceA);
                int gPuissanceB = Integer.parseInt(in.nextLine());
                // Donnée secrète du client
                return expoModulaire(gPuissanceB, a, p);
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la génération de la clé.");
            return 0;
        }
    }

    /**
     * Génère une clé de chiffrement aléatoire en utilisant la donnée
     * secrète.
     * 
     * @param socket     Le socket pour échanger la clé Diffie-Hellman
     * @param estServeur Indique si l'appelant est le serveur
     * @return Une clé de chiffrement aléatoire de longueur fixe
     */
    public static String creationCleChiffrement(Socket socket,
            boolean estServeur) {
        // Obtenir la donnée secrète avec Diffie-Hellman
        int donneeSecrete = methodeDiffieHellman(socket, estServeur);

        if (donneeSecrete == 0) {
            System.err.println("Erreur : La donnée secrète est invalide.");
            return null;
        }

        StringBuilder cleChiffrement = new StringBuilder();

        /*
         * Générer une chaîne de 20 caractères basée sur la donnée
         * secrète
         */
        int valeurCourante = donneeSecrete;
        for (int i = 0; i < 20; i++) {
            // Calculer chaque caractère en fonction de
            // `valeurCourante`
            char caractere = (char) ('a' + (valeurCourante % 26));
            cleChiffrement.append(caractere);

            /*
             * Mise à jour de `valeurCourante` pour le prochain
             * caractère
             */
            valeurCourante = (valeurCourante * 31 + i) % 1000; // Changements
                                                               // pour
                                                               // plus
                                                               // de
                                                               // variété
        }

        return cleChiffrement.toString();
    }

    /**
     * Méthode pour chiffrer un texte avec le chiffrement de Vigenère
     * 
     * @param texte à crypter
     * @param cle   de chiffrement
     * @return le texte crypté
     */
    public static String crypterVigenere(String texte, String cle) {
        int longueurAlphabet = alphabet.length();
        HashMap<Character, Integer> charToIndex = new HashMap<>();
        HashMap<Integer, Character> indexToChar = new HashMap<>();

        /*
         * Remplir les maps charToIndex et indexToChar pour associer
         * chaque caractère de l'alphabet à son indice et inversement
         */
        for (int i = 0; i < longueurAlphabet; i++) {
            charToIndex.put(alphabet.charAt(i), i);
            indexToChar.put(i, alphabet.charAt(i));
        }

        StringBuilder texteCrypte = new StringBuilder();
        int longueurCle = cle.length();
        int indice = 0;

        for (char c : texte.toCharArray()) {
            if (charToIndex.containsKey(c)) {
                // Trouver les positions dans l'alphabet
                int textCharIndex = charToIndex.get(c);
                int keyCharIndex =
                        charToIndex.get(cle.charAt(indice % longueurCle));

                // Calculer l'indice du caractère chiffré
                int indiceCharCrypte =
                        (textCharIndex + keyCharIndex) % longueurAlphabet;
                texteCrypte.append(indexToChar.get(indiceCharCrypte));

                // Avancer dans la clé
                indice++;
            } else {
                /*
                 * Si le caractère n'est pas dans l'alphabet, le
                 * laisser inchangé
                 */
                texteCrypte.append(c);
            }
        }

        return texteCrypte.toString();
    }

    /**
     * Méthode pour déchiffrer un texte avec le chiffrement de
     * Vigenère
     * 
     * @param texte texte à déchiffrer
     * @param cle   clé de (dé)chiffrement
     * @return le texte déchiffré
     */
    public static String decrypterVigenere(String texte, String cle) {
        int longueurAlphabet = alphabet.length();
        StringBuilder resultat = new StringBuilder();
        // Indice pour suivre la position actuelle dans la clé
        int indiceCle = 0;

        // Parcours de chaque caractère du texte à déchiffrer
        for (char c : texte.toCharArray()) {
            // Recherche de l'indice du caractère dans l'alphabet
            int indiceChar = alphabet.indexOf(c);

            // Si le caractère fait partie de l'alphabet
            if (indiceChar >= 0) {
                /*
                 * Trouver l'indice du caractère correspondant dans la
                 * clé. On utilise le modulo pour répéter la clé.
                 */
                int indiceCleAlphabet =
                        alphabet.indexOf(cle.charAt(indiceCle % cle.length()));
                // Calcul du nouvel indice pour le déchiffrement
                int nouveauIndice =
                        (indiceChar - indiceCleAlphabet + longueurAlphabet)
                                % longueurAlphabet;
                resultat.append(alphabet.charAt(nouveauIndice));
                indiceCle++;
            } else {
                /*
                 * Si le caractère ne fait pas partie de l'alphabet,
                 * il est ajouté sans modification
                 */
                resultat.append(c);
            }
        }
        return resultat.toString();
    }

    /**
     * Gère l'exportation d'un fichier texte (envoi ou réception) via
     * un socket réseau. En mode envoi, le fichier spécifié est
     * transféré vers l'adresse IP distante. En mode réception, le
     * fichier est reçu sur le serveur local et sauvegardé sous le nom
     * spécifié.
     * 
     * @param ipDistant        Adresse IP du destinataire pour envoyer
     *                         le fichier. Si null, la méthode se met
     *                         en mode réception.
     * @param fichierAExporter Chemin du fichier à exporter (en mode
     *                         envoi) ou chemin de sauvegarde pour le
     *                         fichier reçu (en mode réception).
     * @param dossierReception Dossier où placer le fichier reçu
     * @throws IOException Si une erreur survient lors de l'envoi ou
     *                     de la réception du fichier.
     */
    public static void exporterFichier(String ipDistant,
            String fichierAExporter,
            String dossierReception)
            throws IOException {
        if (ipDistant != null && fichierAExporter != null) {
            // Validation de l'adresse IP fournie
            if (!validerAdresseIP(ipDistant)) {
                throw new IllegalArgumentException("Adresse IP invalide : "
                        + ipDistant);
            }
            /*
             * Validation de l'existence du fichier et vérification de
             * son extension
             */
            File fichier = new File(fichierAExporter);
            if (!fichier.exists() || !fichier.getName().endsWith(".csv")) {
                throw new IOException("Fichier non trouvé ou non valide (seuls"
                        + " les fichiers CSV sont acceptés) : "
                        + fichierAExporter);
            }

            // Envoi du fichier
            try (Socket socket = new Socket(ipDistant, SERVEUR_PORT);
                    BufferedOutputStream fluxSortie = new BufferedOutputStream(
                            socket.getOutputStream())) {

                /*
                 * Connexion au serveur pour générer la clé de
                 * chiffrement
                 */
                String cleChiffrement;
                cleChiffrement = creationCleChiffrement(socket, false);
                if (cleChiffrement == null) {
                    throw new IOException(
                      "Erreur lors de la génération de la clé de chiffrement.");
                }

                String contenu = new String(
                        java.nio.file.Files.readAllBytes(fichier.toPath()));
                String contenuChiffre =
                        crypterVigenere(contenu, cleChiffrement);

                // Envoi du nom du fichier
                String nomFichier = fichier.getName();
                /*
                 * Conversion en tableau de bytes car les sockets
                 * envoient des données sous forme de bytes
                 */
                fluxSortie.write(nomFichier.getBytes());
                fluxSortie.flush();

                // Envoyer le contenu chiffré
                fluxSortie.write(contenuChiffre.getBytes());
                fluxSortie.flush();
                
            } catch (IOException erreurReception) {
                throw erreurReception;
            }

        } else {
            // Mode réception
            if (serverSocket == null || serverSocket.isClosed()) {
                isRunning = true;
                demarrerServeur(); // Démarrer le serveur
            }

            try (Socket clientSocket = serverSocket.accept();
                    BufferedInputStream fluxEntrant = new BufferedInputStream(
                            clientSocket.getInputStream())) {

                // Générer la clé de chiffrement via Diffie-Hellman
                String cleChiffrement =
                        creationCleChiffrement(clientSocket, true);
                if (cleChiffrement == null) {
                    throw new IOException(
                       "Erreur lors de la génération de la clé de chiffrement.");
                }

                // Lire le nom du fichier
                byte[] nomFichierBuffer = new byte[1024]; // Vérifier
                                                          // que le
                                                          // Buffer
                                                          // est assez
                                                          // grand
                int bytesRead = fluxEntrant.read(nomFichierBuffer);
                String nomFichierRecu = new String(nomFichierBuffer, 0,
                        bytesRead).trim(); // Nom du fichier reçu

                // Créer le nouveau nom pour le fichier reçu
                String nomSansExtension = nomFichierRecu.substring(0,
                        nomFichierRecu.lastIndexOf('.'));
                String nomFinal = nomSansExtension + "_recu.csv";

                byte[] tampon = new byte[1000000];
                int octetsLus;
                StringBuilder contenuRecu = new StringBuilder();

                while ((octetsLus = fluxEntrant.read(tampon)) != -1) {
                    contenuRecu.append(new String(tampon, 0, octetsLus));
                }

                // Déchiffrer le contenu
                String contenuDechiffre =
                        decrypterVigenere(contenuRecu.toString(),
                                cleChiffrement);

                // Sauvegarder le fichier déchiffré
                try (FileOutputStream fluxDestination = new FileOutputStream(
                        new File(dossierReception, nomFinal))) {
                    fluxDestination.write(contenuDechiffre.getBytes());
                }

            } catch (IOException erreurReception) {
                throw erreurReception;
            } finally {
                /*
                 * Indiquer que le serveur n'est plus en cours
                 * d'exécution
                 */
                isRunning = false;
            }
        }
    }

    /**
     * Vérifie si une adresse IP est valide (soit entre 0.0.0.0 et
     * 255.255.255.255)
     * 
     * @param ip L'adresse IP à valider sous forme de chaîne de
     *           caractères (ex. : "192.168.0.1").
     * @return `true` si l'adresse IP est présente sur le serveur,
     *         `false` sinon.
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