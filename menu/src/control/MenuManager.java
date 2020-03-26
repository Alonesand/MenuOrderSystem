package control;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import model.Menu;
import model.My;
import model.User;
import util.BaseException;
import util.BusinessException;
import util.DBException;
import util.DBUtil;

public class MenuManager {

	public List<Menu> loadAllMenus() throws BaseException{
		List<Menu> result=new ArrayList<Menu>();
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select menu_id,menu_name,menu_pic,menu_user,menu_dsp,menu_grade,"
					+ "menu_collect,menu_look from menu_info";
			java.sql.Statement st=conn.createStatement();
			java.sql.ResultSet rs=st.executeQuery(sql);
			while(rs.next()){
				Menu f=new Menu();
				f.setMenu_id(rs.getInt(1));
				f.setMenu_name(rs.getString(2));
				f.setMenu_pic(rs.getString(3));
				f.setMenu_user(rs.getString(4));
				f.setMenu_dsp(rs.getString(5));
				f.setMenu_grade(rs.getFloat(6));
				f.setMenu_collect(rs.getInt(7));
				f.setMenu_look(rs.getInt(8));
				result.add(f);
			}
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
		return result;
	}
	public Menu loadMenu(int menuid)throws BaseException{
		Menu f = new Menu();
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select menu_id,menu_name,menu_pic,menu_user,menu_dsp,menu_grade,"
					+ "menu_collect,menu_look from menu_info where menu_id=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setInt(1,menuid);
			java.sql.ResultSet rs=pst.executeQuery();
			if(rs.next()) {
				f.setMenu_id(rs.getInt(1));
				f.setMenu_name(rs.getString(2));
				f.setMenu_pic(rs.getString(3));
				f.setMenu_user(rs.getString(4));
				f.setMenu_dsp(rs.getString(5));
				f.setMenu_grade(rs.getFloat(6));
				f.setMenu_collect(rs.getInt(7));
				f.setMenu_look(rs.getInt(8));
			}else {
				throw new BusinessException("未找到该菜谱");
			}
			rs.close();
			pst.close();
			return f;
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
	public void addMenu(Menu f)throws BaseException{
		if(f.getMenu_name()==null || "".equals(f.getMenu_name()) || f.getMenu_name().length()>20){
			throw new BusinessException("食材必须是1-30个字");
		}
		if(f.getMenu_pic()==null||"".equals(f.getMenu_pic())) {
			throw new BusinessException("请选择图片");
		}
		if("".equals(f.getMenu_dsp()) || f.getMenu_dsp()==null){
			throw new BusinessException("食材详情不能为空");
		}
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select menu_name from menu_info where menu_name=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,f.getMenu_name());
			java.sql.ResultSet rs=pst.executeQuery();
			if(rs.next()) throw new BusinessException("该菜谱已经存在");
			rs.close();
			pst.close();
			sql="insert into menu_info(menu_name,menu_pic,menu_user,menu_dsp,menu_grade,menu_collect,"
					+ "menu_look) values(?,?,?,?,?,?,?)";
			pst=conn.prepareStatement(sql);
			pst.setString(1,f.getMenu_name());
			pst.setString(2,f.getMenu_pic());
			pst.setString(3,(new UserManager()).currentUser.getUser_id());
			pst.setString(4,f.getMenu_dsp());
			pst.setFloat(5, 0);
			pst.setInt(6, 0);
			pst.setInt(7, 0);
			pst.execute();
			pst.close();
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
	public void deleteMenu(int menuid)throws BaseException{
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql = "delete from menu_info where menu_id=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst = conn.prepareStatement(sql);
			pst.setInt(1, menuid);
			pst.execute();
			pst.close();
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
	public List<Menu> search(String menuname) throws BaseException{
		List<Menu> n = new ArrayList<Menu>();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select menu_id,menu_name,menu_pic,menu_user,menu_dsp,menu_grade,"
					+ "menu_collect,menu_look from menu_info where menu_name like '%"+menuname+"%'";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			java.sql.ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				Menu m = new Menu();
				m.setMenu_id(rs.getInt(1));
				m.setMenu_name(rs.getString(2));
				m.setMenu_pic(rs.getString(3));
				m.setMenu_user(rs.getString(4));
				m.setMenu_dsp(rs.getString(5));
				m.setMenu_grade(rs.getFloat(6));
				m.setMenu_collect(rs.getInt(7));
				m.setMenu_look(rs.getInt(8));
				n.add(m);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return n;
	}
	public List<Menu> myMenu() throws BaseException{
		String uname = (new UserManager()).currentUser.getUser_id();
		List<Menu> M = new ArrayList<Menu>();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select menu_id,menu_name,menu_pic,menu_user,menu_dsp,menu_grade,"
					+ "menu_collect,menu_look from menu_info where menu_user=?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, uname);
			java.sql.ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				Menu m = new Menu();
				m.setMenu_id(rs.getInt(1));
				m.setMenu_name(rs.getString(2));
				m.setMenu_pic(rs.getString(3));
				m.setMenu_user(rs.getString(4));
				m.setMenu_dsp(rs.getString(5));
				m.setMenu_grade(rs.getFloat(6));
				m.setMenu_collect(rs.getInt(7));
				m.setMenu_look(rs.getInt(8));
				M.add(m);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return M;
	}
	public void addCollection(Menu menu,User cUser)throws BaseException{
		Menu f = menu;
		My m = new My();
		Connection conn=null;
		int coll;
		try {
			conn=DBUtil.getConnection();
			conn.setAutoCommit(false);
			String sql = "select collect_id,user_id,menu_id,collect_flag"
					+ " from my_info where menu_id=? and user_id=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setInt(1, menu.getMenu_id());
			pst.setString(2, cUser.getUser_id());
			java.sql.ResultSet rs=pst.executeQuery();
			if(rs.next()) {
				m.setCollect_id(rs.getInt(1));
				m.setUser_id(rs.getString(2));
				m.setMenu_id(rs.getInt(3));
				m.setCollect_flag(rs.getInt(4));
			}else {
				sql="insert into my_info(user_id,menu_id,collect_flag) values(?,?,?)";
				pst=conn.prepareStatement(sql);
				pst.setString(1, cUser.getUser_id());
				pst.setInt(2, menu.getMenu_id());
				pst.setInt(3, 0);
				pst.execute();
				sql = "select collect_id,user_id,menu_id,collect_flag"
						+ " from my_info where menu_id=? and user_id=?";
				pst = conn.prepareStatement(sql);
				pst.setInt(1, menu.getMenu_id());
				pst.setString(2, cUser.getUser_id());
				rs = pst.executeQuery();
				if(rs.next()) {
					m.setCollect_id(rs.getInt(1));
					m.setUser_id(rs.getString(2));
					m.setMenu_id(rs.getInt(3));
					m.setCollect_flag(rs.getInt(4));
				}
			}
			if(m.getCollect_flag()==1) {
				try {
					JOptionPane.showMessageDialog(null, "不能重复收藏", "错误",JOptionPane.ERROR_MESSAGE);
					return;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return;
			}
			pst.close();
			rs.close();
			coll = f.getMenu_collect()+1;
			sql="update menu_info set menu_collect=? where menu_id=?";
			pst=conn.prepareStatement(sql);
			pst.setInt(1,coll);
			pst.setInt(2,menu.getMenu_id());
			pst.execute();
			pst.close();
			rs.close();
			sql="update my_info set collect_flag=? where menu_id=? and user_id=?";
			pst=conn.prepareStatement(sql);			
			pst.setInt(1, 1);
			pst.setInt(2, menu.getMenu_id());
			pst.setString(3, cUser.getUser_id());
			pst.execute();
			pst.close();
			rs.close();
			conn.commit();
			JOptionPane.showMessageDialog(null,"收藏成功", "提示", JOptionPane.INFORMATION_MESSAGE);
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
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
	public void addLook(int menuid)throws BaseException{
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select menu_look from menu_info where menu_id=?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setInt(1, menuid);
			java.sql.ResultSet rs = pst.executeQuery();
			int look_num=0;
			if(rs.next()) {
				look_num = rs.getInt(1);
			}
			sql = "update menu_info set menu_look=? where menu_id=?";
			pst = conn.prepareStatement(sql);
			pst.setInt(1, look_num+1);
			pst.setInt(2, menuid);
			pst.execute();
			pst.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}