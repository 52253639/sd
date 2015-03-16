/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.report;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.*;
import java.math.BigDecimal;
import java.util.Date;

import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.base.permission.client.longtime.ILongTimeTask;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.client.CRMClientHelper;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.market.client.EnterprisePlanEditUI;
import com.kingdee.eas.fdc.merch.common.KDTableHelper;
import com.kingdee.eas.fdc.sellhouse.CalcTypeEnum;
import com.kingdee.eas.fdc.sellhouse.SellTypeEnum;
import com.kingdee.eas.fdc.sellhouse.TransactionStateEnum;
import com.kingdee.eas.fdc.sellhouse.client.PurchaseManageListUI;
import com.kingdee.eas.fdc.sellhouse.client.SignManageListUI;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.report.ICommRptBase;
import com.kingdee.eas.framework.report.client.CommRptBaseConditionUI;
import com.kingdee.eas.framework.report.util.KDTableUtil;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptRowSet;
import com.kingdee.eas.framework.report.util.RptTableHeader;
import com.kingdee.eas.ma.budget.client.LongTimeDialog;

/**
 * output class name
 */
public class SaleManReportUI extends AbstractSaleManReportUI
{
    private static final Logger logger = CoreUIObject.getLogger(SaleManReportUI.class);
    private boolean isQuery=false;
    public SaleManReportUI() throws Exception
    {
        super();
        tblMain.checkParsed();
        tblMain.getDataRequestManager().addDataRequestListener(this);
        tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
        enableExportExcel(tblMain);
    }


    protected RptParams getParamsForInit() {
		return null;
	}

	protected CommRptBaseConditionUI getQueryDialogUserPanel() throws Exception {
		return new SaleManReportFilterUI();
	}

	protected ICommRptBase getRemoteInstance() throws BOSException {
		return SaleManReportFacadeFactory.getRemoteInstance();
	}

