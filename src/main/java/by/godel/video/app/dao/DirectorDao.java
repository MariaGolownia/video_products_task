package by.godel.video.app.dao;
import by.godel.video.app.entity.Director;
import by.godel.video.app.entity.Film;

import java.util.List;

public interface DirectorDao extends Dao<Director>{

    public List<Director> readAll() throws DaoException;
}
