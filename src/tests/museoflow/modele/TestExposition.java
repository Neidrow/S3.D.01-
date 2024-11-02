/*
 * TestExposition.java                           2 nov. 2024
 * IUT de Rodez Info2 TPD 2024-2025, pas de copyright 
 */
package tests.museoflow.modele;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import museoflow.modele.Exposition;

/**
 * Tests unitaires de la classe Exposition de museoflow.modele
 * 
 * @author Cylian POUPIN
 */
class TestExposition {

    @Test
    void testConstruireExposition() {
        String[] motsClesTest = { "mot1", "mot2" };
        Exposition exp = new Exposition();

        // On crée une exposition avec des attributs non vides
        assertEquals(true, exp.construireExposition("ID", "Intitulé", "période",
                "période", "période", motsClesTest, "résumé", "date", "date"));

        // On essaye de redéfinir l'exposition crée précédemment avec
        // des valeurs vides
        motsClesTest[0] = "";
        motsClesTest[1] = "";
        assertEquals(false, exp.construireExposition("", "", "", "", "",
                motsClesTest, "", "", ""));

        // On vérifie que la tentative de redéfinition ait été ignorée
        assertEquals("ID", exp.getIdExposition());
        assertEquals("Intitulé", exp.getIntituleExposition());
        assertEquals("période", exp.getPeriodeOeuvreDeb());
        assertEquals("période", exp.getPeriodeOeuvreFin());
        assertEquals("période", exp.getNombreOeuvre());
        assertEquals(motsClesTest, exp.getMotsCles());
        assertEquals("résumé", exp.getResume());
        assertEquals("date", exp.getDateDebutExpo());
        assertEquals("date", exp.getDateFinExpo());
    }
}