package HospitalSystem;


/**
 * Exception in case something goes wrong removing an object
 */
public class RemoveException extends Exception {
    private static final long serialVersionUID = 1L;

    public RemoveException(String message) {
        super(message);
    }
}
