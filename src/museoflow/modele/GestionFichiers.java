/*
 * GestionFichiers.java                           23 oct. 2024
 * IUT de Rodez Info2 TPD 2024-2025, pas de copyright 
 */
package museoflow.modele;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderHeaderAwareBuilder;
import com.opencsv.exceptions.CsvException;

import museoflow.modele.exceptions.DonneesDejaImporteesException;
import museoflow.modele.exceptions.HomonymeException;
import museoflow.modele.exceptions.IdentifiantDupliqueException;

/**
 * <p>
 * Gestion des fichiers utilisés par MuesoFlow (à savoir l'importation
 * de fichiers CSV et la gestion des fichiers de sauvegarde)
 * </p>
 * <p>
 * Librairies externes utilisées : OpenCSV 5.9, Apache Commons Lang
 * 3.17.0 (nécessaire au fonctionnement d'OpenCSV)<br>
 * Documentation :
 * <ul>
 * <li>OpenCSV : <a href=
 * "https://javadoc.io/doc/com.opencsv/opencsv/latest/index.html">
 * https://javadoc.io/doc/com.opencsv/opencsv/latest/index.html</a> ;
 * <a href=
 * "https://opencsv.sourceforge.net/">https://opencsv.sourceforge.net/</a></li>
 * <li>Apache Commons Lang :
 * <a href= "https://commons.apache.org/proper/commons-lang/">
 * https://commons.apache.org/proper/commons-lang/</a></li>
 * </ul>
 * </p>
 * 
 * @author Cylian Poupin
 */
public class GestionFichiers {

    /*
     * Variables statiques car doivent être partagées entre toutes les
     * instances de la classe et doivent être consultées sans créer de
     * nouvelle instance de la classe.
     */

    /*
     * Liste des expositions. Visibilité package pour avoir accès aux
     * objets Exposition dans tout le modèle.
     */
    static List<Exposition> expositions = new ArrayList<>();

    /*
     * Liste des conférenciers. Visibilité package pour avoir accès
     * aux objets Conferencier dans tout le modèle.
     */
    static List<Conferencier> conferenciers = new ArrayList<>();

    /*
     * Liste des employés. Visibilité package pour avoir accès aux
     * objets Employe dans tout le modèle.
     */
    static List<Employe> employes = new ArrayList<>();

    /*
     * Liste des visites. Visibilité package pour avoir accès aux
     * objets Visite dans tout le modèle.
     */
    static List<Visite> visites = new ArrayList<>();

