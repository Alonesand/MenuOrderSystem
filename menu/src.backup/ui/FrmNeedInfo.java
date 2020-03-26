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

import control.OrderManager;
import util.BaseException;

public class FrmNeedInfo extends JDialog implements ActionListener{
	private JPanel workPane = new JPanel();
	private JPanel toolBar = new JPanel();
	private Button btnOk = new Button("确定");
	private Button btnCancel = new Button("取消");
	
	private JLabel Needtime = new JLabel("限制时间：");
	private JLabel Needmin = new JLabel("分钟      ");
	private JLabel Needadd = new JLabel("输入地点：");
	private JLabel Needtel = new JLabel("联系电话：");
	
	private JTextField edttime = new JTextField(15);
	private JTextField edtadd = new JTextField(20);
	private JTextField edttel = new JTextField(20);
	
	public FrmNeedInfo(menuItem_SearchMenu menuItem_SearchMenu, String s, boolean b) {
		super(menuItem_SearchMenu,s,b);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.add(Needtime);
		workPane.add(edttime);
		workPane.add(Needmin);
		workPane.add(Needadd);
		workPane.add(edtadd);
		workPane.add(Needtel);
		workPane.add(edttel);
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		// 屏幕居中显示
		this.setSize(320,180);
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
			int menuid = menuItem_SearchMenu.menuid;
			String time = this.edttime.getText();
			String add = this.edtadd.getText();
			String tel = this.edttel.getText();
			if("".equals(time)||"".equals(add)||"".equals(tel)) {
				JOptionPane.showMessageDialog(this.getContentPane(),"订单内容不能为空", "提示", JOptionPane.ERROR_MESSAGE);
			}
			try {
				(new OrderManager()).addOrder(menuid,time,add,tel);
				this.setVisible(false);
			} catch (BaseException e1) {
				
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
}
