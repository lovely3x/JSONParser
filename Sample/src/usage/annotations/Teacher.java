package usage.annotations;

import com.lovely3x.jsonparser.annotations.JSON;

/**
 * Created by lovely3x on 15-11-15.
 */
public class Teacher {
    @JSON(value = "id")
    private int id;
    @JSON(value = "name")
    private String name;

    public Teacher(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Teacher() {
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
