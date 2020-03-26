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

public class menuItem_MyMenu extends JDialog implements ActionListener{
	public static Menu m = new Menu();
	List<Menu> menus;
	private JPanel toolBar = new JPanel();
	private Button btnAddFood = new Button("添加食材");
	private Object tblTitle[]={"编号","菜名","描述"};
	private Object tblData[][];
	DefaultTableModel tablmod=new DefaultTableModel();
	private JTable menuTable=new JTable(tablmod);
	private void reloadMenuTable(){
		TableStyle.setTableStyle(menuTable);
		try {
			menus=(new MenuManager()).myMenu();
			tblData =new Object[menus.size()][3];
			for(int i=0;i<menus.size();i++){
				if(i<menus.size()) {
					tblData[i][0]=menus.get(i).getMenu_id();
					tblData[i][1]=menus.get(i).getMenu_name();
					tblData[i][2]=menus.get(i).getMenu_dsp();
				}
			}
			tablmod.setDataVector(tblData,tblTitle);
			this.menuTable.validate();
			this.menuTable.repaint();
		} catch (BaseException e) {
			e.printStackTrace();
		}
	}
	public menuItem_MyMenu(Frame f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		toolBar.add(btnAddFood);
		this.getContentPane().add(toolBar, BorderLayout.NORTH);
		//提取现有数据
		this.reloadMenuTable();
		this.getContentPane().add(new JScrollPane(this.menuTable), BorderLayout.CENTER);
		
		// 屏幕居中显示
		this.setSize(800, 600);
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();
		this.btnAddFood.addActionListener(this);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				//System.exit(0);
			}
		});
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.btnAddFood) {
			int i=this.menuTable.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "请选择菜谱","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}else {
				m = menus.get(this.menuTable.getSelectedRow());
				FrmMenuUse dlg = new FrmMenuUse(this,"添加食材",true);
				dlg.setVisible(true);
			}
			
		}
	}
}