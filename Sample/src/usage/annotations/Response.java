package usage.annotations;

/**
 * Created by lovely3x on 15-11-15.
 */
public abstract class Response extends Test {
    public static final int SUCCESSFUl_CODE = 1000;

    private int code;
    private String msg;

    public boolean isSuccessful() {
        return code == SUCCESSFUl_CODE;
    }

    @Override
    public String toString() {
        return "Response{" +
                "code=" + code +
                ", msg=" + msg +
                '}';
    }
}
