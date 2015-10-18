/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.*;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ctrl.extendcontrols.IDataFormat;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.foot.KDTFootManager;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.base.permission.client.longtime.ILongTimeTask;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.client.CRMClientHelper;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.sellhouse.client.FDCTreeHelper;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.fdc.tenancy.RevDetailReportFacadeFactory;
import com.kingdee.eas.fdc.tenancy.TenancyAgencyFactory;
import com.kingdee.eas.fdc.tenancy.TenancyAllReportFacadeFactory;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.report.ICommRptBase;
import com.kingdee.eas.framework.report.client.CommRptBaseConditionUI;
import com.kingdee.eas.framework.report.util.DefaultKDTableInsertHandler;
import com.kingdee.eas.framework.report.util.KDTableInsertHandler;
import com.kingdee.eas.framework.report.util.KDTableUtil;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptRowSet;
import com.kingdee.eas.framework.report.util.RptTableHeader;
import com.kingdee.eas.ma.budget.client.LongTimeDialog;
import com.kingdee.eas.util.client.EASResource;

/**
 * output class name
 */
public class TenancyAllReportUI extends AbstractTenancyAllReportUI
{
    private static final Logger logger = CoreUIObject.getLogger(TenancyAllReportUI.class);
    public TenancyAllReportUI() throws Exception
    {
        super();
        tblMain.checkParsed();
        tblMain.getDataRequestManager().addDataRequestListener(this);
        tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
        
        tblRent.checkParsed();
        tblRent.setEnabled(false);
        tblRent.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
        
        tblUnRent.checkParsed();
        tblUnRent.setEnabled(false);
        tblUnRent.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
        
        tblSell.checkParsed();
        tblSell.setEnabled(false);
        tblSell.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
        
        tblUnToRent.checkParsed();
        tblUnToRent.setEnabled(false);
        tblUnToRent.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
        enableExportExcel(tblMain);
        enableExportExcel(tblRent);
        enableExportExcel(tblUnRent);
        enableExportExcel(tblSell);
        enableExportExcel(tblUnToRent);
    }
    private boolean isQuery=false;
    private boolean isOnLoad=false;
    protected RptParams getParamsForInit() {
		return null;
	}

	protected CommRptBaseConditionUI getQueryDialogUserPanel() throws Exception {
		return null;
	}

	protected ICommRptBase getRemoteInstance() throws BOSException {
		return TenancyAllReportFacadeFactory.getRemoteInstance();
	}

	protected KDTable getTableForPrintSetting() {
		return tblMain;
	}

