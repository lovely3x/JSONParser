package usage.annotations;

import com.lovely3x.jsonparser.annotations.JSONObject;

/**
 * 学生对象
 * Created by lovely3x on 15-11-15.
 */
public class Student {

    @JSONObject(value = "teacher", object = Teacher.class)
    private Teacher teacher;

    /**
     * 学生id
     */
    private int id;

    /**
     * 学生的名字
     */
    private String name;

    /**
     * 学生所在班级id
     */
    private int classesId;

    /**
     * 学生所在班级名
     */
    private String classesName;

    public Student() {
    }

    public Student(Teacher teacher, int id, String name, int classesId, String classesName) {
        this.teacher = teacher;
        this.id = id;
        this.name = name;
        this.classesId = classesId;
        this.classesName = classesName;
    }

    @Override
    public String toString() {
        return "Student{" +
                "teacher=" + teacher +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", classesId=" + classesId +
                ", classesName='" + classesName + '\'' +
                '}';
    }
}
