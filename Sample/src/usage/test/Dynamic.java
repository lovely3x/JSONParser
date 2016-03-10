package usage.test;

import com.lovely3x.jsonparser.annotations.JSON;
import com.lovely3x.jsonparser.annotations.JSONArray;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by lovely3x on 16-1-7.
 */
public class Dynamic implements Type {
    private String dynamicId;
    private String dynamicName;
    private String dynamicContent;
    private String userHead;

    @JSONArray(value = "imgs_fuck", object = String.class)
    private List<String> imgs;

    @JSON(value = "dynamicDescAnnotation")
    private String dynamicDesc;

    public Dynamic() {
    }

    public Dynamic(String dynamicContent, String dynamicDesc, String dynamicId, String dynamicName, List<String> imgs, String userHead) {
        this.dynamicContent = dynamicContent;
        this.dynamicDesc = dynamicDesc;
        this.dynamicId = dynamicId;
        this.dynamicName = dynamicName;
        this.imgs = imgs;
        this.userHead = userHead;
    }

    @Override
    public String toString() {
        return "Dynamic{" +
                "dynamicContent='" + dynamicContent + '\'' +
                ", dynamicId='" + dynamicId + '\'' +
                ", dynamicName='" + dynamicName + '\'' +
                ", userHead='" + userHead + '\'' +
                ", imgs=" + imgs +
                ", dynamicDesc='" + dynamicDesc + '\'' +
                '}';
    }
}
