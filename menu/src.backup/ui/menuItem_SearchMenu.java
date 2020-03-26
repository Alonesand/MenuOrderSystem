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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import control.MenuManager;
import model.Menu;
import util.BaseException;

public class menuItem_SearchMenu extends JDialog implements ActionListener{
	public static Menu m = new Menu();
	public static int menuid;
	private JPanel toolBar = new JPanel();
	private JLabel labelSearch = new JLabel("输入食谱名：");
	private JTextField edtSearch = new JTextField(20);
	private Button btnSearch = new Button("搜索");
	private Button btnOrder = new Button("生成订单");
	private Object tblTitle[]={"编号","菜名","贡献者","描述"};
	private Object tblData[][];
	DefaultTableModel tablmod=new DefaultTableModel();
	private JTable menuTable=new JTable(tablmod);
	
	List<Menu> menus;
	private void reloadMenuTable(){
		TableStyle.setTableStyle(menuTable);
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
	}
	public menuItem_SearchMenu(Frame f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		toolBar.add(labelSearch);
		toolBar.add(edtSearch);
		toolBar.add(btnSearch);
		toolBar.add(btnOrder);
		this.getContentPane().add(toolBar, BorderLayout.NORTH);
		//提取现有数据
		tablmod.setDataVector(tblData,tblTitle);
		this.getContentPane().add(new JScrollPane(this.menuTable), BorderLayout.CENTER);
		
		// 屏幕居中显示
		this.setSize(800, 600);
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();
		this.btnSearch.addActionListener(this);
		this.btnOrder.addActionListener(this);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				//System.exit(0);
			}
		});
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.btnSearch) {
			String menuname = edtSearch.getText();
			try {
				menus=(new MenuManager()).search(menuname);
				this.reloadMenuTable();
			} catch (BaseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if(e.getSource()==this.btnOrder) {
			int i=this.menuTable.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,"请选择菜谱","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}else {
				m = menus.get(this.menuTable.getSelectedRow());
				menuid = menus.get(this.menuTable.getSelectedRow()).getMenu_id();
				FrmNeedInfo dlg = new FrmNeedInfo(this,"填写信息",true);
				dlg.setVisible(true);
				
			}
		}
	}
}
