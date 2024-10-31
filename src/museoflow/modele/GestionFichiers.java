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
import java.util.Arrays;
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
        System.out.println(importerFichier(
                "D:\\Cylian\\OneDrive\\Scolaire\\_SAE\\CSVs nettoyés\\expositions 28_08_24 17_26.csv"));
    }

    /**
     * Gère l'importation des fichiers CSV : ouverture, lecture,
     * vérification et importation dans leurs objets respectifs.
     * 
     * @param cheminCSV
     * @return true si l'importation a réussi, false sinon.
     */
    public static boolean importerFichier(String cheminCSV) {
        // Déclaration de l'objet lecteur de fichiers
        Reader reader;

        try {
            // Création du lecteur de fichiers et conversion du string
            // cheminCSV en objet Path
            reader = Files.newBufferedReader(Path.of(cheminCSV));

        } catch (IOException e) {
            // Fichier introuvable à l'emplacement indiqué
            System.out.println(
                    "Fichier introuvable à l'emplacement : " + cheminCSV);
            return false;
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

        try {
            // Essai de la lecture complète du CSV
            List<String[]> csvLu = new ArrayList<>();
            csvLu = csvReader.readAll();

            // DEBUG --------------------------------------------
            for (String[] colonne : csvLu) {
                System.out.println(Arrays.toString(colonne));
            }

            System.out.println((csvLu.get(0))[5]);

            // essai affectation objets avec les expositions
            System.out.println("\n\n -----------");

            for (String colonne : csvLu.get(0)) {
                System.out.println(colonne);
            }
            // TODO conversion String mots clés en String[] sur les
            // virgules (possible avec opencsv en lui passant le
            // string ?)
            String[] tableauTest = { "jaaj", "jaaj_2" };

            // Création des objets expositions avec les attributs lus
            // du CSV
            // TODO tableau d'expositions de taille le nombre de
            // lignes du CSV (voir screen)
            Exposition exp1 = new Exposition(
                    ((csvLu.get(0))[0]),
                    ((csvLu.get(0))[1]),
                    ((csvLu.get(0))[2]),
                    ((csvLu.get(0))[3]),
                    ((csvLu.get(0))[4]),
                    tableauTest,
                    ((csvLu.get(0))[5]),
                    ((csvLu.get(0))[6]),
                    ((csvLu.get(0))[7]));
            
            System.out.println("ID de l'expo : " + exp1.getIdExposition());
            // DEBUG --------------------------------------------

            // éléments déja découpés dans les tableaux de
            // string dans l'arraylist -> lecture et préparation
            // faite en même temps
            // TODO l'affectation dans les objets (for automatique
            // qui crée les objets)


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