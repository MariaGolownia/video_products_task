package by.godel.video.app.entity.entityStatus;

import by.godel.video.app.entity.Director;
import by.godel.video.app.entity.Film;
import by.godel.video.app.entity.VideoProduct;
import com.sun.org.apache.xpath.internal.operations.Bool;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DirectorStatus  {

    private Director director;
    private VideoProduct videoProduct;
    private Boolean actionSucessStatus;

    public DirectorStatus(Director director, VideoProduct videoProduct, Boolean actionSucessStatus) {
        this.director = director;
        this.videoProduct = videoProduct;
        this.actionSucessStatus = actionSucessStatus;
    }

    public Boolean getActionSucessStatus() {
        return actionSucessStatus;
    }

    public void setActionSucessStatus(Boolean actionSucessStatus) {
        this.actionSucessStatus = actionSucessStatus;
    }

    public VideoProduct getVideoProduct() {
        return videoProduct;
    }

    public void setVideoProduct(VideoProduct videoProduct) {
        this.videoProduct = videoProduct;
    }

    public Director getDirector() {
        return director;
    }

    public void setDirector(Director director) {
        this.director = director;
    }

    @Override
    public String toString() {
        return "DirectorStatus{" +
                "directors id=" + director.getId() +
                ", videoProduct=" + videoProduct +
                ", actionSucessStatus=" + actionSucessStatus +
                '}';
    }
}
