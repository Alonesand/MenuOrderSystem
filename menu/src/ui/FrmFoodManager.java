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

import org.hibernate.annotations.Tables;

import control.FoodManager;
import model.Food;
import util.BaseException;

public class FrmFoodManager extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private Button btnAddFood = new Button("添加食材");
	private Button btnMdyFood = new Button("修改食材");
	private Object tblTitle[]={"编号","食材名","价格","数量","单位"};
	private Object tblData[][];
	DefaultTableModel tablmod=new DefaultTableModel();
	private JTable foodTable=new JTable(tablmod);
	private void reloadUserTable(){
		TableStyle.setTableStyle(foodTable);
		try {
			List<Food> foods=(new FoodManager()).loadAllFoods();
			tblData =new Object[foods.size()][5];
			for(int i=0;i<foods.size();i++){
				if(i<foods.size()) {
					tblData[i][0]=foods.get(i).getFood_id();
					tblData[i][1]=foods.get(i).getFood_name();
					tblData[i][2]=foods.get(i).getFood_price();
					tblData[i][3]=foods.get(i).getFood_num();
					tblData[i][4]=foods.get(i).getFood_format();
				}
			}
			tablmod.setDataVector(tblData,tblTitle);
			this.foodTable.validate();
			this.foodTable.repaint();
		} catch (BaseException e) {
			e.printStackTrace();
		}
	}
	public FrmFoodManager(Frame f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		toolBar.add(btnAddFood);
		toolBar.add(this.btnMdyFood);
		this.getContentPane().add(toolBar, BorderLayout.NORTH);
		//提取现有数据
		this.reloadUserTable();
		this.getContentPane().add(new JScrollPane(this.foodTable), BorderLayout.CENTER);
		
		// 屏幕居中显示
		this.setSize(800, 600);
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();
		this.btnAddFood.addActionListener(this);
		this.btnMdyFood.addActionListener(this);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				//System.exit(0);
			}
		});
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.btnAddFood){
			FrmFoodManager_AddFood dlg=new FrmFoodManager_AddFood(this,"添加食材",true);
			dlg.setVisible(true);
			if(dlg.getFood()!=null){//刷新表格
				this.reloadUserTable();
			}
		}
		if(e.getSource()==this.btnMdyFood) {
			Food f = new Food();
			int i=this.foodTable.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "请选择食材","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			f.setFood_name(this.tblData[i][1].toString());
			f.setFood_price(Float.parseFloat(this.tblData[i][2].toString()));
			f.setFood_num(Integer.parseInt(this.tblData[i][3].toString()));
			f.setFood_format(this.tblData[i][4].toString());
			
			FrmFoodManager_MdyFood dlg = new FrmFoodManager_MdyFood(this,"修改食材",true);
			dlg.setVisible(true);
			
			try {
				(new FoodManager()).modifyNum(f);
				this.reloadUserTable();
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}