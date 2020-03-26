package ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ButtonGroup;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import util.BaseException;
import control.GodManager;
import control.UserManager;
import model.God;
import model.User;
import ui.FrmUserManager_AddUser;

public class FrmLogin extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnLogin = new Button("��½");
	private Button btnRegister = new Button("ע��");
	private Button btnCancel = new Button("�˳�");
	private JLabel labelUser = new JLabel("�û���");
	private JLabel labelPwd = new JLabel("���룺");
	private JLabel sexLabel = new JLabel("����:");
	private JRadioButton userType = new JRadioButton("�û�");
	private JRadioButton GodType = new JRadioButton("����Ա");
	private ButtonGroup TypeGroup = new ButtonGroup();
	private JTextField edtUserId = new JTextField(20);
	private JPasswordField edtPwd = new JPasswordField(20);

	public FrmLogin(Frame f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnLogin);
		toolBar.add(btnRegister);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.add(labelUser);
		workPane.add(edtUserId);
		workPane.add(labelPwd);
		workPane.add(edtPwd);
		
		userType.setSelected(true);
		userType.addActionListener(this);
		
		//GodType.setSelected(true);
		GodType.addActionListener(this);
		
		TypeGroup.add(userType);
		TypeGroup.add(GodType);
 
		workPane.add(sexLabel);
		workPane.add(userType);
		workPane.add(GodType);
		
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(300,165);
		// ��Ļ������ʾ
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();

		btnLogin.addActionListener(this);
		btnRegister.addActionListener(this);
		btnCancel.addActionListener(this);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.btnLogin) {
			if(this.userType.isSelected()) {
				UserManager sum=new UserManager();
				String userid=this.edtUserId.getText();
				String pwd=new String(this.edtPwd.getPassword());
				try {
					User user=sum.loadUser(userid);
					if(pwd.equals(user.getUser_pwd())) {
						UserManager.currentUser=user;
						setVisible(false);
					}else {
						JOptionPane.showMessageDialog(null,  "�������","������ʾ",JOptionPane.ERROR_MESSAGE);
					}
				}catch (BaseException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "������ʾ",JOptionPane.ERROR_MESSAGE);
				}
			}else {
				
				GodManager sum=new GodManager();
				String userid=this.edtUserId.getText();
				String pwd=new String(this.edtPwd.getPassword());
				try {
					God user=sum.loadGod(userid);
					if(pwd.equals(user.getGod_pwd())){
						GodManager.currentGod=user;
						setVisible(false);
					}else{
						JOptionPane.showMessageDialog(null,  "�������","������ʾ",JOptionPane.ERROR_MESSAGE);
					}
				} catch (BaseException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "������ʾ",JOptionPane.ERROR_MESSAGE);
				}
			}
			
		}else if (e.getSource() == this.btnRegister) {
			FrmUserManager_AddUser dlg=new FrmUserManager_AddUser(this,"ע���˺�",true);
			dlg.setVisible(true);
		}else if (e.getSource() == this.btnCancel) {
			System.exit(0);
		}
	}

}
