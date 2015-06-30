/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.Color;
import java.awt.Container;
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
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
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
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.client.CRMClientHelper;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.client.FDCTreeHelper;
import com.kingdee.eas.fdc.sellhouse.client.PrePurchaseManageListUI;
import com.kingdee.eas.fdc.sellhouse.client.PurchaseManageListUI;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.fdc.sellhouse.client.SignManageListUI;
import com.kingdee.eas.fdc.tenancy.FeesWarrantEntrysCollection;
import com.kingdee.eas.fdc.tenancy.FeesWarrantEntrysFactory;
import com.kingdee.eas.fdc.tenancy.FeesWarrantEntrysInfo;
import com.kingdee.eas.fdc.tenancy.FeesWarrantFactory;
import com.kingdee.eas.fdc.tenancy.FeesWarrantInfo;
import com.kingdee.eas.fdc.tenancy.RevDetailReportFacadeFactory;
import com.kingdee.eas.fdc.tenancy.RevDetailVoucherReportFacadeFactory;
import com.kingdee.eas.fdc.tenancy.TenancyBillInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
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
public class RevDetailVoucherReportUI extends AbstractRevDetailVoucherReportUI
{
    private static final Logger logger = CoreUIObject.getLogger(RevDetailVoucherReportUI.class);
    public RevDetailVoucherReportUI() throws Exception
    {
        super();
        tblMain.checkParsed();
        tblMain.getDataRequestManager().addDataRequestListener(this);
        tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
        enableExportExcel(tblMain);
    }
    private boolean isQuery=false;
    private boolean isOnLoad=false;
	private Container idsSet;
    protected RptParams getParamsForInit() {
		return null;
	}

	protected CommRptBaseConditionUI getQueryDialogUserPanel() throws Exception {
		return new RevDetailVoucherReportFilterUI();
	}

	protected ICommRptBase getRemoteInstance() throws BOSException {
		return RevDetailVoucherReportFacadeFactory.getRemoteInstance();
	}

	protected KDTable getTableForPrintSetting() {
		return tblMain;
	}

