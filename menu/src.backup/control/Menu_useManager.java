package control;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import model.Food;
import model.Menu_use;
import util.BaseException;
import util.BusinessException;
import util.DBException;
import util.DBUtil;

public class Menu_useManager {
	public void addMaterial(int menuid,int foodid,int num)throws BaseException{
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String manuname = (new MenuManager()).loadMenu(menuid).getMenu_name();
			Food f = (new FoodManager()).loadFood(foodid);
			String foodname = f.getFood_name();
			String format = f.getFood_format();
			String sql = "select use_num from menu_use where food_id=? and menu_id=?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setInt(1, foodid);
			pst.setInt(2, menuid);
			java.sql.ResultSet rs = pst.executeQuery();
			if(!rs.next()) {
				sql="insert into menu_use(menu_id,menu_name,food_id,food_name,use_num,use_format) "
						+ "values(?,?,?,?,?,?)";
				pst=conn.prepareStatement(sql);
				pst.setInt(1, menuid);
				pst.setString(2, manuname);
				pst.setInt(3, foodid);
				pst.setString(4, foodname);
				pst.setFloat(5, num);
				pst.setString(6, format);
				pst.execute();
				pst.close();
			}else {
				int use = rs.getInt(1);
				sql="update menu_use set use_num=? where food_id=? and menu_id=?";
				pst = conn.prepareStatement(sql);
				pst.setInt(1, use+num);
				pst.setInt(2, foodid);
				pst.setInt(3, menuid);
				pst.execute();
				pst.close();
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException(e);
		}
		finally{
			if(conn!=null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
	public List<Menu_use> loadAllUse(int menuid)throws BaseException{
		List<Menu_use> Mu = new ArrayList<>(); 
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select material_id,menu_id,menu_name,food_id,food_name,use_num,"
					+ "use_format from menu_use where menu_id=?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setInt(1, menuid);
			java.sql.ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				Menu_use mu = new Menu_use();
				mu.setMaterial_id(rs.getInt(1));
				mu.setMenu_id(rs.getInt(2));
				mu.setMenu_name(rs.getString(3));
				mu.setFood_id(rs.getInt(4));
				mu.setFood_name(rs.getString(5));
				mu.setUse_num(rs.getInt(6));
				mu.setUse_format(rs.getString(7));
				Mu.add(mu);
			}
			pst.close();
			rs.close();
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
		return Mu;
	}
}
