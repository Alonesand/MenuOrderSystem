package control;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import model.Food;
import util.BaseException;
import util.BusinessException;
import util.DBException;
import util.DBUtil;

public class FoodManager {
	public List<Food> loadAllFoods() throws BaseException{
		List<Food> result=new ArrayList<Food>();
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select food_id,food_name,food_price,food_num,food_format from food_info";
			java.sql.Statement st=conn.createStatement();
			java.sql.ResultSet rs=st.executeQuery(sql);
			while(rs.next()){
				Food f=new Food();
				f.setFood_id(rs.getInt(1));
				f.setFood_name(rs.getString(2));
				f.setFood_price(rs.getFloat(3));
				f.setFood_num(rs.getInt(4));
				f.setFood_format(rs.getString(5));
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
	public void modifyNum(Food f)throws BaseException{
		if(f.getFood_name()==null || "".equals(f.getFood_name()) || f.getFood_name().length()>20){
			throw new BusinessException("食材必须是1-30个字");
		}
		if((f.getFood_price())<0){
			throw new BusinessException("食材价格不能为空");
		}
		if(f.getFood_num()<0){
			throw new BusinessException("食材数量不能为空");
		}
		if("".equals(f.getFood_format())){
			throw new BusinessException("食材规格不能为空");
		}
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="update food_info set food_name=?,food_price=?,food_num=?,"
					+ "food_format=?,food_dsp=? where food_id=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst=conn.prepareStatement(sql);
			pst.setString(1, f.getFood_name());
			pst.setFloat(2, f.getFood_price());
			pst.setInt(3, f.getFood_num());
			pst.setString(4, f.getFood_format());
			pst.setString(5, f.getFood_dsp());
			pst.setInt(6, f.getFood_id());
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
	public Food loadFood(int foodid)throws BaseException{
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select food_id,food_name,food_price,food_num,food_format "
					+ "from food_info where food_id=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setInt(1,foodid);
			java.sql.ResultSet rs=pst.executeQuery();
			Food f=new Food();
			if(rs.next()) {
				f.setFood_id(rs.getInt(1));
				f.setFood_name(rs.getString(2));
				f.setFood_price(rs.getFloat(3));
				f.setFood_num(rs.getInt(4));
				f.setFood_format(rs.getString(5));
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
	public void addFood(Food f)throws BaseException{
		if(f.getFood_name()==null || "".equals(f.getFood_name()) || f.getFood_name().length()>20){
			throw new BusinessException("食材必须是1-30个字");
		}
		if((f.getFood_price())<0){
			throw new BusinessException("食材价格不能为空");
		}
		if(f.getFood_num()<0){
			throw new BusinessException("食材数量不能为空");
		}
		if("".equals(f.getFood_format())){
			throw new BusinessException("食材规格不能为空");
		}
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select food_name from food_info where food_name=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,f.getFood_name());
			java.sql.ResultSet rs=pst.executeQuery();
			if(rs.next()) throw new BusinessException("该食材已经存在");
			rs.close();
			pst.close();
			sql="insert into food_info(food_name,food_price,food_num,food_format,food_dsp"
					+ ") values(?,?,?,?,?)";
			pst=conn.prepareStatement(sql);
			pst.setString(1,f.getFood_name());
			pst.setFloat(2,f.getFood_price());
			pst.setInt(3,f.getFood_num());
			pst.setString(4,f.getFood_format());
			pst.setString(5, f.getFood_dsp());
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
	public void inFood(int id,int inNum)throws BaseException{
		Connection conn=null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select food_num from food_info where food_id=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst=conn.prepareStatement(sql);
			pst.setInt(1, id);
			java.sql.ResultSet rs = pst.executeQuery();
			if(!rs.next()) {
				throw new BusinessException("该食材不存在");
			}
			int now_num = rs.getInt(1);
			sql = "update food_info set food_num=? where food_id=?";
			pst=conn.prepareStatement(sql);
			pst.setInt(1, inNum+now_num);
			pst.setInt(2, id);
			pst.execute();
			rs.close();
			pst.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new DBException(e);
		}finally{
			if(conn!=null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
	public Food loadFood(String foodname)throws BaseException{
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select food_id,food_name,food_price,food_num,food_format "
					+ "from food_info where food_name=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,foodname);
			java.sql.ResultSet rs=pst.executeQuery();
			if(!rs.next()) {
				return null;
			}
			Food f=new Food();
			f.setFood_id(rs.getInt(1));
			f.setFood_name(rs.getString(2));
			f.setFood_price(rs.getFloat(3));
			f.setFood_num(rs.getInt(4));
			f.setFood_format(rs.getString(5));
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
}
