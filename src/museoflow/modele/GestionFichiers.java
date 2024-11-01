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
 * 3.17.0 (néssésaire au fonctionnement d'OpenCSV)<br>
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

    /**
     * Tests manuels
     * 
     * @param args non utilisé
     */
    public static void main(String[] args) {
        System.out.println(importerExpositions(lectureCsv(
                "D:\\Cylian\\OneDrive\\Scolaire\\_SAE\\CSVs nettoyés\\expositions 28_08_24 17_26.csv")));
    }

    // Doit être réutilisé pour chaque type d'objet créé (pas de code
    // dupliqué).
    /**
     * Donne accès à un fichier CSV passé en argument.
     * 
     * @param cheminCSV
     * @return un Reader donnant accès au CSV en argument, null sinon.
     */
    public static Reader lectureCsv(String cheminCSV) {

        try {
            // Création du lecteur de fichiers et conversion du string
            // cheminCSV en objet Path
            return Files.newBufferedReader(Path.of(cheminCSV));

        } catch (IOException e) {
            // Fichier introuvable à l'emplacement indiqué
            System.out.println(
                    "Fichier introuvable à l'emplacement : " + cheminCSV);
            return null;
        }
    }

    // Méthode statique car n'agit pas sur des variables d'instance
    /**
     * Crée les objets Exposition en mémoire à partir des lignes d'un
     * fichier CSV.
     * 
     * @param reader Objet de type Reader donnant l'accès en lecture
     *               au fichier CSV
     * @return true si l'importation a réussi, false sinon.
     */
    public static boolean importerExpositions(Reader reader) {

        // Liste des expositions
        List<Exposition> expositions = new ArrayList<>();

        // Création du lecteur CSV
        CSVReader csvReader;

        // Création de l'analyseur CSV pour changer le délimiteur
        CSVParser csvParser;

        // Instanciation de l'analyseur avec le délimiteur ";"
        csvParser = new CSVParserBuilder().withSeparator(';').build();

        // Instanciation du lecteur CSV avec le délimiteur ";"
        csvReader = new CSVReaderHeaderAwareBuilder(reader)
                .withCSVParser(csvParser).build();

        try {
            // Lecture complète du CSV
            List<String[]> csvLu = new ArrayList<>();
            csvLu = csvReader.readAll();


            // --- Importation des expositions ---

            // On créé les Expositions à l'avance pour pouvoir faire
            // une bloucle pour leur affecter leurs attributs
            // ici, i ira jusqu'au nombre de lignes du CSV
            for (int i = 0; i < csvLu.size(); i++) {
                expositions.add(new Exposition());
            }
            
            // Affectation des attributs aux expositions crées
            // précédemment
            // ici, i ira jusqu'au nombre d'objets Exposition créés
            for (int i = 0; i < expositions.size(); i++) {

                // Supprimer les caractères '#' et séparer par virgule
                // csvLu.get(i))[5] -> Ligne i, colonne 5 (mots clés)
                // du CSV lu
                String[] motsCles =
                        (csvLu.get(i))[5].replace("#", "").split(", ");

                // Affectation aux objets Exposition les attributs lus
                // depuis le CSV
                expositions.get(i).construireExposition(
                        (csvLu.get(i))[0],
                        (csvLu.get(i))[1], 
                        (csvLu.get(i))[2], 
                        (csvLu.get(i))[3],
                        (csvLu.get(i))[4], 
                        motsCles, 
                        (csvLu.get(i))[6], 
                        (csvLu.get(i))[7],
                        (csvLu.get(i))[8]);
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