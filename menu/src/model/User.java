package model;

import java.util.Date;

public class User {
	private String User_id;
	private String User_name;
	private String User_sex;
	private String User_pwd;
	private String User_tel;
	private String User_mail;
	private String User_city;
	private String User_type;
	private Date User_reg;
	private Date User_del;
	public String getUser_id() {
		return User_id;
	}
	public void setUser_id(String user_id) {
		User_id = user_id;
	}
	public String getUser_name() {
		return User_name;
	}
	public void setUser_name(String user_name) {
		User_name = user_name;
	}
	public String getUser_sex() {
		return User_sex;
	}
	public void setUser_sex(String user_sex) {
		User_sex = user_sex;
	}
	public String getUser_pwd() {
		return User_pwd;
	}
	public void setUser_pwd(String user_pwd) {
		User_pwd = user_pwd;
	}
	public String getUser_tel() {
		return User_tel;
	}
	public void setUser_tel(String user_tel) {
		User_tel = user_tel;
	}
	public String getUser_mail() {
		return User_mail;
	}
	public void setUser_mail(String user_mail) {
		User_mail = user_mail;
	}
	public String getUser_city() {
		return User_city;
	}
	public void setUser_city(String user_city) {
		User_city = user_city;
	}
	public String getUser_type() {
		return User_type;
	}
	public void setUser_type(String user_type) {
		User_type = user_type;
	}
	public Date getUser_reg() {
		return User_reg;
	}
	public void setUser_reg(Date user_reg) {
		User_reg = user_reg;
	}
	public Date getUser_del() {
		return User_del;
	}
	public void setUser_del(Date user_del) {
		User_del = user_del;
	}
	
}
