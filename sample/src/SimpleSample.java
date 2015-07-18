import com.lovely3x.jsonpareser.bean.Index;
import com.lovely3x.jsonpareser.bean.Response;
import com.lovely3x.jsonpareser.bean.Water;
import com.lovely3x.jsonpareser.request.UserRequest;
import com.lovely3x.jsonpareser.request.WaterRequest;

import java.io.IOException;
import java.util.List;

/**
 * Created by lovely3x on 15-7-18.
 * 使用列子
 */
public class SimpleSample {

    public static final String TAG = "SimpleSample";

    public static void main(String[] args) throws IOException {
        Response res = new WaterRequest().getWaterInfo("重庆");
        Object[] results = (Object[]) res.obj;
        List<Water> waters = (List<Water>) results[0];
        List<Index> indexes = (List<Index>) results[1];
        printIndex(indexes);
        printEmptyLine(2);
        printWaters(waters);
        printEmptyLine(2);

        //注解解析使用
        Response user = new UserRequest().getUser();
        System.out.println(user.obj);

    }

    /**
     * 打印空白行
     *
     * @param lines
     */
    private static void printEmptyLine(int lines) {
        for (int i = 0; i < lines; i++) {
            System.out.println();
        }
    }

    /**
     * 打印天气
     *
     * @param waters 需要打印的天气
     */
    private static void printWaters(List<Water> waters) {
        if (waters == null) System.out.println("null");
        for (Water water : waters) {
            System.out.println(water);
        }
    }

    /**
     * 打印天气指南
     *
     * @param indexes 需要打印的指南
     */
    private static void printIndex(List<Index> indexes) {
        if (indexes == null) System.out.println("null");
        for (Index index : indexes) {
            System.out.println(index);
        }
    }

}
