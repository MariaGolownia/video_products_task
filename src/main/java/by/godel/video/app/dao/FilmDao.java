package by.godel.video.app.dao;
import by.godel.video.app.entity.Director;
import by.godel.video.app.entity.Film;
import by.godel.video.app.entity.VideoProduct;

import java.util.List;

public interface FilmDao extends Dao<Film>{

    Integer insert(Film film, Director director) throws DaoException;

    List <VideoProduct> readByIdDirector(Integer id) throws DaoException;

    int deleteLinkWithDirector(Integer id) throws DaoException;

}
