package ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import control.MenuManager;
import model.Menu;
import util.BaseException;

public class menuItem_addMenu extends JDialog implements ActionListener{
	private Menu menu = new Menu();
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("È·¶¨");
	private Button btnChoose = new Button("Ñ¡Ôñ");
	private Button btnCancel = new Button("È¡Ïû");
	private JLabel labelMenuname = new JLabel("²ËÆ×Ãû³Æ£º");
	private JLabel labelMenupic = new JLabel("Í¼Æ¬Â·¾¶£º");
	private JLabel labelMenudsp = new JLabel("²ËÆ×ÃèÊö£º");
	
	private JTextField edtMenuname = new JTextField(20);
	private JTextField edtMenupic = new JTextField(16);
	private JTextArea edtMenudsp = new JTextArea(11,20);
	public menuItem_addMenu(FrmMain frmMain, String s, boolean b) {
		super(frmMain, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnOk);
		toolBar.add(btnChoose);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.add(labelMenuname);
		workPane.add(edtMenuname);
		workPane.add(labelMenupic);
		workPane.add(edtMenupic);
		workPane.add(btnChoose);
		workPane.add(labelMenudsp);
		workPane.add(edtMenudsp);
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(320, 340);
		// ÆÁÄ»¾ÓÖÐÏÔÊ¾
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();
		this.btnOk.addActionListener(this);
		this.btnChoose.addActionListener(this);
		this.btnCancel.addActionListener(this);
		
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.btnChoose) {
			String path=""; 
			JFileChooser chooser = new JFileChooser();
			chooser.setCurrentDirectory(new File("E:\\java_work\\menu\\picture"));
		    FileNameExtensionFilter filter = new FileNameExtensionFilter(
		        "JPG & PNG Images", "jpg","png");
		    chooser.setFileFilter(filter);
		    int returnVal = chooser.showOpenDialog(this);
		    if(returnVal == JFileChooser.APPROVE_OPTION) {
		       path = chooser.getCurrentDirectory() +"\\"+
		            chooser.getSelectedFile().getName();
		    }
		    edtMenupic.setText(path);
		}
		if(e.getSource()==this.btnCancel) {
			this.setVisible(false);
			return;
		}else if(e.getSource()==this.btnOk){
			menu.setMenu_name(this.edtMenuname.getText());
			menu.setMenu_pic(this.edtMenupic.getText());
			menu.setMenu_dsp(this.edtMenudsp.getText());
			try {
				(new MenuManager()).addMenu(menu);
				this.setVisible(false);
			} catch (BaseException e1) {
				this.menu=null;
				JOptionPane.showMessageDialog(null, e1.getMessage(),"´íÎó",JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	public Menu getMenu() {
		return menu;
	}
}
