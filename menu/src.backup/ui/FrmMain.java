package ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import control.FrmOrder;
import control.GodManager;
import control.MenuManager;
import control.UserManager;
import model.Menu;
import model.User;
import util.BaseException;
import util.BusinessException;

public class FrmMain extends JFrame implements ActionListener {
	public static User cUser = new User();
	public static Menu cMenu = new Menu();
	private JMenuBar menubar=new JMenuBar();
    private JMenu menu_Manager=new JMenu("信息管理");
    private JMenu menu_Food=new JMenu("菜谱食材");
    private JMenu menu_My=new JMenu("我的");
    
    private JButton Com = new JButton("评论");
    private JButton collect = new JButton("收藏");
    private JMenuItem menuItem_UserManager=new JMenuItem("用户管理");
    private JMenuItem menuItem_FoodManager=new JMenuItem("食材管理");
    private JMenuItem menuItem_MenuManager=new JMenuItem("菜谱管理");
    private JMenuItem menuItem_InfoManager=new JMenuItem("修改资料");
    private JMenuItem menuItem_pwdManager=new JMenuItem("修改密码");
    
    private JMenuItem menuItem_SearchMenu=new JMenuItem("查询菜谱");
    private JMenuItem menuItem_Searchuse=new JMenuItem("查看食材");
    private JMenuItem menuItem_addMenu=new JMenuItem("创建菜谱");
    private JMenuItem menuItem_InFood=new JMenuItem("采购食材");
    
    private JMenuItem menuItem_MyMenu=new JMenuItem("我的菜谱");
    private JMenuItem menuItem_List=new JMenuItem("购物清单");
    private JMenuItem menuItem_MyComment=new JMenuItem("我的评论");
    private JMenuItem menuItem_MyCollection=new JMenuItem("我的收藏");
    
    private JScrollPane scrollPane = new JScrollPane();
    private JPanel showPane = new JPanel();
    private JLabel uper = new JLabel();
    private JLabel menuname = new JLabel();
    private JLabel pic = new JLabel();
    private JLabel menudsp = new JLabel();

    
    private Object tblMenuTitle[]= {"编号","名称","评分","收藏","浏览"};
	private Object tblMenuData[][];
    DefaultTableModel tabMenuModel=new DefaultTableModel();
	private JTable dataTableMenu=new JTable(tabMenuModel);
	
	private FrmLogin dlgLogin=null;
	
