/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
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
import com.kingdee.eas.fdc.contract.client.ContractBillEditUI;
import com.kingdee.eas.fdc.sellhouse.client.FDCTreeHelper;
import com.kingdee.eas.fdc.sellhouse.client.SignManageListUI;
import com.kingdee.eas.fdc.sellhouse.report.PaymentReportUI;
import com.kingdee.eas.fdc.finance.OrgUnitMonthPGReportFacadeFactory;
import com.kingdee.eas.fdc.finance.client.OrgUnitMonthPGReportFilterUI;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.report.ICommRptBase;
import com.kingdee.eas.framework.report.client.CommRptBaseConditionUI;
import com.kingdee.eas.framework.report.util.KDTableUtil;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptRowSet;
import com.kingdee.eas.framework.report.util.RptTableHeader;
import com.kingdee.eas.ma.budget.client.LongTimeDialog;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class OrgUnitMonthPGReportUI extends AbstractOrgUnitMonthPGReportUI
{
    private static final Logger logger = CoreUIObject.getLogger(OrgUnitMonthPGReportUI.class);
    public OrgUnitMonthPGReportUI() throws Exception
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
		return new OrgUnitMonthPGReportFilterUI();
	}

	protected ICommRptBase getRemoteInstance() throws BOSException {
		return OrgUnitMonthPGReportFacadeFactory.getRemoteInstance();
	}

	protected KDTable getTableForPrintSetting() {
		return tblMain;
	}

	protected void query() {
		if(isOnLoad) return;
		tblMain.removeColumns();
		tblMain.removeRows();
	    
		tblMain.getColumn("name").getStyleAttributes().setFontColor(Color.BLUE);
		CRMClientHelper.changeTableNumberFormat(tblMain, new String[]{"contractAmount","actAmount","amount"});
		CRMClientHelper.getFootRow(tblMain, new String[]{"contractAmount","actAmount","amount"});
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
             	 RptParams rpt = getRemoteInstance().createTempTable(params);
                 RptTableHeader header = (RptTableHeader)rpt.getObject("header");
                 KDTableUtil.setHeader(header, tblMain);
                 
                 RptRowSet rs = (RptRowSet)((RptParams)result).getObject("rowset");
     	         tblMain.setRowCount(rs.getRowCount()+tblMain.getRowCount());
     	         KDTableUtil.insertRows(rs, 0, tblMain);
                 
     	         tblMain.setRefresh(true);
     	         if(rs.getRowCount() > 0){
     	        	tblMain.reLayoutAndPaint();
     	         }else{
     	        	tblMain.repaint();
     	         }
     	        tblMain.getGroupManager().setGroup(true);
    			
    			tblMain.getColumn("company").setGroup(true);
    			tblMain.getColumn("company").setMergeable(true);
    			
    			tblMain.getColumn("curProject").setGroup(true);
    			tblMain.getColumn("curProject").setMergeable(true);
    			
    			tblMain.getColumn("bgNumber").setGroup(true);
    			tblMain.getColumn("bgNumber").setMergeable(true);
    			
    			tblMain.getColumn("bgName").setGroup(true);
    			tblMain.getColumn("bgName").setMergeable(true);
             }
        }
        );
        dialog.show();
	
    	isQuery=false;
	}
	private void refresh() throws Exception {
		query();
	}
	public void onLoad() throws Exception {
		isOnLoad=true;
		setShowDialogOnLoad(true);
		tblMain.getStyleAttributes().setLocked(true);
		super.onLoad();
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_CELL_SELECT);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		isOnLoad=false;
		this.refresh();
	}
	IUIWindow uiWindow=null;
	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
			if(this.tblMain.getColumnKey(e.getColIndex()).equals("name")){
				String id=(String)this.tblMain.getRow(e.getRowIndex()).getCell("contractId").getValue();
				if(id!=null){
					UIContext uiContext = new UIContext(this);
					uiContext.put(UIContext.OWNER, this);
					uiContext.put("ID", id);
					IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(ContractBillEditUI.class.getName(), uiContext, null, OprtState.VIEW);
					uiWindow.show();
				}
			}
		}
	}
}