package by.godel.video.app.service.impl;
import by.godel.video.app.dao.DaoException;
import by.godel.video.app.dao.FilmDao;
import by.godel.video.app.dao.sql.DaoSql;
import by.godel.video.app.dao.sql.FactoryDaoSql;
import by.godel.video.app.entity.Director;
import by.godel.video.app.entity.Film;
import by.godel.video.app.entity.VideoProduct;
import by.godel.video.app.entity.entityStatus.DirectorStatus;
import by.godel.video.app.entity.validation.validator_film_director.*;
import by.godel.video.app.service.FactoryService;
import by.godel.video.app.service.FilmService;
import by.godel.video.app.service.Service;
import by.godel.video.app.service.ServiceException;
import org.apache.logging.log4j.LogManager;
import java.util.ArrayList;
import java.util.List;

public class FilmServiceImpl extends Service implements FilmService {
    private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger();

    public static final Boolean RESULT_IF_SUCCESS = true;
    public static final Boolean RESULT_IF_NOT_SUCCESS = false;

    public static final Boolean RESULT_IF_CONDITION_MATCHES = true;


    @Override
    public VideoProduct findById(Integer filmId) {
        VideoProduct videoProduct = new Film();
        FilmDao dao = null;
        try {
            dao = FactoryDaoSql.getInstance().get(DaoSql.FilmDao);
            videoProduct = dao.readById(filmId);
        } catch (DaoException e) {
            logger.error("Error of finding from table `film`. Id of film is: " +  filmId);
            e.printStackTrace();
        }
        return videoProduct;
    }

    @Override
    public List<VideoProduct> findByIdDirecror(Integer directorId) {
        List<VideoProduct> videoProducts = new ArrayList<>();
        FilmDao dao = null;
        try {
            dao = FactoryDaoSql.getInstance().get(DaoSql.FilmDao);
            videoProducts = dao.readByIdDirector(directorId);
        } catch (DaoException e) {
            logger.error("Error of finding from table `film`. Id of director is: " +  directorId);
            e.printStackTrace();
        }
        return videoProducts;
    }

    @Override
    public List<Film> findAll() {
        return null;
    }

// Данный метод закомментирован, так как запрещено вставлять в БД фильм без его привязки к конкретному режисеру
// В случае ввода данной возможности - разблокировать
//    @Override
//    public Integer insert(Film film) {
//        Integer idFilmNew = null;
//        FilmDao dao = null;
//        try {
//            dao = FactoryDaoSql.getInstance().get(DaoSql.FilmDao);
//            idFilmNew = dao.insert(film);
//        } catch (DaoException e) {
//            e.printStackTrace();
//        }
//        return idFilmNew;
//    }


    @Override
    public Integer insert(Film film, Director director) {
        Integer idFilmNew = null;
        FilmDao dao = null;
        try {
            dao = FactoryDaoSql.getInstance().get(DaoSql.FilmDao);
            idFilmNew = dao.insert(film, director);
        } catch (DaoException e) {
            logger.error("Error of inserting to table `film`." +
                    " Film is: " +  film.toString() + ". Director is: " + director.toString()+ ". ");
            e.printStackTrace();
        }
        return idFilmNew;
    }


    // Метод обработки общих условий вставки (включая в Condition данные data base)
    public List<DirectorStatus> insertConditionWithDB(List<VideoProduct> videoProductList,
                                                      SatisfactionDirectorFilm condition, Boolean ifMatchesDo) throws ServiceException {
        List<DirectorStatus> directorStatusList = new ArrayList<>();
        DirectorStatus directorStatus = null;
        if (videoProductList != null) {
            Director director = checkIfVideoProductOfOneDirector(videoProductList, condition.getDirector());
            if (director != null) {
                Boolean result = false;
                director.addVideoProductList(videoProductList);
                condition.setDirector(director);
                result = condition.satisfy();
                for (int i = 0; i < videoProductList.size(); i++) {
                    VideoProduct videoProductFromList = videoProductList.get(i);
                    if (videoProductFromList instanceof Film) {
                        Film film = (Film) videoProductFromList;
                        film.setId_director(condition.getDirector().getId());
                        if (result.equals(true) && result.equals(ifMatchesDo)) {
                            insert(film, director);
                            directorStatus = new DirectorStatus(director, film, RESULT_IF_SUCCESS);
                            directorStatusList.add(directorStatus);
                        } else {
                            directorStatus = new DirectorStatus(director, film, RESULT_IF_NOT_SUCCESS);
                            directorStatusList.add(directorStatus);
                        }
                    } else {
                        throw new ServiceException("The DB insertion method is called for non-matching entities!" +
                                " The method is waiting for \"Film\", and the input is different.");
                    }
                }
            }
        } else {
            throw new ServiceException("The list of video products is empty!");
        }
        return directorStatusList;
    }