    /**
     * Tests manuels
     * 
     * @param args non utilisé
     * @throws CsvException                  Si problème avec les
     *                                       données du CSV
     * @throws DonneesDejaImporteesException
     * @throws IOException
     * @throws HomonymeException
     * @throws IdentifiantDupliqueException
     */
    public static void main(String[] args)
            throws CsvException, IOException, DonneesDejaImporteesException,
            HomonymeException, IdentifiantDupliqueException {
        // Tests manuels importation expositions
        System.out.println("Taille de la liste d'Exposition : "
                + expositions.size());

        System.out.println("\nRésultat d'éxecution : "
                + importerExpositions(lectureCsv(
                        "src/museoflow/modele/donneescsv/expositions 28_08_24 17_26.csv")));
        System.out.println("Taille de la liste d'Exposition : "
                + expositions.size());

        System.out.println("\nEssai de deuxièmme importation des expositions");
        System.out.println("\nRésultat d'éxecution : "
                + importerExpositions(lectureCsv(
                        "src/museoflow/modele/donneescsv/expositions 28_08_24 17_26.csv")));
        System.out.println("Taille de la liste d'Exposition : "
                + expositions.size());
        
     // Tests manuels importation conférenciers 
        System.out.println("\nRésultat d'éxecution : "
                + importerConferenciers(lectureCsv(
                        "src/museoflow/modele/donneescsv/conferencier 28_08_24 17_26.csv")));
        System.out
                .println("\nEssai de deuxièmme importation des conférenciers");
        System.out.println("\nRésultat d'éxecution : "
                + importerConferenciers(lectureCsv(
                        "src/museoflow/modele/donneescsv/conferencier 28_08_24 17_26.csv")));

        System.out.println("Taille de la liste de Conferencier : "
                + conferenciers.size());

        // Tests manuels importation employés
        System.out.println("\nRésultat d'éxecution : "
                + importerEmployes(lectureCsv(
                        "src/museoflow/modele/donneescsv/employes 28_08_24 17_26.csv")));

        System.out.println("\nEssai de deuxièmme importation des employés");
        System.out.println("\nRésultat d'éxecution : "
                + importerEmployes(lectureCsv(
                        "src/museoflow/modele/donneescsv/employes 28_08_24 17_26.csv")));
        System.out.println("Taille de la liste d'Employe : "
                + employes.size());

        // Tests manuels importation visites
        System.out.println("Taille de la liste des visites : "
                + visites.size());

        System.out.println("\nRésultat d'éxecution : "
                + importerVisites(lectureCsv(
                        "src/museoflow/modele/donneescsv/visites 28_08_24 17_26.csv")));
        System.out.println("Taille de la liste visites : "
                + visites.size());

        System.out.println("\nEssai de deuxièmme importation des visites");
        System.out.println("\nRésultat d'éxecution : "
                + importerVisites(lectureCsv(
                        "src/museoflow/modele/donneescsv/visites 28_08_24 17_26.csv")));
        System.out.println("Taille de la liste visites : "
                + visites.size());
    }

    // ---
    // Méthodes statiques car n'agit pas sur des variables d'instance

    // Doit être réutilisé pour chaque type d'objet créé (pas de code
    // dupliqué).
    /**
     * Donne accès à un fichier CSV passé en argument.
     * 
     * @param cheminCSV
     * @return un Reader donnant accès au CSV en argument, null si le
     *         fichier est introuvable ou ne peut pas être ouvert.
     */
    public static CSVReader lectureCsv(String cheminCSV) {

        // Création de l'objet lecteur de fichiers
        Reader reader;

        try {
            // Création du lecteur de fichiers et conversion du string
            // cheminCSV en objet Path
            reader = Files.newBufferedReader(Path.of(cheminCSV));

        } catch (IOException e) {
            // Fichier introuvable à l'emplacement indiqué
            System.out.println(
                    "Fichier introuvable à l'emplacement : " + cheminCSV);
            return null;
        }

        // Création du lecteur CSV
        CSVReader csvReader;

        // Création de l'analyseur CSV pour changer le délimiteur
        CSVParser csvParser;

        // Instanciation de l'analyseur avec le délimiteur ";"
        csvParser = new CSVParserBuilder().withSeparator(';').build();

        // Instanciation du lecteur CSV avec le délimiteur ";"
        csvReader = new CSVReaderHeaderAwareBuilder(reader)
                .withCSVParser(csvParser).build();

        return csvReader;
    }

    // TODO on throw des exception avec messages custom
    // qui sont catch dans le controleur et un message explicite est
    // affiché

