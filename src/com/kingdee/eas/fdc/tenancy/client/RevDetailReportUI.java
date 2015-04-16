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
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.base.permission.client.longtime.ILongTimeTask;
import com.kingdee.eas.basedata.org.NewOrgUtils;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgViewType;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.client.CRMClientHelper;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.sellhouse.client.CustomerEditUI;
import com.kingdee.eas.fdc.sellhouse.client.FDCTreeHelper;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.fdc.sellhouse.client.SignManageListUI;
import com.kingdee.eas.fdc.sellhouse.report.PaymentReportUI;
import com.kingdee.eas.fdc.sellhouse.report.SaleScheduleReportFacadeFactory;
import com.kingdee.eas.fdc.sellhouse.report.SaleScheduleReportFilterUI;
import com.kingdee.eas.fdc.tenancy.RevDetailReportFacadeFactory;
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
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class RevDetailReportUI extends AbstractRevDetailReportUI
{
    private static final Logger logger = CoreUIObject.getLogger(RevDetailReportUI.class);
    public RevDetailReportUI() throws Exception
    {
        super();
        tblMain.checkParsed();
        tblMain.getDataRequestManager().addDataRequestListener(this);
        tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
        enableExportExcel(tblMain);
    }
    private boolean isQuery=false;
    private boolean isOnLoad=false;
    protected RptParams getParamsForInit() {
		return null;
	}

	protected CommRptBaseConditionUI getQueryDialogUserPanel() throws Exception {
		return new RevDetailReportFilterUI();
	}

	protected ICommRptBase getRemoteInstance() throws BOSException {
		return RevDetailReportFacadeFactory.getRemoteInstance();
	}

	protected KDTable getTableForPrintSetting() {
		return tblMain;
	}

	protected void query() {
		if(isOnLoad) return;
		tblMain.removeColumns();
		tblMain.removeRows();
	    
		CRMClientHelper.changeTableNumberFormat(tblMain, new String[]{"buildArea","tenancyArea","dealTotal","dealPrice","roomPrice","appAmount","invoiceAmount","actRevAmount"});
		CRMClientHelper.fmtDate(tblMain, new String[]{"startDate","endDate"});
		
		tblMain.getColumn("conName").getStyleAttributes().setFontColor(Color.BLUE);
		    
		mergerTable(tblMain,new String[]{"conId"},new String[]{"sellProject","build","room","buildArea","tenancyArea","conNumber","conName","customer","startDate","endDate","freeDays","dealTotal","dealPrice","roomPrice"});
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
                     RptTableHeader header = (RptTableHeader)rpt.getObject("header");
                     KDTableUtil.setHeader(header, tblMain);
                     
                     RptRowSet rs = (RptRowSet)((RptParams)result).getObject("rs");
         	         tblMain.setRowCount(rs.getRowCount());
         	         Map rowMap=new HashMap();
         	         while(rs.next()){
         	        	 IRow row=tblMain.addRow();
	                   	 ((KDTableInsertHandler)(new DefaultKDTableInsertHandler(rs))).setTableRowData(row, rs.toRowArray());
	                   	 
	                   	 rowMap.put(rs.getString("conId")+rs.getString("mdId"), row);
         	         }
         	         
         	         RptRowSet appdaters = (RptRowSet)((RptParams)result).getObject("appdaters");
         	         Date maxAppDate=null;
         	         Date minAppDate=null;
         	         while(appdaters.next()){
         	        	maxAppDate=(Date) appdaters.getObject("maxAppDate");
         	        	minAppDate=(Date) appdaters.getObject("minAppDate");
         	         }
         	         Calendar cal = Calendar.getInstance();
         	         Date addDate=minAppDate;
         	         
         	         if(maxAppDate!=null||minAppDate!=null){
         	        	for(int i=0;i<getMonthDiff(minAppDate,maxAppDate)+1;i++){
            	        	 if(i>0){
            	        		addDate=FDCDateHelper.getNextMonth(addDate);
            	        	 }
            	        	 cal.setTime(addDate);

            	        	 int year  = cal.get(Calendar.YEAR);
            	        	 int month = cal.get(Calendar.MONTH)+1;
           	        	 
            	        	 IColumn column=tblMain.addColumn();
            	        	 column.setKey(year+"Y"+month+"M"+"appAmount");
            	        	 column.setWidth(70);
            	        	 int merge=tblMain.getHeadRow(0).getCell(column.getKey()).getColumnIndex();
            	        	 
            	        	 tblMain.getHeadRow(0).getCell(column.getKey()).setValue(year+"-"+month);
            	        	 tblMain.getHeadRow(1).getCell(column.getKey()).setValue("Ӧ�ս��");
            	        	 CRMClientHelper.changeTableNumberFormat(tblMain, column.getKey());
            	        	 
            	        	 column=tblMain.addColumn();
	           	        	 column.setKey(year+"Y"+month+"M"+"invoiceAmount");
	           	        	 column.setWidth(70);
	           	        	 
	           	        	 tblMain.getHeadRow(0).getCell(column.getKey()).setValue(year+"-"+month);
	           	        	 tblMain.getHeadRow(1).getCell(column.getKey()).setValue("Ʊ�ݽ��");
	           	        	 CRMClientHelper.changeTableNumberFormat(tblMain, column.getKey());
	           	        	 
	           	        	 column=tblMain.addColumn();
            	        	 column.setKey(year+"Y"+month+"M"+"actRevAmount");
            	        	 column.setWidth(70);
            	        	
            	        	 tblMain.getHeadRow(0).getCell(column.getKey()).setValue(year+"-"+month);
            	        	 tblMain.getHeadRow(1).getCell(column.getKey()).setValue("ʵ�ս��");
            	        	 CRMClientHelper.changeTableNumberFormat(tblMain, column.getKey());
            	        	 
            	        	 tblMain.getHeadMergeManager().mergeBlock(0, merge, 0, merge+2);
            	         }
         	         }
         	         
         	         RptRowSet detailrs = (RptRowSet)((RptParams)result).getObject("detailrs");
        	         while(detailrs.next()){
        	        	 String key=detailrs.getString("conId")+detailrs.getString("mdId");
        	        	 if(rowMap.containsKey(key)){
        	        		IRow row=(IRow) rowMap.get(key);
        	        		int year=detailrs.getInt("appYear");
        	        		int month=detailrs.getInt("appMonth");
        	        		if(row.getCell(year+"Y"+month+"M"+"appAmount")==null){
        	        			continue;
        	        		}
        	        		row.getCell(year+"Y"+month+"M"+"appAmount").setValue(detailrs.getBigDecimal("appAmount"));
        	        		row.getCell(year+"Y"+month+"M"+"invoiceAmount").setValue(detailrs.getBigDecimal("invoiceAmount"));
        	        		row.getCell(year+"Y"+month+"M"+"actRevAmount").setValue(detailrs.getBigDecimal("actRevAmount"));
        	        		
        	        		row.getCell("appAmount").setValue(FDCHelper.add(row.getCell("appAmount").getValue(), detailrs.getBigDecimal("appAmount")));
        	        		row.getCell("invoiceAmount").setValue(FDCHelper.add(row.getCell("invoiceAmount").getValue(), detailrs.getBigDecimal("invoiceAmount")));
        	        		row.getCell("actRevAmount").setValue(FDCHelper.add(row.getCell("actRevAmount").getValue(), detailrs.getBigDecimal("actRevAmount")));
        	        		
        	        		if(detailrs.getBigDecimal("appAmount").compareTo(detailrs.getBigDecimal("actRevAmount"))==0){
        	        			row.getCell(year+"Y"+month+"M"+"appAmount").getStyleAttributes().setBackground(Color.GREEN);
        	        			row.getCell(year+"Y"+month+"M"+"invoiceAmount").getStyleAttributes().setBackground(Color.GREEN);
        	        			row.getCell(year+"Y"+month+"M"+"actRevAmount").getStyleAttributes().setBackground(Color.GREEN);
        	        		}
        	        		if(detailrs.getBigDecimal("appAmount").compareTo(detailrs.getBigDecimal("actRevAmount"))>0){
        	        			row.getCell(year+"Y"+month+"M"+"appAmount").getStyleAttributes().setBackground(Color.YELLOW);
        	        			row.getCell(year+"Y"+month+"M"+"invoiceAmount").getStyleAttributes().setBackground(Color.YELLOW);
        	        			row.getCell(year+"Y"+month+"M"+"actRevAmount").getStyleAttributes().setBackground(Color.YELLOW);
        	        		}
        	        		if(detailrs.getBigDecimal("appAmount").compareTo(FDCHelper.ZERO)>0
        	        				&&detailrs.getBigDecimal("actRevAmount").compareTo(FDCHelper.ZERO)==0){
        	        			row.getCell(year+"Y"+month+"M"+"appAmount").getStyleAttributes().setBackground(Color.RED);
        	        			row.getCell(year+"Y"+month+"M"+"invoiceAmount").getStyleAttributes().setBackground(Color.RED);
        	        			row.getCell(year+"Y"+month+"M"+"actRevAmount").getStyleAttributes().setBackground(Color.RED);
        	        		}
        	        	 }
        	         }
        	         
         	         tblMain.setRefresh(true);
         	         if(rs.getRowCount() > 0){
         	        	tblMain.reLayoutAndPaint();
         	         }else{
         	        	tblMain.repaint();
         	         }
         	        tblMain.getGroupManager().setGroup(true);
                 }
            }
            );
            dialog.show();
    	}
    	isQuery=false;
	}
	public int getMonthDiff(Date startDate, Date endDate) {
        int monthday;

        Calendar starCal = Calendar.getInstance();
        starCal.setTime(startDate);

        int sYear  = starCal.get(Calendar.YEAR);
        int sMonth = starCal.get(Calendar.MONTH)+1;
        int sDay   = starCal.get(Calendar.DAY_OF_MONTH);

        Calendar endCal = Calendar.getInstance();
        endCal.setTime(endDate);
        int eYear  = endCal.get(Calendar.YEAR);
        int eMonth = endCal.get(Calendar.MONTH)+1;
        int eDay   = endCal.get(Calendar.DAY_OF_MONTH);

        monthday = ((eYear - sYear) * 12 + (eMonth - sMonth));
        
        if (sDay < eDay) {
            monthday = monthday + 1;
        }
        return monthday;
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
		this.treeMain.setModel(SHEHelper.getSellProjectTree(this.actionOnLoad,MoneySysTypeEnum.TenancySys));
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
			String allSpIdStr = FDCTreeHelper.getStringFromSet(FDCTreeHelper.getAllObjectIdMap(treeNode, "SellProject").keySet());
			params.setObject("sellProject", allSpIdStr);
			query();
		}
	}
	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
		this.refresh();
	}
	public void onLoad() throws Exception {
		isOnLoad=true;
		setShowDialogOnLoad(true);
		tblMain.getStyleAttributes().setLocked(true);
		super.onLoad();
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_CELL_SELECT);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
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