    @Override
    public List<DirectorStatus> insertConditionOnlyDB(List<VideoProduct> videoProductList,
                                                      SatisfactionDirectorFilm condition, Boolean ifMatchesDo) throws ServiceException {
        List<DirectorStatus> directorStatusList = new ArrayList<>();
        DirectorStatus directorStatus = null;
        if (videoProductList != null) {
            Director director = checkIfVideoProductOfOneDirector(videoProductList, condition.getDirector());
            if (director != null) {
                Boolean result = false;
                condition.setDirector(director);
                result = condition.satisfy();
                for (int i = 0; i < videoProductList.size(); i++) {
                    VideoProduct videoProductFromList = videoProductList.get(i);
                    if (videoProductFromList instanceof Film) {
                        Film film = (Film) videoProductFromList;
                        film.setId_director(condition.getDirector().getId());
                        if (result.equals(true) && result.equals(ifMatchesDo)) {
                            insert(film, director);
                            directorStatus = new DirectorStatus(director, film, RESULT_IF_SUCCESS);
                            directorStatusList.add(directorStatus);
                        } else {
                            directorStatus = new DirectorStatus(director, film, RESULT_IF_NOT_SUCCESS);
                            directorStatusList.add(directorStatus);
                        }
                    } else {
                        throw new ServiceException("The DB insertion method is called for non-matching entities!" +
                                " The method is waiting for \"Film\", and the input is different.");
                    }
                }
            }
        } else {
            throw new ServiceException("The list of video products is empty!");
        }
        return directorStatusList;

    }

    @Override
    public Director checkIfVideoProductOfOneDirector(List<VideoProduct> videoProductList, Director director) throws ServiceException {
        if (!videoProductList.equals(null) || videoProductList != null) {
            Integer dircetorID = director.getId();
            Boolean flag = false;
            for (int i = 0; i < videoProductList.size(); i++) {
                if (videoProductList.get(i).getId_director() != null) {
                    flag = true;
                    if (!videoProductList.get(i).getId_director().equals(dircetorID)) {
                        throw new ServiceException("Films of one director are accepted for insertion!");
                    }
                    director = checkIfDiretorIdExists(videoProductList.get(0));
                } else {
                    if (flag)
                        throw new ServiceException("Check the links of each movie in the list with the director's id!");
                }
            }
        }
        return director;
    }

    @Override
    public Director checkIfDiretorIdExists(VideoProduct videoProduct) throws ServiceException {
        Integer dircetorID = videoProduct.getId_director();
        Director directorDB = null;
        if (dircetorID == null) {
            throw new ServiceException("Before processing the movie insertion operation," +
                    " please enter data about the unique identifier (id) of the director!");
        }
        DirectorServiceImpl directorService = FactoryService.getInstance().get(DaoSql.DirectorDao);
        directorDB = directorService.findById(dircetorID);
        if (directorDB == null) {
            throw new ServiceException("Before processing the movie insertion operation," +
                    " please enter the information about the director! " +
                    "The specified director Id does not exist in the database!");
        }
        return directorDB;
    }

    @Override
    public Director checkIfDiretorExists(Director director) throws ServiceException {
        Director directorDB = null;
        if (director == null) {
            throw new ServiceException("Before processing the movie insertion operation," +
                    " please enter data of the director!");
        }
        Integer dircetorID = director.getId();
        DirectorServiceImpl directorService = FactoryService.getInstance().get(DaoSql.DirectorDao);
        directorDB = directorService.findById(dircetorID);
        if (directorDB.getId() == null) {
            throw new ServiceException("Before processing the movie insertion operation," +
                    " please enter the information about the director! " +
                    "The specified director Id does not exist in the database!");
        }
        return directorDB;
    }

