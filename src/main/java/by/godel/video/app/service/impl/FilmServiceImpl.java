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
import java.util.Arrays;
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
            logger.error("Error of finding from table `film`. Id of film is: " + filmId);
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
            logger.error("Error of finding from table `film`. Id of director is: " + directorId);
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
                    " Film is: " + film.toString() + ". Director is: " + director.toString() + ". ");
            e.printStackTrace();
        }
        return idFilmNew;
    }

    @Override
    public Integer insert(Film film, List<Director> directorList) {
        Integer idFilmNew = null;
        FilmDao dao = null;
        try {
            dao = FactoryDaoSql.getInstance().get(DaoSql.FilmDao);
            idFilmNew = dao.insert(film, directorList);
        } catch (DaoException e) {
            logger.error("Error of inserting to table `film`." +
                    " Film is: " + film.toString() + ". Directors are: " + Arrays.toString(directorList.toArray()) + ". ");
            e.printStackTrace();
        }
        return idFilmNew;
    }

    // Метод обработки общих условий вставки (включая в Condition данные data base)
    // Используется в случае если у одного фильма - один режисер
    public List<DirectorStatus> insertConditionWithDB(List<VideoProduct> videoProductList,
                                                      SatisfactionDirectorFilm condition, Boolean ifMatchesDo) throws ServiceException {
        List<DirectorStatus> directorStatusList = new ArrayList<>();
        List<Director> directorList = new ArrayList<>();
        Director director = condition.getDirector();
        directorList.add(director);
        DirectorStatus directorStatus = null;
        if (videoProductList != null) {
            checkIfVideoOfOneDirectorOrNull(videoProductList, condition.getDirector());
            if (director != null) {
                Boolean result = false;
                director.addVideoProductList(videoProductList);
                condition.setDirector(director);
                result = condition.satisfy();
                for (int i = 0; i < videoProductList.size(); i++) {
                    VideoProduct videoProductFromList = videoProductList.get(i);
                    if (videoProductFromList instanceof Film) {
                        Film film = (Film) videoProductFromList;
                        film.addDirector(condition.getDirector().getId());
                        if (result.equals(true) && result.equals(ifMatchesDo)) {
                            insert(film, director);
                            directorStatus = new DirectorStatus(directorList, film, RESULT_IF_SUCCESS);
                            directorStatusList.add(directorStatus);
                        } else {
                            directorStatus = new DirectorStatus(directorList, film, RESULT_IF_NOT_SUCCESS);
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

    // Метод обработки общих условий вставки (включая в Condition данные data base)
    // Используется в случае если у одного фильма - может много режисеров
    public List<DirectorStatus> insertConditionWithDB(List<VideoProduct> videoProductList,
                                                      List<SatisfactionDirectorFilm> conditionList, Boolean ifMatchesDo) throws ServiceException {
        List<DirectorStatus> directorStatusList = new ArrayList<>();
        List<Director> directorList = new ArrayList<>();
        DirectorStatus directorStatus = null;
        Boolean result = false;
        int countFalseCondition = 0;
        if (videoProductList != null) {
            // Director director = checkIfVideoProductOfOneDirector(videoProductList, condition.getDirector());
            if (videoProductList.size() > 0) {
                // определяем, что режиcер и(или) все режиcеры удовлетворяют заданному уловию
                for (int k = 0; k < conditionList.size(); k++) {
                    directorList.add(conditionList.get(k).getDirector());
                    result = conditionList.get(k).satisfy();
                    if (result.equals(false))
                        countFalseCondition++;
                }
            }
            if (countFalseCondition != 0) {
                result = false;
            }
            for (int i = 0; i < videoProductList.size(); i++) {
                VideoProduct videoProductFromList = videoProductList.get(i);
                if (videoProductFromList instanceof Film) {
                    Film film = (Film) videoProductFromList;
                    film.setDirectorListIdFromDirectorList(directorList);
                    if (result.equals(true) && result.equals(ifMatchesDo)) {
                        insert(film, directorList);
                        directorStatus = new DirectorStatus(directorList, film, RESULT_IF_SUCCESS);
                        directorStatusList.add(directorStatus);
                    } else {
                        directorStatus = new DirectorStatus(directorList, film, RESULT_IF_NOT_SUCCESS);
                        directorStatusList.add(directorStatus);
                    }
                } else {
                    throw new ServiceException("The DB insertion method is called for non-matching entities!" +
                            " The method is waiting for \"Film\", and the input is different.");
                }
            }
        } else {
            throw new ServiceException("The list of video products is empty!");
        }
        return directorStatusList;
    }

    // Используется в случае если у одного фильма - один режисер
    @Override
    public List<DirectorStatus> insertConditionOnlyDB(List<VideoProduct> videoProductList,
                                                      SatisfactionDirectorFilm condition, Boolean ifMatchesDo) throws ServiceException {
        List<DirectorStatus> directorStatusList = new ArrayList<>();
        List<Director> directorList = new ArrayList<>();
        Director director = condition.getDirector();
        directorList.add(director);
        DirectorStatus directorStatus = null;
        if (videoProductList != null) {
            checkIfVideoOfOneDirectorOrNull(videoProductList, condition.getDirector());
            if (director != null) {
                Boolean result = false;
                condition.setDirector(director);
                result = condition.satisfy();
                for (int i = 0; i < videoProductList.size(); i++) {
                    VideoProduct videoProductFromList = videoProductList.get(i);
                    if (videoProductFromList instanceof Film) {
                        Film film = (Film) videoProductFromList;
                        film.setOneDirectorList(condition.getDirector().getId());
                        if (result.equals(true) && result.equals(ifMatchesDo)) {
                            insert(film, director);
                            directorStatus = new DirectorStatus(directorList, film, RESULT_IF_SUCCESS);
                            directorStatusList.add(directorStatus);
                        } else {
                            directorStatus = new DirectorStatus(directorList, film, RESULT_IF_NOT_SUCCESS);
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

    // Используется в случае если у одного фильма - много режисеров
    @Override
    public List<DirectorStatus> insertConditionOnlyDB(List<VideoProduct> videoProductList,
                                                      List<SatisfactionDirectorFilm> conditionList, Boolean ifMatchesDo) throws ServiceException {
        List<DirectorStatus> directorStatusList = new ArrayList<>();
        List<Director> directorList = new ArrayList<>();
        Boolean result = false;
        Integer countFalseCondition = 0;
        DirectorStatus directorStatus = null;
        if (videoProductList != null) {
            if (videoProductList.size() > 0) {
                // определяем, что режиcер и(или) все режиcеры удовлетворяют заданному уловию
                for (int k = 0; k < conditionList.size(); k++) {
                    directorList.add(conditionList.get(k).getDirector());
                    result = conditionList.get(k).satisfy();
                    if (result.equals(false))
                        countFalseCondition++;
                }
            }
            if (countFalseCondition != 0) {
                result = false;
            }
            for (int i = 0; i < videoProductList.size(); i++) {
                VideoProduct videoProductFromList = videoProductList.get(i);
                if (videoProductFromList instanceof Film) {
                    Film film = (Film) videoProductFromList;
                    film.setDirectorListIdFromDirectorList(directorList);
                    if (result.equals(true) && result.equals(ifMatchesDo)) {
                        insert(film, directorList);
                        directorStatus = new DirectorStatus(directorList, film, RESULT_IF_SUCCESS);
                        directorStatusList.add(directorStatus);
                    } else {
                        directorStatus = new DirectorStatus(directorList, film, RESULT_IF_NOT_SUCCESS);
                        directorStatusList.add(directorStatus);
                    }
                } else {
                    throw new ServiceException("The DB insertion method is called for non-matching entities!" +
                            " The method is waiting for \"Film\", and the input is different.");
                }
            }
        } else {
            throw new ServiceException("The list of video products is empty!");
        }
        return directorStatusList;

    }

    @Override
    public Boolean checkIfVideoOfOneDirectorOrNull(List<VideoProduct> videoProductList, Director director) throws ServiceException {
        Boolean result = true;
        Integer countDifferenceId = 0;
        if (!videoProductList.equals(null) || videoProductList != null) {
            Integer countOfNullIdDirector = 0;
            Integer countOfDirectors = 0;
            for (int k = 0; k < videoProductList.size(); k++) {
                List<Integer> directorIdList = videoProductList.get(k).getDirectorListId();
                for (int j = 0; j < directorIdList.size(); j++) {
                    Integer idDirector = directorIdList.get(j);
                    countOfDirectors++;
                    if (idDirector.equals(null)) {
                        countOfNullIdDirector++;
                    }
                }
            }
            if (countOfNullIdDirector == countOfDirectors) {
                return result;
            } else if (countOfNullIdDirector != 0) {
                throw new ServiceException("Check the links of each movie in the list with the director's id" +
                        " or set null of director id in all movies!");
            } else {
                Integer dircetorID = director.getId();

                for (int i = 0; i < videoProductList.size(); i++) {
                    List<Integer> directorListId = videoProductList.get(i).getDirectorListId();
                    for (int j = 0; j < directorListId.size(); j++) {
                        if (!directorListId.get(j).equals(director.getId()))
                            countOfNullIdDirector++;
                    }
                    if (countOfNullIdDirector > 0) {
                        result = false;
                        throw new ServiceException("Films of one director are accepted for insertion!");
                    }
                }
            }
        }
        return result;
    }

    @Override
    public List<Director> checkIfDiretorsIdExists(VideoProduct videoProduct) throws ServiceException {
        List<Integer> directorId = new ArrayList<>();
        List<Director> directorIdDB = new ArrayList<>();
        directorId = videoProduct.getDirectorListId();
        for (int i = 0; i < directorId.size(); i++) {
            Integer dircetorID = directorId.get(i);
            Director directorDB = null;
            if (dircetorID == null) {
                throw new ServiceException("Before processing the movie insertion operation," +
                        " please enter data about the unique identifier (id) of the director!");
            }
            DirectorServiceImpl directorService = FactoryService.getInstance().get(DaoSql.DirectorDao);
            directorDB = directorService.findById(dircetorID);
            directorIdDB.add(directorDB);
            if (directorDB == null) {
                throw new ServiceException("Before processing the movie insertion operation," +
                        " please enter the information about the director! " +
                        "The specified director Id does not exist in the database!");
            }
        }
        return directorIdDB;
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
    // Используется в случае если у одного фильма - один режисер
    @Override
    public List<DirectorStatus> insertConditionWithoutDB
    (List<VideoProduct> videoProductList, SatisfactionDirectorFilm condition, Boolean ifMatchesDo) throws ServiceException {
        List<Director> directorList = new ArrayList<>();
        Director director = condition.getDirector();
        directorList.add(director);
        Director directorFromCondition = condition.getDirector();
        directorFromCondition = checkIfDiretorExists(directorFromCondition);
        List<DirectorStatus> directorStatusList = new ArrayList<>();
        DirectorStatus directorStatus = null;
        checkIfVideoOfOneDirectorOrNull(videoProductList, directorFromCondition);
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
                    film.setOneDirectorList(condition.getDirector().getId());
                    if (result.equals(true) && result.equals(ifMatchesDo)) {
                        insert(film, director);
                        directorStatus = new DirectorStatus(directorList, videoProduct, RESULT_IF_SUCCESS);
                        directorStatusList.add(directorStatus);
                    } else {
                        directorStatus = new DirectorStatus(directorList, videoProduct, RESULT_IF_NOT_SUCCESS);
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

    // Используется в случае если у одного фильма может быть много режисеров
    @Override
    public List<DirectorStatus> insertConditionWithoutDB
    (List<VideoProduct> videoProductList, List<SatisfactionDirectorFilm> conditionList, Boolean ifMatchesDo) throws ServiceException {
        List<Director> directorList = new ArrayList<>();
        List<DirectorStatus> directorStatusList = new ArrayList<>();
        DirectorStatus directorStatus = null;
        Boolean result = false;
        Integer countFalseCondition = 0;
        if (videoProductList.size() > 0) {
            // определяем, что режиcер и(или) все режиcеры удовлетворяют заданному уловию
            for (int k = 0; k < conditionList.size(); k++) {
                Director directorFromCondition = conditionList.get(k).getDirector();
                checkIfDiretorExists(directorFromCondition);
                directorList.add(directorFromCondition);
                result = conditionList.get(k).satisfy();
                if (result.equals(false))
                    countFalseCondition++;
            }
        }
        if (countFalseCondition != 0) {
            result = false;
        }
        // Очищаем каждого директора от фильмов БД
        for (int i = 0; i < directorList.size(); i++) {
            directorList.get(i).setVideoProductList(videoProductList);
        }

        if (directorList != null) {
            for (int i = 0; i < videoProductList.size(); i++) {
                VideoProduct videoProduct = videoProductList.get(i);
                if (videoProduct instanceof Film) {
                    Film film = (Film) videoProduct;
                    film.setDirectorListIdFromDirectorList(directorList);
                    if (result.equals(true) && result.equals(ifMatchesDo)) {
                        insert(film, directorList);
                        directorStatus = new DirectorStatus(directorList, videoProduct, RESULT_IF_SUCCESS);
                        directorStatusList.add(directorStatus);
                    } else {
                        directorStatus = new DirectorStatus(directorList, videoProduct, RESULT_IF_NOT_SUCCESS);
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
