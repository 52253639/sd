/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.client;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.*;
import java.io.File;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.common.variant.Variant;
import com.kingdee.bos.ctrl.excel.io.kds.KDSBookToBook;
import com.kingdee.bos.ctrl.excel.model.struct.Sheet;
import com.kingdee.bos.ctrl.kdf.export.ExportManager;
import com.kingdee.bos.ctrl.kdf.export.KDTables2KDSBook;
import com.kingdee.bos.ctrl.kdf.export.KDTables2KDSBookVO;
import com.kingdee.bos.ctrl.kdf.kds.KDSBook;
import com.kingdee.bos.ctrl.kdf.read.POIXlsReader;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTMenuManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDContainer;
import com.kingdee.bos.ctrl.swing.KDFileChooser;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.base.permission.client.longtime.ILongTimeTask;
import com.kingdee.eas.fdc.aimcost.CostIndexConfigInfo;
import com.kingdee.eas.fdc.aimcost.CostIndexEntryInfo;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.invite.InviteTypeInfo;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.ma.budget.client.LongTimeDialog;
import com.kingdee.eas.util.client.EASResource;

/**
 * output class name
 */
public class SalePlanBillUI extends AbstractSalePlanBillUI
{
    private static final Logger logger = CoreUIObject.getLogger(SalePlanBillUI.class);
    private  KDTable table=null;
    public SalePlanBillUI() throws Exception
    {
        super();
    }
    public void onLoad() throws Exception {
		super.onLoad();
		KDWorkButton btnImportExcel = new KDWorkButton();
		KDWorkButton btnExportExcel = new KDWorkButton();

		
		this.actionExcelImport.putValue("SmallIcon", EASResource.getIcon("imgTbtn_input"));
		btnImportExcel = (KDWorkButton)contEntry.add(this.actionExcelImport);
		btnImportExcel.setText("导入");
		btnImportExcel.setSize(new Dimension(140, 19));
		
		this.actionExcelExport.putValue("SmallIcon", EASResource.getIcon("imgTbtn_output"));
		btnExportExcel = (KDWorkButton)contEntry.add(this.actionExcelExport);
		btnExportExcel.setText("导出");
		btnExportExcel.setSize(new Dimension(140, 19));
	 }
    public void actionExcelExport_actionPerformed(ActionEvent e)throws Exception {
    	ExportManager exportM = new ExportManager();
        String path = null;
        File tempFile = File.createTempFile("eastemp",".xls");
        path = tempFile.getCanonicalPath();

        KDTables2KDSBookVO[] tablesVO = new KDTables2KDSBookVO[1];
        tablesVO[0] = new KDTables2KDSBookVO(table);
		tablesVO[0].setTableName("供应计划");
		
        KDSBook book = null;
        book = KDTables2KDSBook.getInstance().exportKDTablesToKDSBook(tablesVO,true,true);
        exportM.exportToExcel(book, path);
        
		KDFileChooser fileChooser = new KDFileChooser();
		fileChooser.setFileSelectionMode(0);
		fileChooser.setMultiSelectionEnabled(false);
		fileChooser.setSelectedFile(new File("供应计划.xls"));
		int result = fileChooser.showSaveDialog(this);
		if (result == KDFileChooser.APPROVE_OPTION){
			File dest = fileChooser.getSelectedFile();
			try{
				File src = new File(path);
				if (dest.exists())
					dest.delete();
				src.renameTo(dest);
				FDCMsgBox.showInfo("导出成功！");
				KDTMenuManager.openFileInExcel(dest.getAbsolutePath());
			}
			catch (Exception e3)
			{
				handUIException(e3);
			}
		}
		tempFile.delete();
    }
    String path=null;
    public void actionExcelImport_actionPerformed(ActionEvent e)throws Exception {
    	path = SHEHelper.showExcelSelectDlg(this);
		if (path == null) {
			return;
		}
		Window win = SwingUtilities.getWindowAncestor(this);
        LongTimeDialog dialog = null;
        if(win instanceof Frame){
        	dialog = new LongTimeDialog((Frame)win);
        }else if(win instanceof Dialog){
        	dialog = new LongTimeDialog((Dialog)win);
        }
        if(dialog==null){
        	dialog = new LongTimeDialog(new Frame());
        }
        dialog.setLongTimeTask(new ILongTimeTask() {
			public void afterExec(Object arg0) throws Exception {
				Boolean bol=(Boolean)arg0;
				if(bol){
					FDCMsgBox.showInfo("导入成功！");
				}
			}
			public Object exec() throws Exception {
				boolean bol=importExcelToTable(path);
				return bol;
			}
    	}
	    );
	    dialog.show();
	}
	private boolean importExcelToTable(String fileName) throws Exception {
		KDSBook kdsbook = null;
		try {
			kdsbook = POIXlsReader.parse2(fileName);
		} catch (Exception e) {
			e.printStackTrace();
			FDCMsgBox.showWarning(this,"读EXCEL出错,EXCEl格式不匹配！");
			return false;
		}
		if (kdsbook == null) {
			return false;
		}
		for(int i=0;i<KDSBookToBook.traslate(kdsbook).getSheetCount();i++){
			Sheet excelSheet = KDSBookToBook.traslate(kdsbook).getSheet(i);
			if(excelSheet.getSheetName().indexOf("楼栋跟进计划表")>=0){
            	Map e_colNameMap = new HashMap();
        		int e_maxRow = excelSheet.getMaxRowIndex();
        		int e_maxColumn = excelSheet.getMaxColIndex();
        		table=new KDTable();
        		table.checkParsed();
        		table.getStyleAttributes().setLocked(true);
        		IRow headRow=table.addHeadRow();
        		IRow headRowTwo=table.addHeadRow();
        		IRow headRowThree=table.addHeadRow();
        		for (int col = 0; col <= e_maxColumn; col++) {
        			String excelColName = excelSheet.getCell(3, col, true).getText();
        			if(excelColName!=null&&!excelColName.trim().equals("")){
        				if(excelColName.indexOf("区域")>=0){
        					e_colNameMap.put(excelColName, new Integer(col));
            				IColumn column=table.addColumn();
            				column.setKey(excelColName);
            				headRow.getCell(excelColName).setValue(excelColName);
            				headRowTwo.getCell(excelColName).setValue(excelColName);
            				headRowThree.getCell(excelColName).setValue(excelColName);
            				table.getHeadMergeManager().mergeBlock(0, column.getColumnIndex(), 2, column.getColumnIndex());
        				}else if(excelColName.indexOf("项目")>=0){
        					e_colNameMap.put(excelColName, new Integer(col));
            				IColumn column=table.addColumn();
            				column.setKey(excelColName);
            				headRow.getCell(excelColName).setValue(excelColName);
            				headRowTwo.getCell(excelColName).setValue(excelColName);
            				headRowThree.getCell(excelColName).setValue(excelColName);
            				table.getHeadMergeManager().mergeBlock(0, column.getColumnIndex(), 2, column.getColumnIndex());
        				}else if(excelColName.indexOf("产品类型")>=0){
        					e_colNameMap.put("业态", new Integer(col));
            				IColumn column=table.addColumn();
            				column.setKey("业态");
            				headRow.getCell("业态").setValue("业态");
            				headRowTwo.getCell("业态").setValue("业态");
            				headRowThree.getCell("业态").setValue("业态");
            				table.getHeadMergeManager().mergeBlock(0, column.getColumnIndex(), 2, column.getColumnIndex());
        				}else if(excelColName.indexOf("可售面积")>=0){
        					e_colNameMap.put("可售面积", new Integer(col));
            				IColumn column=table.addColumn();
            				column.setKey("可售面积");
            				headRow.getCell("可售面积").setValue("可售面积");
            				headRowTwo.getCell("可售面积").setValue("可售面积");
            				headRowThree.getCell("可售面积").setValue("可售面积");
            				table.getHeadMergeManager().mergeBlock(0, column.getColumnIndex(), 2, column.getColumnIndex());
        				}else if(excelColName.indexOf("可售货值")>=0){
        					e_colNameMap.put("总货值", new Integer(col));
            				IColumn column=table.addColumn();
            				column.setKey("总货值");
            				headRow.getCell("总货值").setValue("总货值");
            				headRowTwo.getCell("总货值").setValue("总货值");
            				headRowThree.getCell("总货值").setValue("总货值");
            				table.getHeadMergeManager().mergeBlock(0, column.getColumnIndex(), 2, column.getColumnIndex());
            				column.getStyleAttributes().setNumberFormat("#0.00");
        				}else if(excelColName.indexOf("预售证获取时间")>=0){
        					e_colNameMap.put("预售证获取时间", new Integer(col));
        				}else if(excelColName.indexOf("已售货值")>=0){
        					e_colNameMap.put("已售货值", new Integer(col));
        				}
        			}
        		}
//        		e_colNameMap.put("未来供应", new Integer(table.getColumnCount()-1));
        		
        		IColumn kccolumn=table.addColumn();
        		kccolumn.setKey("库存");
				headRow.getCell("库存").setValue("库存");
				headRowTwo.getCell("库存").setValue("库存");
				headRowThree.getCell("库存").setValue("库存");
				table.getHeadMergeManager().mergeBlock(0, kccolumn.getColumnIndex(), 2, kccolumn.getColumnIndex());
				kccolumn.getStyleAttributes().setNumberFormat("#0.00");
				
				IColumn addcolumn=table.addColumn();
				addcolumn.setKey("未来供应");
				headRow.getCell("未来供应").setValue("未来供应");
				headRowTwo.getCell("未来供应").setValue("未来供应");
				headRowThree.getCell("未来供应").setValue("未来供应");
				table.getHeadMergeManager().mergeBlock(0, addcolumn.getColumnIndex(), 2, addcolumn.getColumnIndex());
				addcolumn.getStyleAttributes().setNumberFormat("#0.00");
				
        		Date now=FDCCommonServerHelper.getServerTimeStamp();
        		Calendar cal = Calendar.getInstance();
    			cal.setTime(now);
    			int year=cal.get(Calendar.YEAR);
    			int month=cal.get(Calendar.MONTH)+1;
    			String nowkey=year+"year"+month+"m";
    			int comYear=year+3;
    			
    			while(true){
    				if(year>comYear){
    					break;
    				}
    				String key=year+"year"+month+"m";
    				IColumn column=table.addColumn();
    				column.setKey(key+"area");
    				headRow.getCell(key+"area").setValue("供应计划");
    				headRowTwo.getCell(key+"area").setValue(year+"年"+month+"月");
    				headRowThree.getCell(key+"area").setValue("面积");
    				
    				column=table.addColumn();
    				column.setKey(key+"amount");
    				headRow.getCell(key+"amount").setValue("供应计划");
    				headRowTwo.getCell(key+"amount").setValue(year+"年"+month+"月");
    				headRowThree.getCell(key+"amount").setValue("货值");
    				
    				column.getStyleAttributes().setNumberFormat("#0.00");
    				++month;
    				if (month > 12) {
    					year += 1;
    					month = 1;
    				}
    				table.getHeadMergeManager().mergeBlock(1, table.getHeadRow(0).getCell(key+"area").getColumnIndex(), 1, table.getHeadRow(0).getCell(key+"amount").getColumnIndex());
    			}
    			int merge=table.getHeadRow(0).getCell(nowkey+"area").getColumnIndex();
    			table.getHeadMergeManager().mergeBlock(0, merge, 0, table.getColumnCount()-1);
    			
    			Map date=new HashMap();
    			String keyProject="";
    			for (int rowIndex = 4; rowIndex <= e_maxRow; rowIndex++) {
    				Integer colInt = (Integer) e_colNameMap.get("区域");
    				Variant cellRawVal = excelSheet.getCell(rowIndex, colInt.intValue(), true).getValue();
    				String city="";
    				if(cellRawVal.getValue()==null||cellRawVal.getValue().toString().trim().equals("")){
    					int curIndex=rowIndex;
    					while(true){
    						curIndex--;
    						if(excelSheet.getCell(curIndex, colInt.intValue(), true).getValue()!=null
    								&&!excelSheet.getCell(curIndex, colInt.intValue(), true).getValue().toString().trim().equals("")){
    							city=excelSheet.getCell(curIndex, colInt.intValue(), true).getValue().toString();
    							break;
    						}
    					}
    				}else{
    					city=cellRawVal.getValue().toString();
    				}
    				
    				colInt = (Integer) e_colNameMap.get("项目");
    				cellRawVal = excelSheet.getCell(rowIndex, colInt.intValue(), true).getValue();
    				String curProject="";
    				if(cellRawVal.getValue()==null||cellRawVal.getValue().toString().trim().equals("")){
    					int curIndex=rowIndex;
    					while(true){
    						curIndex--;
    						if(excelSheet.getCell(curIndex, colInt.intValue(), true).getValue()!=null
    								&&!excelSheet.getCell(curIndex, colInt.intValue(), true).getValue().toString().trim().equals("")){
    							curProject=excelSheet.getCell(curIndex, colInt.intValue(), true).getValue().toString();
    							break;
    						}
    					}
    				}else{
    					curProject=cellRawVal.getValue().toString();
    				}
    				colInt = (Integer) e_colNameMap.get("业态");
    				cellRawVal = excelSheet.getCell(rowIndex, colInt.intValue(), true).getValue();
    				if(cellRawVal.getValue()==null) continue;
    				String productType=cellRawVal.getValue().toString();
    				
    				if(keyProject.equals("")){
    					keyProject=curProject;
    				}else{
    					if(!keyProject.equals(curProject)){
        					IRow totalRow=table.addRow();
        					totalRow.getCell("项目").setValue("小计");
        					totalRow.getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_TOTAL_BG_COLOR);
        					for(int j=totalRow.getRowIndex()-1;j>=0;j--){
        						if(table.getRow(j).getCell("项目")==null) continue;
        						String comKey=table.getRow(j).getCell("项目").getValue().toString();
        						if(comKey.equals(keyProject)){
        							totalRow.getCell("区域").setValue(table.getRow(j).getCell("区域").getValue());
        							totalRow.getCell("总货值").setValue(FDCHelper.add(table.getRow(j).getCell("总货值").getValue(), totalRow.getCell("总货值").getValue()));
        							totalRow.getCell("库存").setValue(FDCHelper.add(table.getRow(j).getCell("库存").getValue(), totalRow.getCell("库存").getValue()));
        							totalRow.getCell("未来供应").setValue(FDCHelper.add(table.getRow(j).getCell("未来供应").getValue(), totalRow.getCell("未来供应").getValue()));
        							totalRow.getCell("可售面积").setValue(FDCHelper.add(table.getRow(j).getCell("可售面积").getValue(), totalRow.getCell("可售面积").getValue()));
        						}else{
        							break;
        						}
        					}
        					keyProject=curProject;
        				}
    				}
    				
    				IRow row=null;
    				if(date.containsKey(curProject+productType)){
    					row=(IRow) date.get(curProject+productType);
    					
    					Integer areaIndex = (Integer) e_colNameMap.get("可售面积");
    					Variant areavalue = excelSheet.getCell(rowIndex, areaIndex.intValue(), true).getValue();
    					row.getCell("可售面积").setValue(FDCHelper.add(areavalue.getValue(), row.getCell("可售面积").getValue()));
    					
    					Integer totalIndex = (Integer) e_colNameMap.get("总货值");
    					Variant value = excelSheet.getCell(rowIndex, totalIndex.intValue(), true).getValue();
    					row.getCell("总货值").setValue(FDCHelper.add(value.getValue(), row.getCell("总货值").getValue()));
    					
    					Integer sellIndex = (Integer) e_colNameMap.get("已售货值");
    					Variant sellvalue = excelSheet.getCell(rowIndex, sellIndex.intValue(), true).getValue();
    					row.getCell("库存").setValue(FDCHelper.add(row.getCell("库存").getValue(), FDCHelper.subtract(value.getValue(), sellvalue.getValue())));
    					
    					Integer dateIndex = (Integer) e_colNameMap.get("预售证获取时间");
    					Variant datevalue = excelSheet.getCell(rowIndex, dateIndex.intValue(), true).getValue();
    					
    					if(datevalue.getValue()!=null&&datevalue.getValue() instanceof GregorianCalendar){
			    			cal.setTime(((GregorianCalendar)datevalue.getValue()).getTime());
    			    		year=cal.get(Calendar.YEAR);
    			    		month=cal.get(Calendar.MONTH)+1;
    			    		String key=year+"year"+month+"m";
    			    		
    			    		if(row.getCell(key+"amount")!=null){
    			    			row.getCell("未来供应").setValue(FDCHelper.add(row.getCell("未来供应").getValue(), FDCHelper.subtract(value.getValue(), sellvalue.getValue())));
    			    			row.getCell(key+"amount").setValue(FDCHelper.add(row.getCell(key+"amount").getValue(), FDCHelper.subtract(value.getValue(), sellvalue.getValue())));
    			    			row.getCell(key+"area").setValue(FDCHelper.add(row.getCell(key+"area").getValue(), areavalue.getValue()));
    			    		}
    					}
    				}else{
    					row = table.addRow();
    					row.getCell("区域").setValue(city);
    					row.getCell("项目").setValue(curProject);
    					row.getCell("业态").setValue(productType);
    					
    					Integer areaIndex = (Integer) e_colNameMap.get("可售面积");
    					Variant areavalue = excelSheet.getCell(rowIndex, areaIndex.intValue(), true).getValue();
    					row.getCell("可售面积").setValue(areavalue.getValue());
    					
    					Integer totalIndex = (Integer) e_colNameMap.get("总货值");
    					Variant value = excelSheet.getCell(rowIndex, totalIndex.intValue(), true).getValue();
    					row.getCell("总货值").setValue(value.getValue());
    					
    					Integer sellIndex = (Integer) e_colNameMap.get("已售货值");
    					Variant sellvalue = excelSheet.getCell(rowIndex, sellIndex.intValue(), true).getValue();
    					row.getCell("库存").setValue(FDCHelper.subtract(value.getValue(), sellvalue.getValue()));
    					
    					Integer dateIndex = (Integer) e_colNameMap.get("预售证获取时间");
    					Variant datevalue = excelSheet.getCell(rowIndex, dateIndex.intValue(), true).getValue();
    					
    					if(datevalue.getValue()!=null&&datevalue.getValue() instanceof GregorianCalendar){
			    			cal.setTime(((GregorianCalendar)datevalue.getValue()).getTime());
    			    		year=cal.get(Calendar.YEAR);
    			    		month=cal.get(Calendar.MONTH)+1;
    			    		String key=year+"year"+month+"m";
    			    		
    			    		if(row.getCell(key+"amount")!=null){
    			    			row.getCell("未来供应").setValue(FDCHelper.subtract(value.getValue(), sellvalue.getValue()));
    			    			row.getCell(key+"amount").setValue(FDCHelper.subtract(value.getValue(), sellvalue.getValue()));
    			    			row.getCell(key+"area").setValue(areavalue.getValue());
    			    		}
    					}
    					date.put(curProject+productType, row);
    				}
        		}
    			table.getGroupManager().setGroup(true);
    			table.getColumn("区域").setGroup(true);
    			table.getColumn("区域").setMergeable(true);
     			
    			table.getColumn("项目").setGroup(true);
    			table.getColumn("项目").setMergeable(true);
			}
		}
		if(table==null){
			FDCMsgBox.showWarning(this,"楼栋跟进计划表不存在！");
			return false;
		}else{
			contEntry.getContentPane().removeAll();
			
			contEntry.setName(table.getName());
			contEntry.getContentPane().setLayout(new BorderLayout(0, 0));        
			contEntry.getContentPane().add(table, BorderLayout.CENTER);
		}
		return true;
	}
	private String getString(Object value){
		if(value==null) return "";
		if(value!=null&&value.toString().trim().equals("")){
			return "";
		}else{
			return value.toString();
		}
	}
	private void mergerTable(KDTable table,String coloum[],String mergeColoum[]){
		int merger=0;
		for(int i=0;i<table.getRowCount();i++){
			if(i>0){
				boolean isMerge=true;
				for(int j=0;j<coloum.length;j++){
					Object curRow=table.getRow(i).getCell(coloum[j]).getValue();
					Object lastRow=table.getRow(i-1).getCell(coloum[j]).getValue();
					if(!getString(curRow).equals(getString(lastRow))){
						isMerge=false;
						merger=i;
					}
				}
				if(isMerge){
					for(int j=0;j<mergeColoum.length;j++){
						table.getMergeManager().mergeBlock(merger, table.getColumnIndex(mergeColoum[j]), i, table.getColumnIndex(mergeColoum[j]));
					}
				}
			}
		}
	}
}