    // Метод обработки общих условий вставки (не включая данные data base)
    @Override
    public List<DirectorStatus> insertConditionWithoutDB
    (List<VideoProduct> videoProductList, SatisfactionDirectorFilm condition, Boolean ifMatchesDo) throws ServiceException {
        Director directorFromCondition = condition.getDirector();
        directorFromCondition = checkIfDiretorExists(directorFromCondition);
        List<DirectorStatus> directorStatusList = new ArrayList<>();
        DirectorStatus directorStatus = null;
        Director director = checkIfVideoProductOfOneDirector(videoProductList, directorFromCondition);
        Director directorNew = new Director();
        directorNew.setId(director.getId());
        directorNew.setFirst_name(director.getFirst_name());
        directorNew.setLast_name(director.getLast_name());
        directorNew.setBirth_date(director.getBirth_date());
        directorNew.setVideoProductList(videoProductList);
        if (director != null) {
            Boolean result = false;
            SatisfactionDirectorFilm satisfactionDirectorFilm = null;
            condition.setDirector(directorNew);
            result = condition.satisfy();
            for (int i = 0; i < videoProductList.size(); i++) {
                VideoProduct videoProduct = videoProductList.get(i);
                if (videoProduct instanceof Film) {
                    Film film = (Film) videoProduct;
                    film.setId_director(condition.getDirector().getId());
                    if (result.equals(true) && result.equals(ifMatchesDo)) {
                        insert(film, director);
                        directorStatus = new DirectorStatus(directorNew, videoProduct, RESULT_IF_SUCCESS);
                        directorStatusList.add(directorStatus);
                    } else {
                        directorStatus = new DirectorStatus(directorNew, videoProduct, RESULT_IF_NOT_SUCCESS);
                        directorStatusList.add(directorStatus);
                    }
                } else {
                    throw new ServiceException("The DB insertion method is called for non-matching entities!" +
                            " The method is waiting for \"Film\", and the input is different.");
                }
            }
        }
        return directorStatusList;
    }

//    @Override
//    public List<DirectorStatus> insertConditionWithoutDB
//            (List<VideoProduct> videoProductList, SatisfactionDirectorFilm condition) throws ServiceException {
//        Director directorFromCondition = condition.getDirector();
//        directorFromCondition = checkIfDiretorExists(directorFromCondition);
//        List<DirectorStatus> directorStatusList = new ArrayList<>();
//        DirectorStatus directorStatus = null;
//        Director director = checkIfVideoProductOfOneDirector(videoProductList, directorFromCondition);
//        Director directorNew = new Director();
//        directorNew.setId(director.getId());
//        directorNew.setFirst_name(director.getFirst_name());
//        directorNew.setLast_name(director.getLast_name());
//        directorNew.setBirth_date(director.getBirth_date());
//        directorNew.setVideoProductList(videoProductList);
//        if (director != null) {
//            Boolean result = false;
//            SatisfactionDirectorFilm satisfactionDirectorFilm = null;
//            condition.setDirector(directorNew);
//            result = condition.satisfy();
//            for (int i = 0; i < videoProductList.size(); i++) {
//                VideoProduct videoProduct = videoProductList.get(i);
//                if (videoProduct instanceof Film) {
//                    Film film = (Film) videoProduct;
//                    film.setId_director(condition.getDirector().getId());
//                    if (result) {
//                        insert(film, director);
//                        directorStatus = new DirectorStatus(directorNew, videoProduct, RESULT_IF_SUCCESS);
//                        directorStatusList.add(directorStatus);
//                    } else {
//                        directorStatus = new DirectorStatus(directorNew, videoProduct, RESULT_IF_NOT_SUCCESS);
//                        directorStatusList.add(directorStatus);
//                    }
//                } else {
//                    throw new ServiceException("The DB insertion method is called for non-matching entities!" +
//                            " The method is waiting for \"Film\", and the input is different.");
//                }
//            }
//        }
//        return directorStatusList;
//    }

    @Override
    public void update(Film film) {
        FilmDao filmDao = null;
        try {
            filmDao = FactoryDaoSql.getInstance().get(DaoSql.FilmDao);
            filmDao.update(film);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Integer filmId) {
        FilmDao filmDao = null;
        try {
            filmDao = FactoryDaoSql.getInstance().get(DaoSql.FilmDao);
            filmDao.delete(filmId);
        } catch (DaoException e) {
            e.printStackTrace();
        }

    }
}
