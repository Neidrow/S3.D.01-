/*
 * ValidationException.java                           28 nov. 2024
 * IUT de Rodez Info2 TPD 2024-2025, pas de copyright 
 */
package museoflow.modele.exceptions;


/**
 * Exception personalisée levée quand on essaye de rentrer des dates
 * incohérentes dans les filtres.
 */
public class ValidationException extends RuntimeException {


    /**
     * ID de sérialisation auto-généré.
     */
    private static final long serialVersionUID = -9027020129774739292L;

    /**
     * Constructs an {@code ValidationException} with the
     * specified detail message.
     *
     * @param message The detail message (which is saved for later
     *                retrieval by the {@link #getMessage()} method)
     */
    public ValidationException(String message) {
        super(message);
    }
}