    /**
     * Crée les objets Employe en mémoire à partir des lignes d'un
     * fichier CSV.
     * 
     * @param csvReader Objet de type CSVReader donnant l'accès en
     *                  lecture au fichier CSV
     * @return true si l'importation a réussi, sinon une exception
     *         détaillant l'erreur d'importation est levée.
     * @throws IOException                   Si le CSV a pu être
     *                                       ouvert, mais une erreur
     *                                       est survenue durant la
     *                                       lecture
     * @throws CsvException                  Si le CSV a pu être
     *                                       ouvert, mais un
     *                                       validateur est
     *                                       défaillant.
     * @throws DonneesDejaImporteesException Si on essaye de
     *                                       ré-importer des données
     *                                       sans effacer les donénes
     *                                       précédentes au préalable.
     * @throws HomonymeException             Si des employés ont le
     *                                       même nom et prénom.
     * @throws IdentifiantDupliqueException  Si un identifiant
     *                                       dupliqué est trouvé dans
     *                                       un CSV importé.
     */
    public static boolean importerEmployes(CSVReader csvReader)
            throws IOException, 
                   CsvException, 
                   DonneesDejaImporteesException,
                   HomonymeException, 
                   IdentifiantDupliqueException {
        // Vérification que la liste des employés soit vide,
        // dans le cas contraire l'importation a déja été
        // effectuée
        if (employes.size() == 0) {
            try {
                // Lecture complète du CSV
                List<String[]> csvLu = new ArrayList<>();
                csvLu = csvReader.readAll();

                // --- Importation des employés ---
                /*
                 * Affectation des attributs aux employés crées
                 * précédemment.
                 */
                for (int i = 0; i < csvLu.size(); i++) {
                    // Affectation aux objets Employe les attributs
                    // lus depuis le CSV
                    employes.add(new Employe((csvLu.get(i))[0],
                            (csvLu.get(i))[1],
                            (csvLu.get(i))[2],
                            (csvLu.get(i))[3]));
                }

                // -------------------------------------
                // --- Vérification des données ---

                // Si un identifiant est dupliqué
                HashSet<String> ids = new HashSet<>();

                // Si l'ajout d'un idEmployé au HashSet échoue
                // (c'est-à-dire que l'élément est déjà présent), cela
                // signifie qu'il y a un doublon.
                for (int i = 0; i < employes.size(); i++) {
                    if (!ids.add(employes.get(i).getIdEmploye())) {
                        System.out.println("Données des employés incorrectes "
                                            + "(données dupliquées), "
                                            + "vidage de la liste !");
                        /*
                         * Des données dupliquées ont étés détectées ;
                         * on annule l'importation. Il faut donc vider
                         * la liste des employés sinon une autre
                         * tentative d'importation sera refusée et les
                         * données incorrectes resteront en mémoire.
                         */
                        employes.clear();
                        throw new IdentifiantDupliqueException(
                                "Identifiant de l'employé ligne " + (i + 2)
                                        + " du CSV dupliqué");
                    }
                }

                // S'il y a homonyme sur le nom et le prénom d'un
                // employé
                HashSet<String> nomPrenom = new HashSet<>();

                // Si l'ajout d'un nom + prenom au HashSet échoue
                // (c'est-à-dire que l'élément est déjà présent), cela
                // signifie qu'il y a un doublon.
                for (int i = 0; i < employes.size(); i++) {
                    if (!nomPrenom.add(employes.get(i).getNomEmploye()
                                       + employes.get(i).getNomEmploye())) {
                        System.out.println("Données des employés incorrectes "
                                + "(homonyme Nom Prénom), "
                                + "vidage de la liste !");
                        /*
                         * Des données dupliquées ont étés détectées ;
                         * on annule l'importation. Il faut donc vider
                         * la liste des employés sinon une autre
                         * tentative d'importation sera refusée et les
                         * données incorrectes resteront en mémoire.
                         */
                        employes.clear();
                        throw new HomonymeException(
                                "Il y a homonyme sur l'employé ligne" + (i + 2)
                                        + "du CSV");
                    }
                }
                // ----------------------------------

                // DEBUG --------------------------------------------
                System.out.println(
                        "Taille employés : " + employes.size());

                System.out.println(
                        "\nID de l'emp. 3 : "
                                + employes.get(2).getIdEmploye());
                // DEBUG --------------------------------------------

                csvReader.close();
                return true;

                // Eventuelles erreurs de lecture du CSV
            } catch (IOException e) {
                throw new IOException("Le CSV des employés a pu être ouvert, "
                        + "mais une erreur est survenue durant la lecture.");

            } catch (CsvException e) {
                throw new CsvException("Le CSV des employés a pu être ouvert, "
                        + "mais un validateur est défaillant.");
            }
        } else {
            throw new DonneesDejaImporteesException(
                    "Employés déjà importés ! Demande d'import ignorée.");
        }
    }


