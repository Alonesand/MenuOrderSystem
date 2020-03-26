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

import control.ComManager;
import control.MenuManager;
import model.Com;
import util.BaseException;
import util.BusinessException;

public class FrmCominfo extends JDialog implements ActionListener{
	public static Com com = new Com();
	JPanel toolBar = new JPanel();
	JButton btndelCom = new JButton("删除评论");	
	private Object tblTitle[]={"菜名","评分","评论"};
	private Object tblData[][];
	DefaultTableModel tablmod=new DefaultTableModel();
	private JTable comTable=new JTable(tablmod);
	private List<Com> coms;
	private void reloadComTable(){
		TableStyle.setTableStyle(comTable);
		try {
			coms=(new ComManager()).loadUserComs(FrmMain.cUser.getUser_id());
			tblData =new Object[coms.size()][3];
			for(int i=0;i<coms.size();i++){
					String meun = (new MenuManager()).loadMenu(coms.get(i).getMenu_id()).getMenu_name();
					tblData[i][0]=meun;
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
	
	public FrmCominfo(FrmMain frmMain, String s, boolean b) {
		super(frmMain, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		toolBar.add(btndelCom);
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
		
		this.btndelCom.addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.btndelCom) {
			int i=this.comTable.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "请选择评论","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}else {
				try {
					com = coms.get(this.comTable.getSelectedRow());
					(new ComManager()).delComs(com);
					JOptionPane.showMessageDialog(this.getContentPane(),"操作成功", "提示", JOptionPane.INFORMATION_MESSAGE);
				} catch (BusinessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
			this.reloadComTable();
		}
	}
}
