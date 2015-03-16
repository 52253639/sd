package com.kingdee.eas.fdc.invite.client.offline.ui;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.prefs.Preferences;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import javax.swing.ActionMap;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.common.variant.SyntaxErrorException;
import com.kingdee.bos.ctrl.common.variant.Variant;
import com.kingdee.bos.ctrl.excel.io.kds.KDSBookToBook;
import com.kingdee.bos.ctrl.excel.model.struct.Row;
import com.kingdee.bos.ctrl.excel.model.struct.Sheet;
import com.kingdee.bos.ctrl.kdf.kds.KDSBook;
import com.kingdee.bos.ctrl.kdf.kds.KDSSheet;
import com.kingdee.bos.ctrl.kdf.table.CellTreeNode;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTAction;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTResizeEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTResizeListener;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.eas.fdc.invite.client.offline.Constants;
import com.kingdee.eas.fdc.invite.client.offline.util.ExportToExcel;
import com.kingdee.eas.fdc.invite.client.offline.util.ObjectUtil;
import com.kingdee.eas.fdc.invite.client.offline.util.PrintHelper;

/**
 * 客户端界面
 *
 * @author jianxing_zhou 2007-9-3
 */
public class MainFrame extends AbstractMainFrame {

	private static final boolean fAlter4Save = false;//保存要提示

	private static Logger logger = Logger.getLogger(MainFrame.class);

	private static final long serialVersionUID = 15994768302L;

	private static final char separator = '`';

	// ////////////////////////////

	public static String currentFile = null;

	private String title = "离线报价客户端";

	// private String promptMessage = "确定要进行此操作吗？";

	// ///////////////////////////

	private List fileComment = new ArrayList(5);

	private List tables = new ArrayList(8);

	private Map tableMap = new HashMap();

	// 各表汇总金额列
	private String totalColumn = "";

	// 各表汇总金额列
	private Map totalColumnMap = new HashMap(10);

	// 不需要打印的列
	private String withoutPrintColumn = "";

	public static Map withoutPrintMap = new HashMap(5);

	// 金额类型报价列
	private Map priceColumn = new HashMap(8);

	// 配置文件
	Properties properties = null;

	// 综合单价列或综合单价小计列
	public static Map totalPriceMap = new HashMap(14);

	private transient JFileChooser fileChooser = null;
	private ErrorListDialog dialog = null;

	//20101019 zhougang
	Map columnObjMap = new HashMap();
	private transient ICellEditor amtEditor = null;
	private static final Color LOCKCOLOR = new Color(0xF0EDD9);
	
	private Map userDefindedTableMap = new HashMap();
	
	public MainFrame() {
		super();
	}

	public MainFrame(String title) {
		super(title);
		upLineButton.setVisible(false);
		upLineMenuItem.setVisible(false);
		downLineButton.setVisible(false);
		downLineMenuItem.setVisible(false);
		operationMenu.setVisible(false);
		this.title = title;
	}

	/**
	 * 打开和保存文件对话框
	 *
	 * @param title
	 * @param mode
	 * @return
	 */
	private File fileDialog(String title, int mode,String fileExt,String fileExtDesc) {
		fileChooser = new FdcFileChooser(currentFile,fileExt,fileExtDesc);
		fileChooser.setDialogTitle(title);
		fileChooser.setDialogType(mode);

		if (fileChooser.showDialog(this, null) == JFileChooser.APPROVE_OPTION) {
			return fileChooser.getSelectedFile();
		}
		return null;
	}

	protected boolean beforeImport() {
		if (!isSaved) {
			if (JOptionPane.OK_OPTION != JOptionPane.showConfirmDialog(this, "当前报表未保存，确定导入新报表吗？", "导入前提示", JOptionPane.OK_CANCEL_OPTION)) {
				return false;
			}
		}
		File file = fileDialog("导入", JFileChooser.OPEN_DIALOG,FdcFileChooser.FDCFileExt,FdcFileChooser.FDCFileExtDesc);
		if (file != null) {
			context.put("zipFile", file);
			return true;
		}
		return false;
	}

	protected boolean importFile() {
		logger.info("正在importFile......");
		userDefindedTableMap.clear(); // 每次导入前先清空一下，否则第二次导入会受第一次影响
		File file = (File) context.get("zipFile");
		if (file == null && !file.exists()) {
			JOptionPane.showConfirmDialog(this, "找不到文件 " + file.getAbsolutePath(), "错误提示", JOptionPane.DEFAULT_OPTION);
			return false;
		}
		properties = null;
		currentFile = file.getAbsolutePath();
		tabbedPane.removeAll();
		tables.clear();
		tableMap.clear();
		fileComment.clear();
		priceColumn.clear();
		totalPriceMap.clear();
		try {
			ZipFile zipFile = new ZipFile(file);
			ZipEntry zipEntry = null;
			// 从第二个页签起为需要填写报价的KDTable
			int tableIndex = 2;
			Enumeration enums = zipFile.entries();
			for (int i = 0; enums.hasMoreElements(); i++) {
				zipEntry = (ZipEntry) enums.nextElement();
				if (zipEntry.getName().endsWith("kd0.fdc")) {// 招标说明
					String comment = zipEntry.getComment();
					if (comment.indexOf(separator) < 0) {
						JOptionPane.showConfirmDialog(this, "对不起，无法完成导入！可能文件已损坏", "错误提示", JOptionPane.DEFAULT_OPTION);
						return false;
					}
					// 提示导入状态
					// statusBar.setLeftInfo("正在导入报价文件");
					totalColumn = comment.substring(comment.indexOf(separator) + 1, comment.lastIndexOf(separator));
					withoutPrintColumn = comment.substring(comment.lastIndexOf(separator) + 1);
					fileComment.add(0, comment);
					KDTable table = new KDTable();
					table.getIOManager().load(zipFile.getInputStream(zipEntry));
					tabbedPane.add(table);
					tableMap.put(table.getName(),table);
				} else if (zipEntry.getName().endsWith("kd1.fdc")) {// 报价汇总
					String comment = zipEntry.getComment();					
					fileComment.add(1, comment);// 是否供应商自定义页签的属性
					String[] userDefinded = comment.split(String.valueOf(separator));
					for(int index=0;index<userDefinded.length;index++){
						if(userDefinded[index].length()>0)
							userDefindedTableMap.put(userDefinded[index], Boolean.TRUE);
					}
					
					KDTable table = new KDTable();
					table.getIOManager().load(zipFile.getInputStream(zipEntry));
					tabbedPane.add(table);
				} else if (zipEntry.getName().endsWith(".kdf")) {
					KDTable table = new KDTable();
					table.getIOManager().load(zipFile.getInputStream(zipEntry));
					tables.add(table);
					tableMap.put(table.getName(),table);

					// 综合单价列
					String id = table.getID();
					totalPriceMap.put(table.getName(), id.substring(id.lastIndexOf(separator) + 1));

					String comment = zipEntry.getComment();
					fileComment.add(tableIndex, comment);
					tableIndex++;
					setTreeLevel(table);
					// 金额报价列
					int[] cols = ObjectUtil.StringArray2IntArray(comment.substring(0, comment.indexOf(separator)).split(";"));
					priceColumn.put(table.getName(), cols);
					IColumn c = null;
					c = table.getColumn("子目名称");
					if (c != null)
						c.getStyleAttributes().setWrapText(true);
					c = table.getColumn("备注");
					if (c != null)
						c.getStyleAttributes().setWrapText(true);
					tabbedPane.add(table);
					table.addKDTResizeListener(new KDTResizeListener(){

						public void tableRowResize(KDTResizeEvent e) {
							// TODO 自动生成方法存根
							
						}

						public void tableColumnResize(KDTResizeEvent e) {
							// TODO 自动生成方法存根
							setSaveAction();
						}
						
					});

					withoutPrintMap.put(table.getName(), withoutPrintColumn.split("!")[i - 2]);

					if (comment != null && comment.length() > 1) {
						//
						int separatorIndex = comment.indexOf(separator);
						if (separatorIndex > 0)
						{
							String[] comments = comment.split("`");
							//向旧版本兼容,旧版的EAS导出的comment中不包含日期列
							if(comments.length==4){
								//金额格式
								formateTable(table, comments[0]);
								//字符串
								formateTable(table, comments[1]);
								//日期
								formateTable(table, comments[2]);
							}
							else{
								formateTable(table, comment.substring(0, separatorIndex));
							}
						}
						else
							formateTable(table, comment);
						if (comment.lastIndexOf(separator) > 0)
							computeTable(table, comment.substring(comment.lastIndexOf(separator) + 1));
					}
				}
			}
			// 各页签金额汇总列
			String str[] = totalColumn.split(";");
			for (int i = 0; i < str.length; i++) {
				totalColumnMap.put(((KDTable) tables.get(i)).getName(), str[i]);
			}
		} catch (IOException e) {
			logger.error(e);
			return false;
		}
		return true;
	}
	
	public void setSaveAction(){
		this.saveButton.setEnabled(true);
		this.saveMenuItem.setEnabled(true);
	}

