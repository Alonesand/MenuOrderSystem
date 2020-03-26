package model;

import java.util.Date;

public class God {
	private String God_id;
	private String God_name;
	private String God_pwd;
	private String God_type;
	private Date reg_time;
	private Date del_time;
	public String getGod_id() {
		return God_id;
	}
	public void setGod_id(String god_id) {
		God_id = god_id;
	}
	public String getGod_name() {
		return God_name;
	}
	public void setGod_name(String god_name) {
		God_name = god_name;
	}
	public String getGod_pwd() {
		return God_pwd;
	}
	public void setGod_pwd(String god_pwd) {
		God_pwd = god_pwd;
	}
	public String getGod_type() {
		return God_type;
	}
	public void setGod_type(String god_type) {
		God_type = god_type;
	}
	public Date getReg_time() {
		return reg_time;
	}
	public void setReg_time(Date reg_time) {
		this.reg_time = reg_time;
	}
	public Date getDel_time() {
		return del_time;
	}
	public void setDel_time(Date del_time) {
		this.del_time = del_time;
	}
	
	
}