	protected KDTable getTableForPrintSetting() {
		return tblMain;
	}
	public void onLoad() throws Exception {
    	setShowDialogOnLoad(true);
    	tblMain.getStyleAttributes().setLocked(true);
		super.onLoad();
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_CELL_SELECT);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
    }
	protected void query() {
		tblMain.removeColumns();
		tblMain.removeRows();
		CRMClientHelper.changeTableNumberFormat(tblMain, new String[]{"purBuildArea","purRoomArea","purAccount","buildArea","roomArea","account","buildPrice","roomPrice","revAccount","revAccountRate"});
	
		String[] fields=new String[tblMain.getColumnCount()];
		for(int i=0;i<tblMain.getColumnCount();i++){
			fields[i]=tblMain.getColumnKey(i);
		}
		KDTableHelper.setSortedColumn(tblMain,fields);
		
		CRMClientHelper.getFootRow(tblMain, new String[]{"tel","visit","purAmount","purBuildArea","purRoomArea","purAccount","amount","buildArea","roomArea","account","revAccount"});
		BigDecimal roomArea=tblMain.getFootRow(0).getCell("roomArea").getValue()==null?FDCHelper.ZERO:(BigDecimal)tblMain.getFootRow(0).getCell("roomArea").getValue();
		BigDecimal buildArea=tblMain.getFootRow(0).getCell("buildArea").getValue()==null?FDCHelper.ZERO:(BigDecimal)tblMain.getFootRow(0).getCell("buildArea").getValue();
		BigDecimal account=tblMain.getFootRow(0).getCell("account").getValue()==null?FDCHelper.ZERO:(BigDecimal)tblMain.getFootRow(0).getCell("account").getValue();
		BigDecimal revAccount=tblMain.getFootRow(0).getCell("revAccount").getValue()==null?FDCHelper.ZERO:(BigDecimal)tblMain.getFootRow(0).getCell("revAccount").getValue();
		tblMain.getFootRow(0).getCell("buildPrice").setValue(buildArea.compareTo(FDCHelper.ZERO)==0?FDCHelper.ZERO:account.divide(buildArea, 2, BigDecimal.ROUND_HALF_UP));
		tblMain.getFootRow(0).getCell("roomPrice").setValue(roomArea.compareTo(FDCHelper.ZERO)==0?FDCHelper.ZERO:account.divide(roomArea, 2, BigDecimal.ROUND_HALF_UP));
		tblMain.getFootRow(0).getCell("revAccountRate").setValue(account.compareTo(FDCHelper.ZERO)==0?FDCHelper.ZERO:revAccount.divide(account, 2, BigDecimal.ROUND_HALF_UP));
		
		tblMain.getColumn("amount").getStyleAttributes().setFontColor(Color.BLUE);
	}
	protected void mergerTable(KDTable table,String coloum[],String mergeColoum[]){
		int merger=0;
		for(int i=0;i<table.getRowCount();i++){
			if(i>0){
				boolean isMerge=true;
				for(int j=0;j<coloum.length;j++){
					Object curRow=table.getRow(i).getCell(coloum[j]).getValue();
					Object lastRow=table.getRow(i-1).getCell(coloum[j]).getValue();
					if(getString(curRow).equals("")||getString(lastRow).equals("")||!getString(curRow).equals(getString(lastRow))){
						isMerge=false;
						merger=i;
					}
				}
				if(isMerge){
					for(int j=0;j<mergeColoum.length;j++){
						table.getMergeManager().mergeBlock(merger, table.getColumnIndex(mergeColoum[j]), i, table.getColumnIndex(mergeColoum[j]));
					}
					table.getRow(i).getCell("tel").setValue(null);
					table.getRow(i).getCell("visit").setValue(null);
				}
			}
		}
	}
	private static String getString(Object value){
		if(value==null) return "";
		if(value!=null&&value.toString().trim().equals("")){
			return "";
		}else{
			return value.toString();
		}
	}
	public void tableDataRequest(KDTDataRequestEvent kdtdatarequestevent) {
		if(isQuery) return;
		isQuery=true;
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
            	RptParams pp=(RptParams)params.clone();
            	pp.setObject("tree", null);
                RptParams resultRpt= getRemoteInstance().query(pp);
            	return resultRpt;
            }
            public void afterExec(Object result)throws Exception{
            	tblMain.setRefresh(false);
            	
            	RptParams pp=(RptParams)params.clone();
            	pp.setObject("tree", null);
            	
            	RptParams rpt = getRemoteInstance().createTempTable(pp);
                RptTableHeader header = (RptTableHeader)rpt.getObject("header");
                KDTableUtil.setHeader(header, tblMain);
                
    	        RptRowSet rs = (RptRowSet)((RptParams)result).getObject("rowset");
    	        tblMain.setRowCount(rs.getRowCount());
    	        KDTableUtil.insertRows(rs, 0, tblMain);
    	        if(rs.getRowCount() > 0){
    	        	tblMain.reLayoutAndPaint();
    	        }else{
    	        	tblMain.repaint();
    	        }
    	        tblMain.setRefresh(true);
            }
        }
        );
        dialog.show();
        isQuery=false;
	}
	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
			Object value=this.tblMain.getRow(e.getRowIndex()).getCell(e.getColIndex()).getValue();
			if(value==null
					||(value!=null&&value instanceof BigDecimal
							&&((BigDecimal)value).compareTo(FDCHelper.ZERO)<=0)){
				return;
			}
			Date fromDate = (Date)params.getObject("fromDate");
	    	Date toDate =   (Date)params.getObject("toDate");
	    	
	     	String user=this.tblMain.getRow(e.getRowIndex()).getCell("id").getValue().toString();
	     	String sellProject=this.tblMain.getRow(e.getRowIndex()).getCell("sellProjectId").getValue().toString();
			if(this.tblMain.getColumn(e.getColIndex()).getKey().equals("amount")){
				StringBuffer sb = new StringBuffer();
				sb.append(" select sign.fid from t_she_signManage sign left join t_she_sellProject sp on sp.fid=sign.fsellProjectid left join t_she_sellProject psp on psp.fid=sp.fparentid");
				sb.append(" left join t_pm_user saleMan on sign.fsalesmanid=saleMan.fid");
		    	sb.append(" where sign.fbizState in('SignApple','SignAudit')");
		    	sb.append(" and saleMan.ftype in('20','50')");
				if(sellProject!=null){
		    		sb.append(" and (case when psp.fid is null then sp.fid else psp.fid end) ='"+sellProject+"'");
		    	}
		    	if(fromDate!=null){
					sb.append(" and sign.fbusAdscriptionDate>={ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}");
				}
				if(toDate!=null){
					sb.append(" and sign.fbusAdscriptionDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
				}
		    	if(user!=null){
		    		sb.append(" and saleMan.fid ='"+user.toString()+"'");
		    	}
		    	
		    	FilterInfo filter=new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("id",sb.toString(),CompareType.INNER));
				
				UIContext uiContext = new UIContext(this);
				uiContext.put(UIContext.OWNER, this);
				uiContext.put("filter", filter);
				IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(SignManageListUI.class.getName(), uiContext, null, OprtState.VIEW);
				uiWindow.show();
			}
		}
	}
}