	protected void query() {
		if(isOnLoad) return;
		tblRent.removeColumns();
		tblRent.removeRows();
		
		tblUnRent.removeColumns();
		tblUnRent.removeRows();
		
		tblSell.removeColumns();
		tblSell.removeRows();
		
		tblUnToRent.removeColumns();
		tblUnToRent.removeRows();
		
		tblMain.removeColumns();
		tblMain.removeRows();
		
		CRMClientHelper.changeTableNumberFormat(tblRent, new String[]{"buildArea","tenancyArea","dealTotal","dealPrice","roomPrice"});
		CRMClientHelper.fmtDate(tblRent, new String[]{"startDate","endDate"});
		
		tblRent.getColumn("conName").getStyleAttributes().setFontColor(Color.BLUE);
		
		mergerTable(tblRent,new String[]{"conId"},new String[]{"sellProject","build","room","buildArea","tenancyArea","conNumber","conName","customer","startDate","endDate","freeDays","dealTotal","dealPrice","roomPrice"});
		getFootRow(tblRent, new String[]{"buildArea","tenancyArea","dealTotal"});
	
		CRMClientHelper.changeTableNumberFormat(tblUnRent, new String[]{"buildArea","tenancyArea"});
		getFootRow(tblUnRent, new String[]{"buildArea","tenancyArea"});
		
		CRMClientHelper.changeTableNumberFormat(tblSell, new String[]{"buildArea","tenancyArea"});
		getFootRow(tblSell, new String[]{"buildArea","tenancyArea"});
	}
	public void tableDataRequest(KDTDataRequestEvent kdtdatarequestevent) {
		if(isQuery) return;
		isQuery=true;
		DefaultKingdeeTreeNode treeNode = (DefaultKingdeeTreeNode)this.treeMain.getLastSelectedPathComponent();
    	if(treeNode!=null){
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
            	 public Object exec()throws Exception{
                 	 RptParams resultRpt= getRemoteInstance().query(params);
                  	 return resultRpt;
                 }
                 public void afterExec(Object result)throws Exception{
                 	 RptParams rpt = getRemoteInstance().createTempTable(params);
                     RptTableHeader rentHeader = (RptTableHeader)rpt.getObject("rentHeader");
                     KDTableUtil.setHeader(rentHeader, tblRent);
                     
                     RptTableHeader unRentHeader = (RptTableHeader)rpt.getObject("unRentHeader");
                     KDTableUtil.setHeader(unRentHeader, tblUnRent);
                     
                     RptTableHeader sellHeader = (RptTableHeader)rpt.getObject("sellHeader");
                     KDTableUtil.setHeader(sellHeader, tblSell);
                     
                     RptTableHeader unToRentHeader = (RptTableHeader)rpt.getObject("unToRentHeader");
                     KDTableUtil.setHeader(unToRentHeader, tblUnToRent);
                     
                     RptTableHeader mainHeader = (RptTableHeader)rpt.getObject("mainHeader");
                     KDTableUtil.setHeader(mainHeader, tblMain);
                     
                     RptRowSet rentRS = (RptRowSet)((RptParams)result).getObject("rentRS");
         	         tblRent.setRowCount(rentRS.getRowCount());
         	         while(rentRS.next()){
         	        	 IRow row=tblRent.addRow();
	                   	 ((KDTableInsertHandler)(new DefaultKDTableInsertHandler(rentRS))).setTableRowData(row, rentRS.toRowArray());
         	         }
         	         tblRent.setRefresh(true);
         	         if(rentRS.getRowCount() > 0){
         	        	tblRent.reLayoutAndPaint();
         	         }else{
         	        	tblRent.repaint();
         	         }
         	         
         	         RptRowSet unRentRS = (RptRowSet)((RptParams)result).getObject("unRentRS");
        	         tblUnRent.setRowCount(unRentRS.getRowCount());
        	         while(unRentRS.next()){
        	        	 IRow row=tblUnRent.addRow();
	                   	 ((KDTableInsertHandler)(new DefaultKDTableInsertHandler(unRentRS))).setTableRowData(row, unRentRS.toRowArray());
        	         }
        	         tblUnRent.setRefresh(true);
        	         if(unRentRS.getRowCount() > 0){
        	        	 tblUnRent.reLayoutAndPaint();
        	         }else{
        	        	 tblUnRent.repaint();
        	         }
        	         
        	         RptRowSet sellRS = (RptRowSet)((RptParams)result).getObject("sellRS");
        	         tblSell.setRowCount(sellRS.getRowCount());
        	         while(sellRS.next()){
        	        	 IRow row=tblSell.addRow();
	                   	 ((KDTableInsertHandler)(new DefaultKDTableInsertHandler(sellRS))).setTableRowData(row, sellRS.toRowArray());
        	         }
        	         tblSell.setRefresh(true);
        	         if(sellRS.getRowCount() > 0){
        	        	 tblSell.reLayoutAndPaint();
        	         }else{
        	        	 tblSell.repaint();
        	         }
        	         BigDecimal buildArea=FDCHelper.ZERO;
        	         BigDecimal tenArea=FDCHelper.ZERO;
        	         RptRowSet buildAreaRS = (RptRowSet)((RptParams)result).getObject("buildAreaRS");
        	         while(buildAreaRS.next()){
        	        	 buildArea=buildAreaRS.getBigDecimal("buildArea");
        	        	 tenArea=buildAreaRS.getBigDecimal("tenArea");
        	         }
        	         
        	         BigDecimal rentArea=FDCHelper.ZERO;
        	         RptRowSet rentAreaRS = (RptRowSet)((RptParams)result).getObject("rentAreaRS");
        	         while(rentAreaRS.next()){
        	        	 rentArea=rentAreaRS.getBigDecimal("tenArea");
        	         }
        	         
        	         BigDecimal sellArea=FDCHelper.ZERO;
        	         RptRowSet sellAreaRS = (RptRowSet)((RptParams)result).getObject("sellAreaRS");
        	         while(sellAreaRS.next()){
        	        	 sellArea=sellAreaRS.getBigDecimal("sellArea");
        	         }
        	         tblMain.getColumn("type").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.CENTER);
        	         tblMain.getColumn("value").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.CENTER);
    	        	 IRow row=tblMain.addRow();
    	        	 row.setHeight(50);
    	        	 row.getCell("type").setValue("建筑面积：");
    	        	 row.getCell("value").setValue(buildArea);
    	        	 row.getCell("value").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
    	        	
    	        	 row=tblMain.addRow();
    	        	 row.setHeight(50);
    	        	 row.getCell("type").setValue("不可租面积：");
    	        	 row.getCell("value").setValue(FDCHelper.subtract(buildArea, tenArea));
    	        	 row.getCell("value").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");

    	        	 row=tblMain.addRow();
    	        	 row.setHeight(50);
    	        	 row.getCell("type").setValue("可租面积：");
    	        	 row.getCell("value").setValue(tenArea);
    	        	 row.getCell("value").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
    	        	 
    	        	 row=tblMain.addRow();
    	        	 row.setHeight(50);
    	        	 row.getCell("type").setValue("已租面积：");
    	        	 row.getCell("value").setValue(rentArea);
    	        	 row.getCell("value").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
    	        	 
    	        	 row=tblMain.addRow();
    	        	 row.setHeight(50);
    	        	 row.getCell("type").setValue("已售面积：");
    	        	 row.getCell("value").setValue(sellArea);
    	        	 row.getCell("value").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
    	        	 
    	        	 row=tblMain.addRow();
    	        	 row.setHeight(50);
    	        	 row.getCell("type").setValue("空置面积：");
    	        	 row.getCell("value").setValue(FDCHelper.subtract(tenArea, rentArea));
    	        	 row.getCell("value").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
    	        	 
    	        	 row=tblMain.addRow();
    	        	 row.setHeight(50);
    	        	 row.getCell("type").setValue("预留面积：");
    	        	 
    	        	 row=tblMain.addRow();
    	        	 row.setHeight(50);
    	        	 row.getCell("type").setValue("出租率：");
    	        	 if(tenArea!=null&&tenArea.compareTo(FDCHelper.ZERO)!=0){
    	        		 row.getCell("value").setValue(FDCHelper.divide(FDCHelper.multiply(rentArea,new BigDecimal(100)),tenArea,2, BigDecimal.ROUND_HALF_UP)+"%");
    	        	 }else{
    	        		 row.getCell("value").setValue(FDCHelper.ZERO+"%");
    	        	 }
        	         tblMain.setRefresh(true);
        	         if(buildAreaRS.getRowCount() > 0){
        	        	 tblMain.reLayoutAndPaint();
        	         }else{
        	        	 tblMain.repaint();
        	         }
                 }
            }
            );
            dialog.show();
    	}
    	isQuery=false;
	}
	public void getFootRow(KDTable tblMain,String[] columnName){
		IRow footRow = null;
        KDTFootManager footRowManager = tblMain.getFootManager();
        if(footRowManager == null)
        {
            String total = EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_Total");
            footRowManager = new KDTFootManager(tblMain);
            footRowManager.addFootView();
            tblMain.setFootManager(footRowManager);
            footRow = footRowManager.addFootRow(0);
            footRow.getStyleAttributes().setHorizontalAlign(com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment.getAlignment("right"));
            tblMain.getIndexColumn().setWidthAdjustMode((short)1);
            tblMain.getIndexColumn().setWidth(30);
            footRowManager.addIndexText(0, total);
        } else
        {
            footRow = footRowManager.getFootRow(0);
        }
        int columnCount = tblMain.getColumnCount();
        for(int c = 0; c < columnCount; c++)
        {
            String fieldName = tblMain.getColumn(c).getKey();
            for(int i = 0; i < columnName.length; i++)
            {
                String colName = (String)columnName[i];
                if(colName.equalsIgnoreCase(fieldName))
                {
                    ICell cell = footRow.getCell(c);
                    cell.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
                    cell.getStyleAttributes().setHorizontalAlign(com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment.getAlignment("right"));
                    cell.getStyleAttributes().setFontColor(java.awt.Color.BLACK);
                    cell.setValue(getColumnValueSum(tblMain,colName));
                }
            }

        }
        footRow.getStyleAttributes().setBackground(new java.awt.Color(246, 246, 191));
	}
	public static BigDecimal getColumnValueSum(KDTable table,String columnName) {
    	BigDecimal sum = new BigDecimal(0);
    	Set id=new HashSet();
    	if(table.getColumn("conId")!=null){
    		for(int i=0;i<table.getRowCount();i++){
            	if(table.getRow(i).getCell(columnName).getValue()!=null&&!id.contains(table.getRow(i).getCell("conId").getValue().toString())){
            		if( table.getRow(i).getCell(columnName).getValue() instanceof BigDecimal)
                		sum = sum.add((BigDecimal)table.getRow(i).getCell(columnName).getValue());
                	else if(table.getRow(i).getCell(columnName).getValue() instanceof String){
                		String value = (String)table.getRow(i).getCell(columnName).getValue();
                		if(value.indexOf("零")==-1 && value.indexOf("[]")==-1){
                			value = value.replaceAll(",", "");
                    		sum = sum.add(new BigDecimal(value));
                		}
                	}
                	else if(table.getRow(i).getCell(columnName).getValue() instanceof Integer){
                		String value = table.getRow(i).getCell(columnName).getValue().toString();
                		sum = sum.add(new BigDecimal(value));
                	}
            		id.add(table.getRow(i).getCell("conId").getValue().toString());
            	}
            }
    	}else{
    		for(int i=0;i<table.getRowCount();i++){
            	if(table.getRow(i).getCell(columnName).getValue()!=null){
            		if( table.getRow(i).getCell(columnName).getValue() instanceof BigDecimal)
                		sum = sum.add((BigDecimal)table.getRow(i).getCell(columnName).getValue());
                	else if(table.getRow(i).getCell(columnName).getValue() instanceof String){
                		String value = (String)table.getRow(i).getCell(columnName).getValue();
                		if(value.indexOf("零")==-1 && value.indexOf("[]")==-1){
                			value = value.replaceAll(",", "");
                    		sum = sum.add(new BigDecimal(value));
                		}
                	}
                	else if(table.getRow(i).getCell(columnName).getValue() instanceof Integer){
                		String value = table.getRow(i).getCell(columnName).getValue().toString();
                		sum = sum.add(new BigDecimal(value));
                	}
            	}
            }
    	}
        return sum;
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
	private String getString(Object value){
		if(value==null) return "";
		if(value!=null&&value.toString().trim().equals("")){
			return "";
		}else{
			return value.toString();
		}
	}
	protected void buildTree() throws Exception{
		this.treeMain.setModel(SHEHelper.getBuildingTree(this.actionOnLoad,null));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
		this.treeMain.setSelectionRow(0);
	}
	protected DefaultKingdeeTreeNode getSelectedTreeNode(KDTree selectTree) {
		if (selectTree.getLastSelectedPathComponent() != null) {
			DefaultKingdeeTreeNode treeNode = (DefaultKingdeeTreeNode) selectTree.getLastSelectedPathComponent();
			return treeNode;
		}
		return null;
	}
	private void refresh() throws Exception {
		DefaultKingdeeTreeNode treeNode = (DefaultKingdeeTreeNode)treeMain.getLastSelectedPathComponent();
		if(treeNode!=null){
			String allSpIdStr = FDCTreeHelper.getStringFromSet(FDCTreeHelper.getAllObjectIdMap(treeNode, "Building").keySet());
			params.setObject("building", allSpIdStr);
			query();
		}
	}
	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
		this.refresh();
	}
	public void onLoad() throws Exception {
		isOnLoad=true;
		setShowDialogOnLoad(false);
		tblMain.getStyleAttributes().setLocked(true);
		super.onLoad();
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_CELL_SELECT);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		this.actionQuery.setVisible(false);
		buildTree();
		isOnLoad=false;
		this.refresh();
	}
	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
			if(this.tblMain.getColumn(e.getColIndex()).getKey().equals("conName")){
				String conId=this.tblMain.getRow(e.getRowIndex()).getCell("conId").getValue().toString();
				UIContext uiContext = new UIContext(this);
				uiContext.put(UIContext.ID, conId);
				IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(TenancyBillEditUI.class.getName(), uiContext, null, OprtState.VIEW);
				uiWindow.show();
			}
		}
	}
}