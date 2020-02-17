package by.godel.video.app.entity.entityStatus;
import by.godel.video.app.entity.Director;
import by.godel.video.app.entity.Film;
import by.godel.video.app.entity.VideoProduct;
import com.sun.org.apache.xpath.internal.operations.Bool;
import java.time.LocalDate;
import java.util.*;

public class DirectorStatus  {

    private List <Director> directorList;
    private VideoProduct videoProduct;
    private Boolean actionSuccessStatus;

    public DirectorStatus(List<Director> directorList, VideoProduct videoProduct, Boolean actionSuccessStatus) {
        this.directorList = directorList;
        this.videoProduct = videoProduct;
        this.actionSuccessStatus = actionSuccessStatus;
    }

    public List<Director> getDirectorList() {
        return directorList;
    }

    public void setDirectorList(List<Director> directorList) {
        this.directorList = directorList;
    }

    public VideoProduct getVideoProduct() {
        return videoProduct;
    }

    public void setVideoProduct(VideoProduct videoProduct) {
        this.videoProduct = videoProduct;
    }

    public Boolean getActionSucessStatus() {
        return actionSuccessStatus;
    }

    public void setActionSucessStatus(Boolean actionSucessStatus) {
        this.actionSuccessStatus = actionSucessStatus;
    }

    public List <Integer>  getDirectorIdList (List <Director> directorList) {
        List <Integer> directorListId = new ArrayList<>();
        for (int i = 0; i < directorList.size(); i ++) {
            directorListId.add (directorList.get(i).getId());
        }
        return directorListId;
    }


    @Override
    public String toString() {
        return "DirectorStatus{" +
                "directorId=" + Arrays.toString(getDirectorIdList(directorList).toArray()) +
                ", videoProduct=" + videoProduct +
                ", actionSucessStatus=" + actionSuccessStatus +
                '}';
    }
}
