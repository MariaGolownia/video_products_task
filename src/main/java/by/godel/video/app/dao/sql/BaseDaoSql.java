package by.godel.video.app.dao.sql;
import java.sql.Connection;

public class BaseDaoSql {

    protected Connection connection;

    public BaseDaoSql(Connection connection) {
        this.connection = connection;
    }

    protected BaseDaoSql() {
    }
}
