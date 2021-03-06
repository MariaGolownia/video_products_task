package by.godel.video.app.controller;
import by.godel.video.app.action.validator.HumanDataValidator;
import by.godel.video.app.action.validator.IncorrectDataException;
import by.godel.video.app.dao.sql.DaoSql;
import by.godel.video.app.entity.DateConverter;
import by.godel.video.app.entity.Director;
import by.godel.video.app.entity.Film;
import by.godel.video.app.entity.VideoProduct;
import by.godel.video.app.entity.en_um.Genre;
import by.godel.video.app.entity.entityStatus.DirectorStatus;
import by.godel.video.app.entity.validation.validator_film_director.SatisfactionByAccurateFilmCount;
import by.godel.video.app.entity.validation.validator_film_director.SatisfactionByDateAndProductCount;
import by.godel.video.app.entity.validation.validator_film_director.SatisfactionByFilmCountInOneDate;
import by.godel.video.app.service.FactoryService;
import by.godel.video.app.service.ServiceException;
import by.godel.video.app.service.impl.DirectorServiceImpl;
import by.godel.video.app.service.impl.FilmServiceImpl;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Runner {

    public static void main(String[] args) {
        FilmServiceImpl filmService = FactoryService.getInstance().get(DaoSql.FilmDao);
        DirectorServiceImpl directorService = FactoryService.getInstance().get(DaoSql.DirectorDao);
//--------------------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------------
// РЕШЕНИЕ ТАСКА 2.1:
// Таблицы должны заполняться данными так, чтобы данные описывали ситуации, когда:
//- один из режиссеров выпустил несколько фильмов до какой-либо даты и после нее
//--------------------------------------------------------------------------------------------------------------
// Logic 1 of task 2.1 (without DB)
//--------------------------------------------------------------------------------------------------------------
// Оределение выполняемости условия без уже внесенных данных в БД
// По логике 1 данные о фильме добавятся в таблицу, если режисер выпустил определенное
// количество фильмов до определенной даты (без учета данных БД)
//--------------------------------------------------------------------------------------------------------------
//        Film film1 = new Film("Short movie1", LocalDate.of(1780, 02,11), Genre.BIOGRAPHY, 1);
//        Film film2 = new Film("Short movie2", LocalDate.of(2011, 02,11), Genre.BIOGRAPHY, 1);
//        List<VideoProduct> videoProducts = new ArrayList<>();
//        videoProducts.add(film1);
//        videoProducts.add(film2);
//        Director director = directorService.findById(film1.getDirectorListId().get(0));
//        SatisfactionByDateAndProductCount satisfactionByDateAndProductCount = new SatisfactionByDateAndProductCount(director,
//                10, LocalDate.of(1782, 02,11), true);
//        List<DirectorStatus> directorStatusList = new ArrayList<>();
//        try {
//            directorStatusList = filmService.insertConditionWithoutDB(videoProducts,
//                    satisfactionByDateAndProductCount, true);
//        } catch (ServiceException e) {
//            e.printStackTrace();
//        }
//        for (int i = 0; i < directorStatusList.size(); i++)
//        System.out.println(directorStatusList.get(i).toString());

//--------------------------------------------------------------------------------------------------------------
// Logic 2 of task 2.1 (with DB)
//--------------------------------------------------------------------------------------------------------------
// определение выполняемости условия с учетом данных в БД
// По разработанной логике данные о фильме добавятся в таблицу, если режисер выпустил определенное
// количество фильмов до определенной даты (с учетом данных БД)
//--------------------------------------------------------------------------------------------------------------
//        Film film1 = new Film("Long movie 3", LocalDate.of(2011, 01, 01), Genre.ACTION, 2);
//        Film film2 = new Film("Long movie 4", LocalDate.of(2012, 01, 01), Genre.BLOCKBUSTER, 2);
//        List<VideoProduct> videoProducts = new ArrayList<>();
//        videoProducts.add(film1);
//        videoProducts.add(film2);
//        Director director = directorService.findById(film1.getDirectorListId().get(0));
//        SatisfactionByDateAndProductCount satisfactionByDateAndProductCount = new SatisfactionByDateAndProductCount(director,
//                10, LocalDate.of(2013, 02,11), true);
//        List<DirectorStatus> directorStatusList = new ArrayList<>();
//        try {
//            directorStatusList = filmService.insertConditionWithDB(videoProducts,
//                    satisfactionByDateAndProductCount, true);
//        } catch (ServiceException e) {
//            e.printStackTrace();
//        }
//        for (int i = 0; i < directorStatusList.size(); i++)
//        System.out.println(directorStatusList.get(i).toString());

//--------------------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------------
// РЕШЕНИЕ ТАСКА 2.2:
// Таблицы должны заполняться данными так, чтобы данные описывали ситуации, когда:
//- один из режиссеров пока еще не выпустил ни одного фильма
// Вставляем, если не еще выпустил ни одного фильма (данные БД)
// Можно использовать для условия любого количества фильмов в БД
//--------------------------------------------------------------------------------------------------------------
//        Film film1 = new Film("Successful movie1", LocalDate.of(2015, 01,01), Genre.BIOGRAPHY);
//        Film film2 = new Film("Successful movie22", LocalDate.of(2015, 01,01), Genre.BIOGRAPHY);
//        List<VideoProduct> videoProducts = new ArrayList<>();
//        videoProducts.add(film1);
//        videoProducts.add(film2);
//        Director director = new Director("Sid88", "Sidorov", LocalDate.of(1951, 03,01));
//        Integer directorIdDB = directorService.save(director);
//        Director directorDB = directorService.findById(directorIdDB);
//        SatisfactionByAccurateFilmCount satisfactionByAccurateFilmCount = new SatisfactionByAccurateFilmCount(0, directorDB);
//        List<DirectorStatus> directorStatusList = new ArrayList<>();
//        try {
//            directorStatusList = filmService.insertConditionOnlyDB(videoProducts,
//            satisfactionByAccurateFilmCount, true);
//        } catch (ServiceException e) {
//            e.printStackTrace();
//        }
//        for (int i = 0; i < directorStatusList.size(); i++)
//            System.out.println(directorStatusList.get(i).toString());

//--------------------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------------
// РЕШЕНИЕ ТАСКА 2.3:
// Таблицы должны заполняться данными так, чтобы данные описывали ситуации, когда:
//- один из режиссеров выпустил хотя бы 2 фильма в одну дату
//--------------------------------------------------------------------------------------------------------------
// Logic 1 of task 2.3 (without DB)
//--------------------------------------------------------------------------------------------------------------
// Вставляем, если выпустил необходимое количество фильмов (без учета БД)
//--------------------------------------------------------------------------------------------------------------
//        Film film1 = new Film("New movie WWWW", LocalDate.of(2015, 01,01), Genre.BIOGRAPHY);
//        Film film2 = new Film("New movie WWWWW", LocalDate.of(2015, 01,01), Genre.BIOGRAPHY);
//        Film film3 = new Film("New movie WWWWWW", LocalDate.of(2015, 01,01), Genre.BIOGRAPHY);
//        List<VideoProduct> videoProducts = new ArrayList<>();
//        videoProducts.add(film1);
//        videoProducts.add(film2);
//        videoProducts.add(film3);
//        Director director = new Director("TimWWWWW", "Timovich", LocalDate.of(1951, 03,01));
//        Integer directoId = directorService.save(director);
//        director.setId(directoId);
//        SatisfactionByFilmCountInOneDate satisfactionByFilmCountInOneDate = new SatisfactionByFilmCountInOneDate(director, 3, LocalDate.of(2015,01,01));
//        List<DirectorStatus> directorStatusList = new ArrayList<>();
//        try {
//            directorStatusList = filmService.insertConditionWithoutDB(videoProducts,
//            satisfactionByFilmCountInOneDate, true);
//        } catch (ServiceException e) {
//            e.printStackTrace();
//        }
//        for (int i = 0; i < directorStatusList.size(); i++)
//            System.out.println(directorStatusList.get(i).toString());

//--------------------------------------------------------------------------------------------------------------
// Logic 2 of task 2.3 (with DB)
//--------------------------------------------------------------------------------------------------------------
// определение выполняемости условия с учетом данных в БД-----------------------------------------------------
// По разработанной логике данные о фильме добавятся в таблицу, если режисер выпустил определенное
// количество фильмов в определенную дату
//--------------------------------------------------------------------------------------------------------------

//        Film film1BD = new Film("Add movieEEE", LocalDate.of(2016, 01,01), Genre.BIOGRAPHY);
//        Film film2 = new Film("Add movie 1EEEE", LocalDate.of(2016, 01,01), Genre.BIOGRAPHY);
//        Film film3 = new Film("Add movie 2EEEEE", LocalDate.of(2016, 01,01), Genre.BIOGRAPHY);
//        Director director = new Director("TimEEEEE", "Timovich", LocalDate.of(1951, 03,01));
//        director.addVideoProductList(film1BD);
//        Integer directoId = directorService.save(director);
//        director.setId(directoId);
//        film2.setOneDirectorList(directoId);
//        film3.setOneDirectorList(directoId);
//        List<VideoProduct> videoProducts = new ArrayList<>();
//        videoProducts.add(film2);
//        videoProducts.add(film3);
//        SatisfactionByFilmCountInOneDate satisfactionByFilmCountInOneDate = new SatisfactionByFilmCountInOneDate(director, 0, LocalDate.of(2016,01,01));
//        List<DirectorStatus> directorStatusList = new ArrayList<>();
//        try {
//            directorStatusList = filmService.insertConditionWithDB(videoProducts,
//            satisfactionByFilmCountInOneDate, true);
//        } catch (ServiceException e) {
//            e.printStackTrace();
//        }
//        for (int i = 0; i < directorStatusList.size(); i++)
//            System.out.println(directorStatusList.get(i).toString());

//--------------------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------------
// РЕШЕНИЕ ТАСКА 2.4:
// Таблицы должны заполняться данными так, чтобы данные описывали ситуации, когда:
//- один из режиссеров выпустил только один (N) фильм
//--------------------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------------
// Logic 1 of task 2.4 (without DB)
//--------------------------------------------------------------------------------------------------------------
// Вставляем, если выпустил необходимое количество фильмов (без учета БД)
//--------------------------------------------------------------------------------------------------------------
//        Film film1 = new Film("New movie 444", LocalDate.of(2015, 01, 01), Genre.BIOGRAPHY);
//        List<VideoProduct> videoProducts = new ArrayList<>();
//        videoProducts.add(film1);
//        Director director = new Director("TimWWWWW", "Timovich", LocalDate.of(1951, 03, 01));
//        Integer directoId = directorService.save(director);
//        director.setId(directoId);
//        SatisfactionByAccurateFilmCount satisfactionByAccurateFilmCount = new SatisfactionByAccurateFilmCount(1, director);
//        List<DirectorStatus> directorStatusList = new ArrayList<>();
//        try {
//            directorStatusList = filmService.insertConditionWithoutDB(videoProducts,
//            satisfactionByAccurateFilmCount, true);
//        } catch (ServiceException e) {
//            e.printStackTrace();
//        }
//        for (int i = 0; i < directorStatusList.size(); i++)
//            System.out.println(directorStatusList.get(i).toString());

//--------------------------------------------------------------------------------------------------------------
// Logic 2 of task 2.4 (withDB)
//--------------------------------------------------------------------------------------------------------------
// Вставляем, если выпустил необходимое количество фильмов (с  учетом БД)
//--------------------------------------------------------------------------------------------------------------
//        Film film1 = new Film("New movie 555555", LocalDate.of(2015, 01, 01), Genre.BIOGRAPHY);
//        List<VideoProduct> videoProducts = new ArrayList<>();
//        videoProducts.add(film1);
//        //Director director = directorService.findById(1);
//        Director director = new Director("jjjj", "Timovich", LocalDate.of(1951, 03, 01));
//        Film film2 = new Film("New movie dbbbbb jjjj22", LocalDate.of(2015, 01, 01), Genre.BIOGRAPHY);
//        Film film3 = new Film("New movie dbbbbb jjjj333", LocalDate.of(2015, 01, 01), Genre.BIOGRAPHY);
//        director.addVideoProductList(film2, film3);
//        Integer directoId = directorService.save(director);
//        director.setId(directoId);
//        SatisfactionByAccurateFilmCount satisfactionByAccurateFilmCount = new SatisfactionByAccurateFilmCount(3, director);
//        List<DirectorStatus> directorStatusList = new ArrayList<>();
//        try {
//            directorStatusList = filmService.insertConditionWithDB(videoProducts,
//            satisfactionByAccurateFilmCount, true);
//        } catch (ServiceException e) {
//            e.printStackTrace();
//        }
//        for (int i = 0; i < directorStatusList.size(); i++)
//            System.out.println(directorStatusList.get(i).toString());
//--------------------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------------
// получение фильма по id
//Film film = (Film) filmService.findById(17);
//System.out.println(film.toString());
//--------------------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------------
// удаление фильма по id
//filmService.delete(1364);
//--------------------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------------
// валидация данных: имя
//--------------------------------------------------------------------------------------------------------------
//        try {
//            HumanDataValidator.checkStr("Tom");
//        } catch (IncorrectDataException e) {
//            e.printStackTrace();
//        }
//--------------------------------------------------------------------------------------------------------------
// валидация данных: дата рождения
//--------------------------------------------------------------------------------------------------------------
//        try {
//            String dateStr = "10-10-2000";
//            HumanDataValidator.checkFormateDate(dateStr);
//            HumanDataValidator.checkMinDateBirth(dateStr);
//            HumanDataValidator.checkMaxDateBirth(dateStr);
//        } catch (IncorrectDataException e) {
//            e.printStackTrace();
//        }
//--------------------------------------------------------------------------------------------------------------
// Трансформация строки времени
//--------------------------------------------------------------------------------------------------------------
//LocalDate localDate = DateConverter.converterDateFromString("10-10-2020");
//System.out.println(localDate.toString());
//--------------------------------------------------------------------------------------------------------------

        // Промежуточные вызовы
//        // условие TRUE
//        SatisfactionByDateAndProductCount satisfactionByDateAndProductCount = new SatisfactionByDateAndProductCount();
//        satisfactionByDateAndProductCount.setProductСount(1);
//        satisfactionByDateAndProductCount.setDate(LocalDate.of(2020,01,01));
//        satisfactionByDateAndProductCount.setIfBefore(true);
//        // фильм, который хотим добавить
//        FilmServiceImpl filmService = FactoryService.getInstance().get(DaoSql.FilmDao);
//        Film filmTest = new Film("testFilmCondition", LocalDate.of(2000, 1,01));
//        filmTest.setGenreValues(Genre.ADVENTURE, Genre.ACTION);
//        // режисер
//        DirectorServiceImpl directorService = FactoryService.getInstance().get(DaoSql.DirectorDao);
//        Director director = new Director();
//        director = directorService.findById(1);
//        // действие
//        Integer idNewFilm = filmService.save(filmTest, director, satisfactionByDateAndProductCount);
//        System.out.println(idNewFilm);


        // условие FALSE
//        SatisfactionByDateAndProductCount satisfactionByDateAndProductCount2 = new SatisfactionByDateAndProductCount();
//        satisfactionByDateAndProductCount2.setProductСount(10);
//        satisfactionByDateAndProductCount2.setDate(LocalDate.of(2020,01,01));
//        satisfactionByDateAndProductCount2.setIfBefore(true);
//        // фильм, который хоти добавить
//        FilmServiceImpl filmService2 = FactoryService.getInstance().get(DaoSql.FilmDao);
//        Film filmTest2 = new Film("testFilmCondition", LocalDate.of(2000, 1,01));
//        filmTest2.setGenreValues(Genre.ADVENTURE, Genre.ACTION);
//        // режисер
//        DirectorServiceImpl directorService2 = FactoryService.getInstance().get(DaoSql.DirectorDao);
//        Director director2 = new Director();
//        director2 = directorService2.findById(1);
//        // действие
//        Integer idNewFilm2 = filmService2.save(filmTest2, director2, satisfactionByDateAndProductCount2);
//        System.out.println(idNewFilm2);

        // -------------------------Логика 2-----------------------------------------------------
        // Можно сделать так, что данные всегда будут добавляться, но будет возвращаться лист
        // режиссеров, которые выпустили несколько фильмов до какой-либо даты и после нее

//        Director directorTestCount = new Director();
//        directorTestCount.setFirst_name("James");
//        directorTestCount.setLast_name("Cameron");
//        directorTestCount.setBirth_date(LocalDate.of(1954, 8, 16));
//        // условие
//        SatisfactionByAccurateFilmCount satisfactionByAccurateFilmCount = new SatisfactionByAccurateFilmCount(2, directorTestCount);
//
//        // фильм, который хотим добавить
//        FilmServiceImpl filmService = FactoryService.getInstance().get(DaoSql.FilmDao);
//        Film filmTest = new Film("super", LocalDate.of(1999, 1,01));
//        filmTest.setGenreValues(Genre.ADVENTURE, Genre.ACTION);
//        filmTest.setId_director(2);
//
//        // действие
//        List<Director> directorList = new ArrayList<>();
//        DirectorServiceImpl directorService = FactoryService.getInstance().get(DaoSql.DirectorDao);
//        directorList = directorService.save(filmTest, satisfactionByAccurateFilmCount);
//        System.out.println(directorList);

        //      //--------------------------------------------------------------------------------------------------------------
        // Заполнение/обработка трех таблиц одновременно
//        FilmServiceImpl filmService = FactoryService.getInstance().get(DaoSql.FilmDao);
//        Film filmTest = new Film("testFilm", LocalDate.of(2020, 1,01
//        filmTest.setGenreValues(Genre.ADVENTURE, Genre.ACTION);
//        Director director = new Director("test", "test", LocalDate.of(1950, 1,01));));
//        Integer idDirectorFilm = filmService.save(filmTest, director);
//        System.out.println(idDirectorFilm);

//      //--------------------------------------------------------------------------------------------------------------
        // Заполнение director
//        DirectorServiceImpl directorService = FactoryService.getInstance().get(DaoSql.DirectorDao);
//        Director director = new Director("test", "test", LocalDate.of(1950, 1,01));
//        List<VideoProduct> videoProductList = new ArrayList<>();
//        Integer intResultDirectorId = directorService.save(director);
//        System.out.println(intResultDirectorId.toString());
//      //--------------------------------------------------------------------------------------------------------------
//      //--------------------------------------------------------------------------------------------------------------
        // Заполнение film
//        FilmServiceImpl filmService = FactoryService.getInstance().get(DaoSql.FilmDao);
//        Film filmTest = new Film("testFilm", LocalDate.of(2020, 1,01));
//        filmTest.setGenreValues(Genre.ADVENTURE);
//        Integer intResultFilmId = directorService.save(director);
//        System.out.println(intResultFilmId.toString());
//      //--------------------------------------------------------------------------------------------------------------


        // ПРОМЕЖУТОЧНЫЕ ПРОВЕРКИ

//        //--------------------------------------------------------------------------------------------------------------
//        // create the new film
//        VideoProduct videoProductTest = new Film();
//        videoProductTest.setName("The Terminator");
//        videoProductTest.setRelease_date(LocalDate.of(1984, 10, 26));
//        videoProductTest.setGenreValues(Genre.FANTASTIC, Genre.ACTION, Genre.THRILLER);
//        System.out.println(videoProductTest.toString());
//        System.out.println("-----------------------------------------------------------------------------------------");
//        //--------------------------------------------------------------------------------------------------------------
//        // create the new director
//        Director directorTest = new Director();
//        directorTest.setFirst_name("James");
//        directorTest.setLast_name("Cameron");
//        directorTest.setBirth_date(LocalDate.of(1954, 8, 16));
//        directorTest.setVideoProductList(videoProductTest);
//        System.out.println(directorTest.toString());
//        System.out.println("-----------------------------------------------------------------------------------------");
//        //--------------------------------------------------------------------------------------------------------------
//        // test a genre
//        System.out.println(Genre.FANTASTIC);
//        System.out.println(Genre.getAccurateGenre("fantastic"));
//        System.out.println(Genre.getAccurateGenre("fant"));
//        System.out.println(Genre.getApproximateGenre("fant"));
//        System.out.println("-----------------------------------------------------------------------------------------");
//        //--------------------------------------------------------------------------------------------------------------
//        // Проверка ситуации 1
//        // Описание ситуации, когда:
//        // один из режиссеров выпустил несколько фильмов до какой-либо даты и после нее
//        // Должно вернуть true, если выпоняет условию количества фильмов, дате, условию до или после
//        //--------------------------------------------------------------------------------------------------------------
//        Boolean resultTestCondition1_0 = ValidatorDirectorFilm.satisfyByDateAndProductCount(directorTest,
//                1, LocalDate.of(1986, 01, 01), true);
//        System.out.println(resultTestCondition1_0);
//        Boolean resultTestCondition1_2 = ValidatorDirectorFilm.satisfyByDateAndProductCount(directorTest,
//                2, LocalDate.of(1986, 01, 01), true);
//        System.out.println(resultTestCondition1_2);
//        Boolean resultTestCondition1_3 = ValidatorDirectorFilm.satisfyByDateAndProductCount(directorTest,
//                1, LocalDate.of(1986, 01, 01), false);
//        System.out.println(resultTestCondition1_3);
//        System.out.println("-----------------------------------------------------------------------------------------");
//        //--------------------------------------------------------------------------------------------------------------
//        // Проверка ситуации 2
//        // Описание ситуации, когда:
//        // один из режиссеров пока еще не выпустил ни одного фильма
//        // Должно вернуть true, если выпоняет условию
//        //--------------------------------------------------------------------------------------------------------------
//        Boolean resultTestCondition2_0 = ValidatorDirectorFilm.satisfyByFilmCount(directorTest,
//                0);
//        System.out.println(resultTestCondition2_0);
//        Boolean resultTestCondition2_1 = ValidatorDirectorFilm.satisfyByFilmCount(directorTest,
//                1);
//        System.out.println(resultTestCondition2_1);
//        Boolean resultTestCondition2_2 = ValidatorDirectorFilm.satisfyByFilmCount(directorTest,
//                2);
//        System.out.println(resultTestCondition2_2);
//        System.out.println("-----------------------------------------------------------------------------------------");
//        //--------------------------------------------------------------------------------------------------------------
//        // Проверка ситуации 3
//        // Описание ситуации, когда:
//        // один из режиссеров выпустил хотя бы 2 (N) фильмов в одну дату
//        // Должно вернуть true, если выпоняет условию
//        //--------------------------------------------------------------------------------------------------------------
//        Boolean resultTestCondition3_0 = ValidatorDirectorFilm.satisfyByFilmCountInOneDate(directorTest,
//                LocalDate.of(1984, 10, 26), 1);
//        System.out.println(resultTestCondition3_0);
//        Boolean resultTestCondition3_1 = ValidatorDirectorFilm.satisfyByFilmCountInOneDate(directorTest,
//                LocalDate.of(1984, 10, 26), 2);
//        System.out.println(resultTestCondition3_1);
//        VideoProduct videoProductTest2 = new Film();
//        videoProductTest2.setName("Mini-movie");
//        videoProductTest2.setRelease_date(LocalDate.of(1984, 10, 26));
//        videoProductTest2.setGenreValues(Genre.FANTASTIC);
//        directorTest.addVideoProductList(videoProductTest2);
//        Boolean resultTestCondition3_2 = ValidatorDirectorFilm.satisfyByFilmCountInOneDate(directorTest,
//                LocalDate.of(1984, 10, 26), 2);
//        System.out.println(resultTestCondition3_2);
//        System.out.println("-----------------------------------------------------------------------------------------");
//        //--------------------------------------------------------------------------------------------------------------
//        // Проверка ситуации 4
//        // Описание ситуации, когда:
//        // один из режиссеров выпустил только один (N) фильм
//        // Должно вернуть true, если выпоняет условию
//        //--------------------------------------------------------------------------------------------------------------
//        Boolean resultTestCondition4_0 = ValidatorDirectorFilm.satisfyByFilmCount(directorTest,
//                1);
//        System.out.println(resultTestCondition4_0);
//        Boolean resultTestCondition4_1 = ValidatorDirectorFilm.satisfyByFilmCount(directorTest,
//                2);
//        System.out.println(resultTestCondition4_1);
//        Boolean resultTestCondition4_2 = ValidatorDirectorFilm.satisfyByFilmCount(directorTest,
//                3);
//        System.out.println(resultTestCondition4_2);
//        System.out.println("-----------------------------------------------------------------------------------------");


//        //--------------------------------------------------------------------------------------------------------------
        // Достаем из БД Director и его фильмы
//        //--------------------------------------------------------------------------------------------------------------
//        DirectorServiceImpl directorService = FactoryService.getInstance().get(DaoSql.DirectorDao);
//        Director director = new Director();
//        director = directorService.findById(1);
//        System.out.println(director.toString());


//        //--------------------------------------------------------------------------------------------------------------
        // Достаем из БД Film и id Director
//        //--------------------------------------------------------------------------------------------------------------
//        FilmServiceImpl filmService = FactoryService.getInstance().get(DaoSql.FilmDao);
//        Film film = new Film();
//        film = filmService.findById(2);
//        System.out.println(film.toString());
//        //--------------------------------------------------------------------------------------------------------------

    }
}
