package by.godel.video.app.dao.sql;
import by.godel.video.app.dao.Dao;
import by.godel.video.app.dao.DaoException;
import by.godel.video.app.dao.pool.ConnectionException;
import by.godel.video.app.dao.pool.ConnectionPool;
import by.godel.video.app.dao.pool.ConnectionSQL;
import org.apache.logging.log4j.LogManager;
import java.sql.Connection;
import java.sql.SQLException;

final public class FactoryDaoSql {
    private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger();
    private static final FactoryDaoSql instance = new FactoryDaoSql();
    private Connection connection;

    public static FactoryDaoSql getInstance(){
        return instance;
    }

    private FactoryDaoSql(){
        if (this.connection == null) {
            ConnectionSQL connectionSQL = new ConnectionSQL();
            connection = connectionSQL.getConnectionToDB();
        }
    }


    public <Type extends Dao<?>> Type get (DaoSql entityDao) throws DaoException {
        switch (entityDao) {
            case DirectorDao:
                return (Type) new DirectorDaoSql(connection);
            case FilmDao:
                return (Type)new FilmDaoSql(connection);
            default:
                throw new DaoException("Check the existence of the desired entity! " + entityDao);
        }
    }

    public void closeConnection () {
        try {
            this.connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
