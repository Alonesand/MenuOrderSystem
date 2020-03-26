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

import control.MenuManager;
import model.Menu;
import util.BaseException;

public class FrmMenuManager extends JDialog implements ActionListener{
	private JPanel toolBar = new JPanel();
	private Button btnDelMenu = new Button("ɾ��");
	private Object tblTitle[]={"���","����","������","����"};
	private Object tblData[][];
	DefaultTableModel tablmod=new DefaultTableModel();
	private JTable menuTable=new JTable(tablmod);
	List<Menu> menus;
	private void reloadMenuTable(){
		TableStyle.setTableStyle(menuTable);
		try {
			menus=(new MenuManager()).loadAllMenus();
			tblData =new Object[menus.size()][4];
			for(int i=0;i<menus.size();i++){
				if(i<menus.size()) {
					tblData[i][0]=menus.get(i).getMenu_id();
					tblData[i][1]=menus.get(i).getMenu_name();
					tblData[i][2]=menus.get(i).getMenu_user();
					tblData[i][3]=menus.get(i).getMenu_dsp();
				}
			}
			tablmod.setDataVector(tblData,tblTitle);
			this.menuTable.validate();
			this.menuTable.repaint();
		} catch (BaseException e) {
			e.printStackTrace();
		}
	}
	public FrmMenuManager(Frame f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		toolBar.add(btnDelMenu);
		this.getContentPane().add(toolBar, BorderLayout.NORTH);
		//��ȡ��������
		this.reloadMenuTable();
		this.getContentPane().add(new JScrollPane(this.menuTable), BorderLayout.CENTER);
		
		// ��Ļ������ʾ
		this.setSize(800, 600);
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();
		this.btnDelMenu.addActionListener(this);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				//System.exit(0);
			}
		});
	}
	public void actionPerformed(ActionEvent e) {
		int i=this.menuTable.getSelectedRow();
		if(i<0) {
			JOptionPane.showMessageDialog(null,  "��ѡ�����","��ʾ",JOptionPane.ERROR_MESSAGE);
			return;
		}
		if(JOptionPane.showConfirmDialog(this,"ȷ��ɾ��������","ȷ��",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
			int menuid=menus.get(i).getMenu_id();			
			try {
				(new MenuManager()).deleteMenu(menuid);
				this.reloadMenuTable();
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(),"����",JOptionPane.ERROR_MESSAGE);
			}

		}
	}
}
