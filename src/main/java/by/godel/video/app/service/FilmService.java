package by.godel.video.app.service;
import by.godel.video.app.entity.Director;
import by.godel.video.app.entity.Film;
import by.godel.video.app.entity.VideoProduct;
import by.godel.video.app.entity.entityStatus.DirectorStatus;
import by.godel.video.app.entity.validation.validator_film_director.*;

import java.util.List;

public interface FilmService {

    VideoProduct findById(Integer filmId);

    List <VideoProduct> findByIdDirecror(Integer directorId);

    List<Film> findAll();

    // Данный метод закомментирован, так как запрещено вставлять в БД фильм без его привязки к конкретному режисеру
    // В случае ввода данной возможности - разблокировать
    //Integer insert (Film film);

    Integer insert(Film film, Director director);

    Integer insert(Film film, List <Director> directorList);

    List <Director> checkIfDiretorsIdExists (VideoProduct videoProduct) throws ServiceException;

    Boolean checkIfVideoOfOneDirectorOrNull (List<VideoProduct> videoProductList, Director director) throws ServiceException;

    List <DirectorStatus> insertConditionWithDB(List <VideoProduct> videoProductList,
                                                SatisfactionDirectorFilm condition, Boolean ifMatchesDo) throws ServiceException;

    List<DirectorStatus>  insertConditionOnlyDB(List <VideoProduct> videoProductList,
                                                SatisfactionDirectorFilm condition, Boolean ifMatchesDo) throws ServiceException;

    List <DirectorStatus>  insertConditionWithoutDB (List <VideoProduct> videoProductList,
                                             SatisfactionDirectorFilm condition, Boolean ifMatchesDo) throws ServiceException;

    // Следующие методы добавлены/перегружены в связи с тем, что у одного фильма может быть несколько режичесеров

    List <DirectorStatus> insertConditionWithDB(List <VideoProduct> videoProductList,
                                                List <SatisfactionDirectorFilm> conditionList, Boolean ifMatchesDo) throws ServiceException;

    List<DirectorStatus>  insertConditionOnlyDB(List <VideoProduct> videoProductList,
                                                List <SatisfactionDirectorFilm> conditionList, Boolean ifMatchesDo) throws ServiceException;

    List <DirectorStatus>  insertConditionWithoutDB (List <VideoProduct> videoProductList,
                                                     List <SatisfactionDirectorFilm> conditionList, Boolean ifMatchesDo) throws ServiceException;


    Director checkIfDiretorExists(Director director) throws ServiceException;

    void update(Film film);

    void delete(Integer filmId);

}
