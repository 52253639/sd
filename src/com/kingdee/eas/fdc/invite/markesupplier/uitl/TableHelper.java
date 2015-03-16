package com.kingdee.eas.fdc.invite.markesupplier.uitl;

import java.awt.Component;
import java.awt.Rectangle;
import java.math.BigDecimal;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JMenuItem;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSortManager;
import com.kingdee.bos.ctrl.kdf.table.KDTViewManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.foot.KDTFootManager;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDLayout;
import com.kingdee.bos.ctrl.swing.KDPanel;
import com.kingdee.bos.ctrl.swing.KDPopupMenu;
import com.kingdee.bos.ctrl.swing.KDSeparator;
import com.kingdee.bos.dao.query.SQLExecutorFactory;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.eas.framework.client.CoreUI;
import com.kingdee.eas.framework.client.multiDetail.DetailPanel;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.enums.EnumUtils;

public class TableHelper {
	//右键菜单定义
	public static void addPopMenu(CoreUI ui,KDTable tblMain, ItemAction[] action)
    {

		KDPopupMenu kdmenu = new KDPopupMenu();
        for(int i = 0; i < action.length; i++){            
            JMenuItem item = new JMenuItem();
            item.setAction(action[i]);
            kdmenu.add(item);
            item.setEnabled(action[i].isEnabled());
        }

        kdmenu.add(new KDSeparator());
        
        KDPopupMenu menu = ui.getMenuManager(tblMain).getMenu();
        int count = menu.getComponentCount();
        for(int i = 0; i < count; i++){
            kdmenu.add(menu.getComponent(0));
        }
        ui.getMenuManager(tblMain).setMenu(kdmenu);

    }
	//设置分组融合
	public static void setMerge(KDTable table, String[] columnNames,
			boolean isMerge) {

		for (int i = 0, count = columnNames.length; i < count; i++) {
			table.getColumn(columnNames[i]).setMergeable(isMerge);
		}

	}
	/**
	 * 设置融合
	 * */
	public static void mergeThemeRow(KDTable table, String columnName,int colIndex) {
		String theme = "";
		String lastTheme = "";
		KDTMergeManager mm = table.getMergeManager();
		int rowIndx = 0;
		int endIndx = 0;
		for (int i = 0; i < table.getRowCount(); i++) {
			endIndx = i;
			theme = UIRuleUtil.getString(table.getRow(i).getCell(columnName).getValue()) ; // 当前主题
			if (i > 0) {
				lastTheme = UIRuleUtil.getString(table.getRow(i - 1).getCell(columnName).getValue()); // 上一主题
				if (!theme.equals(lastTheme)) { // 获取当前主题 与 上一主题 不相同，所在的行号
					mm.mergeBlock(rowIndx, colIndex, endIndx - 1, colIndex); // 将最后相同的主题融合
					rowIndx = endIndx;
				}
			}
		}
		mm.mergeBlock(rowIndx, colIndex, endIndx, colIndex); // 将最后相同的主题融合
	}

	
	/**
	 * 设置合计行 可以对多列求和，
	 * @param columnName 列名
	 * **/
	public static void getFootRow(KDTable tblMain, String[] columnName) {
		IRow footRow = null;
		KDTFootManager footRowManager = tblMain.getFootManager();
		if (footRowManager == null) {
			String total = EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_Total");
			footRowManager = new KDTFootManager(tblMain);
			footRowManager.addFootView();
			tblMain.setFootManager(footRowManager);
			footRow = footRowManager.addFootRow(0);
			footRow.getStyleAttributes().setHorizontalAlign(com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment.getAlignment("right"));
			tblMain.getIndexColumn().setWidthAdjustMode((short) 1);
			tblMain.getIndexColumn().setWidth(30);
			footRowManager.addIndexText(0, total);
		} else {
			footRow = footRowManager.getFootRow(0);
		}
		int columnCount = tblMain.getColumnCount();
		for (int c = 0; c < columnCount; c++) {
			String fieldName = tblMain.getColumn(c).getKey();
			for (int i = 0; i < columnName.length; i++) {
				String colName = (String) columnName[i];
				if (colName.equalsIgnoreCase(fieldName)) {
					ICell cell = footRow.getCell(c);
					cell.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
					cell.getStyleAttributes().setHorizontalAlign(com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment.getAlignment("right"));
					cell.getStyleAttributes().setFontColor(java.awt.Color.BLACK);
					cell.setValue(getColumnValueSum(tblMain, colName));
				}
			}

		}
		footRow.getStyleAttributes().setBackground(new java.awt.Color(246, 246, 191));
	}
	/**
	 * 设置表格可以排序
	 * */
	public static void setTableAutoSort(KDTable tblMain)
	  {
	    KDTSortManager sm = new KDTSortManager(tblMain);
	    sm.setSortAuto(true);
	    for (int index = 0; index < tblMain.getColumnCount(); index++)
	      tblMain.getColumn(index).setSortable(true);
	  }
	
