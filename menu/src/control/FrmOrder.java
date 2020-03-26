package control;

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

import model.Order;
import ui.FrmMain;
import ui.TableStyle;
import util.BaseException;

public class FrmOrder extends JDialog implements ActionListener{
	JPanel toolBar = new JPanel();
	JButton btndelOrder = new JButton("�˵�");	
	private Object tblTitle[]={"�ǳ�","Ԥ���ʹ�ʱ��","��ַ","��ϵ�绰","����״̬"};
	private Object tblData[][];
	DefaultTableModel tablmod=new DefaultTableModel();
	private JTable orderTable=new JTable(tablmod);
	private List<Order> orders;
	private void reloadOrderTable(){
		TableStyle.setTableStyle(orderTable);
		try {
			orders=(new OrderManager()).loadOrder(FrmMain.cUser.getUser_id());
			tblData =new Object[orders.size()][5];
			for(int i=0;i<orders.size();i++){
					tblData[i][0]=orders.get(i).getOrder_userid();
					tblData[i][1]=orders.get(i).getOrder_time();
					tblData[i][2]=orders.get(i).getOrder_add();
					tblData[i][3]=orders.get(i).getOrder_tel();
					tblData[i][4]=orders.get(i).getOrder_status();
			}
			tablmod.setDataVector(tblData,tblTitle);
			this.orderTable.validate();
			this.orderTable.repaint();
		} catch (BaseException e) {
			e.printStackTrace();
		}
	}
	
	public FrmOrder(FrmMain frmMain, String s, boolean b) {
		super(frmMain, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		toolBar.add(btndelOrder);
		this.getContentPane().add(toolBar, BorderLayout.NORTH);
		this.setSize(800,600);
		
		this.reloadOrderTable();
		this.getContentPane().add(new JScrollPane(this.orderTable), BorderLayout.CENTER);
		// ��Ļ������ʾ
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();
		this.btndelOrder.addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.btndelOrder) {
			int i=this.orderTable.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "��ѡ�����","��ʾ",JOptionPane.ERROR_MESSAGE);
				return;
			}else {
				try {
					(new OrderManager()).delOrder(orders.get(i).getOrder_id());					
					this.reloadOrderTable();
				} catch (BaseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			this.reloadOrderTable();
		}
	}
}
