/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.event.*;
import org.apache.log4j.Logger;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.fdc.basedata.CostSplitStateEnum;
import com.kingdee.eas.fdc.basedata.client.FDCSplitClientHelper;
import com.kingdee.eas.fdc.finance.PaymentSplitFactory;
import com.kingdee.eas.framework.*;

/**
 * output class name
 */
public class InvalidWorkLoadSplitListUI extends AbstractInvalidWorkLoadSplitListUI
{
    private static final Logger logger = CoreUIObject.getLogger(InvalidWorkLoadSplitListUI.class);
    
    private static final String BILLSPLITSTATE = "costSplit.splitState";
    
    /**
     * output class constructor
     */
    public InvalidWorkLoadSplitListUI() throws Exception
    {
        super();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

    /**
     * output tblMain_tableClicked method
     */
    protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
        super.tblMain_tableClicked(e);
    }
 
    public void onLoad() throws Exception {
    	super.onLoad();
    	actionQuery.setEnabled(true);
    	actionQuery.setVisible(true);
    	actionDelVoucher.setEnabled(false);
    	actionDelVoucher.setVisible(false);
    	actionVoucher.setEnabled(false);
    	actionVoucher.setVisible(false);
    	actionTraceDown.setEnabled(false);
    	actionTraceDown.setVisible(false);
    	actionEdit.setVisible(false);
    	actionEdit.setEnabled(false);
    	actionRemove.setVisible(false);
    	actionRemove.setEnabled(false);
    	actionAddNew.setVisible(false);
    	actionAddNew.setEnabled(false);
    	actionAudit.setEnabled(false);
    	actionAudit.setVisible(false);
    	actionUnAudit.setEnabled(false);
    	actionUnAudit.setVisible(false);
    	
    	actionWorkFlowG.setEnabled(false);
    	actionWorkFlowG.setVisible(false);
    	menuWorkFlow.setVisible(false);
    	menuWorkFlow.setEnabled(false);
    	menuBiz.setEnabled(false);
    	menuBiz.setVisible(false);
    	actionAuditResult.setVisible(false);
    	actionAuditResult.setEnabled(false);
    	this.setSplitStateColor();
    	
    }

    protected boolean isVoucherVisible() {
    	return false;
    }
    
    protected ICoreBase getBizInterface() throws Exception {
    	return PaymentSplitFactory.getRemoteInstance();
    }
    
    /**
     * output tblMain_tableSelectChanged method
     */
    protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception
    {
        super.tblMain_tableSelectChanged(e);
    }

    /**
     * output menuItemImportData_actionPerformed method
     */
    protected void menuItemImportData_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        super.menuItemImportData_actionPerformed(e);
    }

    /**
     * output treeProject_valueChanged method
     */
    protected void treeProject_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception
    {
        super.treeProject_valueChanged(e);
    }
    
    protected String getEditUIName() {
    	return com.kingdee.eas.fdc.finance.client.WorkLoadSplitEditUI.class.
    			getName();
    }

//    protected String getSplitStateFieldName() {
//		return "costSplit.splitState";
//	}
//    
//	protected String getCostBillStateFieldName() {
//		return "billStatus";
//	}
	
	protected String getKeyFieldName() {
		// TODO 自动生成方法存根
//		 return super.getKeyFieldName();

		return "costSplit.id";
	}
	
    /**
     * output actionProjectAttachment_actionPerformed
     */
   
    
    protected void setSplitStateColor() {

		// 完全拆分工作量（绿色）、部分拆分工作量（黄色）、未拆分工作量
		String splitState;

		for (int i = 0; i < tblMain.getRowCount(); i++) {
			IRow row = tblMain.getRow(i);

			ICell cell = row.getCell(BILLSPLITSTATE);
			if (cell.getValue() == null || cell.getValue().toString().equals("")) {
				splitState = CostSplitStateEnum.NOSPLIT.toString();
				cell.setValue(splitState);
			} else {
				splitState = cell.getValue().toString();
			}
			
			if (splitState.equals(CostSplitStateEnum.ALLSPLIT.toString())) {
				row.getStyleAttributes().setBackground(FDCSplitClientHelper.COLOR_ALLSPLIT);
			} else if (splitState.equals(CostSplitStateEnum.PARTSPLIT.toString())) {
				row.getStyleAttributes().setBackground(	FDCSplitClientHelper.COLOR_PARTSPLIT);
			} else {
				row.getStyleAttributes().setBackground(FDCSplitClientHelper.COLOR_NOSPLIT);
			}

		}

	}

}