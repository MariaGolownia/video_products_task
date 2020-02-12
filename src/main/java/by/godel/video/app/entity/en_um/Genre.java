package by.godel.video.app.entity.en_um;

import by.godel.video.app.entity.validation.ValidatorGenre;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum Genre {
    ACTION("action"),
    BLOCKBUSTER("blockbuster"),
    GANGSTER("gangster "),
    SCI_FI("sci-fi "),
    FANTASTIC("fantastic"),
    FANTASY("fantasy"),
    MUSICAL("musical"),
    WESTERN("western"),
    POST_APOCALYPTIC("post-apocalyptic"),
    DISASTER("disaster"),
    WAR("war "),
    FAMILY("family"),
    LOVE_STORY("love story"),
    ROMANTIC_FILM("romantic film"),
    ROMANTIC_COMEDY("romantic comedy"),
    COMEDY("comedy"),
    ADVENTURE("adventure"),
    HISTORY("history"),
    CRIME("crime"),
    HORROR("horror"),
    THRILLER("thriller"),
    SPY_THRILLER("spy thriller"),
    EDUCATIONAL("educational"),
    THEATRICAL("theatrical"),
    MYSTERY("mystery"),
    DRAMA("drama"),
    PSYCHOLOGICAL_DRAMA("psychological drama "),
    MELODRAMA("melodrama"),
    BIOGRAPHY("biography"),
    CARTOON("cartoon"),
    DETECTIVE("detective"),
    UNDEFINED("undefined");

    private String genre;

    private Genre(String genre) {
        this.genre = genre;
    }


    @Override
    public String toString() {
        return genre;
    }

    public Boolean equalsName(String otherGenre) {
        return genre.equals(otherGenre);
    }

    public static Genre getAccurateGenre(String genreStr) {
        Boolean ifGenreStrExists = ValidatorGenre.validateAccurateGenre(genreStr);
        if (ifGenreStrExists) {
            String genreStrLowerCase = genreStr.toLowerCase().trim();
            return Genre.valueOf(genreStrLowerCase.toUpperCase());
        } else {
            return Genre.UNDEFINED;
        }

    }

    public static Genre getApproximateGenre(String genreStr) {
        String genreStrExists = ValidatorGenre.validateApproximateGenre(genreStr);
        if (genreStrExists != null) {
            String genreStrLowerCase = genreStrExists.toLowerCase().trim();
            return Genre.valueOf(genreStrExists.toUpperCase());
        } else {
            return Genre.UNDEFINED;
        }
    }


    public static List<Genre> getGenres() {
        //List<Genre>genresList = new ArrayList<Genre>(EnumSet.allOf(Enum.class));
        //List<Genre>genresList = Arrays.asList(Genre.values());
        List<Genre> genresList = new ArrayList<>();
        genresList = Arrays.asList(Genre.values());
        return genresList;
    }


    public static List<String> getGenresString() {
        List<String> genresList = Stream.of(Genre.values())
                .map(Enum::name)
                .collect(Collectors.toList());
        return genresList;

    }

}
