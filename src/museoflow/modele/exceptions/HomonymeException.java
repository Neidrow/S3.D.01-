/*
 * HomonymeException.java                           15 nov. 2024
 * IUT de Rodez Info2 TPD 2024-2025, pas de copyright 
 */
package museoflow.modele.exceptions;

/**
 * Exception personalisée levée quand on essaye d'importer des données
 * contenant des noms et prénoms et que ceux cis sont dupliqués.
 */
public class HomonymeException extends Exception {

    /** ID de sérialisation auto-généré. */
    private static final long serialVersionUID = -3433440930426263718L;

    /**
     * Constructs an {@code HomonymeException} with the specified
     * detail message.
     *
     * @param message The detail message (which is saved for later
     *                retrieval by the {@link #getMessage()} method)
     */
    public HomonymeException(String message) {
        super(message);
    }
}