	protected void afterImport() {
		isSaved = true;
		ObjectUtil.setEnabled(new Object[] { exportMenuItem,
				exportExcelMenuItem, importExcelMenuItem, printMenuItem,
				prePrintMenuItem, saveMenuItem, exportButton, exportExcelButton,
				importExcelButton, printButton, prePrintButton, saveButton }, true);
		logger.info("成功导入报价文件:" + currentFile);
		setTitle(title + "  " + currentFile);
		statusBar.setLeftInfo("报价文件:" + currentFile);
		for (int i = 0, count = tabbedPane.getTabCount(); i < count; i++) {
			new TablePopuMenu(this, (KDTable) tabbedPane.getComponentAt(i));
		}

		boolean xyz = false;
		for (int i = 0; i < fileItems.length; i++) {
			if (currentFile.equalsIgnoreCase(fileItems[i])) {
				xyz = true;
				break;
			}
		}
		if (!xyz) {
			Preferences p = Preferences.userRoot();
			boolean b = false;
			for (int i = 0; i < fileItems.length; i++) {
				if (fileItems[i] == null) {
					fileItems[i] = currentFile;
					b = true;
					break;
				}
			}
			if (!b)
				fileItems[0] = currentFile;
			for (int i = 0; i < fileItems.length; i++) {
				if (fileItems[i] == null)
					break;
				p.put("fileItem" + i, fileItems[i]);
			}
		}
		// 冻结列相关
		loadConfigFile();
		
		//设置自动行高
		ObjectUtil.setAotuHeight(this.tabbedPane);
		map.clear();
	}

	// 加载冻结列的设置
	private void loadConfigFile() {

		int seperatorIndex = MainFrame.currentFile.lastIndexOf('\\');
		if (seperatorIndex < 1)
			seperatorIndex = MainFrame.currentFile.lastIndexOf('/');
		String dir = MainFrame.currentFile.substring(0, seperatorIndex);
		String filename = MainFrame.currentFile.substring(seperatorIndex + 1);
		File file = new File(dir, filename + ".config");
		if (file.exists()) {
			properties = new Properties();
			FileInputStream fis = null;
			try {
				fis = new FileInputStream(file);
				properties.load(fis);
			} catch (FileNotFoundException e) {
				logger.error(e);
			} catch (IOException e) {
				logger.error(e);
			} finally {
				try {
					if (fis != null)
						fis.close();
				} catch (IOException e) {
					logger.error(e);
				}
			}
		}
	
		//20101024 zhougang
		//自定义类型的页签中没有子目名称不允许导出。
		for(int i = 0; i < tabbedPane.getTabCount(); i++){
			KDTable table = (KDTable) tabbedPane.getComponentAt(i);
			if(i > 1){
				int projectAmtIndex = 0;
				int projectAmtSum = 0;
				int totalPriceIndex = 0;
				int totalPriceSum = 0;
				int amountIndex = 0;
				int amountSum = 0;
				
				if("工程量".equals(table.getColumn(5).getKey())){
					projectAmtIndex = 5;
					projectAmtSum = 5;
				}else{
					projectAmtIndex = 5;
					for(int j = projectAmtIndex; j < table.getColumnCount(); j++){
						if("小计".equals(table.getColumn(j).getKey())){
							projectAmtSum = j;
							table.getColumn(j).getStyleAttributes().setLocked(true);
							break;
						}
					}
				}
				
				if(userDefindedTableMap.containsKey(String.valueOf(i))){
					if("综合单价".equals(table.getColumn(projectAmtSum + 1).getKey())){
						totalPriceIndex = projectAmtSum + 1;
						totalPriceSum = projectAmtSum + 1;
					}else{
						totalPriceIndex = projectAmtSum + 1;
						for(int j = totalPriceIndex; j < table.getColumnCount(); j++){
							if("小计".equals(table.getColumn(j).getKey())){
								totalPriceSum = j;
								table.getColumn(j).getStyleAttributes().setLocked(true);
								break;
							}
						}
					}
				}else{
					if("综合单价".equals(table.getColumn(projectAmtSum + 1).getKey())){
						totalPriceIndex = projectAmtSum + 1;
						totalPriceSum = projectAmtSum + 1;
					}else{
						totalPriceIndex = projectAmtSum + 1;
						for(int j = totalPriceIndex; j < table.getColumnCount(); j++){
							if("小计".equals(table.getColumn(j).getKey())){
								totalPriceSum = j;
								break;
							}
						}
					}
				}
				
				if("金额".equals(table.getColumn(totalPriceSum + 1).getKey())){
					amountIndex = totalPriceSum + 1;
					amountSum = totalPriceSum + 1;
					table.getColumn(amountIndex).getStyleAttributes().setLocked(true);
				}else{
					amountIndex = totalPriceSum + 1;
					for(int j = amountIndex; j < table.getColumnCount(); j++){
						table.getColumn(j).getStyleAttributes().setLocked(true);
						if("小计".equals(table.getColumn(j).getKey())){
							amountSum = j;
							table.getColumn(j).getStyleAttributes().setLocked(true);
							break;
						}
					}
				}
				
				if(userDefindedTableMap.containsKey(String.valueOf(i))){
					KDFormattedTextField txtAmtFld = new KDFormattedTextField(KDFormattedTextField.BIGDECIMAL_TYPE);
					KDFormattedTextField txtProjectAmtFld = new KDFormattedTextField(KDFormattedTextField.BIGDECIMAL_TYPE);
					txtAmtFld.setPrecision(2);	
					txtAmtFld.setRemoveingZeroInDispaly(false);
					txtAmtFld.setRemoveingZeroInEdit(false);
					txtAmtFld.setNegatived(false);
					txtAmtFld.setMaximumValue(Constants.MAX_VALUE);
					txtAmtFld.setMinimumValue(Constants.MIN_VALUE);
					txtAmtFld.setHorizontalAlignment(SwingConstants.RIGHT);
					txtAmtFld.setSupportedEmpty(true);
					txtProjectAmtFld.setPrecision(2);	
					txtProjectAmtFld.setRemoveingZeroInDispaly(false);
					txtProjectAmtFld.setRemoveingZeroInEdit(false);
					txtProjectAmtFld.setNegatived(false);
					txtProjectAmtFld.setMaximumValue(Constants.PROJECTAMT_MAX_VALUE);
					txtProjectAmtFld.setMinimumValue(Constants.MIN_VALUE);
					txtProjectAmtFld.setHorizontalAlignment(SwingConstants.RIGHT);
					txtProjectAmtFld.setSupportedEmpty(true);
					
					initTextColumn(table, 3, 250);
					initTextColumn(table, 4, 60);
					initTextColumn(table, amountSum + 1, 80);
					
					amtEditor = new KDTDefaultCellEditor(txtProjectAmtFld);
					if(projectAmtIndex == projectAmtSum){
						table.getColumn(projectAmtIndex).setEditor(amtEditor);
						table.getColumn(projectAmtIndex).getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
						table.getColumn(projectAmtIndex).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
					}else{
						for(int k = projectAmtIndex; k < projectAmtSum ; k ++){
							table.getColumn(k).setEditor(amtEditor);
							table.getColumn(k).getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
							table.getColumn(k).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
						}
					}

					amtEditor = new KDTDefaultCellEditor(txtAmtFld);
					if(totalPriceIndex == amountSum){
						table.getColumn(totalPriceIndex).setEditor(amtEditor);
						table.getColumn(totalPriceIndex).getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
						table.getColumn(totalPriceIndex).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
					}else{
						for(int k = totalPriceIndex; k <= totalPriceSum ; k ++){
							table.getColumn(k).setEditor(amtEditor);
							table.getColumn(k).getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
							table.getColumn(k).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
						}
					}
					
					for(int k = amountIndex; k < amountSum ; k ++){
						table.getColumn(k).getStyleAttributes().setLocked(true);					
					}
						
				}else{
					KDFormattedTextField txtAmtFld = new KDFormattedTextField(KDFormattedTextField.BIGDECIMAL_TYPE);
					txtAmtFld.setPrecision(2);	
					txtAmtFld.setRemoveingZeroInDispaly(false);
					txtAmtFld.setRemoveingZeroInEdit(false);
					txtAmtFld.setNegatived(false);
					txtAmtFld.setMaximumValue(Constants.MAX_VALUE);
					txtAmtFld.setMinimumValue(Constants.MIN_VALUE);
					txtAmtFld.setHorizontalAlignment(SwingConstants.RIGHT);
					txtAmtFld.setSupportedEmpty(true);

					amtEditor = new KDTDefaultCellEditor(txtAmtFld);
					for(int k = totalPriceIndex; k < totalPriceSum ; k ++){
						table.getColumn(k).setEditor(amtEditor);
						table.getColumn(k).getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
						table.getColumn(k).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
					}
				}
				
				//客户自定义列类型控制
				if(table.getColumnCount() > amountSum ){
					for(int colIndex = amountSum + 1 ; colIndex < table.getColumnCount() ; colIndex ++){
						this.formateTable(table, String.valueOf(colIndex));
					}
				}
				
//				//父节点行
////				ArrayList isLeafListNo = new ArrayList();
//				for (int k = 1; k < table.getRowCount(); k++) {
//					IRow row = table.getRow(k);
////					BigDecimal rowLevel = (BigDecimal)(row.getCell(0).getValue());
//					if(!checkLeafRow(table, k)){
//						row.getCell(4).setValue(null);
//						row.getCell(5).setValue(null);
//						row.getCell(6).setValue(null);
//
////						isLeafListNo.add(String.valueOf(k));
////						
////						for (int m = k + 1; m < table.getRowCount(); m++) {
////							IRow rowNext = table.getRow(m);
////							if(checkLeafRow(table, m)){
////								BigDecimal rowLevelNext = FDCHelper.toBigDecimal(rowNext.getCell(0).getValue());
////								BigDecimal value = FDCHelper.toBigDecimal(rowNext.getCell(7).getValue());
////								if(rowLevelNext.floatValue() > rowLevel.floatValue()){
////									sum = sum.add(FDCHelper.toBigDecimal(value));
////								}
////							}
////						}
////						row.getCell(7).setValue(sum);
//					}
//				}
			}
		}
		//20101024 zhougang
	}
	
