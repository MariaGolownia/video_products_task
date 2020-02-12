package by.godel.video.app.service;

import by.godel.video.app.dao.sql.DaoSql;
import by.godel.video.app.service.impl.DirectorServiceImpl;
import by.godel.video.app.service.impl.FilmServiceImpl;
import org.apache.logging.log4j.LogManager;

final public class FactoryService {
    private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger();

    private static FactoryService instance;

    private FactoryService() {
    }

    public static FactoryService getInstance() {
        if (instance == null) {
            synchronized (FactoryService.class) {
                instance = new FactoryService();
            }
        }
        return instance;
    }

    public <Type extends Service> Type get(DaoSql entityDao) {
        switch (entityDao) {
            case DirectorDao:
                return (Type) new DirectorServiceImpl();
            case FilmDao:
                Dao:
                return (Type) new FilmServiceImpl();
            default:
                try {
                    throw new ServiceException("Check the existence of the desired entity! " + entityDao);
                } catch (ServiceException e) {
                    e.printStackTrace();
                }
        }
        return null;
    }
}
