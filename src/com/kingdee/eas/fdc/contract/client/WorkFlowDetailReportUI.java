/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.eas.base.multiapprove.MetadataUtils;
import com.kingdee.eas.base.permission.client.longtime.ILongTimeTask;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.client.CRMClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.WorkFlowDetailReportFacade;
import com.kingdee.eas.fdc.contract.WorkFlowDetailReportFacadeFactory;
import com.kingdee.eas.fdc.contract.WorkFlowReportFacadeFactory;
import com.kingdee.eas.framework.report.ICommRptBase;
import com.kingdee.eas.framework.report.client.CommRptBaseConditionUI;
import com.kingdee.eas.framework.report.util.KDTableUtil;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptRowSet;
import com.kingdee.eas.framework.report.util.RptTableHeader;
import com.kingdee.eas.ma.budget.client.LongTimeDialog;
import com.kingdee.util.StringUtils;

/**
 * output class name
 */
public class WorkFlowDetailReportUI extends AbstractWorkFlowDetailReportUI
{
    private static final Logger logger = CoreUIObject.getLogger(WorkFlowDetailReportUI.class);
    public WorkFlowDetailReportUI() throws Exception
    {
        super();
        tblMain.checkParsed();
        tblMain.getDataRequestManager().addDataRequestListener(this);
        tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
        enableExportExcel(tblMain);
    }
    private boolean isQuery=false;
    protected RptParams getParamsForInit() {
		return null;
	}

	protected CommRptBaseConditionUI getQueryDialogUserPanel() throws Exception {
		return null;
	}

	protected ICommRptBase getRemoteInstance() throws BOSException {
		return WorkFlowDetailReportFacadeFactory.getRemoteInstance();
	}

	protected KDTable getTableForPrintSetting() {
		return tblMain;
	}
	public void onLoad() throws Exception {
    	setShowDialogOnLoad(false);
    	tblMain.getStyleAttributes().setLocked(true);
		super.onLoad();
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_CELL_SELECT);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		this.actionRefresh.setVisible(false);
		this.actionQuery.setVisible(false);
    }
	protected void query() {
		tblMain.removeColumns();
		tblMain.removeRows();
		
		CRMClientHelper.changeTableNumberFormat(tblMain,new String[]{"daySub","hourSub"});
		CRMClientHelper.getFootRow(tblMain,new String[]{"daySub","hourSub"});
		
		tblMain.getColumn("subject").getStyleAttributes().setFontColor(Color.BLUE);
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
                RptParams resultRpt= getRemoteInstance().query(params);
            	return resultRpt;
            }
            public void afterExec(Object result)throws Exception{
            	tblMain.setRefresh(false);
            	
            	RptParams rpt = getRemoteInstance().createTempTable(params);
                RptTableHeader header = (RptTableHeader)rpt.getObject("header");
                KDTableUtil.setHeader(header, tblMain);
                
                RptRowSet rs = (RptRowSet)((RptParams)result).getObject("rowset");
    	        KDTableUtil.insertRows(rs, 0, tblMain);
    	        tblMain.setRowCount(rs.getRowCount());
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
			String key=this.tblMain.getColumnKey(e.getColIndex());
			if(!key.equals("subject")){
				return;
			}
			IRow row=this.tblMain.getRow(e.getRowIndex());
			String bizObjID=(String)row.getCell("bizObjID").getValue();
			if(bizObjID==null){
				return;
			}
			String editUIName = "";
			if(!StringUtils.isEmpty(bizObjID))
				editUIName = MetadataUtils.getEditUIClassByBillId(bizObjID);
			if(StringUtils.isEmpty(bizObjID) || StringUtils.isEmpty(editUIName)){
				FDCMsgBox.showWarning(this, "\u6253\u5F00\u5355\u636E\u5931\u8D25\uFF0C\u627E\u4E0D\u5230\u5355\u636E\u5143\u6570\u636E\u6216\u627E\u4E0D\u5230\u5BF9\u5E94\u7684EditUI\u754C\u9762\uFF0C\u8BF7\u67E5\u770B\u8BE6\u7EC6\u4FE1\u606F");
				return;
            }else{
            	UIContext uiContext = new UIContext(this);
    			uiContext.put("ID", bizObjID);
    			uiContext.put("isFromWorkflow", new Boolean(true));
    			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(editUIName, uiContext, null, OprtState.VIEW);
    			uiWindow.show();
    			return;
            }
		}
	}
}