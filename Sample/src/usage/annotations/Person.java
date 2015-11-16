package usage.annotations;

/**
 * Created by lovely3x on 15-11-15.
 */
public class Person {

    private int id;

    private String name;

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
