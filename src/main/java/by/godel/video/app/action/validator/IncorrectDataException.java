package by.godel.video.app.action.validator;
/**
 * Encapsulates exceptions that indicate incoming form data as incorrect.
 */
public class IncorrectDataException extends Exception{

    public IncorrectDataException(String message) {
        super(message);
    }

    public IncorrectDataException(Exception cause) {
        super(cause);
    }

    public IncorrectDataException(String message, Exception cause) {
        super(message, cause);
    }

}
