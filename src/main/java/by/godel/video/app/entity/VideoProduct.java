package by.godel.video.app.entity;
import by.godel.video.app.entity.en_um.Genre;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    //private Integer id_director;
    private List<Integer> directorListId = new ArrayList<>();

    public VideoProduct(String name, LocalDate release_date, List<Genre> genreValues, List <Integer> directorListId) {
        this.name = name;
        this.release_date = release_date;
        this.genreValues = genreValues;
        this.directorListId = directorListId;
    }

    public VideoProduct(String name, LocalDate release_date, Genre genreValue, List <Integer> directorListId) {
        this.name = name;
        this.release_date = release_date;
        List<Genre> genreValuesTemp = new ArrayList< Genre >();
        genreValuesTemp.add(genreValue);
        this.genreValues = genreValuesTemp;
        this.directorListId = directorListId;
    }

    public VideoProduct(String name, LocalDate release_date, List<Genre> genreValues, Integer id_director) {
        this.name = name;
        this.release_date = release_date;
        this.genreValues = genreValues;
        List<Integer> directorListTemp = new ArrayList<>();
        directorListTemp.add(id_director);
        this.directorListId = directorListTemp;
    }


    public VideoProduct(String name, LocalDate release_date, Genre genre, Integer id_director) {
        this.name = name;
        this.release_date = release_date;
        List<Genre> genreValuesTemp = new ArrayList< Genre >();
        genreValuesTemp.add(genre);
        this.genreValues = genreValuesTemp;
        List<Integer> directorListTemp = new ArrayList<>();
        directorListTemp.add(id_director);
        this.directorListId = directorListTemp;
    }

    public VideoProduct(String name, LocalDate release_date, List<Genre> genreValues) {
        this.name = name;
        this.release_date = release_date;
        this.genreValues = genreValues;
    }

    public VideoProduct(String name, LocalDate release_date, Genre genre) {
        this.name = name;
        this.release_date = release_date;
        List<Genre> genreValuesTemp = new ArrayList< Genre >();
        genreValuesTemp.add(genre);
        this.genreValues = genreValuesTemp;
    }

    public VideoProduct(String name, LocalDate release_date) {
        this.name = name;
        this.release_date = release_date;
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

    public List<Integer> getDirectorListId() {
        return directorListId;
    }

    public void setDirectorListId(List<Integer> directorListIdNew) {
        this.directorListId = directorListIdNew;
    }

    public void setOneDirectorList(Integer directorIdNew) {
        List <Integer> directorListIdNew = new ArrayList<>();
        directorListIdNew.add(directorIdNew);
        this.directorListId = directorListIdNew;
    }


    public void setDirectorListIdFromDirectorList(List<Director> directorList) {
        List <Integer> directorListId = new ArrayList<>();
        for (int i = 0; i < directorList.size(); i ++) {
            directorListId.add(directorList.get(i).getId());
        }
        this.directorListId = directorListId;
    }

    public void addDirector(Integer directorId) {
        this.directorListId.add(directorId);
    }

    @Override
    public String toString() {
        return "VideoProduct{" +
                "name='" + name + '\'' +
                ", release_date=" + release_date +
                ", genreValues=" + genreValues +
                ", directorListId=" + Arrays.toString(directorListId.toArray()) +
                '}';
    }
}
