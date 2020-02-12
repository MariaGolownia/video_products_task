package by.godel.video.app.entity;
import by.godel.video.app.entity.en_um.Genre;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * VideoProductModel Object.
 *
 * Various attributes of video product and related behaviour.
 *
 * @author MaryiaHalaunia
 * @version 1.0
 */
public class VideoProduct extends Entity {
    private String name;
    private LocalDate release_date;
    private List<Genre> genreValues = new ArrayList< Genre >();
    private Integer id_director;

    public VideoProduct(String name, LocalDate release_date, List<Genre> genreValues, Integer id_director) {
        this.name = name;
        this.release_date = release_date;
        this.genreValues = genreValues;
        this.id_director = id_director;
    }


    public VideoProduct(String name, LocalDate release_date, Genre genre, Integer id_director) {
        this.name = name;
        this.release_date = release_date;
        List<Genre> genreValuesTemp = new ArrayList< Genre >();
        genreValuesTemp.add(genre);
        this.genreValues = genreValuesTemp;
        this.id_director = id_director;
    }

    public VideoProduct(String name, LocalDate release_date, List<Genre> genreValues) {
        this.name = name;
        this.release_date = release_date;
        this.genreValues = genreValues;
        this.id_director = null;
    }

    public VideoProduct(String name, LocalDate release_date, Genre genre) {
        this.name = name;
        this.release_date = release_date;
        List<Genre> genreValuesTemp = new ArrayList< Genre >();
        genreValuesTemp.add(genre);
        this.genreValues = genreValuesTemp;
        this.id_director = null;
    }

    public VideoProduct(String name, LocalDate release_date) {
        this.name = name;
        this.release_date = release_date;
        this.id_director = null;
    }

    public VideoProduct() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getRelease_date() {
        return release_date;
    }

    public void setRelease_date(LocalDate release_date) {
        this.release_date = release_date;
    }

    public List<Genre> getGenreValues() {
        return genreValues;
    }

    public String getGenreValuesStr() {
        String genresStr = "";
        for (int i = 0; i < genreValues.size(); i++) {
            genresStr += genreValues.get(i) + "/";
        }
        return genresStr;
    }

    public void setGenreValuesStr(String strDB) {
        List<String> genreValuesTemp = Arrays.asList(strDB.split("/"));
        List<Genre> genreValuesNew = new ArrayList< Genre >();
        for (int i = 0; i < genreValuesTemp.size(); i++) {
            Genre genreTemp = Genre.valueOf(genreValuesTemp.get(i).toUpperCase());
            genreValuesNew.add(genreTemp);
        }
        this.genreValues = genreValuesNew;
    }

    public void setGenreValues(List<Genre> genreValues) {
        this.genreValues = genreValues;
    }

    public void setGenreValues(Genre ... genreValues) {
       List<Genre> genreValuesTemp = new ArrayList< Genre >();
        genreValuesTemp = Arrays.asList(genreValues);
        this.genreValues = genreValuesTemp;
    }
    public Integer getId_director() {
        return id_director;
    }

    public void setId_director(Integer id_director) {
        this.id_director = id_director;
    }

    @Override
    public String toString() {
        return "VideoProduct: " +
                "name='" + name + '\'' +
                ", release_date='" + release_date +
                "', genreValues=" + genreValues +
                ", id_director=" + id_director;
    }
}
