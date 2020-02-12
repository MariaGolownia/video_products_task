package by.godel.video.app.service;
import by.godel.video.app.entity.Director;
import by.godel.video.app.entity.Film;
import by.godel.video.app.entity.VideoProduct;
import by.godel.video.app.entity.entityStatus.DirectorStatus;
import by.godel.video.app.entity.validation.validator_film_director.*;

import java.util.List;

public interface FilmService {

    Film findById(Integer filmId);

    List<Film> findAll();

    Integer insert (Film film);

    Integer insert(Film film, Director director);

    Director checkIfDiretorIdExists (VideoProduct videoProduct) throws ServiceException;

    Director checkIfVideoProductOfOneDirector (List <VideoProduct> videoProductList) throws ServiceException;

    List <DirectorStatus> insertConditionWithDB(List <VideoProduct> videoProductListt, SatisfactionDirectorFilm condition) throws ServiceException;

    DirectorStatus insertOneSetConditionWithDB(VideoProduct videoProduct, SatisfactionDirectorFilm condition) throws ServiceException;

    List <DirectorStatus>  insertConditionWithoutDB (List <VideoProduct> videoProductList,
                                             SatisfactionDirectorFilm condition) throws ServiceException;

    Director checkIfDiretorExists(Director director) throws ServiceException;

    void update(Film film);

    void delete(Integer filmId);

}
