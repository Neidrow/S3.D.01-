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
import java.util.List;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderHeaderAwareBuilder;
import com.opencsv.exceptions.CsvException;

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

    /**
     * Tests manuels
     * 
     * @param args non utilisé
     */
    public static void main(String[] args) {
//        System.out.println("Taille de la liste d'Exposition : " 
//                           + expositions.size());
//        
//        System.out.println("\nRésultat d'éxecution : " 
//                           + importerExpositions(lectureCsv(
//            "src/museoflow/modele/donneescsv/expositions 28_08_24 17_26.csv"))); 
//        System.out.println("Taille de la liste d'Exposition : " 
//                           + expositions.size());
//
//        System.out.println("\nEssai de deuxièmme importation des expositions");
//        System.out.println("\nRésultat d'éxecution : " 
//                           + importerExpositions(lectureCsv(
//            "src/museoflow/modele/donneescsv/expositions 28_08_24 17_26.csv")));
//        System.out.println("Taille de la liste d'Exposition : " 
//                           + expositions.size());
        
// System.out.println("\nRésultat d'éxecution : "
// + importerConferenciers(lectureCsv(
// "src/museoflow/modele/donneescsv/conferencier 28_08_24
// 17_26.csv")));
//
// System.out.println("\nEssai de deuxièmme importation des
// conférenciers");
// System.out.println("\nRésultat d'éxecution : "
// + importerConferenciers(lectureCsv(
// "src/museoflow/modele/donneescsv/expositions 28_08_24
// 17_26.csv")));
// System.out.println("Taille de la liste de Conferencier : "
// + conferenciers.size());

        System.out.println("\nRésultat d'éxecution : "
        + importerEmployes(lectureCsv(
                        "src/museoflow/modele/donneescsv/employes 28_08_24 17_26.csv")));

        System.out.println("\nEssai de deuxièmme importation des employés");
        System.out.println("\nRésultat d'éxecution : "
                + importerEmployes(lectureCsv(
                        "src/museoflow/modele/donneescsv/employes 28_08_24 17_26.csv")));
        System.out.println("Taille de la liste d'Employe : "
                + conferenciers.size());

    }

    // ---
    // Méthodes statiques car n'agit pas sur des variables d'instance

    // Doit être réutilisé pour chaque type d'objet créé (pas de code
    // dupliqué).
    /**
     * Donne accès à un fichier CSV passé en argument.
     * 
     * @param cheminCSV
     * @return un Reader donnant accès au CSV en argument, null sinon.
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

    // TODO vérif si deux employés ont le même nom/prénom
    /**
     * Crée les objets Employe en mémoire à partir des lignes
     * d'un fichier CSV.
     * 
     * @param csvReader Objet de type CSVReader donnant l'accès en
     *                  lecture au fichier CSV
     * @return true si l'importation a réussi, false sinon.
     */
    public static boolean importerEmployes(CSVReader csvReader) {
        try {
            // Lecture complète du CSV
            List<String[]> csvLu = new ArrayList<>();
            csvLu = csvReader.readAll();

            // --- Importation des employés ---

            // Vérification que la liste des employés soit vide,
            // dans le cas contraire l'importation a déja été
            // effectuée
            if (employes.size() == 0) {

                // On créé les Employes à l'avance pour pouvoir
                // faire une bloucle pour leur affecter leurs
                // attributs.
                // ici, i ira jusqu'au nombre de lignes du CSV
                for (int i = 0; i < csvLu.size(); i++) {
                    employes.add(new Employe());
                    
                }
                /*
                 * Affectation des attributs aux employés crées
                 * précédemment. Ici, i ira jusqu'au nombre d'objets
                 * Employe créés. Attention : cas d'arrêt dans le
                 * corps de la boucle -> return false si les
                 * conférenciers ont déja étés importées.
                 */
                for (int i = 0; i < employes.size(); i++) {

                    // Affectation aux objets Exposition les attributs
                    // lus
                    // depuis le CSV et vérification que l'importation
                    // n'ait pas été déja effectuée
                    if (!employes.get(i).construireEmploye(
                            (csvLu.get(i))[0],
                            (csvLu.get(i))[1],
                            (csvLu.get(i))[2],
                            (csvLu.get(i))[3])) {

                        System.out.println("L'importation des employés "
                                + "a déja été effectuée !");
                        return false;
                    }
                }

                // DEBUG --------------------------------------------
                System.out.println(
                        "Taille employés : " + employes.size());

                System.out.println(
                        "\nID de l'emp. 3 : "
                                + employes.get(2).getIdEmploye());
                // DEBUG --------------------------------------------

                csvReader.close();
                return true;

            } else {
                System.out.println("L'importation des employés "
                        + "a déja été effectuée !");
                return false;
            }
            // Eventuelles erreurs de lecture du CSV
        } catch (IOException e) {
            System.out.println("Le CSV a pu être ouvert, "
                    + "mais une erreur est survenue durant la lecture.\n" + e);

        } catch (CsvException e) {
            System.out.println("Le CSV a pu être ouvert, "
                    + "mais un validateur est défaillant\n" + e);
        }
        return false;
    }

    // TODO vérif si deux conféreciers ont le même nom/prénom
    /**
     * Crée les objets Conferencier en mémoire à partir des lignes
     * d'un fichier CSV.
     * 
     * @param csvReader Objet de type CSVReader donnant l'accès en
     *                  lecture au fichier CSV
     * @return true si l'importation a réussi, false sinon.
     */
    public static boolean importerConferenciers(CSVReader csvReader) {
        // true si le conférencier est interne au musée, false s'il
        // est externe.
        boolean employeParMusee;

        try {
            // Lecture complète du CSV
            List<String[]> csvLu = new ArrayList<>();
            csvLu = csvReader.readAll();

            // --- Importation des expositions ---

            // Vérification que la liste des expositions soit vide,
            // dans le cas contraire l'importation a déja été
            // effectuée
            if (conferenciers.size() == 0) {

                // On créé les Conferenciers à l'avance pour pouvoir
                // faire une bloucle pour leur affecter leurs
                // attributs.
                // ici, i ira jusqu'au nombre de lignes du CSV
                for (int i = 0; i < csvLu.size(); i++) {
                    conferenciers.add(new Conferencier());
                }

                /*
                 * Affectation des attributs aux conférenciers crées
                 * précédemment. Ici, i ira jusqu'au nombre d'objets
                 * Conferencier créés. Attention : cas d'arrêt dans le
                 * corps de la boucle -> return false si les
                 * conférenciers ont déja étés importées.
                 */
                for (int i = 0; i < conferenciers.size(); i++) {

                    // Supprimer les caractères '#' et séparer par
                    // virgule
                    // csvLu.get(i))[5] -> Ligne i, colonne 5 (mots
                    // clés)
                    // du CSV lu
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
                    // attributs
                    // lus
                    // depuis le CSV et vérification que l'importation
                    // n'ait pas été déja effectuée
                    if (!conferenciers.get(i).construireConferencier(
                            (csvLu.get(i))[0],
                            (csvLu.get(i))[1],
                            (csvLu.get(i))[2],
                            specialite,
                            (csvLu.get(i))[4],
                            employeParMusee,
                            indisponibilites)) {

                        System.out.println("L'importation des conférenciers "
                                + "a déja été effectuée !");
                        return false;
                    }
                }

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

            } else {
                System.out.println("L'importation des conférenciers "
                        + "a déja été effectuée !");
                return false;
            }
            // Eventuelles erreurs de lecture du CSV
        } catch (IOException e) {
            System.out.println("Le CSV a pu être ouvert, "
                    + "mais une erreur est survenue durant la lecture.\n" + e);

        } catch (CsvException e) {
            System.out.println("Le CSV a pu être ouvert, "
                    + "mais un validateur est défaillant\n" + e);
        }
        return false;
    }


    /**
     * Crée les objets Exposition en mémoire à partir des lignes d'un
     * fichier CSV.
     * 
     * @param csvReader Objet de type CSVReader donnant l'accès en
     *                  lecture au fichier CSV
     * @return true si l'importation a réussi, false sinon.
     */
    public static boolean importerExpositions(CSVReader csvReader) {

        try {
            // Lecture complète du CSV
            List<String[]> csvLu = new ArrayList<>();
            csvLu = csvReader.readAll();


            // --- Importation des expositions ---

            // Vérification que la liste des expositions soit vide,
            // dans le cas contraire l'importation a déja été
            // effectuée
            if (expositions.size() == 0) {

                // On créé les Expositions à l'avance pour pouvoir
                // faire une bloucle pour leur affecter leurs
                // attributs.
                // ici, i ira jusqu'au nombre de lignes du CSV
                for (int i = 0; i < csvLu.size(); i++) {
                    expositions.add(new Exposition());
                }

            } else {
                System.out.println("L'importation des expositions "
                        + "a déja été effectuée !");
                return false;
            }
            
            /*
             * Affectation des attributs aux expositions crées
             * précédemment. 
             * Ici, i ira jusqu'au nombre d'objets Exposition créés.
             * 
             * Attention : cas d'arrêt dans le corps de la boucle 
             * -> return false si les expositions ont déja étés
             *    importées.
             */
            for (int i = 0; i < expositions.size(); i++) {

                // Supprimer les caractères '#' et séparer par virgule
                // csvLu.get(i))[5] -> Ligne i, colonne 5 (mots clés)
                // du CSV lu
                String[] motsCles =
                        (csvLu.get(i))[5].replace("#", "").split(", ");

                // Affectation aux objets Exposition les attributs lus
                // depuis le CSV et vérification que l'importation
                // n'ait pas été déja effectuée
                if (!expositions.get(i).construireExposition(
                        (csvLu.get(i))[0],
                        (csvLu.get(i))[1],
                        (csvLu.get(i))[2],
                        (csvLu.get(i))[3],
                        (csvLu.get(i))[4],
                        motsCles,
                        (csvLu.get(i))[6],
                        (csvLu.get(i))[7],
                        (csvLu.get(i))[8])) {

                    System.out.println("L'importation des expositions "
                                        + "a déja été effectuée !");
                    return false;
                }
            }
            // -------------------------------------
            
            
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
            System.out.println("Le CSV a pu être ouvert, "
                    + "mais une erreur est survenue durant la lecture.\n" + e);

        } catch (CsvException e) {
            System.out.println("Le CSV a pu être ouvert, "
                    + "mais un validateur est défaillant\n" + e);
        }
        return false;
    }
}