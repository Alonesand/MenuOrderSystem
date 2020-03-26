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
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import control.UserManager;
import model.User;
import util.BaseException;

public class FrmUserInfoManager extends JDialog implements ActionListener{
	User user = new User();
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("确定");
	private Button btnCancel = new Button("取消");
	private JLabel labelBlank = new JLabel("                                               ");
	private JLabel labelUsername = new JLabel("昵称：");
	private JLabel labelUsersex = new JLabel("性别：");
	private JLabel labelUsertel = new JLabel("电话：");
	private JLabel labelUsermail = new JLabel("邮箱：");
	private JLabel labelUsercity = new JLabel("城市：");
	
	private JRadioButton sex_man = new JRadioButton("男");
	private JRadioButton sex_woman = new JRadioButton("女");
	private ButtonGroup sex_type = new ButtonGroup();
	private JTextField edtUsername = new JTextField(20);
	private JTextField edtUsertel = new JTextField(20);
	private JTextField edtUsermail = new JTextField(20);
	private JTextField edtUsercity = new JTextField(20);
	public FrmUserInfoManager(FrmMain frmMain, String s,boolean b) {
		super(frmMain, s, b);
		toolBar.add(btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.add(labelUsername);
		workPane.add(edtUsername);
		workPane.add(labelUsersex);
		sex_type.add(sex_man);
		sex_man.setSelected(true);
		sex_type.add(sex_woman);
		workPane.add(sex_man);
		workPane.add(sex_woman);
		workPane.add(labelBlank);
		workPane.add(labelUsertel);
		workPane.add(edtUsertel);
		workPane.add(labelUsermail);
		workPane.add(edtUsermail);
		workPane.add(labelUsercity);
		workPane.add(edtUsercity);
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
			String username=this.edtUsername.getText();
			String usersex;
			if(sex_man.isSelected())
				usersex=this.sex_man.getText();
			else
				usersex=this.sex_woman.getText();
			String usertel=this.edtUsertel.getText();
			String usermail=this.edtUsermail.getText();
			String usercity=this.edtUsercity.getText();
			user.setUser_id(userid);
			user.setUser_name(username);
			user.setUser_sex(usersex);
			user.setUser_tel(usertel);
			user.setUser_mail(usermail);
			user.setUser_city(usercity);
			try {
				(new UserManager()).modifyUser(user);
				JOptionPane.showMessageDialog(this.getContentPane(),"操作成功", "提示", JOptionPane.INFORMATION_MESSAGE);
				this.setVisible(false);
			} catch (BaseException e1) {
				this.user=null;
				JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	public User getUser() {
		return user;
	}
}
