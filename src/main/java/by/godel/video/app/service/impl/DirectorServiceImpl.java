package by.godel.video.app.service.impl;
import by.godel.video.app.dao.DaoException;
import by.godel.video.app.dao.DirectorDao;
import by.godel.video.app.dao.sql.DaoSql;
import by.godel.video.app.dao.sql.FactoryDaoSql;
import by.godel.video.app.entity.Director;
import by.godel.video.app.entity.Film;
import by.godel.video.app.entity.VideoProduct;
import by.godel.video.app.entity.validation.validator_film_director.SatisfactionByAccurateFilmCount;
import by.godel.video.app.service.DirectorService;
import by.godel.video.app.service.Service;
import org.apache.logging.log4j.LogManager;
import java.util.ArrayList;
import java.util.List;

public class DirectorServiceImpl extends Service implements DirectorService {
    private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger();

    @Override
    public Director findById(Integer directorId) {
        Director director = new Director();
        DirectorDao dao = null;
        try {
            dao = FactoryDaoSql.getInstance().get(DaoSql.DirectorDao);
            director = dao.readById(directorId);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return director;
    }

    @Override
    public List<Director> findAll() {
        List<Director> directorList = new ArrayList<>();
        DirectorDao dao = null;
        try {
            dao = FactoryDaoSql.getInstance().get(DaoSql.DirectorDao);
            directorList = dao.readAll();
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return directorList;
    }

    @Override
    public Integer save(Director director) {
        Integer idDirectorNew = null;
        DirectorDao dao = null;
        try {
            dao = FactoryDaoSql.getInstance().get(DaoSql.DirectorDao);
            idDirectorNew = dao.insert(director);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return idDirectorNew;
    }

    @Override
    public List<Director> save(Film film, SatisfactionByAccurateFilmCount condition) {
        Integer idFilmNew = null;
        Director directorBD = null;
        List<VideoProduct> filmListWillBeAfterSaving = new ArrayList<>();
        List<Director> directorList = new ArrayList<>();
        List<Director> directorListReturn = new ArrayList<>();
        // Достанем из БД существующие фильмы director (если есть id director -
//        // Подразумевается при наличии id, что director есть в БД)
//        Integer directorID = film.getId_director();
//        if (directorID != null) {
//            FilmServiceImpl filmService = FactoryService.getInstance().get(DaoSql.FilmDao);
//            idFilmNew = filmService.save(film);
////            DirectorDao dao = FactoryDaoSql.getInstance().get(DaoSql.DirectorDao);
////            directorBD = dao.readById(directorID);
//            directorList = findAll();
//            for (int i = 0; i < directorList.size(); i++) {
//                if (condition.satisfy(directorList.get(i), condition.getProductСount())) {
//                    directorListReturn.add(directorList.get(i));
//                }
//            }
//        }
        return directorListReturn;
    }


    @Override
    public void update(Director director) {
        DirectorDao dao = null;
        try {
            dao = FactoryDaoSql.getInstance().get(DaoSql.DirectorDao);
             dao.update(director);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Integer directorId) {
        DirectorDao dao = null;
        try {
            dao = FactoryDaoSql.getInstance().get(DaoSql.DirectorDao);
            dao.delete(directorId);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }
}
