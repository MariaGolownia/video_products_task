package by.godel.video.app.entity.validation.validator_film_director;
import by.godel.video.app.entity.Director;
import by.godel.video.app.entity.validation.ValidatorDirectorFilm;

import java.time.LocalDate;

public class SatisfactionByFilmCountInOneDate
        extends  SatisfactionDirectorFilm implements SatisfactionInterface  {

    public SatisfactionByFilmCountInOneDate(Director director, Integer productСount, LocalDate date) {
        super(director, productСount, date);
    }

    public Boolean satisfy() {
        return ValidatorDirectorFilm.satisfyByFilmCountInOneDate(super.director,
                super.date, super.productСount);
    }

}
