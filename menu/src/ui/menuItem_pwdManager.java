package ui;

import java.awt.BorderLayout;
import java.awt.Button;
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

import control.UserManager;
import model.User;
import util.BaseException;
import util.BusinessException;

public class menuItem_pwdManager extends JDialog implements ActionListener{
	User user = new User();
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("确定");
	private Button btnCancel = new Button("取消");
	private JLabel labelOldpwd = new JLabel("原密码：");
	private JLabel labelNewpwd = new JLabel("新密码：");
	private JLabel labelNewpwd2 = new JLabel("再次输入：");
	
	private JPasswordField edtOldpwd = new JPasswordField(20);
	private JPasswordField edtNewpwd = new JPasswordField(20);
	private JPasswordField edtNewpwd2 = new JPasswordField(20);
	public menuItem_pwdManager(FrmMain frmMain, String s,boolean b) {
		super(frmMain, s, b);
		toolBar.add(btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.add(labelOldpwd);
		workPane.add(edtOldpwd);
		workPane.add(labelNewpwd);
		workPane.add(edtNewpwd);
		workPane.add(labelNewpwd2);
		workPane.add(edtNewpwd2);
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(300, 240);
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
		}else if(e.getSource()==this.btnOk){
			user = new User();
			String userid = FrmMain.cUser.getUser_id();
			String oldpwd = this.edtOldpwd.getText();
			String newpwd = this.edtNewpwd.getText();
			String newpwd2 = this.edtNewpwd2.getText();
			if(!newpwd.equals(newpwd2))
				try {
					throw new BusinessException("两次新密码不同");
				} catch (BusinessException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			else {
				try {
					(new UserManager()).modifypwd(userid, newpwd);
					JOptionPane.showMessageDialog(this.getContentPane(),"操作成功", "提示", JOptionPane.INFORMATION_MESSAGE);
					this.setVisible(false);
				} catch (BaseException e1) {
					this.user=null;
					JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}
	public User getUser() {
		return user;
	}
}