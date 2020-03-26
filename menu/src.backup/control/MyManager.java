package control;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.My;
import util.BusinessException;
import util.DBUtil;

public class MyManager {
	public List<My> loadMy(String userid)throws BusinessException{
		List<My> my = new ArrayList<>();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select collect_id,user_id,menu_id,collect_flag "
					+ "from my_info where user_id=? and collect_flag=1";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, userid);
			java.sql.ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				My m = new My();
				m.setCollect_id(rs.getInt(1));
				m.setUser_id(rs.getString(2));
				m.setMenu_id(rs.getInt(3));
				m.setCollect_flag(rs.getInt(4));
				my.add(m);
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
		
		return my;
	}
	public void delMyColl(int menuid,String userid)throws BusinessException{
		Connection conn = null;
		My my = new My();
		try {
			conn = DBUtil.getConnection();
			String sql = "select collect_id,user_id,menu_id,collect_flag from my_info where menu_id=? and user_id=?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setInt(1, menuid);
			pst.setString(2, userid);
			java.sql.ResultSet rs = pst.executeQuery();
			if(rs.next()) {
				my.setCollect_id(rs.getInt(1));
				my.setUser_id(rs.getString(2));
				my.setMenu_id(rs.getInt(3));
				my.setCollect_flag(rs.getInt(4));
			}
			pst.close();
			rs.close();
			sql = "update my_info set collect_flag=0 where user_id=? and menu_id=?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, userid);
			pst.setInt(2, menuid);
			pst.execute();
			pst.close();
			sql = "select menu_collect from menu_info where menu_id=?";
			pst = conn.prepareStatement(sql);
			pst.setInt(1, menuid);
			rs = pst.executeQuery();
			int collect_num=0;
			if(rs.next()) {
				collect_num = rs.getInt(1);
			}
			collect_num-=1;
			pst.close();
			rs.close();
			sql = "update menu_info set menu_collect=? where menu_id=?";
			pst = conn.prepareStatement(sql);
			pst.setInt(1, collect_num);
			pst.setInt(2, menuid);
			pst.execute();
			pst.close();
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
	}
}
