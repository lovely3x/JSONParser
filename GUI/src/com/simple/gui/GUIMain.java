package com.simple.gui;

import com.lovely3x.jsonparser.JSONType;
import com.lovely3x.jsonparser.formatter.JSONFormatterImpl;
import com.lovely3x.jsonparser.model.JSONValueImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;

/**
 * 一个简单的使用JSONParser库编写的JSON格式化个JavaBean生成小工具
 *
 * @author lovely3x
 * @version 1.0
 * @time 2015-7-18 上午1:40:45
 * @Description: {}
 */
public class GUIMain extends JFrame {

    private static final long serialVersionUID = 1L;

    public static final int WIDTH = 300;

    public static final int HEIGHT = 500;

    private JPanel panel;
    private JPanel p;
    private CardLayout card;
    private JButton btnCreateJavaBean;
    private JButton btnFormat;
    private TextArea textAreaContent;

    public GUIMain() {
        super("JSONParser");
        card = new CardLayout(5, 5);
        panel = new JPanel(card);

        p = new JPanel();

        btnCreateJavaBean = new JButton("创建JavaBean");
        btnCreateJavaBean.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                String path = showFileChoiceDialog(GUIMain.this);
                if (path != null) {
                    String text = textAreaContent.getText();
                    JSONValueImpl value = new JSONValueImpl(text);
                    switch (value.guessType()) {
                        case JSONType.JSON_TYPE_OBJECT:
                            File f = new File(path);
                            FileOutputStream fos = null;
                            try {
                                fos = new FileOutputStream(f);
                                String javaBeanName = f.getName().indexOf(".") != -1 ? f.getName().substring(0, f.getName().indexOf(".")) : f.getName();
                                value.getJSONObject().createClass(javaBeanName, fos);
                                showAlertDialog("生成成功,文件保存在: " + f.getAbsolutePath());
                            } catch (Exception e) {
                                e.printStackTrace();
                                showAlertDialog("错误:" + e.getMessage());
                            } finally {
                                if (fos != null)
                                    try {
                                        fos.close();
                                    } catch (Exception e) {
                                        showAlertDialog("警告:文件未正常关闭");
                                    }
                            }
                            break;
                        case JSONType.JSON_TYPE_ARRAY:
                            showAlertDialog("错误:" + "你不能将一个JSONArray转换为对象");
                            break;
                        default:
                            showAlertDialog("错误:" + "不是有效的JSON字符串");
                            break;
                    }
                }
            }
        });
        btnFormat = new JButton("格式化JSON");
        btnFormat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                String text = textAreaContent.getText();
                JSONValueImpl value = new JSONValueImpl(text);
                String formatted = null;
                switch (value.guessType()) {
                    case JSONType.JSON_TYPE_OBJECT:
                        formatted = value.getJSONObject().format(new JSONFormatterImpl("***"));
                        break;
                    case JSONType.JSON_TYPE_ARRAY:
                        formatted = value.getJSONArray().format(new JSONFormatterImpl("***"));
                        break;
                }
                if (formatted != null) {
                    textAreaContent.setText(formatted);
                } else {
                    showAlertDialog("格式化失败");
                }
            }
        });

        textAreaContent = new TextArea();
        textAreaContent.setFont(new Font("黑体", Font.BOLD, 18));

        p.add(btnCreateJavaBean);
        p.add(btnFormat);

        panel.add(textAreaContent);

        getContentPane().add(panel);
        getContentPane().add(p, BorderLayout.SOUTH);

        Dimension size = java.awt.Toolkit.getDefaultToolkit().getScreenSize();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLocation(size.width / 2 - WIDTH / 2, size.height / 2 - HEIGHT / 2);
        setResizable(true);
        setVisible(true);
    }

    /**
     * 显示文件选择器
     *
     * @param frame 显示依赖层
     * @return null 或选择的文件的完整路径
     */
    protected String showFileChoiceDialog(Frame frame) {
        FileDialog dialog = new FileDialog(frame);
        dialog.setVisible(true);
        String directory = dialog.getDirectory();
        String file = dialog.getFile();

        if (directory == null || file == null)
            return null;
        return directory + file;

    }

    /**
     * 显示提示
     *
     * @param alert
     */
    protected void showAlertDialog(String alert) {
        JOptionPane.showMessageDialog(null, alert);
    }

    public static void main(String[] args) {
        new GUIMain();
    }
}