	public static boolean checkLeafRow(KDTable table, int rowIndex) {
		if (rowIndex + 1 == table.getRowCount()) {
			return true;
		}
		Integer rowLevel = (Integer)table.getRow(rowIndex).getCell(0).getValue();
		Integer nextLevel = (Integer)table.getRow(rowIndex + 1).getCell(0).getValue();
		if (nextLevel.floatValue() <= rowLevel.floatValue()) {
			return true;
		}
		return false;
	}
	
	// 初始化文本框控件，供分录使用
	private void initTextColumn(KDTable tblMain, int index, int length) {
		KDTextField txt = new KDTextField();
		txt.setMaxLength(length);
		KDTDefaultCellEditor editor = new KDTDefaultCellEditor(txt);
		tblMain.getColumn(index).setEditor(editor);
	}

	protected boolean beforeExport() {
		// String errorMessage = checkData();
		// if (errorMessage != null) {
		// if (JOptionPane.showConfirmDialog(this, errorMessage + "，确实要进行此操作吗？",
		// "数据校验",
		// JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION)
		// return false;
		// }
		
		//20101015 zhougang
		//自定义类型的页签中没有子目名称不允许导出。
		for(int i = 0; i < tabbedPane.getTabCount(); i++){
			KDTable table = (KDTable) tabbedPane.getComponentAt(i);
			if(userDefindedTableMap.containsKey(String.valueOf(i))){
				if(table.getRowCount() > 1){
					int projectAmtIndex = 0;
					int projectAmtSum = 0;
					
					if("工程量".equals(table.getColumn(5).getKey())){
						projectAmtIndex = 5;
						projectAmtSum = 5;
					}else{
						projectAmtIndex = 5;
						for(int j = projectAmtIndex; j < table.getColumnCount(); j++){
							if("小计".equals(table.getColumn(j).getKey())){
								projectAmtSum = j;
								table.getColumn(j).getStyleAttributes().setLocked(true);
								break;
							}
						}
					}
					
					for (int j = 1; j < table.getRowCount(); j++) {
						StringBuffer info = new StringBuffer("“");
						info.append(table.getName() + "”");
						if (ObjectUtil.isNullString(table.getCell(j, 3).getValue())) {
							info.append("页签中存在未录入子目名称的子目！");
							JOptionPane.showConfirmDialog(this, info.toString(), "错误提示", JOptionPane.DEFAULT_OPTION);
							return false;
						}
						
						if (table.getCell(j, 4).getValue() == null  || "".equals(table.getCell(j, 4).getValue().toString().trim())) {
							info.append("页签中存在未录入单位的子目！");
							JOptionPane.showConfirmDialog(this, info.toString(), "错误提示", JOptionPane.DEFAULT_OPTION);
							return false;
						}
						
						if (ObjectUtil.convertToBigDecimal(table.getCell(j, projectAmtSum).getValue(), Constants.zero).floatValue() <= Constants.zero.floatValue()) {
							info.append("页签中存在未录入工程量的子目！");
							JOptionPane.showConfirmDialog(this, info.toString(), "错误提示", JOptionPane.DEFAULT_OPTION);
							return false;
						}
					}
				}
			}
		}
		//20101015 zhougang

		/* 因为在客户端已经做了验证，如果是无效数据就不能导出，因此这里不需要验证了。
		 * Owen_wen 2011-10-29
		String errorMessage = checkData();
		if (errorMessage != null) {
			JOptionPane.showConfirmDialog(this, errorMessage + "，可暂时保存，但不能导出", "导出错误", JOptionPane.DEFAULT_OPTION);
			return false;
		}
		*/

		File sFile = fileDialog("导出", JFileChooser.SAVE_DIALOG,FdcFileChooser.FDCFileExt,FdcFileChooser.FDCFileExtDesc);
		if (sFile == null)
			return false;
		context.put("zipFile", sFile);
		return true;
	}

	/*
	 * 导出
	 *
	 * @see com.kingdee.eas.fdc.invite.client.offline.AbstractMainFrame#exportFile()
	 */
	protected boolean exportFile() {
		Object obj = context.get("zipFile");
		
		File zipFile = (File) obj;
		ZipOutputStream out = null;
		File files[] = null;
		try {
			out = new ZipOutputStream(new FileOutputStream(zipFile));
			files = new File[tabbedPane.getTabCount()];
			for (int i = 0, count = tabbedPane.getTabCount(); i < count; i++) {
				if (i < 2) {
					files[i] = new File("kd" + i + ".fdc");
					((KDTable) tabbedPane.getComponentAt(i)).getIOManager().save(files[i].getAbsolutePath());
				} else {
					files[i] = new File("table_" + i + ".kdf");
					((KDTable) tabbedPane.getComponentAt(i)).getIOManager().save(files[i].getAbsolutePath());
				}
			}
			for (int i = 0; i < files.length; i++) {
				ZipEntry zipEntry = new ZipEntry(files[i].getAbsolutePath());
				if (fileComment != null)
					zipEntry.setComment((String) fileComment.get(i));
				out.putNextEntry(zipEntry);
				FileInputStream input = new FileInputStream(files[i]);
				int b = -1;
				while ((b = input.read()) != -1) {
					out.write(b);
				}
				input.close();
			}
			out.close();
			currentFile = zipFile.getAbsolutePath();
		} catch (FileNotFoundException fnfe) {
			logger.error(fnfe);
			JOptionPane.showMessageDialog(this, fnfe);
			return false;
		} catch (java.util.zip.ZipException zipe) {
			logger.error(zipe);
			JOptionPane.showMessageDialog(this, zipe);
			return false;
		} catch (IOException ioe) {
			logger.error(ioe);
			JOptionPane.showMessageDialog(this, ioe);
			return false;
		} finally {
			if (files != null)
				for (int j = 0; j < files.length; j++)
					files[j].delete();
		}
		return true;
	}

	protected void afterExport() {
		isSaved = true;
		logger.info("成功保存报价文件:" + currentFile);
		ObjectUtil.setEnabled(new Object[] { saveMenuItem, saveButton }, !isSaved);
		setTitle(title + "  " + currentFile);
		statusBar.showTip("文件已保存!");
	}

