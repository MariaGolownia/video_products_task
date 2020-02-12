package by.godel.video.app.dao;
import by.godel.video.app.entity.Director;
import by.godel.video.app.entity.Film;

public interface FilmDao extends Dao<Film>{

    Integer insert(Film film, Director director) throws DaoException;

}
