package com.simple.gui;

import java.io.*;

/**
 * Created by lovely3x on 15-8-26.
 */
public class FileCodingTransition {

    public static final String ORIGINAL_ENCODING = "GBK";

    public static final String NEW_ENCODING = "UTF-8";

    public static final String PATH = "/home/lovely3x/Developer/java/FileManagerServers/src";

    /**
     * 匹配文件的正则表达式
     */
    public static final String re = ".*?\\.java";


    public static void main(String[] args) {
        File file = new File(PATH);
        new FileCodingTransition().iteratorFiles(file);
    }

    /**
     * 遍历文件对象集合
     *
     * @param file 需要修改的文件的根目录
     */
    private void iteratorFiles(File file) {
        if (file.isFile()) {
            if (file.getName().matches(re)) {
                transition(file, ORIGINAL_ENCODING, NEW_ENCODING);
            }
        } else {
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    iteratorFiles(f);
                }
            }
        }
    }

    /**
     * 转换文件编码
     *
     * @param oldEncoding 原文见的编码
     * @param newCoding   新文件的编码
     * @param file        需要转换的文件对象
     */
    private void transition(File file, String oldEncoding, String newCoding) {
        System.out.println("transition file -> " + file.getName());
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), oldEncoding));

            String line;
            StringBuffer sb = new StringBuffer();
            while ((line = br.readLine()) != null) {
                sb.append('\n');
                sb.append(line);
            }
            br.close();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), newCoding));
            bw.write(sb.toString());
            bw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
