package by.godel.video.app.entity;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Director extends Human {

    List<VideoProduct> videoProductList = new ArrayList<VideoProduct>();

    /**
     * Constructor
     * <p>
     * Various attributes of director and related behaviour.
     *
     * @param first_name
     * @param last_name
     * @param birth_date
     * @paramfirst_name is first name of human
     * @paramlast_name is surname of human
     * @parambirth_date is the day, month and year of birth of the human
     * @author Maryia_Halaunia
     * @version 1.0
     */
    public Director(String first_name, String last_name, LocalDate birth_date) {
        super(first_name, last_name, birth_date);
    }

    public Director(String first_name, String last_name, LocalDate birth_date, List<VideoProduct> videoProductList) {
        super(first_name, last_name, birth_date);
        this.videoProductList = videoProductList;
    }

    public Director() {
    }

    public List<VideoProduct> getVideoProductList() {
        return videoProductList;
    }

    public void setVideoProductList(List<VideoProduct> videoProductList) {
        this.videoProductList = videoProductList;
    }

    public void setVideoProductList(VideoProduct videoProduct) {
        List<VideoProduct> videoProductListTemp = new ArrayList<VideoProduct>();
        videoProductListTemp.add(videoProduct);
        this.videoProductList = videoProductListTemp;
    }

    public void addVideoProductList(VideoProduct videoProduct) {
        videoProductList.add(videoProduct);
        this.videoProductList = videoProductList;
    }

    @Override
    public String toString() {
        return super.toString() + " director: " +
                "videoProductList=" + videoProductList;
    }

}
