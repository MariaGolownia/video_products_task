package by.godel.video.app.dao.pool;

/**
 * This exception can be thrown in case connection pool
 * cannot provide expected result for the caller.
 */
public class ConnectionException extends Exception {

    public ConnectionException(String message) {
        super(message);
    }

    public ConnectionException(Exception cause) {
        super(cause);
    }

    public ConnectionException(String message, Exception cause) {
        super(message, cause);
    }
}
