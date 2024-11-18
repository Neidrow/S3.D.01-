/*
 * IdentifiantDupliqueException.java                           15 nov. 2024
 * IUT de Rodez Info2 TPD 2024-2025, pas de copyright 
 */
package museoflow.modele.exceptions;

/**
 * Exception personalisée levée quand on essaye d'importer des données
 * et que celles-ci contiennent au moins un identifiant dupliqué.
 */
public class IdentifiantDupliqueException extends Exception {

    /**
     * ID de sérialisation auto-généré.
     */
    private static final long serialVersionUID = -4477327359403594247L;

    /**
     * Constructs an {@code IdentifiantDupliqueException} with the
     * specified detail message.
     *
     * @param message The detail message (which is saved for later
     *                retrieval by the {@link #getMessage()} method)
     */
    public IdentifiantDupliqueException(String message) {
        super(message);
    }
}