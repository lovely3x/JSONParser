package usage.annotations;

import com.lovely3x.jsonparser.annotations.JSONArray;

import java.util.List;

/**
 * Created by lovely3x on 15-11-15.
 * 班级对象
 */
public class Classes {
    /**
     * 班级id
     */
    private int id;

    /**
     * 班级名
     */
    private String name;

    /**
     * 班级的学员
     */
    @JSONArray(object = Student.class, value = "students")
    private List<Student> students;

    public Classes() {
    }

    public Classes(int id, String name, List<Student> students) {
        this.id = id;
        this.name = name;
        this.students = students;
    }

    @Override
    public String toString() {
        return "Classes{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", students=" + students +
                '}';
    }
}
