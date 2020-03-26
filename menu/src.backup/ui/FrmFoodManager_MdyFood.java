package ui;

import java.awt.BorderLayout;
import java.awt.Button;
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

public class FrmFoodManager_MdyFood extends JDialog implements ActionListener{
	private Food food = new Food();
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("ȷ��");
	private Button btnCancel = new Button("ȡ��");
	private JLabel labelFoodid = new JLabel("ʳ�ı�ţ�");
	private JLabel labelFoodname = new JLabel("ʳ�����ƣ�");
	private JLabel labelFoodprice = new JLabel("ʳ�ļ۸�");
	private JLabel labelFoodnum = new JLabel("ʳ��������");
	private JLabel labelFoodformat = new JLabel("ʳ�ĵ�λ��");
	private JLabel labelFooddsp = new JLabel("ʳ��������");
	
	private JTextField edtFoodid = new JTextField(20);
	private JTextField edtFoodname = new JTextField(20);
	private JTextField edtFoodprice = new JTextField(20);
	private JTextField edtFoodnum = new JTextField(20);
	private JTextField edtFoodformat = new JTextField(20);
	private JTextField edtFooddsp = new JTextField(20);
	public FrmFoodManager_MdyFood(JDialog f, String s,boolean b) {
		super(f, s, b);
		toolBar.add(btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.add(labelFoodid);
		workPane.add(edtFoodid);
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
		this.setSize(330, 260);
		// ��Ļ������ʾ
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
			int foodid=Integer.parseInt(this.edtFoodid.getText());
			String foodname=this.edtFoodname.getText();
			String foodprice=this.edtFoodprice.getText();
			String foodnum=this.edtFoodnum.getText();
			String foodformat=this.edtFoodformat.getText();
			String fooddsp=this.edtFooddsp.getText();
			food.setFood_id(foodid);
			food.setFood_name(foodname);
			food.setFood_price(Float.parseFloat(foodprice));
			food.setFood_num(Integer.parseInt(foodnum));
			food.setFood_format(foodformat);
			food.setFood_dsp(fooddsp);
			try {
				(new FoodManager()).modifyNum(food);
				this.setVisible(false);
			} catch (BaseException e1) {
				this.food=null;
				JOptionPane.showMessageDialog(null, e1.getMessage(),"����",JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	public Food getFood() {
		return food;
	}
}
