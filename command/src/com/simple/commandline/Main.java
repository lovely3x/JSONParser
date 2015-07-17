package com.simple.commandline;

import com.lovely3x.jsonparser.JSONType;
import com.lovely3x.jsonparser.formatter.JSONFormatterImpl;
import com.lovely3x.jsonparser.log.Log;
import com.lovely3x.jsonparser.model.JSONValue;
import com.lovely3x.jsonparser.model.JSONValueImpl;
import com.lovely3x.jsonparser.source.JSONSourcImpl;
import com.lovely3x.jsonparser.source.JSONSource;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by lovely3x on 15-7-3.
 */
public class Main {

    private static final String TAG = "Main";

    /**
     * 帮助
     */
    public static final String HELP = "--help";


    /**
     * 输入路径
     */
    public static final String ENTRY_PATH = "-p";

    /**
     * 输出路径
     */
    public static final String OUT_PATH = "-o";

    /**
     * 格式化
     */
    public static final String FORMAT = "-f";

    /**
     * 输入一个string
     */
    public static final String ENTRY_STRING = "-s";

    /**
     * 根据指定的 json串 来创建类对象
     */
    public static final String CREATE_CLASS = "-c";

    /**
     * 编码方式
     */
    public static final String CHARSET = "-e";


    /**
     * 默认的编码方式
     */
    private static final String DEFAULT_CHARSET = "UTF-8";

    /**
     * 缩进符号 格式化使用
     */
    private static final String BLOCK = "-b";

    /**
     * 默认的缩进符号 四个空格
     */
    private static final String DEFAULT_BLOCK = "    ";


    /**
     * 默认的类名
     */
    private static final String DEFAULT_CLASS_NAME = "JObject";

    public static void main(String[] args) {
        if (args == null || args.length == 0) {
            printHelp();
            return;
        } else {
            ArrayList<String> list = toList(args);
            // Log.e(TAG, Arrays.toString(args));
            switch (args[0]) {
                case FORMAT://格式化
                    excFormat(list);
                    break;
                case CREATE_CLASS://创建类
                    excClass(list);
                    break;
                case HELP:
                    printHelp();
                    break;
                default:
                    System.out.printf("\n不能识别的指令\n");
                    break;
            }
        }
    }

