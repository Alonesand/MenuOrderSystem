package control;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import model.Food;
import model.Menu_use;
import model.Order;
import model.Order_detail;
import ui.FrmMain;
import util.BaseException;
import util.BusinessException;
import util.DBUtil;

public class OrderManager {
	public int max;
	private int orderid;
	public void addOrder(int menuid,String time,String add,String tel)throws BaseException{
		List<Menu_use> use = new ArrayList<>();
		List<Food> food = new ArrayList<>();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			String sql = "select material_id,menu_id,menu_name,food_id,food_name,"
					+ "use_num,use_format from menu_use where menu_id=?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setInt(1, menuid);
			java.sql.ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				Menu_use u = new Menu_use();
				u.setMaterial_id(rs.getInt(1));
				u.setMenu_id(rs.getInt(2));
				u.setMenu_name(rs.getString(3));
				u.setFood_id(rs.getInt(4));
				u.setFood_name(rs.getString(5));
				u.setUse_num(rs.getInt(6));
				u.setUse_format(rs.getString(7));
				use.add(u);
			}
			if(use.isEmpty()) {
				JOptionPane.showMessageDialog(null,"未找到食材","提示",JOptionPane.ERROR_MESSAGE);
			}
			pst.close();
			rs.close(); 
			for(int i=0;i<use.size();i++) {
				Food f = new Food();
				sql = "select food_id,food_name,food_price,food_num,food_format,"
						+ "food_dsp from food_info where food_id=?";
				pst = conn.prepareStatement(sql);
				pst.setInt(1, use.get(i).getFood_id());
				rs = pst.executeQuery();
				if(rs.next()) {
					f.setFood_id(rs.getInt(1));
					f.setFood_name(rs.getString(2));
					f.setFood_price(rs.getFloat(3));
					f.setFood_num(rs.getInt(4));
					f.setFood_format(rs.getString(5));
					f.setFood_dsp(rs.getString(6));
					food.add(f);
				}
			}
			pst.close();
			rs.close();
			for(int i=0;i<use.size();i++) {
				if(food.get(i).getFood_num()<use.get(i).getUse_num()) {
					JOptionPane.showMessageDialog(null,"食材不足，待管理员进购","提示",JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
			
			String sql2 = "insert into order_info(order_menuid,order_userid,order_price,order_time,"
					+ "order_add,order_tel,order_status) values(?,?,?,?,?,?,?)";
			pst = conn.prepareStatement(sql2);
			float price = 0;
			for(int i=0;i<use.size();i++) {//计算订单食材的总价
				if(use.get(i).getUse_format().equals("克")) {//以克为单位的食材按“元/500克”计算价格
					price+=use.get(i).getUse_num()/500*food.get(i).getFood_price();
				}else {
					price+=use.get(i).getUse_num()*food.get(i).getFood_price();
				}
			}
			pst.setInt(1, menuid);
			pst.setString(2, FrmMain.cUser.getUser_id());
			pst.setFloat(3, price);
			pst.setTimestamp(4,new java.sql.Timestamp(System.currentTimeMillis()+Integer.parseInt(time)*60*1000));
			//设置期望时间
			pst.setString(5, add);
			pst.setString(6, tel);
			pst.setString(7, "已下单等待配送");
			pst.execute();
			pst.close();
			rs.close();
			sql = "select max(order_id) from order_detail";
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();
			if(rs.next()) {
				max = rs.getInt(1)+1;
			}else {
				max = 1;
			}
			pst.close();
			rs.close();
			sql = "select max(order_id) from order_info";
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();
			if(rs.next())
				orderid = rs.getInt(1)+1;
			else
				orderid = 1;
			pst.close();
			rs.close();
			for(int i=0;i<use.size();i++) {
				sql = "insert into order_detail(order_id,food_id,menu_id,user_id,food_name,food_num,food_price,orderid) "
						+ "values(?,?,?,?,?,?,?,?)";
				pst = conn.prepareStatement(sql);
				pst.setInt(1, max);
				pst.setInt(2, use.get(i).getFood_id());
				pst.setInt(3, menuid);
				pst.setString(4, FrmMain.cUser.getUser_id());
				pst.setString(5, use.get(i).getFood_name());
				pst.setFloat(6, use.get(i).getUse_num());
				float price1 = food.get(i).getFood_price();
				pst.setFloat(7, price1);
				pst.setInt(8, orderid);
				pst.execute();
			}
			pst.close();
			for(int i=0;i<use.size();i++) {
				sql2 = "update food_info set food_num=? where food_id=?";
				pst = conn.prepareStatement(sql2);
				if(food.get(i).getFood_num()-use.get(i).getUse_num()<0) {
					JOptionPane.showMessageDialog(null,"食材不足，待管理员进购","提示",JOptionPane.ERROR_MESSAGE);
					return;
				}
				pst.setFloat(1, food.get(i).getFood_num()-use.get(i).getUse_num());
				pst.setInt(2, use.get(i).getFood_id());
				pst.execute();
			}
			conn.commit();
			JOptionPane.showMessageDialog(null,"下单成功，请在您的购物清单中查看", "提示", JOptionPane.INFORMATION_MESSAGE);
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public List<Order> loadOrder(String userid)throws BaseException{
		List<Order> order = new ArrayList<>();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select order_id,order_menuid,order_userid,order_price,order_time,"
					+ "order_add,order_tel,order_status from order_info where order_userid=?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, userid);
			java.sql.ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				Order o = new Order();
				o.setOrder_id(rs.getInt(1));
				o.setOrder_menuid(rs.getInt(2));
				o.setOrder_userid(rs.getString(3));
				o.setOrder_price(rs.getFloat(4));
				o.setOrder_time(rs.getDate(5));
				o.setOrder_add(rs.getString(6));
				o.setOrder_tel(rs.getString(7));
				o.setOrder_status(rs.getString(8));
				order.add(o);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return order;
	}
	public void delOrder(int orderid)throws BaseException{
		List<Order_detail> od = new ArrayList<>();
		List<Food> food = new ArrayList<>();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			String sql = "select order_id,food_id,food_name,food_num,food_price from order_detail"
					+ " where orderid=?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setInt(1, orderid);
			java.sql.ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				Order_detail o = new Order_detail();
				o.setOrder_id(rs.getInt(1));
				o.setFood_id(rs.getInt(2));
				o.setFood_name(rs.getString(3));
				o.setFood_num(rs.getInt(4));
				o.setFood_price(rs.getFloat(5));
				od.add(o);
			}
			pst.close();
			rs.close();
			for(int i=0;i<od.size();i++) {
				sql="select food_id,food_name,food_price,food_num,food_format from food_info where food_id=?";
				pst=conn.prepareStatement(sql);
				pst.setInt(1,od.get(i).getFood_id());
				rs=pst.executeQuery();
				if(rs.next()) {
					Food f=new Food();
					f.setFood_id(rs.getInt(1));
					f.setFood_name(rs.getString(2));
					f.setFood_price(rs.getFloat(3));
					f.setFood_num(rs.getInt(4));
					f.setFood_format(rs.getString(5));
					food.add(f);
				}
			}
			pst.close();
			rs.close();
			for(int i=0;i<food.size();i++) {
				sql = "update food_info set food_num=? where food_id=?";
				pst = conn.prepareStatement(sql);
				pst.setInt(1, food.get(i).getFood_num()+od.get(i).getFood_num());
				pst.setInt(2, food.get(i).getFood_id());
				pst.execute();
				pst.close();
			}
			
			sql = "update order_info set order_status=? where order_id=?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, "已退单");
			pst.setInt(2, orderid);
			pst.execute();
			pst.close();
			conn.commit();
			JOptionPane.showMessageDialog(null,"删除成功", "提示", JOptionPane.INFORMATION_MESSAGE);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}