package by.godel.video.app.dao;

/**
 * Class processes general Dao exceptions that can be thrown when some kind of error
 * occurred while working with database connection.
 */

public class DaoException extends Exception{

    public DaoException(){
        super();
    };

    public DaoException(String str){
        super(str);
    };

    public DaoException(String str, Exception ex){
        super(str, ex);
    };

    public DaoException(Exception ex){
        super(ex);
    };
}
