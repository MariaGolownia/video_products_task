package test.by.godel.video.app.service.impl;
import by.godel.video.app.dao.sql.DaoSql;
import by.godel.video.app.entity.Director;
import by.godel.video.app.entity.Film;
import by.godel.video.app.entity.VideoProduct;
import by.godel.video.app.entity.en_um.Genre;
import by.godel.video.app.service.FactoryService;
import by.godel.video.app.service.ServiceException;
import by.godel.video.app.service.impl.DirectorServiceImpl;
import by.godel.video.app.service.impl.FilmServiceImpl;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.logging.log4j.core.layout.GelfLayout;
import org.junit.Assert;
import org.junit.Test;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class FilmServiceImplTest {
    DirectorServiceImpl directorService = FactoryService.getInstance().get(DaoSql.DirectorDao);
    FilmServiceImpl filmService = FactoryService.getInstance().get(DaoSql.FilmDao);

    @Test
    public void findById() {
    }

    @Test
    public void findByIdDirecror() {
    }

    @Test
    public void findAll() {
    }

    @Test
    public void insert() {
    }

    @Test
    public void insert1() {
    }

    @Test
    public void insertConditionWithDB() {
    }

    @Test
    public void insertConditionWithDB1() {
    }

    @Test
    public void insertConditionOnlyDB() {
    }


    @Test
    public void checkIfVideoProductOfOneDirector() {
        Boolean requestedResult = true;
        Boolean result = false;
                Director director = directorService.findById(1);
        List<VideoProduct> videoProductList = new ArrayList<>();
        Film film1 = new Film("New film 1", LocalDate.of(2015,12,  01),
                Genre.ADVENTURE, 1);
        Film film2 = new Film("New film 2", LocalDate.of(2015,12,  01),
                Genre.ADVENTURE, 1);
        videoProductList.add(film1);
        videoProductList.add(film2);
        try {
            result = filmService.checkIfVideoOfOneDirectorOrNull(videoProductList, director);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(requestedResult, result);
    }

    @Test
    public void checkIfDiretorsIdExists() {
        List <Integer> directorIdListResult = new ArrayList<>();
        List<Integer> directorListId = new ArrayList<>();
        directorListId.add(1);
        directorListId.add(1);
        Film film1 = new Film("New film 1", LocalDate.of(2015,12,  01),
                Genre.ADVENTURE, directorListId);
        try {
            List <Director> directorListResult =  filmService.checkIfDiretorsIdExists(film1);
            if (directorListResult != null) {
                for (int i = 0; i < directorListResult.size(); i++) {
                    directorIdListResult.add(directorListResult.get(i).getId());
                }
            }
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        Arrays.sort(directorListId.toArray());
        Arrays.sort(directorIdListResult.toArray());
        Assert.assertEquals(directorListId, directorIdListResult);
    }

    @Test
    public void checkIfDiretorExists() {
    }

    @Test
    public void insertConditionWithoutDB() {
    }


    @Test
    public void update() {
    }

    @Test
    public void delete() {
    }
}