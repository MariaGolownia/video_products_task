package by.godel.video.app.entity.validation.validator_film_director;
import by.godel.video.app.entity.Director;
import by.godel.video.app.entity.validation.ValidatorDirectorFilm;

import java.time.LocalDate;

public class SatisfactionByFilmCountInOneDate
        extends  SatisfactionDirectorFilm implements SatisfactionInterface  {


    public SatisfactionByFilmCountInOneDate(Integer productСount, Director director) {
        super(productСount, director);
    }

    public Boolean satisfy() {
        return ValidatorDirectorFilm.satisfyByFilmCountInOneDate(super.director,
                super.date, super.productСount);
    }

}
