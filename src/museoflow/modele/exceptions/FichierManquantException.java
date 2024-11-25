/*
 * FichierManquantException.java                           15 nov. 2024
 * IUT de Rodez Info2 TPD 2024-2025, pas de copyright 
 */
package museoflow.modele.exceptions;

/**
 * Exception levée quand on essaye d'accéder à des données qui
 * n'existent pas.
 */
public class FichierManquantException extends Exception {
	
    /** ID de sérialisation auto-généré */
    private static final long serialVersionUID = -876420024718962118L;

    /**
     * Constructs an {@code FichierManquantException} with the
     * specified detail message.
     *
     * @param message The detail message (which is saved for later
     *                retrieval by the {@link #getMessage()} method)
     */
	public FichierManquantException(String message) {
        super(message);
    }
}