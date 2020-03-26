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

import control.FoodManager;
import model.Food;
import util.BaseException;

public class FrmFoodinManager extends JDialog implements ActionListener{
	public static Food f = new Food();
	private JPanel toolBar = new JPanel();
	private Button btnInFood = new Button("进购食材");
	private Object tblTitle[]={"编号","食材名","价格","数量","单位"};
	private Object tblData[][];
	DefaultTableModel tablmod=new DefaultTableModel();
	private JTable foodTable=new JTable(tablmod);
	List<Food> foods = new ArrayList<>();
	private void reloadTableTable(){
		TableStyle.setTableStyle(foodTable);
		try {
			foods=(new FoodManager()).loadAllFoods();
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
	public FrmFoodinManager(Frame f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		toolBar.add(btnInFood);
		this.getContentPane().add(toolBar, BorderLayout.NORTH);
		//提取现有数据
		this.reloadTableTable();
		this.getContentPane().add(new JScrollPane(this.foodTable), BorderLayout.CENTER);
		
		// 屏幕居中显示
		this.setSize(800, 600);
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();
		this.btnInFood.addActionListener(this);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				//System.exit(0);
			}
		});
	}
	public void actionPerformed(ActionEvent e) {
		int i=this.foodTable.getSelectedRow();
		if(i<0) {
			JOptionPane.showMessageDialog(null,  "请选择食材","提示",JOptionPane.INFORMATION_MESSAGE);
			return;
		}else {
			f = foods.get(this.foodTable.getSelectedRow());
			if(e.getSource()==this.btnInFood){
				FrmFoodManager_InFood dlg=new FrmFoodManager_InFood(this,"进购食材",true);
				dlg.setVisible(true);
				if(dlg.getFood()!=null){//刷新表格
					this.reloadTableTable();
				}
			}
		}
	}
}
