package by.godel.video.app.entity;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Human Model Object.
 * <p>
 * Various attributes of human and related behaviour.
 * <p>
 * Note that {@link LocalDate} is used to model the birth_date.
 *
 * @author Maryia_Halaunia
 * @version 1.0
 */

public class Human extends Entity {
    private String first_name;
    private String last_name;
    private LocalDate birth_date;

    /**
     * Constructor
     *
     * @paramfirst_name is first name of human
     * @paramlast_name is surname of human
     * @parambirth_date is the day, month and year of birth of the human
     */
    public Human(String first_name, String last_name, LocalDate birth_date) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.birth_date = birth_date;
    }

    public Human() {
    }

    // Введем допущение, что не будет однофамильцев с одинаковым именем и датой рождения, фамили/имени/ даты рождения
    // достаточно для идентификации
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Human)) return false;
        if (!super.equals(o)) return false;
        Human human = (Human) o;
        return (getFirst_name().equals(human.getFirst_name())
        && getLast_name().equals(human.last_name)&& getBirth_date().isEqual(human.getBirth_date()));
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getFirst_name(), getLast_name(), getBirth_date());
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public LocalDate getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(LocalDate birth_date) {
        this.birth_date = birth_date;
    }


    @Override
    public String toString() {
        return "Human: " +
                super.toString() +
                "first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", birth_date='" + birth_date +
                "',";
    }
}