	private Menu curMenu=null;
	private Menu menuInfo = null;
	private List<Menu> allMenu = null;
	public void reloadMenuTable(){
		TableStyle.setTableStyle(dataTableMenu);
		try {
			allMenu = (new MenuManager()).loadAllMenus();
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		tblMenuData =  new Object[allMenu.size()][6];
		for(int i=0;i<allMenu.size();i++){
			tblMenuData[i][0]=allMenu.get(i).getMenu_id();
			tblMenuData[i][1]=allMenu.get(i).getMenu_name();
			tblMenuData[i][2]=allMenu.get(i).getMenu_grade();
			tblMenuData[i][3]=allMenu.get(i).getMenu_collect();
			tblMenuData[i][4]=allMenu.get(i).getMenu_look();
		}
		tabMenuModel.setDataVector(tblMenuData,tblMenuTitle);
		this.dataTableMenu.validate();
		this.dataTableMenu.repaint();
	}
	
	public void reloadMenuInfoTable(int MenuIdx){
		if(MenuIdx<0) return;
		curMenu=allMenu.get(MenuIdx);
		try {
			menuInfo=(new MenuManager()).loadMenu(curMenu.getMenu_id());
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		menuname.setText(menuInfo.getMenu_name());
		uper.setText("提供者："+menuInfo.getMenu_user());
		Icon icon=new ImageIcon(menuInfo.getMenu_pic());
		pic.setIcon(icon);
		
		String str = "<html><body>"+menuInfo.getMenu_dsp()+"</body></html>";
		String str2 = str.replace("�?", "�?<br><br>");
	    menudsp.setText(str2);
	    Com.setVisible(true);
	    collect.setVisible(true);
	}
	
	public FrmMain(){
//		this.setExtendedState(Frame.MAXIMIZED_BOTH);
		this.setTitle("厨房助手");
		dlgLogin=new FrmLogin(this,"登陆",true);
		dlgLogin.setVisible(true);
	    //菜单
	    if(GodManager.currentGod!=null&&"管理�?".equals(GodManager.currentGod.getGod_type())){
	    	menu_Manager.add(menuItem_UserManager);
	    	menuItem_UserManager.addActionListener(this);
	    	menu_Manager.add(menuItem_FoodManager);
	    	menuItem_FoodManager.addActionListener(this);
	    	menu_Manager.add(menuItem_MenuManager);
	    	menuItem_MenuManager.addActionListener(this);
	    	menubar.add(menu_Manager);
	    	
	    	menu_Food.add(this.menuItem_InFood);
		    menuItem_InFood.addActionListener(this);
		    menubar.add(menu_Food);
		    
		    this.setJMenuBar(menubar);
		    this.setSize(1200,800);
		    
		    // 屏幕居中显示
		    double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
			double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
			this.setLocation((int) (width - this.getWidth()) / 2,
					(int) (height - this.getHeight()) / 2);

			this.validate();
	    }else {
	    	cUser = UserManager.currentUser;
	    	menu_Manager.add(menuItem_InfoManager);
	    	menuItem_InfoManager.addActionListener(this);
	    	menu_Manager.add(menuItem_pwdManager);
	    	menuItem_pwdManager.addActionListener(this);
	    	menubar.add(menu_Manager);
	    	
	    	menu_Food.add(this.menuItem_SearchMenu);
	    	menuItem_SearchMenu.addActionListener(this);
	    	menu_Food.add(this.menuItem_Searchuse);
	    	menuItem_Searchuse.addActionListener(this);
	    	menu_Food.add(menuItem_addMenu);
	    	menuItem_addMenu.addActionListener(this);
		    menubar.add(menu_Food);
		    
		    menu_My.add(menuItem_MyMenu);
		    menuItem_MyMenu.addActionListener(this);
		    menu_My.add(menuItem_List);
		    menuItem_List.addActionListener(this);
		    menu_My.add(menuItem_MyComment);
		    menuItem_MyComment.addActionListener(this);
		    menu_My.add(menuItem_MyCollection);
		    menuItem_MyCollection.addActionListener(this);
		    menubar.add(menu_My);

		    this.setJMenuBar(menubar);
		    this.getContentPane().add(new JScrollPane(this.dataTableMenu), BorderLayout.WEST);
		    
		    this.setSize(1500,1000);
		    
		    // 屏幕居中显示
			double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
			double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
			this.setLocation((int) (width - this.getWidth()) / 2,
					(int) (height - this.getHeight()) / 2);

			this.validate();
		    //鼠标点击左侧菜谱查看详细
		    this.dataTableMenu.addMouseListener(new MouseAdapter (){
		    	public void mouseClicked(MouseEvent e) {
					int i=FrmMain.this.dataTableMenu.getSelectedRow();
					if(i<0) {
						return;
					}else {
						FrmMain.this.reloadMenuInfoTable(i);
						try {
							cMenu = allMenu.get(dataTableMenu.getSelectedRow());
							(new MenuManager()).addLook(cMenu.getMenu_id());
						} catch (BaseException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
		    });
		    this.reloadMenuTable();
		    
			showPane.add(menuname);
		    showPane.add(uper);
		    uper.setFont(new Font("微软雅黑", Font.PLAIN, 18));
//		    showPane.add(look);
//		    showPane.add(count_look);
//		    showPane.add(coll);
//		    showPane.add(count_coll);
		    showPane.add(pic);
		    menuname.setFont(new Font("微软雅黑", Font.PLAIN, 36));
		    showPane.add(menudsp);
		    menudsp.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		    showPane.add(Com);
		    showPane.add(collect);
		    Com.setVisible(false);
		    collect.setVisible(false);
//		    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//		    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
//		    
		    this.getContentPane().add(scrollPane.add(this.showPane), BorderLayout.CENTER);
		    Com.addActionListener(this);
		    collect.addActionListener(this);
	    }	
	    this.setVisible(true);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource()==this.menuItem_UserManager){
			FrmUserManager dlg=new FrmUserManager(this,"用户管理",true);
			dlg.setVisible(true);
		}
		else if(e.getSource()==this.menuItem_FoodManager){
			FrmFoodManager dlg=new FrmFoodManager(this,"食材管理",true);
			dlg.setVisible(true);
		}
		else if(e.getSource()==this.menuItem_MenuManager){
			FrmMenuManager dlg=new FrmMenuManager(this,"菜谱管理",true);
			dlg.setVisible(true);
		}
		else if(e.getSource()==this.menuItem_InFood){
			FrmFoodinManager dlg=new FrmFoodinManager(this,"食材采购",true);
			dlg.setVisible(true);
		}
		else if(e.getSource()==this.menuItem_InfoManager){
			FrmUserInfoManager dlg=new FrmUserInfoManager(this,"修改资料",true);
			dlg.setVisible(true);
		}
		else if(e.getSource()==this.menuItem_pwdManager){
			menuItem_pwdManager dlg=new menuItem_pwdManager(this,"修改密码",true);
			dlg.setVisible(true);
		}
		else if(e.getSource()==this.menuItem_addMenu){
			menuItem_addMenu dlg=new menuItem_addMenu(this,"添加食谱",true);
			dlg.setVisible(true);
			this.reloadMenuTable();
		}
		else if(e.getSource()==this.menuItem_SearchMenu){
			menuItem_SearchMenu dlg=new menuItem_SearchMenu(this,"查询菜谱",true);
			dlg.setVisible(true);
		}
		else if(e.getSource()==this.menuItem_MyMenu){
			menuItem_MyMenu dlg=new menuItem_MyMenu(this,"我的菜谱",true);
			dlg.setVisible(true);
		}
		else if(e.getSource()==this.menuItem_Searchuse){
			int i=this.dataTableMenu.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "请�?�择菜谱","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}else {
				cMenu = allMenu.get(this.dataTableMenu.getSelectedRow());
				menuItem_Searchuse dlg=new menuItem_Searchuse(this,"查看食材",true);
				dlg.setVisible(true);
			}
		}
		if(e.getSource()==this.Com) {
			int i=this.dataTableMenu.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "请�?�择菜谱","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			else {
				FrmCom dlg = new FrmCom(this,"查看评论",true);
				dlg.setVisible(true);
			}
			this.reloadMenuTable();
		}
		if(e.getSource()==this.collect) {
			int i=this.dataTableMenu.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "请�?�择菜谱","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			else {
				cMenu = allMenu.get(this.dataTableMenu.getSelectedRow());
				try {
					(new MenuManager()).addCollection(cMenu,cUser);
				} catch (BaseException e1) {
					e1.printStackTrace();
					return;
				}
			}
		}
		if(e.getSource()==this.menuItem_List) {
			FrmOrder dlg = new FrmOrder(this,"查看订单",true);
			dlg.setVisible(true);
		}
		if(e.getSource()==this.menuItem_MyCollection) {
			FrmColl dlg = new FrmColl(this,"我的收藏",true);
			dlg.setVisible(true);
		}
		if(e.getSource()==this.menuItem_MyComment) {
			FrmCominfo dlg = new FrmCominfo(this,"查看评论",true);
			dlg.setVisible(true);
			this.reloadMenuTable();
		}
	}
}
