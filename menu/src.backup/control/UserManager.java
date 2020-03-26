package control;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.User;
import util.BaseException;
import util.BusinessException;
import util.DBException;
import util.DBUtil;

public class UserManager {
	public static User currentUser=null;
	public List<User> loadAllUsers(boolean withDeletedUser)throws BaseException{
		List<User> result = new ArrayList<User>();
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select user_id,user_name,user_sex,user_type,user_tel,user_mail,"
					+ "user_city,user_reg,user_del from user_info";
			if(!withDeletedUser)
				sql+=" where user_del is null ";
			sql+=" order by User_id";
			java.sql.Statement st=conn.createStatement();
			java.sql.ResultSet rs=st.executeQuery(sql);
			while(rs.next()){
				User u=new User();
				u.setUser_id(rs.getString(1));
				u.setUser_name(rs.getString(2));
				u.setUser_sex(rs.getString(3));
				u.setUser_type(rs.getString(4));
				u.setUser_tel(rs.getString(5));
				u.setUser_mail(rs.getString(6));
				u.setUser_city(rs.getString(7));
				u.setUser_reg(rs.getDate(8));
				u.setUser_del(rs.getDate(9));
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
	public User loadUser(String userid)throws BaseException{
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select user_id,user_name,user_sex,user_pwd,user_type,user_tel,user_mail,"
					+ "user_city,user_reg,user_del from user_info where user_id=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,userid);
			java.sql.ResultSet rs=pst.executeQuery();
			if(!rs.next()) throw new BusinessException("��½�˺Ų� ����");
			User u=new User();
			u.setUser_id(rs.getString(1));
			u.setUser_name(rs.getString(2));
			u.setUser_sex(rs.getString(3));
			u.setUser_pwd(rs.getString(4));
			u.setUser_type(rs.getString(5));
			u.setUser_tel(rs.getString(6));
			u.setUser_mail(rs.getString(7));
			u.setUser_city(rs.getString(8));
			u.setUser_reg(rs.getDate(9));
			u.setUser_del(rs.getDate(10));
			if(u.getUser_del()!=null) throw new BusinessException("���˺��Ѿ���ɾ��");
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
	public void createUser(User user)throws BaseException{
		if(user.getUser_id()==null || "".equals(user.getUser_id()) || user.getUser_id().length()>30){
			throw new BusinessException("��½�˺ű�����1-30���ַ�");
		}
		if(user.getUser_pwd()==null || "".equals(user.getUser_pwd()) || user.getUser_pwd().length()>30) {
			throw new BusinessException("��½���������1-30���ַ�");
		}
		if(user.getUser_name()==null || "".equals(user.getUser_name()) || user.getUser_name().length()>30){
			throw new BusinessException("�˺����Ʊ�����1-30���ַ�");
		}
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select * from user_info where user_id=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,user.getUser_id());
			java.sql.ResultSet rs=pst.executeQuery();
			if(rs.next()) throw new BusinessException("��½�˺��Ѿ�����");
			rs.close();
			pst.close();
			sql="insert into user_info(user_id,user_name,user_sex,user_pwd,user_type,user_reg,"
					+ "user_tel,user_mail,user_city) values(?,?,?,?,?,?,?,?,?)";
			//�û�����Ĭ��Ϊ��
			pst=conn.prepareStatement(sql);
			pst.setString(1,user.getUser_id());
			pst.setString(2,user.getUser_name());
			pst.setString(3,user.getUser_sex());
			pst.setString(4,user.getUser_pwd());
			pst.setString(5,"�û�");
			pst.setTimestamp(6,new java.sql.Timestamp(System.currentTimeMillis()));
			pst.setString(7,user.getUser_tel());
			pst.setString(8,user.getUser_mail());
			pst.setString(9,user.getUser_city());
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
	public void changeUserPwd(String userid,String oldPwd,String newPwd)throws BaseException{
		if(oldPwd==null) throw new BusinessException("ԭʼ���벻��Ϊ��");
		if(newPwd==null || "".equals(newPwd) || newPwd.length()>30) throw new BusinessException("����Ϊ1-30���ַ�");
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select user_pwd from user_info where user_id=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,userid);
			java.sql.ResultSet rs=pst.executeQuery();
			if(!rs.next()) throw new BusinessException("��½�˺Ų� ����");
			if(!oldPwd.equals(rs.getString(1))) throw new BusinessException("ԭʼ�������");
			rs.close();
			pst.close();
			sql="update user_info set user_pwd=? where user_id=?";
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
	public void deleteUser(String userid)throws BaseException{
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select user_del from user_info where user_id=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,userid);
			java.sql.ResultSet rs=pst.executeQuery();
			if(!rs.next()) throw new BusinessException("��½�˺Ų� ����");
			if(rs.getDate(1)!=null) throw new BusinessException("���˺��Ѿ���ɾ��");
			rs.close();
			pst.close();
			sql="update user_info set suer_name=?,user_del=? where user_id=?";
			pst=conn.prepareStatement(sql);
			pst.setString(1, "��ɾ���û�");
			pst.setTimestamp(2, new java.sql.Timestamp(System.currentTimeMillis()));
			pst.setString(3, userid);
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
	public void modifyUser(User user)throws BaseException{
		if(user.getUser_name()==null||"".equals(user.getUser_name()))
			throw new BusinessException("�ǳƲ���Ϊ��");
		Connection conn = null;
		try {
			conn=DBUtil.getConnection();
			String sql = "select * from user_info where user_id=?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, user.getUser_id());
			java.sql.ResultSet rs = pst.executeQuery();
			if(!rs.next())
				throw new BusinessException("���û�������");
			else {
				sql = "update user_info set user_name=?,user_sex=?,user_tel=?"
						+ ",user_mail=?,user_city=? where user_id=?";
				pst=conn.prepareStatement(sql);
				pst.setString(1, user.getUser_name());
				pst.setString(2, user.getUser_sex());
				pst.setString(3, user.getUser_tel());
				pst.setString(4, user.getUser_mail());
				pst.setString(5, user.getUser_city());
				pst.setString(6, user.getUser_id());
				pst.execute();
				pst.close();
				rs.close();
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
	}
	public void modifypwd(String userid,String newpwd)throws BaseException{
		Connection conn = null;
		try {
			conn=DBUtil.getConnection();
			String sql = "select * from user_info where user_id=?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, userid);
			java.sql.ResultSet rs = pst.executeQuery();
			if(!rs.next())
				throw new BusinessException("���û�������");
			else {
				sql = "update user_info set user_pwd=? where user_id=?";
				pst=conn.prepareStatement(sql);
				pst.setString(1, newpwd);
				pst.setString(2, userid);
				pst.execute();
				pst.close();
				rs.close();
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
	}
}
