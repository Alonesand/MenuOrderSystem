package ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import control.FoodManager;
import model.Food;
import util.BaseException;

public class FrmFoodManager_AddFood extends JDialog implements ActionListener{
	private Food food = new Food();
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("确定");
	private Button btnCancel = new Button("取消");
	private JLabel labelFoodname = new JLabel("食材名称：");
	private JLabel labelFoodprice = new JLabel("食材价格：");
	private JLabel labelFoodnum = new JLabel("食材数量：");
	private JLabel labelFoodformat = new JLabel("食材单位：");
	private JLabel labelFooddsp = new JLabel("食材详情：");
	
	private JTextField edtFoodname = new JTextField(20);
	private JTextField edtFoodprice = new JTextField(20);
	private JTextField edtFoodnum = new JTextField(20);
	private JTextField edtFoodformat = new JTextField(20);
	private JTextField edtFooddsp = new JTextField(20);
	public FrmFoodManager_AddFood(JDialog f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.add(labelFoodname);
		workPane.add(edtFoodname);
		workPane.add(labelFoodprice);
		workPane.add(edtFoodprice);
		workPane.add(labelFoodnum);
		workPane.add(edtFoodnum);
		workPane.add(labelFoodformat);
		workPane.add(edtFoodformat);
		workPane.add(labelFooddsp);
		workPane.add(edtFooddsp);
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(320, 220);
		// 屏幕居中显示
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();
		this.btnOk.addActionListener(this);
		this.btnCancel.addActionListener(this);
		
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.btnCancel) {
			this.setVisible(false);
			return;
		}else if(e.getSource()==this.btnOk){			
			food.setFood_name(this.edtFoodname.getText());
			food.setFood_price(Float.parseFloat(this.edtFoodprice.getText()));
			food.setFood_num(Integer.parseInt(this.edtFoodnum.getText()));
			food.setFood_format(this.edtFoodformat.getText());
			food.setFood_dsp(this.edtFooddsp.getText());
			try {
				(new FoodManager()).addFood(food);
				this.setVisible(false);
			} catch (BaseException e1) {
				this.food=null;
				JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	public Food getFood() {
		return food;
	}
}