    /**
     * 制定格式化
     *
     * @param list
     */
    private static void excFormat(ArrayList<String> list) {
        JSONSource source = null;
        String charset = list.contains(CHARSET) ?
                (list.indexOf(CHARSET) + 1 < list.size() ? list.get(list.indexOf(CHARSET) + 1) : (DEFAULT_CHARSET)) : DEFAULT_CHARSET;
        Log.e(TAG, list.toString());
        if (list.contains(ENTRY_PATH)) {//使用路径
            int index = list.indexOf(ENTRY_PATH) + 1;
            if (index >= list.size()) {//
                System.out.printf("\n你需要指定JSON资源的路径\n");
            } else {
                File jsonFile = new File(list.get(index));
                if (!jsonFile.exists()) {//如果文件不存在
                    System.out.printf("\n你指定JSON资源的路径是无效的\n");
                } else {
                    try {
                        FileInputStream fos = new FileInputStream(jsonFile);
                        source = new JSONSourcImpl(fos, charset);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            if (list.contains(ENTRY_STRING)) {//使用字符串
                int index = list.indexOf(ENTRY_STRING) + 1;
                if (index >= list.size()) {
                    System.out.printf("\n你指定JSON资源是无效的\n");
                } else {
                    source = new JSONSourcImpl(list.get(index));
                }
            }
        }

        if (source != null) {
            JSONValue value = new JSONValueImpl(source.input());
            String block = DEFAULT_BLOCK;
            if (list.contains(BLOCK)) {
                int index = list.indexOf(BLOCK);
                if (index + 1 < list.size()) {
                    block = list.get(index + 1);
                }
            }
            Log.e(TAG, "block" + block);
            String formatResult = null;
            switch (value.guessType()) {
                case JSONType.JSON_TYPE_OBJECT:
                    formatResult = value.getJSONObject().format(new JSONFormatterImpl(block));
                    break;
                case JSONType.JSON_TYPE_ARRAY:
                    formatResult = value.getJSONArray().format(new JSONFormatterImpl(block));
                    break;
            }
            if (formatResult != null) {
                if (list.contains(OUT_PATH)) {
                    int index = list.indexOf(OUT_PATH) + 1;
                    if (index >= list.size()) {
                        System.out.printf("\n没有指定的输出路径\n");
                    } else {
                        File f = new File(list.get(index));
                        if (f.exists()) {
                            f.delete();
                        }
                        try {
                            if (f.createNewFile()) {
                                FileOutputStream fos = new FileOutputStream(f);
                                fos.write(formatResult.getBytes(charset));
                                fos.flush();
                                try {
                                    fos.close();
                                } catch (Exception e) {
                                    System.out.printf("\n警告: 文件未被正确关闭\n");
                                }
                                System.out.printf("\n成功\n");
                            } else {
                                System.out.printf("\n输出失败,请确保制定的输出是有效的并且检查是否已经指定权限\n");
                            }
                        } catch (Exception e) {
                            System.out.printf("\n输出失败,请确保制定的输出是有效的\n");
                        }
                    }
                } else {
                    System.out.printf("\n\n");
                    System.out.printf(formatResult);
                    System.out.printf("\n\n");
                }
            } else {
                System.out.printf("\n格式化失败\n");
            }
        }

    }


    /**
     * 指定创建class
     *
     * @param list 参数表
     */
    private static void excClass(ArrayList<String> list) {
        //需要输入源
        //需要输出源
        JSONSource source = null;

        String charset = list.contains(CHARSET) ?
                (list.indexOf(CHARSET) + 1 < list.size() ? list.get(list.indexOf(CHARSET) + 1) : (DEFAULT_CHARSET)) : DEFAULT_CHARSET;

        if (list.contains(ENTRY_PATH)) {//使用路径
            int index = list.indexOf(ENTRY_PATH) + 1;
            if (index >= list.size()) {//
                System.out.printf("\n你需要指定JSON资源的路径\n");
            } else {
                File jsonFile = new File(list.get(index));
                if (!jsonFile.exists()) {//如果文件不存在
                    System.out.printf("\n你指定JSON资源的路径是无效的\n");
                } else {
                    try {
                        if (jsonFile.isDirectory()) {
                            System.out.printf("\n你指定的JSON资源路径是一个文件夹,这是不正确的,你应该指定一个文件\n");
                        } else {
                            FileInputStream fos = new FileInputStream(jsonFile);
                            source = new JSONSourcImpl(fos, charset);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        if (source != null) {
            File outFile = null;
            if (list.contains(OUT_PATH)) {
                int index = list.indexOf(OUT_PATH) + 1;
                if (index >= list.size()) {
                    Log.e(TAG, "\n没有指定的输出路径\n");
                } else {
                    outFile = new File(list.get(index));
                }
            }

            if (outFile == null) outFile = new File(DEFAULT_CLASS_NAME);
            JSONValue value = new JSONValueImpl(source.input());

            ByteArrayOutputStream baos = null;

            switch (value.guessType()) {
                case JSONType.JSON_TYPE_OBJECT:
                    baos = new ByteArrayOutputStream();
                    String className = outFile.getName();
                    if (className.indexOf('.') != -1) {
                        className = className.substring(0, className.indexOf('.'));
                    }
                    value.getJSONObject().createClass(className, baos);
                    break;
            }

            if (baos != null) {
                if (!DEFAULT_CLASS_NAME.equals(outFile.getName())) {
                    try {
                        FileOutputStream fos = new FileOutputStream(outFile);
                        fos.write(baos.toByteArray());
                        fos.flush();
                        try {
                            fos.close();
                            baos.close();
                        } catch (Exception e) {
                            System.out.printf("\n警告: 文件未被正确关闭\n");
                        }
                        System.out.printf("\n成功\n");
                    } catch (Exception e) {
                        System.out.printf("\n输出失败,请确保制定的输出是有效的\n");
                    }


                } else {
                    System.out.printf("\n\n");
                    try {
                        System.out.printf(new String(baos.toByteArray(), charset));
                    } catch (UnsupportedEncodingException e) {
                        try {
                            System.out.printf("警告:你指定的编码可能无效,尝试使用默认的 UTF-8 编码\n");
                            System.out.printf("\n\n");
                            System.out.printf(new String(baos.toByteArray(), DEFAULT_CHARSET));
                        } catch (UnsupportedEncodingException e1) {
                            System.out.printf("\n类创建失败\n");
                            e1.printStackTrace();
                        }
                    }
                    System.out.printf("\n\n");
                }
            } else {
                System.out.printf("\n类创建失败,可能是你的JSON数据源不符合规范或者你的你的JSON是一个JSONArray\n");
            }
        } else {
            System.out.printf("\n类创建失败,可能是你的JSON数据源不符合规范或者你的你的JSON是一个JSONArray\n");
        }
    }


    /**
     * 将array 转换为list
     *
     * @param args 需要转换的源数据
     * @return
     */
    private static ArrayList<String> toList(String[] args) {
        if (args == null) return null;
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = 0; i < args.length; i++) {
            arrayList.add(args[i]);
        }
        return arrayList;
    }

    /**
     * 打印帮助
     */
    private static void printHelp() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n使用%s可以查看该帮助内容\n");
        sb.append("\n你可以使用这个工具做下面的这些事\n");
        sb.append("1,对现有的json数据进行格式化(-f)\n");
        sb.append("如何使用 ? ");
        sb.append("\n").append("如果你需要格式化json字符串,你可以这样做 %s [%s] [%s] ");
        sb.append("\n代表什么 ? \n").append(" %s 表示是对json进行格式化,这时我们需要数据源\n %s 制定数据源为文件");
        sb.append("\n").append(" %s 表示输出到什么地方,如果未指定,则直接输出到控制台.");
        sb.append("\n").append(" 举个例子 %s %s /data/home/lovely3x/json.txt %s /data/home/lovely3x/json_format.txt");
        sb.append("\n").append(" 另外你还可以通过 %s 来指定格式化时使用的缩进符号,默认使用的四个空格");
        sb.append("\n\n");
        sb.append("2,通过json对象生成java文件(-c)\n");
        sb.append("如何使用 ? ");
        sb.append("\n").append("如果你需要根据一个JSON对象创建一个类,你可以这样做 %s [%s][%s] ");
        sb.append("\n代表什么 ? \n").append(" %s 表示是进行类创建\n %s 输出的文件的位置,如果未指定,则直接输出到控制台\n %s 指定数据源为文件");
        sb.append("\n\n");
        System.out.printf(sb.toString(),
                HELP,
                FORMAT, ENTRY_PATH, OUT_PATH,
                FORMAT, ENTRY_PATH, OUT_PATH,
                FORMAT, ENTRY_PATH, OUT_PATH,
                BLOCK,
                CREATE_CLASS,
                OUT_PATH, ENTRY_PATH,
                CREATE_CLASS, OUT_PATH, ENTRY_PATH
        );
    }
}
