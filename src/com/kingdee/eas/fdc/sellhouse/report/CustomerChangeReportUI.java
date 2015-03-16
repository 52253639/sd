/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.report;

import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.base.permission.client.longtime.ILongTimeTask;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basecrm.client.CRMClientHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.market.client.EnterprisePlanEditUI;
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
public class CustomerChangeReportUI extends AbstractCustomerChangeReportUI
{
    private static final Logger logger = CoreUIObject.getLogger(CustomerChangeReportUI.class);
    private boolean isQuery=false;
    public CustomerChangeReportUI() throws Exception
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
		return new CustomerChangeReportFilterUI();
	}

	protected ICommRptBase getRemoteInstance() throws BOSException {
		return CusChangeReportFacadeFactory.getRemoteInstance();
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
		
		EnterprisePlanEditUI.mergerTable(this.tblMain, new String[]{"id"},new String[]{"sellProjectName","number","name","room"});
		CRMClientHelper.fmtDate(tblMain, new String[]{"createTime"});
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
                
                Map room=new HashMap();
    	        RptRowSet roomRowSet = (RptRowSet)((RptParams)result).getObject("roomRowSet");
    	        while(roomRowSet.next()){
    	        	if(room.containsKey(roomRowSet.getString("customerid"))){
    	        		room.put(roomRowSet.getString("customerid"), room.get(roomRowSet.getString("customerid"))+roomRowSet.getString("room")+";");
    	        	}else{
    	        		room.put(roomRowSet.getString("customerid"), roomRowSet.getString("room")+";");
    	        	}
    	        }
    	        tblMain.setRowCount(rs.getRowCount());
    	        while(rs.next()){
    	        	IRow row=tblMain.addRow();
        			((KDTableInsertHandler)(new DefaultKDTableInsertHandler(rs))).setTableRowData(row, rs.toRowArray());
        			row.getCell("room").setValue(room.get(row.getCell("id").getValue().toString()));
    	        }
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
}