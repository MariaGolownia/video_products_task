package by.godel.video.app.entity.validation;
import by.godel.video.app.entity.Director;
import by.godel.video.app.entity.Film;
import by.godel.video.app.entity.VideoProduct;
import org.apache.logging.log4j.LogManager;

import java.time.LocalDate;
import java.util.List;

public class ValidatorDirectorFilm {
    private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger();

    public static final Boolean IF_DEFAULT_SEARCH_BEFORE_DATE = true;
    public static final Integer FILM_DEFAULT_COUNT = 0;

    /**
     * Method-description of the situation when:
     * one of the directors released several films before and after a certain date
     */

    public static Boolean satisfyByDateAndProductCount(Director director, Integer productСount,
                                                       LocalDate date) {
        return satisfyByDateAndProductCount(director, productСount,
                date, IF_DEFAULT_SEARCH_BEFORE_DATE);
    }

    public static Boolean satisfyByDateAndProductCount(Director director, Integer productСount,
                                                       LocalDate date, Boolean ifBefore) {
        Boolean result = false;
        List<VideoProduct> videoProductList = director.getVideoProductList();
        Integer countTemp = 0;
        for (int i = 0; i < videoProductList.size(); i++) {
            VideoProduct productFromList = videoProductList.get(i);
            LocalDate dateProductReleaseFromList = videoProductList.get(i).getRelease_date();
            if (ifBefore.equals(IF_DEFAULT_SEARCH_BEFORE_DATE)) {
                if (dateProductReleaseFromList.isBefore(date) || dateProductReleaseFromList.equals(date)) {
                    countTemp++;
                }
            } else {
                if (dateProductReleaseFromList.isAfter(date) || dateProductReleaseFromList.equals(date)) {
                    countTemp++;
                }
            }
        }
        if (countTemp >= productСount) return !result;
        else return result;
    }


    /**
     * Method-description of the situation when:
     * one of the Directors has not released a single film yet
     */
    public static Boolean satisfyByFilmLack(Director director) {
        return satisfyByFilmCount(director, FILM_DEFAULT_COUNT);
    }

    public static Boolean satisfyByFilmCount(Director director, Integer videoProductCount) {
        Boolean result = false;
        List<VideoProduct> videoProductList = director.getVideoProductList();
        Integer countTemp = 0;
        for (int i = 0; i < videoProductList.size(); i++) {
            VideoProduct productFromList = videoProductList.get(i);
            if (productFromList instanceof Film)
                countTemp++;
        }
        if (countTemp.equals(videoProductCount)) return !result;
        else return result;
    }

    /**
     * Method-description of the situation when:
     * one of the Directors released at least N films on the same date
     */
    public static Boolean satisfyByFilmCountInOneDate(Director director, LocalDate date,
                                                      Integer productСount) {
        Boolean result = false;
        List<VideoProduct> videoProductList = director.getVideoProductList();
        Integer countTemp = 0;

        for (int i = 0; i < videoProductList.size(); i++) {
            VideoProduct productFromList = videoProductList.get(i);
            LocalDate dateProductReleaseFromList = productFromList.getRelease_date();
            if (dateProductReleaseFromList.equals(date))
                countTemp++;
        }
        if (countTemp == productСount) return !result;
        else return result;
    }


    /**
     * Method-description of the situation when:
     * one of the Directors produced N films
     */
    public static Boolean satisfyByAccurateFilmCount(Director director, Integer productСount) {
        Boolean result = false;
        List<VideoProduct> videoProductList = director.getVideoProductList();
        Integer countTemp = videoProductList.size();
        if (countTemp.equals(productСount)) result = true;
        return result;
    }
}
