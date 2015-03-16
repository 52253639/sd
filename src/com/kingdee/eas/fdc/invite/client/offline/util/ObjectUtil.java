package com.kingdee.eas.fdc.invite.client.offline.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.common.util.StringUtil;
import com.kingdee.bos.ctrl.excel.io.kds.KDSBookToBook;
import com.kingdee.bos.ctrl.excel.model.struct.Sheet;
import com.kingdee.bos.ctrl.kdf.kds.KDSBook;
import com.kingdee.bos.ctrl.kdf.read.POIXlsReader;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.KDTableHelper;
import com.kingdee.eas.fdc.invite.client.offline.Constants;
import com.kingdee.eas.fdc.invite.client.offline.ui.MainFrame;

public class ObjectUtil {

	private static Logger logger = Logger.getLogger(ObjectUtil.class);

	public static boolean isNullArray(Object[] obj) {
		return obj == null || obj.length < 1;
	}

	public static boolean isNullString(Object str) {
		return !(str instanceof String && ((String) str).length() > 0);
	}

	public static boolean isNullString(String str) {
		return str == null || str.length() < 1;
	}

	public static void setEnabled(Object[] objs, boolean b) {
		for (int i = 0; i < objs.length; i++) {
			Object obj = objs[i];
			if (obj instanceof JButton) {
				JButton button = (JButton) obj;
				if (button.isEnabled() != b)
					button.setEnabled(b);
			} else if (obj instanceof JMenuItem) {
				JMenuItem menuItem = (JMenuItem) obj;
				if (menuItem.isEnabled() != b)
					menuItem.setEnabled(b);
			}
		}
	}

	public static int[] StringArray2IntArray(String[] str) {
		if (str == null)
			return null;
		int x[] = new int[str.length];
		for (int i = 0; i < x.length; i++) {
			x[i] = Integer.parseInt(str[i]);
		}
		return x;
	}

	public static boolean contains(int[] array, int elem) {
		if (array == null)
			return false;
		for (int i = 0; i < array.length; i++) {
			if (elem == array[i])
				return true;
		}
		return false;
	}

	public static int convertToInt(Object obj, int defaultValue) {
		if (obj != null && obj.toString().trim().length() > 0)
			if (obj instanceof String || obj instanceof Double || obj instanceof Long || obj instanceof Float || obj instanceof Integer) {
				try {
					return Integer.parseInt(obj.toString());
				} catch (NumberFormatException nfe) {
					logger.error(nfe);
				}
			}
		return defaultValue;
	}

	public static BigDecimal convertToBigDecimal(Object obj, BigDecimal defaultValue) {
		if (obj instanceof BigDecimal)
			return (BigDecimal) obj;
		if (obj != null && obj.toString().trim().length() > 0)
			if (obj instanceof String || obj instanceof Double || obj instanceof Long || obj instanceof Float || obj instanceof Integer) {
				try {
					return new BigDecimal(obj.toString());
				} catch (NumberFormatException e) {
					logger.error(e);
				}
			}
		return defaultValue;
	}

