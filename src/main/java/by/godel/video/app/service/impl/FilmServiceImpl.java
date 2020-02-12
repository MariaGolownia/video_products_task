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
import com.sun.xml.internal.bind.v2.model.core.ID;

import java.util.ArrayList;
import java.util.List;

import static com.sun.xml.internal.ws.dump.LoggingDumpTube.Position.Before;
import static jdk.nashorn.tools.Shell.SUCCESS;

public class FilmServiceImpl extends Service implements FilmService {
    public static final Boolean RESULT_IF_SUCCESS = true;
    public static final Boolean RESULT_IF_NOT_SUCCESS = false;


    @Override
    public Film findById(Integer filmId) {
        Film film = new Film();
        FilmDao dao = null;
        try {
            dao = FactoryDaoSql.getInstance().get(DaoSql.FilmDao);
            film = dao.readById(filmId);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return film;
    }

    @Override
    public List<Film> findAll() {
        return null;
    }

    @Override
    public Integer insert(Film film) {
        Integer idFilmNew = null;
        FilmDao dao = null;
        try {
            dao = FactoryDaoSql.getInstance().get(DaoSql.FilmDao);
            idFilmNew = dao.insert(film);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return idFilmNew;
    }

    @Override
    public Integer insert(Film film, Director director) {
        Integer idFilmNew = null;
        FilmDao dao = null;
        try {
            dao = FactoryDaoSql.getInstance().get(DaoSql.FilmDao);
            idFilmNew = dao.insert(film, director);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return idFilmNew;
    }


    // Метод обработки общих условий вставки (включая в Condition данные data base)
    public List<DirectorStatus> insertConditionWithDB(List<VideoProduct> videoProductList, SatisfactionDirectorFilm condition) throws ServiceException {
        List <DirectorStatus> directorStatusList = new ArrayList<>();
        DirectorStatus directorStatus = null;
        for (int i = 0; i < videoProductList.size(); i++) {
            directorStatus = insertOneSetConditionWithDB (videoProductList.get(i), condition);
            directorStatusList.add(directorStatus);
        }
            return directorStatusList;
    }


    @Override
    public DirectorStatus insertOneSetConditionWithDB(VideoProduct videoProduct,
                                                SatisfactionDirectorFilm condition)
            throws ServiceException {
        DirectorStatus directorStatus = null;
        Director director = checkIfDiretorIdExists(videoProduct);
        if (director != null) {
            director.addVideoProductList(videoProduct);
            Boolean result = false;
            SatisfactionDirectorFilm satisfactionDirectorFilm = null;
            if (condition instanceof SatisfactionByAccurateFilmCount) {
                satisfactionDirectorFilm = (SatisfactionByAccurateFilmCount) condition;
                satisfactionDirectorFilm.setDirector(director);
                result = satisfactionDirectorFilm.satisfy();
            } else if (condition instanceof SatisfactionByDateAndProductCount) {
                satisfactionDirectorFilm = (SatisfactionByDateAndProductCount) condition;
                satisfactionDirectorFilm.setDirector(director);
                result = satisfactionDirectorFilm.satisfy();
            } else if (condition instanceof SatisfactionByFilmCountInOneDate) {
                satisfactionDirectorFilm = (SatisfactionByFilmCountInOneDate) condition;
                satisfactionDirectorFilm.setDirector(director);
                result = satisfactionDirectorFilm.satisfy();
            } else {
                satisfactionDirectorFilm = (SatisfactionByDateAndProductCount) condition;
                satisfactionDirectorFilm.setDirector(director);
                result = satisfactionDirectorFilm.satisfy();
            }
            if (videoProduct instanceof Film) {
                Film film = (Film) videoProduct;
                if (result) {
                    insert(film);
                    directorStatus = new DirectorStatus(director, film, RESULT_IF_SUCCESS);
                } else {
                    directorStatus = new DirectorStatus(director, film, RESULT_IF_NOT_SUCCESS);
                }
            } else {
                throw new ServiceException("The DB insertion method is called for non-matching entities!" +
                        " The method is waiting for \"Film\", and the input is different.");
            }
        }
        return directorStatus;
    }

    @Override
    public Director checkIfVideoProductOfOneDirector(List<VideoProduct> videoProductList) throws ServiceException {
        Integer dircetorID = null;
        Director director = new Director();
        if (!videoProductList.equals(null) || videoProductList != null) {
            Integer dircetorIDfromList = videoProductList.get(0).getId_director();

            for (int i = 0; i < videoProductList.size(); i++) {
                if (!videoProductList.get(i).getId_director().equals(dircetorIDfromList)) {
                    throw new ServiceException("Films of one director are accepted for insertion!");
                }
            }
            director = checkIfDiretorIdExists(videoProductList.get(1));
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
    (List<VideoProduct> videoProductList, SatisfactionDirectorFilm condition) throws ServiceException {
        Director directorFromCondition = condition.getDirector();
        directorFromCondition = checkIfDiretorExists(directorFromCondition);

        List<DirectorStatus> directorStatusList = new ArrayList<>();
        DirectorStatus directorStatus = null;
        Director director = checkIfVideoProductOfOneDirector(videoProductList);
        Director directorNew = new Director();
        directorNew.setId(director.getId());
        directorNew.setFirst_name(director.getFirst_name());
        directorNew.setLast_name(director.getLast_name());
        directorNew.setBirth_date(director.getBirth_date());
        directorNew.setVideoProductList(videoProductList);

        if (director != null) {
            Boolean result = false;
            SatisfactionDirectorFilm satisfactionDirectorFilm = null;
            if (condition instanceof SatisfactionByAccurateFilmCount) {
                satisfactionDirectorFilm = (SatisfactionByAccurateFilmCount) condition;
                satisfactionDirectorFilm.setDirector(directorNew);
                result = satisfactionDirectorFilm.satisfy();
            } else if (condition instanceof SatisfactionByDateAndProductCount) {
                satisfactionDirectorFilm = (SatisfactionByDateAndProductCount) condition;
                satisfactionDirectorFilm.setDirector(directorNew);
                result = satisfactionDirectorFilm.satisfy();
            } else if (condition instanceof SatisfactionByFilmCountInOneDate) {
                satisfactionDirectorFilm = (SatisfactionByFilmCountInOneDate) condition;
                satisfactionDirectorFilm.setDirector(directorNew);
                result = satisfactionDirectorFilm.satisfy();
            } else {
                satisfactionDirectorFilm = (SatisfactionByDateAndProductCount) condition;
                satisfactionDirectorFilm.setDirector(directorNew);
                result = satisfactionDirectorFilm.satisfy();
            }

            for (int i = 0; i < videoProductList.size(); i++) {
                VideoProduct videoProduct = videoProductList.get(i);
                if (videoProduct instanceof Film) {
                    Film film = (Film) videoProduct;
                    if (result) {
                        insert(film, directorNew);
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



    @Override
    public void update(Film film) {

    }

    @Override
    public void delete(Integer filmId) {

    }
}