	/**
     * 获取表格选中行的某列值
     * SystemStatusCtrolUtils
     */
	public static String[] getSelectRowFieldValue(KDTable kdtTable, String fieldname)
    {
        if(!isSelectRow(kdtTable))
            return null;
        KDTSelectManager sm = kdtTable.getSelectManager();
        int blockCount = sm.size();
        int keyField = kdtTable.getColumnIndex(fieldname);
        ArrayList retList = new ArrayList();
        Object value = null;
        for(int i = 0; i < blockCount; i++)
        {
            KDTSelectBlock block = sm.get(i);
            int j = block.getTop();
            for(int bottom = block.getBottom(); j <= bottom; j++)
            {
                IRow row = kdtTable.getRow(j);
                value = row.getCell(keyField).getValue();
                if(value != null && value.toString().trim().length() != 0)
                {
                    String retStr = value.toString();
                    retList.add(retStr);
                }
            }

        }
        retList.trimToSize();
        if(retList.size() == 0)
        {
            return null;
        } else
        {
            String retStrs[] = new String[retList.size()];
            retStrs = (String[])retList.toArray(retStrs);
            return retStrs;
        }
    }

    public static boolean isSelectRow(KDTable kdtTable)
    {
        KDTSelectManager sm = kdtTable.getSelectManager();
        return sm.size() > 0;
    }
	
    /**
     * 冻结表格的前几列
     * 
     * @param table 表格
     * @param count 冻结列数
     * @see com.kingdee.eas.fi.fa.basedata.FaUtils#freezeTable(KDTable table, int count)
     */
    public static void freezeTable(KDTable table, int count) {
    	
        if (table == null) {
            return;
        }
        KDTViewManager viewmgr = table.getViewManager();
        if (viewmgr != null && !viewmgr.isFreeze()) {
            viewmgr.setFreezeView(0, count);
            table.reLayoutAndPaint();
        }
    }
    
    
    /**
     * 删除给定表中选中的行 如果没有选中行，删除最后一行。
     * 
     * @param kDTable 表格
     * @see com.kingdee.eas.fi.fa.manage.client.FaClientUtils#tableDelLine(KDTable kDTable)
     */
    public static void tableDelLine(KDTable kDTable) {
        if (kDTable.getSelectManager().get() == null) {
            return;
        }

        int ibeginRow = kDTable.getSelectManager().get().getBeginRow();
        int iEndrow = kDTable.getSelectManager().get().getEndRow();
        int ilastRow = kDTable.getRowCount();

        if ((ibeginRow != -1) && (iEndrow != -1)) {
            for (int i = ibeginRow; i < (iEndrow + 1); i++) {
                IRow row = kDTable.getRow(i);

                if (row == null) {
                    continue;
                }
                row = kDTable.removeRow(i);
				iEndrow--;
				i--;
            }
        } else { // 没有选中行时删除最后一行，看要不要？
            if (ilastRow > 0) {
                kDTable.removeRow(ilastRow - 1);
            }
        }
    }

    /**
     * 在表最未行加一行 返回所加的行
     * 
     * @param kDTable 表格
     * @see com.kingdee.eas.fi.fa.manage.client.FaClientUtils#tableDelLine(KDTable kDTable)
     */
    public static IRow tableAddLastLine(KDTable kDTable) {
        IRow row = null;
        int iRow = kDTable.getRowCount();

        if (iRow != -1) {
            row = kDTable.addRow(iRow);
        }
        return row;
    }
    
