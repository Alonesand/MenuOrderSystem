package ui;

import java.awt.Font;
import java.util.Enumeration;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
		//����https://blog.csdn.net/LQdesolation/article/details/80232717
public class TableStyle {
	public static void setTableStyle(JTable jtb) {

        //����ѡ���еı���ɫ
        //jtb.setSelectionBackground(new Color(224, 242, 255));

        //���ñ��ÿ�еĸ߶�
        jtb.setRowHeight(35);

        // ���õ����ͷ�Զ�ʵ������
        jtb.setAutoCreateRowSorter(true);
        // ���ñ�ͷ���־�����ʾ
        DefaultTableCellRenderer  renderer = (DefaultTableCellRenderer) jtb.getTableHeader().getDefaultRenderer();
        renderer.setHorizontalAlignment(renderer.CENTER);

        // ���ñ���е����ݾ�����ʾ
        DefaultTableCellRenderer r=new DefaultTableCellRenderer();
        r.setHorizontalAlignment(JLabel.CENTER);
        jtb.setDefaultRenderer(Object.class,r);

        jtb.setFocusable(false);

        jtb.setFont(new Font("΢���ź�", Font.PLAIN, 15));
        fitTableColumns(jtb);
    }

    // ���������Զ����ڱ����п��
    @SuppressWarnings("rawtypes")
    public static void fitTableColumns(JTable myTable){
         myTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
         JTableHeader header = myTable.getTableHeader();
         int rowCount = myTable.getRowCount();
         Enumeration columns = myTable.getColumnModel().getColumns();
         while(columns.hasMoreElements()){
             TableColumn column = (TableColumn)columns.nextElement();
             int col = header.getColumnModel().getColumnIndex(column.getIdentifier());
             int width = (int)header.getDefaultRenderer().getTableCellRendererComponent
             (myTable, column.getIdentifier(), false, false, -1, col).getPreferredSize().getWidth();
             for(int row = 0; row < rowCount; row++){
                 int preferedWidth = (int)myTable.getCellRenderer(row, col).getTableCellRendererComponent
                 (myTable, myTable.getValueAt(row, col), false, false, row, col).getPreferredSize().getWidth();
                 width = Math.max(width, preferedWidth);
             }
             header.setResizingColumn(column); // ���к���Ҫ
             column.setWidth(width+myTable.getIntercellSpacing().width);
         }
    }
}
