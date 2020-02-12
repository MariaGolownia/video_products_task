package by.godel.video.app.entity.validation;
import by.godel.video.app.entity.en_um.Genre;

import java.util.List;

public class ValidatorGenre  {

    public static Boolean validateAccurateGenre(String genreStr) {
        String genreStrLowerCase = genreStr.toLowerCase().trim();
        List<String> genresList = Genre.getGenresString();
        Boolean ifGenreStrExists = false;

        for (int i = 0; i < genresList.size(); i++) {
            String genreFromList = genresList.get(i).toLowerCase();
            if (genreFromList.equals(genreStr)) {
                return !ifGenreStrExists;
            }
        }
        return ifGenreStrExists;
    }

    public static String validateApproximateGenre(String genreStr) {
        String genreStrLowerCase = genreStr.toLowerCase().trim();
        List<String> genresList = Genre.getGenresString();
        String genreStrExistsName = null;

        for (int i = 0; i < genresList.size(); i++) {
            genreStrExistsName = genresList.get(i).toLowerCase();
            if (genreStrExistsName.contains(genreStr)) {
                return genreStrExistsName;
            }
        }
        return genreStrExistsName;
    }

}
