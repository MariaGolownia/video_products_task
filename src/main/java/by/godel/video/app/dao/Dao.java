package by.godel.video.app.dao;
import by.godel.video.app.entity.Entity;

    /**
     * Represents basic dao that must perform the following operations:
     * create, read, update, delete entity.
     *
     * @param <T> any class extending by.godel.video.app.entity.Entity
     */
    public interface Dao<T extends Entity> {

        /**
         * Creates entity in the database and returns an internal ID of it.
         *
         * @param entity entity to be created
         * @return internal ID of the created entity.
         * @throws DaoException if a database error occurred while
         *                      working with the database connection.
         */

        Integer insert(T entity) throws DaoException;

        /**
         * Reads entity from the database with given internal ID.
         *
         * @param id internal ID of the entity to be read
         * @return Optional containing either:
         * <ol>
         *     <li>entity encapsulating all the info from  the database, if it was found.</li>
         *     <li><code>null</code>, if no entity was found for given ID.</li>
         * </ol>
         * @throws DaoException if a database error occurred while
         *                      working with the database connection.
         */
        T readById(Integer id) throws DaoException;

        /**
         * Updates data in the database for given entity. The entity must contain internal ID
         * that is used in the database.
         *
         * @param entity entity to be updated
         * @return number of rows in the database table affected by the query.
         * @throws DaoException if a database error occurred while
         *                      working with the database connection.
         */
        int update(T entity) throws DaoException;

        /**
         * Deletes entity from the database with given internal ID.
         *
         * @param id internal ID of the entity to be deleted
         * @return number of rows in the database table affected by the query.
         * @throws DaoException if a database error occurred while
         *                      working with the database connection.
         */
        int delete(Integer id) throws DaoException;
}