    /**
     * 在表中增加一列
     * 
     * @param kDTable	表格
     * @param keyName	列键名
     * @param celName	列名
     * @param index	索引
     * @param width	列宽
     */
    public static void tableAddColumn(KDTable table, String keyValue, String celName, int index, int width) {
    	//	列指定插入位置
    	table.addColumn(index);
    	//	表头指定插入位置
    	IRow headRow = table.getHeadRow(0);
    	//	获取表头行对象
    	headRow.getCell(index).setValue(celName);
    	headRow.setCell(index, headRow.getCell(index));
    	
    	//	获取列对象
    	IColumn col = table.getColumn(index);
    	col.setKey(keyValue);
    	col.setWidth(width);
    }
    
    
    /**
     * 在表中的一个列中为复选框设置显示状态
     * @param table 表格
     * @param cellName 单元格名
     * @param flag 选择状态 true 选择 fales 没选择
     */
    public static void checkRows(KDTable table, String cellName, boolean flag) {
    	
        Boolean check = Boolean.valueOf(flag);
        int iCount = table.getRowCount();
        for (int i = 0; i < iCount; i++) {
            IRow row = table.getRow(i);
            if(!"locked".equals(row.getUserObject()))
                row.getCell(cellName).setValue(check);
        }
    }
    
    
	/**
	 * 给表中的复选框设置显示状态的事件处理方法
	 * @param kdtable 表格
	 * @param cellName 列名
	 * @param e 事件对象
	 */
	public static void tableCheckChanged(KDTable kdtable, String cellName, KDTEditEvent e) {
    	
        if(!cellName.equals(kdtable.getColumnKey(e.getColIndex())))
            return;
        kdtable.getRow(e.getRowIndex()).getCell(e.getColIndex()).setValue(e.getValue());
    }
    
	
    /**
     * 格式化日期，将日期格式化为“YYYY-MM-DD”
     * 
     * @param table 表格
     * @param columnKey 列键名
     * @see com.kingdee.eas.fi.fa.manage.client.FaClientUtils#fmtDate(KDTable table, String columnKey)
     */
    public static void fmtDate(KDTable table, String columnKey) {
	    if(table.getColumn(columnKey)!=null)	
	    	table.getColumn(columnKey).getStyleAttributes().setNumberFormat("yyyy-MM-dd");
    }

    
    /**
     * 格式化日期，将日期格式化为“YYYY-MM-DD”
     * 
     * @param table 表格
     * @param columnKeys 列键名
     * @see com.kingdee.eas.fi.fa.manage.client.FaClientUtils#fmtDate(KDTable table, String[] columnKey)
     */
    public static void fmtDate(KDTable table, String[] columnKeys) {
        for (int i = 0; i < columnKeys.length; i++) {
            fmtDate(table, columnKeys[i]);
        }
    }
    
