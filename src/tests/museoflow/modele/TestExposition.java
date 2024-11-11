/*
 * TestExposition.java                           2 nov. 2024
 * IUT de Rodez Info2 TPD 2024-2025, pas de copyright 
 */
package tests.museoflow.modele;

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
        Exposition exp;

        // TODO tests de la gestion d'erreur

        // On crée une exposition avec des attributs non vides et on
        // l'affecte dans 'exp'
//        assertEquals(true, exp = new Exposition("ID", "Intitulé", "période",
//                "période", "période", motsClesTest, "résumé", "date", "date"));
    }
}