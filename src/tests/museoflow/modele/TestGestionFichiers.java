/*
 * TestGestionFichiers.java                           13 nov. 2024
 * IUT de Rodez Info2 TPD 2024-2025, pas de copyright 
 */
package tests.museoflow.modele;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.opencsv.exceptions.CsvException;

import museoflow.modele.GestionFichiers;
import museoflow.modele.exceptions.DonneesDejaImporteesException;
import museoflow.modele.exceptions.HomonymeException;
import museoflow.modele.exceptions.IdentifiantDupliqueException;

/**
 * Tests automatiques de GestionFichiers
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TestGestionFichiers {
    
    // --- Tests de la réimportation des données ---
    @Test
    @Order(1)
    void testReimportationExpositions()
            throws IOException, DonneesDejaImporteesException, CsvException,
            IdentifiantDupliqueException {
        System.out.println("\nImport expositions: ");
        assertTrue(GestionFichiers
                   .importerExpositions(GestionFichiers.lectureCsv(
            "src/museoflow/modele/donneescsv/expositions 28_08_24 17_26.csv")));
        assertThrows(DonneesDejaImporteesException.class, () -> GestionFichiers
                   .importerExpositions(GestionFichiers.lectureCsv(
            "src/museoflow/modele/donneescsv/expositions 28_08_24 17_26.csv")));
    }

    @Test
    @Order(2)
    void testReimportationConferenciers()
            throws CsvException, DonneesDejaImporteesException, IOException,
            HomonymeException, IdentifiantDupliqueException {
        System.out.println("\nImport conférenciers: ");
        assertTrue(GestionFichiers
                   .importerConferenciers(GestionFichiers.lectureCsv(
           "src/museoflow/modele/donneescsv/conferencier 28_08_24 17_26.csv")));
        assertThrows(DonneesDejaImporteesException.class, () -> GestionFichiers
                   .importerConferenciers(GestionFichiers.lectureCsv(
           "src/museoflow/modele/donneescsv/conferencier 28_08_24 17_26.csv")));
    }
    
    @Test
    @Order(3)
    void testReimportationEmployes() throws IOException, CsvException,
            DonneesDejaImporteesException, HomonymeException,
            IdentifiantDupliqueException {
        System.out.println("\nImport employés : ");
        assertTrue(GestionFichiers
                .importerEmployes(GestionFichiers.lectureCsv(
           "src/museoflow/modele/donneescsv/employes 28_08_24 17_26.csv")));
        assertThrows(DonneesDejaImporteesException.class, () -> GestionFichiers
                .importerEmployes(GestionFichiers.lectureCsv(
           "src/museoflow/modele/donneescsv/employes 28_08_24 17_26.csv")));
    }
    
    @Test
    @Order(4)
    void testReimportationVisites()
            throws CsvException, DonneesDejaImporteesException, IOException,
            IdentifiantDupliqueException {
        System.out.println("\n\n\n Test réimportation visites : \n");
        System.out.println("Appel de test importer employés");

        assertTrue(GestionFichiers
                .importerVisites(GestionFichiers.lectureCsv(
                "src/museoflow/modele/donneescsv/visites 28_08_24 17_26.csv")));
        assertThrows(DonneesDejaImporteesException.class, () -> GestionFichiers
                .importerVisites(GestionFichiers.lectureCsv(
                "src/museoflow/modele/donneescsv/visites 28_08_24 17_26.csv")));
    }

    // ---------------------------------------------

    // Vidage des listes pour tester la réimportation avec des CSV ou
    // des identifiants sont dupliqués
    @Test
    @Order(5)
    void testIdDupliqueExpositions()
            throws IOException, DonneesDejaImporteesException, CsvException,
            IdentifiantDupliqueException {
        GestionFichiers.effacerDonneesMemoire();
        assertThrows(IdentifiantDupliqueException.class, () -> GestionFichiers
                .importerExpositions(GestionFichiers.lectureCsv(
                        "src/tests/museoflow/modele/donneescsv/"
                        + "expositionsIncorrectesTest 28_08_24 17_26.csv")));
    }

    @Test
    @Order(6)
    void testIdDupliqueEmployes()
            throws IOException, DonneesDejaImporteesException, CsvException,
            IdentifiantDupliqueException {
        assertThrows(IdentifiantDupliqueException.class, () -> GestionFichiers
                .importerEmployes(GestionFichiers.lectureCsv(
                        "src/tests/museoflow/modele/donneescsv/"
                        + "employesIncorrectsTest 28_08_24 17_26.csv")));
    }

    @Test
    @Order(7)
    void testIdDupliqueConferenciers()
            throws IOException, DonneesDejaImporteesException, CsvException,
            IdentifiantDupliqueException {
        assertThrows(IdentifiantDupliqueException.class, () -> GestionFichiers
                .importerConferenciers(GestionFichiers.lectureCsv(
                        "src/tests/museoflow/modele/donneescsv/"
                        + "conferencierIncorrectTest 28_08_24 17_26.csv")));
    }

    @Test
    @Order(8)
    void testIdDupliqueVisites()
            throws IOException, DonneesDejaImporteesException, CsvException,
            IdentifiantDupliqueException, HomonymeException {
        // On n'a importé aucune des données préamlables
        assertThrows(IllegalStateException.class, () -> GestionFichiers
                .importerVisites(GestionFichiers.lectureCsv(
                        "src/tests/museoflow/modele/donneescsv/"
                        + "visitesIncorrectesTest 28_08_24 17_26.csv")));

        // On importe les données préalables une à une puis on teste
        // l'importation à chaque étape
        System.out.println("\nImport expositions: ");
        assertTrue(GestionFichiers
                .importerExpositions(GestionFichiers.lectureCsv(
                        "src/museoflow/modele/donneescsv"
                        + "/expositions 28_08_24 17_26.csv")));
        assertThrows(IllegalStateException.class, () -> GestionFichiers
                .importerVisites(GestionFichiers.lectureCsv(
                        "src/tests/museoflow/modele/donneescsv/"
                        + "visitesIncorrectesTest 28_08_24 17_26.csv")));

        System.out.println("\nImport conférenciers: ");
        assertTrue(GestionFichiers
                .importerConferenciers(GestionFichiers.lectureCsv(
                        "src/museoflow/modele/donneescsv/"
                        + "conferencier 28_08_24 17_26.csv")));
        assertThrows(IllegalStateException.class, () -> GestionFichiers
                .importerVisites(GestionFichiers.lectureCsv(
                        "src/tests/museoflow/modele/donneescsv/"
                        + "visitesIncorrectesTest 28_08_24 17_26.csv")));

        System.out.println("\nImport employés : ");
        assertTrue(GestionFichiers
                .importerEmployes(GestionFichiers.lectureCsv(
                        "src/museoflow/modele/donneescsv/"
                        + "employes 28_08_24 17_26.csv")));

        // Toutes les données préalables ont été importées, le test
        // des ID dupliqué des possible
        assertThrows(IdentifiantDupliqueException.class, () -> GestionFichiers
                .importerVisites(GestionFichiers.lectureCsv(
                        "src/tests/museoflow/modele/donneescsv/"
                        + "visitesIncorrectesTest 28_08_24 17_26.csv")));
    }
}