	public void save() {
		int i = this.tabbedPane.getSelectedIndex();
		if(this.tabbedPane.getComponentAt(i) instanceof KDTable){
			KDTable table = (KDTable)(this.tabbedPane.getComponentAt(i));
			table.getEditManager().stopEditing();
		}
		ObjectUtil.setAotuHeight(this.tabbedPane);
		if (fAlter4Save) {
			String errorMessage = checkData();
			if (errorMessage != null) {
				if (JOptionPane.showConfirmDialog(this, errorMessage + "，确实要进行此操作吗？", "数据校验", JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION)
					return;
			}
		}
		// else if (status == TableStatus.EDITING) {
		// JOptionPane.showMessageDialog(this, context.get("errorMessage"),
		// "数据校验", JOptionPane.OK_OPTION);
		// return;
		// }
		context.put("zipFile", new File(currentFile));
		if (exportFile())
			afterExport();
	}

	/**
	 * 检查数据是否填充完整
	 *
	 * @param table
	 * @return
	 */
	private String checkData() {
		int tableIndex = tabbedPane.getSelectedIndex();
		if (tableIndex > 1) {
			KDTable table = (KDTable) tabbedPane.getComponentAt(tableIndex);
			table.getEditManager().editCellAt(0, 0);
		}
		for (int x = 2; x < tabbedPane.getTabCount(); x++) {
			KDTable table = (KDTable) tabbedPane.getComponentAt(x);
			int[] columns = (int[]) priceColumn.get(table.getName());
			int length = columns.length;
			if (columns == null || length < 1)
				return null;
		}
		if (verifyData())
			return "存在子目名称相同的行报价不同";

		return null;
	}

	/*
	 * 打印预览
	 *
	 * @see com.kingdee.eas.fdc.invite.client.offline.AbstractMainFrame#printPreview()
	 */
	public void printPreview() {
		PrintHelper.printPreview((KDTable) tabbedPane.getSelectedComponent());
	}

	/*
	 * 打印
	 *
	 * @see com.kingdee.eas.fdc.invite.client.offline.AbstractMainFrame#print()
	 */
	public void print() {
		for (int i = 0; i < 100000; i++) {
			int copies = 0;
			String str = JOptionPane.showInputDialog("请输入打印份数(详细设置请点击[打印设置])", "1");
			if (ObjectUtil.isNullString(str))
				return;
			try {
				copies = Integer.parseInt(str);
			} catch (NumberFormatException e) {
				copies = 0;
			}
			if (copies < 1)
				continue;
			PrintHelper.print(tabbedPane, copies);
			break;
		}
	}

	public void exit() {
		if (!isSaved) {
			int option = JOptionPane.showConfirmDialog(null, "数据有改动，是否保存？", "退出确认", JOptionPane.YES_NO_CANCEL_OPTION);
			if (JOptionPane.OK_OPTION == option) {
				// super._export();
				// save();
				context.put("zipFile", new File(currentFile));
				if (exportFile())
					afterExport();
				logger.info("退出程序");
				System.exit(0);
			} else if (JOptionPane.NO_OPTION == option) {
				logger.info("退出程序");
				System.exit(0);
			}
		} else {
			logger.info("退出程序");
			System.exit(0);
		}
	}

	private void setTreeLevel(KDTable table) {
		int depth = 0;
		for (int j = 0; j < table.getRowCount(); j++) {
			ICell cell = table.getCell(j, "level");
			if (cell != null) {
				int level = cell.getValue() != null ? ((Integer) cell.getValue()).intValue() : -1;
				if (level >= 0) {
					if (level > depth)
						depth = level;
					table.getRow(j).setTreeLevel(level);
				}
			}
		}
		table.getTreeColumn().setDepth(depth);
	}

	final static BigDecimal maxValue = new BigDecimal("100000000000000").divide(new BigDecimal("100"), BigDecimal.ROUND_HALF_UP);

	final static BigDecimal minValue = new BigDecimal("-100000000000000").divide(new BigDecimal("100"), BigDecimal.ROUND_HALF_UP);

	/**
	 * @return 数字格式
	 */
	private static final ICellEditor getNumberEditor() {
		KDFormattedTextField formattedTextField = new KDFormattedTextField(KDFormattedTextField.BIGDECIMAL_TYPE);
		formattedTextField.setPrecision(2);
		formattedTextField.setRemoveingZeroInDispaly(false);
		formattedTextField.setRemoveingZeroInEdit(false);
		formattedTextField.setNegatived(false);
		formattedTextField.setMaximumValue(maxValue);
		formattedTextField.setMinimumValue(minValue);
		formattedTextField.setSupportedEmpty(false);
		formattedTextField.setHorizontalAlignment(KDFormattedTextField.RIGHT);
		return new KDTDefaultCellEditor(formattedTextField);
	}

	public ICellEditor getDateCellEditor() {
		
		KDDatePicker dateEditor = new KDDatePicker();
		dateEditor.setName("dateEditor");
		dateEditor.setVisible(true);
		dateEditor.setEditable(true);
		
		KDTDefaultCellEditor date_CellEditor = new KDTDefaultCellEditor(
				dateEditor);
		date_CellEditor.setClickCountToStart(1);
		
		return date_CellEditor;
	}

	private static final ICellEditor numberEditor = getNumberEditor();

	/**
	 * 格式化Table
	 *
	 * @param table
	 * @param cols
	 */
	private void formateTable(KDTable table, String cols) {
		if (ObjectUtil.isNullString(cols))
			return;
		String[] myCols = cols.split(";");
		if (ObjectUtil.isNullArray(myCols))
			return;
		for (int i = 0; i < myCols.length; i++) {
			IColumn column = table.getColumn(Integer.parseInt(myCols[i]));
			if (/* column != null && column.getStyleAttributes() != null && */column.getStyleAttributes().isLocked()) {
				;// do nothing
			} else {
				if(column.getStyleAttributes().getNumberFormat().equals("yyyy-MM-dd")||
						column.getStyleAttributes().getNumberFormat().equals("yyyy-M-d")||
						column.getStyleAttributes().getNumberFormat().equals("yyyy-m-d"))
				{
					column.setEditor(getDateCellEditor());
				}
				else if(column.getStyleAttributes().getNumberFormat().endsWith("#,##0.00;-#,##0.00") 
						||column.getStyleAttributes().getNumberFormat().endsWith("#,##0.00;[Red]-#,##0.00; " )){
					column.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
					column.setEditor(numberEditor);
					// 0值不显示
					column.getStyleAttributes().setNumberFormat("#,##0.00;[Red]-#,##0.00; ");
				}
				else{
					// 字符串列
				}
			}
		}
	}

	/**
	 * 设置汇总计算
	 *
	 * @param table
	 * @param cols
	 */
	private void computeTable(final KDTable table, final String cols) {
		if (ObjectUtil.isNullString(cols))
			return;
		String[] strs = cols.split(";");
		if (ObjectUtil.isNullArray(strs))
			return;
		final int colsInt[] = new int[strs.length];
		for (int i = 0; i < strs.length; i++) {
			colsInt[i] = Integer.parseInt(strs[i]);
		}
		table.addKDTEditListener(new X(table, colsInt));

		ActionMap actionMap = table.getActionMap();
		actionMap.remove(KDTAction.PASTE);
		actionMap.remove(KDTAction.CUT);
		actionMap.remove(KDTAction.DELETE);

	}

	public boolean isLeafRow(KDTable table, int rowIndex) {
		if (rowIndex + 1 == table.getRowCount()) {
			return true;
		}
		int rowLevel = table.getRow(rowIndex).getTreeLevel();
		int nextLevel = table.getRow(rowIndex + 1).getTreeLevel();
		if (nextLevel <= rowLevel) {
			return true;
		}
		return false;
	}

	private int getMaxLevel(KDTable table) {
		int count = table.getRowCount();
		int levelthis;
		int levelmax = 0;
		for (int i = 1; i < count; i++) {
			levelthis = Integer.parseInt(table.getCell(i, "level").getValue().toString());
			if (levelthis > levelmax) {
				levelmax = levelthis;
			}
		}
		return levelmax;
	}
	// 设置父科目汇总
	public void setUnionData(KDTable table, int[] cols) {
		int selectComponent = tabbedPane.getSelectedIndex();
		if(userDefindedTableMap.containsKey(String.valueOf(selectComponent))){
			setTableScript(table);
		}
		
		for (int i = 1; i < table.getRowCount(); i++) {
			IRow row = table.getRow(i);
			// 设置汇总行
			int level = row.getTreeLevel();
			int maxLevel = this.getMaxLevel(table);
			if (level == maxLevel) {
				continue;
			}
			List aimRowList = new ArrayList();
			for (int j = i + 1; j < table.getRowCount(); j++) {
				IRow rowAfter = table.getRow(j);
				if (rowAfter.getTreeLevel() <= level) {
					break;
				}
				if (isLeafRow(table, j)){
					aimRowList.add(rowAfter);
				}
			}
			if (aimRowList.size() > 0) {
				for (int j = 0; j < cols.length; j++) {
					int colIndex = cols[j];
					BigDecimal sum = Constants.zero;
					for (int rowIndex = 0; rowIndex < aimRowList.size(); rowIndex++) {
						IRow rowAdd = (IRow) aimRowList.get(rowIndex);
						Object value = rowAdd.getCell(colIndex).getValue();
						if (value != null) {
							sum = sum.add(ObjectUtil.convertToBigDecimal(value, Constants.zero));
						}
					}
					row.getCell(colIndex).setValue(sum);
				}
			}
		}
		table.getScriptManager().runAll();
		setTableTotal(table);
		updateTotalPageData();
		
		//20101018 zhougang
		setTableLock(table);
//		table.getTreeColumn().setDepth(getMaxLevel(table) + 1);
//		table.revalidate();
	}
	
	private void setTableScript(KDTable table) {
		int rowCount = table.getRowCount();
		int projectAmtIndex = 0;
		int projectAmtSum = 0;
		int totalPriceIndex = 0;
		int totalPriceSum = 0;
		int amountIndex = 0;
		int amountSum = 0;
		
		if("工程量".equals(table.getColumn(5).getKey())){
			projectAmtIndex = 5;
			projectAmtSum = 5;
		}else{
			projectAmtIndex = 5;
			for(int i = projectAmtIndex; i < table.getColumnCount(); i++){
				if("小计".equals(table.getColumn(i).getKey())){
					projectAmtSum = i;
					break;
				}
			}
		}
		
		if("综合单价".equals(table.getColumn(projectAmtSum + 1).getKey())){
			totalPriceIndex = projectAmtSum + 1;
			totalPriceSum = projectAmtSum + 1;
		}else{
			totalPriceIndex = projectAmtSum + 1;
			for(int i = totalPriceIndex; i < table.getColumnCount(); i++){
				if("小计".equals(table.getColumn(i).getKey())){
					totalPriceSum = i;
					break;
				}
			}
		}
		
		if("金额".equals(table.getColumn(totalPriceSum + 1).getKey())){
			amountIndex = totalPriceSum + 1;
			amountSum = totalPriceSum + 1;
		}else{
			amountIndex = totalPriceSum + 1;
			for(int i = amountIndex; i < table.getColumnCount(); i++){
				if("小计".equals(table.getColumn(i).getKey())){
					amountSum = i;
					break;
				}
			}
		}

		for (int i = 1; i < rowCount; i++) {
			if (isLeafRow(table, i)) {
				if (projectAmtIndex < projectAmtSum) {
					setTableRowSumFormalu(table.getCell(i,
							projectAmtSum), projectAmtSum - projectAmtIndex);
				}
				if (totalPriceIndex < totalPriceSum) {
					setTableRowSumFormalu(table.getCell(i, totalPriceSum), totalPriceSum - totalPriceIndex);
				}
				for (int j = 0; j <= projectAmtSum - projectAmtIndex; j++) {
					setTableMultiplyFormalu(table.getCell(i,
							amountIndex + j), table.getCell(i, projectAmtIndex
							+ j), table.getCell(i, totalPriceSum));
				}
			}
		}
	}
	
	public static void setTableRowSumFormalu(ICell cell, int count) {
		StringBuffer expression = new StringBuffer("=SUM(");
		for (int i = 0; i < count; i++) {
			StringBuffer text = new StringBuffer("CELL(");
			text.append("ROW_INDEX");
			text.append(",");
			text.append("COLUMN_INDEX-" + (count - i));
			text.append(").getValue()");
			if (!expression.toString().equals("=SUM(")) {
				expression.append(",");
			}
			expression.append(text);
		}
		expression.append(")");
		cell.setExpressions(expression.toString());
	}
	
	public static void setTableMultiplyFormalu(ICell cell, ICell cell1, ICell cell2) {
		StringBuffer expression = new StringBuffer("=");
		expression.append("CELL(" + (cell1.getRowIndex() + 1));
		expression.append(",");
		expression.append(cell1.getColumnIndex() + 1);
		expression.append(").getValue()*");
		expression.append("CELL(" + (cell2.getRowIndex() + 1));
		expression.append(",");
		expression.append(cell2.getColumnIndex() + 1);
		expression.append(").getValue()");
		cell.setExpressions(expression.toString());
	}
	
	/**
	 *  锁定合计行
	 */
	private void setTableLock(final KDTable table) {
		table.getRow(0).getStyleAttributes().setLocked(true);
	}

	/**
	 *  汇总金额列
	 */
	private void setTableTotal(KDTable table) {
		if (table.getName().equals(Constants.Total_Table) || table.getName().equals(Constants.Desc_Table))
			return;
		int amountSumCol = Integer.parseInt((String) totalColumnMap.get(table.getName()));
		if (amountSumCol < 1)
			return;
		IRow row = (IRow) table.getRow(0);
		List aimRowList = new ArrayList();
		for (int j = 1; j < table.getRowCount(); j++) {
			IRow rowAfter = table.getRow(j);
			if (rowAfter.getTreeLevel() == 0)
				aimRowList.add(rowAfter);
		}
		if (aimRowList.size() > 0) {
			BigDecimal sum = Constants.zero;
			for (int rowIndex = 0; rowIndex < aimRowList.size(); rowIndex++) {
				IRow rowAdd = (IRow) aimRowList.get(rowIndex);
				Object value = rowAdd.getCell(amountSumCol).getValue();
				if (value != null) {
					sum = sum.add(ObjectUtil.convertToBigDecimal(value, Constants.zero));
				}
			}
			row.getCell(amountSumCol).setValue(sum);
		}else{
			row.getCell(amountSumCol).setValue(Constants.zero);
		}
	}

	/**
	 *  更新报价汇总表
	 */
	private void updateTotalPageData() {
		if (this.tabbedPane.getTabCount() == 0) {
			return;
		}
		KDTable table = (KDTable) tabbedPane.getComponentAt(1);
		if (!table.getName().equals(Constants.Total_Table)) {
			return;
		}
		int pageCount = tabbedPane.getTabCount() - 2;
		BigDecimal sum = Constants.zero;
		String[] str = totalColumn.split(";");
		for (int i = 2; i < pageCount + 2; i++) {
			if ("0".equals(str[i - 2]))
				continue;
			KDTable page = (KDTable) tabbedPane.getComponentAt(i);
			int col = Integer.parseInt(str[i - 2]);
			for (int j = 3; j < page.getColumnCount(); j++) {
				if (j == col) {
					BigDecimal value = (BigDecimal) page.getCell(0, j).getValue();
					if(value == null){
						table.getCell(i - 2, 2).setValue(Constants.zero);
					}else{
						table.getCell(i - 2, 2).setValue(value);
					}
					if (value != null) {
						sum = sum.add(value);
					}
					break;
				}
			}
		}
		table.getCell(pageCount, "amount").setValue(sum);
		Object value = table.getCell(pageCount + 1, "amount").getValue();
		BigDecimal scale = ObjectUtil.convertToBigDecimal(value, Constants.zero);
		BigDecimal amount = sum.multiply(scale).divide(new BigDecimal(100), BigDecimal.ROUND_HALF_UP);
		table.getCell(pageCount + 2, "amount").setValue(amount);
		table.getCell(pageCount + 3, "amount").setValue(sum.add(amount));
	}

	private HashMap map = new HashMap(5);

	public void tabbedPaneChange() {
		KDTable table = (KDTable) tabbedPane.getSelectedComponent();
		int selectIndex = tabbedPane.getSelectedIndex();
		if(table != null){
//			NewListingPageInfo pageInfo=(NewListingPageInfo)table.getUserObject();
			//20101012 zhougang
			//当选择的表头为供应商自定义时，甲方将不能对此表头进行任何操作。
			if(userDefindedTableMap.containsKey(String.valueOf(selectIndex))){
					addLineMenuItem.setEnabled(true);
					insertLineMenuItem.setEnabled(true);
					removeLineMenuItem.setEnabled(true);
					upLineMenuItem.setEnabled(true);
					downLineMenuItem.setEnabled(true);
					addLineButton.setEnabled(true);
					insertLineButton.setEnabled(true);
					removeLineButton.setEnabled(true);
					upLineButton.setEnabled(true);
					downLineButton.setEnabled(true);
					table.getColumn(1).getStyleAttributes().setHided(true);
					table.getColumn(2).getStyleAttributes().setHided(true);
//					table.getColumn("金额").getStyleAttributes().setLocked(true);
				}else{
					addLineMenuItem.setEnabled(false);
					insertLineMenuItem.setEnabled(false);
					removeLineMenuItem.setEnabled(false);
					upLineMenuItem.setEnabled(false);
					downLineMenuItem.setEnabled(false);
					addLineButton.setEnabled(false);
					insertLineButton.setEnabled(false);
					removeLineButton.setEnabled(false);
					upLineButton.setEnabled(false);
					downLineButton.setEnabled(false);
				}
//			}else{
//				addLineMenuItem.setEnabled(true);
//				insertLineMenuItem.setEnabled(true);
//				removeLineMenuItem.setEnabled(true);
//				upLineMenuItem.setEnabled(true);
//				downLineMenuItem.setEnabled(true);
//				addLineButton.setEnabled(true);
//				insertLineButton.setEnabled(true);
//				removeLineButton.setEnabled(true);
//				upLineButton.setEnabled(true);
//				downLineButton.setEnabled(true);
//			}
		}
		
		if (MainFrame.currentFile == null || properties == null || tabbedPane.getSelectedIndex() == 0){
			return;
		}
		
		if (map.containsKey(table.getName())){
			return;
		}

		int freezeCol = ObjectUtil.convertToInt(properties.getProperty(table.getName()), -1);
		table.getViewManager().freeze(-1, freezeCol);
		map.put(table.getName(), null);
	}

	class X extends KDTEditAdapter {
		private KDTable table;

		private int[] colsInt;
		// 综合单价列
		int priceCol;

		X(final KDTable table, final int[] colsInt) {
			this.table = table;
			this.colsInt = colsInt;
			init();
		}

		private void init() {
			String str = (String) totalPriceMap.get(table.getName());
			priceCol = Integer.parseInt(str);
		}
		
		public void editStopped(KDTEditEvent e) {
			isSaved = false;
			ObjectUtil.setEnabled(new Object[] { saveMenuItem, saveButton }, !isSaved);
			int selectComponent = tabbedPane.getSelectedIndex();
			if(!userDefindedTableMap.containsKey(String.valueOf(selectComponent))){
				if (!ObjectUtil.contains((int[]) priceColumn.get(table.getName()), e.getColIndex())){
					return;
				}
			}
			Object old = e.getOldValue();
			ICell cell = table.getCell(e.getRowIndex(), e.getColIndex());
			Object _new = cell.getValue();
			if ((old != null && _new != null) && !(old.equals(_new)) || (old == null && _new !=null) || (old != null && _new ==null)) {
				setUnionData(table, colsInt);
				if (Constants.middle_warm_color.equals(cell.getStyleAttributes().getBackground())) {
					if (!ObjectUtil.isZero(_new)) {
						cell.getStyleAttributes().setBackground(Color.WHITE);
					}
				} else {
					Color background2 = table.getCell(cell.getRowIndex(), priceCol).getStyleAttributes().getBackground();
					if (Constants.middle_warm_color.equals(background2)) {
						if (!ObjectUtil.isZero(table.getCell(cell.getRowIndex(), priceCol).getValue())) {
							table.getCell(cell.getRowIndex(), priceCol).getStyleAttributes().setBackground(Color.WHITE);
						}
					}
				}
			}
		}
	};

	private boolean verifyData() {
		boolean isErr = false;
		for (int i = 2; i < this.tabbedPane.getTabCount(); i++) {
			KDTable table = (KDTable) this.tabbedPane.getComponentAt(i);
			int[] columns = (int[]) priceColumn.get(table.getName());
			// 校验同名称价格是否相同
			Map sameItemMap = new HashMap();
			for (int j = 1; j < table.getRowCount(); j++) {
				IRow row = table.getRow(j);
				ICell nameCell = row.getCell("子目名称");
//				ICell unitCell = row.getCell("单位");
				String name = null;//, unit = null;
				if (nameCell.getValue() instanceof String) {
					name = (String) nameCell.getValue();
				} else {
					logger.error("no name cell ??");
				}
//				if (unitCell.getValue() instanceof String) {
//					unit = (String) unitCell.getValue();
//				} else {
//					logger.error("no unit cell ??");
//				}
//				Color color = new Color(0, true);
				Color errColor = new Color(0xFF, 0xEA, 0x67);
//				int colCount = table.getColumnCount();
				String key = name;
				if (sameItemMap.containsKey(key)) {
					IRow fRow = (IRow) sameItemMap.get(key);
					for (int k = 0; k < columns.length; k++) {
						ICell cell = row.getCell(columns[k]);
						if (!cell.getStyleAttributes().isLocked()) {
							cell.getStyleAttributes().setBackground(Color.white);
							BigDecimal fValue = Constants.zero;
							Object sameRowCellVal = fRow.getCell(columns[k]).getValue();
							if (sameRowCellVal != null && sameRowCellVal instanceof BigDecimal) {
								fValue = (BigDecimal) sameRowCellVal;
							}
							BigDecimal value = Constants.zero;
							Object rowCellVal = row.getCell(columns[k]).getValue();
							if (rowCellVal != null && rowCellVal instanceof BigDecimal) {
								value = (BigDecimal) rowCellVal;
							}
							if (value.compareTo(fValue) != 0) {
								cell.getStyleAttributes().setBackground(errColor);
								logger.error("row:" + j + ", sameNameRow:" + fRow.getRowIndex());
								logger.error("val:" + value + ", oVal:" + sameRowCellVal + ",cell:" + k);
								isErr = true;
							}
						}
					}

				} else {
					sameItemMap.put(key, row);
				}
			}
		}
		return isErr;
	}
	public void importExcel(File file) throws Exception {
		if (file == null) {
			logger.warn("importFile null");
			return;
		}
		
		String fileName = file.getAbsolutePath();
		KDSBook kdsbook = ObjectUtil.excelReaderParse(this,fileName);
		
		if(kdsbook != null){							
			List errList = ObjectUtil.validateImportExcelHeadRow(tableMap,kdsbook);					
			if(errList.size()>0){
				//JOptionPane.showMessageDialog(this,"Excel中的表头和需要导入的表头不匹配","导入Excel",JOptionPane.ERROR_MESSAGE);
				if(dialog == null)
					dialog = new ErrorListDialog(this);
				dialog.setErrorList(errList);
				dialog.show();
			}
			else{				
				importKDSbookToTables(tableMap,kdsbook);
			}
		}		
	}
	private void importKDSbookToTables(Map tables,KDSBook kdsbook) throws Exception{
		for(int i=0;i<kdsbook.getSheetCount();i++){
			Sheet excelSheet = KDSBookToBook.traslate(kdsbook).getSheet(i);
			if(excelSheet.getSheetName().equals(Constants.Total_Table)||
					excelSheet.getSheetName().equals(Constants.Desc_Table))
				continue;
			KDSSheet kdsSheet = kdsbook.getSheet(new Integer(i));
			importSheetToTable((KDTable) tables.get(excelSheet.getSheetName()), excelSheet, kdsSheet, i);
		}
	}
	/***
	 * 导入招标说明页签
	 * @param table
	 * @param excelSheet
	 * @param kdsSheet
	 * @throws Exception
	 */
	private void importListingDescriptionSheetToTable(KDTable table, Sheet excelSheet,
			KDSSheet kdsSheet) throws Exception {
		int pageHeadIndex = table.getColumnIndex("name");
		int maxRow = excelSheet.getMaxRowIndex();
		int maxColumn = excelSheet.getMaxColIndex();
		for(int row = 1;row <= maxRow; row++) {
			Row excelRow = excelSheet.getRow(row, true);
			Variant rawVal = excelRow.getCell(pageHeadIndex, true).getValue();
			if (Variant.isNull(rawVal)) {
				continue;
			}
			String excelItemName = (String) kdsSheet.getCell(row,
					pageHeadIndex, true).getValue();
			String name = (String) table.getCell(row - 1, pageHeadIndex).getValue();
			if (!(name.equals(excelItemName))) {
				continue;
			}
			for (int col = 1; col <= maxColumn; col++) {
				ICell tblCell = table.getCell(row - 1, col);
				if (tblCell == null || tblCell.getStyleAttributes().isLocked())
					continue;

				Variant cellRawVal = excelSheet.getCell(row, col, true)
						.getValue();
				if (Variant.isNull(cellRawVal)) {
					continue;
				}
				String colValue = cellRawVal.toString();
				if (colValue.length() > 500) {
					colValue = colValue.substring(499);
				}
				tblCell.setValue(colValue);
			}
		}
	}

	private void importSheetToTable(KDTable table, Sheet excelSheet, KDSSheet kdsSheet, int i) throws Exception {
		int itemNameIndex = table.getColumnIndex("子目名称");
		int unitIndex = table.getColumnIndex("单位");
		int maxRow = excelSheet.getMaxRowIndex();
		int maxColumn = excelSheet.getMaxColIndex();
		for (int row = 3; row <= maxRow; row++) {	
			
			Row excelRow = excelSheet.getRow(row, true);
			Variant rawVal = excelRow.getCell(itemNameIndex, true).getValue();
			if (Variant.isNull(rawVal)) {
				continue;
			}
			String excelItemName = (String) kdsSheet.getCell(row,
					itemNameIndex, true).getValue();
			String excelUnit = (String) kdsSheet.getCell(row, unitIndex,
					true).getValue();
			excelUnit = (excelUnit == null) ? "" : excelUnit;
			
			String name = (String) table.getCell(row - 2, itemNameIndex).getValue();
			String unit = (String) table.getCell(row - 2, unitIndex).getValue();
			unit = (unit == null) ? "" : unit;
			// 检查excel数据的子目名称+单位和界面上的是否一致
			if (!(name.equals(excelItemName) && unit.equals(excelUnit))) {
				continue;
			}
			for (int col = 1; col <= maxColumn; col++) {
				ICell tblCell = table.getCell(row - 2, col);
				if (tblCell == null || tblCell.getStyleAttributes().isLocked())
					continue;

				Variant cellRawVal = excelSheet.getCell(row, col, true).getValue();				
				if (Variant.isNull(cellRawVal)) {
					continue;
				}
				if(table.getColumn(col).getStyleAttributes().getNumberFormat().equals("yyyy-MM-dd")||
						table.getColumn(col).getStyleAttributes().getNumberFormat().equals("yyyy-M-d")||
						table.getColumn(col).getStyleAttributes().getNumberFormat().equals("yyyy-m-d")){
					Date dateVal = null;
					if (cellRawVal.isDate()) {
						if (cellRawVal.getVt() == Variant.VT_CALENDAR) {
							dateVal = ((Calendar) cellRawVal.getValue()).getTime();
						} else {
							dateVal = (Date) cellRawVal.getValue();
						}
					} else {				
						try {
							//加入了对数字转换为日期的一个判断
							//如果是数字，转换为日期的时候，应当-1，才与Excel里面的值对应
							if(cellRawVal.isNumber()){
								//变态的EXCEL问题，excel中1900年2月29日竟然是存在的，垃圾的微软
								BigDecimal temp=cellRawVal.toBigDecimal();
								if(temp.intValue()>=60){
									Calendar cal= cellRawVal.toCalendar();
									//cal.add(Calendar.DATE,-1);
									dateVal = cal.getTime();
								}
								else if(temp.intValue()<60&&temp.intValue()>0){
									Calendar cal= cellRawVal.toCalendar();
									cal.add(Calendar.DATE,1);
									dateVal = cal.getTime();
								}
							}
							else
								dateVal = cellRawVal.toDate();
							logger.debug("Variant.toDate :" + dateVal);
						} catch (SyntaxErrorException e) {
							// excel val may not be date val
							logger.error("cannot parsed as Date", e);
						}
					}
					tblCell.setValue(dateVal);
					continue;
				}
				String colValue = cellRawVal.toString();
				if (colValue.length() > 500) {
					colValue = colValue.substring(499);
				}
				tblCell.setValue(colValue);
			}
		}
		
		String comment = (String) fileComment.get(i);
		String cols = comment.substring(comment.lastIndexOf(separator) + 1);
		String[] strs = cols.split(";");
		if (ObjectUtil.isNullArray(strs))
			return;
		final int colsInt[] = new int[strs.length];
		for (int j = 0; j < strs.length; j++) {
			colsInt[j] = Integer.parseInt(strs[j]);
		}
		setUnionData(table, colsInt);
	}
	
	protected boolean beforeImportExcel() {
		
//		if (tabbedPane.getSelectedIndex() < 2) {
//			JOptionPane.showMessageDialog(this,"当前页签不允许导入Excel！","导入Excel",JOptionPane.ERROR_MESSAGE);
//			return false;
//		}
		if (JOptionPane.OK_OPTION != JOptionPane.showConfirmDialog(this, "此操作将覆盖以前的数据!", "导入前提示", JOptionPane.OK_CANCEL_OPTION)) 
		{
			return false;
		}
		return true;
	}

	protected boolean importFileExcel() {
		// TODO 自动生成方法存根
		File file = fileDialog("导入", JFileChooser.OPEN_DIALOG,FdcFileChooser.XlsFileExt,FdcFileChooser.XlsFileExtDesc);
		try {
			importExcel(file);
		} catch (Exception e) {
			// TODO 自动生成 catch 块
			logger.error("import excel error:",e);
			return false;
		}
		return true;
	}
	
	protected void afterImportExcel() {
		//		设置自动行高
		ObjectUtil.setAotuHeight(this.tabbedPane);
	}

	protected boolean exportExcel() {		
		// TODO 自动生成方法存根
		try {
			List tables = new ArrayList();
			for (int i = 0; i < tabbedPane.getTabCount(); i++) {
				KDTable table = (KDTable) tabbedPane.getComponentAt(i);
				if(userDefindedTableMap.containsKey(String.valueOf(i))){
					table.getColumn(1).getStyleAttributes().setHided(false);
					table.getColumn(2).getStyleAttributes().setHided(false);
				}
				tables.add(tabbedPane.getComponentAt(i));
			}
			ExportToExcel.exportToExcel(tables);
			for (int j = 0; j < tabbedPane.getTabCount(); j++) {
				KDTable table = (KDTable) tabbedPane.getComponentAt(j);
				if(userDefindedTableMap.containsKey(String.valueOf(j))){
					table.getColumn(1).getStyleAttributes().setHided(true);
					table.getColumn(2).getStyleAttributes().setHided(true);
				}
			}
		} catch (Exception e) {
			logger.error(e);
		}
		return false;
	}

	/**
	 * 增加分录。
	 */
	public void addLine() {
		setColumnObject();
		KDTable table = (KDTable) this.tabbedPane.getSelectedComponent();
		
		if(this.tabbedPane.getSelectedIndex()<2){
			JOptionPane.showConfirmDialog(this, "不能在此页签上进行分录的操作！", "错误提示", JOptionPane.DEFAULT_OPTION);
			return;
		}
		
//		if (!checkSelectTable(table)) {
//			showInfo();
//			return;
//		}
		//Object page = table.getUserObject();
//		NewListingEntryInfo infoEntry = new NewListingEntryInfo();
		IRow row = table.addRow();
		CellTreeNode node = new CellTreeNode();
		row.setTreeLevel(0);
		
		//String headTypeId = page.getHeadType().getId().toString();
		KDTextField f7 =  new KDTextField();//this.getF7Box("");
//		CellTextRenderImpl avr = new CellTextRenderImpl();
//		avr.getText(new BizDataFormat("$name$"));
		row.getCell("子目名称").setEditor(new KDTDefaultCellEditor(f7));
//		row.getCell("子目名称").setRenderer(avr);
		row.getCell("level").setValue(new Integer(0));
		row.getCell("isKey").setValue(Boolean.FALSE);
		row.getCell("isCompose").setValue(Boolean.FALSE);
		node.setValue(new Integer(0));
		node.setTreeLevel(0);
//		infoEntry.setLevel(0);
//		infoEntry.setIsLeaf(true);
//		infoEntry.setIsCanZeroProAmount(false);
//		table.getRow(table.getRowCount()-1).setUserObject(infoEntry);
//		FDCHelper.formatTableNumber(table, "工程量");
//		FDCHelper.formatTableNumber(table, "综合单价");

		int colsInt[] = new int[1];
		colsInt[0] = 7;
		setUnionData(table,colsInt);
	}
	private void showInfo() {
		if(this.tabbedPane.getSelectedIndex()<2){
			JOptionPane.showConfirmDialog(this, "不能在此页签上进行分录的操作！", "错误提示", JOptionPane.DEFAULT_OPTION);
		}
//		else{
//			JOptionPane.showConfirmDialog(this, "请先选择表头！", "错误提示", JOptionPane.DEFAULT_OPTION);
//		}
	}
	
	public boolean checkSelectTable(KDTable table){
		if(table.getUserObject() == null || table.getColumnCount() == 0){
			return false;
		}else{
			return true;
		}
	}
	
//	static class myF7Box extends KDBizPromptBox{
//		/**
//		 * 
//		 */
//		private static final long serialVersionUID = 821085289475565286L;
//
//		protected Object stringToValue(String t) {
//			Object obj = super.stringToValue(t);
//			if (obj instanceof IObjectValue) {
//				return obj;
//			} else {
//				if (t != null && t.length() > 499) {
//					t = t.substring(0, 499);
//				}
//				return t;
//			}
//
//		}
//	}
//	
//	public Set getAllParentIds() {
//		TreeModel orgTreeModel = null;
//		try {
//			orgTreeModel = NewOrgUtils.getTreeModel(OrgViewType.COMPANY, "",
//					null, null, null);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		Set idSet = new HashSet();
//		if(orgTreeModel==null||orgTreeModel.getRoot()==null)
//			return idSet;
//		DefaultKingdeeTreeNode orgRoot = (DefaultKingdeeTreeNode) orgTreeModel
//				.getRoot();
//		DefaultKingdeeTreeNode node = this.findNode(orgRoot, this.currentOrg
//				.getId().toString());
//		
//		idSet.add(currentOrg.getId().toString());
//		while (node.getParent() != null) {
//			DefaultKingdeeTreeNode parent = (DefaultKingdeeTreeNode) node
//					.getParent();
//			OrgStructureInfo oui = (OrgStructureInfo) parent.getUserObject();
//			FullOrgUnitInfo info = oui.getUnit();
//			idSet.add(info.getId().toString());
//			node = parent;
//		}
//		return idSet;
//	}
//	
//	private DefaultKingdeeTreeNode findNode(DefaultKingdeeTreeNode node,
//			String id) {
//		if (node.getUserObject() instanceof CurProjectInfo) {
//			CurProjectInfo projectInfo = (CurProjectInfo) node.getUserObject();
//			if (projectInfo.getId().toString().equals(id)) {
//				return node;
//			}
//		} else if (node.getUserObject() instanceof OrgStructureInfo) {
//			OrgStructureInfo oui = (OrgStructureInfo) node.getUserObject();
//			FullOrgUnitInfo info = oui.getUnit();
//			if (info.getId().toString().equals(id)) {
//				return node;
//			}
//		}
//
//		for (int i = 0; i < node.getChildCount(); i++) {
//			DefaultKingdeeTreeNode findNode = findNode(
//					(DefaultKingdeeTreeNode) node.getChildAt(i), id);
//			if (findNode != null) {
//				return findNode;
//			}
//
//		}
//		return null;
//	}
	
	public void setColumnObject(){
		Set set = columnObjMap.keySet();
		for (Iterator iter = set.iterator(); iter.hasNext();) {
			IColumn column = (IColumn) iter.next();
			column.setUserObject(columnObjMap.get(column));
		}
	}
	
	/**
	 * 插入分录。
	 */
	public void insertLine() {
		setColumnObject();
		KDTable table = (KDTable) this.tabbedPane.getSelectedComponent();
		if(this.tabbedPane.getSelectedIndex()<2){
			JOptionPane.showConfirmDialog(this, "不能在此页签上进行分录的操作！", "错误提示", JOptionPane.DEFAULT_OPTION);
			return;
		}
		int index = table.getSelectManager().getActiveRowIndex();
		Integer level = null;
		if (index == -1) {
			table.getSelectManager().set(table.getRowCount() - 1, 0);
			index = table.getRowCount() - 1;
		} else {
			level = (Integer) table.getCell(index, 0).getValue();
		}
		if (level == null) {
			level = new Integer(0);
		}
		
//		NewListingEntryInfo infoEntry = new NewListingEntryInfo();
		IRow row = table.addRow(index + 1);
		CellTreeNode node = new CellTreeNode();
		row.setTreeLevel(0);
//		row.setUserObject(infoEntry);
		KDTextField f7 = new KDTextField(); 
//		CellTextRenderImpl avr = new CellTextRenderImpl();
//		avr.getText(new BizDataFormat("$number$"));
		row.getCell("子目名称").setEditor(new KDTDefaultCellEditor(f7));
//		row.getCell("子目名称").setRenderer(avr);
		row.getCell("level").setValue(level);
		row.getCell("isKey").setValue(Boolean.FALSE);
		row.getCell("isCompose").setValue(Boolean.FALSE);
		row.setTreeLevel(level.intValue());
		node.setValue(new Integer(0));
		node.setTreeLevel(0);
//		infoEntry.setLevel(0);
//		infoEntry.setIsLeaf(true);
//		infoEntry.setIsCanZeroProAmount(false);
//		table.getRow(index + 1).setUserObject(infoEntry);
		int colsInt[] = new int[1];
		colsInt[0] = 7;
		setUnionData(table,colsInt);
	}

	/**
	 * 删除分录。
	 */
	public void removeLine() {
		setColumnObject();
		KDTable table = (KDTable) this.tabbedPane.getSelectedComponent();
		if(this.tabbedPane.getSelectedIndex()<2){
			JOptionPane.showConfirmDialog(this, "不能在此页签上进行分录的操作！", "错误提示", JOptionPane.DEFAULT_OPTION);
			return;
		}
		KDTSelectBlock sb = table.getSelectManager().get();
		if (sb == null || sb.size() <= 0) {
//			MsgBox.showInfo("请先选择行！");
			JOptionPane.showConfirmDialog(this, "请先选择行！", "错误提示", JOptionPane.DEFAULT_OPTION);
			return;
		}
		int top = table.getSelectManager().get().getBeginRow();
		int bottom = table.getSelectManager().get().getEndRow();
		for (int i = bottom; i >= top; i--) {
			if(i == 0){
//				MsgBox.showInfo("选择了非明细行,不能删除！");
				JOptionPane.showConfirmDialog(this, "选择了非明细行,不能删除！", "错误提示", JOptionPane.DEFAULT_OPTION);
				return;
			}
			if (i != 0 && !isLeafRow(table, i)) {
//				MsgBox.showInfo("选择了非明细行,不能删除！");
				JOptionPane.showConfirmDialog(this, "选择了非明细行,不能删除！", "错误提示", JOptionPane.DEFAULT_OPTION);
				return;
			}
		}

		if(JOptionPane.showConfirmDialog(this, "是否确认删除？") != JOptionPane.YES_OPTION){
			return;
		}
		
		for (int i = bottom; i >= top; i--) {
			if (i != 0) {
				table.removeRow(i);
			}
		}
		
		int colsInt[] = new int[1];
		colsInt[0] = 7;
		setUnionData(table,colsInt);
	}

	/**
	 * 升级。
	 */
	public void upLine() {
//		setColumnObject();
//		KDTable table = (KDTable) this.tabbedPane.getSelectedComponent();
//		if (!checkSelectTable(table)) {
//			showInfo();
//			return;
//		}
//		KDTSelectBlock sb = table.getSelectManager().get();
//		if (sb == null || sb.size() <= 0) {
////			MsgBox.showInfo("请先选择行!");
//			JOptionPane.showConfirmDialog(this, "请先选择行！", "错误提示", JOptionPane.DEFAULT_OPTION);
//			return;
//		}
//		int top = sb.getTop(); // 选择块最上边行索引
//		if (top == 0) {
//			return;
//		}
//		int bottom = sb.getBottom(); // 选择块最下边行索引
//		int levelOrig = Integer.parseInt(table.getCell(top, "level").getValue()
//				.toString());
//		if (levelOrig == 0) {
////			MsgBox.showInfo("最上级别不能升级！");
//			JOptionPane.showConfirmDialog(this, "最上级别不能升级！", "错误提示", JOptionPane.DEFAULT_OPTION);
//			return;
//		}
//		for (int i = top + 1; i <= bottom; i++) {
//			int level = Integer.parseInt(table.getCell(i, "level").getValue()
//					.toString());
//			if (level != levelOrig) {
////				MsgBox.showError(this, "必须对同一级次执行本操作！");
//				JOptionPane.showConfirmDialog(this, "必须对同一级次执行本操作！", "错误提示", JOptionPane.DEFAULT_OPTION);
//				return;
//			}
//		}
//		
//		for (int i = top; i <= bottom; i++) {
//			table.getCell(i, "level").setValue(new Integer(levelOrig - 1));
//			table.getRow(i).setTreeLevel(levelOrig - 1);
//		}
//		for (int j = bottom + 1; j < table.getRowCount(); j++) {
//			int levelnext = Integer.parseInt(table.getCell(j, "level")
//					.getValue().toString());
//			if (levelnext <= levelOrig) {
//				break;
//			}
//			table.getCell(j, "level").setValue(new Integer(levelnext - 1));
//			table.getRow(j).setTreeLevel(levelnext - 1);
//		}
//		table.getTreeColumn().setDepth(getMaxLevel(table));
//
//		for (int i = top - 1; i >= 1; i--) {
//			int levelOld = Integer.parseInt(table.getCell(i, "level")
//					.getValue().toString());
//			if (levelOld == levelOrig) {
//				NewListingEntryInfo info = (NewListingEntryInfo) table
//						.getRow(i).getUserObject();
//				info.setIsLeaf(false);
//				break;
//			}
//		}
//		
//		table.getCell(top-1, 7).setValue(Constants.zero);
//		int colsInt[] = new int[1];
//		colsInt[0] = 7;
//		setUnionData(table,colsInt);
	}

	/**
	 * 降级。
	 */
	public void downLine() {
//		setColumnObject();
//		KDTable table = (KDTable) this.tabbedPane.getSelectedComponent();
//		if (!checkSelectTable(table)) {
//			showInfo();
//			return;
//		}
//		KDTSelectBlock sb = table.getSelectManager().get();
//		if (sb == null || sb.size() <= 0) {
////			MsgBox.showInfo("请先选择行!");
//			JOptionPane.showConfirmDialog(this, "请先选择行！", "错误提示", JOptionPane.DEFAULT_OPTION);
//			return;
//		}
//		int top = sb.getTop(); // 选择块最上边行索引
//		if (top == 0) {
//			return;
//		}
//		int bottom = sb.getBottom(); // 选择块最下边行索引
//		int levelOrig = Integer.parseInt(table.getCell(top, "level").getValue()
//				.toString());
//		if (top - 1 >= 1) {
//			int uplevel = Integer.parseInt(table.getCell(top - 1, "level")
//					.getValue().toString());
//			if (uplevel < levelOrig) {
////				MsgBox.showInfo("最末级别不能降级！");
//				JOptionPane.showConfirmDialog(this, "最末级别不能降级！", "错误提示", JOptionPane.DEFAULT_OPTION);
//				return;
//			}
//		} else {
////			MsgBox.showInfo("最末级别不能降级!");
//			JOptionPane.showConfirmDialog(this, "最末级别不能降级！", "错误提示", JOptionPane.DEFAULT_OPTION);
//			return;
//		}
//
//		for (int i = top + 1; i <= bottom; i++) {
//			int level = Integer.parseInt(table.getCell(i, "level").getValue()
//					.toString());
//			if (level != levelOrig) {
////				MsgBox.showError(this, "必须对同一级次执行本操作！");
//				JOptionPane.showConfirmDialog(this, "必须对同一级次执行本操作！", "错误提示", JOptionPane.DEFAULT_OPTION);
//				return;
//			}
//		}
//
//		for (int i = top; i <= bottom; i++) {
//			table.getCell(i, "level").setValue(new Integer(levelOrig + 1));
//			table.getRow(i).setTreeLevel(levelOrig + 1);
//		}
//		for (int i = bottom + 1; i < table.getRowCount(); i++) {
//			int levelnext = Integer.parseInt(table.getCell(i, "level")
//					.getValue().toString());
//			if (levelnext <= levelOrig) {
//				break;
//			}
//			table.getCell(i, "level").setValue(new Integer(levelnext + 1));
//			table.getRow(i).setTreeLevel(levelnext + 1);
//		}
//		table.getTreeColumn().setDepth(getMaxLevel(table));
//		for (int i = top - 1; i >= 0; i--) {
//			int levelOld = Integer.parseInt(table.getCell(i, "level")
//					.getValue().toString());
//			if (levelOld == levelOrig) {
//				NewListingEntryInfo info = (NewListingEntryInfo) table
//						.getRow(i).getUserObject();
//				info.setIsLeaf(false);
//				break;
//			}
//		}
//		int colsInt[] = new int[1];
//		colsInt[0] = 7;
//		setUnionData(table,colsInt);
	}
}
