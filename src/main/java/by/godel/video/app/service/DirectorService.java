package by.godel.video.app.service;
import by.godel.video.app.entity.Director;
import by.godel.video.app.entity.Film;
import by.godel.video.app.entity.validation.validator_film_director.SatisfactionByAccurateFilmCount;

import java.util.List;

public interface DirectorService {

    Director findById(Integer directorId);

    List<Director> findAll();

    Integer save(Director director);

    List<Director> save(Film film, SatisfactionByAccurateFilmCount condition);

    void update(Director director);

    void delete(Integer directorId);

}