    /**
     * 给指定table设定金额格式，并且右对齐
     * 
     * @param table 表格
     * @param columnName 列名
     */
    public static void changeTableNumberFormat(KDTable table, String columnName) {
    	if(table.getColumn(columnName)!=null){
    		table.getColumn(columnName).getStyleAttributes().setNumberFormat(strDataFormat);
            table.getColumn(columnName).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT); // 右对齐
    	}
    }

    /**
     * 给指定table设定金额格式，并且右对齐
     * 
     * @param table 表格
     * @param columnIndex 列索引
     * @param format 格式化
     */
    public static void changeTableNumberFormat(KDTable table, int columnIndex, String format) {
    	if(table.getColumn(columnIndex)!=null){
	        table.getColumn(columnIndex).getStyleAttributes().setNumberFormat(format);
	        table.getColumn(columnIndex).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT); // 右对齐
    	}
    }

    /**
     * 给指定table设定金额格式，并且右对齐
     * 
     * @param table 表格
     * @param columnNames 列名数组
     */
    public static void changeTableNumberFormat(KDTable table, String[] columnNames) {
        for (int i = 0; i < columnNames.length; i++)
            changeTableNumberFormat(table, columnNames[i]);
    }

    /**
     * 给指定table设定金额格式，并且右对齐
     * 
     * @param table 表格
     * @param columnNames 列名数组
     * @param format 格式化
     */
    public static void changeTableNumberFormat(KDTable table, String[] columnNames, String format) {
        for (int i = 0; i < columnNames.length; i++)
            changeTableNumberFormat(table, columnNames[i], format);
    }
    
    /**
     * 给指定table设定金额格式，并且右对齐
     * 
     * @param table 表格
     * @param columnNames 列名数组
     * @param format 格式化
     */
    public static void changeTableNumberFormat(KDTable table, String columnName, String format) {
        table.getColumn(columnName).getStyleAttributes().setNumberFormat(format);
        table.getColumn(columnName).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT); // 右对齐
    }
    
    
    /**
     * 给指定table设定下拉框的（枚举）
     * 
     * @param table 表格
     * @param classEnumName 枚举的类名
     * @param columnName 表格列名
     */
    public static void setTableComboBox(KDTable table, String classEnumName,String columnName) {
    	
        KDComboBox tableComboBox = new KDComboBox();
        tableComboBox.setName(table.getName()+"_"+columnName+"_ComboBox");
        tableComboBox.setVisible(true);
        List l = EnumUtils.getEnumList(classEnumName);
        tableComboBox.addItem("");//增加为空的选择
        tableComboBox.addItems(l.toArray());
        KDTDefaultCellEditor tableColumnCellEditor = new KDTDefaultCellEditor(tableComboBox);
        table.getColumn(columnName).setEditor(tableColumnCellEditor);
    }
    /**
     * 给指定table数字列求和
     * 
     * @param table 表格
     * @param columnName 表格列名
     */
    public static BigDecimal getColumnValueSum(KDTable table,String columnName) {
    	BigDecimal sum = new BigDecimal(0);
        for(int i=0;i<table.getRowCount();i++){
        	if(table.getRow(i).getCell(columnName).getValue()!=null 
        			&& table.getRow(i).getCell(columnName).getValue() instanceof BigDecimal)
        		sum = sum.add((BigDecimal)table.getRow(i).getCell(columnName).getValue());
        }
        return sum;
    }
    /**
	 * 隐藏table上的增删插按钮
	 * @param table
	 */
	public static void hidePanelButtons(KDTable table){
		Component c = table.getParent().getParent();  
		if (c instanceof DetailPanel) {  
			 DetailPanel panel = (DetailPanel) c;
			 Component[] components = panel.getComponents();  
			 for(int i = 0; i < components.length; i++){  
				 Component component = components[i];  
				 if (component instanceof KDPanel) {  
					 KDPanel kdPanel = (KDPanel) component;  
					 if("controlPanel".equals(kdPanel.getName())){  
						 panel.remove(kdPanel);  
					 }  
					 if("entryPanel".equals(kdPanel.getName())){  
						 Rectangle entryRect = new Rectangle(0, 0,table.getWidth(),table.getHeight()+ 29);  
						 kdPanel.setBounds(entryRect);  
						 kdPanel.putClientProperty("OriginalBounds",entryRect);  
						 panel.add(kdPanel, new KDLayout.Constraints(KDLayout.Constraints.ANCHOR_TOP  
								 | KDLayout.Constraints.ANCHOR_RIGHT | KDLayout.Constraints.ANCHOR_LEFT  
								 | KDLayout.Constraints.ANCHOR_BOTTOM , entryRect));  
					 }  
				 }  
			 }  	
		}   
	} 
	/**
	 * 以list方式填充table
	 */
	protected void fillTableWithList(KDTable table,List records) {
		for (int i = records.size(), j = 0; j < i; j++) {
			Object record = records.get(j);
			if (record instanceof List) {
				List list = (List) record;
				IRow row = table.addRow();
				for (int k = 0, x = list.size(); k < x; k++) {
					row.getCell(k).setValue(list.get(k));
				}
			}
		}
	}
	/**
	 * 执行有效的ksql，返回结果集（list）
	 * 
	 * @param sql
	 * @return
	 * @throws BOSException
	 * @throws SQLException
	 */
	public static List execQuery(String sql) throws BOSException, SQLException {
		ArrayList result = new ArrayList();
		IRowSet rowSet = SQLExecutorFactory.getRemoteInstance(sql).executeSQL();
		ResultSetMetaData metaData = rowSet.getMetaData();
		int colCount = metaData.getColumnCount();
		while (rowSet.next()) {
			HashMap resMap = new HashMap();
			for (int i = 1; i <= colCount; i++) {
				String colName = metaData.getColumnName(i);
				resMap.put(colName, rowSet.getObject(i));
			}
			result.add(resMap);
		}
		return result;
	}
	/**
	 * 设置Table的是否隐藏列
	 * */
   public static void setHided(KDTable table,String[] strings,Boolean boll){
	   for(int i=0;i<strings.length;i++){
		   String colName = (String) strings[i];
		   table.getColumn(colName).getStyleAttributes().setHided(boll);
	   }
   }
   /**
	 * 设置Table的是否不可编辑
	 * */
  public static void setLocked(KDTable table,String[] strings,Boolean boll){
	   for(int i=0;i<strings.length;i++){
		   String colName = (String) strings[i];
		   table.getColumn(colName).getStyleAttributes().setLocked(boll);
	   }
  }
  private static String strDataFormat = "#,##0.00;-#,##0.00";
}
