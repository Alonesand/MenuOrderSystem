package control;
import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class Main extends JFrame {
    public Main(){
        setBounds(100,100,700,650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Container c=getContentPane();
        JLabel l=new JLabel("����һ��չʾͼƬ��ǩ");
//        URL url=Main.class.getResource("picture/fqcd.jpg");//��ȡͼƬURL·��
//        Icon icon=new ImageIcon(url);//��ȡ��Ӧ·���µ�ͼƬ�ļ�
        Icon icon=new ImageIcon("picture/fqcd.jpg");//�ڶ��ַ�����ȡ��Ӧ·���µ�ͼƬ�ļ�
        l.setIcon(icon);//���ͼƬ
//        l.setSize(20,20);//�趨��ǩ��С����ʹ�趨��ǩ��С��Ҳ����ı�ͼƬ��С
        c.add(l);
        setVisible(true);
    }

    public static void main(String[] args) {
    // write your code here
        new Main();
    }
}