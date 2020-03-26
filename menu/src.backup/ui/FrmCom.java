package ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import control.ComManager;
import model.Com;
import util.BaseException;

public class FrmCom extends JDialog implements ActionListener{
	JPanel toolBar = new JPanel();
	JButton btnaddCom = new JButton("添加评论");	
	private Object tblTitle[]={"昵称","评分","评论"};
	private Object tblData[][];
	DefaultTableModel tablmod=new DefaultTableModel();
	private JTable comTable=new JTable(tablmod);
	
	private void reloadComTable(){
		TableStyle.setTableStyle(comTable);
		try {
			List<Com> coms=(new ComManager()).loadMenuComs(FrmMain.cMenu.getMenu_id());
			tblData =new Object[coms.size()][3];
			for(int i=0;i<coms.size();i++){
					tblData[i][0]=coms.get(i).getUser_id();
					tblData[i][1]=coms.get(i).getCom_grade();
					tblData[i][2]=coms.get(i).getCom();
			}
			tablmod.setDataVector(tblData,tblTitle);
			this.comTable.validate();
			this.comTable.repaint();
		} catch (BaseException e) {
			e.printStackTrace();
		}
	}
	
	public FrmCom(FrmMain frmMain, String s, boolean b) {
		super(frmMain, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		toolBar.add(btnaddCom);
		this.getContentPane().add(toolBar, BorderLayout.NORTH);
		this.setSize(800,600);
		
		this.reloadComTable();
		this.getContentPane().add(new JScrollPane(this.comTable), BorderLayout.CENTER);
		// 屏幕居中显示
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();
		
		this.btnaddCom.addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.btnaddCom) {
			FrmaddCom dlg = new FrmaddCom(this,"添加评论",true);
			dlg.setVisible(true);
			this.reloadComTable();
		}
	}
}
