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
import control.Menu_useManager;
import model.Food;
import model.Menu;
import util.BaseException;

public class FrmMenuUse extends JDialog implements ActionListener{
	private Food food = new Food();
	private JPanel workPane = new JPanel();
	
	private Button btnOk = new Button("ȷ��");
	private Button btnSearch = new Button("����");
	private JLabel labelmenu = new JLabel("ʳ������");
	private JLabel labelmenuname = new JLabel();
	private JLabel labeluse = new JLabel("����ʳ�ģ�");
	private JLabel labelFoodneed = new JLabel("��Ҫ");
	private JLabel labelFoodname = new JLabel();
	private JLabel labelFoodformat = new JLabel();
	
	private JTextField edtuse = new JTextField(10);
	private JTextField edtuse_num = new JTextField(5);
	Food ff = new Food();
	Menu mm = new Menu();
	public FrmMenuUse(JDialog f, String s,boolean b) {
		super(f, s, b);
		mm = menuItem_MyMenu.m;
		ff = FrmFoodinManager.f;
		workPane.add(labelmenu);
		labelmenuname.setText(mm.getMenu_name());
		workPane.add(labelmenuname);
		workPane.add(labeluse);
		workPane.add(edtuse);
		workPane.add(btnSearch);
		workPane.add(labelFoodname);
		workPane.add(labelFoodneed);
		workPane.add(edtuse_num);
		workPane.add(labelFoodformat);
		workPane.add(btnOk);
		
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(600, 100);
		// ��Ļ������ʾ
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();
		this.btnOk.addActionListener(this);
		this.btnSearch.addActionListener(this);
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.btnSearch) {
			String foodname = this.edtuse.getText();
			if(foodname.equals("")||foodname.equals(null)) {
				JOptionPane.showMessageDialog(null,  "����д��Ҫ��ʳ��","��ʾ",JOptionPane.INFORMATION_MESSAGE);			
			}else {
				try {
					food = (new FoodManager()).loadFood(foodname);
					if(food==null) {
						JOptionPane.showMessageDialog(this.getContentPane(),"δ�ҵ���ʳ��", "��ʾ", JOptionPane.ERROR_MESSAGE);
						return;
					}
				} catch (BaseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				labelFoodname.setText(food.getFood_name());
				labelFoodformat.setText(food.getFood_format());
			}
		}else if(e.getSource()==this.btnOk) {
			if(edtuse_num.getText()==null||"".equals(edtuse_num.getText())) {
				JOptionPane.showMessageDialog(null,  "����д����","��ʾ",JOptionPane.INFORMATION_MESSAGE);
			}else {
				int menuid = mm.getMenu_id();
				int foodid = food.getFood_id();
				int innum = Integer.parseInt(edtuse_num.getText());
				try {
					(new Menu_useManager()).addMaterial(menuid,foodid,innum);
					JOptionPane.showMessageDialog(this.getContentPane(),"�����ɹ�", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
					this.setVisible(false);
				} catch (BaseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}

}
