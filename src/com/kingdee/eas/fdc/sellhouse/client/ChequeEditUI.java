/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.sellhouse.ChequeFactory;
import com.kingdee.eas.fdc.sellhouse.ChequeInfo;
import com.kingdee.eas.framework.ICoreBase;

/**
 * output class name
 */
public class ChequeEditUI extends AbstractChequeEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(ChequeEditUI.class);
    
    /**
     * output class constructor
     */
    public ChequeEditUI() throws Exception
    {
        super();
    }

    public void onLoad() throws Exception {
    	super.onLoad();
    	SHEHelper.setTextFormat(this.txtLimitAmount);
    	SHEHelper.setTextFormat(this.txtAmount);
    	
    	this.actionSave.setVisible(false);
    	this.actionSubmit.setVisible(false);
    	this.actionAddNew.setVisible(false);
    	this.actionEdit.setVisible(false);
    	this.actionRemove.setVisible(false);
    	this.actionCopy.setVisible(false);
    	this.actionFirst.setVisible(false);
    	this.actionPre.setVisible(false);
    	this.actionNext.setVisible(false);
    	this.actionLast.setVisible(false);
    	this.actionTraceUp.setVisible(false);
    	this.actionTraceDown.setVisible(false);
    	this.actionPrint.setVisible(false);
    	this.actionPrintPreview.setVisible(false);
    	
    	this.actionCopyFrom.setVisible(false);
    	this.actionWorkFlowG.setVisible(false);
    	this.actionCreateFrom.setVisible(false);
    	this.actionAddLine.setVisible(false);
    	this.actionInsertLine.setVisible(false);
    	this.actionRemoveLine.setVisible(false);
    	
    	this.actionAuditResult.setVisible(false);
    	this.actionMultiapprove.setVisible(false);
    	this.actionNextPerson.setVisible(false);
    	
    	this.menuEdit.setVisible(false);
    	this.menuView.setVisible(false);
    	this.menuTable1.setVisible(false);
    	this.menuWorkflow.setVisible(false);
    	this.menuBiz.setVisible(false);
    	this.menuSubmitOption.setVisible(false);
    }
    
    public void loadFields() {
    	super.loadFields();    	
    }
    
    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

	protected IObjectValue createNewDetailData(KDTable table) {
		return null;
	}

	protected IObjectValue createNewData() {
		return new ChequeInfo();
	}
	
	protected KDTable getDetailTable() {
		return null;
	}
	protected void checkIsOUSealUp() throws Exception {
	}
	
	protected ICoreBase getBizInterface() throws Exception {
		return ChequeFactory.getRemoteInstance();
	}
}