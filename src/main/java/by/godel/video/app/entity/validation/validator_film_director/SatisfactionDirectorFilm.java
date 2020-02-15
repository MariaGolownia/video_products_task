package by.godel.video.app.entity.validation.validator_film_director;

import by.godel.video.app.entity.Director;

import java.time.LocalDate;

/**
 * Abstract Class-Marker.
 *
 * @author Maryia_Halaunia
 * @version 1.0
 */
abstract public class SatisfactionDirectorFilm {
    LocalDate date;
    Integer productСount;
    Director director;
    Boolean ifBefore;

    private Boolean ifSatisfy;

    abstract public Boolean satisfy();

    public SatisfactionDirectorFilm(Integer productСount, Director director) {
        this.productСount = productСount;
        this.director = director;
    }

    public SatisfactionDirectorFilm(Director director, Integer productСount, LocalDate date) {
        this.date = date;
        this.productСount = productСount;
        this.director = director;
    }


    public SatisfactionDirectorFilm(Director director, Integer productСount, LocalDate date,  Boolean ifBefore) {
        this.date = date;
        this.productСount = productСount;
        this.director = director;
        this.ifBefore = ifBefore;
    }

    public Boolean getIfSatisfy() {
        return ifSatisfy;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getProductСount() {
        return productСount;
    }

    public void setProductСount(Integer productСount) {
        this.productСount = productСount;
    }

    public Director getDirector() {
        return director;
    }

    public void setDirector(Director director) {
        this.director = director;
    }

    public Boolean getIfBefore() {
        return ifBefore;
    }

    public void setIfBefore(Boolean ifBefore) {
        this.ifBefore = ifBefore;
    }

    public void setIfSatisfy(Boolean ifSatisfy) {
        this.ifSatisfy = ifSatisfy;
    }
}
