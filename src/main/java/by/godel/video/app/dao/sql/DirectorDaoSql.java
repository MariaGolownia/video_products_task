package by.godel.video.app.dao.sql;
import by.godel.video.app.dao.DaoException;
import by.godel.video.app.dao.DirectorDao;
import by.godel.video.app.entity.Director;
import by.godel.video.app.entity.Film;
import by.godel.video.app.entity.VideoProduct;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DirectorDaoSql extends BaseDaoSql implements DirectorDao {
    //private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger();
    private static final String SQL_DIRECTOR_INSERT =
            "INSERT INTO director (first_name, last_name, birth_date)" +
                    " VALUES (?, ?, ?)";

    private static final String SQL_SELECT_DIRECTOR_BY_ID =
            "SELECT director.first_name, director.last_name, director.birth_date " +
                     "FROM director WHERE director.id = ?";

//       "SELECT director.first_name, director.last_name, director.birth_date, " +
//               "film.* FROM director " +
//               "JOIN director_film ON director.id = director_film.director_id " +
//               "JOIN film ON director_film.film_id = film.id WHERE director.id = ?";

    private static final String SQL_ALL_DIRECTORS_SELECT =
            "SELECT director.id, director.first_name, director.last_name, director.birth_date, " +
                    "film.* FROM director " +
                    "JOIN director_film ON director.id = director_film.director_id " +
                    "JOIN film ON director_film.film_id = film.id";

    private static final String SQL_DIRECTOR_UPDATE =
            "UPDATE `director` SET `first_name` = ?, `last_name` = ?, `birth_date` = ?" +
                    " WHERE `id` = ?";

    private static final String SQL_DIRECTOR_DELETE = "DELETE FROM `director` WHERE `id` = ?";

    protected DirectorDaoSql(Connection connection) {
        super(connection);
    }

    @Override
    public Integer insert(Director director) throws DaoException {
        ResultSet resultSet = null;
        Integer idDirector = null;
        try (PreparedStatement statement = connection.prepareStatement(SQL_DIRECTOR_INSERT,
                Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, director.getFirst_name());
            statement.setString(2, director.getLast_name());
            statement.setDate(3, Date.valueOf(director.getBirth_date()));
            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                idDirector = resultSet.getInt(1);
                return idDirector;
            } else {
                throw new DaoException("No auto-incremented value (id) " +
                        "was returned after attempting to insert: " + director.toString());
            }
        } catch (SQLException e) {
            throw new DaoException("Failed to insert: " + director.toString(), e);
        }
    }

    @Override
    public Director readById(Integer id) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_DIRECTOR_BY_ID)) {
            statement.setObject(1, id);
            ResultSet resultSet = statement.executeQuery();
            Director director = new Director();
            //VideoProduct videoProduct = new VideoProduct();
            //List<VideoProduct> filmsList = new ArrayList<>();
             while (resultSet.next()) {
                director.setId(id);
                director.setFirst_name(resultSet.getString("first_name"));
                director.setLast_name(resultSet.getString("last_name"));
                Date resultDate = resultSet.getDate("birth_date");
                director.setBirth_date(resultDate == null ? null : resultDate.toLocalDate());
//                videoProduct.setId(resultSet.getInt("id"));
//                videoProduct.setName(resultSet.getString("name"));
//                Date resultDateFilm = resultSet.getDate("release_date");
//                videoProduct.setRelease_date(resultDate == null ? null : resultDateFilm.toLocalDate());
//                videoProduct.setGenreValuesStr(resultSet.getString("genre"));
//                director.addVideoProductList(videoProduct);
            }
            return director;
        } catch (SQLException e) {
            throw new DaoException("Failed to read director by id: " + id, e);
        }
    }
//---------------------------------------------------------------------------?????????
    @Override
    public List<Director> readAll() throws DaoException {
        List<Director> directorList = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SQL_ALL_DIRECTORS_SELECT)) {
            ResultSet resultSet = statement.executeQuery();
            Director director = null;
            VideoProduct videoProduct = new VideoProduct();
            List<VideoProduct> filmsList = new ArrayList<>();
            while (resultSet.next()) {
                director = new Director();
                director.setId(resultSet.getInt("id"));
                director.setFirst_name(resultSet.getString("first_name"));
                director.setLast_name(resultSet.getString("last_name"));
                Date resultDate = resultSet.getDate("birth_date");
                director.setBirth_date(resultDate == null ? null : resultDate.toLocalDate());
                videoProduct.setId(resultSet.getInt("id"));
                videoProduct.setName(resultSet.getString("name"));
                Date resultDateFilm = resultSet.getDate("release_date");
                videoProduct.setRelease_date(resultDate == null ? null : resultDateFilm.toLocalDate());
                videoProduct.setGenreValuesStr(resultSet.getString("genre"));
                director.addVideoProductList(videoProduct);
                directorList.add(director);
            }
            return directorList;
        } catch (SQLException e) {
            throw new DaoException("Failed to read directors by id: ", e);
        }
    }

    @Override
    public int update(Director director) throws DaoException {
        ResultSet resultSet = null;
        Integer updateId = null;
        try (PreparedStatement statement = connection.prepareStatement(SQL_DIRECTOR_UPDATE)) {
            statement.setString(1, director.getFirst_name());
            statement.setString(2, director.getLast_name());
            LocalDate dateDirectorBirth = director.getBirth_date();
            statement.setDate(3, (dateDirectorBirth == null) ? null : Date.valueOf(dateDirectorBirth));
            statement.executeUpdate();
            int rowsAffected = statement.executeUpdate();
            return rowsAffected;
        } catch (SQLException e) {
            throw new DaoException("Failed to update: " + director.toString(), e);
        }
    }

    @Override
    public int delete(Integer id) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_DIRECTOR_DELETE)) {
            statement.setInt(1, id);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected;
        } catch (SQLException e) {
            throw new DaoException("Failed to delete by id: " + id, e);
        }
    }
}
