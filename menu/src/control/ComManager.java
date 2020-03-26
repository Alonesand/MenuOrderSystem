package control;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Com;
import model.Food;
import util.BusinessException;
import util.DBException;
import util.DBUtil;

public class ComManager {
	public List<Com> loadMenuComs(int menuid) throws BusinessException{
		List<Com> com = new ArrayList<>();
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select com_id,menu_id,user_id,com_grade,com from com_info where menu_id=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setInt(1, menuid);
			java.sql.ResultSet rs=pst.executeQuery();
			while(rs.next()){
				Com c = new Com();
				c.setCom_id(rs.getInt(1));
				c.setMenu_id(rs.getInt(2));
				c.setUser_id(rs.getString(3));
				c.setCom_grade(rs.getFloat(4));
				c.setCom(rs.getString(5));
				com.add(c);
			}
			pst.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
				try {
					throw new DBException(e);
				} catch (DBException e1) {
					e1.printStackTrace();
				}
		}
		finally{
			if(conn!=null)
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return com;
	}
	public void addCom(String userid,int menuid,float grade,String com)throws BusinessException{
		if(grade==0||"".equals(grade)) {
			throw new BusinessException("«ÎÃÓ–¥∆¿∑÷");
		}
		if("".equals(com)||com.equals(null)) {
			throw new BusinessException("«ÂÃÓ–¥∆¿¬€");
		}
		Connection conn = null;
		int people = 0;
		float sum = 0;
		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			String sql = "insert into com_info(menu_id,user_id,com_grade,com) values(?,?,?,?)";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setInt(1, menuid);
			pst.setString(2, userid);
			pst.setFloat(3, grade);
			pst.setString(4, com);
			pst.execute();
			pst.close();
			sql = "select count(*),sum(com_grade) from com_info where menu_id=?";
			pst = conn.prepareStatement(sql);
			pst.setInt(1, menuid);
			java.sql.ResultSet rs = pst.executeQuery();
			if(rs.next()) {
				people = rs.getInt(1);
				sum = rs.getFloat(2);
			}
			pst.close();
			rs.close();
			sql = "update menu_info set menu_grade=? where menu_id=?";
			pst =conn.prepareStatement(sql);
			pst.setFloat(1, sum/people);
			pst.setInt(2, menuid);
			pst.execute();
			pst.close();
			conn.commit();
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
	public void delComs(Com com) throws BusinessException{
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="delete from com_info where com_id=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setInt(1, com.getCom_id());
			pst.execute();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
				try {
					throw new DBException(e);
				} catch (DBException e1) {
					e1.printStackTrace();
				}
		}
		finally{
			if(conn!=null)
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
	}
	public List<Com> loadUserComs(String userid) throws BusinessException{
		List<Com> com = new ArrayList<>();
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select com_id,menu_id,user_id,com_grade,com from com_info where user_id=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1, userid);
			java.sql.ResultSet rs=pst.executeQuery();
			while(rs.next()){
				Com c = new Com();
				c.setCom_id(rs.getInt(1));
				c.setMenu_id(rs.getInt(2));
				c.setUser_id(rs.getString(3));
				c.setCom_grade(rs.getFloat(4));
				c.setCom(rs.getString(5));
				com.add(c);
			}
			pst.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
				try {
					throw new DBException(e);
				} catch (DBException e1) {
					e1.printStackTrace();
				}
		}
		finally{
			if(conn!=null)
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return com;
	}
}