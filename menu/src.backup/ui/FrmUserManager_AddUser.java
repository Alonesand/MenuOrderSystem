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

import control.UserManager;
import model.User;
import util.BaseException;

public class FrmUserManager_AddUser extends JDialog implements ActionListener {
	private User user = new User();
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("ȷ��");
	private Button btnCancel = new Button("ȡ��");
	private JLabel labelBlank = new JLabel("                                              ");
	private JLabel labelUserid = new JLabel("�˺ţ�");
	private JLabel labelUsername = new JLabel("������");
	private JLabel labelUsersex = new JLabel("�Ա�");
	private JLabel labelUserpwd = new JLabel("���룺");
	private JLabel labelUsertel = new JLabel("�绰��");
	private JLabel labelUsermail = new JLabel("���䣺");
	private JLabel labelUsercity = new JLabel("���У�");
	
	private JRadioButton sex_man = new JRadioButton("��");
	private JRadioButton sex_woman = new JRadioButton("Ů");
	private ButtonGroup sex_type = new ButtonGroup();
	
	private JTextField edtUserid = new JTextField(20);
	private JTextField edtUsername = new JTextField(20);
	private JPasswordField edtUserpwd = new JPasswordField(20);
	private JTextField edtUsertel = new JTextField(20);
	private JTextField edtUsermail = new JTextField(20);
	private JTextField edtUsercity = new JTextField(20);
	public FrmUserManager_AddUser(JDialog f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.add(labelUserid);
		workPane.add(edtUserid);
		workPane.add(labelUsername);
		workPane.add(edtUsername);
		workPane.add(labelUsersex);
		sex_type.add(sex_man);
		sex_man.setSelected(true);
		sex_type.add(sex_woman);
		workPane.add(sex_man);
		workPane.add(sex_woman);
		workPane.add(labelBlank);
		workPane.add(labelUserpwd);
		workPane.add(edtUserpwd);
		workPane.add(labelUsertel);
		workPane.add(edtUsertel);
		workPane.add(labelUsermail);
		workPane.add(edtUsermail);
		workPane.add(labelUsercity);
		workPane.add(edtUsercity);
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(300, 300);
		// ��Ļ������ʾ
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
			String userid=this.edtUserid.getText();
			String username=this.edtUsername.getText();
			String userpwd=this.edtUserpwd.getText();
			String usertel=this.edtUsertel.getText();
			String usermail=this.edtUsermail.getText();
			String usercity=this.edtUsercity.getText();
			user.setUser_id(userid);
			user.setUser_name(username);
			if(this.sex_man.isSelected()) {
				user.setUser_sex("��");
			}else {
				user.setUser_sex("Ů");
			}
			user.setUser_pwd(userpwd);
			user.setUser_tel(usertel);
			user.setUser_mail(usermail);
			user.setUser_city(usercity);
			try {
				(new UserManager()).createUser(user);
				JOptionPane.showMessageDialog(this.getContentPane(),"�����ɹ�", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
				this.setVisible(false);
			} catch (BaseException e1) {
				this.user=null;
				JOptionPane.showMessageDialog(null, e1.getMessage(),"����",JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	public User getUser() {
		return user;
	}
}
