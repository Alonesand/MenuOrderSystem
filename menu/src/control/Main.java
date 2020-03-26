package control;
import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class Main extends JFrame {
    public Main(){
        setBounds(100,100,700,650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Container c=getContentPane();
        JLabel l=new JLabel("这是一个展示图片标签");
//        URL url=Main.class.getResource("picture/fqcd.jpg");//获取图片URL路径
//        Icon icon=new ImageIcon(url);//获取相应路径下的图片文件
        Icon icon=new ImageIcon("picture/fqcd.jpg");//第二种方法获取相应路径下的图片文件
        l.setIcon(icon);//添加图片
//        l.setSize(20,20);//设定标签大小，即使设定标签大小，也不会改变图片大小
        c.add(l);
        setVisible(true);
    }

    public static void main(String[] args) {
    // write your code here
        new Main();
    }
}