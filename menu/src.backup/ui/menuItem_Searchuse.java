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
import java.util.ArrayList;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import control.Menu_useManager;
import model.Menu_use;
import util.BaseException;

public class menuItem_Searchuse extends JDialog implements ActionListener{
	private Object tblTitle[]={"菜谱编号","食材名","需要数量","单位"};
	private Object tblData[][];
	DefaultTableModel tablmod=new DefaultTableModel();
	private JTable menuTable=new JTable(tablmod);
	
	List<Menu_use> use = new ArrayList<>();
	private void reloadUseTable(){
		try {
			use=(new Menu_useManager()).loadAllUse(FrmMain.cMenu.getMenu_id());
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		TableStyle.setTableStyle(menuTable);
		tblData =new Object[use.size()][4];
		for(int i=0;i<use.size();i++){
			if(i<use.size()) {
				tblData[i][0]=use.get(i).getMenu_id();
				tblData[i][1]=use.get(i).getFood_name();
				tblData[i][2]=use.get(i).getUse_num();
				tblData[i][3]=use.get(i).getUse_format();
			}
		}
		tablmod.setDataVector(tblData,tblTitle);
		this.menuTable.validate();
		this.menuTable.repaint();
		
	}
	public menuItem_Searchuse(Frame f, String s, boolean b) {
		super(f, s, b);
		//提取现有数据
		this.reloadUseTable();
		tablmod.setDataVector(tblData,tblTitle);
		this.getContentPane().add(new JScrollPane(this.menuTable), BorderLayout.CENTER);
		
		// 屏幕居中显示
		this.setSize(700, 500);
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				//System.exit(0);
			}
		});
		
	}
	public void actionPerformed(ActionEvent e) {
//		if(e.getSource()==this.btnOrder) {
//			if(use.size()==0) {
//				JOptionPane.showMessageDialog(null,"等待贡献者提供食材信息", "提示", JOptionPane.INFORMATION_MESSAGE);
//			}
//			(new Menu_use()).addOrder();
//		}
	}
}
