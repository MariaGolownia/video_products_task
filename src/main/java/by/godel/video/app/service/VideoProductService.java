package by.godel.video.app.service;
import by.godel.video.app.entity.Film;
import by.godel.video.app.entity.VideoProduct;
import java.util.List;

public interface VideoProductService {

    VideoProduct findById(Integer videoProductId);

    List<VideoProduct> findAll();

    Integer save (Film filmNew);

    void update (VideoProduct videoProduct);

    void delete (Integer videoProductId);

}