    /**
     * Crée les objets Conferencier en mémoire à partir des lignes
     * d'un fichier CSV.
     * 
     * @param csvReader Objet de type CSVReader donnant l'accès en
     *                  lecture au fichier CSV
     * @return true si l'importation a réussi, sinon une exception
     *         détaillant l'erreur d'importation est levée.
     * @throws IOException                   Si le CSV a pu être
     *                                       ouvert, mais une erreur
     *                                       est survenue durant la
     *                                       lecture
     * @throws CsvException                  Si le CSV a pu être
     *                                       ouvert, mais un
     *                                       validateur est
     *                                       défaillant.
     * @throws DonneesDejaImporteesException Si on essaye de
     *                                       ré-importer des données
     *                                       sans effacer les donénes
     *                                       précédentes au préalable.
     * @throws HomonymeException             Si des employés ont le
     *                                       même nom et prénom.
     * @throws IdentifiantDupliqueException  Si un identifiant
     *                                       dupliqué est trouvé dans
     *                                       un CSV importé.
     */
    public static boolean importerConferenciers(CSVReader csvReader)
            throws CsvException, 
                   DonneesDejaImporteesException, 
                   IOException,
                   HomonymeException, 
                   IdentifiantDupliqueException {
        // Vérification que la liste des conférenciers soit vide,
        // dans le cas contraire l'importation a déja été
        // effectuée
        if (conferenciers.size() == 0) {
            // true si le conférencier est interne au musée, false
            // s'il est externe.
            boolean employeParMusee;
            try {
                // Lecture complète du CSV
                List<String[]> csvLu = new ArrayList<>();
                csvLu = csvReader.readAll();

                // --- Importation des conférenciers ---
                /*
                 * Affectation des attributs aux conférenciers crées
                 * précédemment.
                 */
                for (int i = 0; i < csvLu.size(); i++) {

                    // Supprimer les caractères '#' et séparer par
                    // virgule csvLu.get(i))[3] -> Ligne i, colonne 3
                    // (mots clés) du CSV lu
                    String[] specialite =
                            (csvLu.get(i))[3].replace("#", "").split(", ");

                    // Conversion de "oui" ou "non" en booléin
                    if ((csvLu.get(i))[5].equals("oui")) {
                        employeParMusee = true;
                    } else {
                        employeParMusee = false;
                    }

                    // Liste des indisponibilités des conférenciers
                    List<String> indisponibilites = new ArrayList<>();

                    /*
                     * Affectation des indisponibilités dans une
                     * liste. Ici, j démmare à 6 car 6 est la première
                     * colonne ou commencent les indisponibilités,
                     * jusqu'à la dernière colonne du CSV.
                     */
                    for (int j = 6; j < csvLu.get(i).length; j++) {
                        indisponibilites.add(csvLu.get(i)[j]);
                    }

                    // Affectation aux objets Conferencier les
                    // attributs lus depuis le CSV et vérification que
                    // l'importation n'ait pas été déja effectuée.
                    conferenciers.add(new Conferencier((csvLu.get(i))[0],
                                                       (csvLu.get(i))[1],
                                                       (csvLu.get(i))[2],
                                                       specialite,
                                                       (csvLu.get(i))[4],
                                                       employeParMusee,
                                                       indisponibilites));
                }

                // -------------------------------------
                // --- Vérification des données ---

                // Si un identifiant est dupliqué
                HashSet<String> ids = new HashSet<>();

                // Si l'ajout d'un idConférencier au HashSet échoue
                // (c'est-à-dire que l'élément est déjà présent), cela
                // signifie qu'il y a un doublon.
                for (int i = 0; i < conferenciers.size(); i++) {
                    if (!ids.add(conferenciers.get(i).getIdConferencier())) {
                        System.out.println(
                                "Données des conférenciers incorrectes "
                                + "(données dupliquées), "
                                + "vidage de la liste !");
                        /*
                         * Des données dupliquées ont étés détectées ;
                         * on annule l'importation. Il faut donc vider
                         * la liste des conférenciers sinon une autre
                         * tentative d'importation sera refusée et les
                         * données incorrectes resteront en mémoire.
                         */
                        conferenciers.clear();
                        throw new IdentifiantDupliqueException(
                                "Identifiant du conférencier ligne " + (i + 2)
                                        + " du CSV dupliqué");
                    }
                }

                // S'il y a homonyme sur le nom et le prénom d'un
                // employé
                HashSet<String> nomPrenom = new HashSet<>();

                // Si l'ajout d'un nom + prenom au HashSet échoue
                // (c'est-à-dire que l'élément est déjà présent), cela
                // signifie qu'il y a un doublon.
                for (int i = 0; i < employes.size(); i++) {
                    if (!nomPrenom.add(employes.get(i).getNomEmploye()
                            + employes.get(i).getPrenomEmploye())) {
                        System.out.println("Données des employés incorrectes "
                                + "(homonyme Nom Prénom), "
                                + "vidage de la liste !");
                        /*
                         * Des données dupliquées ont étés détectées ;
                         * on annule l'importation. Il faut donc vider
                         * la liste des employés sinon une autre
                         * tentative d'importation sera refusée et les
                         * données incorrectes resteront en mémoire.
                         */
                        employes.clear();
                        throw new HomonymeException(
                                "Il y a homonyme sur l'employé ligne" + (i + 2)
                                        + "du CSV");
                    }
                }
                // ----------------------------------

                // DEBUG --------------------------------------------
                System.out.println(
                        "Taille conferencier : " + conferenciers.size());
                System.out.println("Indisponibilités du conférencier 3 : "
                        + (conferenciers.get(2).getIndisponibilites())
                                .toString());

                System.out.println(
                        "\nID du conf. 4 : "
                                + conferenciers.get(3).getIdConferencier());
                // DEBUG --------------------------------------------

                csvReader.close();
                return true;

                // Eventuelles erreurs de lecture du CSV
            } catch (IOException e) {
                throw new IOException(
                        "Le CSV des conférenciers a pu être ouvert, "
                        + "mais une erreur est survenue durant la lecture.");

            } catch (CsvException e) {
                throw new CsvException(
                        "Le CSV des conférenciers a pu être ouvert, "
                        + "mais un validateur est défaillant.");
            }
        } else {
            throw new DonneesDejaImporteesException(
                    "Employés déjà importés ! \n Demande d'import ignorée.");
        }
    }


