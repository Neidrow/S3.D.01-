/*
 * TestGestionFichiers.java                           13 nov. 2024
 * IUT de Rodez Info2 TPD 2024-2025, pas de copyright 
 */
package tests.museoflow.modele;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.opencsv.exceptions.CsvException;

import museoflow.modele.GestionFichiers;

/**
 * Tests automatiques de GestionFichiers
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TestGestionFichiers {
    
    // --- Tests de la réimportation des données ---
    @Test
    @Order(1)
    void testReimportationExpositions() {
        System.out.println("\nImport expositions: ");
        assertTrue(GestionFichiers
                   .importerExpositions(GestionFichiers.lectureCsv(
            "src/museoflow/modele/donneescsv/expositions 28_08_24 17_26.csv")));
        assertFalse(GestionFichiers
                   .importerExpositions(GestionFichiers.lectureCsv(
            "src/museoflow/modele/donneescsv/expositions 28_08_24 17_26.csv")));
    }

    @Test
    @Order(2)
    void testReimportationConferenciers() {
        System.out.println("\nImport conférenciers: ");
        assertTrue(GestionFichiers
                   .importerConferenciers(GestionFichiers.lectureCsv(
           "src/museoflow/modele/donneescsv/conferencier 28_08_24 17_26.csv")));
        assertFalse(GestionFichiers
                   .importerConferenciers(GestionFichiers.lectureCsv(
           "src/museoflow/modele/donneescsv/conferencier 28_08_24 17_26.csv")));
    }
    
    @Test
    @Order(3)
    void testReimportationEmployes() {
        System.out.println("\nImport employés : ");
        assertTrue(GestionFichiers
                .importerEmployes(GestionFichiers.lectureCsv(
           "src/museoflow/modele/donneescsv/employes 28_08_24 17_26.csv")));
        assertFalse(GestionFichiers
                .importerEmployes(GestionFichiers.lectureCsv(
           "src/museoflow/modele/donneescsv/employes 28_08_24 17_26.csv")));
    }
    
    @Test
    @Order(4)
    void testReimportationVisites() throws CsvException {
        System.out.println("\n\n\n Test réimportation visites : \n");
        System.out.println("Appel de test importer employés");

        assertTrue(GestionFichiers
                .importerVisites(GestionFichiers.lectureCsv(
                        "src/museoflow/modele/donneescsv/visites 28_08_24 17_26.csv")));
        assertFalse(GestionFichiers
                .importerVisites(GestionFichiers.lectureCsv(
                        "src/museoflow/modele/donneescsv/visites 28_08_24 17_26.csv")));
    }

    // ---------------------------------------------

    // TODO tester les identifiants dupliqués
}