package model;

public class Com {
	private int com_id;
	private int menu_id;
	private String user_id;
	private float com_grade;
	private String com;
	public int getCom_id() {
		return com_id;
	}
	public void setCom_id(int com_id) {
		this.com_id = com_id;
	}
	public int getMenu_id() {
		return menu_id;
	}
	public void setMenu_id(int menu_id) {
		this.menu_id = menu_id;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public float getCom_grade() {
		return com_grade;
	}
	public void setCom_grade(float com_grade) {
		this.com_grade = com_grade;
	}
	public String getCom() {
		return com;
	}
	public void setCom(String com) {
		this.com = com;
	}
}