	protected void query() {
		if(isOnLoad) return;
		tblMain.removeColumns();
		tblMain.removeRows();
	    
		CRMClientHelper.changeTableNumberFormat(tblMain, new String[]{"buildArea","tenancyArea","dealTotal","dealPrice","roomPrice"});
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
         	         Map totalrowMap=new HashMap();
         	         String conId=null;
         	         while(rs.next()){
	                   	 if(conId!=null&&!conId.equals(rs.getString("conId"))){
	                   		IRow totalrow=tblMain.addRow();
	                   		for(int i=0;i<17;i++){
	                   			totalrow.getCell(i).setValue(tblMain.getRow(totalrow.getRowIndex()-1).getCell(i).getValue());
	                   		}
	                   		totalrow.getCell(17).setValue("合计");
	                   		totalrow.getStyleAttributes().setBackground(FDCHelper.KDTABLE_TOTAL_BG_COLOR);
	                   		totalrowMap.put(conId, totalrow);
	                   	 }
	                   	 conId=rs.getString("conId");
	                   	 
	                   	 IRow row=tblMain.addRow();
	                   	 ((KDTableInsertHandler)(new DefaultKDTableInsertHandler(rs))).setTableRowData(row, rs.toRowArray());
	                   	 rowMap.put(rs.getString("conId")+rs.getString("mdId"), row);
         	         }
         	         if(tblMain.getRowCount()>0){
        	        	 IRow lastTotalrow=tblMain.addRow();
	   	           		 for(int i=0;i<17;i++){
	   	           			 lastTotalrow.getCell(i).setValue(tblMain.getRow(lastTotalrow.getRowIndex()-1).getCell(i).getValue());
	   	           		 }
	   	           		 lastTotalrow.getCell(17).setValue("合计");
	   	           		 lastTotalrow.getStyleAttributes().setBackground(FDCHelper.KDTABLE_TOTAL_BG_COLOR);
	   	           		 totalrowMap.put(conId, lastTotalrow);
        	         }
                		
         	         int year=params.getInt("year");
         	         int month=params.getInt("month");
         	         IColumn column=tblMain.addColumn();
      	        	 column.setKey(year+"Y"+month+"M"+"isGen");
      	        	 column.setWidth(130);
      	        	 int merge=tblMain.getHeadRow(0).getCell(column.getKey()).getColumnIndex();
      	        	 tblMain.getHeadRow(0).getCell(column.getKey()).setValue(year+"-"+month);
      	        	 tblMain.getHeadRow(1).getCell(column.getKey()).setValue("是否生成应收费用汇总");
      	        	 
      	        	 column=tblMain.addColumn();
        	         column.setKey(year+"Y"+month+"M"+"appAmount");
        	         column.setWidth(70);
        	         
        	         tblMain.getHeadRow(0).getCell(column.getKey()).setValue(year+"-"+month);
        	         tblMain.getHeadRow(1).getCell(column.getKey()).setValue("应收金额");
        	         CRMClientHelper.changeTableNumberFormat(tblMain, column.getKey());
        	         
        	         column=tblMain.addColumn();
        	         column.setKey(year+"Y"+month+"M"+"feeAmount");
        	         column.setWidth(120);
        	         
        	         tblMain.getHeadRow(0).getCell(column.getKey()).setValue(year+"-"+month);
        	         tblMain.getHeadRow(1).getCell(column.getKey()).setValue("累计确认收入金额");
        	         CRMClientHelper.changeTableNumberFormat(tblMain, column.getKey());
        	         
	   	        	 tblMain.getHeadMergeManager().mergeBlock(0, merge, 0, merge+2);
         	         
	   	        	 Calendar cal = Calendar.getInstance();
	        		 cal.set(Calendar.YEAR, year);
	        		 cal.set(Calendar.MONTH, month-1);

	        		 Date curEndDate=FDCDateHelper.getSQLBegin(FDCDateHelper.getLastDayOfMonth(cal.getTime()));
	        		
         	         RptRowSet detailrs = (RptRowSet)((RptParams)result).getObject("detailrs");
        	         while(detailrs.next()){
        	        	 String key=detailrs.getString("conId")+detailrs.getString("mdId");
        	        	 
        	        	 if(rowMap.containsKey(key)){
        	        		IRow row=(IRow) rowMap.get(key);
        	        		if(row.getCell(year+"Y"+month+"M"+"appAmount")==null){
        	        			continue;
        	        		}
        	        		FilterInfo filter=new FilterInfo();
        	        		filter.getFilterItems().add(new FilterItemInfo("tenancyBill.id",detailrs.getString("conId")));
        	        		filter.getFilterItems().add(new FilterItemInfo("moneyDefine.id",detailrs.getString("mdId")));
        	        		filter.getFilterItems().add(new FilterItemInfo("appDate",curEndDate));
        	        		
        	        		if(FeesWarrantEntrysFactory.getRemoteInstance().exists(filter)){
        	        			row.getCell(year+"Y"+month+"M"+"isGen").setValue(Boolean.TRUE);
        	        		}else{
        	        			row.getCell(year+"Y"+month+"M"+"isGen").setValue(Boolean.FALSE);
        	        		}
        	        		Date startDate=(Date) row.getCell("startDate").getValue();
           	        	    Date endDate=(Date) row.getCell("endDate").getValue();
        	        		int monthDiff=getMonthDiff(startDate,endDate)+1;
        	        		BigDecimal appAmount=detailrs.getBigDecimal("appAmount");
        	        		BigDecimal feeAmount=detailrs.getBigDecimal("feeAmount");
        	        		if(monthDiff!=1){
        	        			int dayDiff=FDCDateHelper.getDiffDays(startDate, endDate);
            	        		BigDecimal avg=FDCHelper.divide(appAmount, dayDiff, 2, BigDecimal.ROUND_HALF_UP);
            	        		Date subDate=startDate;
            	        		for(int i=0;i<monthDiff;i++){
            	        			if(i>0){
            	        				subDate=FDCDateHelper.getNextMonth(subDate);
            	        			}
            	        			Date beginDate=startDate;
            	        			Date lastDate=endDate;
            	        			if(i==0){
            	        				lastDate=FDCDateHelper.getLastDayOfMonth(beginDate);
            	        			}else if(i>0&&i<monthDiff-1){
            	        				beginDate=FDCDateHelper.getFirstDayOfMonth(subDate);
            	        				lastDate=FDCDateHelper.getLastDayOfMonth(subDate);
            	        			}else if(i==monthDiff-1){
            	        				beginDate=FDCDateHelper.getFirstDayOfMonth(subDate);
            	        			}
            	        			int curDayDiff=FDCDateHelper.getDiffDays(beginDate, lastDate);
            	        			System.out.println(FDCDateHelper.formatDate(subDate));
            	        			System.out.println(FDCDateHelper.formatDate(curEndDate));
            	        			System.out.println(getMonthDiff(subDate,curEndDate));
            	        			if(getMonthDiff(subDate,curEndDate)==0){
            	        				if(i!=monthDiff-1){
            	        					appAmount=FDCHelper.multiply(avg, curDayDiff);
            	        				}else{
            	        					appAmount=FDCHelper.subtract(appAmount, FDCHelper.multiply(avg, dayDiff-curDayDiff));
            	        				}
            	        			}
            	        		}
        	        		}
        	        		row.getCell(year+"Y"+month+"M"+"appAmount").setValue(appAmount);
        	        		row.getCell(year+"Y"+month+"M"+"feeAmount").setValue(feeAmount);
        	        		if(totalrowMap.containsKey(detailrs.getString("conId"))){
        	        			IRow totalrow=(IRow) totalrowMap.get(detailrs.getString("conId"));
        	        			totalrow.getCell(year+"Y"+month+"M"+"appAmount").setValue(FDCHelper.add(totalrow.getCell(year+"Y"+month+"M"+"appAmount").getValue(), appAmount));
        	        			totalrow.getCell(year+"Y"+month+"M"+"feeAmount").setValue(FDCHelper.add(totalrow.getCell(year+"Y"+month+"M"+"feeAmount").getValue(), feeAmount));
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
	public int getMonthDiff(Date start, Date end) {
		if (start.after(end)) {
            Date t = start;
            start = end;
            end = t;
        }
		Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(start);
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(end);
        Calendar temp = Calendar.getInstance();
        temp.setTime(end);
        temp.add(Calendar.DATE, 1);

        int year = endCalendar.get(Calendar.YEAR)
                - startCalendar.get(Calendar.YEAR);
        int month = endCalendar.get(Calendar.MONTH)
                - startCalendar.get(Calendar.MONTH);
        
        if ((startCalendar.get(Calendar.DATE) == 1)
                && (temp.get(Calendar.DATE) == 1)) {
            return year * 12 + month;
        } else if ((startCalendar.get(Calendar.DATE) != 1)
                && (temp.get(Calendar.DATE) == 1)) {
            return year * 12 + month;
        } else if ((startCalendar.get(Calendar.DATE) == 1)
                && (temp.get(Calendar.DATE) != 1)) {
            return year * 12 + month;
        } else {
            return (year * 12 + month - 1) < 0 ? 0 : (year * 12 + month);
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
		this.btnGen.setIcon(EASResource.getIcon("imgTbtn_createcredence"));
		this.btnView.setIcon(EASResource.getIcon("imgTbtn_view"));
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
	protected void btnGen_actionPerformed(ActionEvent e) throws Exception {
		int[] selectRows = KDTableUtil.getSelectedRows(this.tblMain);
		if(selectRows==null || selectRows.length==0){
			FDCMsgBox.showWarning(this, EASResource.getString(FrameWorkClientUtils.strResource + "Msg_MustSelected"));
			return;
		}
		FeesWarrantInfo info=new FeesWarrantInfo();
		info.setId(BOSUuid.create(info.getBOSType()));
         
        String mdName="";
        Set conIdSet=new HashSet();
        
        int year=params.getInt("year");
        int month=params.getInt("month");
        
        Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month-1);

		Date curEndDate=FDCDateHelper.getSQLBegin(FDCDateHelper.getLastDayOfMonth(cal.getTime()));
		for (int i = 0; i < selectRows.length; i++) {
			IRow row = this.tblMain.getRow(selectRows[i]);
			
			BigDecimal amount=(BigDecimal) row.getCell(year+"Y"+month+"M"+"appAmount").getValue();
			if(amount==null||amount.compareTo(FDCHelper.ZERO)==0||row.getStyleAttributes().getBackground().equals(FDCHelper.KDTABLE_TOTAL_BG_COLOR)){
				continue;
			}
			
			SellProjectInfo sp=new SellProjectInfo();
			sp.setId(BOSUuid.read(row.getCell("spId").getValue().toString()));
			info.setSellProject(sp);
			info.setNumber(year+"年"+month+"月－"+row.getCell("conName").getValue().toString());
	        info.setName(year+"年"+month+"月－"+row.getCell("conName").getValue().toString());
	        
			FeesWarrantEntrysInfo entry=new FeesWarrantEntrysInfo();
			
			String conId=row.getCell("conId").getValue().toString();
			conIdSet.add(conId);
			if(conIdSet.size()>1){
				FDCMsgBox.showWarning(this,"请选择同一租赁合同！");
				return;
			}
			TenancyBillInfo ten=new TenancyBillInfo();
			ten.setId(BOSUuid.read(conId));
			entry.setTenancyBill(ten);
			entry.setTenancyName(row.getCell("conName").getValue().toString());
			
			RoomInfo room=new RoomInfo();
			room.setId(BOSUuid.read(row.getCell("roomId").getValue().toString()));
			entry.setRoom(room);
			
			MoneyDefineInfo md=new MoneyDefineInfo();
			md.setId(BOSUuid.read(row.getCell("mdId").getValue().toString()));
			entry.setMoneyDefine(md);
			
			FDCCustomerInfo customer=new FDCCustomerInfo();
			customer.setId(BOSUuid.read(row.getCell("customerId").getValue().toString()));
			entry.setCustomer(customer);
			
			mdName=mdName+row.getCell("moneyDefine").getValue().toString()+";";
			
			entry.setAppAmount((BigDecimal) row.getCell(year+"Y"+month+"M"+"appAmount").getValue());
			
			entry.setAppDate(curEndDate);
			
			info.getFeesWarrantEntry().add(entry);
		}
		if(info.getFeesWarrantEntry().size()>0){
			info.setNumber(info.getNumber()+"："+mdName);
	        info.setName(info.getName()+"："+mdName);
	        
			FeesWarrantFactory.getRemoteInstance().submit(info);
			FDCClientUtils.showOprtOK(this);
			this.query();
		}else{
			FDCMsgBox.showWarning(this,"无应收记录！");
		}
	}
	IUIWindow uiWindow=null;
	protected void btnView_actionPerformed(ActionEvent e) throws Exception {
		int[] selectRows = KDTableUtil.getSelectedRows(this.tblMain);
		if(selectRows==null || selectRows.length==0){
			FDCMsgBox.showWarning(this, EASResource.getString(FrameWorkClientUtils.strResource + "Msg_MustSelected"));
			return;
		}
        Set conIdSet=new HashSet();
        for (int i = 0; i < selectRows.length; i++) {
			IRow row = this.tblMain.getRow(selectRows[i]);
	        
			conIdSet.add(row.getCell("conId").getValue().toString());
			
        }
		int year=params.getInt("year");
        int month=params.getInt("month");
        
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month-1);

		Date curEndDate=FDCDateHelper.getSQLBegin(FDCDateHelper.getLastDayOfMonth(cal.getTime()));
		
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("tenancyBill.id",conIdSet,CompareType.INCLUDE));
		filter.getFilterItems().add(new FilterItemInfo("appDate",curEndDate));
		view.setFilter(filter);
		FeesWarrantEntrysCollection col=FeesWarrantEntrysFactory.getRemoteInstance().getFeesWarrantEntrysCollection(view);
		if(col.size()>0){
			Set id=new HashSet();
			for(int i=0;i<col.size();i++){
				id.add(col.get(i).getParent().getId().toString());
			}
			filter=new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("id",id,CompareType.INCLUDE));
			if(uiWindow!=null)uiWindow.close();
			UIContext uiContext = new UIContext(this);
			uiContext.put(UIContext.OWNER, this);
			uiContext.put("filter", filter);
			uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(FeesVoucherListUI.class.getName(), uiContext, null, OprtState.VIEW);
			uiWindow.show();
		}else{
			FDCMsgBox.showWarning(this,"无应收费用汇总记录！");
		}
	}
}