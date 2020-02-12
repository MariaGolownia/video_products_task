package by.godel.video.app.entity.validation.validator_film_director;
import by.godel.video.app.entity.Director;
import by.godel.video.app.entity.validation.ValidatorDirectorFilm;

import java.time.LocalDate;


public class SatisfactionByDateAndProductCount
        extends SatisfactionDirectorFilm implements SatisfactionInterface  {


    public SatisfactionByDateAndProductCount(Director director, Integer productСount, LocalDate date,  Boolean ifBefore) {
        super(director, productСount, date, ifBefore);
    }

    public Boolean satisfy() {
        return ValidatorDirectorFilm.satisfyByDateAndProductCount(super.director, super.productСount,
                super.date, super.ifBefore);
    }

}
