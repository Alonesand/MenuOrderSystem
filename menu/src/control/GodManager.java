package control;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.God;
import util.BaseException;
import util.BusinessException;
import util.DBException;
import util.DBUtil;

public class GodManager {
	public static God currentGod=null;
	public List<God> loadAllGods(boolean withDeletedGod)throws BaseException{
		List<God> result=new ArrayList<God>();
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select god_id,god_name,user_type,reg_time from god_info";
			if(!withDeletedGod)
				sql+=" where del_time is null ";
			sql+=" order by god_id";
			java.sql.Statement st=conn.createStatement();
			java.sql.ResultSet rs=st.executeQuery(sql);
			while(rs.next()){
				God u=new God();
				u.setGod_id(rs.getString(1));
				u.setGod_name(rs.getString(2));
				u.setGod_type(rs.getString(3));
				u.setReg_time(rs.getDate(4));
				result.add(u);
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
	public void createGod(God user)throws BaseException{
		if(user.getGod_id()==null || "".equals(user.getGod_id()) || user.getGod_id().length()>20){
			throw new BusinessException("登陆账号必须是1-30个字");
		}
		if(user.getGod_name()==null || "".equals(user.getGod_name()) || user.getGod_name().length()>50){
			throw new BusinessException("账号名称必须是1-30个字");
		}
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select * from god_info where god_id=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,user.getGod_id());
			java.sql.ResultSet rs=pst.executeQuery();
			if(rs.next()) throw new BusinessException("登陆账号已经存在");
			rs.close();
			pst.close();
			sql="insert into god_info(god_id,god_name,god_pwd,user_type,reg_time) values(?,?,?,?,now())";
			pst=conn.prepareStatement(sql);
			pst.setString(1,user.getGod_id());
			pst.setString(2,user.getGod_name());
			pst.setString(3,user.getGod_pwd());
			pst.setString(4,"管理员");
//			pst.setTimestamp(5,new java.sql.Timestamp(System.currentTimeMillis()));
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
	public void changeGodPwd(String userid,String oldPwd,String newPwd)throws BaseException{
		if(oldPwd==null) throw new BusinessException("原始密码不能为空");
		if(newPwd==null || "".equals(newPwd) || newPwd.length()>30) throw new BusinessException("必须为1-30个字符");
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select god_pwd from god_info where god_id=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,userid);
			java.sql.ResultSet rs=pst.executeQuery();
			if(!rs.next()) throw new BusinessException("登陆账号不 存在");
			if(!oldPwd.equals(rs.getString(1))) throw new BusinessException("原始密码错误");
			rs.close();
			pst.close();
			sql="update god_info set god_pwd=? where god_id=?";
			pst=conn.prepareStatement(sql);
			pst.setString(1, newPwd);
			pst.setString(2, userid);
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
//	public void resetGodPwd(String userid)throws BaseException{
//		Connection conn=null;
//		try {
//			conn=DBUtil.getConnection();
//			String sql="select * from god_info where god_id=?";
//			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
//			pst.setString(1,userid);
//			java.sql.ResultSet rs=pst.executeQuery();
//			if(!rs.next()) throw new BusinessException("登陆账号不 存在");
//			rs.close();
//			pst.close();
//			sql="update god_info set god_pwd=? where god_id=?";
//			pst=conn.prepareStatement(sql);
//			pst.setString(1, userid);
//			pst.setString(2, userid);
//			pst.execute();
//			pst.close();
//		} catch (SQLException e) {
//			e.printStackTrace();
//			throw new DBException(e);
//		}
//		finally{
//			if(conn!=null)
//				try {
//					conn.close();
//				} catch (SQLException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//		}
//	}
	public void deleteGod(String userid)throws BaseException{
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select del_time from god_info where god_id=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,userid);
			java.sql.ResultSet rs=pst.executeQuery();
			if(!rs.next()) throw new BusinessException("登陆账号不 存在");
			if(rs.getDate(1)!=null) throw new BusinessException("该账号已经被删除");
			rs.close();
			pst.close();
			sql="update god_info set del_time=? where god_id=?";
			pst=conn.prepareStatement(sql);
			pst.setTimestamp(1, new java.sql.Timestamp(System.currentTimeMillis()));
			pst.setString(2, userid);
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
	public God loadGod(String userid)throws BaseException{
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select god_id,god_name,god_pwd,user_type,reg_time,del_time from god_info where god_id=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,userid);
			java.sql.ResultSet rs=pst.executeQuery();
			if(!rs.next()) throw new BusinessException("登陆账号不 存在");
			God u=new God();
			u.setGod_id(rs.getString(1));
			u.setGod_name(rs.getString(2));
			u.setGod_pwd(rs.getString(3));
			u.setGod_type(rs.getString(4));
			u.setReg_time(rs.getDate(5));
			u.setDel_time(rs.getDate(6));
			if(u.getDel_time()!=null) throw new BusinessException("该账号已经被删除");
			rs.close();
			pst.close();
			return u;
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

}
