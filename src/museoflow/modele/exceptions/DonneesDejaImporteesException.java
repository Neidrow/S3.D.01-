/*
 * DonneesDejaImporteesException.java                           15 nov. 2024
 * IUT de Rodez Info2 TPD 2024-2025, pas de copyright 
 */
package museoflow.modele.exceptions;

/**
 * Exception personalisée levée quand on essaye d'importer des données
 * alors que celles-ci ont déja été importées.
 */
public class DonneesDejaImporteesException extends Exception {

    /**
     * ID de sérialisation auto-généré.
     */
    private static final long serialVersionUID = 1540284776926918560L;

    /**
     * Constructs an {@code DonneesDejaImporteesException} with the
     * specified detail message.
     *
     * @param message The detail message (which is saved for later
     *                retrieval by the {@link #getMessage()} method)
     */
    public DonneesDejaImporteesException(String message) {
        super(message);
    }
}
