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
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import control.GodManager;
import control.UserManager;
import model.God;
import model.User;
import util.BaseException;

public class FrmUserManager extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private Button btnAddGod = new Button("��ӹ���Ա");
	private Button btnDelete = new Button("ɾ��");
	private Object tblTitle[]={"�˺�","�û���","����","����ʱ��"};
	private Object tblData[][];
	DefaultTableModel tablmod=new DefaultTableModel();
	private JTable userTable=new JTable(tablmod);
	private void reloadUserTable(){
		TableStyle.setTableStyle(userTable);
		try {
			List<User> users=(new UserManager()).loadAllUsers(false);
			List<God> gods=(new GodManager()).loadAllGods(false);
			tblData =new Object[gods.size()+users.size()][4];
			int i=0;
			int j=0;
			for(;i<(gods.size()+users.size());i++){
				if(i<users.size()) {
					tblData[i][0]=users.get(i).getUser_id();
					tblData[i][1]=users.get(i).getUser_name();
					tblData[i][2]=users.get(i).getUser_type();
					tblData[i][3]=users.get(i).getUser_reg();
				}else {
					tblData[i][0]=gods.get(j).getGod_id();
					tblData[i][1]=gods.get(j).getGod_name();
					tblData[i][2]=gods.get(j).getGod_type();
					tblData[i][3]=gods.get(j).getReg_time();
					j++;
				}
			}
			tablmod.setDataVector(tblData,tblTitle);
			this.userTable.validate();
			this.userTable.repaint();
		} catch (BaseException e) {
			e.printStackTrace();
		}
	}
	public FrmUserManager(Frame f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		toolBar.add(btnAddGod);
		toolBar.add(this.btnDelete);
		this.getContentPane().add(toolBar, BorderLayout.NORTH);
		//��ȡ��������
		this.reloadUserTable();
		this.getContentPane().add(new JScrollPane(this.userTable), BorderLayout.CENTER);
		
		// ��Ļ������ʾ
		this.setSize(800, 600);
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();
		this.btnAddGod.addActionListener(this);
		this.btnDelete.addActionListener(this);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				//System.exit(0);
			}
		});
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.btnAddGod){
			FrmUserManager_AddGod dlg=new FrmUserManager_AddGod(this,"��ӹ���Ա",true);
			dlg.setVisible(true);
			if(dlg.getGod()!=null){//ˢ�±��
				this.reloadUserTable();
			}
		}
		if(e.getSource()==this.btnDelete) {
			int i=this.userTable.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "��ѡ���˺�","��ʾ",JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(JOptionPane.showConfirmDialog(this,"ȷ��ɾ���˺���","ȷ��",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
				String userid=this.tblData[i][0].toString();
				String type=this.tblData[i][2].toString();
				if("����Ա".equals(type)) {
					try {
						(new GodManager()).deleteGod(userid);
						this.reloadUserTable();
					} catch (BaseException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage(),"����",JOptionPane.ERROR_MESSAGE);
					}
				}else if("�û�".equals(type)) {
					try {
						(new UserManager()).deleteUser(userid);
						this.reloadUserTable();
					} catch (BaseException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage(),"����",JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		}
	}
}
