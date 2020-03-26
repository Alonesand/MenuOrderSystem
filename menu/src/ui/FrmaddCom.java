package ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import control.ComManager;
import model.Menu;
import model.User;
import util.BusinessException;

public class FrmaddCom extends JDialog implements ActionListener{
	private User u = FrmMain.cUser;
	private Menu m = FrmMain.cMenu;
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private JLabel labelgrade = new JLabel("评分：");
	private JLabel labelcom = new JLabel("评价：");
	private JTextField edtgrade = new JTextField(20);
	private JTextArea edtcom = new JTextArea(3,20);
	private Button btnOk = new Button("确定");
	private Button btnCancel = new Button("取消");
	
	
	public FrmaddCom(FrmCom frmCom, String s, boolean b) {
		super(frmCom, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.add(labelgrade);
		workPane.add(edtgrade);
		workPane.add(labelcom);
		workPane.add(edtcom);
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(320,180);
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
		}
		if(e.getSource()==this.btnOk) {
			if(edtgrade.getText().equals(null)||"".equals(edtgrade.getText())||edtcom.getText().equals(null)||"".equals(edtcom.getText())
					||Float.parseFloat(this.edtgrade.getText().toString())<0||Float.parseFloat(this.edtgrade.getText().toString())>10) {
				JOptionPane.showMessageDialog(this.getContentPane(),"请输入正确的评分和评价", "提示", JOptionPane.ERROR_MESSAGE);
				return;
			}
			float f = Float.parseFloat(this.edtgrade.getText().toString());
			String com = this.edtcom.getText().toString();
			try {
				(new ComManager()).addCom(u.getUser_id(),m.getMenu_id(),f,com);
				JOptionPane.showMessageDialog(this.getContentPane(),"评论成功", "提示", JOptionPane.INFORMATION_MESSAGE);
			} catch (BusinessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			this.setVisible(false);
		}
	}
}