	public static boolean isZero(Object obj) {
		return convertToBigDecimal(obj, new BigDecimal("0")).floatValue() == 0;
	}
	/***************************************************************************
	 * 判断Excel格式正确否
	 * 
	 * @param coreui
	 *            UI对象，直接传this
	 * @param fileName
	 *            文件绝对路径
	 * @return 正确返回KDSBook,否则返回NULL
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static KDSBook excelReaderParse(MainFrame coreui, String fileName) {
		try {
			return POIXlsReader.parse2(fileName);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(coreui,"Excel文件格式不正确！","导入Excel",JOptionPane.ERROR_MESSAGE);
			logger.error("POIXls cannot parse the excel:" + fileName, e);			
			return null;
		}
	}
	public static List validateImportExcelHeadRow(Map tableMap,KDSBook kdsbook){
		List importErrorInfoList = new ArrayList();
		boolean has=false;//是否有合适的
		for(int i=0;i<kdsbook.getSheetCount();i++){
			Sheet excelSheet = KDSBookToBook.traslate(kdsbook).getSheet(i);
			if(excelSheet.getSheetName().equals(Constants.Total_Table)||
					excelSheet.getSheetName().equals(Constants.Desc_Table))
				continue;
			if(tableMap.containsKey(excelSheet.getSheetName())){
				has = true;
				KDTable table = (KDTable)tableMap.get(excelSheet.getSheetName());				
				List errList = ObjectUtil.validateImportExcelHeadRow(table,excelSheet);					
				if(errList.size()>0){					
					importErrorInfoList.addAll(errList);
				}
			}
		}
		if(!has)
		{	
			HashMap em = new HashMap();
			em.put("errInfo","没有匹配到可导入的页签！");
			importErrorInfoList.add(em);
		}
		return importErrorInfoList;
		
	}
	
	/***************************************************************************
	 * 判断表头是否一致，将不一致的信息写入list，并返回
	 * 
	 * @param table
	 * @param excelSheet
	 * @return
	 * @throws UIException
	 */
	public static List validateImportExcelHeadRow(KDTable table,
			Sheet excelSheet) {		
		List importErrorInfoList = new ArrayList();
		int headRowCount = table.getHeadRowCount();
		for (int col = 0; col < table.getColumnCount(); col++) {
			if (headRowCount==1){
				//只有一行表头
				String columnName = table.getHeadRow(0).getCell(col)
				.getValue().toString();
				String excelColName = excelSheet.getCell(0, col, true).getText();
				if (!columnName.equals(excelColName)) {
					HashMap errMap = new HashMap();
					errMap.put("seq", (1 + 1) + "");
					errMap.put("errInfo", ""+excelSheet.getSheetName()+"中表头结构不一致(EAS表头[列数:" + (col + 1)
							+ ";名称:" + columnName + "],Excel表头[名称:"
							+ excelColName + "])!");
					importErrorInfoList.add(errMap);
				}
			}
			else if(headRowCount==2){
				if (table.getHeadRow(1).getCell(col).getValue() != null) {
					String columnName1 = table.getHeadRow(0).getCell(col)
							.getValue().toString();
					String columnName2 = table.getHeadRow(1).getCell(col)
							.getValue().toString();
					String excelColName = excelSheet.getCell(1, col, true)
							.getText();
					if (StringUtil.isEmptyString(excelColName)) {
						excelColName = excelSheet.getCell(0, col, true).getText();
					}
					if (!columnName1.equals(excelColName)
							&& !columnName2.equals(excelColName)) {
						HashMap errMap = new HashMap();
						errMap.put("seq", (1 + 1) + "");
						errMap.put("errInfo", ""+excelSheet.getSheetName()+"中表头结构不一致(EAS表头[列数:" + (col + 1)
								+ ";名称:" + columnName1 + "],Excel表头[名称:"
								+ excelColName + "])!");
						importErrorInfoList.add(errMap);
					}
				}
			}
			
		}
		if (excelSheet.getMaxColIndex() != table.getColumnCount()-1) {
			HashMap errMap = new HashMap();
			errMap.put("seq", (1 + 1) + "");
			errMap.put("errInfo", ""+excelSheet.getSheetName()+"中表头结构不一致,Excel表头中有多余的列");
			importErrorInfoList.add(errMap);
		}
		return importErrorInfoList;
	}
	/**
	 * 设置每行KDTabbedPane中的每个table自动行高
	 *
	 */
	public static void setAotuHeight(JTabbedPane kDTabbedPane1){
		for(int tableIndex=0;tableIndex<kDTabbedPane1.getTabCount();tableIndex++){
			Object obj = (Object)kDTabbedPane1.getComponentAt(tableIndex);
			if(obj != null && obj instanceof KDTable){
				KDTable table = (KDTable)obj;
				for(int rowIndex=0;rowIndex<table.getRowCount();rowIndex++){
					KDTableHelper.autoFitRowHeight(table,rowIndex,5);
					
				}
			}
			
		}
	}
}
