package ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import control.MenuManager;
import control.MyManager;
import model.Menu;
import model.My;
import util.BaseException;

public class FrmColl extends JDialog implements ActionListener{
	JPanel toolBar = new JPanel();
	JButton btndelColl = new JButton("取消收藏");	
	private Object tblTitle[]={"菜名","详细"};
	private Object tblData[][];
	DefaultTableModel tablmod=new DefaultTableModel();
	private JTable collTable=new JTable(tablmod);
	private Menu menu;
	private List<My> my;
	private void reloadCollTable(){
		TableStyle.setTableStyle(collTable);
		try {
			my=(new MyManager()).loadMy(FrmMain.cUser.getUser_id());
			tblData =new Object[my.size()][2];
			for(int i=0;i<my.size();i++){
				menu = (new MenuManager()).loadMenu(my.get(i).getMenu_id());
				tblData[i][0]=menu.getMenu_name();
				tblData[i][1]=menu.getMenu_dsp();
			}
			tablmod.setDataVector(tblData,tblTitle);
			this.collTable.validate();
			this.collTable.repaint();
		} catch (BaseException e) {
			e.printStackTrace();
		}
	}
	
	public FrmColl(FrmMain frmMain, String s, boolean b) {
		super(frmMain, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		toolBar.add(btndelColl);
		this.getContentPane().add(toolBar, BorderLayout.NORTH);
		this.setSize(800,600);
		
		this.reloadCollTable();
		this.getContentPane().add(new JScrollPane(this.collTable), BorderLayout.CENTER);
		// 屏幕居中显示
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();
		this.btndelColl.addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.btndelColl) {
			int i=this.collTable.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "请选择菜谱","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}else {
				try {
					(new MyManager()).delMyColl(my.get(i).getMenu_id(),my.get(i).getUser_id());
					JOptionPane.showMessageDialog(this.getContentPane(),"取消成功", "提示", JOptionPane.INFORMATION_MESSAGE);
				} catch (BaseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			this.reloadCollTable();
		}
	}
}