    /**
     * Crée les objets Exposition en mémoire à partir des lignes d'un
     * fichier CSV.
     * 
     * @param csvReader Objet de type CSVReader donnant l'accès en
     *                  lecture au fichier CSV
     * @return true si l'importation a réussi, sinon une exception
     *         détaillant l'erreur d'importation est levée.
     * @throws IOException                   Si le CSV a pu être
     *                                       ouvert, mais une erreur
     *                                       est survenue durant la
     *                                       lecture
     * @throws CsvException                  Si le CSV a pu être
     *                                       ouvert, mais un
     *                                       validateur est
     *                                       défaillant.
     * @throws DonneesDejaImporteesException Si on essaye de
     *                                       ré-importer des données
     *                                       sans effacer les donénes
     *                                       précédentes au préalable.
     * @throws IdentifiantDupliqueException  Si un identifiant
     *                                       dupliqué est trouvé dans
     *                                       un CSV importé.
     */
    public static boolean importerExpositions(CSVReader csvReader)
            throws IOException, 
                   DonneesDejaImporteesException, 
                   CsvException,
                   IdentifiantDupliqueException {
        // Vérification que la liste des expositions soit vide,
        // dans le cas contraire l'importation a déja été
        // effectuée
        if (expositions.size() == 0) {
            try {
                // Lecture complète du CSV
                List<String[]> csvLu = new ArrayList<>();
                csvLu = csvReader.readAll();

                // --- Importation des expositions ---
                /*
                 * Affectation des attributs aux expositions
                 */
                for (int i = 0; i < csvLu.size(); i++) {

                    // Supprimer les caractères '#' et séparer par
                    // virgule csvLu.get(i))[5] -> Ligne i, colonne 5
                    // (mots clés) du CSV lu.
                    String[] motsCles =
                            (csvLu.get(i))[5].replace("#", "").split(", ");

                    // Affectation aux objets Exposition les attributs
                    // lus
                    // depuis le CSV
                    expositions.add(new Exposition(
                            (csvLu.get(i))[0],
                            (csvLu.get(i))[1],
                            (csvLu.get(i))[2],
                            (csvLu.get(i))[3],
                            (csvLu.get(i))[4],
                            motsCles,
                            (csvLu.get(i))[6],
                            (csvLu.get(i))[7],
                            (csvLu.get(i))[8]));
                }
                // -------------------------------------

                // -------------------------------------
                // --- Vérification des données ---

                // Si un identifiant est dupliqué
                HashSet<String> ids = new HashSet<>();

                // Si l'ajout d'un idExposition au HashSet échoue
                // (c'est-à-dire que l'élément est déjà présent), cela
                // signifie qu'il y a un doublon.
                for (int i = 0; i < expositions.size(); i++) {
                    if (!ids.add(expositions.get(i).getIdExposition())) {
                        System.out.println("Données des exposition incorrectes "
                                + "(données dupliquées), "
                                + "vidage de la liste !");
                        /*
                         * Des données dupliquées ont étés détectées ;
                         * on annule l'importation. Il faut donc vider
                         * la liste des expositions sinon une autre
                         * tentative d'importation sera refusée et les
                         * données incorrectes resteront en mémoire.
                         */
                        expositions.clear();
                        throw new IdentifiantDupliqueException(
                                "Identifiant de l'exposition ligne " + (i + 2)
                                        + " du CSV dupliqué");
                    }
                }
                // ----------------------------------

                // DEBUG --------------------------------------------
                System.out.println(
                        "\nID de l'expo 4 : "
                                + expositions.get(3).getIdExposition());

                System.out.println("\nMots clés de l'expo 4 : ");
                String[] mots = expositions.get(3).getMotsCles();
                for (String motsIndividuels : mots) {
                    System.out.println(motsIndividuels);
                }
                // DEBUG --------------------------------------------

                csvReader.close();
                return true;

                // Eventuelles erreurs de lecture du CSV
            } catch (IOException e) {
                throw new IOException(
                        "Le CSV des expositions a pu être ouvert, "
                        + "mais une erreur est survenue durant la lecture.");

            } catch (CsvException e) {
                throw new CsvException(
                        "Le CSV des expositions a pu être ouvert, "
                        + "mais un validateur est défaillant.");
            }
        } else {
            throw new DonneesDejaImporteesException(
                    "Employés déjà importés ! \n Demande d'import ignorée.");
        }
    }

