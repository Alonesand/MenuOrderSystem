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

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import control.FoodManager;
import model.Food;
import util.BaseException;

public class FrmFoodManager_InFood extends JDialog implements ActionListener{
	private Food food = new Food();
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();

	
	private Button btnOk = new Button("确定");
	private Button btnCancel = new Button("取消");
	private JLabel labelFoodid = new JLabel("食材编号：");
	private JLabel labelFoodid2 = new JLabel();
	private JLabel labelFoodname = new JLabel("食材名称：");
	private JLabel labelFoodname2 = new JLabel();
	private JLabel labelFoodprice = new JLabel("食材价格：");
	private JLabel labelFoodprice2 = new JLabel();
	private JLabel labelFoodnum = new JLabel("食材数量：");
	private JLabel labelFoodnum2 = new JLabel();
	private JLabel labelFoodin = new JLabel("进购：");
	private JLabel labelFoodformat2 = new JLabel();
	
	private JTextField edtFoodin = new JTextField(10);
	Food ff = new Food();
	
	public FrmFoodManager_InFood(JDialog f, String s,boolean b) {
		super(f, s, b);
		ff = FrmFoodinManager.f;
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.add(labelFoodid);
		labelFoodid2.setText(String.valueOf(ff.getFood_id()));
		workPane.add(labelFoodid2);
		workPane.add(labelFoodname);
		labelFoodname.setLayout(new FlowLayout(FlowLayout.LEFT));
		labelFoodname2.setText(String.valueOf(ff.getFood_name()));
		workPane.add(labelFoodname2);
		workPane.add(labelFoodprice);
		labelFoodprice2.setText(String.valueOf(ff.getFood_price()));
		workPane.add(labelFoodprice2);
		workPane.add(labelFoodnum);
		labelFoodnum2.setText(String.valueOf(ff.getFood_num()));
		workPane.add(labelFoodnum2);
		workPane.add(labelFoodformat2);
		workPane.add(labelFoodin);
		workPane.add(edtFoodin);
		labelFoodformat2.setText(String.valueOf(ff.getFood_format()));
		
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(650, 120);
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
		}else if(e.getSource()==this.btnOk) {
			if(edtFoodin.getText()==null) {
				JOptionPane.showMessageDialog(null,  "请填写进购量","提示",JOptionPane.ERROR_MESSAGE);
			}else {
				int id = ff.getFood_id();
				int innum = Integer.parseInt(edtFoodin.getText());
				try {
					(new FoodManager()).inFood(id,innum);
					JOptionPane.showMessageDialog(this.getContentPane(),"操作成功", "提示", JOptionPane.INFORMATION_MESSAGE);
					this.setVisible(false);
				} catch (BaseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}
	public Food getFood() {
		return food;
	}
}