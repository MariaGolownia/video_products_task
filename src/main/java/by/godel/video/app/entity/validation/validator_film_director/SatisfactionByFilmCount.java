package by.godel.video.app.entity.validation.validator_film_director;
import by.godel.video.app.entity.Director;
import by.godel.video.app.entity.validation.ValidatorDirectorFilm;

public class SatisfactionByFilmCount
        extends SatisfactionDirectorFilm implements SatisfactionInterface  {

    public SatisfactionByFilmCount(Integer productСount, Director director) {
        super(productСount, director);
    }

    public Boolean satisfy() {
        return ValidatorDirectorFilm.satisfyByFilmCount(super.director, super.productСount);
    }

}
