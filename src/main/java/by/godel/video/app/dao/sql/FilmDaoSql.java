package by.godel.video.app.dao.sql;
import by.godel.video.app.dao.DaoException;
import by.godel.video.app.dao.DirectorDao;
import by.godel.video.app.dao.FilmDao;
import by.godel.video.app.entity.Director;
import by.godel.video.app.entity.Film;
import by.godel.video.app.service.FactoryService;
import by.godel.video.app.service.impl.DirectorServiceImpl;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FilmDaoSql extends BaseDaoSql implements FilmDao {
//    INSERT INTO film (director_id, name, release_date, genre)
//    VALUES ('The Physician', '2013-12-25', 'adventure/history/drama'),

    //private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger();

    private static final String SQL_SELECT_FILM_BY_ID =
            "SELECT film.name, film.release_date, film.genre, director_film.director_id FROM film " +
                    "JOIN director_film ON film.id = director_film.film_id WHERE film.id = ?";

    private static final String SQL_FILM_INSERT =
            "INSERT INTO film (name, release_date, genre)" +
                    " VALUES (?, ?, ?)";

    private static final String SQL_DIRECTORY_FILM_INSERT =
            "INSERT INTO director_film (director_id, film_id)" +
                    " VALUES (?, ?)";

    private static final String SQL_FILM_DIRECTOR_INSERT =
            "INSERT INTO film (director_id, name, release_date, genre)" +
                    " VALUES (?, ?, ?, ?)";

    private static final String SQL_ALL_FILMS_SELECT =
            "SELECT `id`, `director_id`, `name`, `release_date`, `genre` FROM `film`" +
                    " ORDER BY `name`";

    private static final String SQL_FILM_UPDATE =
            "UPDATE `film` SET `director_id` = ?, `name` = ?, `release_date` = ?, `genre` = ?" +
                    " WHERE `id` = ?";

    private static final String SQL_FILM_DELETE = "DELETE FROM `film` WHERE `id` = ?";

    protected FilmDaoSql(Connection connection) {
        super(connection);
    }

    @Override
    public Integer insert(Film film) throws DaoException {
        ResultSet resultSet = null;
        Integer idFilm = null;
        try (PreparedStatement statement = connection.prepareStatement(SQL_FILM_INSERT,
            Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, film.getName());
            statement.setDate(2, Date.valueOf(film.getRelease_date()));
            statement.setString(3, film.getGenreValuesStr());
            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                idFilm = resultSet.getInt(1);
                return idFilm;
            } else {
                throw new DaoException("No auto-incremented value (id) " +
                        "was returned after attempting to insert: " + film.toString());
            }
        } catch (SQLException e) {
            throw new DaoException("Failed to insert: " + idFilm.toString(), e);
        }
    }

    @Override
    public Integer insert(Film film, Director director) throws DaoException {
        ResultSet resultSet = null;
        Integer idFilm = null;
        Integer idDirectorFilm = null;
        try (PreparedStatement statement = connection.prepareStatement(SQL_FILM_INSERT,
                Statement.RETURN_GENERATED_KEYS)) {
            connection.setAutoCommit(false);
            statement.setString(1, film.getName());
            statement.setDate(2, Date.valueOf(film.getRelease_date()));
            statement.setString(3, film.getGenreValuesStr());
            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                idFilm = resultSet.getInt(1);

            } else {
                connection.rollback();
                throw new DaoException("No auto-incremented value (id) " +
                        "was returned after attempting to insert: " + film.toString());
            }
            DirectorDao dao = FactoryDaoSql.getInstance().get(DaoSql.DirectorDao);
            Integer idDirector = dao.insert(director);
        try {
            PreparedStatement statement2 = connection.prepareStatement(SQL_DIRECTORY_FILM_INSERT,
                    Statement.RETURN_GENERATED_KEYS);
            statement2.setInt(1, idDirector);
            statement2.setInt(2, idFilm);
            statement2.executeUpdate();
            resultSet = statement2.getGeneratedKeys();
            if (resultSet.next()) {
                idDirectorFilm = resultSet.getInt(1);

            } else {
                connection.rollback();
                throw new DaoException("No auto-incremented value (id) " +
                        "was returned after attempting to insert: " + film.toString());
            }
        }
        catch (SQLException e) {
        }
            connection.commit();
            return idDirectorFilm;
        } catch (SQLException e) {
            throw new DaoException("Failed to insert " +  e);
        }
    }


    @Override
    public Film readById(Integer id) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_FILM_BY_ID)) {
            statement.setObject(1, id);
            ResultSet resultSet = statement.executeQuery();
            Film film = new Film();
            if (resultSet.next()) {
                film.setId(id);
                film.setName(resultSet.getString("name"));
                film.setGenreValuesStr(resultSet.getString("genre"));
                Date resultDate = resultSet.getDate("release_date");
                film.setRelease_date(resultDate == null ? null : resultDate.toLocalDate());
                film.setId_director(resultSet.getInt("director_id"));
            }
            return film;
        } catch (SQLException e) {
            throw new DaoException("Failed to read director by id: " + id, e);
        }
    }


    public List<Film> readAll() throws DaoException {
        List<Film> filmList = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SQL_ALL_FILMS_SELECT)) {
            ResultSet resultSet = statement.executeQuery();
            Film film = null;
            while (resultSet.next()) {
                film = new Film();
                film.setId(resultSet.getInt("id"));
                film.setName(resultSet.getString("name"));
                film.setGenreValuesStr(resultSet.getString("genre"));
                Date resultDate = resultSet.getDate("release_date");
                film.setRelease_date(resultDate == null ? null : resultDate.toLocalDate());
                filmList.add(film);
            }
            return filmList;
        } catch (SQLException e) {
            throw new DaoException("Failed to read list of films from DB", e);
        }
    }

    @Override
    public int update(Film film) throws DaoException {
//        ResultSet resultSet = null;
//        Integer updateId = null;
//        try (PreparedStatement statement = connection.prepareStatement(SQL_DIRECTOR_UPDATE)) {
//            statement.setString(1, director.getFirst_name());
//            statement.setString(2, director.getLast_name());
//            LocalDate dateDirectorBirth = director.getBirth_date();
//            statement.setDate(3, (dateDirectorBirth == null) ? null : Date.valueOf(dateDirectorBirth));
//            statement.executeUpdate();
//            int rowsAffected = statement.executeUpdate();
//            return rowsAffected;
//        } catch (SQLException e) {
//            throw new DaoException("Failed to update: " + director.toString(), e);
//        }
        return 0;
    }

    @Override
    public int delete(Integer id) throws DaoException {
//        try (PreparedStatement statement = connection.prepareStatement(SQL_DIRECTOR_DELETE)) {
//            statement.setInt(1, id);
//            int rowsAffected = statement.executeUpdate();
//            return rowsAffected;
//        } catch (SQLException e) {
//            throw new DaoException("Failed to delete by id: " + id, e);
//        }
        return 0;
    }


}
