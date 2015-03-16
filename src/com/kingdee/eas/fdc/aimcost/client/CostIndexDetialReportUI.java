/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.client;

import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.*;
import java.math.BigDecimal;

import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.base.permission.client.longtime.ILongTimeTask;
import com.kingdee.eas.fdc.aimcost.ContractPhaseEnum;
import com.kingdee.eas.fdc.aimcost.CostIndexDetialReportFacadeFactory;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.market.client.EnterprisePlanEditUI;
import com.kingdee.eas.fdc.sellhouse.PeriodEnum;
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
public class CostIndexDetialReportUI extends AbstractCostIndexDetialReportUI
{
    private static final Logger logger = CoreUIObject.getLogger(CostIndexDetialReportUI.class);
    private boolean isQuery=false;
    public CostIndexDetialReportUI() throws Exception
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
		return new CostIndexDetialReportFilterUI(this,this.actionOnLoad);
	}
	protected ICommRptBase getRemoteInstance() throws BOSException {
		return CostIndexDetialReportFacadeFactory.getRemoteInstance();
	}
	protected KDTable getTableForPrintSetting() {
		return this.tblMain;
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
		
		tblMain.getColumn("contractPhase").setRenderer(new ObjectValueRender(){
			public String getText(Object obj) {
				if(obj !=null){
					return ContractPhaseEnum.getEnum(obj.toString()).getAlias();
				}else{
					return null;
				}
			}
		});
		
		FDCHelper.formatTableDate(tblMain,"conAuditTime");
	}
	public void tableDataRequest(KDTDataRequestEvent arg0) {
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
    	        tblMain.setRowCount(rs.getRowCount());
    	        KDTableUtil.insertRows(rs, 0, tblMain);
    	        if(rs.getRowCount() > 0){
    	        	tblMain.reLayoutAndPaint();
    	        }else{
    	        	tblMain.repaint();
    	        }
    	        tblMain.getGroupManager().setGroup(true);
    			
    			tblMain.getColumn("proName").setGroup(true);
    			tblMain.getColumn("proName").setMergeable(true);
    			
    			tblMain.getColumn("contractPhase").setGroup(true);
    			tblMain.getColumn("contractPhase").setMergeable(true);
    			
    			tblMain.getColumn("conName").setGroup(true);
    			tblMain.getColumn("conName").setMergeable(true);
    			
    			tblMain.getColumn("conNumber").setGroup(true);
    			tblMain.getColumn("conNumber").setMergeable(true);
    			
    			tblMain.getColumn("conAuditTime").setGroup(true);
    			tblMain.getColumn("conAuditTime").setMergeable(true);
    			
    			for(int i=0;i<tblMain.getColumnCount();i++){
    				String head=tblMain.getHeadRow(0).getCell(i).getValue().toString();
    				if(head.equals("业态")||head.equals("楼号")||head.equals("工作内容")||head.equals("业态说明")){
    					tblMain.getColumn(i).setGroup(true);
    					tblMain.getColumn(i).setMergeable(true);
    				}
    			}
    	        tblMain.setRefresh(true);
            }
        }
        );
        dialog.show();
        isQuery=false;
	}
}