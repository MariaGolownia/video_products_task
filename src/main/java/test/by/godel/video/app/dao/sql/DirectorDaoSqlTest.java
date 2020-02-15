package test.by.godel.video.app.dao.sql;
import by.godel.video.app.dao.sql.DaoSql;
import by.godel.video.app.entity.Director;
import by.godel.video.app.service.FactoryService;
import by.godel.video.app.service.impl.DirectorServiceImpl;
import org.junit.Assert;
import java.time.LocalDate;
import java.util.List;

public class DirectorDaoSqlTest {
    DirectorServiceImpl directorService = FactoryService.getInstance().get(DaoSql.DirectorDao);

    @org.junit.Test
    public void insert() {
        Director director = new Director("FirstNameTest", "LastNameTest",
                LocalDate.of(1951, 03,01));
        Integer directorIdDB = directorService.save(director);
        Director directorSample = directorService.findById(directorIdDB);
        Assert.assertEquals(director, directorSample);
    }

    @org.junit.Test
    public void readById() {
        Director directorSample = new Director("Philipp", "Stiolzl",
                LocalDate.of(1967, 01,01));
        Director directorReadById = directorService.findById(1);
        Assert.assertEquals(directorSample.getFirst_name(), directorSample.getFirst_name());
        Assert.assertEquals(directorSample.getLast_name(), directorSample.getLast_name());
        Assert.assertEquals(directorSample.getBirth_date(), directorSample.getBirth_date());
    }

    @org.junit.Test
    public void readAll() {
        List<Director> directorList = directorService.findAll();
        Assert.assertEquals(178, directorList);
    }

    @org.junit.Test
    public void update() {
        Director director = directorService.findById(1363);
        director.setFirst_name("Tom");
        directorService.update(director);
        String nameNew = directorService.findById(1363).getFirst_name();
        Assert.assertEquals("Tom", nameNew);
    }

    @org.junit.Test
    public void delete() {
        Director directorBefore = directorService.findById(1362);
        directorService.delete(1362);
        Director director = directorService.findById(1362);
        Assert.assertEquals(null, director.getFirst_name());
        Assert.assertEquals(null, director.getLast_name());
        Assert.assertEquals(null, director.getBirth_date());
    }
}