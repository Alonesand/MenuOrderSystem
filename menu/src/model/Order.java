package model;

import java.util.Date;

public class Order {
	private int order_id;
	private int order_menuid;
	private String order_userid;
	private float order_price;
	private Date order_time;
	private String order_add;
	private String order_tel;
	private String order_status;
	public int getOrder_id() {
		return order_id;
	}
	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}
	public int getOrder_menuid() {
		return order_menuid;
	}
	public void setOrder_menuid(int order_menuid) {
		this.order_menuid = order_menuid;
	}
	public String getOrder_userid() {
		return order_userid;
	}
	public void setOrder_userid(String order_userid) {
		this.order_userid = order_userid;
	}
	public float getOrder_price() {
		return order_price;
	}
	public void setOrder_price(float order_price) {
		this.order_price = order_price;
	}
	public Date getOrder_time() {
		return order_time;
	}
	public void setOrder_time(Date order_time) {
		this.order_time = order_time;
	}
	public String getOrder_add() {
		return order_add;
	}
	public void setOrder_add(String order_add) {
		this.order_add = order_add;
	}
	public String getOrder_tel() {
		return order_tel;
	}
	public void setOrder_tel(String order_tel) {
		this.order_tel = order_tel;
	}
	public String getOrder_status() {
		return order_status;
	}
	public void setOrder_status(String order_status) {
		this.order_status = order_status;
	}
	
}
