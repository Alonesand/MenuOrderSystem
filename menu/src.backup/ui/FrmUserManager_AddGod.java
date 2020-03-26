package ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import control.GodManager;
import model.God;
import util.BaseException;

public class FrmUserManager_AddGod extends JDialog implements ActionListener{

	private God god = new God();
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("确定");
	private Button btnCancel = new Button("取消");
	private JLabel labelGodid = new JLabel("账号：");
	private JLabel labelGodname = new JLabel("姓名：");
	private JLabel labelGodpwd = new JLabel("密码：");
	
	
	private JTextField edtGodid = new JTextField(20);
	private JTextField edtGodname = new JTextField(20);
	private JPasswordField edtGodpwd = new JPasswordField(20);
	public FrmUserManager_AddGod(JDialog f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.add(labelGodid);
		workPane.add(edtGodid);
		workPane.add(labelGodname);
		workPane.add(edtGodname);
		workPane.add(labelGodpwd);
		workPane.add(edtGodpwd);
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(300, 170);
		// 屏幕居中显示
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();
		this.btnOk.addActionListener(this);
		this.btnCancel.addActionListener(this);
		
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.btnCancel) {
			this.setVisible(false);
			return;
		}else{			
			String godid=this.edtGodid.getText();
			String godname=this.edtGodname.getText();
			String godpwd=this.edtGodpwd.getText();
			
			god.setGod_id(godid);
			god.setGod_name(godname);
			god.setGod_pwd(godpwd);
			try {
				(new GodManager()).createGod(god);
				this.setVisible(false);
			} catch (BaseException e1) {
				this.god=null;
				JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	public God getGod() {
		return god;
	}
}
