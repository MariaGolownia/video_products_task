package by.godel.video.app.entity;
import by.godel.video.app.entity.en_um.Genre;
import java.time.LocalDate;
import java.util.List;

public class Film extends VideoProduct {

    public Film() {
        super();
    }

    public Film(String name, LocalDate release_date, List<Genre> genreValues, List<Integer> directorListId) {
        super(name, release_date, genreValues, directorListId);
    }

    public Film(String name, LocalDate release_date, Genre genreValue, List<Integer> directorListId) {
        super(name, release_date, genreValue, directorListId);
    }

    public Film(String name, LocalDate release_date, List<Genre> genreValues, Integer id_director) {
        super(name, release_date, genreValues, id_director);
    }


    public Film(String name, LocalDate release_date, Genre genre, Integer id_director) {
        super(name, release_date, genre, id_director);
    }

    public Film(String name, LocalDate release_date, List<Genre> genreValues) {
        super(name, release_date, genreValues);
    }

    public Film(String name, LocalDate release_date, Genre genre) {
        super(name, release_date, genre);
    }

    public Film(String name, LocalDate release_date) {
        super(name, release_date);
    }
}
