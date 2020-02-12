package by.godel.video.app.entity;

public class PersistentException extends Exception {

    public PersistentException() {
        super();
    }

    public PersistentException(String str) {
        super(str);
    }

    public PersistentException(String str, Exception ex) {
        super(str, ex);
    }

    public PersistentException(Exception ex) {
        super(ex);
    }

}