    /**
     * Crée les objets Visite en mémoire à partir des lignes d'un
     * fichier CSV.
     * 
     * @param csvReader Objet de type CSVReader donnant l'accès en
     *                  lecture au fichier CSV
     * @return true si l'importation a réussi, sinon une exception
     *         détaillant l'erreur d'importation est levée.
     * @throws IOException                   Si le CSV a pu être
     *                                       ouvert, mais une erreur
     *                                       est survenue durant la
     *                                       lecture
     * @throws CsvException                  Si le CSV a pu être
     *                                       ouvert, mais un
     *                                       validateur est
     *                                       défaillant.
     * @throws DonneesDejaImporteesException Si on essaye de
     *                                       ré-importer des données
     *                                       sans effacer les donénes
     *                                       précédentes au préalable.
     * @throws IdentifiantDupliqueException  Si un identifiant
     *                                       dupliqué est trouvé dans
     *                                       un CSV importé.
     */
    public static boolean importerVisites(CSVReader csvReader)
            throws CsvException, 
                   DonneesDejaImporteesException, 
                   IOException,
                   IdentifiantDupliqueException {
        // Vérification que la liste des visites soit vide,
        // dans le cas contraire l'importation a déja été
        // effectuée
        if (visites.size() == 0) {
            try {
                // Lecture complète du CSV
                List<String[]> csvLu = new ArrayList<>();
                csvLu = csvReader.readAll();

                // --- Importation des visites ---
                /*
                 * Affectation des attributs aux visites
                 */
                for (int i = 0; i < csvLu.size(); i++) {

                    // Affectation aux objets Visite les attributs
                    // lus depuis le CSV
                    visites.add(new Visite(
                            (csvLu.get(i))[0],
                            (csvLu.get(i))[1],
                            (csvLu.get(i))[2],
                            (csvLu.get(i))[3],
                            (csvLu.get(i))[4],
                            (csvLu.get(i))[5],
                            (csvLu.get(i))[6],
                            (csvLu.get(i))[7]));
                }
                // -------------------------------------
                // --- Vérification des données ---

                // Si un identifiant est dupliqué
                HashSet<String> ids = new HashSet<>();

                // Si l'ajout d'un idVisite au HashSet échoue
                // (c'est-à-dire que l'élément est déjà présent), cela
                // signifie qu'il y a un doublon.
                for (int i = 0; i < visites.size(); i++) {
                    if (!ids.add(visites.get(i).getIdVisite())) {
                        System.out.println("Données des visites incorrectes "
                                + "(données dupliquées), "
                                + "vidage de la liste !");
                        /*
                         * Des données dupliquées ont étés détectées ;
                         * on annule l'importation. Il faut donc vider
                         * la liste des visites sinon une autre
                         * tentative d'importation sera refusée et les
                         * données incorrectes resteront en mémoire.
                         */
                        visites.clear();
                        throw new IdentifiantDupliqueException(
                                "Identifiant de la visite ligne " + (i + 2)
                                        + " du CSV dupliqué");
                    }
                }


                // ----------------------------------

                // DEBUG --------------------------------------------
                System.out.println(
                        "\nID de la visite 4 : "
                                + visites.get(3).getIdVisite());
                // DEBUG --------------------------------------------

                csvReader.close();
                return true;

                // Eventuelles erreurs de lecture du CSV
            } catch (IOException e) {
                throw new IOException("Le CSV des visites a pu être ouvert, "
                        + "mais une erreur est survenue durant la lecture.");

            } catch (CsvException e) {
                throw new CsvException("Le CSV des visites a pu être ouvert, "
                        + "mais un validateur est défaillant.");
            }
        } else {
            throw new DonneesDejaImporteesException(
                    "Employés déjà importés ! \n Demande d'import ignorée.");
        }
    }

    /**
     * Efface les données importées en mémoire depuis les CSV pour
     * pouvoir importer de nouvelles données.
     */
    // Vide les listes d'objets expositions, employes, conferenciers
    // et visites. Les objets précédemment créés seront déréférencés
    // et effacés par le garbage collector de la JVM.
    public static void effacerDonneesMemoire() {
        expositions.clear();
        System.out.println("Liste expositions vidée");
        conferenciers.clear();
        System.out.println("Liste conférenciers vidée");
        employes.clear();
        System.out.println("Liste employés vidée");
        visites.clear();
        System.out.println("Liste visites vidée");
    }
}