package museoflow.modele;

/**
 * Exception en cas d'homonyme
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
